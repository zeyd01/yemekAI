package com.example.yemekai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private OnRecipeClickListener onRecipeClickListener;

    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener onRecipeClickListener) {
        this.recipeList = recipeList;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view, onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView recipeTextView;
        private OnRecipeClickListener onRecipeClickListener;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeClickListener onRecipeClickListener) {
            super(itemView);
            recipeTextView = itemView.findViewById(R.id.recipeTextView);
            this.onRecipeClickListener = onRecipeClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            recipeTextView.setText(recipe.getRecipeName() + ": " + recipe.getRecipeSummary());
        }

        @Override
        public void onClick(View v) {
            if (onRecipeClickListener != null) {
                onRecipeClickListener.onRecipeClick(getAdapterPosition(), recipeList.get(getAdapterPosition()).getRecipeName());
            }
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position, String recipeName);
    }

    public void updateData(List<Recipe> newRecipeList) {
        recipeList.clear();
        recipeList.addAll(newRecipeList);
        notifyDataSetChanged();
    }
}