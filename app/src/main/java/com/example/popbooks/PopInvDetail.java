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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PopInvDetail extends AppCompatActivity implements EditPopInvDialog.EditPopInvDialogListener{
    public long popInvId;
    public long scoutId;
    public int popQty;
    DBOpenHelper helper = new DBOpenHelper(this);
    private ImageView img;
    private TextView name;
    private TextView desc;
    private TextView price;
    private TextView qty;
    private Button caseBtn;
    PopInv pop = new PopInv();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_inv_detail);
        Intent intent = getIntent();
        popInvId = intent.getLongExtra("popInvId", -1);
        scoutId = intent.getLongExtra("scoutId", -1);
        img=findViewById(R.id.pop_detail_img);
        name=findViewById(R.id.pop_detail_name);
        desc=findViewById(R.id.pop_detail_desc);
        price=findViewById(R.id.pop_detail_price);
        qty=findViewById(R.id.pop_detail_qty);
        caseBtn=findViewById(R.id.add_case_btn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        caseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Add Case")
                        .setMessage("Are you sure you want to add a case(12 units) to inventory?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addCase();
                                Toast.makeText(PopInvDetail.this, "Case has been added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("no",null)
                        .show();

            }
        });

    }
    @Override
    protected void onResume() {
        helper.getReadableDatabase();
        Cursor cursor = helper.getDetailPop((int)popInvId, (int)scoutId);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_NAME));
            pop.setImg(name);
            pop.setName(name);
            pop.setDesc(cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_DESC)));
            int cents = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE));
            int price = cents / 100;
            pop.setPrice(price);
            pop.setQty(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY)));
            popQty = pop.getQty();
        }
        getSupportActionBar().setTitle(pop.getName());
        img.setImageResource(pop.getImg());
        name.setText("Name: " + pop.getName());
        desc.setText("Description: " + pop.getDesc());
        price.setText(String.valueOf("Price: $" + (int)pop.getPrice()));
        qty.setText(String.valueOf("Quantity: " + pop.getQty()));


        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popinvdetailmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit_prod_details:
                openDialog(pop);
                return true;
            case R.id.delete_prod:
                if(pop.getQty() == 0){
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Product")
                            .setMessage("Are you sure you want to delete this product?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    helper.deletePopInv((int)popInvId);
                                    Toast.makeText(PopInvDetail.this, "Product deleted.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PopInvDetail.this, PopInvListview.class);
                                    intent.putExtra("scoutId", scoutId);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("no",null)
                            .show();

                }else{
                    Toast.makeText(this, "Quantity is not 0, please make sure you do not have this product before deleting.", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(PopInv p){
        EditPopInvDialog d = new EditPopInvDialog();
        d.setDialogValues(p);
        d.show(getSupportFragmentManager(), "Edit Product");
    }

    public void addCase(){
        helper.getWritableDatabase();
        helper.updatePopQty(popInvId,popQty +12);
        onResume();

    }

    @Override
    public void applyTexts(String name, String desc, int price, int qty) {
        helper.updatePopInv((int)popInvId, name, desc, price, qty);
        onResume();
    }
}