package com.example.todolist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



//https://habrahabr.ru/post/332562/
//https://habrahabr.ru/post/332562/
//https://habrahabr.ru/post/332562/

@Entity(tableName = "task")
public class TaskDB {
  @PrimaryKey
  private int id;
  @ColumnInfo(name = "name")
  private String name;
  @ColumnInfo(name =   "description")
  private  String description;
  @ColumnInfo (name = "rate")
  private String rate;

}
