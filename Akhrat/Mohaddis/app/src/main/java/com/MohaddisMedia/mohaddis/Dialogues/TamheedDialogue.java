package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;

public class TamheedDialogue extends DialogFragment
{
    public static TamheedDialogue createInstance(String title,String text){
        TamheedDialogue tamheedDialogue = new TamheedDialogue();
        Bundle b = new Bundle();
        b.putString(Constants.Title, title);
        b.putString(Constants.SEARCHTEXT, text);
        tamheedDialogue.setArguments(b);
        return tamheedDialogue;
    }
    SharedPreferences pref;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView titleTV;
        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
            pref = getActivity().getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
            String title = getArguments().getString(Constants.Title);
            String text = getArguments().getString(Constants.SEARCHTEXT);
            View rootview = inflater.inflate(R.layout.tabbed_sharha_dialogue, container, false);

            tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout4);
            viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager4);
            titleTV = rootview.findViewById(R.id.toolbartitlek);
            titleTV.setText(title);
            DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());


            adapter.addFragment(title, HasiaHukamFragment.createInstance(text,pref));
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

//            TextView titelTv = rootview.findViewById(R.id.toolbartitlek);
//            TextView textViewTv = rootview.findViewById(R.id.textView24);
//            ImageView imageView = rootview.findViewById(R.id.imageView16);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//            if(pref.getFloat(Constants.FONT,-1) != -1) {
//                titelTv.setTextSize( pref.getFloat(Constants.FONT,-1));
//                textViewTv.setTextSize( pref.getFloat(Constants.FONT,-1));
//            }
//            titelTv.setText(title);
//            if(text.length()>0) {
//                textViewTv.setText(Html.fromHtml(text));
//            }
            return rootview;
        }

}
