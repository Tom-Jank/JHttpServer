package org.example.httpserver;

import java.util.Map;

record HttpRequest(HttpRequestMethod method, String resource, String protocol, Map<String, String> headers, String body) {
  static HttpRequest of(String[] startLine, Map<String, String> headers, String body) {
    return new HttpRequest(HttpRequestMethod.valueOf(startLine[0]), startLine[1], startLine[2], headers, body);
  }
}
