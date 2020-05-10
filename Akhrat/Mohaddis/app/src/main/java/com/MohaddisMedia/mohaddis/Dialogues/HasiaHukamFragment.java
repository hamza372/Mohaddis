package com.MohaddisMedia.mohaddis.Dialogues;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;

public class HasiaHukamFragment extends Fragment {

    public String text;
    static SharedPreferences preff;
    Context context;
    public static HasiaHukamFragment createInstance(String text,SharedPreferences pref)
    {
        HasiaHukamFragment fragment = new HasiaHukamFragment();
        fragment.text = text;
        preff = pref;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_for_hasia_dialgue,container,false);
        TextView tarjama = (TextView)v.findViewById(R.id.textView4hasia);
        if(preff.getFloat(Constants.FONT,-1) != -1) {
            tarjama.setTextSize(preff.getFloat(Constants.FONT,-1));
        }
        tarjama.setText(Html.fromHtml(text));
        return v;
    }
}