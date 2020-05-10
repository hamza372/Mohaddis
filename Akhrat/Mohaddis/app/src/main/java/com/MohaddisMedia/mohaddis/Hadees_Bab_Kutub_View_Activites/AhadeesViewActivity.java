package com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;

public class AhadeesViewActivity extends AppCompatActivity {

    RecyclerView searchResultRecycler;
    HadeesSearchAdapter ahadeesSearchAdapter;
    AbwabDataModel abwabDataModel;
    Handler handler;


    String query;

    //TODO search bar code
    EditText searchView;
    SharedPreferences pref;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_ahadees_view);
        setTitle("Ø§Ø­Ø§Ø¯ÛŒØ«");
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar3);
        Bundle b = getIntent().getExtras();
        abwabDataModel = (AbwabDataModel) b.get(Constants.ABWAB);
        handler = new Handler();
        searchResultRecycler = (RecyclerView)findViewById(R.id.ahadeesviewrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        searchResultRecycler.setLayoutManager(linearLayoutManager);

        //TODO toolbar title
        TextView toolbarTV = findViewById(R.id.toolbartitleh);
        toolbarTV.setText(abwabDataModel.getBabNameUrdu());
        searchView = findViewById(R.id.editText2h);
        ImageButton searchButton = findViewById(R.id.imageButton2h);
        query = "SELECT * from " +
                DBHelper.HadeesEntry.TABLE_NAME +" WHERE "+ DBHelper.HadeesEntry.BAB_ID +" = " + abwabDataModel.getId();
        hadeesSearch(query);

        //TODO searchview activity

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quer = searchView.getText().toString();
                if (quer.equals("")) {
                    query = "SELECT * from " +
                            DBHelper.HadeesEntry.TABLE_NAME +" WHERE "+ DBHelper.HadeesEntry.BAB_ID +" = " + abwabDataModel.getId();//+" ORDER BY "+ DBHelper.HadeesEntry.HIDDEN_ID;;

                }else {
                    query = "SELECT * from " +
                            DBHelper.HadeesEntry.TABLE_NAME +" WHERE "+ DBHelper.HadeesEntry.BAB_ID +" = " + abwabDataModel.getId()+
                            " AND " + DBHelper.HadeesEntry.HADEES_TEXT_URDU + " LIKE '%" + quer + "%'";//+" ORDER BY "+ DBHelper.HadeesEntry.HIDDEN_ID;;
                }
                ahadeesSearchAdapter = null;
                hadeesSearch();
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(KutubViewActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                if(s.toString().equals(""))
                {
                    query = "SELECT * from " +
                            DBHelper.HadeesEntry.TABLE_NAME +" WHERE "+ DBHelper.HadeesEntry.BAB_ID +" = " + abwabDataModel.getId();
                    ahadeesSearchAdapter = null;
                    hadeesSearch(query);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(AhadeesViewActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(AhadeesViewActivity.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(AhadeesViewActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(AhadeesViewActivity.this, Contact_us.class);
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

    public void backButton(View v)
    {
        onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void hadeesSearch()
    {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

                ahadeesSearchAdapter = new HadeesSearchAdapter(getApplicationContext(),AhadeesViewActivity.this);
                searchResultRecycler.setAdapter(ahadeesSearchAdapter);
                if(!searchView.getText().toString().equals("")){
                    ahadeesSearchAdapter.setSearchText(searchView.getText().toString());
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchDBforAhadess();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);

            }
        }.execute();

    }

    public void searchDBforAhadess()
    {





        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());

        // Gets the data repository in write mode/
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        Log.d("trySearchResult","  there  "+query);



        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final HadeesDataModel model = new HadeesDataModel();
            model.setMasadirNameUrdu(abwabDataModel.getMasadirNameUrdu());
            model.setKutubNameUrdu(abwabDataModel.getKutubNameUrdu());
            model.setKutubNameArabic(abwabDataModel.getKutubNameArabic());
            model.setBabNameUrdu(abwabDataModel.getBabNameUrdu());
            model.setBabNameArabic(abwabDataModel.getBabNameArabic());
            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));
            model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
            model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.BAB_ID)));
            model.setMasadirId(abwabDataModel.getMasadirId());

            //TODO taraqeem
            Log.d("trySearchResult",DBHelper.HadeesEntry.HadeesNumberTOne);
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
                    ahadeesSearchAdapter.addHadees(model);
                    ahadeesSearchAdapter.notifyDataSetChanged();
                }
            });


        }
        hadeesDBHelper.close();
    }



    public void hadeesSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ahadeesSearchAdapter = new HadeesSearchAdapter(getApplicationContext(),AhadeesViewActivity.this);
                searchResultRecycler.setAdapter(ahadeesSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchDBforAhadess(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);


            }
        }.execute();
    }

    public void searchDBforAhadess(String query)
    {

        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"    "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final HadeesDataModel model = new HadeesDataModel();
            model.setMasadirNameUrdu(abwabDataModel.getMasadirNameUrdu());
            model.setMasadirNameArabic(abwabDataModel.getMasadirNameArabic());
            model.setKutubNameUrdu(abwabDataModel.getKutubNameUrdu());
            model.setKutubNameArabic(abwabDataModel.getKutubNameArabic());
            model.setBabNameUrdu(abwabDataModel.getBabNameUrdu());
            model.setBabNameArabic(abwabDataModel.getBabNameArabic());
            model.setMasadirId(abwabDataModel.getMasadirId());
            model.setBabId(abwabDataModel.getId());

            //       model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
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

            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));
            model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
            Log.d("tryData",i+"   "+cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));

            //TODO tarajum
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

            model.setHadithTypeAtraaf(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithTypeAtraaf)));

            //TODO updating recyclerview
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ahadeesSearchAdapter.addHadees(model);
                    ahadeesSearchAdapter.notifyDataSetChanged();
                }
            });
        }
        hadeesDBHelper.close();
    }

}
