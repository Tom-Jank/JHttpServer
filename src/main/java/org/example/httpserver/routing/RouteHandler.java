package org.example.httpserver.routing;

@FunctionalInterface
public interface RouteHandler {
  Object handle();

  static RouteHandler bindVoid(Runnable runnable) {
    return () -> {
      runnable.run();
      return null;
    };
  }
}

