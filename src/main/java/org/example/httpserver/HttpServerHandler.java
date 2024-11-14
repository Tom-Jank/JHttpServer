package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

class HttpServerHandler extends Thread {
  private final Socket clientSocket;

  public HttpServerHandler(Socket socket) {
    this.clientSocket = socket;
  }

  public void run() {
    try (var out = new PrintWriter(clientSocket.getOutputStream(), true);
        var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      try (clientSocket) {
        switch (getRequestMethod(in)) {
          case GET -> respond(out);
          case POST -> throw new HttpServerException("Lmao nice try");
          default -> throw new HttpServerException("Method not supported");
        }
      }
    } catch (IOException e) {
      throw new HttpServerException("Error accepting connection: " + e.getMessage());
    }
  }

  private RequestMethod getRequestMethod(BufferedReader in) {
    try {
      String method = Arrays.stream(in.readLine().split(" "))
          .findFirst().orElseThrow(RuntimeException::new);
      return RequestMethod.valueOf(method);
    } catch (IOException e) {
      throw new HttpServerException("Error reading request: " + e.getMessage());
    }
  }

  private void respond(PrintWriter out) {
    var response = HttpResponse.of("Hello, this is response body of HttpResponse");

    out.write(response.type() + "\r\n");
    response.headers().forEach(header -> out.write(header + "\r\n"));
    out.write("\r\n");
    out.write(response.body() + "\r\n");
    out.flush();
  }
}
