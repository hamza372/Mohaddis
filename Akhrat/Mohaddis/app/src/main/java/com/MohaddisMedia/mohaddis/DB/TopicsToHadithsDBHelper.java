package com.MohaddisMedia.mohaddis.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class TopicsToHadithsDBHelper extends SQLiteAssetHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mohaddis";
    Context context;

    public TopicsToHadithsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public ArrayList<TopicsDataModel> findTopics(int hdeesNumber, int bookId)
    {
        ArrayList<TopicsDataModel> topicsDataModels = new ArrayList<>();
        String query = "SELECT * FROM "+ DBHelper.HadithTopicsTOAhadithEntry.TABLE_NAME+" WHERE "+
                DBHelper.HadithTopicsTOAhadithEntry.HADEESNUMBER +" = "+hdeesNumber+
                " AND "+DBHelper.HadithTopicsTOAhadithEntry.HADITHBOOKID +" = "+bookId;
        TopicsToHadithsDBHelper hadeesDBHelper = new TopicsToHadithsDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        cursor1.moveToFirst();
        for(int i =0;i<cursor1.getCount();i++)
        {

            topicsDataModels.add(findTopicNameFromTopicId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadithTopicsTOAhadithEntry.MOZUID))));
            Log.d("tryTopics",cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadithTopicsTOAhadithEntry.MOZUID))+"");
            cursor1.moveToNext();
        }
        return topicsDataModels;
    }

    public TopicsDataModel findTopicNameFromTopicId(int topicId)
    {
        String query = "SELECT * FROM "+ DBHelper.TopicEntry.TABLE_NAME+" WHERE "+ DBHelper.TopicEntry.ID +" = "+topicId;
        HadithTopicsDBHelper hadeesDBHelper = new HadithTopicsDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        cursor1.moveToFirst();
        TopicsDataModel topicsDataModel = new TopicsDataModel();
        topicsDataModel.setTopicUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU)));
        topicsDataModel.setTopicArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICARABIC)));
        topicsDataModel.setId(topicId);


        Log.d("tryTopicsSeniorTitle",findSeniorParent(topicId).getTopicUrdu());

        return topicsDataModel;
    }

    public TopicsDataModel findSeniorParent(int topicId)
    {
        String query = "SELECT * FROM "+ DBHelper.TopicEntry.TABLE_NAME+" WHERE "+ DBHelper.TopicEntry.ID +" = "+topicId;
        HadithTopicsDBHelper hadeesDBHelper = new HadithTopicsDBHelper(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        cursor1.moveToFirst();
        if(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.PARENT_ID)) != 1)
        {
            return findSeniorParent(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.PARENT_ID)));
        }
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        TopicsDataModel topicsDataModel = new TopicsDataModel();
        topicsDataModel.setTopicUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU)));
        topicsDataModel.setTopicArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICARABIC)));
        topicsDataModel.setId(topicId);

        Log.d("tryTopicsTitle",cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU))+"");

        return topicsDataModel;
    }
}
