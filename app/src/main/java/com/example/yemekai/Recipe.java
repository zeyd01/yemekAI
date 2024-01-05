package com.example.yemekai;

public class Recipe {
    private String recipeName;
    private String recipeSummary;

    public Recipe(String recipeName, String recipeSummary) {
        this.recipeName = recipeName;
        this.recipeSummary = recipeSummary;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeSummary() {
        return recipeSummary;
    }

    public void setRecipeSummary(String recipeSummary) {
        this.recipeSummary = recipeSummary;
    }
}