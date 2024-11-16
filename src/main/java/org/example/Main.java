package org.example;

import org.example.httpserver.HttpServer;
import org.example.httpserver.routing.RouteHolder;

/**
 * Things yet to do
 * - Implementing function invocations on endpoints
 * - Route grouping
 * - More request methods eg. POST, PUT
 */
public class Main {
  public static void main(String[] args) {
    var server = new HttpServer(8080);
    RouteHolder.bindGET("/normal", "This is a second endpoint");
    server.listen();
  }
}
