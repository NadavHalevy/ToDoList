package com.example.nadavhalevy.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        list = (ListView) findViewById(R.id.list);

        itemList = FileHelper.readData(this);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);

        list.setAdapter(arrayAdapter);

        add.setOnClickListener(view -> {

            String itemName = item.getText().toString();
            if(item == null){
                Toast.makeText(getApplicationContext(), "An empty task cannot be added", Toast.LENGTH_LONG).show();
            }else{
                itemList.add(itemName);
                Log.d("message", itemName);
                arrayAdapter.notifyDataSetChanged();
                FileHelper.writeData(itemList, getApplicationContext());
                item.setText("");
                list.setAdapter(arrayAdapter);
            }
                Log.d("arraylist", arrayAdapter.getItem(arrayAdapter.getCount() - 1));
        });

        list.setOnItemClickListener((adapterView, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle( getApplicationContext().getString(R.string.delete));
            alert.setMessage(getApplicationContext().getString(R.string.if_youre_done));
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {

                itemList.remove(position);
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.well_done),
                                Toast.LENGTH_LONG).show();
                arrayAdapter.notifyDataSetChanged();
                FileHelper.writeData(itemList, getApplicationContext());
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
    }

}