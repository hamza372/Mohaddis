package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class TarajumFragment  extends Fragment {

    public String tarjamaText;
    public String hasiaText;
    public String writerName;
    SharedPreferences pref;
    public static TarajumFragment createInstance(String tarjama,String writerName,String hasiaText)
    {
        TarajumFragment fragment = new TarajumFragment();
        fragment.writerName = writerName;
        fragment.tarjamaText = tarjama;
        fragment.hasiaText = hasiaText;
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        pref = getContext().getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        View v = inflater.inflate(R.layout.card_for_tarajum_dialogue,container,false);
        TextView tarjama = (TextView)v.findViewById(R.id.textView4tar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tarjama.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        TextView hasiaTitle= (TextView)v.findViewById(R.id.textView4tar3);
        TextView hasia= (TextView)v.findViewById(R.id.textView4tar2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hasia.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        TextView name = (TextView)v.findViewById(R.id.textView17);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            tarjama.setTextSize(pref.getFloat(Constants.FONT,-1));
            name.setTextSize(pref.getFloat(Constants.FONT,-1));
        }
        name.setText(writerName);

        tarjama.setText(Html.fromHtml(tarjamaText));
        hasiaTitle.setText("اردو حاشیہ");

//        Log.d("tryHasia",hasiaText);
        if(hasiaText != null && !hasiaText.equals("w") && !hasiaText.equals("0")) {
            if(pref.getFloat(Constants.FONT,-1) != -1) {
                hasia.setTextSize(pref.getFloat(Constants.FONT,-1));
            }
            hasia.setText(Html.fromHtml(hasiaText));
        }else{
            hasia.setText("");
        }
        return v;
    }
}