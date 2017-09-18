package com.example.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeTaskActivity extends AppCompatActivity {
  //Позиция задачи в списке
  public static final String CHANGE_EXTRA_TASKNO = "changetaskNo";

  private SQLiteDatabase db;
  private EditText textName;
  private EditText textDiscription;
  private int taskNo;
  private ToDoListDataBaseHelper toDoListDataBaseHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_change_task);

    final Button okButton = (Button) findViewById(R.id.change_ok_button);
    final Button cencelButton = (Button) findViewById(R.id.change_cencel_button);
    textName = (EditText) findViewById(R.id.change_name_text);
    textDiscription = (EditText) findViewById(R.id.change_discription_text);

    //Получение позиции задачи из интента
    taskNo = (Integer) getIntent().getExtras().get(CHANGE_EXTRA_TASKNO);
    try {
      // TODO TODO 1
      SQLiteOpenHelper todolistDataBaseHelper = new ToDoListDataBaseHelper(this);
      db = todolistDataBaseHelper.getWritableDatabase();
      Cursor cursor = db.query("TASK", new String[] { "NAME", "DISCRIPTION" }, "_id = ?",
          new String[] { Integer.toString(taskNo) }, null, null, null);
      //Переход к первой записи в курсоре
      if (cursor.moveToFirst()) {
        //Получение данных задачи из курсора
        String nameText = cursor.getString(0);
        String discriptionText = cursor.getString(1);  // TODO Grammar
        //Заполнение названия задачи
        textName.setText(nameText);
        //Заполнение описания задачи
        textDiscription.setText(discriptionText);
      }
    } catch (SQLException ex) {
      Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
      toast.show();
    }

    okButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(ChangeTaskActivity.this, MainActivity.class);
        // TODO TODO 1
        toDoListDataBaseHelper = new ToDoListDataBaseHelper(ChangeTaskActivity.this);
        db = toDoListDataBaseHelper.getWritableDatabase();
        //Код изминения полей БД при изменении полей EditText
        ContentValues taskValues = new ContentValues();
        taskValues.put("NAME", textName.getText().toString());
        taskValues.put("DISCRIPTION", textDiscription.getText().toString());
        db.update("TASK", taskValues, "_id = ?", new String[] { Integer.toString(taskNo) });
        startActivity(intent);
      }
    });

    cencelButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(ChangeTaskActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }

  public void getString() {
    String textString = getIntent().getStringExtra("textName");
  }
}