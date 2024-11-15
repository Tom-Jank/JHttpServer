package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

class HttpServerHandler implements Runnable {
  private final Socket clientSocket;

  public HttpServerHandler(Socket socket) {
    this.clientSocket = socket;
  }

  @Override
  public void run() {
    try (var out = new PrintWriter(clientSocket.getOutputStream(), true);
        var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      try (clientSocket) {
        var request = readRequest(in);
        switch (request.method()) {
          case GET -> respond(out, request.resource());
          case POST -> throw new HttpServerException("Lmao nice try");
          default -> throw new HttpServerException("Method not supported");
        }
      }
    } catch (IOException e) {
      throw new HttpServerException("Error accepting connection: " + e.getMessage());
    }
  }

  private void respond(PrintWriter out, String route) {
    var body = RouteHolder.GET.get(route);
    if (body == null) body = "Something wrong";
    var response = HttpResponse.of(body);

    out.write(response.type() + "\r\n");
    response.headers().forEach(header -> out.write(header + "\r\n"));
    out.write("\r\n");
    out.write(response.body() + "\r\n");
    out.flush();
  }

  // todo fix a bug where request from postman throws exception because of empty BufferedReader
  private HttpRequest readRequest(BufferedReader in) {
    try {
      String[] requestString = Arrays.stream(in.readLine().split(" ")).toArray(String[]::new);
      return HttpRequest.of(requestString);
    } catch (IOException e) {
      throw new HttpServerException("Error parsing request: " + e.getMessage());
    }
  }
}
