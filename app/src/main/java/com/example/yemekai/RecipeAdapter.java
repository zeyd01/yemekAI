package com.example.yemekai;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<String> recipeSuggestions;

    public RecipeAdapter(List<String> recipeSuggestions) {
        this.recipeSuggestions = recipeSuggestions;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String recipeContent = recipeSuggestions.get(position);
        holder.recipeTextView.setText(recipeContent);

        // Set an onClickListener for each recipe TextView
        holder.recipeTextView.setOnClickListener(view -> {
            // Implement your specific actions for the clicked recipe
            // For now, let's log the selected recipe content
            Log.d("RecipeClick", "Selected Recipe: " + recipeContent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeSuggestions.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTextView = itemView.findViewById(R.id.recipeTextView);
        }
    }
}
