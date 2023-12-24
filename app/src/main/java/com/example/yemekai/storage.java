package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class storage extends AppCompatActivity {
    boolean selected;
    EditText et1,et2;
    TextView tw1;
    FloatingActionButton b1;
    public List<ItemL> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage);

        Spinner spinner=findViewById(R.id.spinner);
        et1=findViewById(R.id.editTextText2);
        tw1=findViewById(R.id.textViewv);
        et2=findViewById(R.id.editTextNumber2);
        b1=findViewById(R.id.floatingActionButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItem();
            }
        });
        items=new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String itemM= adapterView.getItemAtPosition(position).toString();
                selected=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            selected=false;
            }
        });
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add("Gram");
        arrayList.add("Kilogram");
        arrayList.add("Mililitre");
        arrayList.add("Litre");
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    public void createItem() {
        String itemName = et1.getText().toString();
        String value=et2.getText().toString();
        String measure = "";
        if (selected) {
            Spinner spinner = findViewById(R.id.spinner);
            measure = spinner.getSelectedItem().toString();
        }

        if (itemName.isEmpty() || !selected || value.isEmpty()) {
            Toast t = Toast.makeText(this, "Enter an item", Toast.LENGTH_SHORT);
            t.show();
            return;

        }

        ItemL newItem = new ItemL(itemName,value,measure);
        items.add(newItem);

        StringBuilder listStringBuilder = new StringBuilder();
        for (ItemL item : items) {
            listStringBuilder.append(item.toString()).append("\n\n");
        }
        tw1.setText(listStringBuilder.toString());

    }





}




