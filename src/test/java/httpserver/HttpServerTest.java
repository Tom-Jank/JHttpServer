package httpserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.example.httpserver.HttpServer;
import org.example.httpserver.routing.RouteHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
*  This class test general behaviour of the http server
*/
class HttpServerTest {
  private HttpServer httpServer;
  private static final int PORT_NUMBER = 4200;
  private static final String TEST_URL = "http://localhost:" + PORT_NUMBER + "/test";

  @BeforeEach
  public void setup() {
    httpServer = new HttpServer(PORT_NUMBER);
    RouteHolder.bindGET("/test", () -> { return "test route"; });
    new Thread(httpServer::listen).start();
  }

  @AfterEach
  public void tearDown() {
    httpServer.stopListening();
  }

  @Test
  void shouldRespondToGETRequest() {
    String something = sendTestRequest();
    assertThat(something).isNotNull();
    assertThat(something).isNotBlank();
  }

  @Test
  void shouldRespondToMultipleGETRequests() {
    List<String> responses = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      responses.add(sendTestRequest());
    }

    assertThat(responses).isNotNull();
    assertThat(responses.size()).isEqualTo(10);
  }

  private static String sendTestRequest() {
    try (var client = HttpClient.newHttpClient()) {
      var request = HttpRequest.newBuilder().uri(URI.create(TEST_URL)).build();
      return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Error sending request: " + e.getMessage());
    }
  }
}
