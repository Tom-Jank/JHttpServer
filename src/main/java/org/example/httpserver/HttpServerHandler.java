package org.example.httpserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.example.httpserver.routing.RouteHandler;
import org.example.httpserver.routing.RouteHolder;

class HttpServerHandler implements Runnable {
  private final Socket clientSocket;
  private final ObjectMapper objectMapper;

  public HttpServerHandler(Socket socket) {
    this.clientSocket = socket;
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public void run() {
    try (var out = new PrintWriter(clientSocket.getOutputStream(), false);
        var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      try (clientSocket) {
        handleRequest(readRequest(in), out);
      } catch (IOException e) {
        writeResponse(out, HttpResponse.badRequest("Error parsing request"));
      }
    } catch (IOException e) {
      throw new RuntimeException("Error accepting connection: " + e.getMessage());
    }
  }

  private HttpRequest readRequest(BufferedReader in) throws IOException {
    var startLine = readRequestStartLine(in);
    var headers = readRequestHeaders(in);
    var body = readRequestBody(in, headers);
    return HttpRequest.of(startLine, headers, body);
  }

  private String[] readRequestStartLine(BufferedReader in) throws IOException {
    var startLine = in.readLine().split(" ");
    if (startLine.length < 3) {
      throw new IOException("Invalid request");
    }
    return startLine;
  }

  private Map<String, String> readRequestHeaders(BufferedReader in) throws IOException {
    var headers = new HashMap<String, String>();
    String line;
    while ((line = in.readLine()) != null && !line.isEmpty()) {
      // todo look if creating an object String[] every time does not hurt performance
      String[] headerParts = line.split(": ", 2);
      if (headerParts.length == 2) headers.put(headerParts[0], headerParts[1]);
    }
    return headers;
  }

  private String readRequestBody(BufferedReader in, Map<String, String> headers) throws IOException {
    if (headers.containsKey("Content-Length")) {
      var contentLength = Integer.parseInt(headers.get("Content-Length"));
      var bodyContent = new StringBuilder();
      char[] buf = new char[contentLength];
      int read = in.read(buf, 0, contentLength);
      if (read > 0) bodyContent.append(buf, 0, read);
      return bodyContent.toString();
    } else {
      return "";
    }
  }

  private void handleRequest(HttpRequest request, PrintWriter out) {
    switch (request.method()) {
      case GET, POST -> respond(out, request.resource(), request.method());
      case PUT -> writeResponse(out, HttpResponse.badRequest("PUT method not supported"));
      case DELETE -> writeResponse(out, HttpResponse.badRequest("DELETE method not supported"));
      case PATCH -> writeResponse(out, HttpResponse.badRequest("PATCH method not supported"));
      default -> writeResponse(out, HttpResponse.badRequest("Method not supported"));
    }
  }

  private void respond(PrintWriter out, String route, HttpRequestMethod method) {
    var handler = getMethodHandler(route, method);
    var response = createResponse(handler, method.toString(), route);
    writeResponse(out, response);
  }

  private Optional<RouteHandler<?>> getMethodHandler(String route, HttpRequestMethod method) {
    switch (method) {
      case GET -> {
        return RouteHolder.getGET(route);
      }
      case POST -> {
        return RouteHolder.getPOST(route);
      }
      default -> throw new RuntimeException("Method not supported");
    }
  }

  private HttpResponse createResponse(Optional<RouteHandler<?>> handler, String method, String route) {
    if (handler.isPresent()) {
      return invokeHandlerReturnResponse(handler.get());
    } else {
      return HttpResponse.notFound(method + " " + route + " " + "doesn't exist.");
    }
  }

  private HttpResponse invokeHandlerReturnResponse(RouteHandler<?> handler) {
    try {
      var jsonResult = serializeFunctionResult(handler.handle());
      return HttpResponse.ok(jsonResult);
    } catch (Exception e) {
      return HttpResponse.internalServerError(e.getMessage());
    }
  }

  private String serializeFunctionResult(Object result) throws JsonProcessingException {
    var returnValue = "";
    if (result != null) {
      returnValue = objectMapper.writeValueAsString(result);
    }
    return returnValue;
  }

  private void writeResponse(PrintWriter out, HttpResponse response) {
    out.write(response.type() + "\r\n");
    response.headers().forEach(header -> out.write(header + "\r\n"));
    out.write("\r\n");
    out.write(response.body() + "\r\n");
    out.flush();
  }
}
