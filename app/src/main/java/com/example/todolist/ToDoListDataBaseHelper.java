package com.example.todolist;

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

  ToDoListDataBaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    //Код из метода onCreate() выносится в метод updateMyDatabase().
    updateMyDatabase(db, 0, DB_VERSION);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Код из метода onUpgrade() выносится в метод updateMyDatabase().
    updateMyDatabase(db, oldVersion, newVersion);
  }
  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Здесь размещается ваш код
  }
  private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("CREATE TABLE" + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
          + NAME + ", " // TODO Replace all literals with static constants that may be accessible from another classes
          + DISCRIPTION + ");");
  }

  public static void insertTask(SQLiteDatabase db, String name, String discription) {
    ContentValues taskValues = new ContentValues();
    taskValues.put(NAME, name);
    taskValues.put(DISCRIPTION, discription);
    db.insert(TABLE_NAME, null, taskValues);
  }
}

