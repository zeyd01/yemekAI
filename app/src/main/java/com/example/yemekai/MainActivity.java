package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button register,storage,searchResult,login,favoriteFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}