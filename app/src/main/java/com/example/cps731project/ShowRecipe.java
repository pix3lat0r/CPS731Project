package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ShowRecipe extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipe = db.collection("recipes");
    private RecipeAdapter adapter;
    private int count;
    final String id = UserID.user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);


        setUpRecyclerView();

    }

    private void setUpRecyclerView(){
        /*CollectionReference doc = db.collection("users").document(id).collection("ingredients");
        doc.get();

        List<String> userIngreds;
        Query query = db.collection("recipes").whereArrayContains("ingredients", );*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}