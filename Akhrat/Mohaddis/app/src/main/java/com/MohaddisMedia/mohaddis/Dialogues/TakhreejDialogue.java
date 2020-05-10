package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.TakhreejDataModel;
import com.MohaddisMedia.mohaddis.HadeesViewActivity;
import com.MohaddisMedia.mohaddis.R;
import com.MohaddisMedia.mohaddis.Utilites;

import java.util.ArrayList;

public class TakhreejDialogue extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ArrayList<TakhreejDataModel> takhreejDataModelArrayList = getArguments().getParcelableArrayList(Constants.TAKHREEJ);
        //Log.d("tryTakhreej",takhreejDataModelArrayList.size()+"");
        View rootview = inflater.inflate(R.layout.takhreej_dialogue,container,false);

        final ArrayList<Integer>[] arrayLists = new ArrayList[6];
        ArrayList<Integer> b1 = new ArrayList<>();
        arrayLists[0] = b1;
        ArrayList<Integer> b2 = new ArrayList<>();
        arrayLists[1] = b2;
        ArrayList<Integer> b3 = new ArrayList<>();
        arrayLists[2] = b3;
        ArrayList<Integer> b4 = new ArrayList<>();
        arrayLists[3] = b4;
        ArrayList<Integer> b5 = new ArrayList<>();
        arrayLists[4] = b5;
        ArrayList<Integer> b6 = new ArrayList<>();
        arrayLists[5] = b6;
        for(int i =0;i<takhreejDataModelArrayList.size();i++)
        {
            Log.d("tryTAv",i+"   "+takhreejDataModelArrayList.size()+"   "+takhreejDataModelArrayList.get(i).getBookIdTakhreej()+"   "+
                    takhreejDataModelArrayList.get(i).getBookNamePresent()+"   "+takhreejDataModelArrayList.get(i).getBookNameTakhreej());
            if(takhreejDataModelArrayList.get(i).getBookIdTakhreej()>0 )
            {
                Log.d("tryInTakhreej",takhreejDataModelArrayList.get(i).getBookIdTakhreej()+"");
                int pos = takhreejDataModelArrayList.get(i).getBookIdTakhreej()-1;
                ArrayList<Integer> takhreejDataModel = arrayLists[pos];
                takhreejDataModel.add(takhreejDataModelArrayList.get(i).getHeadeesTakhreejId());
                arrayLists[pos] = takhreejDataModel;

            }
        }


        //TODO book1

        Log.d("tryTakhreej",arrayLists[0].size()+" v  "+arrayLists[1].size());
        LinearLayout layout1 = (LinearLayout)rootview.findViewById(R.id.l1);
        TextView tv1 = (TextView)rootview.findViewById(R.id.tvb1);
        tv1.setText(Constants.BUKHARI);
        ListView list1 = (ListView)rootview.findViewById(R.id.listb1);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[0].get(position),1);
            }
        });
        if(arrayLists[0].size()>0) {

            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1, GetStringArray(arrayLists[0]));
            list1.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list1);
        }else
        {
            layout1.setVisibility(View.GONE);
        }


        //TODO book2
        LinearLayout layout2 = (LinearLayout)rootview.findViewById(R.id.l2);
        TextView tv2 = (TextView)rootview.findViewById(R.id.tvb2);
        tv2.setText(Constants.MUSLIM);
        ListView list2 = (ListView)rootview.findViewById(R.id.listb2);
        list2.setScrollContainer(false);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[1].get(position),2);
            }
        });
        if(arrayLists[1].size()>0) {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1,  GetStringArray(arrayLists[1]));
            list2.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list2);
        }else
        {
            layout2.setVisibility(View.GONE);
        }


        //TODO book3
        LinearLayout layout3 = (LinearLayout)rootview.findViewById(R.id.l3);
        TextView tv3 = (TextView)rootview.findViewById(R.id.tvb3);
        tv3.setText(Constants.DAOWD);
        ListView list3 = (ListView)rootview.findViewById(R.id.listb3);
        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[2].get(position),3);
            }
        });
        if(arrayLists[2].size()>0) {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1,  GetStringArray(arrayLists[2]));
            list3.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list3);
        }else
        {
            layout3.setVisibility(View.GONE);
        }

        //TODO book4

        Log.d("tryTakhreej",arrayLists[0].size()+" v  "+arrayLists[1].size());
        LinearLayout layout4 = (LinearLayout)rootview.findViewById(R.id.l4);
        TextView tv4 = (TextView)rootview.findViewById(R.id.tvb4);
        tv4.setText(Constants.TIRMAZI);
        ListView list4 = (ListView)rootview.findViewById(R.id.listb4);
        list4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[3].get(position),4);
            }
        });
        if(arrayLists[3].size()>0) {

            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1, GetStringArray(arrayLists[3]));
            list4.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list4);
        }else
        {
            layout4.setVisibility(View.GONE);
        }

        //TODO book5
        LinearLayout layout5 = (LinearLayout)rootview.findViewById(R.id.l5);
        TextView tv5 = (TextView)rootview.findViewById(R.id.tvb5);
        tv5.setText(Constants.NISAI);
        ListView list5 = (ListView)rootview.findViewById(R.id.listb5);
        list5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[4].get(position),5);
            }
        });
        if(arrayLists[4].size()>0) {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1,  GetStringArray(arrayLists[4]));
            list5.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list5);
        }else
        {
            layout5.setVisibility(View.GONE);
        }


        //TODO book6
        LinearLayout layout6 = (LinearLayout)rootview.findViewById(R.id.l6);
        TextView tv6 = (TextView)rootview.findViewById(R.id.tvb6);
        tv6.setText(Constants.MAJA);
        ListView list6 = (ListView)rootview.findViewById(R.id.listb6);
        list6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showHadees(arrayLists[5].get(position),6);
            }
        });
        if(arrayLists[5].size()>0) {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1,  GetStringArray(arrayLists[5]));
            list6.setAdapter(arrayAdapter1);
            Utilites.setListViewHeightBasedOnChildren(list6);
        }else
        {
            layout6.setVisibility(View.GONE);
        }

        //TODO showing books other than sahaye sitta
        LinearLayout otherBookLayout = rootview.findViewById(R.id.l7);
        boolean hasOtherBook = false;
        TextView otherBooks = rootview.findViewById(R.id.tvb7);
        for(int i= 0;i<takhreejDataModelArrayList.size();i++) {
            Log.d("tryTakhreej",takhreejDataModelArrayList.get(i).getBookIdTakhreej()+"");
            if (takhreejDataModelArrayList.get(i).getBookIdTakhreej() <= 0 || takhreejDataModelArrayList.get(i).getBookIdTakhreej() > 6) {
                hasOtherBook = true;
                otherBooks.append(takhreejDataModelArrayList.get(i).getBookNameTakhreej()+" - "+takhreejDataModelArrayList.get(i).getHeadeesTakhreejId()+"\n");
            }
        }
        if(!hasOtherBook){
            otherBookLayout.setVisibility(View.GONE);
        }

        ImageView backBtn = rootview.findViewById(R.id.imageView16);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootview;
    }

    int pos = 0;
    public String[] GetStringArray(ArrayList<Integer> arr)
    {

        pos++;
        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j)+" :"+pos;
        }

        return str;
    }


    public void showHadees(float hno,int masadirId)
    {

        Log.d("tryTakhreej",hno+"");
        Intent intent =new Intent(getContext(), HadeesViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.HADESSNO,hno);
        intent.putExtra(Constants.MASADIR,masadirId);
        getContext().startActivity(intent);
        // getActivity().finish();

    }

}
