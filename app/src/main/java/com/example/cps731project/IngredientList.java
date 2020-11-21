package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class IngredientList extends AppCompatActivity {
    public String ingredient;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "IngredientList";
    private static final String KEY_TITLE = "name";

    private EditText ingred;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

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

    }
    public void getItem() {

    }
}
