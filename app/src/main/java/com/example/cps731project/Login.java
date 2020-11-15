package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.widget.ProgressBar;


public class Login extends AppCompatActivity {

    private EditText logEmail;
    private EditText logPassword;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.progressBar);

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = logEmail.getText().toString().trim();
                String password = logPassword.getText().toString().trim();

                //Validating the input
                if(TextUtils.isEmpty(email)){
                    logEmail.setError("Please enter an email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    logPassword.setError(("Please enter a password"));
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //This is to authenticate the user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    //This is to check is the login (task) is successful or not
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });
        TextView doesntHaveAccount = findViewById(R.id.dontHaveAccount);
        doesntHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }

        });
    }

    /* I'm not sure if this part is necessary
     //but basically it's supposed to check if a user is already logged into the app

     @Override
     public void onStart() {
         super.onStart();
         // Check if user is signed in (non-null) and update UI accordingly.
         FirebaseUser currentUser = mAuth.getCurrentUser();
         updateUI(currentUser);
     }

     private void updateUI(FirebaseUser currentUser) {
         if(currentUser != null){
             Toast.makeText(Login.this, "Already logged in", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(Login.this, MainActivity.class));
         }
         else{
             Toast.makeText(Login.this, "Not logged in yet", Toast.LENGTH_SHORT).show();
         }
     }*/
}