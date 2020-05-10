package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.Huakm;
import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HukamAdapter extends ArrayAdapter<Huakm> {
    SharedPreferences preff;
    public HukamAdapter(Context context, ArrayList<Huakm> users) {
        super(context, 0, users);
        preff = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Huakm user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_for_hukam_listview_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView21yy);
        TextView tvHuakm = (TextView) convertView.findViewById(R.id.textView3hyy);
        // Populate the data into the template view using the data object
        if(preff.getFloat(Constants.FONT,-1) != -1) {
            tvHuakm.setTextSize(preff.getFloat(Constants.FONT,-1));
            tvName.setTextSize(preff.getFloat(Constants.FONT,-1));
        }
        tvName.setText(user.getName());
        tvHuakm.setText(user.getHukam());
        // Return the completed view to render on screen
        return convertView;
    }
}
