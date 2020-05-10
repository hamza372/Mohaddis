package com.MohaddisMedia.mohaddis;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.MohaddisMedia.mohaddis.DB.AHadeesDBHelper;
import com.MohaddisMedia.mohaddis.DB.HadithTopicsDBHelper;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;

public class Utilites {



    public static  float getTaraqimBasedHNo(SharedPreferences pref, int bookId, HadeesDataModel model)
    {
        int selectedTaraqim = 0;
        switch(bookId)
        {
            case 1:
                selectedTaraqim = pref.getInt(Constants.book1Tarqeem,1);
                break;
            case 2:
                selectedTaraqim = pref.getInt(Constants.book2Tarqeem,1);
                break;
            case 3:
                selectedTaraqim = pref.getInt(Constants.book3Tarqeem,1);
                break;
            case 4:
                selectedTaraqim = pref.getInt(Constants.book4Tarqeem,1);
                break;
            case 5:
                selectedTaraqim = pref.getInt(Constants.book5Tarqeem,1);
                break;
            case 6:
                selectedTaraqim = pref.getInt(Constants.book6Tarqeem,1);
                break;
        }

        String tarqimName = DBHelper.HadeesEntry.HadeesNumberTOne;
        switch(selectedTaraqim)
        {
            case 0:
                return  model.getHadeesNo();

            case 1:
                return  model.getHadeesNumberTOne();


            case 2:
                return  model.getHadeesNumberTTwo();

            case 3:
                return  model.getHadeesNumberTThree();

            case 4:
                return  model.getHadeesNumberTFour();

            case 5:
                return  model.getHadeesNumberTFive();

            case 6:
                return  model.getHadeesNumberTSix();

            case 7:
                return  model.getHadeesNumberTSeven();

            case 8:
                return  model.getHadeesNumberTEight();

            case 9:
                return  model.getHadeesNumberTNine();

            case 10:
                return  model.getHadeesNumberTTen();

        }

        return  model.getHadeesNumberTOne();
    }

    public static String getTaraqimNameAndNo(SharedPreferences pref,int bookId)
    {
        int selectedTaraqim = 0;


        switch(bookId)
        {
            case 1:
                selectedTaraqim = pref.getInt(Constants.book1Tarqeem,1);
                break;
            case 2:
                selectedTaraqim = pref.getInt(Constants.book2Tarqeem,1);
                break;
            case 3:
                selectedTaraqim = pref.getInt(Constants.book3Tarqeem,1);
                break;
            case 4:
                selectedTaraqim = pref.getInt(Constants.book4Tarqeem,1);
                break;
            case 5:
                selectedTaraqim = pref.getInt(Constants.book5Tarqeem,1);
                break;
            case 6:
                selectedTaraqim = pref.getInt(Constants.book6Tarqeem,1);
                break;
        }


        Log.d("tryTarqeem",selectedTaraqim+"  t");
        String tarqimName = DBHelper.HadeesEntry.HadeesNumberTTwo;
        int s = selectedTaraqim;
        switch(s)
        {
            case 0:
                return DBHelper.HadeesEntry.HADEES_NO;
            case 1:
                return DBHelper.HadeesEntry.HadeesNumberTOne;

            case 2:
                return DBHelper.HadeesEntry.HadeesNumberTTwo;

            case 3:
                return DBHelper.HadeesEntry.HadeesNumberTThree;

            case 4:
                return DBHelper.HadeesEntry.HadeesNumberTFour;

            case 5:
                return DBHelper.HadeesEntry.HadeesNumberTFive;

            case 6:
                return DBHelper.HadeesEntry.HadeesNumberTSix;

            case 7:
                return DBHelper.HadeesEntry.HadeesNumberTSeven;

            case 8:
                return DBHelper.HadeesEntry.HadeesNumberTEight;

            case 9:
                return DBHelper.HadeesEntry.HadeesNumberTNine;

            case 10:
                Log.d("tryTar","there");
                return DBHelper.HadeesEntry.HadeesNumberTTen;

        }

        return  tarqimName;
    }

    public static String getTaraqimForURL(SharedPreferences pref,int bookId)
    {
        int selectedTaraqim = 0;


        switch(bookId)
        {
            case 1:
                selectedTaraqim = pref.getInt(Constants.book1Tarqeem,0);
                break;
            case 2:
                selectedTaraqim = pref.getInt(Constants.book2Tarqeem,0);
                break;
            case 3:
                selectedTaraqim = pref.getInt(Constants.book3Tarqeem,0);
                break;
            case 4:
                selectedTaraqim = pref.getInt(Constants.book4Tarqeem,0);
                break;
            case 5:
                selectedTaraqim = pref.getInt(Constants.book5Tarqeem,0);
                break;
            case 6:
                selectedTaraqim = pref.getInt(Constants.book6Tarqeem,0);
                break;
        }


        Log.d("tryTarqeem",selectedTaraqim+"  t");
        String tarqimName = DBHelper.HadeesEntry.HadeesNumberTTwo;
        int s = selectedTaraqim;
        switch(s)
        {
            case 0:
                return "T1";
            case 1:
                return "T2";

            case 2:
                return "T3";

            case 3:
                return "T4";

            case 4:
                return "T5";

            case 5:
                return "T6";

            case 6:
                return "T7";

            case 7:
                return "T8";

            case 8:
                return "T9";

            case 9:
                return "T10";



        }

        return  "T1";
    }


