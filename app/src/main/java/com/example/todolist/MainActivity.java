package com.example.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private SQLiteOpenHelper todolistDatabaseHelper;
  private CursorAdapter listAdapter;
  private SQLiteDatabase db;
  private Cursor cursor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /*
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        cursorAdapter = new MyAdapter(cursor);
        mRecyclerView.setAdapter(cursorAdapter);
      */

    todolistDatabaseHelper = new ToDoListDataBaseHelper(this);

    ListView listTasks = (ListView) findViewById(R.id.listView);

    listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
        // The context of the column NAME with text in ListView.
        new String[] { ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION },
        new int[] { android.R.id.text1 }, 0);

    // Connect Adapter with Cursor.
    listTasks.setAdapter(listAdapter);

    // Event of click on the list item, call to Activity and put number of item.
    listTasks.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ChangeTaskActivity.class);
        intent.putExtra(ChangeTaskActivity.CHANGE_EXTRA_TASKNO, (int) id);
        startActivity(intent);
      }
    });

    listTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position,
          long id) {
        final int taskPosition = (int) id;
        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.delete)
            .setMessage(R.string.was_deleted)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, R.string.was_deleted, Toast.LENGTH_LONG).show();
                //Code to delete DB columns
                db.delete(ToDoListDataBaseHelper.TABLE_NAME, "_id = ?",
                    new String[] { Integer.toString(taskPosition) });
                reloadData();
              }
            })
            .setNegativeButton(R.string.cencel, null)
            .show();
        return true;
      }
    });

    findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, OneTaskActivity.class);
        startActivity(intent);
      }
    });
  }

  private void reloadData() {
    //Gets link on the data base.
    db = todolistDatabaseHelper.getReadableDatabase();
    try {
      //Create cursor.
      cursor = db.query(ToDoListDataBaseHelper.TABLE_NAME, null, null, null, null, null, null);
    } catch (SQLException ex) {
      Toast toast = Toast.makeText(this, R.string.dbnavailable, Toast.LENGTH_SHORT);
      toast.show();
    }
    // cursor adapter issue
    if (listAdapter != null) {
      listAdapter.changeCursor(cursor);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    reloadData();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    cursor.close();
    db.close();
  }
}

