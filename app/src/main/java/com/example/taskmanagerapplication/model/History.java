package com.example.taskmanagerapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id")
    public int historyId;

    @ColumnInfo(name = "action")
    public String action; // ej: valores como "insert_task", "update_note", "delete_task"

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "details")
    public String details; // Ej:  título de la tarea o  nota

    // Constructor vacío requerido por Room
    public History() {}

    // Constructor para facilitar uso
    public History(String action, String createdAt, String details) {
        this.action = action;
        this.createdAt = createdAt;
        this.details = details;
    }
}