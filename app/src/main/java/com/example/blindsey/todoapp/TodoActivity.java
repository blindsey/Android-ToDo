package com.example.blindsey.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TodoActivity extends ActionBarActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> aItems;
    private ListView lvItems;
    public static final String FILE_NAME = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.listView);
        readItems();
        aItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(aItems);
        setupListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addItem(View v) {
        EditText text = (EditText) findViewById(R.id.editText);
        aItems.add(text.getText().toString());
        text.setText("");
        saveItems();
    }

    private void setupListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
                items.remove(position);
                aItems.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });
    }

    private void readItems() {
        items = new ArrayList<String>();
        File todoFile = new File(getFilesDir(), FILE_NAME);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(todoFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                items.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveItems() {
        File todoFile = new File(getFilesDir(), FILE_NAME);
        try {
            FileWriter writer = new FileWriter(todoFile);
            for (int i = 0; i < items.size(); ++i) {
                writer.write(items.get(i));
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
