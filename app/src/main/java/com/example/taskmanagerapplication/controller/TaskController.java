package com.example.taskmanagerapplication.controller;

import android.content.Context;
import android.util.Log;

import com.example.taskmanagerapplication.model.History;
import com.example.taskmanagerapplication.model.HistoryDao;
import com.example.taskmanagerapplication.model.Task;
import com.example.taskmanagerapplication.model.TaskDao;
import com.example.taskmanagerapplication.model.TaskDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskController {

    private final TaskDao taskDao;
    private final HistoryDao historyDao;

    public TaskController(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        taskDao = database.taskDao();
        historyDao = database.historyDao();
    }

    // fecha acomodada
    private String getFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Create a task
    public boolean addTask(String taskTitle, String taskDescription, String createdAt, Boolean isCompleted){
        try{
            Task task = new Task();
            task.taskTitle = taskTitle;
            task.taskDescription = taskDescription;
            task.createdAt = createdAt;
            task.isCompleted = isCompleted;

            taskDao.insertTask(task);

            // fecha del sistema
            registrarHistorial("INSERT_TASK", "Se creó la tarea: " + taskTitle);

            return true;
        }catch (Exception e){
            Log.e("TASK_ERROR",e.getMessage());
            return false;
        }
    }

    // Get all tasks
    public List<Task> getAllTasks(){
        return taskDao.getAllTasks();
    }

    // Update task
    public void updateTask(Task task){
        taskDao.updateTask(task);
        registrarHistorial("UPDATE_TASK", "Se actualizó la tarea: " + task.taskTitle);
    }

    // Delete task
    public void deleteTask(Task task){
        taskDao.deleteTask(task);
        registrarHistorial("DELETE_TASK", "Se eliminó la tarea: " + task.taskTitle);
    }



    private void registrarHistorial(String accion, String detalle) {
        History log = new History();
        log.action = accion;
        log.createdAt = getFechaActual();
        log.details = detalle;
        historyDao.insertHistory(log);
    }

    public List<History> getHistory() {
        return historyDao.getAllHistory();
    }

    // filtar
    public List<History> getHistoryPorAccion(String accion) {
        return historyDao.getHistoryByAction(accion);
    }
}