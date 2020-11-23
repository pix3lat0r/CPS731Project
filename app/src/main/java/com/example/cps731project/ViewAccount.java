package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewAccount extends AppCompatActivity {

    FirebaseFirestore db;
    private Button logout;
    private Button deleteAcc;
    DocumentReference doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        final String id = UserID.user_id;
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
                /*CollectionReference usersRef = db.collection("users");
                Query query = usersRef.whereEqualTo("email", id);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            doc.delete();
                        }
                    }
                });*/
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

        Button home = findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewAccount.this, MainActivity.class));
            }
        });
    }
}
