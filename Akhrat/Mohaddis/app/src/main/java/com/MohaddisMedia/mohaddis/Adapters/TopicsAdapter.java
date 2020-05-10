package com.MohaddisMedia.mohaddis.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.TopicsDataModel;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Topics.TopicsActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.viewHolder> {
    Context context;
    ArrayList<TopicsDataModel> dataList;
    Activity activity;
    String searchText = "-1";
    SharedPreferences pref;
    public TopicsAdapter(Context context,Activity activity)
    {
        this.context = context;
        this.activity = activity;
        dataList = new ArrayList<>();
        language = Constants.URDU;
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }
    public TopicsAdapter(Context context,ArrayList<TopicsDataModel> model)
    {
        this.context = context;
        this.dataList = model;
        language = Constants.URDU;
        Log.d("tryData",dataList.size()+"");
    }

    public void addTopic(TopicsDataModel model)
    {
        //Log.d("trySearch",model.getTextHadeesUrdu());
        dataList.add(model);
        notifyDataSetChanged();

    }

    public void setSearchText(String searchText){
        this.searchText = searchText;
    }
    public ArrayList<TopicsDataModel> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<TopicsDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_topic_recycler,viewGroup,false);
        return new viewHolder(view);
    }
    String language = Constants.URDU;
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {

        holder.urduTitle.setTextSize(pref.getFloat(Constants.FONT,-1));
        if(language.equals(Constants.URDU)) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/urdu_font.ttf");
            holder.urduTitle.setTypeface(tf);
             //holder.urduTitle.setTextAppearance(activity,R.style.StyleUrduFont);
            holder.urduTitle.setText(Html.fromHtml((i + 1) + ("." + (dataList.get(i).getTopicUrdu().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")))));
        }else{
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/arabic_font.ttf");
            holder.urduTitle.setTypeface(tf);
            //holder.urduTitle.setTextAppearance(activity,R.style.StyleArabicFont);
            holder.urduTitle.setText(Html.fromHtml((i + 1) + ("." + (dataList.get(i).getTopicArabic().replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>")))));
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TopicsActivity.class);
                intent.putExtra(Constants.Topic, dataList.get(i).getId());
                intent.putExtra(Constants.Title, dataList.get(i).getTopicUrdu());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView urduTitle;

        ConstraintLayout mainLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = (ConstraintLayout) itemView.findViewById(R.id.tlayout);
            urduTitle = (TextView) itemView.findViewById(R.id.textView10);
        }
    }
}

