package com.yykaoo.photolibrary.photo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.adapter.ABaseAdapter;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.tool.DeviceUtil;
import com.yykaoo.photolibrary.photo.tool.LogUtil;
import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.widget.imageview.ImageFilterOnTouchListener;

import java.util.List;


/**
 * 选择图片适配器 name: PhotoGridAdapter <BR>
 * description: please write your description <BR>
 * create date: 2015-6-5
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoGridAdapter extends ABaseAdapter<PhotoItem> {

    private SelectedCallback textcallback = null;
    final String TAG = getClass().getSimpleName();
    private FrameLayout.LayoutParams layoutParams;

    public PhotoGridAdapter(List<PhotoItem> data, Context context, int maxPic) {
        super(data, context);
        int width = DeviceUtil.getScreenWidth() / 3; // 估算每个item的宽
        layoutParams = new FrameLayout.LayoutParams(width, width);
    }

    public interface SelectedCallback {

        /**
         * 点击选中的监听 Method name: onListen <BR>
         * Description: onListen <BR>
         * void<BR>
         */
        public void onListen();
    }

    public void setSelectedCallback(SelectedCallback textcallback) {
        this.textcallback = textcallback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(getContext(), R.layout.photo_item_imgselect, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.photo_item_imgselect_img);
            holder.selected = (ImageView) convertView.findViewById(R.id.photo_item_imgselect_isselected);
            holder.selectedLin = (LinearLayout) convertView.findViewById(R.id.photo_item_imgselect_isselectedlin);
            holder.camera = (ImageView) convertView.findViewById(R.id.photo_item_imgselect_camera);
            holder.iv.setLayoutParams(layoutParams);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final PhotoItem item = getItem(position);
        holder.iv.setVisibility(View.INVISIBLE);
        LogUtil.d(item.getImgPath());
        if (PhotoUtil.camera.equals(item.getImgPath())) {
            holder.selected.setVisibility(View.GONE);
            holder.iv.setImageBitmap(null);
            holder.iv.setImageDrawable(null);
            holder.iv.setOnTouchListener(new ImageFilterOnTouchListener());
            holder.iv.setVisibility(View.INVISIBLE);
            holder.camera.setVisibility(View.VISIBLE);
        } else {
            holder.camera.setVisibility(View.INVISIBLE);
            holder.selected.setVisibility(View.VISIBLE);
            holder.iv.setOnTouchListener(null);
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setBackgroundResource(android.R.color.transparent);
            holder.selected.setBackgroundResource(android.R.color.transparent);
            if (TextUtils.isEmpty(item.getThumbnailPath())) {
                Glide.with(mContext).load(item.getImgPath()).dontAnimate().thumbnail(0.1f).into(holder.iv);
            } else {
                Glide.with(mContext).load(item.getThumbnailPath()).dontAnimate().thumbnail(0.1f).into(holder.iv);
            }

//            if (PhotoActivity.maxPic != 1) {
            if (PhotoActivity.getImgs().containsKey(item.getImgPath()) && item.getImgPathType() != 1) {
                holder.selected.setImageResource(PhotoActivity.iconSelected);
                holder.iv.setColorFilter(Color.parseColor("#77000000"));
            } else {
                holder.selected.setImageResource(PhotoActivity.iconUncheck);
                holder.iv.setColorFilter(null);
            }
            holder.selectedLin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String path = getData().get(position).getImgPath();
                    if (PhotoActivity.getImgs().containsKey(path)) {
                        PhotoActivity.getImgs().remove(path);
                        holder.selected.setImageResource(PhotoActivity.iconUncheck);
                        holder.iv.setColorFilter(null);
                        if (textcallback != null)
                            textcallback.onListen();
                    } else {
                        if (PhotoActivity.getImgs().size() < PhotoActivity.maxPic) {
                            PhotoActivity.getImgs().put(path, item);
                            holder.selected.setImageResource(PhotoActivity.iconSelected);
                            holder.iv.setColorFilter(Color.parseColor("#77000000"));
                            if (textcallback != null)
                                textcallback.onListen();
                        } else {
                            ToastUtil.show("最多选择" + PhotoActivity.maxPic + "张图片");
                        }
                    }
                }
            });
           /* } else {
                holder.selected.setVisibility(View.GONE);
                holder.iv.setVisibility(View.VISIBLE);
            }*/
        }
        return convertView;
    }

    static class Holder {

        ImageView iv;
        ImageView selected;
        ImageView camera;
        LinearLayout selectedLin;
    }
}
