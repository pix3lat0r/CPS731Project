package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "mealType";
    private Spinner mealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement

                if (id == R.id.mainActivity) {

                    Intent navHome = new Intent(MainActivity.this, MainActivity.class);
                    MainActivity.this.startActivity(navHome);
                    return true;
                }

                if (id == R.id.viewFavourites) {
                    Toast.makeText(MainActivity.this, "Favs has been clicked", Toast.LENGTH_LONG).show();
                    return true;
                }

                if (id == R.id.viewHistory) {
                    Toast.makeText(MainActivity.this, "History has been clicked", Toast.LENGTH_LONG).show();
                    return true;
                }

                if (id == R.id.viewAccount) {
                    Intent navLog = new Intent(MainActivity.this, ViewAccount.class);
                    MainActivity.this.startActivity(navLog);
                    return true;
                }
                return false;

            }
        });
        mealType = findViewById(R.id.mealType);

        final String id = UserID.user_id;

        Button goBtn = findViewById(R.id.btnGo);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenMeal = mealType.getSelectedItem().toString();

                Map<String, Object> meal = new HashMap<>();
                meal.put(KEY_TITLE, chosenMeal);
                db.collection("users").document(id).collection("ingredients")
                        .add(meal)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Toast.makeText(MainActivity.this, "Ingredient Added", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, IngredientList.class));
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding meal type", e);
                            }
                        });
            }
        });
    }

}