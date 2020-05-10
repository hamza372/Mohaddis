package com.MohaddisMedia.mohaddis.NavigationDrawerActivites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.MohaddisMedia.mohaddis.R;

public class Way_to_use extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_way_to_use);

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(Way_to_use.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(Way_to_use.this, About_us.class);
                    startActivity(intent);
                }  else if (id == R.id.coopertion) {
                    Intent intent = new Intent(Way_to_use.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(Way_to_use.this, Contact_us.class);
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
