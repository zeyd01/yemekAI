package com.example.yemekai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class login extends AppCompatActivity {


    EditText e1,e2;
    Button b1;
    private List<Account> accounts;

    private List<Account> getStoredAccounts() {
        register registerInstance = new register();
        return registerInstance.getStoredAccounts(this); // 'this' refers to the current activity context
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=findViewById(R.id.editTextTextEmailAddress3);
        e2=findViewById(R.id.editTextTextPassword3);
        b1=findViewById(R.id.button8);
        accounts = new ArrayList<>();
        accounts.add(new Account("admin","admin@gmail.com","admin"));
        accounts = getStoredAccounts();
        Log.d("LoginActivity", "Accounts retrieved: " + Arrays.toString(accounts.toArray()));

        b1.setOnClickListener(v -> {
            String enteredEmail = e1.getText().toString();
            String enteredPassword = e2.getText().toString();

            if (isValidUser(enteredEmail, enteredPassword)) {
                Toast.makeText(login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            }
            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();

            }

            else {
                if (isValidUser(enteredEmail, enteredPassword)) {}
                else Toast.makeText(login.this, "Invalid email or password. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidUser(String enteredEmail, String enteredPassword) {
        for (Account user : accounts) {
            if (user.getEmail().equals(enteredEmail) && user.getPassword().equals(enteredPassword)) {
                return true;
            }
        }
        return false;
    }
}
