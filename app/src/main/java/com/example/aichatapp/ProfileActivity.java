package com.example.aichatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView username;
    TextView email;
    Switch showDataSwitch;
    String extras;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.emailtext);
        Bundle b = getIntent().getExtras();
        String emailTxt = b.getString("email");
        email.setText(emailTxt);

    }

}