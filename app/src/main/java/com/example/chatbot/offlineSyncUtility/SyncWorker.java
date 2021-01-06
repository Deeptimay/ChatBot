package com.example.chatbot.offlineSyncUtility;

import android.content.Context;

import com.example.chatbot.MyApplication;
import com.example.chatbot.models.ChatBotResponse;
import com.example.chatbot.models.Message;
import com.example.chatbot.persistence.MessageDao;
import com.example.chatbot.persistence.MessageDatabase;
import com.example.chatbot.services.RetrofitClient;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.hilt.work.WorkerInject;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Response;

public class SyncWorker extends Worker {
    private static final String TAG = "SyncWorker";

    @WorkerInject
    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        MessageDao messageDao = MessageDatabase.getInstance(MyApplication.getInstance()).getMessageDao();
        List<Message> messageList = messageDao.findUnSyncedMessages(false);

        for (Message message : messageList) {
            Call<ChatBotResponse> call = RetrofitClient.getInstance().getBotResponse(message.getMessage(), message.getExternalID());
            Response<ChatBotResponse> response = null;
            try {
                response = call.execute();
                if (response.code() == 200) {
                    Message messageResponse = response.body().getMessage();
                    messageResponse.setExternalID(message.getExternalID());
                    messageDao.insert(messageResponse);
                    message.setSynced(true);
                    messageDao.update(message);
                } else {
                    return Result.retry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Result.success();
    }

}