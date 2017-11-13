package com.example.as.uestc.Answer.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by as on 2017/11/5.
 */

public class MyPagerAdapter extends android.support.v4.view.PagerAdapter {

    private List<View> list;

    public MyPagerAdapter(List<View> list)
    {
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }
}
