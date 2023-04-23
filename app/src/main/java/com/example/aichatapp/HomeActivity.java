package com.example.aichatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "LOGINACTIVITY";
    ProgressBar loading;
    TextView signin, textSign;
    EditText name, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        onEnter();
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.INVISIBLE);
        name = findViewById(R.id.inputName);
        password = findViewById(R.id.InputPassword);
        signin = findViewById(R.id.SignUpBtn);
        textSign = findViewById(R.id.notAccountText);
        mAuth = FirebaseAuth.getInstance();
        toSignUp();
    }

    public void onEnter(){

        Button enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString() , userPassword = password.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("email", username);
                signIn(username, userPassword);
            }
        });
    }

    private void signIn(String nomeUtente, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(nomeUtente, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(getApplicationContext(), "Authentication successful", Toast.LENGTH_SHORT).show();
        Button enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        signin.setVisibility(View.INVISIBLE);
        textSign.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        if(user != null){
            String email = user.getEmail();
            Intent toChat = new Intent(HomeActivity.this, MainActivity.class);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(toChat);
                }
            }, 4000);
        }
    }

    public void toSignUp(){
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(HomeActivity.this, SignupActivity.class);
                startActivity(loginIntent);
            }
        });
    }

}