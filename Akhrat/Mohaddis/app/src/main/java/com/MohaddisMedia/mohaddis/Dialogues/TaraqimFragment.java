package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

import static android.content.Context.MODE_PRIVATE;

public class TaraqimFragment extends Fragment {
    private String []value;
    private String []taraqimName;

    public static TaraqimFragment createInstance(String []value,String []tn)
    {
        TaraqimFragment fragment = new TaraqimFragment();
        fragment.value = value ;
        fragment.taraqimName = tn;

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_for_taraqim_dialogue,container,false);
        ListView listView = (ListView)v.findViewById(R.id.t_list);
        MySimpleArrayAdapter arrayAdapter = new MySimpleArrayAdapter(getContext(),taraqimName,value);
        listView.setAdapter(arrayAdapter);
        return v;
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;
        private final String[] names;
        SharedPreferences pref;
        public MySimpleArrayAdapter(Context context,String []name, String[] values) {
            super(context, -1, name);
            this.context = context;
            this.values = values;
            this.names = name;
            pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.card_for_taraqim_listview_adapter, parent, false);
            TextView textName = (TextView) rowView.findViewById(R.id.textView27);
            TextView textNo = (TextView) rowView.findViewById(R.id.textView28);
            if(pref.getFloat(Constants.FONT,-1) != -1) {
                textName.setTextSize(pref.getFloat(Constants.FONT,-1));
            }
            textName.setText(names[position]);
            textNo.setText(values[position]);


            return rowView;
        }
    }


}

