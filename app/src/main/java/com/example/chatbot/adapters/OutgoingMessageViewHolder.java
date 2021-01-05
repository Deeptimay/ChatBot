package com.example.chatbot.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatbot.R;
import com.example.chatbot.adapters.ChatRvAdapter.MessageViewHolder;
import com.example.chatbot.models.Message;

public class OutgoingMessageViewHolder extends MessageViewHolder {
    ImageView imageMe;
    TextView body;

    public OutgoingMessageViewHolder(View itemView) {
        super(itemView);
        imageMe = (ImageView) itemView.findViewById(R.id.ivProfileMe);
        body = (TextView) itemView.findViewById(R.id.tvBody);
    }

    @Override
    public void bindMessage(Message message) {
        body.setText(message.getMessage());
    }
}
