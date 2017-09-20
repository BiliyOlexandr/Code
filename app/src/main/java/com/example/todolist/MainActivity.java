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
  private SQLiteDatabase db;
  private Cursor cursor;
  private AlertDialog.Builder builder;
  private ToDoListDataBaseHelper toDoListDataBaseHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView listTasks = (ListView) findViewById(R.id.listView);

    try {
      SQLiteOpenHelper todolistDatabaseHelper = new ToDoListDataBaseHelper(this);
      //Gets link on the data base.
      db = todolistDatabaseHelper.getReadableDatabase();
      cursor = db.query(ToDoListDataBaseHelper.TABLE_NAME, new String[] {
          "_id", ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION
      }, null, null, null, null, null);
      //Create cursor.
      CursorAdapter listAdapter =
          new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
              // The context of the column NAME with text in ListView.
              new String[] { ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION },
              new int[] { android.R.id.text1 }, 0);

      // Connect Adapter with Cursor.
      listTasks.setAdapter(listAdapter);
    } catch (SQLException ex) {
      Toast toast = Toast.makeText(this, R.string.dbnavailable, Toast.LENGTH_SHORT);
      toast.show();
    }

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
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.was_deleted);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(MainActivity.this, R.string.was_deleted, Toast.LENGTH_LONG).show();
            // TODO 1 Organize correct db management
            toDoListDataBaseHelper = new ToDoListDataBaseHelper(MainActivity.this);
            db = toDoListDataBaseHelper.getWritableDatabase();
            //Code to delete DB columns
            db.delete(ToDoListDataBaseHelper.TABLE_NAME, "_id = ?",
                new String[] { Integer.toString(taskPosition) });
            // Restart Activity
            // TODO replace next 6 lines of code with 1 line - "listAdapter.notifyDataSetChanged()"
            Intent intent = getIntent();
            overridePendingTransition(0, 0);//4
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//5
            finish();//6
            overridePendingTransition(0, 0);//7
            startActivity(intent);//8
          }
        });
        builder.setNegativeButton(R.string.cencel, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
          }
        });
        builder.show();
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

  //Data base and Cursor has closed in method onDestroy() of Activity.
  @Override public void onDestroy() {
    super.onDestroy();
    cursor.close();
    db.close();
  }
}

