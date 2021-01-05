package com.example.chatbot.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatbot.R;
import com.example.chatbot.adapters.ChatRvAdapter.MessageViewHolder;
import com.example.chatbot.models.Message;

public class IncomingMessageViewHolder extends MessageViewHolder {
    ImageView imageOther;
    TextView body;
    TextView name;

    public IncomingMessageViewHolder(View itemView) {
        super(itemView);
        imageOther = (ImageView) itemView.findViewById(R.id.ivProfileOther);
        body = (TextView) itemView.findViewById(R.id.tvBody);
        name = (TextView) itemView.findViewById(R.id.tvName);
    }

    @Override
    public void bindMessage(Message message) {
        body.setText(message.getMessage());
        name.setText(message.getChatBotName());
    }
}