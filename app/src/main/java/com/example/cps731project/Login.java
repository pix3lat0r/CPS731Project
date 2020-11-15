package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    private EditText logEmail;
    private EditText logPassword;

    private final FirebaseAuth db = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = findViewById(R.id.loginEmail);
        logPassword = findViewById(R.id.loginPassword);
        Button login = findViewById(R.id.btnLogin);
        //ProgressBar progress = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = logEmail.getText().toString().trim();
                final String userPassword = logPassword.getText().toString();

                if(userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(Login.this, "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else{


            }
        });
        TextView doesntHaveAccount = findViewById(R.id.dontHaveAccount);
        doesntHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
            }
        });
    }
}