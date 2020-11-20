package com.example.popbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AddScoutDialog.AddScoutDialogListener{
    private DBOpenHelper helper = new DBOpenHelper(MainActivity.this);
    public ListView listView;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDialog ld = new loginDialog();
        ld.setCancelable(false);
        ld.show(getSupportFragmentManager(), "Login");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scout Select");
        listView = findViewById(R.id.scoutLV);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long posid) {
                Intent intent = new Intent(MainActivity.this, ScoutDetail.class);
                long id = view.getId();
                intent.putExtra("scoutId", id);
                Cursor cursor = helper.getAllPopInv((int)id);
                if(cursor.getCount() == 0){
                    addBasePopINv((int)id);
                }else {
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scoutselectmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_test_data_ss:
                    addTestData();
                return true;
            case R.id.add_scout:
                openDialog();
                return true;
            case R.id.about_popbooks:
                aboutPopbooksDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        helper.getReadableDatabase();
        cursor = helper.getAllScouts();

        displayListView();
        super.onResume();
    }
    public void aboutPopbooksDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.about_popbooks, null);
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addTestData() {
        helper.insertScout( "Ethan Digger", 1);
        helper.insertScout( "Tristian Stumpf", 6);
        helper.insertScout("Justin Wright",5);
        helper.insertScout("Turtle McDurtle", 3);
        onResume();
    }
    public void addBasePopINv(int scoutId){
        helper.insertPopInv(scoutId,"White Cheddar", "9oz White Cheddar Popcorn Bag The perfect combination of light, crispy popcorn and rich white cheddar cheese deliciousness in every bite (Contains Milk)",20,0);
        helper.insertPopInv(scoutId,"Unbelievable Butter Popcorn", "Unbelievable Butter Popcorn - 12pk Microwave Carton: The perfect microwavable combination of popcorn, oil, salt and butter to make you feel like you’re at the movies (Contains Milk)", 25, 0);
        helper.insertPopInv(scoutId, "Salted Caramel Popcorn", "20oz Salted Caramel Bag: A unique combination of sweet caramel corn with a perfectly balanced finish of sea salt (Contains Milk and Soy) *The nutritional facts panel, ingredients and allergen statements for the Salted Caramel Popcorn may vary. Consumers should rely on our package ingredient label for the most accurate information.",25,0);
        helper.insertPopInv(scoutId,"Dark Chocolate Salted Caramels", "10.5oz Dark Chocolate Salted Caramels Bag: Rich caramels generously coated in our smooth dark chocolate. (Contains Eggs, Milk, Soy)", 30,0);
        helper.insertPopInv(scoutId, "Caramel Corn", "9oz Caramel Corn Bag: A traditional favorite full of rich caramel flavor (Contains Soy)", 10,0);
        helper.insertPopInv(scoutId,"Blazing Hot Popcorn","8oz Blazin' Hot Popcorn Bag: Bold and cheesy flavored popcorn with the perfect spicy zing in every bite (Contains Milk)", 20,0);
        helper.insertPopInv(scoutId, "30 Dollar American Heroes Donation", "Send a gift of popcorn to our first responders, military men and women, their families, and veteran organizations.", 30,0);
        helper.insertPopInv(scoutId, "Chocolatey Caramel Crunch Tin", "16oz Chocolatey Caramel Crunch Tin: Sweet, crunchy caramel popcorn coated in smooth & creamy chocolate in a Gift Tin (Contains Milk and Soy, gift tin design may vary).", 35, 0);
    }
    public void displayListView(){
        ArrayList<Scout> scouts = new ArrayList<>();
        while(cursor.moveToNext()){
            Scout scout = new Scout();
            scout.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SCOUT_NAME)));
            int rank = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCOUT_RANK));
            int id = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCOUT_ID));
            scout.setId(id);
            switch(rank){
                case 0:
                    scout.setImg(R.drawable.lion1);
                    break;
                case 1:
                    scout.setImg(R.drawable.bobcat2);
                    break;
                case 2:
                    scout.setImg(R.drawable.tiger3);
                    break;
                case 3:
                    scout.setImg(R.drawable.wolf4);
                    break;
                case 4:
                    scout.setImg(R.drawable.bear5);
                    break;
                case 5:
                    scout.setImg(R.drawable.webelo6);
                    break;
                case 6:
                    scout.setImg(R.drawable.arrow7);
                    break;
                default:
                    Toast.makeText(this, "no img found", Toast.LENGTH_SHORT).show();
            }
            scouts.add(scout);

        }
        scoutAdapter adapter = new scoutAdapter(this, R.layout.ss_listview_item,scouts);
        listView = findViewById(R.id.scoutLV);
        listView.setAdapter(adapter);

    }
    public void openDialog(){
        AddScoutDialog addScoutDialog = new AddScoutDialog();
        addScoutDialog.show(getSupportFragmentManager(), "Add Scout Dialog");
    }

    @Override
    public void applyTexts(String name, int rank) {
        Scout scout = new Scout();
        scout.setName(name);
        scout.setRank(rank);
        helper.insertScout(scout.getName(), scout.getRank());
        onResume();
    }
}