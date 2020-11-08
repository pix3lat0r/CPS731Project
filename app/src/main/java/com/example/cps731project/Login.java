package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    private Button login;
    private EditText email;
    private EditText password;
    private TextView createAcc;
    private ProgressBar progress;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.btnLogin);
        progress = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logEmail = email.getText().toString();
                final String logPass = password.getText().toString();

                if (logEmail.equals("")) {
                    Toast.makeText(Login.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (logPass.equals("")) {
                    Toast.makeText(Login.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
                progress.setVisibility(View.VISIBLE);
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String a1 = logEmail.trim();
                                        String b1 = logPass.trim();
                                        if (email.getText().toString().equalsIgnoreCase(a1) & logPass.equalsIgnoreCase(b1)) {
                                            startActivity(new Intent(Login.this, MainActivity.class));
                                            Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Login.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }
}