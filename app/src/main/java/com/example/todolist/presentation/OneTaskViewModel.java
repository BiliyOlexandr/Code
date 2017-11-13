package com.example.todolist.presentation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import com.example.todolist.model.TaskDB;


/**
 * Created by alex on 11/9/17.
 */

public class OneTaskViewModel extends AndroidViewModel {

  private TaskDB mTask;

  public void setTask(TaskDB task) {
    mTask = task;
    insertTask(mTask);
  }

  private void insertTask(TaskDB task){

  }

  public OneTaskViewModel( Application application) {
    super(application);
  }

}
