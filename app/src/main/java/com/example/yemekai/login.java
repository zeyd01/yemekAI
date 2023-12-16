package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {

    TextView tw1;
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=findViewById(R.id.editTextTextEmailAddress3);
        e2=findViewById(R.id.editTextTextPassword3);
        b1=findViewById(R.id.button8);
        tw1=findViewById(R.id.textView2);
    }
}