package com.example.taskmanagerapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private final List<Task> taskList = new ArrayList<>();

    // interfaz cominicar los cliks
    private final OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onEdit(Task task);
        void onDelete(Task task);
    }


    public TaskAdapter(OnTaskActionListener listener) {
        this.listener = listener;
    }

    public void setData(List<Task> tasks){
        taskList.clear();
        if(tasks != null){
            taskList.addAll(tasks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // que se muestren los textos en la card
        holder.txtTaskTitle.setText(task.taskTitle);
        holder.txtTaskDescription.setText(task.taskDescription);
        holder.txtCreatedAt.setText(task.createdAt);

        // boton de editar
        holder.btnEdit.setOnClickListener(view -> {
            listener.onEdit(task);
        });

        // boton de eliminar
        holder.btnDelete.setOnClickListener(view -> {
            listener.onDelete(task);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView txtTaskTitle, txtTaskDescription, txtCreatedAt;
        ImageButton btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            txtTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            txtCreatedAt = itemView.findViewById(R.id.tvCreatedAt);


            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}