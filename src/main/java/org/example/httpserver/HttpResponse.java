package org.example.httpserver;

import java.time.LocalDateTime;
import java.util.List;

record HttpResponse(String type, List<String> headers, String body) {

  static HttpResponse ok(String body) {
    String type = "HTTP/1.1 200 OK";
    List<String> headers = List.of(
        "Date: " + LocalDateTime.now(),
        "Content-Type: text/html; charset=utf-8",
        "Server: JHttpServer"
    );

    return new HttpResponse(type, headers, body);
  }

  static HttpResponse badRequest(String body) {
    String type = "HTTP/1.1 400 Bad Request";
    List<String> headers = List.of(
        "Date: " + LocalDateTime.now(),
        "Content-Type: text/html; charset=utf-8",
        "Server: JHttpServer"
    );

    return new HttpResponse(type, headers, body);
  }


  static HttpResponse notFound(String body) {
    String type = "HTTP/1.1 404 Not Found";
    List<String> headers = List.of(
        "Date: " + LocalDateTime.now(),
        "Content-Type: text/html; charset=utf-8",
        "Server: JHttpServer"
    );

    return new HttpResponse(type, headers, body);
  }


  static HttpResponse internalServerError(String body) {
    String type = "HTTP/1.1 500 Internal Server Error";
    List<String> headers = List.of(
        "Date: " + LocalDateTime.now(),
        "Content-Type: text/html; charset=utf-8",
        "Server: JHttpServer"
    );

    return new HttpResponse(type, headers, body);
  }
}
