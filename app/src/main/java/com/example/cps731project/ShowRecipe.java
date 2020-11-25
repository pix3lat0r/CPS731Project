package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowRecipe extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference recipe = db.collection("recipes");
    private RecipeAdapter adapter;
    private RecyclerView mFirestoreList;
    //private TextView result;
    //private int count;
    final String id = UserID.user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

        Button home = findViewById(R.id.btnSRHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowRecipe.this, MainActivity.class));
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

        adapter = new RecipeAdapter(options);

        mFirestoreList = findViewById(R.id.recycler_view);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

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
}