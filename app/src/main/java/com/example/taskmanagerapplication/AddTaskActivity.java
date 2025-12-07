package com.example.taskmanagerapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanagerapplication.controller.TaskController;
import com.example.taskmanagerapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {


    private EditText etTaskTitle;
    private EditText etTaskDescription;
    private Button btnSave;
    private TaskController taskController;



    private int taskId = -1;
    private boolean isEditMode = false;
    private String originalDate;
    private boolean originalCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        taskController = new TaskController(this);

        // hay datos?
        if (getIntent().hasExtra("taskId")) {
            isEditMode = true;
            taskId = getIntent().getIntExtra("taskId", -1);

            // rrelenar los campos con lo que hay en la base de datos para su edicion
            etTaskTitle.setText(getIntent().getStringExtra("taskTitle"));
            etTaskDescription.setText(getIntent().getStringExtra("taskDesc"));


            originalDate = getIntent().getStringExtra("taskDate");
            originalCompleted = getIntent().getBooleanExtra("taskCompleted", false);

            // actualizamos los datos
            btnSave.setText(R.string.actualizar_tarea);
        }

        btnSave.setOnClickListener(view -> {
            String title = etTaskTitle.getText().toString();
            String description = etTaskDescription.getText().toString();

            if (title.isEmpty()) {
                etTaskTitle.setError(getString(R.string.titulo_obligatorio));
                return;
            }
            if(description.isEmpty()) {
                etTaskDescription.setError(getString(R.string.descipcion_obligatorio));
                return;
            }

            saveTask(title, description);
        });
    }

    private void saveTask(String title, String description) {
        if (isEditMode) {
            //editar datos
            Task taskToUpdate = new Task();
            taskToUpdate.id = taskId;
            taskToUpdate.taskTitle = title;
            taskToUpdate.taskDescription = description;
            taskToUpdate.createdAt = originalDate; // fecha original
            taskToUpdate.isCompleted = originalCompleted;

            taskController.updateTask(taskToUpdate);
            Toast.makeText(this, R.string.tarea_actualizada, Toast.LENGTH_SHORT).show();
        } else {
            // insertar nueva tarea
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            taskController.addTask(title, description, currentDate, false);
            Toast.makeText(this, getString(R.string.task_saved_success), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void initViews(){
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnSave = findViewById(R.id.btnSave);
    }
}