package org.example;

import org.example.httpserver.HttpServer;

/**
 * The goal of this application is to serve as a simple http server
 * with support for multiple connections and basic request types like GET.
 */
public class Main {
  public static void main(String[] args) {
    var server = new HttpServer(8080);
    server.serve();
  }
}