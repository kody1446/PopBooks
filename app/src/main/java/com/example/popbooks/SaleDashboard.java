package com.example.popbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class SaleDashboard extends AppCompatActivity implements AddRecruitDialog.AddRecruitDialogListener, AddDonationDialog.AddDonationDialogListener {
    private DBOpenHelper helper = new DBOpenHelper(this);
    public ListView listView;
    Cursor cursor;
    long scoutId;
    SearchView searchView;
    Sale sale = new Sale();
    String location;
    int type;
    long saleId;
    Button endSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sale Dashboard");
        searchView = findViewById(R.id.sale_lv_search);
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
        listView = findViewById(R.id.sale_inv_LV);
        endSale = findViewById(R.id.end_sale_btn);
        endSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        scoutId = intent.getLongExtra("scoutId", -1);
        location = intent.getStringExtra("location");
        type = intent.getIntExtra("type", 0);
        sale.setDate(LocalDate.now().toString());
        sale.setLocation(location);
        sale.setType(type);
        sale.setScoutId(scoutId);
        sale.setTotal(0);
        saleId= helper.createSale(sale);
        sale.setId(saleId);


    }

    @Override
    public void onResume() {
        helper.getReadableDatabase();
        cursor = helper.getAllPopInv((int) scoutId);

        displayListView();

        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saledashboardmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_recruit:
                AddRecruitDialog d = new AddRecruitDialog();
                d.show(getSupportFragmentManager(),"Add Recruit");
                return true;
            case R.id.add_donation:
                AddDonationDialog dd = new AddDonationDialog();
                dd.show(getSupportFragmentManager(), "Add Donation");
                return true;
            case R.id.end_sale:
                onBackPressed();
                return true;
            case R.id.advertise_location:
                Intent sendIntent = new Intent();
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                switch(type) {
                    case 0:
                        String advert = "Hello everyone, Cub scouts are currently selling at " +
                                location;
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, advert);
                        sendIntent.setType("text/plain");
                        startActivity(shareIntent);
                        return true;
                    case 1:
                        String wagon = "Hello everyone, Cub scouts are currently selling via wagon starting at " +
                                location;
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, wagon);
                        sendIntent.setType("text/plain");
                        startActivity(shareIntent);
                        return true;
                    case 2:
                        String online = "Hello everyone, Cub scouts are currently selling online.";
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, online);
                        sendIntent.setType("text/plain");
                        startActivity(shareIntent);
                        return true;


                }
            default:
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("End Sale?")
                .setMessage("Are you sure you want to exit? This will end the sale.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("no",null)
                .show();

    }

    public void displayFilteredListView(String input) {
        ArrayList<PopInv> popInvs = new ArrayList<>();
        Cursor filteredCursor = helper.getAllPopInv((int) scoutId);
        while (filteredCursor.moveToNext()) {
            String n = filteredCursor.getString(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_NAME));
            String lower = n.toLowerCase();
            if (lower.contains(input)) {
                PopInv popInv = new PopInv();
                popInv.setName(filteredCursor.getString(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_NAME)));
                popInv.setId(filteredCursor.getInt(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_ID)));
                popInv.setQty(filteredCursor.getInt(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_QTY)));
                popInv.setPrice(filteredCursor.getInt(filteredCursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE)) / 100);
                popInv.setSaleId(saleId);
                String name = popInv.getName();
                popInv.setImg(name);
                popInvs.add(popInv);
            }
            saleInvAdapter adapter = new saleInvAdapter(this,R.layout.sale_lv_item, popInvs);
            listView = findViewById(R.id.sale_inv_LV);
            listView.setAdapter(adapter);

        }


    }


    public void displayListView() {
        ArrayList<PopInv> popInvs = new ArrayList<>();
        while (cursor.moveToNext()) {
            PopInv popInv = new PopInv();
            popInv.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_NAME)));
            popInv.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_ID)));
            popInv.setQty(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY)));
            popInv.setPrice(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE)) / 100);
            popInv.setScoutId(scoutId);
            popInv.setSaleId(saleId);
            String name = popInv.getName();
            popInv.setImg(name);
            popInvs.add(popInv);
        }
        saleInvAdapter adapter = new saleInvAdapter(this,R.layout.sale_lv_item, popInvs);
        listView = findViewById(R.id.sale_inv_LV);
        listView.setAdapter(adapter);
    }

    @Override
    public void applyRecruit(String name, String phone) {
        helper.insertRecruit(name, phone, (int)scoutId);
        Toast.makeText(this, "Recruit Added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyDonation(int amount) {
        helper.insertDonationSale(amount,(int) saleId, (int)scoutId);
        int total = helper.getTotal((int)saleId);
        int newTotal = total + amount;
        helper.addToTotal((int)saleId, newTotal);
    }
}