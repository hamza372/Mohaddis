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
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

public class MozuTabbedDialogue extends DialogFragment {
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

        ArrayList<TopicsDataModel> topicsDataModels =  getArguments().getParcelableArrayList(Constants.MOZU);
        int masadirId = getArguments().getInt(Constants.MASADIR);
        View rootview = inflater.inflate(R.layout.tabbed_dialogue,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);
        DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());
        adapter.addFragment("موضوعات", MozuFragment.createInstance(Constants.URDU,topicsDataModels));
        adapter.addFragment("المواضيع", MozuFragment.createInstance(Constants.ARABIC,topicsDataModels));
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
