package com.example.aichatapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FragmentContainerView slideMenu;
    boolean slideMenuIsOpen;
    String name = "luca";
    EditText mMessaggio;
    FirebaseDatabase mDatabase;
    FloatingActionButton sendBtn;
    DatabaseReference dbReference;
    private FirebaseAuth mAuth;

    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(slideMenuIsOpen){
            slideMenu.setVisibility(View.INVISIBLE);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageAdapter.onClean();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        setTitle(mAuth.getCurrentUser().getDisplayName());
        dbReference = FirebaseDatabase.getInstance().getReference();
        slideMenu = findViewById(R.id.slideMenu);
        slideMenu.setVisibility(View.INVISIBLE);
        initUI();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        messageAdapter = new MessageAdapter(this, dbReference, mAuth.getCurrentUser().getDisplayName());
        recyclerView.setAdapter(messageAdapter);
        onSlideMenu();

    }

    private void initUI(){
        mDatabase = FirebaseDatabase.getInstance();
        mMessaggio = findViewById(R.id.inputText);
        sendBtn = findViewById(R.id.sendBtn);
        recyclerView = findViewById(R.id.recycleView);


        mMessaggio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void inviaMessaggio(){
        dbReference = mDatabase.getReference("msg");
        String messaggio = mMessaggio.getText().toString();
        dbReference.child("Utenti").child("Nome").setValue(name);
        dbReference.setValue(messaggio);
        // Read from the database
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("msg", "Value is: " + value);
                mMessaggio.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("msg", "Failed to read value.", error.toException());
            }
        });

    }

    public void sendMessage(){
        String inputMes = mMessaggio.getText().toString();
        if(!inputMes.equals("")){
            Message msg = new Message(inputMes, mAuth.getCurrentUser().getDisplayName());
            dbReference.child("messaggi").push().setValue(msg);
            mMessaggio.setText("");
        }
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