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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class ScoutDetail extends AppCompatActivity implements EditScoutDialog.EditScoutDialogListener, AddressEntryDialog.AddressEntryDialogListener {
    public TextView nametxt;
    public Spinner rank;
    public Button inv;
    public Button sale;
    public Button reports;
    long scoutId;
    String scoutName;
    int scoutRank;
    Cursor cursor;
    String add;
    int typ;

    private DBOpenHelper helper = new DBOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        add = "no location provided";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_detail);

        Intent intent = getIntent();
        scoutId = intent.getLongExtra("scoutId", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scout Details");

        nametxt = findViewById(R.id.scout_detail_name);
        rank = findViewById(R.id.scout_detail_rank);
        rank.setEnabled(false);
        inv = findViewById(R.id.scout_detail_inv_btn);
        sale = findViewById(R.id.scout_detail_sale_btn);
        reports = findViewById(R.id.scout_detail_report_btn);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopInvActivity();
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new AlertDialog.Builder(ScoutDetail.this)
                            .setTitle("Request Location")
                            .setMessage("Is this a wagon/storefront sale or an online sale?")
                            .setPositiveButton("Online sale", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ScoutDetail.this, SaleDashboard.class);
                                        intent.putExtra("location", add);
                                        intent.putExtra("type", 2);
                                        intent.putExtra("scoutId", scoutId);
                                        startActivity(intent);


                                }
                            })
                            .setNegativeButton("Wagon or Storefront", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AddressEntryDialog addressEntryDialog = new AddressEntryDialog();
                                    addressEntryDialog.show(getSupportFragmentManager(), "enter address");

                                }
                            })
                            .show();



            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoutDetail.this, ReportActivity.class);
                intent.putExtra("scoutId", scoutId);
                intent.putExtra("scoutName", scoutName);
                startActivity(intent);
            }
        });



    }




    public void openPopInvActivity() {
        Intent intent = new Intent(this, PopInvListview.class);
        intent.putExtra("scoutId", scoutId);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scoutdetailsmenu, menu);
        return true;
    }

    public void openDialog(String name, int rank, long id) {
        EditScoutDialog editScoutDialog = new EditScoutDialog();
        editScoutDialog.setDialogValues(name, rank, id);
        editScoutDialog.show(getSupportFragmentManager(), "Edit Scout");

    }

    @Override
    public void applyTexts(String name, int rank, int id) {
        helper.updateScout(name, rank, id);
        onResume();
    }

    @Override
    public void applyAddress(String address, int type) {
        add = address;
        typ = type;
        Intent intent = new Intent(ScoutDetail.this, SaleDashboard.class);
        intent.putExtra("location", add);
        intent.putExtra("type", typ);
        intent.putExtra("scoutId", scoutId);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_scout:
                openDialog(scoutName, scoutRank, scoutId);
                return true;
            case R.id.delete_scout:
                if (checkScoutInv(scoutId) == true) {
                    Toast.makeText(this, "Cannot delete scout with active inventory, please reallocate inventory.", Toast.LENGTH_LONG).show();
                } else {
                    int c = helper.deleteScout((int) scoutId);
                    Toast.makeText(this, "This scout profile has been deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.scout_detail_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    // use this for scout detail page
    public boolean checkScoutInv(long id) {
        Cursor check = helper.getAllPopInv((int) id);
        Boolean checker = false;
        while (check.moveToNext()) {
            if (check.getInt(check.getColumnIndex(DBOpenHelper.POP_INV_QTY)) > 0) {
                checker = true;
                break;
            } else {
                return false;
            }
        }
        return checker;
    }

    @Override
    protected void onResume() {
        helper.getReadableDatabase();
        cursor = helper.getOneScout((int) scoutId);
        String id = String.valueOf(scoutId);
        displayScoutDetails();
        super.onResume();
    }



    public void displayScoutDetails(){
            while(cursor.moveToNext()){
                scoutName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SCOUT_NAME));
                scoutRank = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCOUT_RANK));
                nametxt.setText(scoutName);
                rank.setSelection(scoutRank);
            }
            cursor.moveToFirst();
            while(cursor.moveToNext()){
                scoutName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SCOUT_NAME));
                scoutRank = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCOUT_RANK));
            }

    }


}