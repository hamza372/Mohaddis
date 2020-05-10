package com.MohaddisMedia.mohaddis.Search;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.AHadeesDBHelper;
import com.MohaddisMedia.mohaddis.DB.TakhreejBDHelper;
import com.MohaddisMedia.mohaddis.DB.TopicsToHadithsDBHelper;
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.DataModels.TakhreejDataModel;
import com.MohaddisMedia.mohaddis.Dialogues.HasiaHukamDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.HukamTabbedDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.MozuTabbedDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.TakhreejDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.TamheedDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.TarajumTabbedDialogue;
import com.MohaddisMedia.mohaddis.Dialogues.TaraqimTabbedDialogue;
import com.MohaddisMedia.mohaddis.HadeesViewActivity;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.About_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Contact_us;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Cooperation;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Properties;
import com.MohaddisMedia.mohaddis.NavigationDrawerActivites.Way_to_use;
import com.MohaddisMedia.mohaddis.OnSwipeTouchListener;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

import java.util.ArrayList;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.MohaddisMedia.mohaddis.Constants.HUKAM_TAFSELI;
import static com.MohaddisMedia.mohaddis.Constants.MASADIR;

public class HadeesViewSearchActivity extends AppCompatActivity {

    //TODO layout elements
    TextView arabicTitleTV;
    TextView arabicHadeeshTV;

    TextView urduTitleTV;
    TextView urduHadeeshTV;

    EditText hadessnoEText;

    //TODO slider elements
    SeekBar hadeesSeekbar;


    HadeesDataModel model;

    TextView atrafTV;
    TextView akmTV;

    int babId = 0;
    int masadirId = 1;
    String qery = "";

    SharedPreferences pref;
    int no_of_ahadees[] ={7632,7756,5305,4376,5778,4474};
    String searchText,language;
    TextView masdirNameToolBarTv;

    //TODO a field for taraqim base hadees no
    String tarqimColumName = DBHelper.HadeesEntry.HadeesNumberTOne;
    int currentLoadedHadees = 0;

    Spinner spinner;
    String existingQuery;
    int existingPoasition;
    Cursor universalCursor = null;
    TextView currentResult;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("محدث");

