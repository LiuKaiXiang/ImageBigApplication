package com.yykaoo.photolibrary.photo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.yykaoo.photolibrary.photo.adapter.ABasePagerAdapter;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.tool.FileUtil;
import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.widget.gestureimageview.GestureImageView;

import java.util.List;


/**
 * 预览页面的图片适配器 name: PhotoPagerAdapter <BR>
 * description: please write your description <BR>
 * create date: 2015-6-10
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoPagerAdapter extends ABasePagerAdapter<PhotoItem> {

    private int postion = 0;
    private int imgType;
    private ClickPhotoListener mClickPhotoListener;
    private int presentPosition = 0;

    public PhotoPagerAdapter(List<PhotoItem> data, Context context, int imageType, int postion) {
        super(data, context);
        this.imgType = imageType;
        this.postion = postion;
    }

    public void setmClickPhotoListener(ClickPhotoListener mClickPhotoListener) {
        this.mClickPhotoListener = mClickPhotoListener;
    }

    public void setPresentVisibilityItem(int position) {
        this.presentPosition = position;
    }

    @Override
    public View initView(ViewGroup container, final int position) {
        /*final GestureImageView photoView = new GestureImageView(container.getContext());
        final PhotoItem item = getItem(position);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(layoutParams);

        Glide.with(getContext()).load(item.getImgPath()).thumbnail(0.1f).dontAnimate().into(photoView);

        photoView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mClickPhotoListener) {
                    mClickPhotoListener.onClickPhoto();
                }
            }
        });
        return photoView;*/

        BigImageView imageView = new BigImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);

        PhotoItem item = getItem(position);
        imageView.showImage(FileUtil.filePathToUri(getContext(), item.getImgPath()));
        imageView.setProgressIndicator(new ProgressPieIndicator());

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mClickPhotoListener) {
                    mClickPhotoListener.onClickPhoto();
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface ClickPhotoListener {
        void onClickPhoto();
    }
}
