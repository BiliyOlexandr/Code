package com.example.todolist.AndroidArchitectureCompinents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.todolist.model.TaskDB;

import java.util.List;

/**
 * Created by alex on 11/9/17.
 */

public class TaskListViewModel extends AndroidViewModel{

    private MutableLiveData<List<TaskDB>> tasks;

    public MutableLiveData<List<TaskDB>> getTasks() {
        if(tasks == null){
           tasks = new MutableLiveData<List<TaskDB>>();
            loadUsers();
        }
        return tasks;
    }


    private void loadUsers(){
        // do async operation to fetch users
    }
    public TaskListViewModel(Application application) {
        super(application);
    }

}
