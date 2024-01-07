package com.example.yemekai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


public class storage extends AppCompatActivity {
    boolean selected;
    EditText et1,et2;
    LinearLayout containerLayout;
    FloatingActionButton b1;
    public List<ItemL> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage);

        Spinner spinner=findViewById(R.id.spinner);
        et1=findViewById(R.id.editTextText2);

        et2=findViewById(R.id.editTextNumber2);
        b1=findViewById(R.id.floatingActionButton);
        containerLayout = findViewById(R.id.itemLayout);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItem();
            }
        });
        items=new ArrayList<>();
        loadDataAndUpdateList(); // Veriyi yükle
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
        saveData(itemName, value, measure);
        ItemL newItem = new ItemL(itemName,value,measure);
        items.add(newItem);

        StringBuilder listStringBuilder = new StringBuilder();
        for (ItemL item : items) {
            listStringBuilder.append(item.toString()).append("\n\n");
        }


        updateList();
    }
    private void saveData(String itemName, String value, String measure) {
        // Her bir öğe için ayrı bir SharedPreferences öğesi kullanarak veriyi kaydetme
        SharedPreferences sharedPreferences = getSharedPreferences("ItemPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Her bir öğe için farklı anahtarlar kullan
        int itemCount = sharedPreferences.getInt("itemCount", 0) + 1;
        editor.putInt("itemCount", itemCount); // Kaçıncı öğe olduğunu tut
        String itemNameKey = "itemName_" + itemCount;
        String valueKey = "value_" + itemCount;
        String measureKey = "measure_" + itemCount;

        editor.putString(itemNameKey, itemName);
        editor.putString(valueKey, value);
        editor.putString(measureKey, measure);

        editor.apply();
    }

    private void loadDataAndUpdateList() {
        // Tüm öğeleri tutmak için bir liste oluştur
        List<ItemL> loadedItems = new ArrayList<>();

        // Her bir öğenin SharedPreferences öğesini al
        SharedPreferences sharedPreferences = getSharedPreferences("ItemPreferences", Context.MODE_PRIVATE);
        int itemCount = sharedPreferences.getInt("itemCount", 0);

        // Her bir öğe için veriyi al ve listeye ekle
        for (int i = 1; i <= itemCount; i++) {
            String itemNameKey = "itemName_" + i;
            String valueKey = "value_" + i;
            String measureKey = "measure_" + i;

            String itemName = sharedPreferences.getString(itemNameKey, "");
            String value = sharedPreferences.getString(valueKey, "");
            String measure = sharedPreferences.getString(measureKey, "");

            // Verileri kullanarak bir ItemL nesnesi oluştur ve listeye ekle
            ItemL loadedItem = new ItemL(itemName, value, measure);
            loadedItems.add(loadedItem);
        }

        // Listeyi güncelle
        items.clear();
        items.addAll(loadedItems);
        updateList();
    }

    private void updateList() {
        // Listeyi güncelleme işlemleri burada yapılabilir
        // Örneğin, bir RecyclerView kullanıyorsanız, adapter'a güncellenmiş listeyi bildirebilirsiniz
        // Veya TextView veya diğer bir görünüm üzerinde gösterim yapabilirsiniz

        containerLayout.removeAllViews(); // Eski öğeleri temizle

        for (int i = 0; i < items.size(); i++) {
            ItemL item = items.get(i);

            // Her bir öğeyi temsil eden düzeni oluştur
            View itemView = getLayoutInflater().inflate(R.layout.item_layout, null);

            // Düzen içindeki metin görünümünü ve butonu bul
            TextView itemTextView = itemView.findViewById(R.id.itemTextView);
            ImageButton deleteButton = itemView.findViewById(R.id.deleteButton);

            // Metin görünümüne öğe bilgilerini yerleştir
            itemTextView.setText(item.toString());

            // Butonun etiketini ayarla (hangi sıradaki öğeyi sileceğini bilelim)
            deleteButton.setTag(i);

            // Butona tıklanma olayını ayarla
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteButtonClick(view);
                }
            });

            // Ana düzene yeni öğeyi ekle
            containerLayout.addView(itemView);
        }
    }

    public void onDeleteButtonClick(View view) {
        int position = (int) view.getTag();

        // İlgili pozisyondaki öğeyi kaldır
        if (position >= 0 && position < items.size()) {
            // SharedPreferences'ten tüm öğeleri sil
            SharedPreferences sharedPreferences = getSharedPreferences("ItemPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();  // Tüm öğeleri temizle
            editor.apply();

            // items listesinden öğeyi kaldır
            items.remove(position);

            // Listeyi güncelle
            updateList();
        }
    }
}