    public static boolean[] allowedBooksInsearch(SharedPreferences pref)
    {
        boolean[]arr = new boolean[6];
        for(int i = 0;i<6;i++)
        {
         arr[i] = false;
        }
        if(pref.getBoolean(Constants.book1,true))
        {
            arr[0] = true;
        }

        if(pref.getBoolean(Constants.book2,true))
        {
            arr[1] = true;
        }
        if(pref.getBoolean(Constants.book3,true))
        {
            arr[2] = true;
        }
        if(pref.getBoolean(Constants.book4,true))
        {
            arr[3] = true;
        }
        if(pref.getBoolean(Constants.book5,true))
        {
            arr[4] = true;
        }
        if(pref.getBoolean(Constants.book6,true))
        {
            arr[5] = true;
        }

        for(int i = 0;i<6;i++)
        {
            Log.d("tryAllowedBooks","i = "+arr[i]);
        }
        return arr;

    }

    public static String AppendSearchQueryForBooks(SharedPreferences pref,String query)
    {
        boolean flag = false;
        if(pref.getBoolean(Constants.book1,true))
        {
            flag = true;
            query+=" m."+ DBHelper.MasadaEntry.MA_ID+" = "+1;
        }
        if(pref.getBoolean(Constants.book2,true))
        {
            if(flag) {
                query += " OR m." + DBHelper.MasadaEntry.MA_ID + " = " + 2;
            }else
            {
                query += " m." + DBHelper.MasadaEntry.MA_ID + " = " + 2;
            }
            flag = true;
        }
        if(pref.getBoolean(Constants.book3,true))
        {
            if(flag) {
                query += " OR m." + DBHelper.MasadaEntry.MA_ID + " = " + 3;
            }else
            {
                query += " m." + DBHelper.MasadaEntry.MA_ID + " = " + 3;
            }
            flag = true;
        }
        if(pref.getBoolean(Constants.book4,true))
        {
            if(flag) {
                query += " OR m." + DBHelper.MasadaEntry.MA_ID + " = " + 4;
            }else
            {
                query += " m." + DBHelper.MasadaEntry.MA_ID + " = " + 4;
            }
            flag = true;
        }
        if(pref.getBoolean(Constants.book5,true))
        {
            if(flag) {
                query += " OR m." + DBHelper.MasadaEntry.MA_ID + " = " + 5;
            }else
            {
                query += " m." + DBHelper.MasadaEntry.MA_ID + " = " + 5;
            }
            flag = true;
        }
        if(pref.getBoolean(Constants.book6,true))
        {
            Log.d("tryQUpdate","there");
            if(flag) {
                query += " OR m." + DBHelper.MasadaEntry.MA_ID + " = " + 6;
            }else
            {
                query += " m." + DBHelper.MasadaEntry.MA_ID + " = " + 6;
            }
            flag = true;
        }
        Log.d("tryQUpdate","reached"+query);
        return  query;
    }

    public static boolean isAnyBookSelected(SharedPreferences pref)
    {
        boolean flag = false;
        if(pref.getBoolean(Constants.book1,true))
        {
            flag = true;
        }
        if(pref.getBoolean(Constants.book2,true))
        {
            flag = true;
        }
        if(pref.getBoolean(Constants.book3,true))
        {
            flag = true;
        }
        if(pref.getBoolean(Constants.book4,true))
        {
            flag = true;
        }
        if(pref.getBoolean(Constants.book5,true))
        {
            flag = true;
        }
        if(pref.getBoolean(Constants.book6,true))
        {

            flag = true;
        }
        //Log.d("tryQUpdate","reached"+query);
        return  flag;
    }


    public static HadeesDataModel fetchHadeesWithMasadir(int hadeesNo, int masadirId, Context context)
    {

        String query = "SELECT * from " +
                    DBHelper.HadeesEntry.TABLE_NAME + " AS h INNER JOIN " + DBHelper.AbwabEntry.TABLE_NAME + " AS b " +
                    "ON h." + DBHelper.HadeesEntry.BAB_ID + " = b." + DBHelper.AbwabEntry.ID +
                    " INNER JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                    "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                    " INNER JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                    "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                    " WHERE h." + DBHelper.HadeesEntry.HADEES_NO +" = "+hadeesNo+
                    " AND m." + DBHelper.MasadaEntry.MA_ID +" = "+masadirId;

        //query.concat(hadeesNo+"");
        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(context);

        // Gets the data repository in write mode
            SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",hadeesNo+"     "+cursor1.getCount()+"    "+query);
        HadeesDataModel model2 =  populateModel(cursor1);
        hadeesDBHelper.close();
        return  model2;

    }

