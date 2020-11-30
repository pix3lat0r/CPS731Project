package com.example.cps731project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewRecipe extends AppCompatActivity {
    private FirebaseFirestore db;
    private static final String KEY_TITLE = "name";
    TextView title, ingredients, instructions;
    Button fav;
    final String id = UserID.user_id;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
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
                        startActivity(new Intent(getApplicationContext(), ViewAccount.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        title = findViewById(R.id.txtRecipeTitle);
        ingredients = findViewById(R.id.txtRecipeIngreds);
        instructions = findViewById(R.id.txtRecipeInstructions);

        final String name = getIntent().getStringExtra("name");
        ArrayList<String> ingreds = getIntent().getStringArrayListExtra("ingredients");
        ArrayList<String> instrucs = getIntent().getStringArrayListExtra("instructions");

        title.setText(name);
        for(String x : ingreds){
            ingredients.setText(ingredients.getText() + "\n" + count + ". " + x);
            count ++;
        }

        for(String y : instrucs) {
            instructions.setText(instructions.getText() + "\n" + y);
        }

        db = FirebaseFirestore.getInstance();

        fav = findViewById(R.id.btnFavs);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> favourites = new HashMap<>();
                favourites.put(KEY_TITLE, name);

                db.collection("users").document(id).collection("favourites")
                        .add(favourites)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ViewRecipe.this, "Added to Favourites", Toast.LENGTH_LONG).show();
                                Log.d("ViewRecipe", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("ViewRecipe", "Error adding document", e);
                            }
                        });
            }
        });

    }
}
