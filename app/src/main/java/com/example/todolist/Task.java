package com.example.todolist;

class Task {
  private String name;
  private String description;

  Task(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  String getDescription() {
    return description;
  }
}
