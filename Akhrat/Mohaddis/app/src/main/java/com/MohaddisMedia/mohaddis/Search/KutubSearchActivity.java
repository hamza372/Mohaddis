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

import com.MohaddisMedia.mohaddis.Adapters.KutubSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.KutubDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.KutubDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

public class KutubSearchActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    RecyclerView searchResultRecycler;
    KutubSearchAdapter kutubSearchAdapter;


    Handler handler;

    String query;
    int masdirId = 0;

    //TODO search bar code
    EditText searchView;

    String searchText = "";
    String language = "";

    SharedPreferences pref;

    String chosenLanguage = "";

    TextView resultCounter;
    ProgressBar progressBar;
    int currentItems,totalItems,scrolledOutItems;
    Boolean isScrolling = false;
    LinearLayoutManager linearLayoutManager;
    int itemsOnPage = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_kutub_search);
        progressBar = findViewById(R.id.progressBar4);
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        setTitle("کتب");
        Bundle b = getIntent().getExtras();
        language = b.getString(Constants.LANGUAGE);
        searchText = b.getString(Constants.SEARCHTEXT);
        resultCounter = (TextView)findViewById(R.id.textView7);

        //masadirDataModel = (MasadirDataModel) b.get(Constants.MASADIR);
        handler = new Handler();
        searchResultRecycler = (RecyclerView)findViewById(R.id.kutubrecycler);
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
                                for(int i =0;i<itemsOnPage;i++)
                                {
                                    cursor1.moveToNext();
                                    final KutubDataModel model = new KutubDataModel();
                                    model.setMasadirNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));
                                    model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_ARABIC)));
                                    model.setKutubNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_URDU)));
                                    if(model.getKutubNameUrdu() != null && model.getKutubNameUrdu().length()>7 && model.getKutubNameUrdu().startsWith("<p>")) {
                                        model.setKutubNameUrdu(model.getKutubNameUrdu().substring(3, model.getKutubNameUrdu().length() - 4));
                                    }
                                    model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_ARABIC)));
                                    if(model.getKutubNameArabic() != null && model.getKutubNameArabic().length()>7 && model.getKutubNameArabic().startsWith("<p>"))
                                    {
                                        model.setKutubNameArabic(model.getKutubNameArabic().substring(3,model.getKutubNameArabic().length()-4));
                                    }
                                    model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.ID)));
                                    model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));
                                    model.setKitab_hiddenId(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.HIDDEN_ID)));
                                    //TODO updating recyclerview
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            kutubSearchAdapter.addKitab(model);
                                            kutubSearchAdapter.notifyDataSetChanged();

                                        }
                                    });
                                    Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
                                }

                            }
                        }).start();
                    }
                }
            }
        });

        //TODO masadirName
        TextView masdirNTV = findViewById(R.id.toolbartitlek);
        masdirNTV.setText(searchText);


        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.KutubEntry.KITAAB_NAME_URDU;
        }else
        {
            chosenLanguage = DBHelper.KutubEntry.KITAAB_NAME_ARABIC;
        }
        query= "SELECT * from " +
                DBHelper.KutubEntry.TABLE_NAME + " AS k "+
                " INNER JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE k." + chosenLanguage +" LIKE '%"+searchText +
                "%'  AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;
        if(Utilites.isAnyBookSelected(pref)) {
            kutubSearch(query);
        }else
        {
            progressBar.setVisibility(View.GONE);
        }



        //TODO searchview activity
        searchView = findViewById(R.id.editText2);
        ImageButton searchButton = findViewById(R.id.imageButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = searchView.getText().toString();
                query = "SELECT * from " +
                        DBHelper.KutubEntry.TABLE_NAME +" As K "+
                        " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                        "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                        " WHERE h." + chosenLanguage +" LIKE '%"+ q +
                        "%' AND (";

                query = Utilites.AppendSearchQueryForBooks(pref,query);
                query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;
                Log.d("tryQuery",query);
                kutubSearchAdapter = null;
                kutubSearch(query);

                Log.d("tryQuery",query);
            }
        });


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(KutubSearchActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(KutubSearchActivity.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(KutubSearchActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(KutubSearchActivity.this, Contact_us.class);
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

        final ImageView bookChose = findViewById(R.id.imageView40);
        bookChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseBook();
            }
        });

    }

    public void backButton()
    {
        onBackPressed();
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

    public void kutubSearch(final String query2)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                kutubSearchAdapter = new KutubSearchAdapter(getApplicationContext(),KutubSearchActivity.this,searchText,chosenLanguage,false);
                kutubSearchAdapter.setLanguage(language);
                searchResultRecycler.setAdapter(kutubSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                searchInKutub(query2);
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
    public void searchInKutub(String query2)
    {

        KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = kutubDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query2,null);
        cursor1.moveToNext();
        handler.post(new Runnable() {
            @Override
            public void run() {
                resultCounter.setText("نتائج :  "  + cursor1.getCount());
            }
        });
//        Log.d("trySearchResult",cursor1.getCount()+"   "+query2);
        int firstTimeItems = 30;
        if(cursor1.getCount()<30){
            firstTimeItems = cursor1.getCount();
        }
        for(int i =0;i<firstTimeItems;i++)
        {

            final KutubDataModel model = new KutubDataModel();
            model.setMasadirNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));
            model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_ARABIC)));
            model.setKutubNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_URDU)));
            if(model.getKutubNameUrdu() != null && model.getKutubNameUrdu().length()>7 && model.getKutubNameUrdu().startsWith("<p>")) {
                model.setKutubNameUrdu(model.getKutubNameUrdu().substring(3, model.getKutubNameUrdu().length() - 4));
            }
            model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_ARABIC)));
            if(model.getKutubNameArabic() != null && model.getKutubNameArabic().length()>7 && model.getKutubNameArabic().startsWith("<p>"))
            {
                model.setKutubNameArabic(model.getKutubNameArabic().substring(3,model.getKutubNameArabic().length()-4));
            }
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.ID)));
            model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));
            model.setKitab_hiddenId(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.HIDDEN_ID)));
            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    kutubSearchAdapter.addKitab(model);
                    kutubSearchAdapter.notifyDataSetChanged();

                }
            });
            cursor1.moveToNext();
//            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }

    }


    public void backBtn(View v)
    {
        onBackPressed();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d("tryAction","dismidded");
       // kutubSearchAdapter = null;
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
            kutubSearch(query);
        }else
        {
            resultCounter.setText("نتائج :  " +0);
            kutubSearchAdapter = null;
            searchResultRecycler.setAdapter(kutubSearchAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }
}
