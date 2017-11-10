package com.example.todolist.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
  @Insert
  public void insertTask (TaskDB task);
  @Update
  public void updateTask (TaskDB task);
  @Delete
  public void deleteTask (TaskDB task);
  @Query("SELECT * FROM task")
  public LiveData<List<TaskDB>> getAllTasks();
}
