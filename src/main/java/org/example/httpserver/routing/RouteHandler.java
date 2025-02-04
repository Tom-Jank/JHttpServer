package org.example.httpserver.routing;

@FunctionalInterface
public interface RouteHandler<T> {
  T handle();

  static RouteHandler<Void> bindVoid(Runnable runnable) {
    return () -> {
      runnable.run();
      return null;
    };
  }
}

