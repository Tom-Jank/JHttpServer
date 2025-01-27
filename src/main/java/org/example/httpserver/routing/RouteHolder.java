package org.example.httpserver.routing;

import java.util.concurrent.ConcurrentHashMap;

public class RouteHolder {
  public static ConcurrentHashMap<String, RouteHandler> GET = new ConcurrentHashMap<>();

  public static void bindGET(String route, RouteHandler action) {
    GET.put(route, action);
  };
}
