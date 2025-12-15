package com.example.taskmanagerapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.controller.TaskController;
import com.example.taskmanagerapplication.model.History;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskController controller;
    private Spinner spinnerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerFilter = findViewById(R.id.spinnerFilter); // Buscamos el spinner

        controller = new TaskController(this);

        configurarFiltro(); //  spinner
    }

    private void configurarFiltro() {
        // filtro
        String[] opciones = {"Todos", "INSERT_TASK", "UPDATE_TASK", "DELETE_TASK"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapter);

        // hay cambios en el filtro?
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = opciones[position];
                cargarHistorial(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void cargarHistorial(String filtro) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<History> lista;

            //traer todos
            if (filtro.equals("Todos")) {
                lista = controller.getHistory();
            } else {
                lista = controller.getHistoryPorAccion(filtro);
            }

            runOnUiThread(() -> {
                HistoryAdapter adapter = new HistoryAdapter(lista);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}