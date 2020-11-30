package com.example.cps731project;

import android.annotation.SuppressLint;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowRecipe extends AppCompatActivity {
    public static final String RECIPE_NAME = "com.example.cps731project.RECIPE_NAME";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference recipe = db.collection("recipes");
    private RecipeAdapter adapterz;
    final String id = UserID.user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

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


        setUpRecyclerView();

    }

    private void setUpRecyclerView(){
/*result = findViewById(R.id.testResult);

        final List<String> userIngreds = new ArrayList<>();
        CollectionReference uRef = db.collection("users").document(id).collection("ingredients");
        uRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            IngredientsModel ingredient = documentSnapshot.toObject(IngredientsModel.class);
                            userIngreds.add(ingredient.getName());
                        }
                    }
                });

        CollectionReference rRef = db.collection("recipes");
        rRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            RecipeModel recipe = documentSnapshot.toObject(RecipeModel.class);
                            int listLength = recipe.getIngredients().size();
                            int count = 0;
                            for(String ingred : recipe.getIngredients()){
                                if(userIngreds.contains(ingred)){
                                 count++;
                                }
                            }

                            if(count == listLength){
                                Map<String, Object> qualifyingRecipes = new HashMap<>();
                                qualifyingRecipes.put("recipe", recipe);

                                db.collection("users").document(id).collection("q_recipes")
                                        .add(qualifyingRecipes)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("ShowRecipe", "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("ShowRecipe", "Error adding document", e);
                                            }
                                        });
                            }
                        }
                    }
                });
*/
        Query query = db.collection("recipes").whereArrayContains("ingredients", "egg");

        FirestoreRecyclerOptions<RecipeModel> options = new FirestoreRecyclerOptions.Builder<RecipeModel>()
                .setQuery(query, RecipeModel.class)
                .build();

        adapterz = new RecipeAdapter(options);

        RecyclerView mFirestoreList = findViewById(R.id.recycler_view);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapterz);

        adapterz.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
            RecipeModel recipe = documentSnapshot.toObject(RecipeModel.class);
            String name = recipe.getName();
            ArrayList<String> ingredients = recipe.getIngredients();
            ArrayList<String> instructions = recipe.getInstructions();
            Intent intent = new Intent(ShowRecipe.this, ViewRecipe.class);
            intent.putExtra("name", name);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);

            startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterz.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterz.stopListening();
    }
}