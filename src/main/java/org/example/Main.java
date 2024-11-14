package org.example;

import org.example.httpserver.HttpServer;

public class Main {
  public static void main(String[] args) {
    var server = new HttpServer(8080);
    server.listen();
  }
}
