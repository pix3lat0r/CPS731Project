package com.example.cps731project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

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

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);
        }
    }
}
