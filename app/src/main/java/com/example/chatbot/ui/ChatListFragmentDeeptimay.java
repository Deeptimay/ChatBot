package com.example.chatbot.ui;

import com.example.chatbot.models.Message;

import java.util.List;

import androidx.lifecycle.Observer;
import dagger.hilt.android.scopes.FragmentScoped;

@FragmentScoped
public class ChatListFragmentDeeptimay extends ChatListBaseFragment {

    @Override
    void subscribeObservers() {
        chatListViewModel.getMessagesForDeeptimay().observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                chatRvAdapter.swapData(messages);
                chatListBinding.rvChat.scrollToPosition(0);
            }
        });
    }

    @Override
    String setExternalID() {
        return "Deeptimay";
    }
}