package com.example.chatbot.viewmodels;


import android.app.Application;

import com.example.chatbot.models.Message;
import com.example.chatbot.repository.ChatRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;

@ActivityRetainedScoped
@ActivityScoped
public class ChatListViewModel extends AndroidViewModel {

    ChatRepository chatRepository;
    private LiveData<List<Message>> messageListLiveDataDeeptimay;
    private LiveData<List<Message>> messageListLiveDataChris;
    private LiveData<List<Message>> messageListLiveDataJulie;
    private LiveData<List<Message>> messageListLiveDataDave;

    @ViewModelInject
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        chatRepository = ChatRepository.getInstance();
        messageListLiveDataDeeptimay = chatRepository.getMessageListLiveData("Deeptimay");
        messageListLiveDataChris = chatRepository.getMessageListLiveData("Chris");
        messageListLiveDataJulie = chatRepository.getMessageListLiveData("Julie");
        messageListLiveDataDave = chatRepository.getMessageListLiveData("Dave");
    }

    public LiveData<List<Message>> getMessagesForDeeptimay() {
        return messageListLiveDataDeeptimay;
    }

    public LiveData<List<Message>> getMessagesForChris() {
        return messageListLiveDataChris;
    }

    public LiveData<List<Message>> getMessagesForJulie() {
        return messageListLiveDataJulie;
    }

    public LiveData<List<Message>> getMessagesForDave() {
        return messageListLiveDataDave;
    }

    public void sendMessage(Message message) {
        chatRepository.sendMessage(message);
    }
}





















