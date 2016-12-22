package com.yykaoo.photolibrary.photo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.adapter.ABaseAdapter;
import com.yykaoo.photolibrary.photo.bean.PhotoBucket;

import java.util.List;


/**
 * 选择相册适配器 name: ImageSelectAdapter <BR>
 * description: please write your description <BR>
 * create date: 2015-6-5
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoSelectAdapter extends ABaseAdapter<PhotoBucket> {

    public PhotoSelectAdapter(List<PhotoBucket> data, Context context) {
        super(data, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.photo_item_albumselect, null);
            viewHolder.photoImg = (ImageView) convertView.findViewById(R.id.photo_item_albumselect_img);
            viewHolder.photoName = (TextView) convertView.findViewById(R.id.photo_item_albumselect_title);
            viewHolder.photoCnt = (TextView) convertView.findViewById(R.id.photo_item_albumselect_count);
            viewHolder.photoIsSelect = (ImageView) convertView.findViewById(R.id.photo_item_albumselect_isselect);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(getContext()).load(getItem(position).getFirstImg()).dontAnimate().thumbnail(0.1f).into(viewHolder.photoImg);
        viewHolder.photoName.setText(getData().get(position).getImageBucket());
        viewHolder.photoCnt.setText("" + getData().get(position).getImageItems().size());
        if (getItem(position).isSelect()) {
            viewHolder.photoIsSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.photoIsSelect.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {

        ImageView photoImg;
        TextView photoName;
        TextView photoCnt;
        ImageView photoIsSelect;
    }

}
