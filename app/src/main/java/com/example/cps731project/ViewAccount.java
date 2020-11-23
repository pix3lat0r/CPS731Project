package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(ViewAccount.this, Login.class));
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

                doc.delete();
                startActivity(new Intent(ViewAccount.this, Login.class));
            }
        });
    }
}