package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DB.TopicsToHadithsDBHelper;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Topics.TopicsActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MozuFragment extends Fragment {

    public String language;
    ArrayList<TopicsDataModel> models;
    SharedPreferences pref;
    public static MozuFragment createInstance(String language, ArrayList<TopicsDataModel> models)
    {
        MozuFragment fragment = new MozuFragment();
        fragment.language = language;
        fragment.models = models;
        return fragment;
    }

    public MozuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        pref = getContext().getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        String[] topicName = new String[models.size()];
        for(int i=0;i<topicName.length;i++)
        {
            TopicsDataModel topicsDataModel = new TopicsToHadithsDBHelper(getContext()).findSeniorParent(models.get(i).getId());
            if(language.equals(Constants.URDU)) {
                topicName[i] = (i+1)+": "+models.get(i).getTopicUrdu()+"("+topicsDataModel.getTopicUrdu()+")";
            }else{
                topicName[i] = (i+1)+": "+models.get(i).getTopicArabic()+"("+topicsDataModel.getTopicArabic()+")";;
            }
        }

        View v = inflater.inflate(R.layout.card_for_taraqim_dialogue,container,false);
        ListView listView = (ListView)v.findViewById(R.id.t_list);
        SimpleListAdapter simpleListAdapter = new SimpleListAdapter(getContext(),0,topicName);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.simple_list_item_general,topicName);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TopicsActivity.class);
                intent.putExtra(Constants.Topic,models.get(position).getId());
                intent.putExtra(Constants.Title,models.get(position).getTopicUrdu());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return v;
    }

    class SimpleListAdapter extends ArrayAdapter<String[]>{

        String[] data;
        public SimpleListAdapter(@NonNull Context context, int resource,String[] array) {
            super(context, resource);
            data = array;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item_general,parent,false);
            TextView tv =v.findViewById(R.id.tvtv);
            if(pref.getFloat(Constants.FONT,-1) != -1) {
                tv.setTextSize( pref.getFloat(Constants.FONT,-1));
            }
            tv.setText(data[position]);
            return v;
        }
    }
}
