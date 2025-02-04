package org.example;

import org.example.httpserver.HttpServer;
import org.example.httpserver.routing.RouteHolder;

// todo write logic for passing path variable with endpoints
// todo write logic for passing query parameters with endpoints
public class Main {
  public static void main(String[] args) {
    final HttpServer server = new HttpServer(8080);
    RouteHolder.bindGET("/test", () -> "Hello world!");
    RouteHolder.bindPOST("/post", () -> "Post method handled!");
    server.listen();
  }
}