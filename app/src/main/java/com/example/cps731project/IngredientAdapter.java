package com.example.cps731project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class IngredientAdapter extends FirestoreRecyclerAdapter<IngredientsModel, IngredientAdapter.IngridentViewHolder> {

    public IngredientAdapter(@NonNull FirestoreRecyclerOptions<IngredientsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IngridentViewHolder holder, int position, @NonNull IngredientsModel model) {
        holder.name.setText(model.getName());
    }

    @NonNull
    @Override
    public IngridentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_view, parent, false);
       return new IngridentViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class IngridentViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public IngridentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);
        }
    }
}
