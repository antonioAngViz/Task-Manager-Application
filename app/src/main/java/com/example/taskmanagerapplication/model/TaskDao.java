package com.example.taskmanagerapplication.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {



    //insertar tareas
    @Insert
    void insertTask(Task task);


    // actualizar las tareas
    @Update
    void updateTask(Task task);


    //eliminar las tareas
    @Delete
    void deleteTask(Task task);


    //buscar todas las tareas
    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();


}
