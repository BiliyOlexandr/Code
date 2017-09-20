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
  // TODO Replace all comments on english (translate in eng)
  //Эти приватные переменные добавляются для того,
  //чтобы базу данных и курсор можно было закрыть в методе onDestroy().
  private SQLiteDatabase db;
  private Cursor cursor;
  private AlertDialog.Builder builder;
  private ToDoListDataBaseHelper toDoListDataBaseHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) { // TODO FIX ALL WARNINGS (ORANGE LINES)
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView listTasks = (ListView) findViewById(R.id.listView);

    try {
      SQLiteOpenHelper todolistDatabaseHelper = new ToDoListDataBaseHelper(this);
      //Получить ссылку на базу данных.
      db = todolistDatabaseHelper.getReadableDatabase();
      cursor = db.query(ToDoListDataBaseHelper.TABLE_NAME, new String[] {
          "_id", ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION
      }, null, null, null, null, null);
      //Создать курсор.
      CursorAdapter listAdapter =
          new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
              //Связать содержимое столбца NAME с текстом в ListView.
              new String[] { ToDoListDataBaseHelper.NAME, ToDoListDataBaseHelper.DISCRIPTION },
              new int[] { android.R.id.text1 }, 0);

      //связываем адаптер с курсором.
      listTasks.setAdapter(listAdapter);
    } catch (SQLException ex) {
      Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
      toast.show();
    }

    //Cобытие по нажатию на элемент списка, вызываеться активити и передаеться номер элемента
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
            //Код удаления полей БД
            db.delete(ToDoListDataBaseHelper.TABLE_NAME, "_id = ?",
                new String[] { Integer.toString(taskPosition) });
            //Перезапускаем активность
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
        //intent.putExtra(OneTaskActivity.TASK_FLAG, true);
        startActivity(intent);
      }
    });
  }

  //База данных и курсор закрываются в методе onDestroy() активности. Курсор остается открытым
  // до того момента, когда он перестает быть нужным адаптеру.
  @Override public void onDestroy() {
    super.onDestroy();
    cursor.close();
    db.close();
  }
}

