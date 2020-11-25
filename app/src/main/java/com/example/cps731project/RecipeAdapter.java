package com.example.cps731project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends FirestoreRecyclerAdapter<RecipeModel, RecipeAdapter.RecipeViewHolder> {

    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<RecipeModel> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position, @NonNull RecipeModel model) {
        holder.name.setText(model.getName());
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view, parent, false);
        return new RecipeViewHolder(view);
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        //ConstraintLayout mainLayout;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);
           // mainLayout = findViewById(R.id.recipe_view);
        }
    }
}
