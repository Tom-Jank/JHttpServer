package org.example.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public record HttpServer(int portNumber) {

  public void serve() {
    try (
        var serverSocket = new ServerSocket(portNumber);
        var socket = serverSocket.accept()
    ) {
      System.out.println("Listening on port: " + this.portNumber);
    } catch (IOException e) {
      System.out.println("Failed to start the server: " + e.getMessage());
    }
  }
}
