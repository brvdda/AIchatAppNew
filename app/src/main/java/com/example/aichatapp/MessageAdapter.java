package com.example.aichatapp;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatViewHolder> {

    private Activity mActivity;
    private DatabaseReference dbReference;
    private String displayName;
    private ArrayList<DataSnapshot> dataSnapshots;
    private FirebaseAuth mAuth;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            dataSnapshots.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public MessageAdapter(Activity activity, DatabaseReference ref, String name){
        mActivity = activity;
        dbReference = ref;
        dbReference = ref.child("messaggi");
        displayName = name;
        dataSnapshots = new ArrayList<>();
        dbReference.addChildEventListener(mListener);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView Auth;

        FrameLayout.LayoutParams layoutParams;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.userMessage);
            Auth = itemView.findViewById(R.id.nameText);
            mAuth = FirebaseAuth.getInstance();
            layoutParams = (FrameLayout.LayoutParams) Auth.getLayoutParams();
        }
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.usermessage_item, parent, false);
        ChatViewHolder ch = new ChatViewHolder(v);

        return ch;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        DataSnapshot snapshot = dataSnapshots.get(position);
        Message msg = snapshot.getValue(Message.class);
        holder.message.setText(msg.getMessage());
        holder.Auth.setText(msg.getAuth());

        boolean sonoIo = msg.getAuth().equals(displayName);
        setChatItemStyle(sonoIo, holder);
    }

    @Override
    public int getItemCount() {
        return dataSnapshots.size();
    }

    public void onClean(){
        dbReference.removeEventListener(mListener);
    }

    private void setChatItemStyle(boolean sonoIo, ChatViewHolder holder){
        if(sonoIo){
            holder.layoutParams.gravity = Gravity.END;
            holder.Auth.setTextColor(Color.WHITE);
            holder.message.setBackgroundResource(R.drawable.in_msg_bg);
            holder.Auth.setText("Tu");
        } else {
            holder.layoutParams.gravity = Gravity.START;
            holder.Auth.setTextColor(Color.WHITE);
            holder.message.setBackgroundResource(R.drawable.out_msg_bg);
        }

        holder.Auth.setLayoutParams(holder.layoutParams);
        holder.message.setLayoutParams(holder.layoutParams);
    }


}
