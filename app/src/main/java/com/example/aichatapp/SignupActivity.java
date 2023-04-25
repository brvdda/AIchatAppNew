package com.example.aichatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    TextView login;
    EditText nameInput;
    TextView successfulText, signUpText, signUptextBtn;
    EditText passwordInput, confirmPasswordInput, emailInput;
    Button signBtn;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this, "Utente gia loggato", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        login = findViewById(R.id.SignUpBtn);
        initUI();

        mAuth = FirebaseAuth.getInstance();
        toLogin();
        onEnterBtn();
    }

    private void initUI(){

        nameInput = findViewById(R.id.inputName);
        successfulText = findViewById(R.id.successfulText);
        successfulText.setVisibility(View.INVISIBLE);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.InputPassword);
        confirmPasswordInput = findViewById(R.id.confermaPassword);
        signBtn = findViewById(R.id.SignUpButton);
        signUpText = findViewById(R.id.notAccountText);
        signUptextBtn = findViewById(R.id.SignUpBtn);
    }

    private void createAccount(String email, String password, String name) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("accountCreate: ", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setNome(name);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("accountCreate: ", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void setNome(String nome){
        FirebaseUser username = mAuth.getCurrentUser();
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
        username.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("setNome","nome caricato con successo");
                }else if(!task.isSuccessful()){
                    Log.i("setNome","nome non caricato con successo");
                }
            }
        });
    }
    public void toLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public void onEnterBtn(){
        String name = nameInput.getText().toString(),
                password = passwordInput.getText().toString(),
                email = emailInput.getText().toString();
        Button enterBtn = findViewById(R.id.SignUpButton);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameIsValid(nameInput.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Username non valido", Toast.LENGTH_SHORT).show();
                }
                else if(!emailIsValid(emailInput.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Email non valida", Toast.LENGTH_SHORT).show();
                }
                else if(!confermaPassword(passwordInput.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password non valida", Toast.LENGTH_SHORT).show();
                }
                else {
                    createAccount(emailInput.getText().toString(), passwordInput.getText().toString(), nameInput.getText().toString());
                    Toast.makeText(getApplicationContext(), "Accesso riuscito!", Toast.LENGTH_SHORT).show();
                    login.setVisibility(View.INVISIBLE);
                    nameInput.setVisibility(View.INVISIBLE);
                    passwordInput.setVisibility(View.INVISIBLE);
                    confirmPasswordInput.setVisibility(View.INVISIBLE);
                    emailInput.setVisibility(View.INVISIBLE);
                    successfulText.setVisibility(View.VISIBLE);
                    signBtn.setVisibility(View.INVISIBLE);
                    signUpText.setVisibility(View.INVISIBLE);
                    signUptextBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private boolean nameIsValid(String name){
        name = nameInput.getText().toString();
        if(name.length() > 4)
            return true;
        else
            return false;
    }

    private boolean emailIsValid(String user_email){
        user_email = emailInput.getText().toString();
        return user_email.contains("@");
    }

    private boolean confermaPassword(String user_pass){
        user_pass = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        return confirmPassword.equals(user_pass) && user_pass.length() > 4;
    }
}