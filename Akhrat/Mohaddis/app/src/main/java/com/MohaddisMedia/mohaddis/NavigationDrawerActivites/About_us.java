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

public class About_us extends AppCompatActivity {
    SharedPreferences pref;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_about_us);

        TextView t1 = findViewById(R.id.textView34);
        TextView t2 = findViewById(R.id.textView29);
        TextView t3 = findViewById(R.id.textView32);
        TextView t4 = findViewById(R.id.textView30);
        TextView t5 = findViewById(R.id.textView33);
        TextView t6 = findViewById(R.id.textView36);
        TextView t7 = findViewById(R.id.textView361);
        TextView t8 = findViewById(R.id.textView362);
        TextView t9 = findViewById(R.id.textView363);
        TextView t10 = findViewById(R.id.textView364);
        TextView t11 = findViewById(R.id.textView365);
        TextView t12 = findViewById(R.id.textView366);
        TextView t13 = findViewById(R.id.textView367);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            t1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            t2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            t3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
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
            t10.setTextSize( pref.getFloat(Constants.FONT,-1));
            t11.setTextSize( pref.getFloat(Constants.FONT,-1));
            t12.setTextSize( pref.getFloat(Constants.FONT,-1));
            t13.setTextSize( pref.getFloat(Constants.FONT,-1));
        }

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(About_us.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(About_us.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(About_us.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(About_us.this, Contact_us.class);
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


