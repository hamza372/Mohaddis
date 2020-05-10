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
import com.MohaddisMedia.mohaddis.DBHelper;
import com.MohaddisMedia.mohaddis.DataModels.HadeesDataModel;
import com.MohaddisMedia.mohaddis.HadeesViewActivity;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Search.HadeesViewSearchActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class HadeesSearchAdapter extends RecyclerView.Adapter<HadeesSearchAdapter.viewHolder> {
    Context context;
    ArrayList<HadeesDataModel> dataList;
    SharedPreferences pref;
    Boolean showMasadir = false;
    String searchText = "-1";
    Activity activity;
    String query;
    String language;
    public HadeesSearchAdapter(Context context, String searchText, Activity activity) {
        this.context = context;
        this.searchText = searchText;
        this.activity = activity;
        dataList = new ArrayList<>();
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        language = Constants.URDU;

    }

    public HadeesSearchAdapter(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
        dataList = new ArrayList<>();
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        language = Constants.URDU;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public HadeesSearchAdapter(Context context, Boolean showMasadir)
    {
        this.context = context;
        dataList = new ArrayList<>();
        pref = context.getSharedPreferences("com.example.mohaddis",MODE_PRIVATE);
        this.showMasadir = showMasadir;
        language = Constants.URDU;
    }
    public  HadeesSearchAdapter(Context context,ArrayList<HadeesDataModel> model)
    {
        this.context = context;
        this.dataList = model;
        language = Constants.URDU;
        Log.d("tryData",dataList.size()+"");
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void addHadees(HadeesDataModel model)
    {
        dataList.add(model);
        notifyDataSetChanged();
    }

    public ArrayList<HadeesDataModel> getDataList() {
        return dataList;
    }

    public void clear()
    {
        dataList.clear();
    }

    public void setDataList(ArrayList<HadeesDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_for_hadees_search,viewGroup,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int i) {
        float pp =  getTaraqimBasedHNo(pref, dataList.get(holder.getAdapterPosition()).getMasadirId(), dataList.get(holder.getAdapterPosition()));
        Log.d("trySHadeesAdapter", pp + "");
        Log.d("trySHadeesAdapter", dataList.get(i).getHadeesNumberTOne() + "");
        //String str =
        String str = null;
        if(pref.getFloat(Constants.FONT,-1) != -1) {
            holder.urduTitle.setTextSize(pref.getFloat(Constants.FONT,-1));
        }
        if(language.equals(Constants.URDU)){
            holder.urduTitle.setTextAppearance(activity,R.style.StyleUrduFont);
            str = pp+" "+Html.fromHtml(dataList.get(i).getTextHadeesUrdu().trim());
        }else{
            holder.urduTitle.setTextAppearance(activity,R.style.StyleArabicFont);
            str = pp+" "+Html.fromHtml(dataList.get(i).getTextHadeesArabic().trim());
        }

        if(!searchText.equals("-1") && str.contains(searchText)) {
            str = str.replaceAll(searchText, "<span style=\"background-color:yellow;\">" + searchText + "</span>");
        }

        Log.d("tryFieldText",dataList.get(i).getTextHadeesUrdu().trim());
        holder.urduTitle.setText(Html.fromHtml(str));
        if(showMasadir)
        {
            //      holder.masadirName.setVisibility(View.VISIBLE);
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(query == null) {
                    float pp =  getTaraqimBasedHNo(pref, dataList.get(holder.getAdapterPosition()).getMasadirId(), dataList.get(holder.getAdapterPosition()));
                    Intent intent = new Intent(context, HadeesViewActivity.class);
                    intent.putExtra(Constants.HADESSNO, pp);
                    intent.putExtra(Constants.ABWAB, dataList.get(holder.getAdapterPosition()).getBabId());
                    intent.putExtra(Constants.MASADIR, dataList.get(holder.getAdapterPosition()).getMasadirId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("trySHadeesAdapter", pp + "");
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }else{
                    int pp = (int)getTaraqimBasedHNo(pref,dataList.get(holder.getAdapterPosition()).getMasadirId(),dataList.get(holder.getAdapterPosition()));
                    Intent intent = new Intent(context, HadeesViewSearchActivity.class);
                    intent.putExtra(Constants.HADESSNO,pp);
                    intent.putExtra(Constants.QUERY,query);
                    intent.putExtra(Constants.POSITION,i);
                    intent.putExtra(Constants.LANGUAGE,language);
                    intent.putExtra(Constants.SEARCHTEXT,searchText);
                    intent.putExtra(Constants.ABWAB,dataList.get(holder.getAdapterPosition()).getBabId());
                    intent.putExtra(Constants.MASADIR,dataList.get(holder.getAdapterPosition()).getMasadirId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("trySHadeesAdapter",pp+"");
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView urduTitle;
        // TextView masadirName;
        ConstraintLayout mainLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = (ConstraintLayout)itemView.findViewById(R.id.hcard);
            urduTitle = (TextView)itemView.findViewById(R.id.textView3AS);
            // masadirName = (TextView)itemView.findViewById(R.id.textView2AS);
        }
    }


    public  float getTaraqimBasedHNo(SharedPreferences pref, int bookId, HadeesDataModel model)
    {
        int selectedTaraqim = 0;
        switch(bookId)
        {
            case 1:
                selectedTaraqim = pref.getInt(Constants.book1Tarqeem,1);
                break;
            case 2:
                selectedTaraqim = pref.getInt(Constants.book2Tarqeem,1);
                break;
            case 3:
                selectedTaraqim = pref.getInt(Constants.book3Tarqeem,2);
                break;
            case 4:
                selectedTaraqim = pref.getInt(Constants.book4Tarqeem,1);
                break;
            case 5:
                selectedTaraqim = pref.getInt(Constants.book5Tarqeem,1);
                break;
            case 6:
                selectedTaraqim = pref.getInt(Constants.book6Tarqeem,1);
                break;
        }

        Log.d("tryTarqim",selectedTaraqim+"   "+model.getHadeesNumberTOne()+"   "+model.getHadeesNumberTSeven());
        String tarqimName = DBHelper.HadeesEntry.HadeesNumberTOne;
        switch(selectedTaraqim)
        {
            case 0:
                return  model.getHadeesNo();

            case 1:
                return  model.getHadeesNumberTOne();


            case 2:
                return  model.getHadeesNumberTTwo();

            case 3:
                return  model.getHadeesNumberTThree();

            case 4:
                return  model.getHadeesNumberTFour();

            case 5:
                return  model.getHadeesNumberTFive();

            case 6:
                return  model.getHadeesNumberTSix();

            case 7:
                return  model.getHadeesNumberTSeven();

            case 8:
                return  model.getHadeesNumberTEight();

            case 9:
                return  model.getHadeesNumberTNine();

            case 10:
                return  model.getHadeesNumberTTen();

        }

        return  model.getHadeesNumberTOne();
    }

}
