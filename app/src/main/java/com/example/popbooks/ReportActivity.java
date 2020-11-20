package com.example.popbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity implements InventoryReportDialog.InventoryReportDialogListener{

    public Button  invReportBtn;
    public Button  totSalesBtn;
    public Button locationSumBtn;
    public Button invSalesBtn;
    public Button recruitBtn;
    String scoutName;
    public Date currentDate;
    long scoutId;
    DBOpenHelper helper = new DBOpenHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = Calendar.getInstance().getTime();
        setContentView(R.layout.activity_report);
        invReportBtn = findViewById(R.id.inv_sum_btn);
        totSalesBtn = findViewById(R.id.total_sales_btn);
        locationSumBtn = findViewById(R.id.location_summary_btn);
        invSalesBtn = findViewById(R.id.invsales_btn);
        recruitBtn = findViewById(R.id.recruit_report_btn);
        Intent intent = getIntent();
        scoutId = intent.getLongExtra("scoutId", -1);
        scoutName = intent.getStringExtra("scoutName");
        invReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report = createInvReport();
                InventoryReportDialog invRD = new InventoryReportDialog();
                invRD.setInvDialogValues(scoutName,report,"Inventory Report");
                invRD.show(getSupportFragmentManager(),"Inventory Report");

            }
        });
        totSalesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report = createSalesReport();
                InventoryReportDialog invRd = new InventoryReportDialog();
                invRd.setInvDialogValues(scoutName, report, "Sales Report");
                invRd.show(getSupportFragmentManager(),"Sales Report");
            }
        });
        invSalesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report = createSalesInvReport();
                InventoryReportDialog invRd = new InventoryReportDialog();
                invRd.setInvDialogValues(scoutName, report, "Sales/Inventory Report");
                invRd.show(getSupportFragmentManager(), "Sales/Inventory Report");
            }
        });
        locationSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, locationReportActivity.class);
                intent.putExtra("scoutId", scoutId);
                startActivity(intent);
            }
        });
        recruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String report = createRecruitReport();
                InventoryReportDialog invRd = new InventoryReportDialog();
                invRd.setInvDialogValues(scoutName, report, "Recruit Report");
                invRd.show(getSupportFragmentManager(), "Recruit Report");

            }
        });


    }
    @Override
    protected  void onResume() {
        helper.getReadableDatabase();

        super.onResume();
    }
    public String createRecruitReport(){
        String invReport = "As of " +currentDate.toString() + "\n" +"\n" ;
        String nameMarker = "RECRUIT NAME: ";
        String phoneMarker = "RECRUIT PHONE: ";
        Cursor rCursor = helper.getAllRecruits((int)scoutId);
        while(rCursor.moveToNext()){
            String name = rCursor.getString(rCursor.getColumnIndex(DBOpenHelper.RECRUIT_NAME));
            String phone = rCursor.getString(rCursor.getColumnIndex(DBOpenHelper.RECRUIT_PHONE));
            invReport = invReport + nameMarker  + name + "\n" +
                    phoneMarker + phone + "\n" + "\n";
        }
        return invReport;

    }
    public String createInvReport(){
        String invReport = "As of " +currentDate.toString() + "\n" +"\n" ;
        String nameMarker = "ITEM NAME: ";
        String qtyMarker ="QTY:";
        String totalMarker = "TOTAL: $";
        Cursor cursor = helper.getAllPopInv((int)scoutId);
        int finalTotal = 0;
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_NAME));
            int qty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));
            int price = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE));
            int dollars = price /100;
            int total = qty * dollars;
            finalTotal = finalTotal + total;
            String qtyString = String.valueOf(qty);
            String totalString = String.valueOf(total);
            invReport = invReport + nameMarker +"'" +name +"'"+ "\n " + qtyMarker + qtyString + "\n " + totalMarker + totalString + "\n\n\n";

        }
        invReport = invReport + "Complete Total: $" + finalTotal + "\n(Complete Inventory Value)";
        return invReport;
    }
    public String createSalesReport(){
        String salesReport = "As of " +currentDate.toString() + "\n" +"\n" ;
        String nameMarker = "ITEM NAME: ";
        String qtyMarker ="QTY:";
        String totalMarker = "TOTAL: $";
        Cursor saleCursor = helper.getallSoldPop((int) scoutId);
        int finalTotal = 0;
        while(saleCursor.moveToNext()){
            String name = saleCursor.getString(saleCursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_NAME));
            int qty = saleCursor.getInt(saleCursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_QTY));
            int price = saleCursor.getInt(saleCursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_PRICE));
            int dollars = price /100;
            int total = qty * dollars;
            finalTotal = finalTotal + total;
            String qtyString = String.valueOf(qty);
            String totalString = String.valueOf(total);
            salesReport = salesReport +  nameMarker +"'" +name +"'"+ "\n " + qtyMarker + qtyString + "\n " + totalMarker + totalString + "\n\n\n";

        }
        salesReport = salesReport + "Complete Total: $" + finalTotal + "\n(Complete Sales Value)";
        return salesReport;
    }
    public String createSalesInvReport(){
        String salesInvReport = "As of " + currentDate.toString() + "\n" +"\n"  ;
        Cursor cursor = helper.getallSoldPop((int)scoutId);
        int salesTotal = 0;
        while(cursor.moveToNext()){
            int qty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_QTY));
            int price = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_PRICE));
            int dollars = price/100;
            int total = qty * dollars;
            salesTotal = salesTotal + total;
        }
        int invTotal = 0;
        Cursor popCursor = helper.getAllPopInv((int)scoutId);
        while(popCursor.moveToNext()){
            int qty = popCursor.getInt(popCursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));
            int price = popCursor.getInt(popCursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE));
            int dollars = price/100;
            int total = qty * dollars;
            invTotal = invTotal + total;

        }
        salesInvReport = "Sales: $" + salesTotal + "/" + "Inventory: $" + invTotal;
        return salesInvReport;
    }


}