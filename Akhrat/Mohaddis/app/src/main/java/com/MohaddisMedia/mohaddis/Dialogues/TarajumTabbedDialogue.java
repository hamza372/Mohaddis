package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;

public class TarajumTabbedDialogue extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences pref;
    public static TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL,
//                android.R.style.Theme_DeviceDefault_NocActionBar);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pref = getActivity().getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        final String [][]names = new String[][]{{" فضيلة الشيخ عبد الستار الحمّاد (دار السّلام)","شیخ الحدیث مولانا محمد داؤد راز (مکتبہ قدوسیہ)"},
                {"شیخ الحدیث مولانا عبد العزیز علوی (نعمانی کتب خانہ)","الشيخ محمد يحيىٰ سلطان محمود جلالبوري (دار السّلام)"},
                { "فضيلة الشيخ أبو عمار عمر فاروق السعيدي (دار السّلام)" ,"فضیلۃ الشیخ ڈاکٹر عبد الرحمٰن فریوائی ومجلس علمی(دار الدعوۃ، نئی دہلی)"},
                {"٢. فضيلة الدكتور عبد الرحمٰن الفريوائي ومجلس علمي (دار الدّعوة، دهلي)"},
                {  "فضيلة الشيخ حافظ محمّد أمين (دار السّلام)","فضیلۃ الشیخ ڈاکٹر عبد الرحمٰن فریوائی ومجلس علمی(دار الدعوۃ، نئی دہلی)"},
                {  "فضيلة الشيخ محمد عطاء الله ساجد (دار السّلام)","فضیلۃ الشیخ ڈاکٹر عبد الرحمٰن فریوائی ومجلس علمی(دار الدعوۃ، نئی دہلی)"}};
        String []tarjama = getArguments().getStringArray(Constants.TARAJUM);
        String []hasia = getArguments().getStringArray(Constants.HASIA);
        final int masadirId = getArguments().getInt(Constants.MASADIR);
        View rootview = inflater.inflate(R.layout.tabbed_dialogue_tarajum,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout2);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager2);
        title = rootview.findViewById(R.id.textView9);
        DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());

        int defChoice = getTarajumDefaultChoice(masadirId);
        Log.d("tryTC",defChoice+"   "+masadirId);

        if(masadirId != 2){
            if(defChoice == 1) {
                if (tarjama[0] != null) {
                    adapter.addFragment("2", TarajumFragment.createInstance(tarjama[0],names[masadirId - 1][0],hasia[0]));
                }
                if (tarjama[1] != null) {
                    if(names[masadirId - 1].length>1) {
                        adapter.addFragment("1", TarajumFragment.createInstance(tarjama[1], names[masadirId - 1][1], hasia[1]));
                    }
                }
            }else{
                Log.d("tryRes",names[masadirId-1].length+"   "+hasia.length);
                if (tarjama[1] != null) {
                    if(names[masadirId - 1].length>1) {
                        adapter.addFragment("2", TarajumFragment.createInstance(tarjama[1], names[masadirId - 1][1], hasia[1]));
                    }
                }
                if (tarjama[0] != null) {
                    adapter.addFragment("1", TarajumFragment.createInstance(tarjama[0],names[masadirId - 1][0],hasia[0]));
                }

            }}else{
            if(defChoice == 1) {
                if (tarjama[1] != null) {
                    if(names[masadirId - 1].length>1) {
                        adapter.addFragment("2", TarajumFragment.createInstance(tarjama[1], names[masadirId - 1][1], hasia[1]));
                    }
                }
                if (tarjama[0] != null) {
                    adapter.addFragment("1", TarajumFragment.createInstance(tarjama[0],names[masadirId - 1][0],hasia[0]));
                }

            }else{
                if (tarjama[0] != null) {
                    adapter.addFragment("2", TarajumFragment.createInstance(tarjama[0],names[masadirId - 1][0],hasia[0]));
                }
                if (tarjama[1] != null) {
                    if(names[masadirId - 1].length>1) {
                        adapter.addFragment("1", TarajumFragment.createInstance(tarjama[1], names[masadirId - 1][1], hasia[1]));
                    }
                    }

            }
        }


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //  viewPager.setCurrentItem(1);
        if(defChoice == 1) {
            title.setText(names[masadirId - 1][1]);
        }else{
            if(names[masadirId-1].length>1) {
                title.setText(names[masadirId - 1][0]);
            }
        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                title.setText(names[masadirId - 1][i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        ImageView backBtn = rootview.findViewById(R.id.imageView16);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(tarjama[1] != null) {
            viewPager.setCurrentItem(1);
        }
        Log.d("tryTL",available(tarjama)+"");
        return rootview;
    }

    public int available(String[] tarjuma)
    {
        int a = 0;
        for(int i =0;i<tarjuma.length;i++)
        {
            if(tarjuma[i] != null)
            {
                a++;
                Log.d("tryTL",tarjuma[i]+"");
            }
        }
        return a;
    }

    public int getTarajumDefaultChoice(int masadirID)
    {
        switch(masadirID)
        {
            case 1:
                return pref.getInt(Constants.book1Tarajum,0);

            case 2:
                return pref.getInt(Constants.book2Tarajum,0);

            case 3:
                return pref.getInt(Constants.book3Tarajum,0);

            case 4:
                return pref.getInt(Constants.book4Tarajum,0);

            case 5:
                return pref.getInt(Constants.book5Tarajum,0);

            case 6:
                return pref.getInt(Constants.book6Tarajum,0);

        }
        return 0;
    }

}
