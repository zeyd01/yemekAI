package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class searchResult extends AppCompatActivity {
    private TextView recipeTextView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        View otherLayout = getLayoutInflater().inflate(R.layout.activity_main, null); // Burası mainpage olucak
        editText = otherLayout.findViewById(R.id.editTextText3);

        recipeTextView = findViewById(R.id.recipeTextView);

        storage storage = new storage();

        chatapi.OnApiResultListener listener = result -> {
            // Handle the result, e.g., update UI with recommended recipes
            Log.d("ChatGPTApiTask", "API Result: " + result);

            // Display the recipes in the TextView
            displayRecipes(result);
        };

        // Pass the List<ItemL> and EditText content to the task
        chatapi apiTask = new chatapi(storage.items, editText.getText().toString(), listener);
        apiTask.execute();
    }

    private void displayRecipes(String result) {
        // Add a null check for result
        if (result == null) {
            Log.e("DisplayRecipes", "Result is null");
            return;
        }

        List<String> recipeNames = Arrays.asList(result.split(", "));

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        for (String recipeName : recipeNames) {
            if (recipeName != null && !recipeName.isEmpty()) {
                // Create clickable links for each recipe
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        // Retrieve the stored recipe name directly
                        String selectedRecipeName = recipeName;
                        // TODO: Launch the full recipe details activity or fragment with the selected recipe
                        // For now, let's log the selected recipe name
                        Log.d("RecipeClick", "Selected Recipe: " + selectedRecipeName);
                    }
                };

                spannableStringBuilder.append(recipeName);
                int start = spannableStringBuilder.length() - recipeName.length();
                int end = spannableStringBuilder.length();
                spannableStringBuilder.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append("\n");
            }
        }

        // Set the Spannable text to the TextView
        recipeTextView.setText(spannableStringBuilder);
        recipeTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
