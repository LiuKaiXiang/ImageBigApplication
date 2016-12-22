/**
 * All rights Reserved, Copyright (C) HAOWU LIMITED 2011-2015
 * FileName: HaowuBasePagerAdapter.java
 *
 * @author 002645
 */

package com.yykaoo.photolibrary.photo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * name: HaowuBasePagerAdapter <BR>
 * description: please write your description <BR>
 * create date: 2015-4-14
 *
 * @author: HAOWU Yan Jinming
 */
public abstract class ABasePagerAdapter<T> extends PagerAdapter {

    private Context context;
    private List<T> data;
    private LayoutInflater inflater;

    public ABasePagerAdapter(List<T> data, Context context) {
        super();
        this.context = context;
        this.data = new ArrayList<T>();
        this.data.addAll(data);
        this.inflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public T getItem(int position) {
        if (data == null) {
            return null;
        }
        return data.get(position);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0.equals(arg1);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = initView(container, position);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    public void updateData(List<T> data) {
        if (this.data == null) {
            return;
        }
        if (data == null) {
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.data.remove(position);
        notifyDataSetChanged();
    }

    public abstract View initView(ViewGroup container, int position);


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
