package com.example.chatbot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatbot.R;
import com.example.chatbot.models.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MessageViewHolder> {

    private static final int MESSAGE_OUTGOING = 123;
    private static final int MESSAGE_INCOMING = 321;
    private List<Message> mMessages;

    @Inject
    public ChatRvAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public int getItemCount() {
        return (mMessages == null) ? 0 : mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).getChatBotName().equalsIgnoreCase("myself")) {
            return MESSAGE_OUTGOING;
        } else {
            return MESSAGE_INCOMING;
        }
    }

    @NotNull
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == MESSAGE_INCOMING) {
            View contactView = inflater.inflate(R.layout.message_incoming, parent, false);
            return new IncomingMessageViewHolder(contactView);
        } else if (viewType == MESSAGE_OUTGOING) {
            View contactView = inflater.inflate(R.layout.message_outgoing, parent, false);
            return new OutgoingMessageViewHolder(contactView);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bindMessage(message);
    }

    public void resetData() {
        mMessages.clear();
        notifyDataSetChanged();
    }

    public void swapData(List<Message> items) {
        if (items != null) {
            mMessages.clear();
            mMessages.addAll(items);
        } else {
            mMessages = null;
        }
        notifyDataSetChanged();
    }

    public abstract static class MessageViewHolder extends RecyclerView.ViewHolder {

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bindMessage(Message message);
    }
}