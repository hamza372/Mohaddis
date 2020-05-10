package com.MohaddisMedia.mohaddis.Dialogues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.R;

public class TaraqimTabbedDialogue extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL,
//                android.R.style.Theme_DeviceDefault_NoActionBar);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String [][]names = new String[][]{{"١. ترقيم موقع محدّث",
                "٢. ترقيم فؤاد عبد الباقي (المكتبة الشاملة)"
                ,"٣. ترقيم العالمية (برنامج الكتب التسعة)"
                ,"٤. ترقيم فتح الباري (برنامج الكتب التسعة)"
                ,"٥. ترقيم د. البغا (برنامج الكتب التسعة)"
                ,"٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                ,"٧. ترقيم دار طوق النّجاة (جامع خادم الحرمين للسنة النبوية)"
                ,"٨. ترقيم فؤاد عبد الباقي (دار السلام)"
                ,"١٠. ترقيم فؤاد عبد الباقي (داود راز)"},

                {"١. ترقيم موقع محدّث",
                        "٢. ترقيم فؤاد عبد الباقي (المكتبة الشاملة)"
                        ,"٣. ترقيم العالمية (برنامج الكتب التسعة)"
                        ,"٤. ترقيم فؤاد عبد الباقي (برنامج الكتب التسعة)"
                        ,"٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧. ترقيم دار إحیاء الکتب العربیة (جامع خادم الحرمين للسنة النبوية)"
                        ,"٨. ترقيم دار السلام"},

                {"١. ترقيم موقع محدّث",
                        "٢. ترقيم محي الدين (المكتبة الشاملة)"
                        ,"٣. ترقيم العالمية (برنامج الكتب التسعة)"
                        ,"٤. ترقيم محي الدين (برنامج الكتب التسعة)"
                        ,"٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧. ترقيم المكتبة العصرية (جامع خادم الحرمين للسنة النبوية)"
                        ,"٨. ترقيم محي الدين (دار السلام)"
                },

                {"١. ترقيم موقع محدّث",
                        "٢٢. ترقيم أحمد شاكر (المكتبة الشاملة)"
                        ,"٣. ترقيم العالمية (برنامج الكتب التسعة)"
                        ,"٤. ترقيم أحمد شاكر (برنامج الكتب التسعة)"
                        ,"٥٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧. ترقيم دار الغرب الإسلامي (جامع خادم الحرمين للسنة النبوية)"
                        ,"٩. ترقيم أحمد شاكر (دار الدعوة السلفية)"
                },
                {"١. ترقيم موقع محدّث",
                        "٢. ترقيم أبي غدّة (المكتبة الشاملة)"
                        ,"٣. ترقيم العالمية (برنامج الكتب التسعة)"
                        ,"٤. ترقيم أبي غدّة (برنامج الكتب التسعة)"
                        ,"٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧. ترقيم دار المعرفة (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧٨. ترقيم أبي غدّة (دار السلام)"
                        ,"٨. ترقيم فؤاد عبد الباقي (دار السلام)"
                        ,"١٠. ترقيم فؤاد عبد الباقي (داود راز)"},
                {"١. ترقيم موقع محدّث",
                        "٢٢. ترقيم فؤاد عبد الباقي (المكتبة الشاملة)"
                        ,"٣. ترقيم العالمية (برنامج الكتب التسعة))"
                        ,"٤. ترقيم فؤاد عبد الباقي (برنامج الكتب التسعة)"
                        ,"٥. ترقيم الأعظمي (برنامج الكتب التسعة)"
                        ,"٦. ترقيم شركة حرف (جامع خادم الحرمين للسنة النبوية)"
                        ,"٧. ترقيم دار الرّسالة العالمية (جامع خادم الحرمين للسنة النبوية)"
                        ,"٨. ترقيم فؤاد عبد الباقي (دار السلام)"
                },};
        String []tData = getArguments().getStringArray(Constants.TARAQIM);
        int masadirId = getArguments().getInt(Constants.MASADIR);
        View rootview = inflater.inflate(R.layout.taraqim_tabbed_dialogue,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);

        ImageView backBtn = rootview.findViewById(R.id.imageView16);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        DialogeCustomAdapter adapter = new DialogeCustomAdapter(getChildFragmentManager());
        adapter.addFragment("التراقيم", TaraqimFragment.createInstance(tData,names[masadirId-1]));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return rootview;
    }

    public void backButton(View v)
    {

    }


}