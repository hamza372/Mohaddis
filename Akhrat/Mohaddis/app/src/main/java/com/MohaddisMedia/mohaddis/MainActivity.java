package com.MohaddisMedia.mohaddis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateTimePatternGenerator;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites.Masadir_Activity;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.Networking.ClassConnectionBuilder;
import com.MohaddisMedia.mohaddis.Search.SearchResultsActivity;
import com.MohaddisMedia.mohaddis.Settings.Settings;
import com.MohaddisMedia.mohaddis.Topics.TopicsActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //TODO layout variables declaration
    ImageView searchBtn;
    ImageView masadirBtn;
    ImageView mozuBtn;
    ImageView updatesBtn;
    ImageView settingsBtn;

    int todaysHaddesNo = 1000;
    int todaysMasadirId = 4;

    SharedPreferences pref;

    LinearLayout todayHaddesLayout;
    TextView todayHadeesHeaderTV;
    TextView todayHadeesTextTV;
    TextView tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(Constants.Package_Name,MODE_PRIVATE);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.nav_activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar3);

        ImageView sidebar = findViewById(R.id.imageView17);
        //TODO searchbox code
        searchBtn = (ImageView) findViewById(R.id.imageView2search);

        //TODO searchbox code
        masadirBtn = (ImageView) findViewById(R.id.imageView9book);
        masadirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Masadir_Activity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        //TODO mozu code
        mozuBtn = (ImageView) findViewById(R.id.imageView10topic);
        mozuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopicsActivity.class);
                intent.putExtra(Constants.Topic,1);
                intent.putExtra(Constants.Title,"شجرۂ موضوعات");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        //TODO update code
        updatesBtn = (ImageView) findViewById(R.id.imageView11update);
        updatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Updates.class));
            }
        });

        //TODO update code
        settingsBtn = (ImageView) findViewById(R.id.imageView2);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });



//        //TODO todays hadees code
//        todayHaddesLayout = findViewById(R.id.todayhlayout);
//        todayHadeesHeaderTV = findViewById(R.id.textView2UT);
//        todayHadeesTextTV = findViewById(R.id.textView3UT);
//        if(pref.getInt(Constants.TODAYS_DATE,-1) == DateTimePatternGenerator.DAY_OF_WEEK_IN_MONTH)
//        {
//            todaysHaddesNo = pref.getInt(Constants.TODAYS_HADEES_NO,100);
//            todaysMasadirId = pref.getInt(Constants.TODAYS_MASADIR_ID,1);
//            hadeesDataModel = Utilites.fetchHadeesWithMasadir(todaysHaddesNo,todaysMasadirId,getApplicationContext());
//            if(hadeesDataModel != null)
//            {
//                todayHadeesHeaderTV.setText(hadeesDataModel.getMasadirNameUrdu() + " :" + hadeesDataModel.getKutubNameUrdu() + " (" + hadeesDataModel.getBabNameUrdu() + ")");
//                todayHadeesTextTV.setText(hadeesDataModel.getTextHadeesUrdu());
//            }
//        }else{
//            loadTodaysHadees();
//        }
//
//        todayHaddesLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), HadeesViewActivity.class);
//                intent.putExtra(Constants.HADESSNO,todaysHaddesNo);
//                intent.putExtra(Constants.MASADIR,todaysMasadirId);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//            }
//        });



        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(MainActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(MainActivity.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(MainActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(MainActivity.this, Contact_us.class);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        final ImageView layout = findViewById(R.id.imageView17);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));

        pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
        tv1 = findViewById(R.id.ic2);
        tv2 = findViewById(R.id.ic4);
        tv3 = findViewById(R.id.ic5);
        tv4 = findViewById(R.id.textView22);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            tv1.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv2.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv3.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv4.setTextSize( pref.getFloat(Constants.FONT,-1));

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            tv1.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv2.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv3.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv4.setTextSize( pref.getFloat(Constants.FONT,-1));

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void masdirClick(View v)
    {
        startActivity(new Intent(MainActivity.this, Masadir_Activity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void updatesClick(View v)
    {
        startActivity(new Intent(MainActivity.this,Updates.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void searchClick(View v)
    {
        startActivity(new Intent(MainActivity.this, SearchResultsActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void mozuClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), TopicsActivity.class);
        intent.putExtra(Constants.Topic,1);
        intent.putExtra(Constants.Title,"شجرۂ موضوعات");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    HadeesDataModel hadeesDataModel;
    public void loadTodaysHadees()
    {

        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("tryLoadStatus","pre");
            }


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String responce = ClassConnectionBuilder.getResponces(new URL("https://api.myjson.com/bins/p5i87"),2000);
                    Log.d("tryNetwork","hello world "+responce);
                    JSONObject jsonObject = new JSONObject(responce);
                    todaysHaddesNo = jsonObject.getInt("hadeesNo");
                    todaysMasadirId = jsonObject.getInt("masadirId");
                    pref.edit().putInt(Constants.TODAYS_HADEES_NO,todaysHaddesNo).apply();
                    pref.edit().putInt(Constants.TODAYS_MASADIR_ID,todaysMasadirId).apply();
                    pref.edit().putInt(Constants.TODAYS_DATE,DateTimePatternGenerator.DAY_OF_WEEK_IN_MONTH).apply();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hadeesDataModel =Utilites.fetchHadeesWithMasadir(todaysHaddesNo,todaysMasadirId,getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("tryLoadStatus","post");
                Log.d("tryNetwork","hello world");
                //Toast.makeText(MainActivity.this, todaysHaddesNo + "    " + todaysMasadirId, Toast.LENGTH_SHORT).show();
                if(hadeesDataModel != null)
                {
                    todayHadeesHeaderTV.setText(hadeesDataModel.getMasadirNameUrdu() + " :" + hadeesDataModel.getKutubNameUrdu() + " (" + hadeesDataModel.getBabNameUrdu() + ")");
                    todayHadeesTextTV.setText(hadeesDataModel.getTextHadeesUrdu());
                }
            }

        }.execute();
    }


}
