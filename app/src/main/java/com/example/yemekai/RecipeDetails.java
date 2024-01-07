package com.example.yemekai;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetails extends AppCompatActivity {
    private TextView recipeDetailsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeDetailsTextView = findViewById(R.id.textViewRecipeDetails);

        // Retrieve the selected recipe name from the intent
        String recipeName = getIntent().getStringExtra("recipeName");

        // Use chatApiDetails to make the API request for detailed recipe content
        chatapiDetails.OnApiResultListener listener = result -> {
            Log.d("RecipeDetails", "API Result: " + result);

            // Parse the detailed recipe content from the API response
            String detailedRecipe = parseApiResult(result);

            // Display the detailed recipe content
            recipeDetailsTextView.setText(detailedRecipe);
        };
        new chatapiDetails(recipeName, listener).execute();
    }

    private String parseApiResult(String jsonResult) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONObject choices = jsonObject.getJSONArray("choices").getJSONObject(0);
            JSONObject message = choices.getJSONObject("message");
            String detailedRecipe = message.getString("content");

            // Extract the recipe content by removing system messages and user prompt
            detailedRecipe = detailedRecipe.replaceFirst("^(.*\\n)*?1\\.", "1.");

            return detailedRecipe;
        } catch (JSONException e) {
            Log.e("RecipeDetails", "Error parsing JSON result: " + e.getMessage());
            return "";
        }
    }
}