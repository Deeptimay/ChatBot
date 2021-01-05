package com.example.chatbot.repository;

import com.example.chatbot.MyApplication;
import com.example.chatbot.models.ChatBotResponse;
import com.example.chatbot.models.Message;
import com.example.chatbot.persistence.MessageDao;
import com.example.chatbot.persistence.MessageDatabase;
import com.example.chatbot.services.RetrofitClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {

    private static final String TAG = "ChatRepository";
    private static ChatRepository instance;
    private LiveData<List<Message>> messageListLiveData;
    private MessageDao messageDao;

    private ChatRepository() {
        messageDao = MessageDatabase.getInstance(MyApplication.getInstance()).getMessageDao();
        messageListLiveData = messageDao.getAll();
    }

    public static ChatRepository getInstance() {
        if (instance == null) {
            instance = new ChatRepository();
        }
        return instance;
    }

    public LiveData<List<Message>> getMessageListLiveData() {
//        return Transformations.distinctUntilChanged(messageListLiveData);
        return messageListLiveData;
    }


    public void sendMessage(String message) {
        Observable.just("io")
                .subscribeOn(Schedulers.io())
                .subscribe(i -> syncAndSaveMessage(message));
    }

    public void syncAndSaveMessage(String message) {
        Message messageObj = new Message(message);
        messageObj.setSynced(false);
        messageObj.setChatBotName("Deeptimay");
        Long id = messageDao.insert(messageObj);
        messageObj.setId(id);

        Call<ChatBotResponse> call = RetrofitClient.getInstance().getBotResponse(message);
        call.enqueue(new Callback<ChatBotResponse>() {
            @Override
            public void onResponse(Call<ChatBotResponse> call, Response<ChatBotResponse> response) {

                Observable.just("io")
                        .subscribeOn(Schedulers.io())
                        .subscribe(i -> {
                            messageDao.insert(response.body().getMessage());
                            messageObj.setSynced(true);
                            messageDao.update(messageObj);
                        });

            }

            @Override
            public void onFailure(Call<ChatBotResponse> call, Throwable t) {

            }
        });
    }
}
