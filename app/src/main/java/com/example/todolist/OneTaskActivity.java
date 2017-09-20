package com.example.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OneTaskActivity extends AppCompatActivity {

  private SQLiteDatabase db;
  private EditText textName;
  private EditText textDiscription;
  private ToDoListDataBaseHelper toDoListDataBaseHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_one_task);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    final Button okButton = (Button) findViewById(R.id.ok_button);
    final Button cencelButton = (Button) findViewById(R.id.cencel_button);
    textName = (EditText) findViewById(R.id.name_text);
    textDiscription = (EditText) findViewById(R.id.discription_text);
    okButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        // TODO TODO 1
        toDoListDataBaseHelper = new ToDoListDataBaseHelper(OneTaskActivity.this);
        db = toDoListDataBaseHelper.getWritableDatabase();
        ToDoListDataBaseHelper.insertTask(db, textName.getText().toString(),
            textDiscription.getText().toString());
        finish();
      }
    });

    cencelButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(OneTaskActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return false;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

}
