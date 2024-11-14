package org.example.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {
  private final ServerSocket serverSocket;
  private volatile boolean IS_RUNNING = true;

  public HttpServer(int portNumber) {
    try {
      this.serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      throw new HttpServerException("Could not initialize socket: " + e.getMessage());
    }
  }

  public void listen() {
    try (serverSocket) {
      System.out.println("Listening on port: " + serverSocket.getLocalPort());
      connectClients();
    } catch (IOException e) {
      throw new HttpServerException("Exception when listening: " + e.getMessage());
    }
  }

  private void connectClients() {
    while (IS_RUNNING) {
      try {
        new HttpServerHandler(serverSocket.accept()).start();
      } catch (IOException e) {
        if (!IS_RUNNING) break;
        throw new HttpServerException("Connection error: " + e.getMessage());
      }
    }
  }

  public void stopListening() {
    IS_RUNNING = false;
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
      }
    } catch (IOException e) {
      throw new HttpServerException("Could not close the socket: " + e.getMessage());
    }
  }
}
