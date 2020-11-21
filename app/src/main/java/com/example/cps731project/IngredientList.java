package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class IngredientList extends AppCompatActivity {
    public String ingredient;

    private FirebaseFirestore db;
    private static final String TAG = "IngredientList";
    private static final String KEY_TITLE = "name";

    private RecyclerView mFirestoreList;
    FirestoreRecyclerAdapter adapter;
    private EditText ingred;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        db = FirebaseFirestore.getInstance();

        //Adding ingredients to Firestore
        final String id = UserID.user_id;
        ingred = findViewById(R.id.addIngredient);
        addBtn = findViewById(R.id.btnAdd);
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

        //Displaying ingredients from Firestore
        mFirestoreList = findViewById(R.id.recyclerView);

        //Query
        Query query = db.collection("users").document(id).collection("ingredients");

        //RecyclerOptions
        FirestoreRecyclerOptions<IngredientsModel> options = new FirestoreRecyclerOptions.Builder<IngredientsModel>()
                .setQuery(query, IngredientsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<IngredientsModel, IngredientViewHolder>(options) {

            @NonNull
            @Override
            public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_view, parent, false);
                return new IngredientViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull IngredientViewHolder holder, int position, @NonNull IngredientsModel model) {
                holder.name.setText(model.getName());
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ingredient_name);
        }
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

    public void getItem() {

    }

}
