package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
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
      return RequestMethod.valueOf(
          Arrays.stream(in.readLine().split(" ")).findFirst().orElseThrow(RuntimeException::new));
    } catch (IOException e) {
      throw new HttpServerException("Error reading request: " + e.getMessage());
    }
  }

  private void respond(PrintWriter out) {
    out.write("HTTP/1.1 200 OK\r\n");
    out.write("date: " + LocalDateTime.now() + " \r\n");
    out.write("cache-control: public, max-age: 3600 \r\n");
    out.write("content-type: text/html \r\n");

    out.write("\r\n");
    out.write("body: somebody \r\n");
    out.flush();
  }
}
