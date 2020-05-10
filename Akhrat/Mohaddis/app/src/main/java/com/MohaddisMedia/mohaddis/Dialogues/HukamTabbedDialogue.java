package com.MohaddisMedia.mohaddis.Dialogues;

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

public class HukamTabbedDialogue extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL,
//                android.R.style.Theme_DeviceDefault_NoActionBar);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String []hukam = getArguments().getStringArray(Constants.HUKAM);
        int masadirId = getArguments().getInt(Constants.MASADIR);
        String hukamTafseli = getArguments().getString(Constants.HUKAM_TAFSELI);
        View rootview = inflater.inflate(R.layout.tabbed_dialgoue_hukam,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout3);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager3);
        DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());

        adapter.addFragment("الحکم التفصیلی", HukamFragment.createInstance(hukamTafseli,0));
        adapter.addFragment("الحكم على الحديث", HukamFragment.createInstance(hukam,masadirId,1));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);

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
