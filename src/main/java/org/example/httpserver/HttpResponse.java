package org.example.httpserver;

import java.time.LocalDateTime;
import java.util.List;

record HttpResponse(String type, List<String> headers, String body) {
  static HttpResponse of(String body) {
    String type = "HTTP/1.1 200 OK";
    List<String> headers = List.of(
        "Date: " + LocalDateTime.now(),
        "Content-Type: text/html; charset=utf-8",
        "Server: JHttpServer"
    );

    return new HttpResponse(type, headers, body);
  }
}
