package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    public DatabaseHelper2(Context context) {
        super(context, "Contactdata.db", null, 1);
    }

    public static Bundle nm = new Bundle();
    public static Bundle ct = new Bundle();

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Contactdetails(name TEXT primary key, contact TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Contactdetails");
    }

    public Boolean insertcdata(String name, String contact) {

        DatabaseHelper2.nm.putString("key",name);
        DatabaseHelper2.ct.putString("lock",contact);
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Contactdetails ", null);
        if (cursor.getCount() > 0) {
            DB.delete("Contactdetails", null, null);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);

        long result = DB.insert("Contactdetails", null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
}

