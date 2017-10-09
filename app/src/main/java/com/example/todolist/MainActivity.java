package com.example.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  // Position the task in list
  public static final String CHANGE_EXTRA_TASKNO = "changetaskNo";

  private SQLiteOpenHelper todolistDatabaseHelper;
  private SQLiteDatabase db;
  private Cursor cursor;
  private MyAdapter myAdapter;
  private List<Task> taskList;
  private Intent intent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_activity_main);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    taskList = new ArrayList<>();

    todolistDatabaseHelper = new ToDoListDataBaseHelper(this);

    getTasks();

    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    myAdapter = new MyAdapter(taskList, username -> {

      intent = new Intent(this, ChangeTaskActivity.class);
      intent.putExtra(CHANGE_EXTRA_TASKNO, username);
      startActivity(intent);
    }, username -> new AlertDialog.Builder(this).setTitle(R.string.delete)
        .setMessage(R.string.was_deleted)
        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
          Toast.makeText(MainActivity.this, R.string.was_deleted, Toast.LENGTH_LONG).show();
          //Code to delete DB columns
          db = todolistDatabaseHelper.getWritableDatabase();
          db.delete(ToDoListDataBaseHelper.TABLE_NAME, "NAME = ?", new String[] { username });
          db.close();
          getTasks();
        })
        .setNegativeButton(R.string.cencel, null)
        .show());
    recyclerView.setAdapter(myAdapter);

    findViewById(R.id.new_fab).setOnClickListener(view -> {
      Intent intent = new Intent(MainActivity.this, OneTaskActivity.class);
      startActivity(intent);
    });
  }

  private void getTasks() {
    taskList.clear();
    db = todolistDatabaseHelper.getReadableDatabase();
    cursor = db.query(ToDoListDataBaseHelper.TABLE_NAME,
        new String[] { ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION }, null,
        null, null, null, null);

    for (int i = 0; i <= cursor.getCount(); i++) {
      if (cursor.moveToNext()) {
        Task task = new Task(cursor.getString(0), cursor.getString(1));
        taskList.add(task);
      }
    }
    if (myAdapter != null) {
      myAdapter.notifyDataSetChanged();
    }
  }

  @Override protected void onRestart() {
    super.onRestart();
    getTasks();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    cursor.close();
    db.close();
  }

  interface OnTaskClickListener {
    void onTaskClicked(String username);
  }

  interface OnLongTaskClickListener {
    void onLongTaskClicked(String username);
  }
}

