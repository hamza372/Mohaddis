package com.MohaddisMedia.mohaddis.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.TakhreejDataModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class TakhreejBDHelper extends SQLiteAssetHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mohaddis";

    public TakhreejBDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<TakhreejDataModel> fetchTakhreej(int hadeesNo, int bookId)
    {
        ArrayList<TakhreejDataModel> takhreejList = new ArrayList<>();
        String query = "SELECT * from " +DBHelper.TakhreejEntry.TABLE_NAME + " AS t WHERE t." + DBHelper.TakhreejEntry.HADEESPRESENTID+" = "+ hadeesNo+" AND "+DBHelper.TakhreejEntry.PRESENTBOOKID+" = "+bookId+" GROUP BY "+ DBHelper.TakhreejEntry.BOOKNAMETAKHREEJ+" ORDER BY "+DBHelper.TakhreejEntry.TAKHREEJBOOKID;


        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);

        for(int i =0;i<cursor1.getCount();i++) {
            TakhreejDataModel model = new TakhreejDataModel();
            cursor1.moveToNext();
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.ID)));
            model.setHeadeesTakhreejId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.HADEESTAKHREEJID)));
            model.setHadeesIDPresent(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.HADEESPRESENTID)));
            model.setBookIdPresent(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.PRESENTBOOKID)));
            model.setBookIdTakhreej(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.TAKHREEJBOOKID)));
            model.setBookNamePresent(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.BOOKNAMEPRESENT)));
            model.setBookNameTakhreej(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.BOOKNAMETAKHREEJ)));
            model.setAvailability(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.AVAILABILITY)));
            takhreejList.add(model);
            Log.d("tryTakhreejResult",cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.BOOKNAMETAKHREEJ))+"  "+cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.TAKHREEJBOOKID))+"  "+cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TakhreejEntry.AVAILABILITY)));
        }
        return takhreejList;
    }

//    public void fetchHadeesWithTaraqeem(){
//        query = "SELECT * from " +
//                DBHelper.HadeesEntry.TABLE_NAME + "where"+
//
//        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());
//        // Gets the data repository in write mode
//        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
//        Cursor cursor1 = db.rawQuery(query,null);
//    }
}
