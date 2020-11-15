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
                                for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                    String eMail = documentSnapshot.getString("email");

                                    if (eMail.equals(email)) {
                                        Log.d(TAG, "User Exists");
                                        Toast.makeText(SignUp.this, "Email exists", Toast.LENGTH_SHORT).show();
                                        progress2.setVisibility(View.GONE);
                                    }
                                }
                            }
                            if(task.getResult().size() == 0 ){
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("email", email);
                                reg_entry.put("name", name);
                                reg_entry.put("password", password);

                                //   String myId = ref.getId();
                                db.collection("users")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                progress2.setVisibility(View.VISIBLE);
                                                Toast.makeText(SignUp.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
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
        /*fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            DocumentReference docRef = db.collection("users").document();
                            // Add document data using a hashmap
                            Map<String, Object> user = new HashMap<>();
                            user.put(NAME_KEY, name);
                            user.put(EMAIL_KEY, email);
                            user.put(PASSWORD_KEY, password);
                            //asynchronously write data
                            docRef.set(user);
                            Toast.makeText(SignUp.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progress2.setVisibility(View.GONE);
                    }
                });
        /*progress2.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email);
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progress2.setVisibility(View.GONE);
                    }
                });*/


    /*public static final String EMAIL_KEY = "email";
    public static final String NAME_KEY = "name";
    public static final String PASSWORD_KEY = "password";
    public static final String TAG = "TAG";
    private EditText name, email, password;
    private Button register;
    private FirebaseAuth fAuth;
    private ProgressBar progress2;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.btnRegister);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progress2 = findViewById(R.id.progressBar2);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String regName = name.getText().toString();
                final String regEmail = email.getText().toString().trim();
                final String regPassword = password.getText().toString().trim();


                if (regEmail.equals("")) {
                    email.setError("Email is required");
                }
                if (regPassword.equals("")) {
                    email.setError("Email is required");
                }
                if (password.length() < 6) {
                    password.setError("Password must be at least 6 Characters");
                    return;
                }

                progress2.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(regName, regEmail)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference doc = db.collection("users").document(userID);
                                    Map<String, Object> newUser = new HashMap<>();
                                    newUser.put(NAME_KEY, regName);
                                    newUser.put(EMAIL_KEY, regEmail);
                                    newUser.put(PASSWORD_KEY, regPassword);
                                    doc.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(SignUp.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progress2.setVisibility(View.GONE);
                                }

                            }

                        });
            }
        });
    }
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
        });*/