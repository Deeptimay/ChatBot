package com.example.chatbot.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatbot.R;
import com.example.chatbot.adapters.ChatRvAdapter;
import com.example.chatbot.databinding.FragmentChatListBinding;
import com.example.chatbot.models.Message;
import com.example.chatbot.viewmodels.ChatListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.scopes.FragmentScoped;

@FragmentScoped
public class ChatListFragment extends Fragment {

    @Inject
    ChatRvAdapter chatRvAdapter;
    @Inject
    ChatListViewModel chatListViewModel;
    FragmentChatListBinding chatListBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        chatListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_list, container, false);
        return chatListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatListViewModel = ViewModelProviders.of(this).get(ChatListViewModel.class);
        initView(view);
    }

    private void initView(View view) {
        chatListBinding.btSend.setOnClickListener(v -> {
            if (!chatListBinding.etMessage.getText().toString().isEmpty())
                chatListViewModel.sendMessage(chatListBinding.etMessage.getText().toString());
        });

        chatListBinding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        chatListViewModel.getMessages().observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                chatRvAdapter.swapData(messages);
                chatListBinding.rvChat.scrollToPosition(0);
            }
        });
    }

    private void initRecyclerView() {
        List<Message> messageList = new ArrayList<>();
        chatRvAdapter = new ChatRvAdapter(messageList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatListFragment.this.getContext());
        mLayoutManager.setReverseLayout(true);
        chatListBinding.rvChat.setLayoutManager(mLayoutManager);
        chatListBinding.rvChat.setItemAnimator(new DefaultItemAnimator());
        chatListBinding.rvChat.setAdapter(chatRvAdapter);
    }
}