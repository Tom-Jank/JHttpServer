package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public record HttpServer(int portNumber) {

  public void listen() {
    try (var socket = new ServerSocket(portNumber)) {
      while (true) new HttpServerHandler(socket.accept()).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static class HttpServerHandler extends Thread {
    private final Socket clientSocket;

    public HttpServerHandler(Socket socket) {
      this.clientSocket = socket;
    }

    public void run() {
      try (var out = new PrintWriter(clientSocket.getOutputStream(), true);
          var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
        try (clientSocket) {
          out.write("HTTP/1.1 200 OK\r\n");
          out.write("\r\n");
          out.flush();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
