package org.example.httpserver.entityservice;

public class Entity {
  private String name;
  private String surname;
  private Integer age;

  public Entity() {}

  public Entity(String name, Integer age, String surname) {
    this.name = name;
    this.age = age;
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
