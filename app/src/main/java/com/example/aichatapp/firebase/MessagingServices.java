package com.example.aichatapp.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aichatapp.MainActivity;
import com.example.aichatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingServices extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "TOKEN: " + token);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM", "MESSAGES: " + remoteMessage.getNotification().getBody());
    }
}
