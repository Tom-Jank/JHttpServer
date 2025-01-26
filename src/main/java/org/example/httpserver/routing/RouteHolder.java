package org.example.httpserver.routing;

import java.util.concurrent.ConcurrentHashMap;

public class RouteHolder {
  public static ConcurrentHashMap<String, String> GET = new ConcurrentHashMap<>();
  public static ConcurrentHashMap<String, RouteHandler> TEST = new ConcurrentHashMap<>();

  public static void bindGET(String route, RouteHandler action){
    TEST.put(route, action);
  };
}
