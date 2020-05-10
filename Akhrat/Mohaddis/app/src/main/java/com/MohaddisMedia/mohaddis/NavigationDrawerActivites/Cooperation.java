package com.MohaddisMedia.mohaddis.NavigationDrawerActivites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Cooperation extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_cooperation);
        t1 = findViewById(R.id.textView37);
        t2 = findViewById(R.id.textView38);
        t3 = findViewById(R.id.textView41);
        t4 = findViewById(R.id.textView40);
        t5 = findViewById(R.id.textView39);
        t6 = findViewById(R.id.textView42);
        t7 = findViewById(R.id.textView37);
        t8 = findViewById(R.id.textView38);
        t9 = findViewById(R.id.textView43);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            t1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            t2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            t3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        SharedPreferences pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            t1.setTextSize( pref.getFloat(Constants.FONT,-1));
            t2.setTextSize( pref.getFloat(Constants.FONT,-1));
            t3.setTextSize( pref.getFloat(Constants.FONT,-1));
            t4.setTextSize( pref.getFloat(Constants.FONT,-1));
            t5.setTextSize( pref.getFloat(Constants.FONT,-1));
            t6.setTextSize( pref.getFloat(Constants.FONT,-1));
            t7.setTextSize( pref.getFloat(Constants.FONT,-1));
            t8.setTextSize( pref.getFloat(Constants.FONT,-1));
            t9.setTextSize( pref.getFloat(Constants.FONT,-1));

        }

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(Cooperation.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(Cooperation.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(Cooperation.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(Cooperation.this, Contact_us.class);
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
}
