package com.MohaddisMedia.mohaddis.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

public class SimpleListAdapter extends ArrayAdapter<String> {
    public SimpleListAdapter(Context context, String[] items) {
        super(context, 0, items);
    }

    public SimpleListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.idd);
        tvName.setText(item);
        // Return the completed view to render on screen
        return convertView;
    }
}