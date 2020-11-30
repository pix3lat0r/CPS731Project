package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ViewFavourites extends AppCompatActivity {
    private FirebaseFirestore db;
    final String id = UserID.user_id;
    private RecipeAdapter adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favourites);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.viewFavourites);
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
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.viewFavourites:
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

        db = FirebaseFirestore.getInstance();

        setUpRecyclerView();

    }

    private void setUpRecyclerView(){
        Query query = db.collection("users").document(id).collection("favourites");

        FirestoreRecyclerOptions<RecipeModel> options = new FirestoreRecyclerOptions.Builder<RecipeModel>()
                .setQuery(query, RecipeModel.class)
                .build();

        adapters = new RecipeAdapter(options);

        RecyclerView mFirestoreList = findViewById(R.id.fav_recyclerView);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapters);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapters.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mFirestoreList);

        /*adapters.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                RecipeModel recipe = documentSnapshot.toObject(RecipeModel.class);
                String name = recipe.getName();
                ArrayList<String> ingredients = recipe.getIngredients();
                ArrayList<String> instructions = recipe.getInstructions();
                Intent intent = new Intent(ViewFavourites.this, ViewRecipe.class);
                intent.putExtra("name", name);
                intent.putExtra("ingredients", ingredients);
                intent.putExtra("instructions", instructions);

                startActivity(intent);
                //Toast.makeText(ViewFavourites.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapters.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapters.stopListening();
    }
}