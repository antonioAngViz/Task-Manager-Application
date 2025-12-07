package com.example.taskmanagerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapplication.controller.TaskController;
import com.example.taskmanagerapplication.model.Task;
import com.example.taskmanagerapplication.view.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        // contolador
        taskController = new TaskController(this);

        RecyclerView recyclerViewTasks = findViewById(R.id.rvTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));


        taskAdapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onEdit(Task task) {
                // mandar datos a otra pantalla
                editTask(task);
            }

            @Override
            public void onDelete(Task task) {
                // llama al metodo para confirmar que quiere eliminar esa tarea (porque no tenia la confirmacion y pues eliminaba sin preguntar)
                confirmationDelete(task);
            }
        });

        recyclerViewTasks.setAdapter(taskAdapter);

        // el noton que nos manda a la pantalla de agregar tarea
        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(view -> {
            showAddTaskActivity();
        });
    }

    // actualizar pantalla
    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        List<Task> tasks = taskController.getAllTasks();
        taskAdapter.setData(tasks);
    }

    private void showAddTaskActivity() {
        Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
        startActivity(intent);
    }

    // pantalla para la edicion
    private void editTask(Task task) {
        Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
        // los datos aparezcan en el formulario
        intent.putExtra("taskId", task.id);
        intent.putExtra("taskTitle", task.taskTitle);
        intent.putExtra("taskDesc", task.taskDescription);
        intent.putExtra("taskDate", task.createdAt);
        intent.putExtra("taskCompleted", task.isCompleted);
        startActivity(intent);
    }


    // metodo para confirmar la eliminacion de la tarea
    private void confirmationDelete(Task task) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.eliminar_tarea_titulo_caja)
                .setMessage(R.string.confirmar_eliminacion)
                .setPositiveButton(R.string.eliminar, (dialog, which) -> {
                    // aqui eliminamos la tarea ya que confirmo la eliminacion
                    taskController.deleteTask(task);
                    loadTasks(); // Refrescamos la lista
                    Toast.makeText(TaskActivity.this, R.string.tarea_eliminada, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancelar_eliminacion, null) // regresa y no elimina
                .show();
    }



}