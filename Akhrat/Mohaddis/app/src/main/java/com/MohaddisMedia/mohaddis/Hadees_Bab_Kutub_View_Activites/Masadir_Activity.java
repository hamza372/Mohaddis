package com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Adapters.KutubSearchAdapter;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AHadeesDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.MasadirDataModel;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.R;

import static com.MohaddisMedia.mohaddis.Constants.BUKHARI;
import static com.MohaddisMedia.mohaddis.Constants.DAOWD;
import static com.MohaddisMedia.mohaddis.Constants.MAJA;
import static com.MohaddisMedia.mohaddis.Constants.MUSLIM;
import static com.MohaddisMedia.mohaddis.Constants.NISAI;
import static com.MohaddisMedia.mohaddis.Constants.TIRMAZI;

public class Masadir_Activity extends AppCompatActivity  {

    Handler handler;
    KutubSearchAdapter kutubSearchAdapter;
    String[] masadirNameUrdu = new String[6];
    String[] masadirNameArabic = new String[6];
    SharedPreferences pref;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_masadir);
        handler = new Handler();
        pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);

        masadirNameArabic[0] = "صحيح البخاري";
        masadirNameArabic[1] = "صحيح مسلم";
        masadirNameArabic[2] = "سنن أبي داؤد";
        masadirNameArabic[3] = "جامع الترمذي";
        masadirNameArabic[4] = "سنن النسائي";
        masadirNameArabic[5] = "سنن ابن ماجه";

        masadirNameUrdu[0] = "صحیح بخاری";
        masadirNameUrdu[1] = "صحیح مسلم";
        masadirNameUrdu[2] = "سنن ابو داؤد";
        masadirNameUrdu[3] = "جامع ترمذی";
        masadirNameUrdu[4] = "سنن نسائی";
        masadirNameUrdu[5] = "سنن ابن ماجہ";

        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(Masadir_Activity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(Masadir_Activity.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(Masadir_Activity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(Masadir_Activity.this, Contact_us.class);
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

        pref = getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
        tv1 = findViewById(R.id.b1);
        tv2 = findViewById(R.id.b2);
        tv3 = findViewById(R.id.b3);
        tv4 = findViewById(R.id.b4);
        tv5 = findViewById(R.id.b5);
        tv6 = findViewById(R.id.b6);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            tv1.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv2.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv3.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv4.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv5.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv6.setTextSize( pref.getFloat(Constants.FONT,-1));
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
            tv5.setTextSize( pref.getFloat(Constants.FONT,-1));
            tv6.setTextSize( pref.getFloat(Constants.FONT,-1));
        }
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View v) {
        int masadirId = 1;
        String bookName = BUKHARI;
        switch(v.getId())
        {
            case R.id.lm1:
                bookName = BUKHARI;
                masadirId = 1;
                break;
            case R.id.lm2:
                bookName = MUSLIM;
                masadirId = 2;
                break;
            case R.id.lm3:
                bookName = DAOWD;
                masadirId = 3;
                break;
            case R.id.lm4:
                bookName = TIRMAZI;
                masadirId = 4;
                break;
            case R.id.lm5:
                bookName = NISAI;
                masadirId = 5;
                break;
            case R.id.lm6:
                bookName = MAJA;
                masadirId = 6;
                break;


        }

        Intent i = new Intent(Masadir_Activity.this, KutubViewActivity.class);
        MasadirDataModel masadirDataModel = new MasadirDataModel();
        masadirDataModel.setId(masadirId);
        masadirDataModel.setMasadirNameArabic(masadirNameArabic[masadirId-1]);
        masadirDataModel.setMasadirNameUrdu(masadirNameUrdu[masadirId-1]);
        i.putExtra(Constants.MASADIR,masadirDataModel);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }



    public Cursor readFromTableAHadess(String masadirName)
    {
        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();

        String query = "SELECT m."+ DBHelper.MasadaEntry.MASADIR_NAME_ARABIC +" ," +
                " k."+DBHelper.KutubEntry.KITAAB_NAME_URDU+
                " , k."+DBHelper.KutubEntry.KITAAB_NAME_ARABIC+
                " , b."+DBHelper.AbwabEntry.BAB_NAME_URDU+
                " , b."+DBHelper.AbwabEntry.BAB_NAME_ARABIC+
                " , h."+DBHelper.HadeesEntry.HADEES_TEXT_URDU+
                " , h."+DBHelper.HadeesEntry.HADEES_TEXT_ARABIC+
                " , h."+DBHelper.HadeesEntry.HADEES_NO+
                " from "+
                DBHelper.HadeesEntry.TABLE_NAME +" AS h INNER JOIN " +DBHelper.AbwabEntry.TABLE_NAME+" AS b " +
                "ON h."+ DBHelper.HadeesEntry.BAB_ID + " = b."+DBHelper.AbwabEntry.ID+
                " INNER JOIN " +DBHelper.KutubEntry.TABLE_NAME+" AS k " +
                "ON b."+ DBHelper.AbwabEntry.KUTUB_ID + " = k."+DBHelper.KutubEntry.ID+
                " INNER JOIN " +DBHelper.MasadaEntry.TABLE_NAME+" AS m " +
                "ON k."+ DBHelper.KutubEntry.MASADIR_ID + " = m."+DBHelper.MasadaEntry.MA_ID+
                " WHERE m."+ DBHelper.MasadaEntry.MASADIR_NAME_ARABIC+" = '"+masadirName+
                "' ORDER BY  "+DBHelper.HadeesEntry.HADEES_NO+" LIMIT 1 ";

        Cursor cursor1 = db.rawQuery(query,null);
        cursor1.moveToNext();
        Log.d("tryPesultsHadees", cursor1.getString(0)+"  1:: "+cursor1.getString(1)+"  2:: "+cursor1.getString(2)+"  3:: "+cursor1.getString(3)+"  4:: "+cursor1.getString(4)+"  5:: "+cursor1.getString(5)+"  6:: "+cursor1.getString(6)+"  7:: "+cursor1.getInt(7));

        return cursor1;
    }
}
