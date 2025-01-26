package org.example;

import java.util.List;
import org.example.httpserver.HttpServer;
import org.example.httpserver.entityservice.EntityService;
import org.example.httpserver.routing.RouteHolder;

/**
 * Things yet to do
 * - Implementing function invocations on endpoints
 * - Route grouping
 * - More request methods eg. POST, PUT
 */
public class Main {

  public static void main(String[] args) {
    final HttpServer server = new HttpServer(8080);
    final EntityService service = new EntityService();

    RouteHolder.bindGET("/hello", Main::helloMessage);
    RouteHolder.bindGET("/integer", Main::returnInteger);
    RouteHolder.bindGET("/list", Main::returnListOfStrings);
    RouteHolder.bindGET("/entity", service::createAndReturnEntity);
    server.listen();
  }

  public static String helloMessage() {
    return "Hello from function!";
  }

  public static Integer returnInteger() {
    return 1;
  }

  public static List<String> returnListOfStrings() {
    return List.of("First", "Second");
  }

  // later create the logic to handle void functions
  public static void doSomethingWithoutReturn() {
    System.out.println("I'm done");
  }
}