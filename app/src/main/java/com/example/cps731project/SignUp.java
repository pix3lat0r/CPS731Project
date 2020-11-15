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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    public static final String EMAIL_KEY = "email";
    public static final String NAME_KEY = "name";
    public static final String PASSWORD_KEY = "password";

    private Button register;
    private EditText name;
    private EditText email;
    private EditText password;
    private ProgressBar progress2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref;// = db.collection("users").document();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ref = db.collection("users").document("email");

        progress2 = findViewById(R.id.progressBar2);
        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.name);
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);

                final String reg_name = name.getText().toString();
                final String reg_email = email.getText().toString();
                final String reg_password = password.getText().toString();

                if(reg_name.isEmpty() || reg_email.isEmpty() || reg_password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(reg_name.equals("")){
                    Toast.makeText(SignUp.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else if(reg_email.equals("")){
                    Toast.makeText(SignUp.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
                else if(reg_password.equals("")){
                    Toast.makeText(SignUp.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else {

                    //This part is supposed to check if account already exists but it's not working not sure why
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override

                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            progress2.setVisibility(View.VISIBLE);
                            if (documentSnapshot.exists()){
                                Toast.makeText(SignUp.this, "Sorry,this user already exists", Toast.LENGTH_SHORT).show();
                                progress2.setVisibility(View.INVISIBLE);
                            }else{
                                Map<String, Object> newuser = new HashMap<>();
                                newuser.put(NAME_KEY, reg_name);
                                newuser.put(EMAIL_KEY, reg_email);
                                newuser.put(PASSWORD_KEY, reg_password);

                                db.collection("users")
                                        .add(newuser)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUp.this, "Error creating account", Toast.LENGTH_LONG).show();
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    });

                }
            }
        });
        TextView hasAccount = findViewById(R.id.haveAccount);
        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

    }
}