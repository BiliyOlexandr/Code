package com.example.todolist.presentation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.util.Log;
import com.example.todolist.model.TaskDB;

import com.example.todolist.model.TaskDao;
import com.example.todolist.model.TaskDataBase;
import java.util.List;


//https://www.youtube.com/watch?v=vOJCrbr144o
//https://developer.android.com/topic/libraries/architecture/guide.html

/**
 * Created by alex on 11/9/17.
 */

public class TaskListViewModel extends AndroidViewModel{

    private TaskDataBase mDataBase;
    private MutableLiveData<List<TaskDB>> mTaskList;

    MutableLiveData<List<TaskDB>> getTasks() {
        if(mTaskList == null){
            mTaskList = new MutableLiveData<List<TaskDB>>();
            loadTasks();
        }
        return mTaskList;
    }


    private void loadTasks(){
        //It seems here i need call method that get tasks from data base
        mDataBase = new TaskDataBase() {
            @Override public TaskDao taskDao() {
                return null;
            }

            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @Override protected InvalidationTracker createInvalidationTracker() {
                return null;
            }
        };
        mTaskList = (MutableLiveData<List<TaskDB>>) mDataBase.taskDao().getAllTasks();


        // do async operation to fetch users
    }
    public TaskListViewModel(Application application) {
        super(application);
    }

}
