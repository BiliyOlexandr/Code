package com.example.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDataBaseHelper extends SQLiteOpenHelper {

  public static final String TABLE_NAME = "TASK";
  public static final String NAME = "NAME";
  public static final String DISCRIPTION = "DISCRIPTION";

  private static final String DB_NAME = "newtodolist";
  private static final int DB_VERSION = 1;

  public ToDoListDataBaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  static void insertTask(SQLiteDatabase db, String name, String discription) {
    ContentValues taskValues = new ContentValues();
    taskValues.put(NAME, name);
    taskValues.put(DISCRIPTION, discription);
    db.insert(TABLE_NAME, null, taskValues);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE "
        + TABLE_NAME
        + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + NAME
        + ", "
        + DISCRIPTION
        + ");");
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists " + TABLE_NAME);
    onCreate(db);
  }
}

