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
  // Position the task in list
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

    // Getting position to the task of intent.
    taskNo = (Integer) getIntent().getExtras().get(CHANGE_EXTRA_TASKNO);
    try {
      // TODO TODO 1
      SQLiteOpenHelper todolistDataBaseHelper = new ToDoListDataBaseHelper(this);
      db = todolistDataBaseHelper.getWritableDatabase();
      Cursor cursor = db.query(ToDoListDataBaseHelper.TABLE_NAME,
          new String[] { ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION },
          "_id = ?", new String[] { Integer.toString(taskNo) }, null, null, null);
      //Get the task data from the cursor.
      if (cursor.moveToFirst()) {
        //Filling name to the task.
        textName.setText(cursor.getString(0));
        //Filling description to the task.
        textDiscription.setText(cursor.getString(1));
      }
    } catch (SQLException ex) {
      Toast toast = Toast.makeText(this, R.string.dbnavailable, Toast.LENGTH_SHORT);
      toast.show();
    }

    okButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(ChangeTaskActivity.this, MainActivity.class);
        // TODO TODO 1
        toDoListDataBaseHelper = new ToDoListDataBaseHelper(ChangeTaskActivity.this);
        db = toDoListDataBaseHelper.getWritableDatabase();
        //Code of change of columns to the data base during change EditText.
        ContentValues taskValues = new ContentValues();
        taskValues.put(ToDoListDataBaseHelper.NAME, textName.getText().toString());
        taskValues.put(ToDoListDataBaseHelper.DISCRIPTION, textDiscription.getText().toString());
        db.update(ToDoListDataBaseHelper.TABLE_NAME, taskValues, "_id = ?",
            new String[] { Integer.toString(taskNo) });
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