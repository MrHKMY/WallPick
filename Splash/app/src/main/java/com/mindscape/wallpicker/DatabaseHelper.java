package com.mindscape.wallpicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hakimi on 26/4/2020.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydatabase.db";

    public DatabaseHelper (Context context){
        super (context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + FavouriteContract.FavouriteEntry.TABLE_NAME + " (" + FavouriteContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteContract.FavouriteEntry.COLUMN_NAME + " TEXT NOT NULL" +
        ");";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addText(String item1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, item1);
        long result = db.insert(FavouriteContract.FavouriteEntry.TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + FavouriteContract.FavouriteEntry.TABLE_NAME, null);
        return data;
    }
}
