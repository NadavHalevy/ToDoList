package com.example.nadavhalevy.todolist;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView list;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        list = findViewById(R.id.list);

        itemList = FileHelper.readData(this);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);

        list.setAdapter(arrayAdapter);

        add.setOnClickListener(view -> {

            String itemName = item.getText().toString();
            itemList.add(itemName);
            Log.d("message", itemName);
            arrayAdapter.notifyDataSetChanged();
            FileHelper.writeData(itemList, getApplicationContext());
            item.setText("");
            list.setAdapter(arrayAdapter);
            for (int i = 0; i < arrayAdapter.getCount() ; i++) {
                Log.d("arraylist", arrayAdapter.getItem(i));
            }


        });
    }

}