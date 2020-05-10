package com.MohaddisMedia.mohaddis.Search;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

public class ChoseBookDialgue extends DialogFragment {
    SharedPreferences pref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_for_book_choser_dialogue,container,false);
        pref = getActivity().getSharedPreferences("com.example.mohaddis", Context.MODE_PRIVATE);

        CheckBox allBooks = v.findViewById(R.id.checkBoxball);
        final CheckBox book1 = v.findViewById(R.id.checkBoxb1);
        final CheckBox book2 = v.findViewById(R.id.checkBoxb2);
        final CheckBox book3 = v.findViewById(R.id.checkBoxb3);
        final CheckBox book4 = v.findViewById(R.id.checkBoxb4);
        final CheckBox book5 = v.findViewById(R.id.checkBoxb5);
        final CheckBox book6 = v.findViewById(R.id.checkBoxb6);

        if(pref.getBoolean(Constants.book1,true) && pref.getBoolean(Constants.book2,true) && pref.getBoolean(Constants.book3 ,true) && pref.getBoolean(Constants.book4,true) && pref.getBoolean(Constants.book5,true) && pref.getBoolean(Constants.book6,true) )
        {
            allBooks.setChecked(true);
        }
        book1.setChecked(pref.getBoolean(Constants.book1,true));
        book2.setChecked(pref.getBoolean(Constants.book2,true));
        book3.setChecked(pref.getBoolean(Constants.book3,true));
        book4.setChecked(pref.getBoolean(Constants.book4,true));
        book5.setChecked(pref.getBoolean(Constants.book5,true));
        book6.setChecked(pref.getBoolean(Constants.book6,true));


        allBooks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book1,true).apply();
                    pref.edit().putBoolean(Constants.book2,true).apply();
                    pref.edit().putBoolean(Constants.book3,true).apply();
                    pref.edit().putBoolean(Constants.book4,true).apply();
                    pref.edit().putBoolean(Constants.book5,true).apply();
                    pref.edit().putBoolean(Constants.book6,true).apply();

                    book1.setChecked(true);
                    book2.setChecked(true);
                    book3.setChecked(true);
                    book4.setChecked(true);
                    book5.setChecked(true);
                    book6.setChecked(true);

                }else{
                    pref.edit().putBoolean(Constants.book1,false).apply();
                    pref.edit().putBoolean(Constants.book2,false).apply();
                    pref.edit().putBoolean(Constants.book3,false).apply();
                    pref.edit().putBoolean(Constants.book4,false).apply();
                    pref.edit().putBoolean(Constants.book5,false).apply();
                    pref.edit().putBoolean(Constants.book6,false).apply();

                    book1.setChecked(false);
                    book2.setChecked(false);
                    book3.setChecked(false);
                    book4.setChecked(false);
                    book5.setChecked(false);
                    book6.setChecked(false);
                }
            }
        });

        book1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book1,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book1,false).apply();
                }
            }
        });

        book2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book2,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book2,false).apply();
                }
            }
        });

        book3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book3,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book3,false).apply();
                }
            }
        });

        book4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book4,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book4,false).apply();
                }
            }
        });

        book5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book5,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book5,false).apply();
                }
            }
        });

        book6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pref.edit().putBoolean(Constants.book6,true).apply();
                }else{
                    pref.edit().putBoolean(Constants.book6,false).apply();
                }
            }
        });

        ImageView backBtn = v.findViewById(R.id.imageView16);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return  v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
