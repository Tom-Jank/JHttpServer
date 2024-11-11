package org.example.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public record HttpServer(int portNumber) {

  public void serve() {
    try (
        var serverSocket = new ServerSocket(portNumber);
        var clientSocket = serverSocket.accept();
        var out = new PrintWriter(clientSocket.getOutputStream(), true);
        var in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()))
    ) {
      out.write("HTTP/1.1 200 OK\r\n");
      out.write("\r\n");
      out.flush();
    } catch (IOException e) {
      System.out.println("Failed to start the server: " + e.getMessage());
    }
  }
}
