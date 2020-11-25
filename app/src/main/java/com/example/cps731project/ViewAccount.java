package com.example.cps731project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAccount extends AppCompatActivity {

    FirebaseFirestore db;
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

        final String id = UserID.user_id;
        db = FirebaseFirestore.getInstance();
        doc = db.collection("users").document(id);


        /*final TextView userNameTxt = (TextView) findViewById(R.id.txtName);
        final TextView userEmailTxt = (TextView) findViewById(R.id.txtEmail);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userName = documentSnapshot.getString("name");
                String userEmail = documentSnapshot.getString("email");
                userNameTxt.setText(userName);
                userEmailTxt.setText(userEmail);
            }
        });*/

        //LOGOUT BUTTON
        Button logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewAccount.this, Login.class));
            }
        });

        //DELETE ACCOUNT BUTTON
        Button deleteAcc = findViewById(R.id.btnDeleteAcc);
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

                doc.delete();
                startActivity(new Intent(ViewAccount.this, Login.class));
            }
        });
    }
}