        //TODO night mode code
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        if(!pref.getBoolean(Constants.NIGHT_MODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        setContentView(R.layout.nav_activity_hadees_view_search);
        Intent b = getIntent();
        existingQuery = b.getStringExtra(Constants.QUERY);
        existingPoasition = b.getIntExtra(Constants.POSITION,0);
        final int hadeesNo = b.getIntExtra(Constants.HADESSNO,1000);
        Log.d("trySH",hadeesNo+" a");
        babId = b.getIntExtra(Constants.ABWAB,-1);
        if(b.getIntExtra(Constants.MASADIR,-1) != -1)
        {
            masadirId = b.getIntExtra(Constants.MASADIR,1);
        }
        Log.d("trySeBabId",babId+"");
       // model = fetchHadeesWithMasadir(hadeesNo,masadirId,false);
        //TODO language and searchtext
        searchText = getIntent().getExtras().getString(Constants.SEARCHTEXT);
        language = getIntent().getExtras().getString(Constants.LANGUAGE);

        //TODO total ahadees code
        universalCursor = fetchAhadeesWithMasadir(existingQuery);
        universalCursor.moveToFirst();
        currentResult = findViewById(R.id.textView15);
        currentResult.setText(existingPoasition+1+" / "+universalCursor.getCount());
        model = populateModel(universalCursor,existingPoasition);
        if(model == null)
        {
            Log.d("trySSS","null model");
        }else{
            Log.d("trySSS","not null model");
        }

        //TODO scroll layout code to detect left and right swipes
        ScrollView scrollView = findViewById(R.id.hvslayout);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                //Toast.makeText(HadeesViewActivity.this, "left", Toast.LENGTH_SHORT).show();
                // int hno = (int) Float.parseFloat(hadessnoEText.getText().toString());
                hadessnoEText.setTag("abab");
                int hno = existingPoasition;
                if(hno-1<0){
                    hno = universalCursor.getCount()-1;
                }else{
                    hno--;
                }
                //changeHadeesWithMasadir(hno,model.getMasadirId(),true);
                existingPoasition = hno;
                hadeesSeekbar.setProgress(hno);
                changeHadees(hno);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                hadessnoEText.setTag("abab");
                int hno = existingPoasition;
                if(hno+1>universalCursor.getCount()-1){
                    hno = 0;
                }else {
                    hno++;
                }
                existingPoasition = hno;
                hadeesSeekbar.setProgress(hno);
                changeHadees(hno);
            }
        });
        //TODO arabic content code
        arabicTitleTV = (TextView)findViewById(R.id.textView2A);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arabicTitleTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        arabicHadeeshTV = (TextView)findViewById(R.id.textView3A);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            arabicHadeeshTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        String text= model.getMasadirNameArabic()+" :" +
                "<font COLOR=\"RED\"><b>" +  model.getKutubNameArabic()+ "</b></font>"+
                "<font COLOR=\"BLACK\"><b>" +" ("+model.getBabNameArabic()+")"+"</b></font>";
        arabicTitleTV.setText(Html.fromHtml(text));
        customTextView(arabicTitleTV,model.getMasadirNameArabic(),model.getKutubNameArabic(),model.getBabNameArabic());
        arabicTitleTV.append("\nحکم :"+getSelectedHukam(model.getMasadirId()));

        //TODO urdu content code
        urduTitleTV = (TextView)findViewById(R.id.textView2U);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            urduTitleTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        urduHadeeshTV = (TextView)findViewById(R.id.textView3U);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            urduHadeeshTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        String text2= model.getMasadirNameUrdu()+" :" +
                "<font COLOR=\"RED\"><b>" +  model.getKutubNameUrdu()+ "</b></font>"+
                "<font COLOR=\"BLACK\"><b>" +" ("+model.getBabNameUrdu()+")"+"</b></font>";
        urduTitleTV.setText(Html.fromHtml(text2));
        customTextView(urduTitleTV,model.getMasadirNameUrdu(),model.getKutubNameUrdu(),model.getBabNameUrdu());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        urduHadeeshTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        if(language.equals(Constants.URDU)) {
            urduHadeeshTV.setText(Html.fromHtml(model.getTextHadeesUrdu().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")));
            arabicHadeeshTV.setText(Html.fromHtml(model.getTextHadeesArabic()));
        }else{
            arabicHadeeshTV.setText(Html.fromHtml(model.getTextHadeesArabic().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")));
            urduHadeeshTV.setText(Html.fromHtml(model.getTextHadeesUrdu()));
        }


        //TODO Hadith type atraf and bab kutub muhadis detail
        atrafTV = (TextView)findViewById(R.id.textView6);
        atrafTV.setText("نوع الحدیث:"+model.getHadithTypeAtraaf());
        akmTV = (TextView)findViewById(R.id.textView11);
        akmTV.setText(model.getMasadirNameUrdu()+" | "+model.getKutubNameUrdu()+" | "+model.getBabNameUrdu());
        Log.d("tryData",model.getHadeesNo()+"");


        //TODO hadess no textfield
        hadessnoEText = (EditText)findViewById(R.id.editText2hn);
        hadessnoEText.setTag("abab");
        hadessnoEText.setText(model.getHadeesNo()+"");

        if(pref.getFloat(Constants.FONT,-1) != -1) {
            urduHadeeshTV.setTextSize(pref.getFloat(Constants.FONT,-1));
            arabicTitleTV.setTextSize(pref.getFloat(Constants.FONT,-1));
            urduTitleTV.setTextSize(pref.getFloat(Constants.FONT,-1));
            arabicHadeeshTV.setTextSize(pref.getFloat(Constants.FONT,-1));
        }

        //TODO slider code
        hadeesSeekbar = (SeekBar)findViewById(R.id.seekBar);
        hadeesSeekbar.setMax(universalCursor.getCount()-1);
        hadeesSeekbar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        hadeesSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(model != null) {
                    changeHadees(seekBar.getProgress());
                   // Toast.makeText(HadeesViewSearchActivity.this, seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        hadeesSeekbar.setProgress(existingPoasition);

        //TODO second bottom navigation dialogue
        LinearLayout nightModeTV = findViewById(R.id.nightmodelayout);
        nightModeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontDialogue();
//                SharedPreferences pref = getSharedPreferences("com.example.mohaddis",Context.MODE_PRIVATE);
//                if(!pref.getBoolean(Constants.NIGHT_MODE,false))
//                {
//                    pref.edit().putBoolean(Constants.NIGHT_MODE,true).apply();
//                    recreate();
//                }else
//                {
//                    pref.edit().putBoolean(Constants.NIGHT_MODE,false).apply();
//                    recreate();
//                }
            }
        });

        LinearLayout mozuIV = findViewById(R.id.topicsmenulayout);
        mozuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                MozuTabbedDialogue mozuTabbedDialogue = new MozuTabbedDialogue();
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants.MOZU
                        ,new TopicsToHadithsDBHelper(getApplicationContext()).findTopics((int)model.getHadeesNumberTOne(),masadirId));
                mozuTabbedDialogue.setArguments(b);
                mozuTabbedDialogue.show(ft,"dialog");

            }
        });

        //TODO toolbar layout
        ImageView copy = (ImageView)findViewById(R.id.imageView9);
        ImageView share = (ImageView)findViewById(R.id.imageView10);
        ImageView nextHadees = (ImageView)findViewById(R.id.imageView11);
        masdirNameToolBarTv = findViewById(R.id.textView3);
        masdirNameToolBarTv.setText(model.getMasadirNameUrdu());

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy",model.toString(pref));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HadeesViewSearchActivity.this, "Copied", Toast.LENGTH_SHORT).show();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,model.toString(pref));
                intent.setType("text/plain");
                startActivity(intent);

            }
        });

        nextHadees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //TODO taraqim spinner
        ConstraintLayout spinnerLayout = findViewById(R.id.spinnerlayout);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> taraqimAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,Constants.taraqim_name[masadirId-1]);
        spinner.setAdapter(taraqimAdapter);

        spinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
        String bookName = Constants.book1Tarqeem;
        if(masadirId == 1)
        {
            bookName = Constants.book1Tarqeem;
        }else if(masadirId == 2)
        {
            bookName = Constants.book2Tarqeem;
        }else if(masadirId == 3)
        {
            bookName = Constants.book3Tarqeem;
        }else if(masadirId == 4)
        {
            bookName = Constants.book4Tarqeem;
        }else if(masadirId == 5)
        {
            bookName = Constants.book5Tarqeem;
        }else if(masadirId == 6)
        {
            bookName = Constants.book6Tarqeem;
        }
        Log.d("tryTaraqim",masadirId+"       "+pref.getInt(bookName,0)+"");
        spinner.setSelection(pref.getInt(bookName,1));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hadessnoEText.setTag("abc");
                switch (position)
                {
                    case 0:
                        hadessnoEText.setText(model.getHadeesNo()+"");
                        break;
                    case 1:
                        hadessnoEText.setText(model.getHadeesNumberTOne()+"");
                        break;

                    case 2:
                        hadessnoEText.setText(model.getHadeesNumberTTwo()+"");
                        break;
                    case 3:
                        hadessnoEText.setText(model.getHadeesNumberTThree()+"");
                        break;
                    case 4:
                        hadessnoEText.setText(model.getHadeesNumberTFour()+"");
                        break;
                    case 5:
                        hadessnoEText.setText(model.getHadeesNumberTFive()+"");
                        break;
                    case 6:
                        hadessnoEText.setText(model.getHadeesNumberTSix()+"");
                        break;
                    case 7:
                        hadessnoEText.setText(model.getHadeesNumberTSeven()+"");
                        break;
                    case 8:
                        hadessnoEText.setText(model.getHadeesNumberTEight()+"");
                        break;
                    case 9:
                        hadessnoEText.setText(model.getHadeesNumberTNine()+"");
                        break;
                    case 10:
                        hadessnoEText.setText(model.getHadeesNumberTTen()+"");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //TODO navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.properties) {
                    Intent intent = new Intent(HadeesViewSearchActivity.this, Properties.class);
                    startActivity(intent);

                } else if (id == R.id.about) {
                    Intent intent = new Intent(HadeesViewSearchActivity.this, About_us.class);
                    startActivity(intent);
                } else if (id == R.id.coopertion) {
                    Intent intent = new Intent(HadeesViewSearchActivity.this, Cooperation.class);
                    startActivity(intent);
                } else if (id == R.id.contact) {
                    Intent intent = new Intent(HadeesViewSearchActivity.this, Contact_us.class);
                    startActivity(intent);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

           final ImageView layout = findViewById(R.id.imageView26);
           layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primary));

    }

    public void selectProperHadeesWithpinner()
    {
        switch (spinner.getSelectedItemPosition())
        {
            case 0:
                hadessnoEText.setText(model.getHadeesNo()+"");
                break;
            case 1:
                hadessnoEText.setText(model.getHadeesNumberTOne()+"");
                break;

            case 2:
                hadessnoEText.setText(model.getHadeesNumberTTwo()+"");
                break;
            case 3:
                hadessnoEText.setText(model.getHadeesNumberTThree()+"");
                break;
            case 4:
                hadessnoEText.setText(model.getHadeesNumberTFour()+"");
                break;
            case 5:
                hadessnoEText.setText(model.getHadeesNumberTFive()+"");
                break;
            case 6:
                hadessnoEText.setText(model.getHadeesNumberTSix()+"");
                break;
            case 7:
                hadessnoEText.setText(model.getHadeesNumberTSeven()+"");
                break;
            case 8:
                hadessnoEText.setText(model.getHadeesNumberTEight()+"");
                break;
            case 9:
                hadessnoEText.setText(model.getHadeesNumberTNine()+"");
                break;
            case 10:
                hadessnoEText.setText(model.getHadeesNumberTTen()+"");
                break;
        }
    }


    public void onBottonNavigationListSelector(View v)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        if(v.getId() == R.id.menubutton)
        {
            // Create and show the dialog.
            LinearLayout seconLayout = findViewById(R.id.secondmenu);
            if(seconLayout.getVisibility() == View.GONE){
                seconLayout.setVisibility(View.VISIBLE);
            }else{
                seconLayout.setVisibility(View.GONE);
            }
            //new DotOptionDialgue().menuDialgue(getSupportFragmentManager(),HadeesViewActivity.this,model.getHadeesNo(),model.getMasadirId());
        }else if(v.getId() == R.id.taraqimhvl){
            // Create and show the dialog.
            String[] array = {model.getHadeesNo()+"",model.getHadeesNumberTOne()+"",model.getHadeesNumberTTwo()+"",model.getHadeesNumberTThree()+"",model.getHadeesNumberTFour()+"",model.getHadeesNumberTFive()+"",model.getHadeesNumberTSix()+"",model.getHadeesNumberTSeven()+"",model.getHadeesNumberTEight()+"",model.getHadeesNumberTNine()+"",model.getHadeesNumberTNine()+"",model.getHadeesNumberTTen()+""};
            TaraqimTabbedDialogue taraqimTabbedDialogue = new TaraqimTabbedDialogue();
            Bundle b = new Bundle();
            b.putInt(MASADIR,model.getMasadirId());
            b.putStringArray(Constants.TARAQIM,array);
            taraqimTabbedDialogue.setArguments(b);
            taraqimTabbedDialogue.show(ft,"dialog");
        }else if(v.getId() == R.id.tarjumahvl){
            // Create and show the dialog.

            String[] tarajum = {model.getTextHadeesUrdu(),model.getHadithUrduTextOne(),model.getHadithUrduTextTwo(),model.getHadithUrduTextThree(),model.getHadithUrduTextFour(),model.getHadithUrduTextFive(),model.getHadithUrduTextSix(),model.getHadithUrduTextSeven(),model.getHadithUrduTextEight(),model.getHadithUrduTextNine(),model.getHadithUrduTextTen()};
            String[] hasia = {model.getHadessHasiaText(),model.getHadithHashiaTextOne(),model.getHadithHashiaTextTwo(),model.getHadithHashiaTextThree(),model.getHadithHashiaTextFour(),model.getHadithHashiaTextFive(),model.getHadithHashiaTextSix(),model.getHadithHashiaTextSeven(),model.getHadithHashiaTextEight(),model.getHadithHashiaTextNine(),model.getHadithHashiaTextTen() };
            Log.d("tryStrarjum",tarajum.toString());
            //String[] hasia = {model.getHadithHashiaTextOne(),model.getHadithHashiaTextTwo(),model.getHadithHashiaTextThree(),model.getHadithHashiaTextFour(),model.getHadithHashiaTextFive(),model.getHadithHashiaTextSix(),model.getHadithHashiaTextSeven(),model.getHadithHashiaTextEight(),model.getHadithHashiaTextNine(),model.getHadithHashiaTextTen()};
            TarajumTabbedDialogue taraqimTabbedDialogue = new TarajumTabbedDialogue();
            Bundle b = new Bundle();
            b.putStringArray(Constants.TARAJUM,tarajum);
            b.putStringArray(Constants.HASIA,hasia);
            b.putString(Constants.TarjumaBabUrdu,model.getTarjumBabUrdu());

            b.putInt(MASADIR,model.getMasadirId());
            taraqimTabbedDialogue.setArguments(b);
            taraqimTabbedDialogue.show(ft,"dialog");
        }
        else if(v.getId() == R.id.hukamhvl){
            String[] hukam = {model.getHadithHukamAjmaliOne(),model.getHadithHukamAjmaliTwo(),model.getHadithHukamAjmaliThree(),model.getHadithHukamAjmaliFour(),model.getHadithHukamAjmaliFive(),model.getHadithHukamAjmaliSix(),model.getHadithHukamAjmaliSeven(),model.getHadithHukamAjmaliEight(),model.getHadithHukamAjmaliNine(),model.getHadithHukamAjmaliTen()};
            HukamTabbedDialogue hukamTabbedDialogue = new HukamTabbedDialogue();
            Bundle b = new Bundle();
            b.putStringArray(Constants.HUKAM,hukam);
            b.putInt(MASADIR,model.getMasadirId());
            b.putString(HUKAM_TAFSELI,model.getHadithHukamTafseeli());
            hukamTabbedDialogue.setArguments(b);
            hukamTabbedDialogue.show(ft,"dialog");

        }else if(v.getId() == R.id.takhreejhvl){
            fetchTakhreej((int) model.getHadeesNo(),ft,model.getMasadirId());
        }else if(v.getId() == R.id.hasiatvl){
            if(model != null) {
                HasiaHukamDialogue hasiaHukamDialogue = new HasiaHukamDialogue();
                Bundle b = new Bundle();
                b.putString(Constants.HUKAM, model.getHadithHukamTafseeli());
                b.putString(Constants.HASIA, model.getHadessHasiaText());
                hasiaHukamDialogue.setArguments(b);
                hasiaHukamDialogue.show(ft, "dialog");
            }else
            {
                Toast.makeText(this, "model is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sliderButtonsClick(View v) {
        // int hno = Integer.parseInt(hadessnoEText.getText().toString());
        int hno = existingPoasition;
        if (v.getId() == R.id.sliderrightbtn) {
            if(hno-1<0){
                hno = universalCursor.getCount() -1;
            }else{
                hno--;
            }
            hadeesSeekbar.setProgress(hno);
            changeHadees(hno);
           // Log.d("tryMasadir","res = "+changeHadeesWithMasadir(hno,model.getMasadirId(),true));
        } else if (v.getId() == R.id.sliderleftbtn) {
            Log.d("tryHNO",model.getHadeesNo()-1+"");
            if(hno+1>universalCursor.getCount()-1){
                hno = 0;
            }else {
                hno++;
            }
            existingPoasition = hno;
            hadeesSeekbar.setProgress(hno);
            changeHadees(hno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.hadees_view_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("tryHadeesUrl",model.toString(pref));
        if(item.getItemId() == R.id.copy)
        {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy",model.toString(pref));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.share){
            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,model.toString(pref));
            intent.setType("text/plain");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





    boolean done = false;
    public boolean changeHadees(final int hadeesNo)
    {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //hadeesSeekbar.setMax(universalCursor.getCount());
            }

            @Override
            protected Void doInBackground(Void... voids) {
                HadeesDataModel modelTest = populateModel(universalCursor,hadeesNo);
                if(modelTest != null)
                {
                    model = modelTest;
                    done = true;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    existingPoasition = hadeesNo;
                    currentResult.setText(existingPoasition+1+" / "+universalCursor.getCount());
                    String text= model.getMasadirNameArabic()+" :" +
                            "<font COLOR=\"RED\"><b>" +  model.getKutubNameArabic()+ "</b></font>"+
                            "<font COLOR=\"BLACK\"><b>" +" ("+model.getBabNameArabic()+")"+"</b></font>";
                    if(model.getKutubTamheedUrdu().equals("")) {
                        arabicTitleTV.setText(Html.fromHtml(text));
                    }else {
                        customTextView(arabicTitleTV,model.getMasadirNameArabic(),model.getKutubNameArabic(),model.getBabNameArabic());
                    }
                    arabicTitleTV.append("\nحکم :"+getSelectedHukam(model.getMasadirId()));
                    String text2 = model.getMasadirNameUrdu()+" :" +
                            "<font COLOR=\"RED\"><b>" +  model.getKutubNameUrdu()+ "</b></font>"+
                            "<font COLOR=\"BLACK\"><b>" +" ("+model.getBabNameUrdu()+")"+"</b></font>";
                    if(model.getKutubTamheedUrdu().equals("")) {
                        urduTitleTV.setText(Html.fromHtml(text2));
                    }else {
                        customTextView(urduTitleTV, model.getMasadirNameUrdu(), model.getKutubNameUrdu(), model.getBabNameUrdu());
                    }

                    if(language.equals(Constants.URDU)) {
                        urduHadeeshTV.setText(Html.fromHtml(model.getTextHadeesUrdu().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")));
                        arabicHadeeshTV.setText(Html.fromHtml(model.getTextHadeesArabic()));
                    }else{
                        arabicHadeeshTV.setText(Html.fromHtml(model.getTextHadeesArabic().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")));
                        urduHadeeshTV.setText(Html.fromHtml(model.getTextHadeesUrdu()));
                    }

                    hadessnoEText.setTag("tag");
                    atrafTV.setText("نوع الحدیث:"+model.getHadithTypeAtraaf());
                    masdirNameToolBarTv.setText(model.getMasadirNameUrdu());
                    akmTV.setText(model.getMasadirNameUrdu()+" | "+model.getKutubNameUrdu()+" | "+model.getBabNameUrdu());

                    //TODO clickable links


                    hadessnoEText.setTag("abab");
                    switch (spinner.getSelectedItemPosition())
                    {
                        case 0:
                            hadessnoEText.setText((int)model.getHadeesNo()+"");
                            break;
                        case 1:
                            hadessnoEText.setText((int)model.getHadeesNumberTOne()+"");
                            break;

                        case 2:
                            hadessnoEText.setText((int)model.getHadeesNumberTTwo()+"");
                            break;
                        case 3:
                            hadessnoEText.setText((int)model.getHadeesNumberTThree()+"");
                            break;
                        case 4:
                            hadessnoEText.setText((int)model.getHadeesNumberTFour()+"");
                            break;
                        case 5:
                            hadessnoEText.setText((int)model.getHadeesNumberTFive()+"");
                            break;
                        case 6:
                            hadessnoEText.setText((int)model.getHadeesNumberTSix()+"");
                            break;
                        case 7:
                            hadessnoEText.setText((int)model.getHadeesNumberTSeven()+"");
                            break;
                        case 8:
                            hadessnoEText.setText((int)model.getHadeesNumberTEight()+"");
                            break;
                        case 9:
                            hadessnoEText.setText((int)model.getHadeesNumberTNine()+"");
                            break;
                        case 10:
                            hadessnoEText.setText((int)model.getHadeesNumberTTen()+"");
                            break;
                    }
                }catch (Exception e)
                {

                }
            }
        }.execute();

        return done;

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



    public Cursor fetchAhadeesWithMasadir(String queryE)
    {
        AHadeesDBHelper hadeesDBHelper = new AHadeesDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = hadeesDBHelper.getWritableDatabase();
        Cursor cursor1 = db.rawQuery(queryE,null);
        //Log.d("trySSearchResult",cursor1.getCount()+"     "+cursor1.getCount()+"    "+query);
        return  cursor1;
    }


    public HadeesDataModel populateModel(Cursor cursor1,int position)
    {
        HadeesDataModel model = null;
        if(position < cursor1.getCount() )
        {
            cursor1.moveToPosition(position);
            model = new HadeesDataModel();
            model.setMasadirNameUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_URDU)));
            model.setMasadirNameArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.MasadaEntry.MASADIR_NAME_ARABIC)));
            model.setKutubNameUrdu(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_URDU))));
            model.setKutubNameArabic(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAAB_NAME_ARABIC))));
            model.setBabNameUrdu(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_URDU))));
            model.setBabNameArabic(Utilites.removeParagraphTag(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_NAME_ARABIC))));
            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setTextHadeesArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_ARABIC)));


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

            //TODO tarajum
            model.setHadithUrduText(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithUrduText)));
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
            model.setHadithHukamAjmali(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamAjmali)));
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

            model.setHadithHukamTafseeli(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithHukamTafseeli)));
            model.setHadessHasiaText(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADESS_HASHIA_TEXT)));
            model.setTextHadeesUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_TEXT_URDU)));
            model.setMasadirId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.MASADIR_ID)));
            model.setBabId(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.ID)));
            model.setHadithTypeAtraaf(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HadithTypeAtraaf)));

            model.setTarjumBabUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.TARJUMA_BAB_URDU)));
            model.setKutubTamheedUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAB_TAMHEED_URDU)));
            model.setKutubTamheedArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.KutubEntry.KITAB_TAMHEED_ARABIC)));
            model.setBabTamheedUrdu(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_TAMHEED_URDU)));
            model.setBabTamheedArabic(cursor1.getString(cursor1.getColumnIndexOrThrow(DBHelper.AbwabEntry.BAB_TAMHEED_ARABIC)));
            masadirId = model.getMasadirId();
            model.setHadeesNo(cursor1.getInt(cursor1.getColumnIndexOrThrow(DBHelper.HadeesEntry.HADEES_NO)));
            currentLoadedHadees = (int) Utilites.getTaraqimBasedHNo(pref,masadirId,model);
            // cursor1.close();
        }
        return  model;
    }

    TakhreejBDHelper takhreejBDHelper;
    ArrayList<TakhreejDataModel> takhreejList;
    public void fetchTakhreej(final int hadeesNo, final FragmentTransaction ft, final int bookId)
    {

        takhreejBDHelper = new TakhreejBDHelper(getApplicationContext());
        new AsyncTask<Void,Void,Void>()
        {

            @Override
            protected Void doInBackground(Void... voids) {
                takhreejList = takhreejBDHelper.fetchTakhreej(hadeesNo,bookId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                TakhreejDialogue takhreejDialogue = new TakhreejDialogue();
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants.TAKHREEJ,takhreejList);
                takhreejDialogue.setArguments(b);
                takhreejDialogue.show(ft,"dialog");
            }
        }.execute();
    }
    public String getSelectedHukam(int masadirId)
    {

        int selectedTaraqim = 0;
        if(masadirId == 1)
        {
            return "أحاديث صحيح البخاريّ كلّها صحيحة";
        }else if(masadirId == 2)
        {
            return " أحاديث صحيح مسلم كلها صحيحة ";
        }

        switch (masadirId) {
            case 1:
                selectedTaraqim = pref.getInt(Constants.book1Hukam, 1);
                break;
            case 2:
                selectedTaraqim = pref.getInt(Constants.book2Hukam, 1);
                break;
            case 3:
                selectedTaraqim = pref.getInt(Constants.book3Hukam, 1);
                break;
            case 4:
                selectedTaraqim = pref.getInt(Constants.book4Hukam, 1);
                break;
            case 5:
                selectedTaraqim = pref.getInt(Constants.book5Hukam, 1);
                break;
            case 6:
                selectedTaraqim = pref.getInt(Constants.book6Hukam, 1);
                break;
        }

        String hukam = "";
        switch (selectedTaraqim) {
            case 0:
                hukam = model.getHadithHukamAjmaliOne() + "(" + Constants.hukam_names_short[masadirId-1][selectedTaraqim] + ")";
                break;
            case 1:
                hukam = model.getHadithHukamAjmaliTwo() + "(" + Constants.hukam_names_short[masadirId-1][selectedTaraqim] + ")";
                break;
            case 2:
                hukam = model.getHadithHukamAjmaliThree() + "(" + Constants.hukam_names_short[masadirId-1][selectedTaraqim] + ")";
                break;
            case 3:
                hukam = model.getHadithHukamAjmaliFour() + "(" + Constants.hukam_names_short[masadirId-1][selectedTaraqim] + ")";
                break;
//
        }

        return  hukam;


    }

    @Override
    protected void onStop() {
        super.onStop();
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void customTextView(TextView view,String masadirName,String kutubName,String babName) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                masadirName+" :");
        spanTxt.append(kutubName);
        if(model.getKutubTamheedUrdu() != null && !model.getKutubTamheedUrdu().equals("")) {
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //Log.d("tryTamheed",model.getKutubTamheedUrdu());
                    showTamheedDialgoue(model.getKutubNameUrdu(),model.getKutubTamheedUrdu());
                }
            }, spanTxt.length() - (kutubName).length(), spanTxt.length(), 0);
        }
        spanTxt.setSpan(new ForegroundColorSpan(Color.RED), spanTxt.length() - (kutubName).length(), spanTxt.length(), 0);

        spanTxt.append("  ");

        spanTxt.append( babName);
        if(model.getBabTamheedUrdu() != null && !model.getBabTamheedUrdu().equals("")) {
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //Log.d("tryTamheed",model.getBabTamheedUrdu());
                    showTamheedDialgoue(model.getBabNameUrdu(),model.getBabTamheedUrdu());
//                    Toast.makeText(getApplicationContext(), "",
//                            Toast.LENGTH_SHORT).show();
                }
            }, spanTxt.length() - (babName).length(), spanTxt.length(), 0);
        }
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), spanTxt.length() -( babName).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }



    public void showTamheedDialgoue(String title,String text)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        TamheedDialogue hasiaHukamDialogue = new TamheedDialogue();
        Bundle b = new Bundle();
        b.putString(Constants.Title, title);
        b.putString(Constants.SEARCHTEXT, text);
        hasiaHukamDialogue.setArguments(b);
        hasiaHukamDialogue.show(ft, "dialog");
    }

    public void showFontDialogue()
    {
        final Dialog dialog = new Dialog(HadeesViewSearchActivity.this);
        dialog.setContentView(R.layout.font_slider_dialgoue);;
        final SeekBar seekBar = dialog.findViewById(R.id.seekBar3);

        seekBar.setMax(70);
        Log.d("tryFont",urduHadeeshTV.getTextSize()+"");
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            seekBar.setProgress((int) pref.getFloat(Constants.FONT,urduHadeeshTV.getTextSize()));
        }else{
            seekBar.setProgress((int) urduHadeeshTV.getTextSize());
        }

        Button changeFont = dialog.findViewById(R.id.button);
        //dialog.setCanceledOnTouchOutside(false);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float newfontsize = (seekBar.getProgress()+150)/10;
                pref.edit().putFloat(Constants.FONT,newfontsize).apply();
                urduHadeeshTV.setTextSize(newfontsize);
                urduTitleTV.setTextSize(newfontsize );
                arabicHadeeshTV.setTextSize(newfontsize);
                arabicTitleTV.setTextSize(newfontsize);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        changeFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
