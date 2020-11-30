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
//import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "TAG";
    //public static final String EMAIL_KEY = "email";
    //public static final String NAME_KEY = "name";
    //public static final String PASSWORD_KEY = "password";
    private EditText mName, mEmail, mPassword;
    //private FirebaseAuth fAuth;
    private FirebaseFirestore db;
    private ProgressBar progress2;
    DocumentReference doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView login = findViewById(R.id.haveAccount);
        Button register = findViewById(R.id.btnRegister);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progress2 = findViewById(R.id.progressBar2);

        db = FirebaseFirestore.getInstance();
        doc = db.collection("users").document();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mName.getText().toString();
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (name.isEmpty()) {
                    mName.setError("Full name is required!");
                    mName.requestFocus();
                } else if (email.isEmpty()) {
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                } else if (password.isEmpty()) {
                    mPassword.setError("Password is required!");
                    mPassword.requestFocus();
                } else if (password.length() < 6) {
                    mPassword.setError("Password should be at least 6 characters!");
                    mPassword.requestFocus();
                } else {
                    CollectionReference usersRef = db.collection("users");
                    Query query = usersRef.whereEqualTo("email", email);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            progress2.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    String eMail = documentSnapshot.getString("email");

                                    if (eMail.equals(email)) {
                                        Log.d(TAG, "User Exists");
                                        Toast.makeText(SignUp.this, "Email exists", Toast.LENGTH_SHORT).show();
                                        progress2.setVisibility(View.GONE);
                                    }
                                }
                            }
                            if (task.getResult().size() == 0) {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("email", email);
                                reg_entry.put("name", name);
                                reg_entry.put("password", password);

                                //   String myId = ref.getId();
                                //set the document id as the email
                                db.collection("users").document(email)
                                        .set(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progress2.setVisibility(View.VISIBLE);
                                                Toast.makeText(SignUp.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                //saving the email as the userid
                                                UserID test = new UserID();
                                                test.setUserID(email);
                                                test.setName(name);
                                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                                        /*.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                progress2.setVisibility(View.VISIBLE);
                                                Toast.makeText(SignUp.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                            }
                                        })*/
                                progress2.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}