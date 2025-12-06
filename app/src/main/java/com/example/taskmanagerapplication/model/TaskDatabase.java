package com.example.taskmanagerapplication.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class TaskDatabase extends  RoomDatabase{

    private static TaskDatabase INSTANCE;
    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TaskDatabase.class,
                    "task_database"
            ).allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

}




