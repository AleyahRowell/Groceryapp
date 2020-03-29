package com.example.grocerylistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    ListView list_view;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_view = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
        list_view.setAdapter(arrayAdapter);

        //
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.item_update:
                                //item to update
                                break;

                            case R.id.item_del:
                                Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                        }
                        return false;
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:

                _addItem();
                break;
        }

        return true;
    }
    private void _addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add New Item");
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.items_dialog, null,false);
        builder.setView(v.findViewById(R.id.etItem));
        builder.setView(R.layout.items_dialog);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!etItem.getText().toString().isEmpty())
                {
                    list.add(etItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                }else {
                    etItem.setError("Add Item Here!");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();
    }
}
