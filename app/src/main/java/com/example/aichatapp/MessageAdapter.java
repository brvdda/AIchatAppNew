package com.example.aichatapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    TextView message;
    LayoutInflater inflater;
    private List myList;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Creare la vista per ogni elemento
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usermessage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LauncherActivity.ListItem list = (LauncherActivity.ListItem) myList.get(position);
        message.setText(message.getText().toString());
    }



    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.botMessage);
        }
    }
    public MessageAdapter(Context applicationContext, String[] botMessage){
        this.message = message;
    }





}
