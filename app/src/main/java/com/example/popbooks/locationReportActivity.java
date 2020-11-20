package com.example.popbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class locationReportActivity extends AppCompatActivity {
    private SimpleCursorAdapter cursorAdapter;
    DBOpenHelper helper = new DBOpenHelper(this);
    public ListView listView;
    long scoutId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_report);
        listView = findViewById(R.id.location_lv);
        Intent intent = getIntent();
        scoutId = intent.getLongExtra("scoutId", -1);



    }
    @Override
    protected void onResume() {
        helper.getReadableDatabase();
        displayListVIew();

        super.onResume();
    }
    public void displayListVIew(){
        Cursor cursor = helper.getallSales((int)scoutId, 0);
        String[] from = new String[]{
                DBOpenHelper.SALE_LOCATION,
                DBOpenHelper.SALE_DATE,
                DBOpenHelper.SALE_TOTAL
        };
        int[] to = new int[]{
                R.id.location_name_txt,
                R.id.location_date_txt,
                R.id.location_total_txt
        };
        cursorAdapter = new SimpleCursorAdapter(locationReportActivity.this,R.layout.location_lv_item, cursor, from, to, 0);
        listView.setAdapter(cursorAdapter);
    }
}