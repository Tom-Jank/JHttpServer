package org.example.httpserver;

import java.util.concurrent.ConcurrentHashMap;

public class RouteHolder {
  static ConcurrentHashMap<String, String> GET = new ConcurrentHashMap<>();

  // binds route and action to route map
  public static void bindGET(String route, String action) {
    GET.put(route, action);
  }
}