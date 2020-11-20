package com.example.popbooks;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBProvider extends ContentProvider {
    // authority and path strings
    private static final String AUTHORITY = "com.example.popbooks.dbprovider";
    private static final String SCOUT_PATH = "scoutTable";
    private static final String POP_PATH = "popInvTable";
    private static final String SALE_PATH ="saleTable";
    private static final String POP_SOLD_PATH = "popSoldTable";
    private static final String RECRUIT_PATH = "recruitTable";

    // Path uris
    public static final Uri SCOUT_URI = Uri.parse("Content://" + AUTHORITY +"/" + SCOUT_PATH);
    public static final Uri POP_URI = Uri.parse("Content://" + AUTHORITY +"/" + POP_PATH);
    public static final Uri SALE_URI = Uri.parse("Content://" + AUTHORITY +"/" + SALE_PATH);
    public static final Uri POP_SOLD_URI = Uri.parse("Content://" + AUTHORITY +"/" + POP_SOLD_PATH);
    public static final Uri RECRUIT_URI = Uri.parse("Content://" + AUTHORITY +"/" + RECRUIT_PATH);

    // constants
    private static final int SCOUTS = 1;
    private static final int SCOUTS_ID = 2;
    private static final int POP = 3;
    private static final int POP_ID = 4;
    private static final int SALES = 5;
    private static final int SALES_ID = 6;
    private static final int POP_SOLD = 7;
    private static final int POP_SOLD_ID = 8;
    private static final int RECRUITS = 9;
    private static final int RECRUITS_ID = 10;

    //Uri matcher initialization
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        uriMatcher.addURI(AUTHORITY, SCOUT_PATH, SCOUTS);
        uriMatcher.addURI(AUTHORITY, SCOUT_PATH + "/#", SCOUTS_ID);
        uriMatcher.addURI(AUTHORITY, POP_PATH, POP);
        uriMatcher.addURI(AUTHORITY, POP_PATH + "/#", POP_ID);
        uriMatcher.addURI(AUTHORITY, SALE_PATH, SALES);
        uriMatcher.addURI(AUTHORITY, SALE_PATH + "/#", SALES_ID);
        uriMatcher.addURI(AUTHORITY, POP_SOLD_PATH, POP_SOLD);
        uriMatcher.addURI(AUTHORITY, POP_SOLD_PATH + "/#", POP_SOLD_ID);
        uriMatcher.addURI(AUTHORITY, RECRUIT_PATH, RECRUITS);
        uriMatcher.addURI(AUTHORITY, RECRUIT_PATH + "/#", RECRUITS_ID);
    }



    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,@Nullable String selection,@Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch(uriMatcher.match(uri)){
            case SCOUTS:
                return database.query(DBOpenHelper.SCOUT_TABLE, DBOpenHelper.SCOUT_COLUMNS, selection, null,
                        null, null, DBOpenHelper.SCOUT_ID + " ASC");
            case SCOUTS_ID:
                return database.query(DBOpenHelper.SCOUT_TABLE, DBOpenHelper.SCOUT_COLUMNS, DBOpenHelper.SCOUT_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, DBOpenHelper.SCOUT_ID + " ASC");
            case POP:
                return database.query(DBOpenHelper.POP_INV_TABLE, DBOpenHelper.POP_INV_COLUMNS, selection, null,
                        null, null, DBOpenHelper.POP_INV_ID + " ASC");
            case POP_ID:
                return database.query(DBOpenHelper.POP_INV_TABLE, DBOpenHelper.POP_INV_COLUMNS, DBOpenHelper.POP_INV_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, DBOpenHelper.POP_INV_ID + " ASC");
            case SALES:
                return database.query(DBOpenHelper.SALE_TABLE, DBOpenHelper.SALE_COLUMNS, selection, null,
                        null, null, DBOpenHelper.SALE_ID + " ASC");
            case SALES_ID:
                return database.query(DBOpenHelper.SALE_TABLE, DBOpenHelper.SALE_COLUMNS, DBOpenHelper.SALE_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, DBOpenHelper.SALE_ID + " ASC");
            case POP_SOLD:
                return database.query(DBOpenHelper.POP_SOLD_TABLE, DBOpenHelper.POP_SOLD_COLUMNS, selection, null,
                        null, null, DBOpenHelper.POP_SOLD_ID + " ASC");
            case POP_SOLD_ID:
                return database.query(DBOpenHelper.POP_SOLD_TABLE, DBOpenHelper.POP_SOLD_COLUMNS, DBOpenHelper.POP_SOLD_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, DBOpenHelper.POP_SOLD_ID + " ASC");
            case RECRUITS:
                return database.query(DBOpenHelper.RECRUIT_TABLE, DBOpenHelper.RECRUIT_COLUMNS, selection, null,
                        null, null, DBOpenHelper.RECRUIT_ID + " ASC");
            case RECRUITS_ID:
                return database.query(DBOpenHelper.RECRUIT_TABLE, DBOpenHelper.RECRUIT_COLUMNS, DBOpenHelper.RECRUIT_ID + "=" + uri.getLastPathSegment(), null,
                        null, null, DBOpenHelper.RECRUIT_ID + " ASC");
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }
    }
    @Override
    public String getType(@NonNull Uri uri) {return null;}

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        switch (uriMatcher.match(uri)){
            case SCOUTS:
                id = database.insert(DBOpenHelper.SCOUT_TABLE, null, contentValues);
                return uri.parse(SCOUT_PATH + "/" + id);
            case POP:
                id = database.insert(DBOpenHelper.POP_INV_TABLE, null, contentValues);
                return uri.parse(POP_PATH + "/" + id);
            case SALES:
                id = database.insert(DBOpenHelper.SALE_TABLE, null, contentValues);
                return uri.parse(SALE_PATH + "/" + id);
            case POP_SOLD:
                id = database.insert(DBOpenHelper.POP_SOLD_TABLE, null, contentValues);
                return uri.parse(POP_SOLD_PATH + "/" + id);
            case RECRUITS:
                id = database.insert(DBOpenHelper.RECRUIT_TABLE, null, contentValues);
                return uri.parse(RECRUIT_PATH + "/" + id);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (uriMatcher.match(uri)){
            case SCOUTS:
                return database.delete(DBOpenHelper.SCOUT_TABLE, s, strings);
            case POP:
                return database.delete(DBOpenHelper.POP_INV_TABLE, s, strings);
            case SALES:
                return database.delete(DBOpenHelper.SALE_TABLE, s, strings);
            case POP_SOLD:
                return database.delete(DBOpenHelper.POP_SOLD_TABLE, s, strings);
            case RECRUITS:
                return database.delete(DBOpenHelper.RECRUIT_TABLE, s, strings);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update( @NonNull Uri uri,@Nullable  ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (uriMatcher.match(uri)){
            case SCOUTS:
                return database.update(DBOpenHelper.SCOUT_TABLE, contentValues,s, strings);
            case POP:
                return database.update(DBOpenHelper.POP_INV_TABLE, contentValues,s, strings);
            case SALES:
                return database.update(DBOpenHelper.SALE_TABLE, contentValues,s, strings);
            case POP_SOLD:
                return database.update(DBOpenHelper.POP_SOLD_TABLE, contentValues,s, strings);
            case RECRUITS:
                return database.update(DBOpenHelper.RECRUIT_TABLE, contentValues,s, strings);
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
