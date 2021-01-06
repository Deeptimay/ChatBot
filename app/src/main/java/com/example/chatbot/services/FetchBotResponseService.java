package com.example.chatbot.services;


import com.example.chatbot.models.ChatBotResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchBotResponseService {

    @GET("/api/chat/")
    Call<ChatBotResponse> getBotResponse(@Query("message") String message, @Query("externalID") String externalID);
}