package com.MohaddisMedia.mohaddis;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.Networking.ClassConnectionBuilder;
import com.MohaddisMedia.mohaddis.Update.UpdatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Updates extends AppCompatActivity {

    ImageView banner;
    Handler mHandler;
    ViewPager viewPager;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_updates);
        banner = findViewById(R.id.imageView3);
        mHandler = new Handler();
        progressBar = findViewById(R.id.progressBar10);
        viewPager = findViewById(R.id.update_view_pager);

        viewPager.setPageMargin(-50);
        viewPager.setHorizontalFadingEdgeEnabled(true);
        viewPager.setFadingEdgeLength(10);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(20,0,20,0);


         loadTodaysHadees();
        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(Updates.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(Updates.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(Updates.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(Updates.this, Contact_us.class);
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

    public void loadTodaysHadees()
    {
        new AsyncTask<Void,Void,Void>()
        {

            ArrayList<String> list = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String responce = ClassConnectionBuilder.getResponces(new URL("https://api.myjson.com/bins/108lip"),2000);
                    final JSONObject jsonObject = new JSONObject(responce);
                    JSONArray jsonArray = jsonObject.getJSONArray("updates");
                    for(int i =0;i<jsonArray.length();i++)
                    {
                        final JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        list.add(jsonObject1.getString("imageUri"));
                        Log.d("tryImage",jsonObject1.getString("imageUri"));
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                UpdatePagerAdapter updatePagerAdapter = new UpdatePagerAdapter(getApplicationContext(),list,Updates.this);
                viewPager.setAdapter(updatePagerAdapter);
                viewPager.setCurrentItem(viewPager.getChildCount()-1);
                progressBar.setVisibility(View.GONE);
            }

        }.execute();
    }
}