    public static HadeesDataModel populateModel(Cursor cursor1)
    {
        HadeesDataModel model = null;
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            model = new HadeesDataModel();
            model.setMasadirNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));
            model.setKutubNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_URDU)));
            model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_ARABIC)));
            model.setBabNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU)));
            model.setBabNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC)));
            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));


            //TODO taraqeem
            model.setHadeesNumberTOne(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTOne)));
            model.setHadeesNumberTTwo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTwo)));
            model.setHadeesNumberTThree(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTThree)));
            model.setHadeesNumberTFour(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFour)));
            model.setHadeesNumberTFive(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFive)));
            model.setHadeesNumberTSix(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSix)));
            model.setHadeesNumberTSeven(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSeven)));
            model.setHadeesNumberTEight(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTEight)));
            model.setHadeesNumberTNine(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTNine)));
            model.setHadeesNumberTTen(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTen)));

            //TODO tarajum
            model.setHadithUrduText(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduText)));
            model.setHadithUrduTextOne(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextOne)));
            model.setHadithUrduTextTwo(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextTwo)));
            model.setHadithUrduTextThree(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextThree)));
            model.setHadithUrduTextFour(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextFour)));
            model.setHadithUrduTextFive(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextFive)));
            model.setHadithUrduTextSix(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextSix)));
            model.setHadithUrduTextSeven(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextSeven)));
            model.setHadithUrduTextEight(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextEight)));
            model.setHadithUrduTextNine(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextNine)));
            model.setHadithUrduTextTen(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduTextTen)));

            //TODO hasia
            model.setHadithHashiaTextOne(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextOne)));
            model.setHadithHashiaTextTwo(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextTwo)));
            model.setHadithHashiaTextThree(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextThree)));
            model.setHadithHashiaTextFour(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextFour)));
            model.setHadithHashiaTextFive(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextFive)));
            model.setHadithHashiaTextSix(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextSix)));
            model.setHadithHashiaTextSeven(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextSeven)));
            model.setHadithHashiaTextEight(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextEight)));
            model.setHadithHashiaTextNine(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextNine)));
            model.setHadithHashiaTextTen(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHashiaTextThree)));

            //TODO hukam
            model.setHadithHukamAjmaliOne(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliOne)));
            model.setHadithHukamAjmaliTwo(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliTwo)));
            model.setHadithHukamAjmaliThree(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliThree)));
            model.setHadithHukamAjmaliFour(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliFour)));
            model.setHadithHukamAjmaliFive(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliFive)));
            model.setHadithHukamAjmaliSix(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliSix)));
            model.setHadithHukamAjmaliSeven(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliSeven)));
            model.setHadithHukamAjmaliEight(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliEight)));
            model.setHadithHukamAjmaliNine(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliNine)));
            model.setHadithHukamAjmaliTen(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmaliTen)));

            model.setHadithHukamTafseeli(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmali)));
            model.setHadessHasiaText(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADESS_HASHIA_TEXT)));
            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.MASADIR_ID)));
            model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)));
            model.setHadithTypeAtraaf(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithTypeAtraaf)));

            model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));

        }

        return  model;
    }

    public static String removeParagraphTag(String text)
    {
        if(text != null && text.length()>7 &&text.startsWith("<p>")) {
            text = text.substring(3, text.length() - 4);
        }
       return text;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup)
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (int) (1.5*(totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))));
        listView.setLayoutParams(params);
    }


    public static int searchTopicForChilds(Context context,int parentId)
    {

        String query = "";
        query = "SELECT * FROM "+ DBHelper.TopicEntry.TABLE_NAME+
                " WHERE "+ DBHelper.TopicEntry.PARENT_ID +" = "+parentId+
                " ORDER BY "+DBHelper.TopicEntry.SEQID;
        HadithTopicsDBHelper hadithTopicsDBHelper = new HadithTopicsDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = hadithTopicsDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        hadithTopicsDBHelper.close();
        return cursor1.getCount();
    }


    public static  HadeesDataModel fetchHadeesWithMasadir(int hadeesNo,int masadirId,boolean useDefaultTaraqim,Context context,SharedPreferences pref)
    {

        String tarqimColumName,query;
        if(useDefaultTaraqim){
            tarqimColumName = DBHelper.HadeesEntry.HADEES_NO;
        }else {
            tarqimColumName = Utilites.getTaraqimNameAndNo(pref, masadirId);
        }
        Log.d("tryTarqeemColumName",tarqimColumName);

        query = "SELECT * from " +
                DBHelper.HadeesEntry.TABLE_NAME + " AS h INNER JOIN " + DBHelper.AbwabEntry.TABLE_NAME + " AS b " +
                "ON h." + DBHelper.HadeesEntry.BAB_ID + " = b." + DBHelper.AbwabEntry.ID +
                " INNER JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " INNER JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE h." + tarqimColumName +" = "+hadeesNo+
                " AND m." + DBHelper.MasadaEntry.MA_ID +" = "+masadirId;

        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",hadeesNo+"     "+cursor1.getCount()+"    "+query);
        HadeesDataModel model2 =  populateModel(cursor1);
        hadeesDBHelper.close();
        return  model2;

    }


}
