package com.example.taskmanagerapplication.model;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(History history);

    @Query("SELECT * FROM history ORDER BY history_id DESC")
    List<History> getAllHistory();

    // filtro  por tipo de acción
    @Query("SELECT * FROM history WHERE action = :actionType")
    List<History> getHistoryByAction(String actionType);
}