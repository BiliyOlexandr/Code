package com.example.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OneTaskActivity extends AppCompatActivity {
  //Позиция задачи в списке
  public static final String EXTRA_TASKNO = "taskNo";

  SQLiteDatabase db;
  EditText textName;
  EditText textDiscription;
  ToDoListDataBaseHelper toDoListDataBaseHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_one_task);

    final Button okButton = (Button) findViewById(R.id.ok_button);
    final Button cencelButton = (Button) findViewById(R.id.cencel_button);
    textName = (EditText) findViewById(R.id.name_text);
    textDiscription = (EditText) findViewById(R.id.discription_text);
    okButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(OneTaskActivity.this, MainActivity.class);
        toDoListDataBaseHelper = new ToDoListDataBaseHelper(OneTaskActivity.this);
        db = toDoListDataBaseHelper.getWritableDatabase();
        toDoListDataBaseHelper.insertTask(db, textName.getText().toString(),
            textDiscription.getText().toString());
        startActivity(intent);
      }
    });

    cencelButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(OneTaskActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }

  public void getString() {
    String textString = getIntent().getStringExtra("textName");
  }
}
