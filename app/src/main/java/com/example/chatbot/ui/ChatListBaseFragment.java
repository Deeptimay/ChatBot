package com.example.chatbot.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.scopes.FragmentScoped;

@FragmentScoped
public abstract class ChatListBaseFragment extends Fragment {

    public static String externalID = "";
    @Inject
    ChatRvAdapter chatRvAdapter;
    @Inject
    ChatListViewModel chatListViewModel;
    FragmentChatListBinding chatListBinding;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

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
        subscribeObservers();
        externalID = setExternalID();
    }

    private void initView(View view) {
        chatListBinding.btSend.setOnClickListener(v -> {
            if (!chatListBinding.etMessage.getText().toString().isEmpty()) {
                postMessage(chatListBinding.etMessage.getText().toString());
            }
        });

        chatListBinding.etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEND)) {
                    if (!chatListBinding.etMessage.getText().toString().isEmpty()) {
                        postMessage(chatListBinding.etMessage.getText().toString());
                    }
                }
                return false;
            }
        });

        chatListBinding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#52c7b8"), PorterDuff.Mode.SRC_ATOP);
                    chatListBinding.btSend.setColorFilter(porterDuffColorFilter);
                } else {
                    PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#cfcfcf"), PorterDuff.Mode.SRC_ATOP);
                    chatListBinding.btSend.setColorFilter(porterDuffColorFilter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initRecyclerView();
        subscribeObservers();
    }

    abstract void subscribeObservers();

    abstract String setExternalID();

    private void initRecyclerView() {
        List<Message> messageList = new ArrayList<>();
        chatRvAdapter = new ChatRvAdapter(messageList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatListBaseFragment.this.getContext());
        mLayoutManager.setReverseLayout(true);
        chatListBinding.rvChat.setLayoutManager(mLayoutManager);
        chatListBinding.rvChat.setItemAnimator(new DefaultItemAnimator());
        chatListBinding.rvChat.setAdapter(chatRvAdapter);
    }

    private void postMessage(String message) {
        Message messageObj = new Message(message);
        messageObj.setSynced(false);
        messageObj.setChatBotName("myself");
        messageObj.setExternalID(externalID);

        chatListViewModel.sendMessage(messageObj);
        chatListBinding.etMessage.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard(getActivity());
    }
}