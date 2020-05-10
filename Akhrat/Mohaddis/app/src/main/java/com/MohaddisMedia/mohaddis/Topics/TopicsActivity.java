package com.MohaddisMedia.mohaddis.Topics;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Adapters.HadeesSearchAdapter;
import com.MohaddisMedia.mohaddis.Adapters.TopicsAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.HadithTopicsDBHelper;
import com.MohaddisMedia.mohaddis.DB.TopicsToHadithsDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

import java.util.ArrayList;

public class TopicsActivity extends AppCompatActivity {

    int parentId = 1;
    String title = "";
    TopicsAdapter topicsAdapter;
    RecyclerView topicRecycler;
    Handler handler;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_topics);
        handler = new Handler();
        progressBar = findViewById(R.id.progressBar7);


        parentId = getIntent().getExtras().getInt(Constants.Topic);
        title = getIntent().getExtras().getString(Constants.Title);

        Log.d("tryTopic",parentId+"");
        setTitle(title);
        TextView titleTv = findViewById(R.id.toolbartitletv);
        titleTv.setText(title);
        topicRecycler = (RecyclerView)findViewById(R.id.topicrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        topicRecycler.setLayoutManager(linearLayoutManager);
        topicSearch(parentId);

        final EditText searchBar = findViewById(R.id.editText2);
        ImageButton search = findViewById(R.id.imageButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchBar.getText().toString();
                if(!query.equals("")) {
                    if (Utilites.searchTopicForChilds(getApplicationContext(), parentId) != 0) {
                        mozuSearch(query);
                    } else {
                        Log.d("tryTopics", "There");
                        hadeesSearch(parentId, query);
                    }
                }else{
                    topicSearch(parentId);
                }

            }
        });


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(TopicsActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(TopicsActivity.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(TopicsActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(TopicsActivity.this, Contact_us.class);
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


    int res = 0;
    public void topicSearch(final int parentId)
    {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                topicsAdapter = new TopicsAdapter(getApplicationContext(),TopicsActivity.this);
                topicRecycler.setAdapter(topicsAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                res = searchInTopics(parentId);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if(res == 0)
                {
                    hadeesSearch(   parentId,null);

                }else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hadees_view_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public int searchInTopics(int parentId)
    {

        String query = "";
        query = "SELECT * FROM "+ DBHelper.TopicEntry.TABLE_NAME+
                " WHERE "+ DBHelper.TopicEntry.PARENT_ID +" = "+parentId+
                " ORDER BY "+DBHelper.TopicEntry.SEQID;
        HadithTopicsDBHelper hadithTopicsDBHelper = new HadithTopicsDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = hadithTopicsDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final TopicsDataModel model = new TopicsDataModel();
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.ID)));
            model.setParentId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.PARENT_ID)));
            model.setSeqId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.SEQID)));
            model.setTopicUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU)));
            model.setTopicUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.TopicEntry.TOPICURDU)));
            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    topicsAdapter.addTopic(model);
                    topicsAdapter.notifyDataSetChanged();

                }
            });
            Log.d("tryResultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        hadithTopicsDBHelper.close();
        return cursor1.getCount();
    }




    HadeesSearchAdapter hadeesSearchAdapter = null;
    public void hadeesSearch(final int topicId, final String searchedText)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                hadeesSearchAdapter = new HadeesSearchAdapter(getApplicationContext(),TopicsActivity.this);
                topicRecycler.setAdapter(hadeesSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                findAhadees(topicId,searchedText);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    public void findAhadees(int topicId,String searchedText)
    {
        String query = "SELECT * FROM "+ DBHelper.HadithTopicsTOAhadithEntry.TABLE_NAME+" WHERE "+ DBHelper.HadithTopicsTOAhadithEntry.MOZUID +" = "+topicId;
        TopicsToHadithsDBHelper hadeesDBHelper = new TopicsToHadithsDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final HadeesDataModel model = Utilites.fetchHadeesWithMasadir(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadithTopicsTOAhadithEntry.HADEESNUMBER)),cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadithTopicsTOAhadithEntry.HADITHBOOKID)),getApplicationContext());

           if(searchedText == null) { //TODO updating recyclerview
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       hadeesSearchAdapter.addHadees(model);
                       hadeesSearchAdapter.notifyDataSetChanged();
                   }
               });
           }else{
               if(model != null && model.getHadithUrduText().contains(searchedText)){
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           hadeesSearchAdapter.addHadees(model);
                           hadeesSearchAdapter.notifyDataSetChanged();
                       }
                   });
               }
           }
        }
        hadeesDBHelper.close();
    }


    //TODO mozu search


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
                topicsAdapter = new TopicsAdapter(getApplicationContext(),TopicsActivity.this);
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
        Cursor cursor1 = db.rawQuery(query,null);

        //TODO update no of results textview

        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();

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


            //Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2)+"  "+cursor1.getString(3)+"  "+cursor1.getString(4));
        }
        hadithTopicsDBHelper.close();
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void showSearchedAhadees(String searchedText)
    {
        Log.d("trTopicsTest","called ");
        ArrayList<HadeesDataModel> arrayList = hadeesSearchAdapter.getDataList();
        hadeesSearchAdapter.clear();
        Log.d("trTopicsTest","size = "+arrayList.size());
        hadeesSearchAdapter.notifyDataSetChanged();
        for(int i=0;i<arrayList.size();i++)
        {
            Log.d("trTopicsTest",arrayList.get(i).getTextHadeesUrdu());
            if(arrayList.get(i).getHadithUrduText().contains(searchedText))
            {
                hadeesSearchAdapter.addHadees(arrayList.get(i));
                hadeesSearchAdapter.notifyDataSetChanged();
            }
        }
    }
    public void backButton(View v)
    {
        onBackPressed();
    }
}
