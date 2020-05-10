package com.MohaddisMedia.mohaddis.Settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

public class Settings extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static SharedPreferences pref;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("ڈیفالٹ سیٹنگ منتخب کریں");
        pref = getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mViewPager.setCurrentItem(2);


        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.primary));
//        .setStatusBarBackgroundColor(
//                getResources().getColor(R.color.primary));
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public void backButton(View v)
    {
        onBackPressed();
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        public static PlaceholderFragment newInstance(int sectionNumber) {
            Log.d("tryTaraqim",sectionNumber+"");
            String type = "";
            if(sectionNumber == 3) {
                type = Constants.TARAQIM;
            }else if(sectionNumber == 2)
            {
                type = Constants.TARAJUM;
            }else if(sectionNumber == 1)
            {
                type = Constants.HUKAM;
            }
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(Constants.TARAJUM,type);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            final Spinner b1 = rootView.findViewById(R.id.spinnerst1);
            final Spinner b2 = rootView.findViewById(R.id.spinnerst2);
            final Spinner b3 = rootView.findViewById(R.id.spinnerst3);
            final Spinner b4 = rootView.findViewById(R.id.spinnerst4);
            final Spinner b5 = rootView.findViewById(R.id.spinnerst5);
            final Spinner b6 = rootView.findViewById(R.id.spinnerst6);

            // 1 is for tarajum 2 for taraqim 3 for
            final String type = getArguments().getString(Constants.TARAJUM);
            Log.d("tryTaraqim",type+"   "+Constants.TARAQIM);
            if(type.equals(Constants.TARAJUM))
            {
                ArrayAdapter<String>[] tarajumAdapter = new ArrayAdapter[6];
                for(int i=0;i<6;i++)
                {
                    tarajumAdapter[i] = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,Constants.tarajum_names[i]);
                }
                b1.setAdapter(tarajumAdapter[0]);
                b2.setAdapter(tarajumAdapter[1]);
                b3.setAdapter(tarajumAdapter[2]);
                b4.setAdapter(tarajumAdapter[3]);
                b5.setAdapter(tarajumAdapter[4]);
                b6.setAdapter(tarajumAdapter[5]);

                b1.setSelection(pref.getInt(Constants.book1Tarajum,0));
                b2.setSelection(pref.getInt(Constants.book2Tarajum,0));
                b3.setSelection(pref.getInt(Constants.book3Tarajum,0));
                b4.setSelection(pref.getInt(Constants.book4Tarajum,0));
                b5.setSelection(pref.getInt(Constants.book5Tarajum,0));
                b6.setSelection(pref.getInt(Constants.book6Tarajum,0));
            }

            //TODO taraqim code
            else if(type.equals(Constants.TARAQIM))
            {

                ArrayAdapter<String>[] tarajumAdapter = new ArrayAdapter[6];
                for(int i=0;i<6;i++)
                {
                    tarajumAdapter[i] = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,Constants.taraqim_name[i]);
                }
                b1.setAdapter(tarajumAdapter[0]);
                b2.setAdapter(tarajumAdapter[1]);
                b3.setAdapter(tarajumAdapter[2]);
                b4.setAdapter(tarajumAdapter[3]);
                b5.setAdapter(tarajumAdapter[4]);
                b6.setAdapter(tarajumAdapter[5]);

                b1.setSelection(pref.getInt(Constants.book1Tarqeem,1));
                b2.setSelection(pref.getInt(Constants.book2Tarqeem,1));
                b3.setSelection(pref.getInt(Constants.book3Tarqeem,1));
                b4.setSelection(pref.getInt(Constants.book4Tarqeem,1));
                b5.setSelection(pref.getInt(Constants.book5Tarqeem,1));
                b6.setSelection(pref.getInt(Constants.book6Tarqeem,1));
            }

            //TODO hukam dialgue
            else if(type.equals(Constants.HUKAM))
            {

                ArrayAdapter<String>[] tarajumAdapter = new ArrayAdapter[6];
                for(int i=0;i<6;i++)
                {
                    tarajumAdapter[i] = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,Constants.hukam_names[i]);
                }
                b1.setAdapter(tarajumAdapter[0]);
                b2.setAdapter(tarajumAdapter[1]);
                b3.setAdapter(tarajumAdapter[2]);
                b4.setAdapter(tarajumAdapter[3]);
                b5.setAdapter(tarajumAdapter[4]);
                b6.setAdapter(tarajumAdapter[5]);

                b1.setSelection(pref.getInt(Constants.book1Hukam,0));
                b2.setSelection(pref.getInt(Constants.book2Hukam,0));
                b3.setSelection(pref.getInt(Constants.book3Hukam,0));
                b4.setSelection(pref.getInt(Constants.book4Hukam,0));
                b5.setSelection(pref.getInt(Constants.book5Hukam,0));
                b6.setSelection(pref.getInt(Constants.book6Hukam,0));
            }
            Button save = (Button)rootView.findViewById(R.id.button2);
            save.setText("ڈیفالٹ سیٹنگ محفوظ کریں");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int choices[] = new int[6];
                    choices[0] = b1.getSelectedItemPosition();
                    choices[1] = b2.getSelectedItemPosition();
                    choices[2] = b3.getSelectedItemPosition();
                    choices[3] = b4.getSelectedItemPosition();
                    choices[4] = b5.getSelectedItemPosition();
                    choices[5] = b6.getSelectedItemPosition();

                    if(type.equals(Constants.TARAQIM)) {
                        pref.edit().putInt(Constants.book1Tarqeem, choices[0]).apply();
                        pref.edit().putInt(Constants.book2Tarqeem, choices[1]).apply();
                        pref.edit().putInt(Constants.book3Tarqeem, choices[2]).apply();
                        pref.edit().putInt(Constants.book4Tarqeem, choices[3]).apply();
                        pref.edit().putInt(Constants.book5Tarqeem, choices[4]).apply();
                        pref.edit().putInt(Constants.book6Tarqeem, choices[5]).apply();
                        Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
                    }else if(type.equals(Constants.TARAJUM))
                    {
                        pref.edit().putInt(Constants.book1Tarajum, choices[0]).apply();
                        pref.edit().putInt(Constants.book2Tarajum, choices[1]).apply();
                        pref.edit().putInt(Constants.book3Tarajum, choices[2]).apply();
                        pref.edit().putInt(Constants.book4Tarajum, choices[3]).apply();
                        pref.edit().putInt(Constants.book5Tarajum, choices[4]).apply();
                        pref.edit().putInt(Constants.book6Tarajum, choices[5]).apply();
                        Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
                    }else if(type.equals(Constants.HUKAM))
                    {
                        pref.edit().putInt(Constants.book1Hukam, choices[0]).apply();
                        pref.edit().putInt(Constants.book2Hukam, choices[1]).apply();
                        pref.edit().putInt(Constants.book3Hukam, choices[2]).apply();
                        pref.edit().putInt(Constants.book4Hukam, choices[3]).apply();
                        pref.edit().putInt(Constants.book5Hukam, choices[4]).apply();
                        pref.edit().putInt(Constants.book6Hukam, choices[5]).apply();
                        Toast.makeText(getContext(), "saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return "Ø§Ù„ØªØ±Ø§Ù‚ÙŠÙ…";
                case 1:
                    return "Ø§Ù„ØªØ±Ø§Ø¬Ù…";
                case 2:
                    return "Ø§Ù„Ø­Ú©Ù… Ø¹Ù„ÛŒ Ø§Ù„Ø­Ø¯ÛŒØ«";
                default:
                    return null;
            }
        }

    }


}
