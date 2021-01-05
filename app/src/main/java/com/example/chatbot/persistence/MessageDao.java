package com.example.chatbot.persistence;

import com.example.chatbot.models.Message;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message ORDER BY id DESC")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM message WHERE chatBotName LIKE :name LIMIT 1")
    Message findByName(String name);

    @Insert
    void insertAll(List<Message> messageList);

    @Query("SELECT  COUNT(DISTINCT id) FROM message")
    Integer getTotalNumberOfColumns();

    @Insert
    Long insert(Message message);

    @Update
    void update(Message message);

    @Delete
    void delete(Message message);
}
