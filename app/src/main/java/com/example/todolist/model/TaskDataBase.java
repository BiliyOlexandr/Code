package com.example.todolist.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 *
 * Created by alex on 11/9/17.
 */

@Database(entities = {TaskDB.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase{
    public abstract TaskDao taskDao();
}
