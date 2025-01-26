package org.example;

import org.example.httpserver.HttpServer;
import org.example.httpserver.routing.RouteHolder;
import org.example.test.TestService;

/**
 * Things yet to do
 * - Implementing function invocations on endpoints
 * - Route grouping
 * - More request methods eg. POST, PUT
 */
public class Main {

  public static void main(String[] args) {
    final TestService testService = new TestService();
    final HttpServer server = new HttpServer(8080);

    RouteHolder.bindGET("/hello", Main::helloMessage);
    server.listen();
  }

  public static String helloMessage() {
    return "Hello from function!";
  }
}