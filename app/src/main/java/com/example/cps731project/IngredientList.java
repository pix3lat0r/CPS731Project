package com.example.cps731project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientList extends AppCompatActivity {
    public String ingredient;

    private FirebaseFirestore db;
    private static final String TAG = "IngredientList";
    private static final String KEY_TITLE = "name";

    private IngredientAdapter adapter;
    private EditText ingred;
    //private Button search;
    //DocumentReference doc;
    final String id = UserID.user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

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

        db = FirebaseFirestore.getInstance();
        CollectionReference recipe = db.collection("recipes");

        //Adding ingredients to Firestore
        ingred = findViewById(R.id.addIngredient);
        Button addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredient = ingred.getText().toString();

                Map<String, Object> ingredients = new HashMap<>();
                ingredients.put(KEY_TITLE, ingredient);

                db.collection("users").document(id).collection("ingredients")
                        .add(ingredients)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(IngredientList.this, "Ingredient Added", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                ingred.getText().clear();
            }
        });

        //calling recycler view method
        setUpRecyclerView();


        //search button
       /* search = findViewById(R.id.btnsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                db.collection("users").document(id).collection("ingredients").get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            }
                                        });
                            }
                        });
            }
        });*/

    }


    private void setUpRecyclerView(){

        Query query = db.collection("users").document(id).collection("ingredients");

        FirestoreRecyclerOptions<IngredientsModel> options = new FirestoreRecyclerOptions.Builder<IngredientsModel>()
                .setQuery(query, IngredientsModel.class)
                .build();

        adapter = new IngredientAdapter(options);

        RecyclerView mFirestoreList = findViewById(R.id.recyclerView);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mFirestoreList);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /*public void getItem() {

    }*/

}
