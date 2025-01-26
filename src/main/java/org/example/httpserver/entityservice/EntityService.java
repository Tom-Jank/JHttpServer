package org.example.httpserver.entityservice;

public class EntityService {
  public EntityService() {};

  public Entity createAndReturnEntity() {
    return new Entity("Marek", 12, "Mostowiak");
  }
}
