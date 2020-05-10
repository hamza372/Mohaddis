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

import com.MohaddisMedia.mohaddis.Adapters.KutubSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.KutubDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.KutubDataModel;
import com.MohaddisMedia.mohaddis.DataModels.MasadirDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;

public class KutubViewActivity extends AppCompatActivity {

    RecyclerView searchResultRecycler;
    KutubSearchAdapter kutubSearchAdapter;


    Handler handler;
    MasadirDataModel masadirDataModel;
    String query;
    int masdirId = 0;

    //TODO search bar code
    EditText searchView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_kutub_view);
        setTitle("Ú©ØªØ¨");
        Bundle b = getIntent().getExtras();
        masadirDataModel = (MasadirDataModel) b.get(Constants.MASADIR);
        handler = new Handler();
        searchResultRecycler = (RecyclerView)findViewById(R.id.kutubrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        searchResultRecycler.setLayoutManager(linearLayoutManager);


        //TODO masadirName
        progressBar = findViewById(R.id.progressBar2);
        TextView masdirNTV = findViewById(R.id.toolbartitlek);
        masdirNTV.setText(masadirDataModel.getMasadirNameUrdu());


        searchView = findViewById(R.id.editText2);
        ImageButton searchButton = findViewById(R.id.imageButton);
        query= "SELECT * from " +
                DBHelper.KutubEntry.TABLE_NAME +
                " WHERE " + DBHelper.KutubEntry.MASADIR_ID +" = "+masadirDataModel.getId()+" ORDER BY "+ DBHelper.KutubEntry.HIDDEN_ID;
        kutubSearch(query);



        //TODO searchview activity

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchView.getText().toString();
                if(query.equals(""))
                {
                    query= "SELECT * from " +
                            DBHelper.KutubEntry.TABLE_NAME +
                            " WHERE " + DBHelper.KutubEntry.MASADIR_ID +" = "+masadirDataModel.getId();
                }else {
                    query = "SELECT * from " +
                            DBHelper.KutubEntry.TABLE_NAME +
                            " WHERE " + DBHelper.KutubEntry.MASADIR_ID + " = " + masadirDataModel.getId() + " AND " + DBHelper.KutubEntry.KITAAB_NAME_URDU + " LIKE '%" + query + "%'";
                }
                kutubSearchAdapter = null;
                kutubSearch(query);
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
                            DBHelper.KutubEntry.TABLE_NAME +
                            " WHERE " + DBHelper.KutubEntry.MASADIR_ID +" = "+masadirDataModel.getId();
                    kutubSearchAdapter = null;
                    kutubSearch(query);
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
                    Intent intent = new Intent(KutubViewActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(KutubViewActivity.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(KutubViewActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(KutubViewActivity.this, Contact_us.class);
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

    public void kutubSearch(final String query)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                kutubSearchAdapter = new KutubSearchAdapter(getApplicationContext(),KutubViewActivity.this);
                searchResultRecycler.setAdapter(kutubSearchAdapter);
                progressBar.setVisibility(View.VISIBLE);
                if(!searchView.getText().toString().equals("")){
                    kutubSearchAdapter.setSearchText(searchView.getText().toString());
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {

                searchInKutub(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void searchInKutub(String query)
    {

        KutubDBHelper kutubDBHelper = new KutubDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = kutubDBHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(query,null);
        Log.d("trySearchResult",cursor1.getCount()+"   "+query);
        for(int i =0;i<cursor1.getCount();i++)
        {
            cursor1.moveToNext();
            final KutubDataModel model = new KutubDataModel();
            model.setMasadirNameUrdu(masadirDataModel.getMasadirNameUrdu());
            model.setKutubNameArabic(masadirDataModel.getMasadirNameArabic());
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
            model.setMasadirId(masadirDataModel.getId());
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
        kutubDBHelper.close();
    }


    public void backBtn(View v)
    {
        onBackPressed();
    }
}
