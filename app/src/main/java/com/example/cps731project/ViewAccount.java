package com.example.cps731project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAccount extends AppCompatActivity {

    FirebaseFirestore db;
    private TextView email, name;
    private Button logout;
    private Button deleteAcc;
    DocumentReference doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.viewAccount);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mainActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.grocery:
                        startActivity(new Intent(getApplicationContext(), TestActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.viewFavourites:
                        startActivity(new Intent(getApplicationContext(), ViewFavourites.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.viewHistory:
                        startActivity(new Intent(getApplicationContext(), ViewHistory.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.viewAccount:
                        return true;
                }
                return false;
            }
        });

        email = findViewById(R.id.txtEmail);
        name = findViewById(R.id.txtName);

        final String id = UserID.user_id;
        final String username = UserID.name;

        name.setText(username);
        email.setText("Email: " + id);

        db = FirebaseFirestore.getInstance();
        doc = db.collection("users").document(id);




        //LOGOUT BUTTON
        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(ViewAccount.this);
                myAlertBuilder.setTitle("Logout");
                myAlertBuilder.setMessage("Are you sure you want to log out?");
                myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewAccount.this, "Logged out successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ViewAccount.this, Login.class));
                        //startActivity(new Intent(ViewAccount.this, MainActivity.class));
                    }
                });

                myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewAccount.this, "You are still logged in!", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.show();
            }
        });

        //DELETE ACCOUNT BUTTON
        deleteAcc = findViewById(R.id.btnDeleteAcc);
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewAccount.this);
                alertBuilder.setTitle("Delete Account");
                alertBuilder.setMessage("Are you sure you want to delete your account? \n\nThis action CANNOT be undone");
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doc.delete();
                        Toast.makeText(ViewAccount.this, "Your account has been deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ViewAccount.this, SignUp.class));
                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViewAccount.this, "Your account has NOT been deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                alertBuilder.show();
            }
        });
    }
}