package com.MohaddisMedia.mohaddis.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites.AbwabViewActivity;
import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.KutubDataModel;
import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class KutubSearchAdapter extends RecyclerView.Adapter<KutubSearchAdapter.viewHolder> {
    Context context;
    ArrayList<KutubDataModel> dataList;
    String searchText = "-1";
    String language;
    Boolean showMasadir = false;
    Activity activity;
    SharedPreferences pref;
    public KutubSearchAdapter(Context context,Activity activity)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.activity = activity;
        language = Constants.URDU;
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    KutubSearchAdapter(Context context, ArrayList<KutubDataModel> model)
    {
        this.context = context;
        this.dataList = model;
        language = Constants.URDU;
    }

    public KutubSearchAdapter(Context context, String searchText, String language) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        language = Constants.URDU;
    }

    public KutubSearchAdapter(Context context,Activity activity, String searchText, String language,Boolean showMasadir) {
        this.context = context;
        this.searchText = searchText;
        this.language = language;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
        this.activity = activity;
        language = Constants.URDU;
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }

    public KutubDataModel getKutub(int postition){
        return dataList.get(postition);
    }
    public void addKitab(KutubDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_kutub_search,viewGroup,false);
        return new viewHolder(view);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
        String str = null;
        Log.d("tryLang",language+"    "+searchText);
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            holder.kutubNameTV.setTextSize(pref.getFloat(Constants.FONT,-1));
            holder.masadirNameTV.setTextSize(pref.getFloat(Constants.FONT,-1));
        }
        if(language.equals(Constants.URDU)){
            holder.kutubNameTV.setTextAppearance(activity,R.style.StyleUrduFont);
            str = (i+1)+("."+dataList.get(i).getKutubNameUrdu());
        }else{
            holder.kutubNameTV.setTextAppearance(activity,R.style.StyleArabicFont);
            str = (i+1)+("."+dataList.get(i).getKutubNameArabic());
        }

        if(!searchText.equals("-1") && str.contains(searchText)) {
            str = str.replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>");
        }
        if(language.equals(Constants.URDU)){
            holder.masadirNameTV.setText(dataList.get(i).getMasadirNameUrdu());
        }else{
            holder.masadirNameTV.setText(dataList.get(i).getMasadirNameArabic());
        }

        if(showMasadir)
        {
            holder.masadirNameTV.setVisibility(View.VISIBLE);
        }
        holder.kutubNameTV.setText(Html.fromHtml(str));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO check weather adapter was shown in kutbSearch or kutubView
                Intent intent = null;
                intent = new Intent(context, AbwabViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("tryExploring",dataList.get(i).getKutubNameArabic()+"   "+dataList.get(i).getId());
                intent.putExtra(Constants.KUTUB,dataList.get(i));
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

        TextView masadirNameTV;
        TextView kutubNameTV;
        ConstraintLayout layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            masadirNameTV = (TextView)itemView.findViewById(R.id.textView2K);
            kutubNameTV = (TextView)itemView.findViewById(R.id.textView3K);
            layout = (ConstraintLayout)itemView.findViewById(R.id.kcard);

        }
    }
}
