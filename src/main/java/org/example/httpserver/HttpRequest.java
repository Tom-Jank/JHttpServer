package org.example.httpserver;

record HttpRequest(HttpRequestMethod method, String resource, String protocol) {
  static HttpRequest of(String[] request) {
    return new HttpRequest(HttpRequestMethod.valueOf(request[0]), request[1], request[2]);
  }
}
