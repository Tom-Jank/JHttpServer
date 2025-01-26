package org.example.httpserver.routing;

@FunctionalInterface
public interface RouteHandler {
  String handle();
}
