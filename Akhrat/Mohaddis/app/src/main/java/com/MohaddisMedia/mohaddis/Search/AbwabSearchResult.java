package com.MohaddisMedia.mohaddis.Search;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Adapters.BabSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AbwabDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.AbwabDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

public class AbwabSearchResult extends AppCompatActivity implements DialogInterface.OnDismissListener{

    RecyclerView searchResultRecycler;
    BabSearchAdapter abwabSearchAdapter;

    Handler handler;

    ProgressBar progressBar;

    String query;
    //TODO search bar code
    EditText searchView;
    ImageButton searchButton;

    String searchText = "";
    String language = "";

    SharedPreferences pref;

    String chosenLanguage = "";
    TextView resultCounter;
    int currentItems,totalItems,scrolledOutItems;
    Boolean isScrolling = false;
    LinearLayoutManager linearLayoutManager;
    int itemsOnPage = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_abwab_search_result);
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        setTitle("ابواب");
        Bundle b = getIntent().getExtras();
        language = b.getString(Constants.LANGUAGE);
        searchText = b.getString(Constants.SEARCHTEXT);

        progressBar = findViewById(R.id.progressBar5);
        handler = new Handler();
        searchResultRecycler = (RecyclerView)findViewById(R.id.abwabrecycler);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        searchResultRecycler.setLayoutManager(linearLayoutManager);
        searchResultRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrolledOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrolledOutItems ) == totalItems){
                    isScrolling = false;
                    if(totalItems<cursor1.getCount()){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(cursor1.getCount() - totalItems<15){
                                    itemsOnPage = cursor1.getCount() - totalItems;
                                }
                                Log.d("tryCursorSize",itemsOnPage+"  "+cursor1.getCount());
                                for(int i =0;i<itemsOnPage;i++)
                                {
                                    if(cursor1.moveToNext()) {
                                        final AbwabDataModel model = new AbwabDataModel();
                                        model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));
                                        model.setBabNameUrdu(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU))));
                                        model.setBabNameArabic(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC))));
                                        Log.d("tryBAab", cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)) + "");
                                        model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)));
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                abwabSearchAdapter.addBab(model);
                                                abwabSearchAdapter.notifyDataSetChanged();

                                            }
                                        });
                                    }
                                }

                            }
                        }).start();
                    }
                }
            }
        });


        resultCounter = (TextView)findViewById(R.id.textView7);

        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.AbwabEntry.BAB_NAME_URDU;
        }else
        {
            chosenLanguage = DBHelper.AbwabEntry.BAB_NAME_ARABIC;
        }

        query = "SELECT * from "+DBHelper.AbwabEntry.TABLE_NAME;
        query= "SELECT m."+ DBHelper.MasadaEntry.MA_ID+",b."+ DBHelper.AbwabEntry.BAB_NAME_URDU+",b."+ DBHelper.AbwabEntry.BAB_NAME_ARABIC+",b."+ DBHelper.AbwabEntry.ID +" from "+
                DBHelper.AbwabEntry.TABLE_NAME + " AS b "+
                " LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE b." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;


        if(Utilites.isAnyBookSelected(pref)) {
            babSearch(query);
        }else{
            progressBar.setVisibility(View.GONE);
        }

        TextView masadirTV = (TextView)findViewById(R.id.toolbartitleb);

        masadirTV.setText(searchText);

        //TODO searchview activity
        searchView = findViewById(R.id.editText2b);
        searchButton = findViewById(R.id.imageButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query2 = searchView.getText().toString();


                query = "SELECT "+DBHelper.MasadaEntry.MA_ID+","+DBHelper.AbwabEntry.BAB_NAME_URDU+","+DBHelper.AbwabEntry.BAB_NAME_ARABIC+","+DBHelper.AbwabEntry.ID+","+DBHelper.AbwabEntry.HIDDEN_ID+" from " +
                        DBHelper.AbwabEntry.TABLE_NAME  +
                        " AS b LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                        "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                        " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                        "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                        " WHERE h." + chosenLanguage +" LIKE '%"+ searchText +
                        "%' AND (";

                query = Utilites.AppendSearchQueryForBooks(pref,query);
                query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;

                abwabSearchAdapter = null;
                babSearch(query);
            }
        });

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.properties) {
                    Intent intent = new Intent(AbwabSearchResult.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(AbwabSearchResult.this, About_us.class);
                    startActivity(intent);
                }else if (id == R.id.coopertion) {
                    Intent intent = new Intent(AbwabSearchResult.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(AbwabSearchResult.this, Contact_us.class);
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

        final ImageView bookChose = findViewById(R.id.imageView41);
        bookChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseBook();
            }
        });



    }

    public void choseBook()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChoseBookDialgue choseBookDialgue = new ChoseBookDialgue();
        choseBookDialgue.show(ft,"dialogue");
    }

    public void backButton(View v)
    {
        onBackPressed();
    }

    public void babSearch(final String query2)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                abwabSearchAdapter = new BabSearchAdapter(getApplicationContext(),AbwabSearchResult.this,searchText,chosenLanguage,true);
                abwabSearchAdapter.setLanguage(language);
                searchResultRecycler.setAdapter(abwabSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInAbwab(query2);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }


    Cursor cursor1;
    public void searchInAbwab(String query2)
    {
        AbwabDBHelper abwabDBHelper = new AbwabDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = abwabDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query2,null);
        cursor1.moveToNext();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(cursor1 != null) {
                    resultCounter.setText("نتائج :  " + cursor1.getCount());
                }
            }
        });
   //     Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        int firstTimeItems = 30;
        if(cursor1.getCount()<30){
            firstTimeItems = cursor1.getCount();
        }
        for(int i =0;i<firstTimeItems;i++)
        {

            final AbwabDataModel model = new AbwabDataModel();

            model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));
            model.setBabNameUrdu(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU))));
            model.setBabNameArabic(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC))));
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)));
            Log.d("tryBabId",model.getId()+"");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    abwabSearchAdapter.addBab(model);
                    abwabSearchAdapter.notifyDataSetChanged();
                }
            });
            cursor1.moveToNext();

       }
        //abwabDBHelper.close();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d("tryAction","dismidded");
        // kutubSearchAdapter = null;
        query = "SELECT * from " +
                DBHelper.AbwabEntry.TABLE_NAME  +
                " AS b LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE b." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;
        Log.d("tryAction",Utilites.isAnyBookSelected(pref)+"");
        if(Utilites.isAnyBookSelected(pref)) {
            babSearch(query);
        }else
        {
            resultCounter.setText("نتائج :  " +0);
            abwabSearchAdapter = null;
            searchResultRecycler.setAdapter(abwabSearchAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }
}
