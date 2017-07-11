package com.example.alex.jichunge.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.alex.jichunge.R;

/**
 * Created by collins on 7/11/17.
 */

public class slidingimage extends PagerAdapter {

    private int [] images={R.drawable.s2, R.drawable.s3};
    private LayoutInflater layoutInflater;
    private Context context;

    public slidingimage(Context context){
        this.context= context;

        //  layoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view= layoutInflater.inflate(R.layout.slidingimage, container, false);
        ImageView imageView= (ImageView)item_view.findViewById(R.id.introImageView);
        imageView.setImageResource(images[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }


}
