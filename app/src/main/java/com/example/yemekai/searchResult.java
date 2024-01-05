package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchResult extends AppCompatActivity {
    private List<Recipe> recipeList;
    private EditText editText;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        View otherLayout = getLayoutInflater().inflate(R.layout.activity_main, null); // Burası mainpage olucak
        editText = otherLayout.findViewById(R.id.editTextText3);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);

        storage storage = new storage();
      //  recipeList = new ArrayList<>();
        chatapi.OnApiResultListener listener = result -> {
            // Handle the result, e.g., update UI with recommended recipes
            Log.d("ChatGPTApiTask", "API Result: " + result);

            // Display the recipes in the TextView
            displayRecipes(result);
        };

        // Pass the List<ItemL> and EditText content to the task
        new chatapi(storage.items, editText.getText().toString(), listener).execute();

    }
    private List<String> parseApiResult(String jsonResult) {
        List<String> recipeSuggestions = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray choices = jsonObject.getJSONArray("choices");

            for (int i = 0; i < choices.length(); i++) {
                JSONObject choice = choices.getJSONObject(i);
                JSONObject message = choice.getJSONObject("message");

                String recipeContent = message.getString("content");
                recipeSuggestions.add(recipeContent);
            }
        } catch (JSONException e) {
            Log.e("chatapi", "Error parsing JSON result: " + e.getMessage());
        }

        return recipeSuggestions;
    }
    private void displayRecipes(String jsonResult) {
        List<String> recipeSuggestions = parseApiResult(jsonResult);

        if (recipeSuggestions.isEmpty()) {
            Log.e("DisplayRecipes", "Recipe suggestions list is empty");
            return;
        }

        // Assuming recipeContainer is the parent layout in your search_result.xml
        LinearLayout recipeContainer = findViewById(R.id.recipeContainer);
        recipeContainer.removeAllViews(); // Clear existing views

        for (String recipeContent : recipeSuggestions) {
            // Extract the number at the start of the recipe to use as an identifier
            String recipeNumberString = recipeContent.replaceAll("\\D+", "");

            try {
                // Create an inflated recipe item layout
                View recipeItemView = getLayoutInflater().inflate(R.layout.recipe_item, null);

                // Set a unique ID for the recipe item view using the recipe number
                if (!recipeNumberString.isEmpty()) {
                    long recipeNumber = Long.parseLong(recipeNumberString);
                    recipeItemView.setId((int) recipeNumber);
                } else {
                    // Handle the case where the recipe number couldn't be extracted or is empty
                    Log.e("RecipeParsing", "Error parsing recipe number for: " + recipeContent);
                    continue; // Skip this iteration if parsing fails
                }

                // Find the TextView within the inflated layout
                TextView recipeTextView = recipeItemView.findViewById(R.id.recipeTextView);
                recipeTextView.setText(recipeContent);

                // Set an onClickListener for each recipe TextView
                recipeTextView.setOnClickListener(view -> {
                    // Retrieve the unique ID assigned to the clicked recipe item
                    int clickedRecipeId = view.getId();

                    // Implement your specific actions for the clicked recipe
                    // For now, let's log the selected recipe content and ID
                    Log.d("RecipeClick", "Selected Recipe: " + recipeContent + ", ID: " + clickedRecipeId);
                });

                // Add the inflated layout to the recipeContainer
                recipeContainer.addView(recipeItemView);

            } catch (NumberFormatException e) {
                // Handle the case where the recipe number is not a valid long
                Log.e("RecipeParsing", "NumberFormatException: " + e.getMessage());
            }
        }
    }

}


