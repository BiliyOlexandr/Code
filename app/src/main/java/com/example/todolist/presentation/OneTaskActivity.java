package com.example.todolist.presentation;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.todolist.R;
import com.example.todolist.model.TaskDB;
import com.example.todolist.model.ToDoListDataBaseHelper;

public class OneTaskActivity extends AppCompatActivity {

  private SQLiteDatabase db;
  private EditText textName;
  private EditText textDiscription;
  private TaskDB mTask;
  private OneTaskViewModel mViewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_one_task);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    final Button okButton = (Button) findViewById(R.id.ok_button);
    final Button cancelButton = (Button) findViewById(R.id.cencel_button);
    textName = (EditText) findViewById(R.id.name_text);
    textDiscription = (EditText) findViewById(R.id.discription_text);

    SQLiteOpenHelper toDoListDataBaseHelper = new ToDoListDataBaseHelper(OneTaskActivity.this);
    db = toDoListDataBaseHelper.getWritableDatabase();

    okButton.setOnClickListener((View view) -> {
      //ToDoListDataBaseHelper.insertTask(db, textName.getText().toString(),
          //textDiscription.getText().toString());


      finish();
    });

    cancelButton.setOnClickListener(view -> finish());
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
