package com.example.popbooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    // db name and version
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "popbooks.db";

    //cub scout table
    public static final String SCOUT_TABLE = "scoutTable";
    public static final String SCOUT_ID = "_id";
    public static final String SCOUT_NAME = "scoutName";
    public static final String SCOUT_RANK = "scoutRank";
    public static final String[] SCOUT_COLUMNS ={SCOUT_ID, SCOUT_NAME,SCOUT_RANK};

    private static final String CREATE_SCOUT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SCOUT_TABLE + " (" +
                    SCOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SCOUT_NAME + " TEXT, " +
                    SCOUT_RANK + " INTEGER)";

    //popcorn inventory table
    public static final String POP_INV_TABLE = "popInvTable";
    public static final String POP_INV_ID ="_id";
    public static final String POP_INV_FK ="popInvFk";
    public static final String POP_INV_NAME = "popInvName";
    public static final String POP_INV_DESC ="popInvDesc";
    public static final String POP_INV_PRICE = "popInvPrice";
    public static final String POP_INV_QTY = "popInvQty";
    public static final String[] POP_INV_COLUMNS ={POP_INV_ID, POP_INV_FK,POP_INV_NAME, POP_INV_DESC, POP_INV_PRICE, POP_INV_QTY};


    private static final String CREATE_POP_INV_TABLE =
            "CREATE TABLE IF NOT EXISTS " + POP_INV_TABLE + " (" +
                    POP_INV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    POP_INV_FK + " INTEGER, " +
                    POP_INV_NAME + " TEXT, " +
                    POP_INV_DESC + " TEXT, " +
                    POP_INV_PRICE + " INTEGER, " +
                    POP_INV_QTY + " INTEGER, " +
                    "FOREIGN KEY(" + POP_INV_FK + ") REFERENCES " + SCOUT_TABLE + "(" + SCOUT_ID + ")" + ")";
    // sale table
    public static final String SALE_TABLE = "saleTable";
    public static final String SALE_ID = "_id";
    public static final String SALE_FK = "saleFk";
    public static final String SALE_TYPE = "saleType";
    public static final String SALE_DATE = "saleDate";
    public static final String SALE_TOTAL = "saleTotal";
    public static final String SALE_LOCATION = "saleLocation";
    public static final String[] SALE_COLUMNS ={SALE_ID, SALE_FK,SALE_TYPE,SALE_DATE, SALE_TOTAL, SALE_LOCATION};


    private static final String CREATE_SALE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + SALE_TABLE + " (" +
                    SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SALE_FK + " INTEGER, " +
                    SALE_TYPE + " INTEGER, " +
                    SALE_DATE + " TEXT, " +
                    SALE_TOTAL + " INTEGER, " +
                    SALE_LOCATION + " TEXT, " +
                    "FOREIGN KEY(" + SALE_FK +") REFERENCES " + SCOUT_TABLE + "(" + SCOUT_ID + ")" + ")";


    //Pop Sold table
    public static final String POP_SOLD_TABLE = "popSoldTable";
    public static final String POP_SOLD_ID ="_id";
    public static final String POP_SOLD_FK = "popSoldCubId";
    public static final String POP_SOLD_SALE_ID = "popSoldSaleId";
    public static final String POP_SOLD_ITEM_QTY ="popSoldItemQty";
    public static final String POP_SOLD_ITEM_NAME = "popSoldItemName";
    public static final String SCOUT_DONATION ="CubScoutDonation";
    public static final String POP_SOLD_ITEM_PRICE ="popSoldItemPrice";
    public static final String[] POP_SOLD_COLUMNS ={POP_SOLD_ID, POP_SOLD_FK,POP_SOLD_SALE_ID, POP_SOLD_ITEM_QTY, POP_SOLD_ITEM_NAME, POP_SOLD_ITEM_PRICE};


    private static final String CREATE_POP_SOLD_TABLE =
            "CREATE TABLE IF NOT EXISTS " + POP_SOLD_TABLE + " (" +
                    POP_SOLD_ID + " INTEGER, " +
                    POP_SOLD_FK + " INTEGER, " +
                    POP_SOLD_SALE_ID + " INTEGER, " +
                    POP_SOLD_ITEM_QTY + " INTEGER, " +
                    POP_SOLD_ITEM_NAME + " TEXT, " +
                    POP_SOLD_ITEM_PRICE + " INTEGER, " +
                    "FOREIGN KEY(" + POP_SOLD_ID + ") REFERENCES " + POP_INV_TABLE + "(" + POP_INV_ID + ")," +
                    "FOREIGN KEY(" + POP_SOLD_FK + ") REFERENCES " + SCOUT_TABLE + "(" + SCOUT_ID + ")," +
                    "FOREIGN KEY(" + POP_SOLD_SALE_ID + ") REFERENCES " + SALE_TABLE + "(" + SALE_ID + ")," +
                    "PRIMARY KEY(" + POP_SOLD_ID +", " + POP_SOLD_SALE_ID + ")" +")";

    //recruit table
    public static final String RECRUIT_TABLE = "recruitTable";
    public static final String RECRUIT_ID ="_id";
    public static final String RECRUIT_FK ="cubScoutId";
    public static final String RECRUIT_NAME = "recruitName";
    public static final String RECRUIT_PHONE ="recruitPhone";
    public static final String[] RECRUIT_COLUMNS ={RECRUIT_ID, RECRUIT_FK,RECRUIT_NAME, RECRUIT_PHONE};

    public static final int DONATION_ID = -50;


    private static final String CREATE_RECRUIT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + RECRUIT_TABLE + " (" +
                    RECRUIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECRUIT_FK + " INTEGER, " +
                    RECRUIT_NAME +" TEXT, " +
                    RECRUIT_PHONE + " TEXT, " +
                    "FOREIGN KEY(" + RECRUIT_FK + ") REFERENCES " + SCOUT_TABLE + "(" + SCOUT_ID + ")" + ")";



    public DBOpenHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_SCOUT_TABLE);
    db.execSQL(CREATE_POP_INV_TABLE);
    db.execSQL(CREATE_SALE_TABLE);
    db.execSQL(CREATE_POP_SOLD_TABLE);
    db.execSQL(CREATE_RECRUIT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP TABLE IF EXISTS " + SCOUT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + POP_INV_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + POP_SOLD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECRUIT_TABLE);
        onCreate(db);

    }
    public Cursor getAllScouts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.SCOUT_TABLE,null);
        return cursor;
    }
    public Cursor getOneScout(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SCOUT_TABLE + " WHERE " + SCOUT_ID +
                "=" + id, null);
        return cursor;
    }
    public int deleteScout(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete(SCOUT_TABLE, SCOUT_ID + "=" + id, null);
        db.delete(POP_INV_TABLE, POP_INV_FK + "=" + id, null);
        db.delete(SALE_TABLE, SALE_FK + "=" + id, null);
        return check;
    }
    public int deletePopInv(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int check = db.delete(POP_INV_TABLE, POP_INV_ID + "=" + id, null);
        return check;
    }
    public void insertScout(String name, int rank){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCOUT_NAME, name);
        values.put(SCOUT_RANK, rank);
        db.insert(SCOUT_TABLE, null, values);
    }
    public Cursor getAllPopInv(int scoutId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POP_INV_TABLE + " WHERE " + POP_INV_FK +
                "=" + scoutId, null);
        return cursor;
    }
    public Cursor getOnePopInv(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POP_INV_TABLE + " WHERE " +
                POP_INV_ID + "=" + id, null);
        return cursor;
    }
    public Cursor getDetailPop(int id, int scoutid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POP_INV_TABLE + " WHERE " +
                POP_INV_ID + "=" + id + " AND " + POP_INV_FK + "=" + scoutid, null);
        return cursor;
    }
    public void addToTotal(int id, int newTotal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SALE_TOTAL, newTotal);
        db.update(SALE_TABLE,values,SALE_ID + "=" +
                id, null);
    }
    public void addToSoldPopTable(PopInv popInv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POP_SOLD_FK,popInv.getScoutId());
        values.put(POP_SOLD_ID, popInv.getId());
        values.put(POP_SOLD_ITEM_NAME, popInv.getName());
        values.put(POP_SOLD_SALE_ID, popInv.getSaleId());
        values.put(POP_SOLD_ITEM_PRICE, popInv.getPrice() * 100);
        values.put(POP_SOLD_ITEM_QTY, 1);
        db.insert(POP_SOLD_TABLE, null, values);
    }
    public Cursor getPopSold(int popId, int saleId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POP_SOLD_TABLE +
                " WHERE " + POP_SOLD_ID + "=" + popId +
                " AND " + POP_SOLD_SALE_ID + "=" + saleId, null);
        return cursor;
    }
    public void insertDonationSale(int amount, int saleId, int scoutId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POP_SOLD_FK,scoutId);
        values.put(POP_SOLD_ITEM_NAME, SCOUT_DONATION);
        values.put(POP_SOLD_ITEM_QTY, 1);
        values.put(POP_SOLD_SALE_ID, saleId);
        values.put(POP_SOLD_ITEM_PRICE, amount * 100);
        db.insert(POP_SOLD_TABLE,null,values);

    }
    public void insertRecruit(String name, String phone, int scoutId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECRUIT_NAME, name);
        values.put(RECRUIT_PHONE, phone);
        values.put(RECRUIT_FK, scoutId);
        db.insert(RECRUIT_TABLE, null, values);
    }
    public void updateSoldPop(int popId, int saleId, int newQty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POP_SOLD_ITEM_QTY, newQty);
        db.update(POP_SOLD_TABLE,values,POP_INV_ID + "="+
                popId + " AND " + POP_SOLD_SALE_ID + "=" + saleId,null);
    }
    public Cursor getSale(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SALE_TABLE +
                " WHERE " + SALE_ID + "=" + (int)id, null);
        return cursor;
    }
    public long createSale(Sale sale){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SALE_FK, sale.getScoutId());
        values.put(SALE_LOCATION, sale.getLocation());
        values.put(SALE_TOTAL, sale.getTotal());
        values.put(SALE_TYPE, sale.getType());
        values.put(SALE_DATE, sale.getDate());
        long id = db.insert(SALE_TABLE, null,values);
        return id;

    }
    public void insertPopInv(int scoutId, String name, String desc, int price, int qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_INV_FK, scoutId);
        values.put(DBOpenHelper.POP_INV_NAME, name);
        values.put(DBOpenHelper.POP_INV_DESC, desc);
        int cents = (int)price *100; // convert double (full price) into raw cents to store in db
        values.put(DBOpenHelper.POP_INV_PRICE, cents);
        values.put(DBOpenHelper.POP_INV_QTY, qty);
        db.insert(POP_INV_TABLE, null, values);

    }
    public void updatePopInv(int id, String name, String desc, int price, int qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POP_INV_NAME, name);
        values.put(POP_INV_DESC, desc);
        int cents = price * 100;
        values.put(POP_INV_PRICE, cents);
        values.put(POP_INV_QTY, qty);
        db.update(POP_INV_TABLE, values,POP_INV_ID + " =" + id, null);

    }
    public void updatePopQty(long id, int qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_INV_QTY, qty);
        db.update(DBOpenHelper.POP_INV_TABLE, values,
                POP_INV_ID + " =" + id, null);
    }

    public void updateScout(String name, int rank, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SCOUT_NAME, name);
        values.put(SCOUT_RANK, rank);
        db.update(SCOUT_TABLE, values, SCOUT_ID +
                "=" + id, null);
    }
    public int getTotal(int saleId){
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + SALE_TABLE +
                " WHERE " + SALE_ID + "=" + saleId,null);
            while(cursor.moveToNext()){
                total = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SALE_TOTAL));
            }

        return total;
    }

    public Cursor getallSoldPop(int scoutId) {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POP_SOLD_TABLE +
                " WHERE " + POP_SOLD_FK + "=" + scoutId, null);
        return cursor;
    }

    public Cursor getallSales(int scoutId, int type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SALE_TABLE +
                " WHERE " + SALE_FK + "=" + scoutId + " AND " + SALE_TYPE + "=" + type, null);
        return cursor;
    }

    public Cursor getAllRecruits(int scoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RECRUIT_TABLE +
                " WHERE " + RECRUIT_FK + "=" + scoutId,null);
        return cursor;
    }
}
