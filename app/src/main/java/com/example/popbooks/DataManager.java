package com.example.popbooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.jetbrains.annotations.NotNull;


public class DataManager {

    public static void insertScout(Context context, String name, int rank){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SCOUT_NAME, name);
        values.put(DBOpenHelper.SCOUT_RANK, rank);
        context.getContentResolver().insert(DBProvider.SCOUT_URI, values);
    }


    @NotNull
    public static Scout getScout(@NotNull Context context, long id){
        Cursor cursor = context.getContentResolver().query(DBProvider.SCOUT_URI, DBOpenHelper.SCOUT_COLUMNS,
                DBOpenHelper.SCOUT_ID + "=" + id, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SCOUT_NAME));
        int rank = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SCOUT_RANK));

        Scout scout = new Scout();
        scout.setId(id);
        scout.setName(name);
        scout.setRank(rank);
        return scout;
    }
    public static boolean deleteScout(@NotNull Context context, long id){
         context.getContentResolver().delete(DBProvider.SCOUT_URI,DBOpenHelper.SCOUT_ID + "=" +
                id,null);
         return true;
    }
    public static void updateScout(@NotNull Context context, long id, String name, int rank){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SCOUT_NAME, name);
        values.put(DBOpenHelper.SCOUT_RANK, rank);
        context.getContentResolver().update(DBProvider.SCOUT_URI, values, DBOpenHelper.SCOUT_ID +
                "=" + id, null);
    }
    public static Uri insertPopInv (@NotNull Context context, long scoutId, String name, String desc, double price, int qty){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_INV_FK, scoutId);
        values.put(DBOpenHelper.POP_INV_NAME, name);
        values.put(DBOpenHelper.POP_INV_DESC, desc);
        int cents = (int)price *100; // convert double (full price) into raw cents to store in db
        values.put(DBOpenHelper.POP_INV_PRICE, cents);
        values.put(DBOpenHelper.POP_INV_QTY, qty);
        Uri popUri = context.getContentResolver().insert(DBProvider.POP_URI, values);
        return popUri;
    }
    @NotNull
    public static PopInv getPopInv(@NotNull Context context, long id){
        Cursor cursor = context.getContentResolver().query(DBProvider.POP_URI, DBOpenHelper.POP_INV_COLUMNS,
                DBOpenHelper.POP_INV_ID + "=" + id, null, null);
        cursor.moveToFirst();
        long scoutId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.POP_INV_FK));
        String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_NAME));
        String desc = cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_INV_DESC));
        int cents = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE));
        double price = (double) cents / 100; // convert raw cents back into currency format.
        int qty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));

        PopInv pop = new PopInv();
        pop.setScoutId(scoutId);
        pop.setName(name);
        pop.setId(id);
        pop.setDesc(desc);
        pop.setPrice(price);
        pop.setQty(qty);
        return pop;
    }
    public static boolean deletePopInv(@NotNull Context context, long id){
        context.getContentResolver().delete(DBProvider.POP_URI,DBOpenHelper.POP_INV_ID + "=" +
                id,null);
        return true;
    }
    public static void updatePopInv(@NotNull Context context, long id, long scoutId, String name, String desc, double price, int qty){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_INV_FK, scoutId);
        values.put(DBOpenHelper.POP_INV_NAME, name);
        values.put(DBOpenHelper.POP_INV_DESC, desc);
        int cents = (int)price *100; // convert double (full price) into raw cents to store in db
        values.put(DBOpenHelper.POP_INV_PRICE, cents);
        values.put(DBOpenHelper.POP_INV_QTY, qty);
        context.getContentResolver().update(DBProvider.POP_URI, values, DBOpenHelper.POP_INV_ID +
                "=" + id, null);
    }
    public static Uri insertSale(@NotNull Context context, long scoutId, String date, int type, double total, String location){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SALE_FK, scoutId);
        values.put(DBOpenHelper.SALE_DATE, date);
        values.put(DBOpenHelper.SALE_TYPE, type);
        int cents = (int) total *100; // convert to cents to store in db
        values.put(DBOpenHelper.SALE_TOTAL, cents);
        values.put(DBOpenHelper.SALE_LOCATION, location);
        Uri saleUri = context.getContentResolver().insert(DBProvider.SALE_URI, values);
        return saleUri;
    }
    @NotNull
    public static Sale getSale(Context context, long id){
        Cursor cursor = context.getContentResolver().query(DBProvider.SALE_URI, DBOpenHelper.SALE_COLUMNS,
                DBOpenHelper.SALE_ID + "=" + id, null, null);
        cursor.moveToFirst();
        long scoutId =  cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SALE_FK));
        String date = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SALE_DATE));
        int type = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SALE_TYPE));
        int cents = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.SALE_TOTAL));
        int total = cents / 100; // convert to currency format
        String location = cursor.getString(cursor.getColumnIndex(DBOpenHelper.SALE_LOCATION));

        Sale sale = new Sale();
        sale.setId(id);
        sale.setScoutId(scoutId);
        sale.setDate(date);
        sale.setType(type);
        sale.setTotal(total);
        sale.setLocation(location);
        return sale;
    }
    public static boolean deleteSale(@NotNull Context context, long id){
        context.getContentResolver().delete(DBProvider.SALE_URI,DBOpenHelper.SALE_ID + "=" +
                id,null);
        return true;
    }
    public static void updateSale(@NotNull Context context, long id, long scoutId, String date, int type, double total, String location){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.SALE_FK, scoutId);
        values.put(DBOpenHelper.SALE_DATE, date);
        values.put(DBOpenHelper.SALE_TYPE, type);
        int cents = (int) total *100; // convert to cents to store in db
        values.put(DBOpenHelper.SALE_TOTAL, cents);
        values.put(DBOpenHelper.SALE_LOCATION, location);
        context.getContentResolver().update(DBProvider.SALE_URI, values, DBOpenHelper.SALE_ID +
                "=" + id, null);
    }
    public static Uri insertPopSold (@NotNull Context context, long id, long scoutId, long saleId, String name, double price, int qty){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_SOLD_ID, id);
        values.put(DBOpenHelper.POP_SOLD_FK, scoutId);
        values.put(DBOpenHelper.POP_SOLD_ID, saleId);
        values.put(DBOpenHelper.POP_SOLD_ITEM_NAME, name);
        int cents = (int)price *100; // convert double (full price) into raw cents to store in db
        values.put(DBOpenHelper.POP_SOLD_ITEM_PRICE, cents);
        values.put(DBOpenHelper.POP_SOLD_ITEM_QTY, qty);
        Uri popUri = context.getContentResolver().insert(DBProvider.POP_SOLD_URI, values);
        return popUri;
    }
    @NotNull
    public static PopInv getPopSold(Context context, long id, long saleId){
        Cursor cursor = context.getContentResolver().query(DBProvider.POP_SOLD_URI, DBOpenHelper.POP_SOLD_COLUMNS,
                DBOpenHelper.POP_SOLD_ID + "=" + id + " AND " + DBOpenHelper.POP_SOLD_SALE_ID +
                "=" + saleId, null, null);
        cursor.moveToFirst();
        long scoutId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_FK));
        String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_NAME));
        int cents = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_PRICE));
        double price = (double) cents / 100; // convert raw cents back into currency format.
        int qty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_QTY));

        PopInv pop = new PopInv();
        pop.setScoutId(scoutId);
        pop.setName(name);
        pop.setId(id);
        pop.setSaleId(saleId);
        pop.setPrice(price);
        pop.setQty(qty);
        return pop;
    }
    public static boolean deletePopSold(Context context, long id, long saleId){
        context.getContentResolver().delete(DBProvider.POP_SOLD_URI,DBOpenHelper.POP_SOLD_ID + "=" +
                id + " AND " + DBOpenHelper.POP_SOLD_SALE_ID + "=" + saleId,null);
        return true;
    }
    public static void updatePopSold(Context context,long id, long scoutId,long saleId, String name, double price, int qty){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.POP_SOLD_ID, id);
        values.put(DBOpenHelper.POP_SOLD_FK, scoutId);
        values.put(DBOpenHelper.POP_SOLD_ID, saleId);
        values.put(DBOpenHelper.POP_SOLD_ITEM_NAME, name);
        int cents = (int)price *100; // convert double (full price) into raw cents to store in db
        values.put(DBOpenHelper.POP_SOLD_ITEM_PRICE, cents);
        values.put(DBOpenHelper.POP_SOLD_ITEM_QTY, qty);
        context.getContentResolver().update(DBProvider.POP_SOLD_URI, values,DBOpenHelper.POP_SOLD_SALE_ID +
                "=" + saleId + " AND " + DBOpenHelper.POP_SOLD_ID + "=" + id, null);
    }
    public static Uri insertRecruit(Context context, long scoutId, String name, String phone){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.RECRUIT_FK, scoutId);
        values.put(DBOpenHelper.RECRUIT_NAME, name);
        values.put(DBOpenHelper.RECRUIT_PHONE, phone);
        Uri recruitUri = context.getContentResolver().insert(DBProvider.RECRUIT_URI, values);
        return recruitUri;

    }
    public static Recruit getRecruit(Context context, long id){
        Cursor cursor = context.getContentResolver().query(DBProvider.RECRUIT_URI, DBOpenHelper.RECRUIT_COLUMNS,
                DBOpenHelper.RECRUIT_ID + "=" + id, null, null);
        cursor.moveToFirst();
        long scoutId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.RECRUIT_FK));
        String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECRUIT_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.RECRUIT_PHONE));
        Recruit recruit = new Recruit();
        recruit.setScoutId(scoutId);
        recruit.setName(name);
        recruit.setPhone(phone);
        return recruit;
    }
    public static boolean deleteRecruit(Context context, long id){
        context.getContentResolver().delete(DBProvider.RECRUIT_URI, DBOpenHelper.RECRUIT_ID +
                "=" + id, null);
        return true;
    }
    public static void updateRecruit(Context context,long id, long scoutId, String name, String phone){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.RECRUIT_FK, scoutId);
        values.put(DBOpenHelper.RECRUIT_NAME, name);
        values.put(DBOpenHelper.RECRUIT_PHONE, phone);
        context.getContentResolver().update(DBProvider.RECRUIT_URI, values,DBOpenHelper.RECRUIT_ID +
                "=" + id, null);
    }
    

}
