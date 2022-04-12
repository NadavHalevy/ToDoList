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
    Button add, deletAllButton;
    ListView list, listDone;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayList<String> itemListDone = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter, arrayAdapterDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        deletAllButton = findViewById(R.id.button_delete_all);
        list = (ListView) findViewById(R.id.list);
        listDone = (ListView) findViewById(R.id.listDone);

        itemList = FileHelper.readData(this);
        itemListDone =  FileHelper.readData(this);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        arrayAdapterDone  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemListDone);

        list.setAdapter(arrayAdapter);
        listDone.setAdapter(arrayAdapterDone);

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

        deletAllButton.setOnClickListener(view -> {
            if ( arrayAdapterDone.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.list_empty),
                        Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle( getApplicationContext().getString(R.string.delete_all));
                alert.setMessage(getApplicationContext().getString(R.string.if_youre_done_all));
                alert.setCancelable(false);
                alert.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
                alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                    itemListDone.clear();
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.well_done_all_done),
                            Toast.LENGTH_LONG).show();
                    arrayAdapterDone.notifyDataSetChanged();
                    FileHelper.writeData(itemListDone, getApplicationContext());
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }


        });

        list.setOnItemClickListener((adapterView, view, position, id) -> {

            itemListDone.add(itemList.get(position));
            Log.d("message", itemList.get(position));
            arrayAdapter.notifyDataSetChanged();
            FileHelper.writeData(itemListDone, getApplicationContext());
            item.setText("");
            listDone.setAdapter(arrayAdapterDone);
            itemList.remove(position);


        });

        listDone.setOnItemClickListener((adapterView, view, position, id) -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle( getApplicationContext().getString(R.string.delete));
            alert.setMessage(getApplicationContext().getString(R.string.if_youre_done));
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {

                itemListDone.remove(position);
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.well_done),
                        Toast.LENGTH_LONG).show();
                arrayAdapterDone.notifyDataSetChanged();
                FileHelper.writeData(itemListDone, getApplicationContext());
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
    }

}