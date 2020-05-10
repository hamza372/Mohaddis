package com.MohaddisMedia.mohaddis.Search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.mohaddis.Adapters.BabSearchAdapter;
import com.MohaddisMedia.mohaddis.Adapters.HadeesSearchAdapter;
import com.MohaddisMedia.mohaddis.Adapters.KutubSearchAdapter;
import com.MohaddisMedia.mohaddis.Adapters.TopicsAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AHadeesDBHelper;
import com.MohaddisMedia.mohaddis.DB.AbwabDBHelper;
import com.MohaddisMedia.mohaddis.DB.HadithTopicsDBHelper;
import com.MohaddisMedia.mohaddis.DB.KutubDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.AbwabDataModel;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.DataModels.KutubDataModel;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.MainActivity;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    String searchText;
    String language;
    String catagory;
    String chosenLanguage;

    ArrayList<HadeesDataModel> ahadees;
    Handler handler;

    RecyclerView searchResultRecycler;
    HadeesSearchAdapter hadeesSearchAdapter;
    KutubSearchAdapter kutubearchAdapter;
    BabSearchAdapter abwabearchAdapter;

    Spinner languageSpinner;
    Spinner catagorySpinner;
    EditText searchBox;
    Cursor cursor1;
    AsyncTask<Void,Void,Void> asyncTask;

    int no_of_search_Results = 0;
    TextView searchResultCountTV ;

    boolean isSearchStarted = false;

    //TODO array to store which books are allowed in search
    boolean []allowedBooks = new boolean[6];

    SharedPreferences pref;
    ImageView bookChoser;
    ArrayAdapter catagoryAdapter;
    LinearLayout searchBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_search_result);
        handler = new Handler();
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);

        //TODO searchbox code
        searchBox =  findViewById(R.id.editText3);
        bookChoser = findViewById(R.id.imageView39);

        searchBarLayout = findViewById(R.id.linearLayout7);
        //TODO search button code
        final ConstraintLayout searchButton = findViewById(R.id.searchbuttonlayout);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch2(searchBox.getText().toString());
            }
        });
        TextView tv = findViewById(R.id.textView14);
        pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            tv.setTextSize( pref.getFloat(Constants.FONT,-1));
            searchBox.setTextSize(pref.getFloat(Constants.FONT,-1));

        }



        language = Constants.URDU;

        //TODO catagory spinner code
        ConstraintLayout spinLayout = findViewById(R.id.spinnerlayout);
        spinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagorySpinner.performClick();
            }
        });
        catagorySpinner = (Spinner)findViewById(R.id.catagory_spinner2);
        final String catagories [] = {"احادیث میں تلاش کریں","کتب میں تلاش کریں","ابواب میں تلاش کریں" ,"موضوعات میں تلاش کریں" };
        final String catagoriesArabic [] = {"البحث في الأحاديث","البحث في الكتب","بحث الفصول" ,"البحث عن المواضيع" };
        catagoryAdapter = new ArrayAdapter(this,R.layout.spinner_layout_2,catagories);
        catagorySpinner.setAdapter(catagoryAdapter);

        final TextView buttonTV = findViewById(R.id.textView14);
        final TextView toolTV = findViewById(R.id.textView5);
        //TODO language  code
        final Button urduBtn = findViewById(R.id.imageButton3);
        final Button arabicBtn = findViewById(R.id.imageButton4);
        urduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urduBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.backbutton));
                arabicBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.make_cornor_round));
                urduBtn.setTextColor(Color.WHITE);
                arabicBtn.setTextColor(Color.BLACK);
                language = Constants.URDU;
                int temp = catagorySpinner.getSelectedItemPosition();
                catagoryAdapter = new ArrayAdapter(SearchResultsActivity.this,R.layout.spinner_layout_2,catagories);
                catagorySpinner.setAdapter(catagoryAdapter);
                catagorySpinner.setSelection(temp);
                Typeface face = ResourcesCompat.getFont(getApplicationContext(), R.font.urdu_font);
                searchBox.setTypeface(face);
                buttonTV.setTypeface(face);
                toolTV.setTypeface(face);
            }
        });
        arabicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urduBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.make_cornor_round));
                arabicBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.backbutton));
                language = Constants.ARABIC;
                urduBtn.setTextColor(Color.BLACK);
                arabicBtn.setTextColor(Color.WHITE);
                int temp = catagorySpinner.getSelectedItemPosition();
                catagoryAdapter = new ArrayAdapter(SearchResultsActivity.this,R.layout.spinner_layout_2,catagoriesArabic);
                catagorySpinner.setAdapter(catagoryAdapter);
                catagorySpinner.setSelection(temp);
                Typeface face = ResourcesCompat.getFont(getApplicationContext(), R.font.arabic_font);
                searchBox.setTypeface(face);
                buttonTV.setTypeface(face);
                toolTV.setTypeface(face);
            }
        });





        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(SearchResultsActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(SearchResultsActivity.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(SearchResultsActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(SearchResultsActivity.this, Contact_us.class);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ImageView layout = findViewById(R.id.imageView33);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));

    }

    public void doSearch(View v)
    {
        performSearch2(searchBox.getText().toString());
    }

    public void performSearch2(String query) {

        searchText = query;
       // language = (String) languageSpinner.getSelectedItem();
        catagory = "none";
        switch (catagorySpinner.getSelectedItemPosition()) {
            case 0:
                catagory = Constants.AHADEES;
                break;
            case 1:
                catagory = Constants.KUTUB;
                break;
            case 2:
                catagory = Constants.ABWAB;
                break;
            case 3:
                catagory = Constants.MOZU;
                break;
        }


        if (catagory.equals(Constants.AHADEES)) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultsActivity.this,(View)searchBarLayout,"searchbar");
            Intent intent = new Intent(SearchResultsActivity.this, HadeesSearchResult.class);
            intent.putExtra(Constants.LANGUAGE,language);
            intent.putExtra(Constants.SEARCHTEXT,query);
            startActivity(intent,activityOptionsCompat.toBundle());
         //   hadeesSearch();
        } else if (catagory.equals(Constants.KUTUB)) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultsActivity.this,(View)searchBarLayout,"searchbar");
            Intent intent = new Intent(SearchResultsActivity.this, KutubSearchActivity.class);
            intent.putExtra(Constants.LANGUAGE,language);
            intent.putExtra(Constants.SEARCHTEXT,query);
            startActivity(intent,activityOptionsCompat.toBundle());
        } else if (catagory.equals(Constants.ABWAB)) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultsActivity.this,(View)searchBarLayout,"searchbar");
            Intent intent = new Intent(SearchResultsActivity.this, AbwabSearchResult.class);
            intent.putExtra(Constants.LANGUAGE,language);
            intent.putExtra(Constants.SEARCHTEXT,query);
            startActivity(intent,activityOptionsCompat.toBundle());
         //   babSearch();
        } else if (catagory.equals(Constants.MOZU)) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchResultsActivity.this,(View)searchBarLayout,"searchbar");
            Intent intent = new Intent(SearchResultsActivity.this, TopicSearchResult.class);
            intent.putExtra(Constants.LANGUAGE,language);
            intent.putExtra(Constants.SEARCHTEXT,query);
            startActivity(intent,activityOptionsCompat.toBundle());
         //   mozuSearch();
        }
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }


    public void performSearch(String query)
    {
        allowedBooks = Utilites.allowedBooksInsearch(pref);
        if(allowedBooks[0] == false && allowedBooks[1] == false && allowedBooks[2] == false && allowedBooks[3] == false && allowedBooks[4] == false && allowedBooks[5] == false){
            Toast.makeText(this, "Please select some book", Toast.LENGTH_SHORT).show();
        }else {
            isSearchStarted = true;
            if (asyncTask != null) {
                asyncTask.cancel(true);
                asyncTask = null;
            }
            if (cursor1 != null && !cursor1.isClosed()) {
                cursor1.close();
            }
            searchText = query;
            language = (String) languageSpinner.getSelectedItem();
            catagory = "none";
            switch (catagorySpinner.getSelectedItemPosition()) {
                case 0:
                    catagory = Constants.AHADEES;
                    break;
                case 1:
                    catagory = Constants.KUTUB;
                    break;
                case 2:
                    catagory = Constants.MOZU;
                    break;
                case 3:
                    catagory = Constants.ABWAB;
                    break;
            }

            switch (languageSpinner.getSelectedItemPosition()) {
                case 0:
                    language = Constants.URDU;
                    break;
                case 1:
                    language = Constants.ARABIC;
                    break;

            }

            if (catagory.equals(Constants.AHADEES)) {
                hadeesSearch();
            } else if (catagory.equals(Constants.KUTUB)) {
                kutubSearch();
            } else if (catagory.equals(Constants.ABWAB)) {
                babSearch();
            } else if (catagory.equals(Constants.MOZU)) {
                mozuSearch();
            }
        }
    }


    ProgressDialog progressDialog;
    public void hadeesSearch()
    {
        hadeesSearchAdapter = null;
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
                progressDialog = new ProgressDialog(SearchResultsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                hadeesSearchAdapter = new HadeesSearchAdapter(getApplicationContext(),true);
                searchResultRecycler.setAdapter(hadeesSearchAdapter);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchDBforAhadess();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();

            }
        };
        asyncTask.execute();
    }

    public void searchDBforAhadess()
    {
        String query = "";

        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_URDU;
        }else
        {
            chosenLanguage = DBHelper.HadeesEntry.HADEES_TEXT_ARABIC;
        }

        query = "SELECT * from " +
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

        // Gets the data repository in write mode/
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);

        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                no_of_search_Results = cursor1.getCount();
                searchResultCountTV.setText("Results = "+no_of_search_Results);
            }
        });


        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
                final HadeesDataModel model = new HadeesDataModel();
                model.setMasadirNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));
                model.setKutubNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_URDU)));
                model.setKutubNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_ARABIC)));
                model.setBabNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU)));
                model.setBabNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC)));
                model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
                model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));
                model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
                model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.BAB_ID)));
                model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MA_ID)));

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

                //TODO updating recyclerview
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        hadeesSearchAdapter.addHadees(model);
                        hadeesSearchAdapter.notifyDataSetChanged();
                    }
                });


        }
        hadeesDBHelper.close();
    }


    public void kutubSearch()
    {
        kutubearchAdapter = null;
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
                progressDialog = new ProgressDialog(SearchResultsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                kutubearchAdapter = new KutubSearchAdapter(getApplicationContext(),SearchResultsActivity.this,searchText,language,true);
                searchResultRecycler.setAdapter(kutubearchAdapter);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInKutub();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
        }.execute();
    }

    public void searchInKutub()
    {

        String query;

        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.KutubEntry.KITAAB_NAME_URDU;
        }else
        {
            chosenLanguage = DBHelper.KutubEntry.KITAAB_NAME_ARABIC;
        }

        query= "SELECT m." + DBHelper.MasadaEntry.MASADIR_NAME_ARABIC + " ," +
                " k." + DBHelper.KutubEntry.KITAAB_NAME_URDU +
                " , k." + DBHelper.KutubEntry.KITAAB_NAME_ARABIC +
                " ,k." + DBHelper.KutubEntry.ID +
                " ,m." + DBHelper.MasadaEntry.MA_ID +
                " from " +
                DBHelper.KutubEntry.TABLE_NAME + " AS k "+
                " INNER JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE k." + chosenLanguage +" LIKE '%"+ searchBox.getText().toString().toString() +
                "%'  AND (";

        query = Utilites.AppendSearchQueryForBooks(pref,query);
        query+=") ORDER BY m."+ DBHelper.MasadaEntry.MA_ID;

        Log.d("trySearchQuery",query);
        KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = kutubDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"");


        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                no_of_search_Results = cursor1.getCount();
                searchResultCountTV.setText("  نتایج:  "+no_of_search_Results);
            }
        });


        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            if(allowedBooks[cursor1.getInt(4)-1]) {
                final KutubDataModel model = new KutubDataModel();
                model.setMasadirNameUrdu(cursor1.getString(0));
                model.setKutubNameUrdu(Utilites.removeParagraphTag(cursor1.getString(1)));
                model.setKutubNameArabic(Utilites.removeParagraphTag(cursor1.getString(2)));
                Log.d("tryQSearchResult",cursor1.getString(1));
                model.setId(cursor1.getInt(3));
                model.setMasadirId(cursor1.getInt(4));
                //TODO updating recyclerview
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        kutubearchAdapter.addKitab(model);
                        kutubearchAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
            }

            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2));
        }
        kutubDBHelper.close();
    }


    public void babSearch()
    {
        abwabearchAdapter = null;
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
                progressDialog = new ProgressDialog(SearchResultsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                abwabearchAdapter = new BabSearchAdapter(getApplicationContext(),SearchResultsActivity.this,searchText,language,true);
                searchResultRecycler.setAdapter(abwabearchAdapter);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInAbwab();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
        };
        asyncTask.execute();
    }

    public void searchInAbwab()
    {
        String query;

        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.AbwabEntry.BAB_NAME_URDU;
        }else
        {
            chosenLanguage = DBHelper.AbwabEntry.BAB_NAME_ARABIC;
        }

        //query = "SELECT * from "+DBHelper.AbwabEntry.TABLE_NAME;
        query= "SELECT m." + DBHelper.MasadaEntry.MASADIR_NAME_ARABIC + " ," +
                " k." + DBHelper.KutubEntry.KITAAB_NAME_URDU +
                " , k." + DBHelper.KutubEntry.KITAAB_NAME_ARABIC +
                " , b." + DBHelper.AbwabEntry.BAB_NAME_URDU +
                " , b." + DBHelper.AbwabEntry.BAB_NAME_ARABIC +
                " , b." + DBHelper.AbwabEntry.ID +
                " , k." + DBHelper.KutubEntry.MASADIR_ID +
                " , k." + DBHelper.KutubEntry.ID +
                " from " +
                DBHelper.AbwabEntry.TABLE_NAME + " AS b "+
                " LEFT JOIN " + DBHelper.KutubEntry.TABLE_NAME + " AS k " +
                "ON b." + DBHelper.AbwabEntry.KUTUB_ID + " = k." + DBHelper.KutubEntry.ID +
                " LEFT JOIN " + DBHelper.MasadaEntry.TABLE_NAME + " AS m " +
                "ON k." + DBHelper.KutubEntry.MASADIR_ID + " = m." + DBHelper.MasadaEntry.MA_ID +
                " WHERE b." + chosenLanguage +" LIKE '%"+ searchText +
                "%' AND (";



        AbwabDBHelper abwabDBHelper = new AbwabDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = abwabDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query,null);

        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                no_of_search_Results = cursor1.getCount();
                searchResultCountTV.setVisibility(View.VISIBLE);
                searchResultCountTV.setText(" نتایج "+no_of_search_Results);
            }
        });
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            if(allowedBooks[cursor1.getInt(6)-1]) {
                final AbwabDataModel model = new AbwabDataModel();
                model.setMasadirNameUrdu(cursor1.getString(0));
                model.setKutubNameUrdu(cursor1.getString(1));
                model.setKutubNameArabic(cursor1.getString(2));
                model.setBabNameUrdu(Utilites.removeParagraphTag(cursor1.getString(3)));
                model.setBabNameArabic(Utilites.removeParagraphTag(cursor1.getString(4)));
                model.setId(cursor1.getInt(5));
                model.setMasadirId(cursor1.getInt(6));
                model.setKitaabId(cursor1.getInt(7));


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        abwabearchAdapter.addBab(model);
                        abwabearchAdapter.notifyDataSetChanged();
                    }
                });

            }
            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2)+"  "+cursor1.getString(3)+"  "+cursor1.getString(4));
        }
        abwabDBHelper.close();
    }



    //TODO mozu search


    TopicsAdapter topicsAdapter;
    public void mozuSearch()
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
                progressDialog = new ProgressDialog(SearchResultsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                topicsAdapter = new TopicsAdapter(getApplicationContext(),SearchResultsActivity.this);
                searchResultRecycler.setAdapter(topicsAdapter);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInMozuat();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
        };
        asyncTask.execute();
    }

    public void searchInMozuat()
    {
        String query;

        if(language.equals(Constants.URDU))
        {
            chosenLanguage = DBHelper.TopicEntry.TOPICURDU;
        }else
        {
            chosenLanguage = DBHelper.TopicEntry.TOPICARABIC;
        }

        //query = "SELECT * from "+DBHelper.AbwabEntry.TABLE_NAME;
        query= "SELECT * from " +
                DBHelper.TopicEntry.TABLE_NAME + " AS t " +
                "WHERE t."+ chosenLanguage+" LIKE '%"+searchText+
        "%' ORDER BY t."+ DBHelper.TopicEntry.SEQID;

        Log.d("tryTopicEntry",query);

        HadithTopicsDBHelper hadithTopicsDBHelper = new HadithTopicsDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadithTopicsDBHelper.getReadableDatabase();
        cursor1 = db.rawQuery(query,null);

        //TODO update no of results textview
        handler.post(new Runnable() {
            @Override
            public void run() {
                no_of_search_Results = cursor1.getCount();
                searchResultCountTV.setVisibility(View.VISIBLE);
                searchResultCountTV.setText(" نتایج "+no_of_search_Results);
            }
        });
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
                        progressDialog.dismiss();
                        topicsAdapter.addTopic(model);
                        topicsAdapter.notifyDataSetChanged();
                    }
                });


            //Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2)+"  "+cursor1.getString(3)+"  "+cursor1.getString(4));
        }
        hadithTopicsDBHelper.close();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(cursor1 != null && !cursor1.isClosed())
        {
            asyncTask.cancel(false);
            cursor1.close();
        }
        Log("onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log("onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log("onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log("onRestart");
        if(isSearchStarted)
        {
            performSearch(searchBox.getText().toString()
                    .toString());
        }
    }

    public void Log(String msg)
    {
        Log.d("tryLifeCycle",msg);
    }


    public void choseBook(View v)
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


}
