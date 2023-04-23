package com.example.aichatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String[] messages = {"Pippo"};
    FragmentContainerView slideMenu;
    boolean slideMenuIsOpen;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(slideMenuIsOpen){
            slideMenu.setVisibility(View.INVISIBLE);
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        slideMenu = findViewById(R.id.slideMenu);
        slideMenu.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        MessageAdapter msg = new MessageAdapter(getApplicationContext(), messages);
        recyclerView.setAdapter(msg);

        onSlideMenu();
    }

    public void onSlideMenu(){
        ImageView slidemenubtn = findViewById(R.id.slideMenuBtn);
        slidemenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.setVisibility(View.VISIBLE);
                slideMenuIsOpen = true;
            }

        });
    }

}