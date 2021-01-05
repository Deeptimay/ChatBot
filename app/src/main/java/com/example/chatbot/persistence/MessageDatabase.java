package com.example.chatbot.persistence;


import android.content.Context;

import com.example.chatbot.models.Message;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Message.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MessageDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "message_db";
    private static MessageDatabase instance;

    public static MessageDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MessageDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract MessageDao getMessageDao();
}






