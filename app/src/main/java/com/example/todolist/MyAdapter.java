package com.example.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

  private List<Task> taskList;
  private MainActivity.OnTaskClickListener clickListener;
  private MainActivity.OnLongTaskClickListener clickLongListener;

  MyAdapter(List<Task> taskList, MainActivity.OnTaskClickListener clickListener,
      MainActivity.OnLongTaskClickListener clickLongListener) {
    this.taskList = taskList;
    this.clickListener = clickListener;
    this.clickLongListener = clickLongListener;
  }

  //Создание нового представления
  @Override public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
    return new ViewHolder(view);
  }

  public void onBindViewHolder(final ViewHolder holder, final int position) {
    Task taskElement = taskList.get(position);
    //Заполнение заданного представления данными
    holder.nameTask.setText(taskElement.getName());
    holder.descriptionTask.setText(taskElement.getDescription());

    holder.itemView.setOnClickListener(v -> {
      if (clickListener != null) {
        clickListener.onTaskClicked(taskElement.getName());
      }
    });

    holder.itemView.setOnLongClickListener(v -> {
      if (clickLongListener != null) {
        clickLongListener.onLongTaskClicked(taskElement.getName());
      }
      return true;
    });
  }

  //Возвращает количество вариантов в наборе данных
  //Длина массива cursor равна количеству элементов данных в RecyclerView.
  @Override public int getItemCount() {
    return taskList.size();
  }

  //Данные передаются адаптеру через конструктор.
  static class ViewHolder extends RecyclerView.ViewHolder {
    //Здесь ддолжна быть кастомная View
    private final TextView nameTask;
    private final TextView descriptionTask;

    ViewHolder(final View itemView) {
      super(itemView);
      nameTask = (TextView) itemView.findViewById(R.id.task_name);
      descriptionTask = (TextView) itemView.findViewById(R.id.task_description);
    }
  }
}
