package org.example;

import org.example.httpserver.HttpServer;

public class Main {
  public static void main(String[] args) {
    final HttpServer server = new HttpServer(8080);
    server.listen();
  }

  // later create the logic to handle void functions
  public static void doSomethingWithoutReturn() {
    System.out.println("I'm done");
  }
}