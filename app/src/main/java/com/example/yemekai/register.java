package com.example.yemekai;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

    EditText name1,email1,password1,age;
    CheckBox rule1;
    Button register;
    private List<Account> accounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name1=findViewById(R.id.editTextText);
        email1=findViewById(R.id.editTextTextEmailAddress);
        password1=findViewById(R.id.editTextTextPassword);
        age=findViewById(R.id.editTextNumber);
        rule1=findViewById(R.id.checkBox);
        register=findViewById(R.id.button);
        accounts = new ArrayList<>();
        accounts.add(new Account("admin","admin@gmail.com","admin"));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createButton();
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

public boolean checkDataEntered(){

   if (isEmpty(name1)) {
        name1.setError("Name is required");
        return false;
    }
    if (isEmail(email1)==false) {
        email1.setError("Enter a valid E-Mail");
        return false;
    }
    if (isEmpty(password1)) {
        password1.setError("Password is required");
        return false;
    }
    if (isEmpty(age)) {
        age.setError("Age is required");
        return false;
    }
    if(!rule1.isChecked()){
        Toast t = Toast.makeText(this, "You must agree on the rule to register!", Toast.LENGTH_SHORT);
        t.show();
        return false;
    }
    else return true;
}
void createButton(){
        checkDataEntered();
        if(checkDataEntered()==true){
            createAccount();
            Intent intent = new Intent(register.this, MainActivity.class);
            startActivity(intent);
            Toast t = Toast.makeText(this, "You successfully created an account", Toast.LENGTH_SHORT);
            t.show();
        }

    }


    public void createAccount() {
        String name = name1.getText().toString();
        String password = password1.getText().toString();
        String email = email1.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {

            return;
        }
        Account account = new Account(name, email, password);
        accounts.add(account);

    }


}