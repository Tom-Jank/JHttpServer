package org.example.httpserver.routing;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class RouteHolder {
  public static ConcurrentHashMap<String, String> GET = new ConcurrentHashMap<>();
  public static ConcurrentHashMap<String, Callable<?>> TEST = new ConcurrentHashMap<>();

  // binds route and action to route map
  public static void bindGET(String route, String action) {
    GET.put(route, action);
  }

  public static void bindTest(String route, Callable<?> action){
    TEST.put(route, action);
  };
}
