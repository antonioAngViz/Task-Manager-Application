package com.example.taskmanagerapplication.controller;

import android.content.Context;
import android.util.Log;

import com.example.taskmanagerapplication.model.Task;
import com.example.taskmanagerapplication.model.TaskDao;
import com.example.taskmanagerapplication.model.TaskDatabase;

import java.util.List;

public class TaskController {

    private final TaskDao taskDao;

    public TaskController(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        taskDao = database.taskDao();
    }

    //Create a task
    public boolean addTask(String taskTitle, String taskDescription, String createdAt,Boolean isCompleted){
        try{
            Task task = new Task();
            task.taskTitle = taskTitle;
            task.taskDescription = taskDescription;
            task.createdAt = createdAt;
            task.isCompleted = isCompleted;

            taskDao.insertTask(task);

            Log.i("TASK_SAVE","La tarea se almacenó correctamente");
            return true;

        }catch (Exception e){
            Log.e("TASK_ERROR",e.getMessage());
            return false;
        }


    }

    //Get all tasks
    public List<Task> getAllTasks(){
        return taskDao.getAllTasks();
    }

    //Update task
    public void updateTask(Task task){
        taskDao.updateTask(task);
    }


    //Delete task
    public void deleteTask(Task task){
        taskDao.deleteTask(task);
    }




}
