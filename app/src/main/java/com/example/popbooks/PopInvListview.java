package com.example.popbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class PopInvListview extends AppCompatActivity implements AddPopDialog.AddPopDialogListener {
    private DBOpenHelper helper = new DBOpenHelper(this);
    public ListView listView;
    Cursor cursor;
    long scoutId;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_inv_listview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inventory");
        searchView = findViewById(R.id.pop_lv_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String lower = newText.toLowerCase();
                displayFilteredListView(lower);
                return false;
            }
        });
        listView = findViewById(R.id.pop_inv_LV);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long posid) {
                Intent intent = new Intent(PopInvListview.this, PopInvDetail.class);
                long id = view.getId();
                intent.putExtra("popInvId", id);
                intent.putExtra("scoutId", scoutId);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        scoutId = intent.getLongExtra("scoutId", -1);
    }

    @Override
    public void onResume() {
        helper.getReadableDatabase();
       cursor= helper.getAllPopInv((int)scoutId);

       displayListView();

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_new_prod_menuitem:
                openDialog();
                return true;
            case R.id.go_home_menuitem:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventorymenu, menu);
        return true;
    }

    public void openDialog(){
        AddPopDialog addPopDialog = new AddPopDialog();
        addPopDialog.show(getSupportFragmentManager(), "Add Pop Dialog");
    }
    public void displayFilteredListView(String input){
        ArrayList<PopInv> popInvs = new ArrayList<>();
        Cursor filteredCursor = helper.getAllPopInv((int)scoutId);
        while(filteredCursor.moveToNext()) {
            String n = filteredCursor.getString(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_NAME));
            String lower = n.toLowerCase();
            if(lower.contains(input)){
                PopInv popInv = new PopInv();
                popInv.setName(filteredCursor.getString(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_NAME)));
                popInv.setId(filteredCursor.getInt(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_ID)));
                popInv.setQty(filteredCursor.getInt(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_QTY)));
                String name = popInv.getName();
                popInv.setImg(name);
                popInvs.add(popInv);
            }
            popInvAdapter adapter = new popInvAdapter(this,R.layout.popinv_lv_item, popInvs);
            listView.setAdapter(adapter);

        }


    }
    public void displayListView(){
        ArrayList<PopInv> popInvs = new ArrayList<>();
        while(cursor.moveToNext()){
            PopInv popInv = new PopInv();
            popInv.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_NAME)));
            popInv.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_ID)));
            popInv.setQty(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY)));
            popInv.setScoutId(scoutId);
            String name = popInv.getName();
            popInv.setImg(name);
            popInvs.add(popInv);
        }
        popInvAdapter adapter = new popInvAdapter(this,R.layout.popinv_lv_item, popInvs);
        listView = findViewById(R.id.pop_inv_LV);
        listView.setAdapter(adapter);
    }
    public void applyTexts(String name, String desc, double price, int qty){
        PopInv popInv = new PopInv();
        popInv.setName(name);
        popInv.setDesc(desc);
        int p = (int) price;
        popInv.setPrice(p);
        popInv.setQty(qty);
        popInv.setScoutId(scoutId);
        helper.insertPopInv((int)popInv.getScoutId(), popInv.getName(),popInv.getDesc(),(int)popInv.getPrice(),popInv.getQty());
        onResume();
    }
}