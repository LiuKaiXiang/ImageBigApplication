package com.yykaoo.photolibrary.photo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * name: SingleDataSetListAdapter <BR>
 * description: please write your description <BR>
 * create date: 2015-4-13
 *
 * @author: HAOWU Jony
 */
public abstract class ABaseAdapter<T> extends BaseAdapter {

    public String tag = ABaseAdapter.class.getSimpleName();
    protected List<T> mData;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public ABaseAdapter(List<T> data, Context context) {
        this.mData = new ArrayList<T>();
        if (data != null) {
            this.mData.addAll(data);
        }
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        if (this.mData == null) {
            return 0;
        }
        return this.mData.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(int position) {
        if (this.mData == null) {
            return null;
        }
        return this.mData.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refresh(List<T> data) {
        if (data == null) {
            return;
        }
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void load(List<T> data) {
        if (data == null)
            return;
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        if (null == this.mData) {
            return new ArrayList<T>();
        }

        return this.mData;
    }

    public Context getContext() {
        return this.mContext;
    }

    public LayoutInflater getInflater() {
        return this.mInflater;
    }

    public void clearData() {
        this.mData.clear();
        notifyDataSetChanged();
    }
}
