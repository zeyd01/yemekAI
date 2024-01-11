package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class searchResult extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
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
        recyclerView.setAdapter(adapter);

        storage storage = new storage();
        chatapi.OnApiResultListener listener = result -> {
            Log.d("ChatGPTApiTask", "API Result: " + result);

            displayRecipes(result);
        }; //buraya bak 2 kere çalışma sebebi olabilir
        new chatapi(storage.items, editText.getText().toString(), listener).execute();

    }
    private List<Recipe> parseApiResult(String jsonResult) {
        List<Recipe> recipeSuggestions = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray choices = jsonObject.getJSONArray("choices");

            for (int i = 0; i < choices.length(); i++) {
                JSONObject choice = choices.getJSONObject(i);
                JSONObject message = choice.getJSONObject("message");

                String recipeContent = message.getString("content");

                // Split the content based on newline character
                String[] sections = recipeContent.split("\n");

                for (String section : sections) {
                    if (section.matches("^\\d+\\..*")) {
                        // Find the index of the first non-whitespace character
                        int startIndex = 0;
                        while (startIndex < section.length() && Character.isWhitespace(section.charAt(startIndex))) {
                            startIndex++;
                        }

                        // Extract the recipe name
                        String recipeName = section.substring(startIndex);

                        Recipe recipe = new Recipe(recipeName, "");
                        recipeSuggestions.add(recipe);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("chatapi", "Error parsing JSON result: " + e.getMessage());
        }

        return recipeSuggestions;
    }

    private void displayRecipes(String jsonResult) {
        recipeList = parseApiResult(jsonResult);

        if (recipeList.isEmpty()) {
            Log.e("DisplayRecipes", "Recipe list is empty");
            return;
        }

        // Update the existing adapter with new data
        if (adapter == null) {
            adapter = new RecipeAdapter(recipeList, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(recipeList);
        }

        // Use RecyclerView and RecipeAdapter
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeClick(int position, String recipeName) {
        Recipe selectedRecipe = recipeList.get(position);
        Log.d("RecipeClick", "Selected Recipe: " + selectedRecipe.getRecipeName() + ", ID: " + position);


        chatapiDetails.OnApiResultListener detailsListener = detailsResult -> {

            Log.d("RecipeDetailsApiTask", "API Result: " + detailsResult);


        };

        new chatapiDetails(selectedRecipe.getRecipeName(), detailsListener).execute();
        Intent intent = new Intent(searchResult.this, RecipeDetails.class);
        startActivity(intent);
    }
}



