package com.MohaddisMedia.mohaddis.Update;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.MohaddisMedia.mohaddis.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class UpdatePagerAdapter extends PagerAdapter {

    private Context context;
    Activity activity;
    private List<String> images;

    public UpdatePagerAdapter(Context context, ArrayList<String> images,Activity activity) {
        this.context = context;
        this.images = images;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        Log.d("tryPager","there");
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.card_for_update_pager, collection, false);
        ImageView pagerImage = layout.findViewById(R.id.imageView29);
        Log.d("tryPager",images.get(position));
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.banner)
                .error(R.drawable.icon);

        Glide.with(activity).load(images.get(position)).apply(options).into(pagerImage);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}