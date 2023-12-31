package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button register,storage,searchResult,login,favoriteFood;
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEditText = findViewById(R.id.editTextText3);

        // Set the editor action listener for the EditText
        searchEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                // Perform the search and switch to the searchResult activity
                performSearch();
                return true; // Consume the event
            }
            return false; // Let the system handle other key events
        });

        register=findViewById(R.id.button2);
        register.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, register.class);
            startActivity(intent);
        });
        storage=findViewById(R.id.button3);
        storage.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, storage.class);
            startActivity(intent);
        });
        searchResult=findViewById(R.id.button4);
        searchResult.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, searchResult.class);
            startActivity(intent);
        });
        login=findViewById(R.id.button5);
        login.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        });
        favoriteFood=findViewById(R.id.button6);
        favoriteFood.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, favFood.class);
            startActivity(intent);
        });
    }
    private void performSearch() {
        // Get the search query from the EditText
        String searchQuery = searchEditText.getText().toString();

        // Start the searchResult activity and pass the search query as an extra
        Intent intent = new Intent(this, searchResult.class);
        intent.putExtra("SEARCH_QUERY", searchQuery);
        startActivity(intent);
    }
}