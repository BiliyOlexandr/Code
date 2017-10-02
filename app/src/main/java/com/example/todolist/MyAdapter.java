package com.example.todolist;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

  private Cursor cursor;

  public MyAdapter(Cursor myCursor) {
    this.cursor = myCursor;
  }

  //Создание нового представления
  @Override public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
    return new ViewHolder(view);
  }

  public void onBindViewHolder(ViewHolder holder, int position){
  //Заполнение заданного представления данными
    if(cursor.moveToFirst())
   holder.nameTask.setText(cursor.getString(0));
   holder.descriptionTask.setText(cursor.getString(1));
  }
  //Возвращает количество вариантов в наборе данных
  //Длина массива cursor равна количеству элементов данных в RecyclerView.
  @Override public int getItemCount() {
    return cursor.getCount();
  }

  //Данные передаются адаптеру через конструктор.
  public static class ViewHolder extends RecyclerView.ViewHolder {
    //Здесь ддолжна быть кастомная View
    private final TextView nameTask;
    private final TextView descriptionTask;

    public ViewHolder(View itemView) {
      super(itemView);
      nameTask = (TextView) itemView.findViewById(R.id.task_name);
      descriptionTask = (TextView) itemView.findViewById(R.id.discription_text);
    }
  }
}
