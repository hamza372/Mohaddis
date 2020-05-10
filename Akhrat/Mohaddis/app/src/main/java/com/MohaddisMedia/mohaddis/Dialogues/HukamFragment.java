package com.MohaddisMedia.mohaddis.Dialogues;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.DataModels.Huakm;
import com.MohaddisMedia.mohaddis.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HukamFragment extends Fragment {
    public int hukamType = 0;
    public String []hukam;
    public int masadirId;
    public String hukamTafseli;

    SharedPreferences pref;
    public static HukamFragment createInstance(String[] hukam,int masadirId,int type)
    {
        HukamFragment hukamFragment = new HukamFragment();
        hukamFragment.hukam = hukam;
        hukamFragment.masadirId = masadirId;
        hukamFragment.hukamType = type;

        return hukamFragment;
    }

    public static HukamFragment createInstance(String hukam,int type)
    {
        HukamFragment hukamFragment = new HukamFragment();
        hukamFragment.hukamTafseli = hukam;
        hukamFragment.hukamType = type;

        return hukamFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hukam_dialgue,container,false);
        if(hukamType == 0)
        {
            TextView list = (TextView) v.findViewById(R.id.textView3h);
            list.setTextColor(Color.BLACK);
            list.setText(hukamTafseli);
        }else {
            pref = getActivity().getSharedPreferences("com.example.mohaddis", MODE_PRIVATE);
            String[][] names = new String[][]{{"إجماع علماء المسلمين"},
                    {"إجماع علماء المسلمين"},
                    {"١. فضيلة الشيخ الإمام محمد ناصر الدين الألباني"
                            , "٢. فضيلة الشيخ العلّامة شُعيب الأرناؤوط"
                            , "٣. مجلس علمي (دار الدّعوة، دهلي)"
                            , "٤. فضيلة الشيخ حافظ أبو طاهر زبير علي زئي"},
                    {"٢. فضيلة الشيخ العلّامة شُعيب الأرناؤوط", "٣. مجلس علمي (دار الدّعوة، دهلي)"},
                    {"٢. فضيلة الشيخ العلّامة شُعيب الأرناؤوط", "٣. مجلس علمي (دار الدّعوة، دهلي)", "٤. فضيلة الشيخ حافظ أبو طاهر زبير علي زئي"},
                    {"٢. فضيلة الشيخ العلّامة شُعيب الأرناؤوط"
                            , "٣. مجلس علمي (دار الدّعوة، دهلي)",
                            "٤. فضيلة الشيخ حافظ أبو طاهر زبير علي زئي"}};



            TextView list = (TextView) v.findViewById(R.id.textView3h);
            TextView leftTab = (TextView) v.findViewById(R.id.textView21);

            leftTab.setText("الحكم");
            String text = "اسم العالم";

            leftTab.setVisibility(View.VISIBLE);
            ListView listView = v.findViewById(R.id._dynamic_hukam);
            HukamAdapter hukamAdapter = new HukamAdapter(getContext(),new ArrayList<Huakm>());
            listView.setAdapter(hukamAdapter);

            if(masadirId == 1)
            {

                hukamAdapter.add(new Huakm("أحاديث صحيح البخاريّ كلّها صحيحة" ,names[masadirId - 1][0]));
            }else if(masadirId == 2)
            {
                hukamAdapter.add(new Huakm("أحاديث صحيح مسلم كلها صحيحة" ,names[masadirId - 1][0]));
            }else if(hukam != null) {
                for (int i = 0; i < hukam.length; i++) {
                    if (hukam[i] != null && !hukam[i].equals("") && i < names[masadirId - 1].length) {
                        hukamAdapter.add(new Huakm(hukam[i], names[masadirId - 1][i]));
                    }
                }
            }
            list.append(text);
        }


        return v;
    }
}

