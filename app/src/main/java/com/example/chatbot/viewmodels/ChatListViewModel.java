package com.example.chatbot.viewmodels;


import android.app.Application;

import com.example.chatbot.models.Message;
import com.example.chatbot.repository.ChatRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;

@ActivityRetainedScoped
@ActivityScoped
public class ChatListViewModel extends AndroidViewModel {

    ChatRepository chatRepository;
    private LiveData<List<Message>> messageListLiveData;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        chatRepository = ChatRepository.getInstance();
        messageListLiveData = chatRepository.getMessageListLiveData();
    }

    public LiveData<List<Message>> getMessages() {
        return messageListLiveData;
    }

    public void sendMessage(String message) {
        chatRepository.sendMessage(message);
    }
}





















