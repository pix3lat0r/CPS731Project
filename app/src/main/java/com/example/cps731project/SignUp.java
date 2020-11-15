package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText mName, mEmail, mPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        TextView login = findViewById(R.id.haveAccount);
        login.setOnClickListener(this);

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        ProgressBar progress2 = findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.haveAccount) {
            startActivity(new Intent(this, Login.class));
        } else if (v.getId() == R.id.btnRegister) {
            registerUser();
        }
    }

    private void registerUser() {
        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(name.isEmpty()) {
            mName.setError("Full name is required!");
            mName.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            mEmail.setError("Email is required!");
            mEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            mPassword.setError("Password is required!");
            mPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            mPassword.setError("Password should be at least 6 characters!");
        }

    }
}
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