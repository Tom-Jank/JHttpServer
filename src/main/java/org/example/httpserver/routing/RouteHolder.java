package org.example.httpserver.routing;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RouteHolder {
  private static ConcurrentHashMap<String, RouteHandler> GET = new ConcurrentHashMap<>();
  private static ConcurrentHashMap<String, RouteHandler> POST = new ConcurrentHashMap<>();

  public static void bindGET(String route, RouteHandler action) {
    GET.put(route, action);
  };

  public static Optional<RouteHandler> getGET(String route) {
    return Optional.ofNullable(GET.get(route));
  }

  public static void bindPOST(String route, RouteHandler action) { POST.put(route, action); };

  public static Optional<RouteHandler> getPOST(String route) {
    return Optional.ofNullable(POST.get(route));
  }
}
