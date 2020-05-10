package com.MohaddisMedia.mohaddis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.MohaddisMedia.mohaddis.DB.MasadirDBHelper;

public class
DBTester {
    Context mContext;
    public DBTester() {
    }

    public DBTester(Context mContext) {
        this.mContext = mContext;
    }

    public Context getApplicationContext()
    {
        return mContext;
    }


    public void readFromMasadirTable()
    {
        MasadirDBHelper masadirDBHelper = new MasadirDBHelper(getApplicationContext());
        SQLiteDatabase db = masadirDBHelper.getWritableDatabase();

        String query = "SELECT * from "+ DBHelper.MasadaEntry.TABLE_NAME;


        String[] projection = {
                DBHelper.MasadaEntry.MA_ID,
                DBHelper.MasadaEntry.MASADIR_NAME_ARABIC,
                DBHelper.MasadaEntry.MASADIR_NAME_URDU,
        };


        Cursor cursor = db.query(
                DBHelper.MasadaEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order

        );
        Cursor cursor1 = db.rawQuery(query,null);

        Log.d("tryResults", cursor1.getCount()+"");
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            Log.d("tryResults",cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID))+"  "+cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_ARABIC))+"  "+cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));

        }
    }
}
