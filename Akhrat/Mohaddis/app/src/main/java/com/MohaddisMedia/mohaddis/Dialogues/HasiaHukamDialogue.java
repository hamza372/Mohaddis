package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;

public class HasiaHukamDialogue extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences pref;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        pref = getContext().getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        String hasia = getArguments().getString(Constants.HASIA);
        String hukam = getArguments().getString(Constants.HUKAM);
        View rootview = inflater.inflate(R.layout.tabbed_sharha_dialogue,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout4);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager4);
        DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());


        adapter.addFragment(" اردو حاشیہ", HasiaHukamFragment.createInstance(hasia,pref));
        //  adapter.addFragment("Ø§Ù„Ø­Ú©Ù… Ø§Ù„ØªÙØµÛŒÙ„ÛŒ", HasiaHukamFragment.createInstance(hukam));


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageView backBtn = rootview.findViewById(R.id.imageView16);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootview;
    }


}
