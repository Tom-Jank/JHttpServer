package org.example;

import org.example.httpserver.HttpServer;

public class Main {
  public static void main(String[] args) {
    var server = new HttpServer(8080);
    server.bind("/test", "For now action is creating a body :D");
    server.bind("/second", "This is a second endpoint");
    server.listen();
  }
}
