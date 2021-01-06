package com.example.chatbot.repository;

import com.example.chatbot.MyApplication;
import com.example.chatbot.models.ChatBotResponse;
import com.example.chatbot.models.Message;
import com.example.chatbot.persistence.MessageDao;
import com.example.chatbot.persistence.MessageDatabase;
import com.example.chatbot.services.RetrofitClient;

import java.util.List;

import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ChatRepository {

    private static final String TAG = "ChatRepository";
    private static ChatRepository instance;
    private MessageDao messageDao;

    private LiveData<List<Message>> messageListLiveDataDeeptimay;
    private LiveData<List<Message>> messageListLiveDataChris;
    private LiveData<List<Message>> messageListLiveDataJulie;
    private LiveData<List<Message>> messageListLiveDataDave;

    private ChatRepository() {
        messageDao = MessageDatabase.getInstance(MyApplication.getInstance()).getMessageDao();
        messageListLiveDataDeeptimay = messageDao.getAll("Deeptimay");
        messageListLiveDataChris = messageDao.getAll("Chris");
        messageListLiveDataJulie = messageDao.getAll("Julie");
        messageListLiveDataDave = messageDao.getAll("Dave");
    }

    @Singleton
    public static ChatRepository getInstance() {
        if (instance == null) {
            instance = new ChatRepository();
        }
        return instance;
    }

    public LiveData<List<Message>> getMessageListLiveData(String externalID) {
        switch (externalID) {
            case "Deeptimay":
                return messageListLiveDataDeeptimay;
            case "Chris":
                return messageListLiveDataChris;
            case "Julie":
                return messageListLiveDataJulie;
            case "Dave":
                return messageListLiveDataDave;
            default:
                return null;
        }
    }


    public void sendMessage(Message message) {
        Observable.just("io")
                .subscribeOn(Schedulers.io())
                .subscribe(i -> syncAndSaveMessage(message));
    }

    public void syncAndSaveMessage(Message message) {
        Long id = messageDao.insert(message);
        message.setId(id);

        Call<ChatBotResponse> call = RetrofitClient.getInstance().getBotResponse(message.getMessage(), message.getExternalID());
        call.enqueue(new Callback<ChatBotResponse>() {
            @Override
            public void onResponse(Call<ChatBotResponse> call, Response<ChatBotResponse> response) {

                Observable.just("io")
                        .subscribeOn(Schedulers.io())
                        .subscribe(i -> {
                            try {
                                Message messageResponse = response.body().getMessage();
                                messageResponse.setExternalID(message.getExternalID());

                                messageDao.insert(messageResponse);
                                message.setSynced(true);
                                messageDao.update(message);
                            } catch (Exception e) {
                            }
                        });

            }

            @Override
            public void onFailure(Call<ChatBotResponse> call, Throwable t) {

            }
        });
    }
}
