package com.MohaddisMedia.mohaddis.DB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class KutubDBHelper extends SQLiteAssetHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mohaddis";

    public KutubDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
