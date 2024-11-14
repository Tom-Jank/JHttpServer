package org.example.httpserver;

record HttpRequest(RequestMethod method, String resource, String protocol) {
  static HttpRequest of(String[] request) {
    return new HttpRequest(RequestMethod.valueOf(request[0]), request[1], request[2]);
  }
}
