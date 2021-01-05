package com.example.chatbot.viewmodels;


import android.app.Application;

import com.example.chatbot.models.Message;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;

@ActivityRetainedScoped
@ActivityScoped
public class ChatListViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Message>> messageList = new MutableLiveData<>();

    public ChatListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Message>> getMessages() {
        return messageList;
    }
}





















