package com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites;

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

import com.MohaddisMedia.mohaddis.Adapters.BabSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AbwabDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.AbwabDataModel;
import com.MohaddisMedia.mohaddis.DataModels.KutubDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

public class AbwabViewActivity extends AppCompatActivity {

    RecyclerView searchResultRecycler;
    BabSearchAdapter abwabSearchAdapter;

    Handler handler;
    KutubDataModel kutubDataModel;


    String query;
    //TODO search bar code
    EditText searchView;
    ImageButton searchButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_abwab_view);
        setTitle("Ø§Ø¨ÙˆØ§Ø¨");
        Bundle b = getIntent().getExtras();
        kutubDataModel = (KutubDataModel) b.get(Constants.KUTUB);
        handler = new Handler();
        searchResultRecycler = (RecyclerView)findViewById(R.id.abwabrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        searchResultRecycler.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.editText2b);
        searchButton = findViewById(R.id.imageButton2);

        query= "SELECT * from " +
                DBHelper.AbwabEntry.TABLE_NAME +
                " WHERE " + DBHelper.AbwabEntry.KUTUB_ID +" = "+ kutubDataModel.getId()+" ORDER BY "+ DBHelper.AbwabEntry.HIDDEN_ID;
        babSearch(query);

        TextView masadirTV = (TextView)findViewById(R.id.toolbartitleb);
        masadirTV.setText(kutubDataModel.getKutubNameUrdu());

        //TODO searchview activity

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query2 = searchView.getText().toString();
                if (query2.equals("")) {
                    query= "SELECT * from " +
                            DBHelper.AbwabEntry.TABLE_NAME +
                            " WHERE " + DBHelper.AbwabEntry.KUTUB_ID +" = "+ kutubDataModel.getId() ;
                }else {
                    query = "SELECT * from " +
                            DBHelper.AbwabEntry.TABLE_NAME +
                            " WHERE " + DBHelper.AbwabEntry.KUTUB_ID + " = " + kutubDataModel.getId() + " AND " + DBHelper.AbwabEntry.BAB_NAME_URDU + " LIKE '%" + query2 + "%'";
                }
                abwabSearchAdapter = null;
                babSearch(query);
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
                    query= "SELECT * from " +
                            DBHelper.AbwabEntry.TABLE_NAME +
                            " WHERE " + DBHelper.AbwabEntry.KUTUB_ID +" = "+ kutubDataModel.getId() ;
                    abwabSearchAdapter = null;
                    babSearch(query);
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
                    Intent intent = new Intent(AbwabViewActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(AbwabViewActivity.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(AbwabViewActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(AbwabViewActivity.this, Contact_us.class);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void backButton(View v)
    {
        onBackPressed();
    }

    public void babSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                abwabSearchAdapter = new BabSearchAdapter(getApplicationContext(),AbwabViewActivity.this);
                searchResultRecycler.setAdapter(abwabSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
                if(!searchView.getText().toString().equals("")){
                    abwabSearchAdapter.setSearchText(searchView.getText().toString());
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                searchInAbwab(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    public void searchInAbwab(String query)
    {
        AbwabDBHelper abwabDBHelper = new AbwabDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = abwabDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final AbwabDataModel model = new AbwabDataModel();

            model.setMasadirNameArabic(kutubDataModel.getMasadirNameArabic());
            model.setMasadirNameUrdu(kutubDataModel.getMasadirNameUrdu());
            model.setKutubNameUrdu(kutubDataModel.getKutubNameUrdu());
            model.setKutubNameArabic(kutubDataModel.getKutubNameArabic());
            model.setMasadirId(kutubDataModel.getMasadirId());
            model.setBabNameUrdu(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU))));
            model.setBabNameArabic(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC))));
            model.setId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)));
            model.setBab_hidden_id(cursor1.getFloat(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.HIDDEN_ID)));
            handler.post(new Runnable() {
                @Override
                public void run() {
                    abwabSearchAdapter.addBab(model);
                    abwabSearchAdapter.notifyDataSetChanged();

                }
            });

            Log.d("trySesultsHadees", cursor1.getString(0)+"  "+cursor1.getString(1)+"  "+cursor1.getInt(2)+"  "+cursor1.getString(3)+"  "+cursor1.getString(4));
        }
        abwabDBHelper.close();
    }
}
