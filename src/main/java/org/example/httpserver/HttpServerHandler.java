package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import org.example.httpserver.routing.RouteHolder;

class HttpServerHandler implements Runnable {
  private final Socket clientSocket;

  public HttpServerHandler(Socket socket) {
    this.clientSocket = socket;
  }

  @Override
  public void run() {
    try (var out = new PrintWriter(clientSocket.getOutputStream(), false);
        var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      try (clientSocket) {
        var request = readRequest(in);
        switch (request.method()) {
          case GET -> responseTest(out, request.resource());
          case POST -> writeResponse(out, HttpResponse.badRequest("Post method not supported"));
          default -> writeResponse(out, HttpResponse.badRequest("Method not supported"));
        }
      } catch (IOException e) {
        writeResponse(out, HttpResponse.badRequest("Error parsing request"));
      }
    } catch (IOException e) {
      throw new RuntimeException("Error accepting connection: " + e.getMessage());
    }
  }

  // todo fix a bug where request from postman throws exception because of empty BufferedReader
  private HttpRequest readRequest(BufferedReader in) throws IOException {
      String[] requestString = Arrays.stream(in.readLine().split(" ")).toArray(String[]::new);
      return HttpRequest.of(requestString);
  }

  private void respond(PrintWriter out, String route) {
    var body = RouteHolder.GET.get(route);
    HttpResponse response;
    if (body == null) {
      response = HttpResponse.notFound("Resource not found");
    } else {
      response = HttpResponse.ok(body);
    }
    writeResponse(out, response);
  }

  private void responseTest(PrintWriter out, String route) {
    HttpResponse response;
    var action = RouteHolder.TEST.get(route);
    try {
      var result = action.call();
      response = HttpResponse.ok((String) result);
    } catch (Exception e) {
      response = HttpResponse.internalServerError(e.getMessage());
    }
    writeResponse(out, response);
  }

  private void writeResponse(PrintWriter out, HttpResponse response) {
    out.write(response.type() + "\r\n");
    response.headers().forEach(header -> out.write(header + "\r\n"));
    out.write("\r\n");
    out.write(response.body() + "\r\n");
    out.flush();
  }
}
