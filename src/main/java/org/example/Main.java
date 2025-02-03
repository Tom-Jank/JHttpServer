package org.example;

import org.example.httpserver.HttpServer;
import org.example.httpserver.routing.RouteHandler;
import org.example.httpserver.routing.RouteHolder;

public class Main {
  public static void main(String[] args) {
    final HttpServer server = new HttpServer(8080);
    RouteHolder.bindGET("/test", () -> "Hello world!");
    RouteHolder.bindGET("/void", RouteHandler.bindVoid(Main::doSomethingWithoutReturn));
    RouteHolder.bindPOST("/post", () -> "Post method handled!");
    RouteHolder.bindPOST("/voidPost", RouteHandler.bindVoid(Main::doSomethingWithoutReturn));
//    RouteHolder.bindPOST("/postBody", postHandler);
    server.listen();
  }

  //todo later create the logic to handle void functions
  public static void doSomethingWithoutReturn() {
    System.out.println("I'm done");
  }

  //todo handle post method when argument is needed for handler execution
  //for now just text plain is sufficient, later handle json parsing
  public static String postHandler(String argument) {
    return "post method handled!";
  }
}