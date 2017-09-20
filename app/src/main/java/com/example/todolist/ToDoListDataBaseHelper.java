package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ToDoListDataBaseHelper extends SQLiteOpenHelper {

  static final String TABLE_NAME = "TASK";
  static final String NAME = "NAME";
  static final String DISCRIPTION = "DISCRIPTION";

  private static final String DB_NAME = "newtodolist";
  private static final int DB_VERSION = 1;

  ToDoListDataBaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    //Code from the method onCreate() get out in the method updateMyDatabase().
    updateMyDatabase(db, 0, DB_VERSION);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Code from the method onCreate() get out in the method updateMyDatabase().
    updateMyDatabase(db, oldVersion, newVersion);
  }
  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
  private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("CREATE TABLE" + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
          + NAME + ", "
          + DISCRIPTION + ");");
  }

  static void insertTask(SQLiteDatabase db, String name, String discription) {
    ContentValues taskValues = new ContentValues();
    taskValues.put(NAME, name);
    taskValues.put(DISCRIPTION, discription);
    db.insert(TABLE_NAME, null, taskValues);
  }
}

