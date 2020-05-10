package com.MohaddisMedia.mohaddis.Search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Adapters.TopicsAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.HadithTopicsDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;

public class TopicSearchResult extends AppCompatActivity {

    int parentId = 1;
    String title = "";
    TopicsAdapter topicsAdapter;
    RecyclerView topicRecycler;
    Handler handler;
    String language,searchText;
    TextView resultCounter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_topic_search_result);
        handler = new Handler();
        progressBar = findViewById(R.id.progressBar6);


        language = getIntent().getExtras().getString(Constants.LANGUAGE);;
        searchText = getIntent().getExtras().getString(Constants.SEARCHTEXT);

        Log.d("tryTopic",parentId+"");
        //TODO search result count
        resultCounter = (TextView)findViewById(R.id.textView7);

        TextView titleTv = findViewById(R.id.toolbartitletv);
        titleTv.setText(searchText);
        topicRecycler = (RecyclerView)findViewById(R.id.topicrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        topicRecycler.setLayoutManager(linearLayoutManager);


        mozuSearch(searchText);


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(TopicSearchResult.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(TopicSearchResult.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(TopicSearchResult.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(TopicSearchResult.this, Contact_us.class);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ImageView layout = findViewById(R.id.imageView13);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hadees_view_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    AsyncTask<Void,Void,Void> asyncTask;
    public void mozuSearch(final String searchText)
    {
        topicsAdapter = null;
        if(asyncTask != null)
        {
            asyncTask.cancel(true);
            asyncTask = null;
        }
        asyncTask = new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
                topicsAdapter = new TopicsAdapter(getApplicationContext(),TopicSearchResult.this);
                topicsAdapter.setLanguage(language);
                topicsAdapter.setSearchText(searchText);
                topicRecycler.setAdapter(topicsAdapter);


            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInMozuat(searchText);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        };
        asyncTask.execute();
    }

    public void searchInMozuat(String searchText)
    {
        String query;



        //query = "SELECT * from "+DBHelper.AbwabEntry.TABLE_NAME;
        query= "SELECT * from " +
                DBHelper.TopicEntry.TABLE_NAME + " AS t " +
                "WHERE t."+ DBHelper.TopicEntry.TOPICURDU+" LIKE '%"+searchText+
                "%' ORDER BY t."+ DBHelper.TopicEntry.SEQID;

        Log.d("tryTopicEntry",query);

        HadithTopicsDBHelper hadithTopicsDBHelper = new HadithTopicsDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadithTopicsDBHelper.getReadableDatabase();
        final Cursor cursor1 = db.rawQuery(query,null);
        cursor1.moveToNext();
        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                resultCounter.setText("نتائج :  "  + cursor1.getCount());
            }
        });

        //TODO update no of results textview
        for(int i =0;i<cursor1.getCount();i++)
        {


            final TopicsDataModel model = new TopicsDataModel();
            model.setTopicUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU)));
            model.setTopicArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICARABIC)));
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.ID)));
            model.setParentId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.PARENT_ID)));
            model.setSeqId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.SEQID)));
            handler.post(new Runnable() {
                @Override
                public void run() {

                    topicsAdapter.addTopic(model);
                    topicsAdapter.notifyDataSetChanged();
                }
            });
            cursor1.moveToNext();

            //Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2)+"  "+cursor1.getString(3)+"  "+cursor1.getString(4));
        }
        hadithTopicsDBHelper.close();
    }


    public void backButton(View v)
    {
        onBackPressed();
    }
}
