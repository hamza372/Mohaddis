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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Adapters.HadeesSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AHadeesDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.AbwabDataModel;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

public class HadeesSearchResult extends AppCompatActivity implements DialogInterface.OnDismissListener{

    RecyclerView searchResultRecycler;
    HadeesSearchAdapter hadeesSearchAdapter;
    String language;
    String chosenLanguage;
    String searchText;

    AbwabDataModel abwabDataModel;
    private Handler handler;
    Cursor cursor1;

    int no_of_search_Results = 0;
    ProgressBar progressBar;

    SharedPreferences pref;
    TextView resultCounter;

    int currentItems,totalItems,scrolledOutItems;
    Boolean isScrolling = false;
    LinearLayoutManager linearLayoutManager;
    int itemsOnPage = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_hadees_search_result);
        pref = getSharedPreferences(Constants.Package_Name,MODE_PRIVATE);
        handler = new Handler();


        Bundle b = getIntent().getExtras();
        language = b.getString(Constants.LANGUAGE);
        searchText = b.getString(Constants.SEARCHTEXT);

        //TODO search result count
        resultCounter = (TextView)findViewById(R.id.textView7);
        progressBar = findViewById(R.id.progressBar8);

        TextView title = findViewById(R.id.toolbartitlek2);
        title.setText(searchText);

        searchResultRecycler = (RecyclerView)findViewById(R.id.brecycler);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        searchResultRecycler.setLayoutManager(linearLayoutManager);
        if(Utilites.isAnyBookSelected(pref)) {
            hadeesSearch();
        }else{
            progressBar.setVisibility(View.GONE);
        }
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
                                for(int i =0;i<itemsOnPage;i++)
                                {
                                    Log.d("tryCursorValue","before cursor closed");
                                    Log.d("tryCursorValue",cursor1.isClosed()+"");
                                    if(!cursor1.isClosed()) {
                                        Log.d("tryCursorNew","inside not closed) ");
                                        if(cursor1.moveToNext()) {
                                            final HadeesDataModel model = new HadeesDataModel();
                                            Log.d("tryCursorValue", cursor1.isNull(i) + "  " + i + "   " + totalItems);
                                            model.setBabNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU)));
                                            model.setBabNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC)));
                                            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
                                            model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));
                                            model.setHadeesNo(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
                                            model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.BAB_ID)));
                                            model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));

                                            //TODO taraqeem
                                            model.setHadeesNumberTOne(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTOne)));
                                            model.setHadeesNumberTTwo(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTwo)));
                                            model.setHadeesNumberTThree(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTThree)));
                                            model.setHadeesNumberTFour(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFour)));
                                            model.setHadeesNumberTFive(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFive)));
                                            model.setHadeesNumberTSix(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSix)));
                                            model.setHadeesNumberTSeven(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSeven)));
                                            model.setHadeesNumberTEight(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTEight)));
                                            model.setHadeesNumberTNine(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTNine)));
                                            model.setHadeesNumberTTen(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTen)));


                                            //TODO updating recyclerview
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    hadeesSearchAdapter.addHadees(model);
                                                    hadeesSearchAdapter.notifyDataSetChanged();
                                                    progressBar.setVisibility(View.GONE);
                                                    //  resultCounter.setText("نتائج :  "  + cursor1.getCount());
                                                }
                                            });
                                        }
                                    }else
                                    {
                                        break;
                                    }

                                }

                            }
                        }).start();
                    }
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
                    Intent intent = new Intent(HadeesSearchResult.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(HadeesSearchResult.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(HadeesSearchResult.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(HadeesSearchResult.this, Contact_us.class);
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

        final ImageView bookChose = findViewById(R.id.imageView42);
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

    AsyncTask<Void,Void,Void> asyncTask;

   // AhadeesCursorAdapter ahadeesCursorAdapter;
    public void HadeesSearch2()
    {
        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_URDU;
        }else
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_ARABIC;
        }



        query = "SELECT "
                +DBHelper.HadeesEntry.HADEES_TEXT_URDU+","+
                DBHelper.HadeesEntry.HADEES_TEXT_ARABIC+","+
                DBHelper.HadeesEntry.BAB_ID+","+
                DBHelper.AbwabEntry.BAB_NAME_URDU+","+
                DBHelper.AbwabEntry.BAB_NAME_ARABIC+","+

                DBHelper.HadeesEntry.HadeesNumberTOne+","+
                DBHelper.HadeesEntry.HadeesNumberTTwo+","+
                DBHelper.HadeesEntry.HadeesNumberTThree+","+
                DBHelper.HadeesEntry.HadeesNumberTFour+","+
                DBHelper.HadeesEntry.HadeesNumberTFive+","+
                DBHelper.HadeesEntry.HadeesNumberTSix+","+
                DBHelper.HadeesEntry.HadeesNumberTSeven+","+
                DBHelper.HadeesEntry.HadeesNumberTEight+","+
                DBHelper.HadeesEntry.HadeesNumberTNine+","+
                DBHelper.HadeesEntry.HadeesNumberTTen+","+

                DBHelper.MasadaEntry.MA_ID+","+
                DBHelper.HadeesEntry.HADEES_NO
                +" from " +
                DBHelper.HadeesEntry.TABLE_NAME + " AS h LEFT JOIN " + DBHelper.AbwabEntry.TABLE_NAME + " AS b " +
                "ON h." + DBHelper.HadeesEntry.BAB_ID + " = b." + DBHelper.AbwabEntry.ID +
                " LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE h." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;



        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query,null);
        progressBar.setVisibility(View.VISIBLE);
        searchResultRecycler.setAdapter(hadeesSearchAdapter);
    }
    public void hadeesSearch()
    {
        asyncTask = new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tryTask","pre");
                progressBar.setVisibility(View.VISIBLE);
                hadeesSearchAdapter = new HadeesSearchAdapter(getApplicationContext(),searchText,HadeesSearchResult.this);
                hadeesSearchAdapter.setLanguage(language);
                searchResultRecycler.setAdapter(hadeesSearchAdapter);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Log.d("tryTask","do in back");
                searchDBforAhadess();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("tryTask","post");
                progressBar.setVisibility(View.GONE);

            }
        }.execute();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    String query = "";
    int totalSearchItems = 0;
    public void searchDBforAhadess()
    {


        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_URDU;
        }else
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_ARABIC;
        }

        query = "SELECT "
                +DBHelper.HadeesEntry.HADEES_TEXT_URDU+","+
                DBHelper.HadeesEntry.HADEES_TEXT_ARABIC+","+
                DBHelper.HadeesEntry.BAB_ID+","+
                DBHelper.AbwabEntry.BAB_NAME_URDU+","+
                DBHelper.AbwabEntry.BAB_NAME_ARABIC+","+

                DBHelper.HadeesEntry.HadeesNumberTOne+","+
                DBHelper.HadeesEntry.HadeesNumberTTwo+","+
                DBHelper.HadeesEntry.HadeesNumberTThree+","+
                DBHelper.HadeesEntry.HadeesNumberTFour+","+
                DBHelper.HadeesEntry.HadeesNumberTFive+","+
                DBHelper.HadeesEntry.HadeesNumberTSix+","+
                DBHelper.HadeesEntry.HadeesNumberTSeven+","+
                DBHelper.HadeesEntry.HadeesNumberTEight+","+
                DBHelper.HadeesEntry.HadeesNumberTNine+","+
                DBHelper.HadeesEntry.HadeesNumberTTen+","+

                DBHelper.MasadaEntry.MA_ID+","+
                DBHelper.HadeesEntry.HADEES_NO+" from " +
                DBHelper.HadeesEntry.TABLE_NAME + " AS h LEFT JOIN " + DBHelper.AbwabEntry.TABLE_NAME + " AS b " +
                "ON h." + DBHelper.HadeesEntry.BAB_ID + " = b." + DBHelper.AbwabEntry.ID +
                " LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE h." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";

        String queryTemp = "SELECT * from " +
                DBHelper.HadeesEntry.TABLE_NAME + " AS h LEFT JOIN " + DBHelper.AbwabEntry.TABLE_NAME + " AS b " +
                "ON h." + DBHelper.HadeesEntry.BAB_ID + " = b." + DBHelper.AbwabEntry.ID +
                " LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE h." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";

        queryTemp = Utilites.AppendSearchQueryForBooks(pref,queryTemp);
        queryTemp+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;

        hadeesSearchAdapter.setQuery(queryTemp);
        hadeesSearchAdapter.setLanguage(language);

        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);

        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                resultCounter.setText("نتائج :  "  + cursor1.getCount());
            }
        });

        int firstTimeItems = 30;
        if(cursor1.getCount()<30){
            firstTimeItems = cursor1.getCount();
        }
        totalSearchItems = cursor1.getCount();
        for(int i =0;i<firstTimeItems;i++)
        {
            if(!cursor1.isClosed()) {
                cursor1.moveToNext();
                final HadeesDataModel model = new HadeesDataModel();
                model.setBabNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU)));
                model.setBabNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC)));
                model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
                model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));
                model.setHadeesNo(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
                model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.BAB_ID)));
                model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));

                //TODO taraqeem
                model.setHadeesNumberTOne(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTOne)));
                model.setHadeesNumberTTwo(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTwo)));
                model.setHadeesNumberTThree(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTThree)));
                model.setHadeesNumberTFour(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFour)));
                model.setHadeesNumberTFive(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTFive)));
                model.setHadeesNumberTSix(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSix)));
                model.setHadeesNumberTSeven(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTSeven)));
                model.setHadeesNumberTEight(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTEight)));
                model.setHadeesNumberTNine(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTNine)));
                model.setHadeesNumberTTen(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadeesNumberTTen)));


                //TODO updating recyclerview
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hadeesSearchAdapter.addHadees(model);
                        hadeesSearchAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                      //  resultCounter.setText("نتائج :  "  + cursor1.getCount());
                    }
                });
            }else
            {
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public  void backBtn(View v)
    {
        onBackPressed();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d("tryAction","dismidded");
        query= "SELECT * from " +
                DBHelper.KutubEntry.TABLE_NAME + " AS k "+
                " INNER JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE k." + chosenLanguage +" LIKE '%"+searchText +
                "%'  AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;
        Log.d("tryAction",Utilites.isAnyBookSelected(pref)+"");
        if(Utilites.isAnyBookSelected(pref)) {
            hadeesSearch();
        }else
        {
            resultCounter.setText("نتائج :  "  + 0);
            hadeesSearchAdapter = null;
            searchResultRecycler.setAdapter(hadeesSearchAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }
}
