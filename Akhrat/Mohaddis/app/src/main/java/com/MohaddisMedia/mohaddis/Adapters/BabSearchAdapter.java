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

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.AbwabDataModel;
import com.MohaddisMedia.mohaddis.Hadees_Bab_Kutub_View_Activites.AhadeesViewActivity;
import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BabSearchAdapter extends RecyclerView.Adapter<BabSearchAdapter.viewHolder> {
    Context context;
    ArrayList<AbwabDataModel> dataList;
    String language;
    Activity activity;
    String searchText = "-1";
    Boolean showMasadir = false;
    SharedPreferences pref;
    public BabSearchAdapter(Context context,Activity activity)
    {
        this.context = context;
        this.activity = activity;
        dataList = new ArrayList<>();
        language = Constants.URDU;
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public BabSearchAdapter(Context context, Boolean showMasadir)
    {
        this.context = context;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
        language = Constants.URDU;
    }
    BabSearchAdapter(Context context,ArrayList<AbwabDataModel> model)
    {
        this.context = context;
        this.dataList = model;
        language = Constants.URDU;
    }

    public BabSearchAdapter(Context context,  String searchText,String language) {
        this.context = context;
        this.language = language;
        this.searchText = searchText;
        dataList = new ArrayList<>();
        language = Constants.URDU;
    }

    public BabSearchAdapter(Context context,Activity activity,  String searchText,String language,Boolean showMasadir) {
        this.context = context;
        this.language = language;
        this.activity = activity;
        this.searchText = searchText;
        dataList = new ArrayList<>();
        this.showMasadir = showMasadir;
        language = Constants.URDU;
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
    }

    public void addBab(AbwabDataModel model)
    {
        dataList.add(model);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_bab_search,viewGroup,false);
        return new viewHolder(view);
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int i) {
        String str = null;
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            holder.babNameTV.setTextSize(pref.getFloat(Constants.FONT,-1));
        }
        if(language.equals(Constants.URDU)){
            holder.babNameTV.setTextAppearance(activity,R.style.StyleUrduFont);
            str = (i+1)+("."+dataList.get(i).getBabNameUrdu());
        }else{
            holder.babNameTV.setTextAppearance(activity,R.style.StyleArabicFont);
            str =(i+1)+("."+dataList.get(i).getBabNameArabic());
        }


        if(!searchText.equals("-1") && str.contains(searchText)) {
            str = str.replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>");
        }
        holder.babNameTV.setText(Html.fromHtml(str));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tryBabId",dataList.get(i).getId()+"");
                Intent intent = null;
                intent = new Intent(context, AhadeesViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constants.ABWAB,dataList.get(i));
                context.startActivity(intent);
                if(intent != null) {
                    activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        TextView babNameTV;
        ConstraintLayout layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            babNameTV = (TextView)itemView.findViewById(R.id.textView1B);
            layout = (ConstraintLayout)itemView.findViewById(R.id.bcard);

        }
    }
}
