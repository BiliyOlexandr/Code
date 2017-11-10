package com.example.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



//https://habrahabr.ru/post/332562/
//https://developer.android.com/topic/libraries/architecture/index.html

@Entity(tableName = "task")
public class TaskDB {
  @PrimaryKey
  private int id;
  @ColumnInfo(name = "name")
  private String name;
  @ColumnInfo(name =   "description")
  private  String description;

  public TaskDB(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
