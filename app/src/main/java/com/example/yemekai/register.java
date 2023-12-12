package com.example.yemekai;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText name,email,password,age;
    CheckBox rule1;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name=findViewById(R.id.editTextText);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        age=findViewById(R.id.editTextNumber);
        rule1=findViewById(R.id.checkBox);
        register=findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });

    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

void checkDataEntered(){
    if (isEmpty(name)) {
        name.setError("Name is required");
    }
    if (isEmail(email)==false) {
        email.setError("Enter a valid E-Mail");
    }
    if (isEmpty(password)) {
        password.setError("Password is required");
    }
    if (isEmpty(age)) {
        age.setError("Age is required");
    }
    if(!rule1.isChecked()){
        Toast t = Toast.makeText(this, "You must agree on the rule to register!", Toast.LENGTH_SHORT);
        t.show();
    }

}
}