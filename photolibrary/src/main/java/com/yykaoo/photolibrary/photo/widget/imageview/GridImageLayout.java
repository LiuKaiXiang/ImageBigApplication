package com.yykaoo.photolibrary.photo.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.PhotoHelper;
import com.yykaoo.photolibrary.photo.PhotoUtil;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.tool.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDC_006 on 2016/7/13.
 */
public class GridImageLayout extends LinearLayout {

    private LayoutParams layoutParams;
    private LayoutParams layoutParams2;
    private LayoutParams layoutParams3;
    private Context mContext;
    private LinearLayout widgetGridImagelayoutImas;
    private ImageView widgetGridImagelayoutImg1;
    private LinearLayout widgetGridImagelayoutImageLin1;
    private ImageView widgetGridImagelayoutImg2;
    private ImageView widgetGridImagelayoutImg3;
    private ImageView widgetGridImagelayoutImg4;
    private LinearLayout widgetGridImagelayoutImageLin2;
    private ImageView widgetGridImagelayoutImg5;
    private ImageView widgetGridImagelayoutImg6;
    private ImageView widgetGridImagelayoutImg7;
    private LinearLayout widgetGridImagelayoutImageLin3;
    private ImageView widgetGridImagelayoutImg8;
    private ImageView widgetGridImagelayoutImg9;
    private ImageView widgetGridImagelayoutImg10;
    private List<String> mImgs;
    private List<String> thumbnail;
    private List<String> source;

    public GridImageLayout(Context context) {
        this(context, null);
    }

    public GridImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int width = DeviceUtil.getScreenWidth() - DeviceUtil.dip2px(24);
        int width2 = (width - DeviceUtil.dip2px(12)) / 3;
        int width3 = (width - DeviceUtil.dip2px(12)) / 2;
        this.mContext = context;
        layoutParams = new LayoutParams(width, width);
        layoutParams2 = new LayoutParams(width2, width2);
        layoutParams2.bottomMargin = 6;
        layoutParams2.rightMargin = 6;
        layoutParams2.leftMargin = 6;
        layoutParams2.topMargin = 6;
        layoutParams3 = new LayoutParams(width3, width3);
        layoutParams3.bottomMargin = 6;
        layoutParams3.rightMargin = 6;
        layoutParams3.leftMargin = 6;
        layoutParams3.topMargin = 6;
        bindViews(context);
    }

    private void bindViews(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_pho_grid_imagelayout, this);
        widgetGridImagelayoutImas = (LinearLayout) findViewById(R.id.widget_grid_imagelayout_imas);
        widgetGridImagelayoutImg1 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg1);
        widgetGridImagelayoutImageLin1 = (LinearLayout) findViewById(R.id.widget_grid_imagelayout_imageLin1);
        widgetGridImagelayoutImg2 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg2);
        widgetGridImagelayoutImg3 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg3);
        widgetGridImagelayoutImg4 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg4);
        widgetGridImagelayoutImageLin2 = (LinearLayout) findViewById(R.id.widget_grid_imagelayout_imageLin2);
        widgetGridImagelayoutImg5 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg5);
        widgetGridImagelayoutImg6 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg6);
        widgetGridImagelayoutImg7 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg7);
        widgetGridImagelayoutImageLin3 = (LinearLayout) findViewById(R.id.widget_grid_imagelayout_imageLin3);
        widgetGridImagelayoutImg8 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg8);
        widgetGridImagelayoutImg9 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg9);
        widgetGridImagelayoutImg10 = (ImageView) findViewById(R.id.widget_grid_imagelayoutImg10);
    }

    public void initializeViews(List<String> images) {
        this.mImgs = images;
        disPoseImgs(mImgs);
    }

    public void initializeViews(List<String> thumbnail, List<String> source) {
        this.thumbnail = thumbnail;
        this.source = source;
        disPoseImgs(thumbnail);
    }

    /**
     * 隐藏所有图片 Method name: hideImgs <BR>
     * Description: hideImgs <BR>
     * void<BR>
     */
    public void hideImgs() {
        widgetGridImagelayoutImageLin1.setVisibility(View.GONE);
        widgetGridImagelayoutImageLin2.setVisibility(View.GONE);
        widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
        widgetGridImagelayoutImg1.setVisibility(View.GONE);
        widgetGridImagelayoutImg2.setVisibility(View.GONE);
        widgetGridImagelayoutImg3.setVisibility(View.GONE);
        widgetGridImagelayoutImg4.setVisibility(View.GONE);
        widgetGridImagelayoutImg5.setVisibility(View.GONE);
        widgetGridImagelayoutImg6.setVisibility(View.GONE);
        widgetGridImagelayoutImg7.setVisibility(View.GONE);
        widgetGridImagelayoutImg8.setVisibility(View.GONE);
        widgetGridImagelayoutImg9.setVisibility(View.GONE);
        widgetGridImagelayoutImg10.setVisibility(View.GONE);

    }

    /**
     * 加载图片 Method name: initImg <BR>
     * Description: initImg <BR>
     *
     * @param imageView
     * @param url       void<BR>
     */
    private void initImg(ImageView imageView, String url, final int position) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setLayoutParams(layoutParams2);
        imageView.setBackgroundResource(android.R.color.transparent);
        Glide.with(mContext).load(url).dontAnimate().thumbnail(0.1f).into(imageView);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendPhoto(position, arg0);
            }
        });
    }

    private void initWidthImg(ImageView imageView, String url, final int position) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setLayoutParams(layoutParams3);
        imageView.setBackgroundResource(android.R.color.transparent);
        Glide.with(mContext).load(url).dontAnimate().thumbnail(0.1f).into(imageView);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendPhoto(position, arg0);
            }
        });
    }

    // 点击发布的照片
    private void sendPhoto(int position, View view) {

        ArrayList<PhotoItem> activity = new ArrayList<PhotoItem>();
        for (int i = 0; i < source.size(); i++) {
            activity.add(new PhotoItem(source.get(i)));
        }
        PhotoHelper.sendPhotoLook((BasePhotoActivity) getContext(), activity, position, view, PhotoUtil.PhotoImgType.NETWORK.getIntVlue());

    }

    /**
     * 隐藏图片 Method name: hideImg <BR>
     * Description: hideImg <BR>
     *
     * @param imageView void<BR>
     */
    private void hideImg(ImageView imageView) {
        imageView.setVisibility(View.GONE);
    }

    // 1-9 张图片各种处理 <BR>
    private void disPoseImgs(List<String> item) {
        switch (item.size()) {
            case 0:
                hideImgs();
                break;
            case 1:
                widgetGridImagelayoutImg1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                widgetGridImagelayoutImg1.setBackgroundResource(android.R.color.transparent);
                widgetGridImagelayoutImg1.setLayoutParams(layoutParams2);
                Glide.with(mContext).load(item.get(0)).dontAnimate().thumbnail(0.1f).into(widgetGridImagelayoutImg1);
                widgetGridImagelayoutImg1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        sendPhoto(0, arg0);
                    }
                });

                break;
            case 2:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                initWidthImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initWidthImg(widgetGridImagelayoutImg3, item.get(1), 1);
                hideImg(widgetGridImagelayoutImg4);
                break;
            case 3:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                break;
            case 4:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                initWidthImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initWidthImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initWidthImg(widgetGridImagelayoutImg5, item.get(2), 2);
                initWidthImg(widgetGridImagelayoutImg6, item.get(3), 3);
                hideImg(widgetGridImagelayoutImg4);
                hideImg(widgetGridImagelayoutImg7);
                break;
            case 5:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                initImg(widgetGridImagelayoutImg5, item.get(3), 3);
                initImg(widgetGridImagelayoutImg6, item.get(4), 4);
                hideImg(widgetGridImagelayoutImg7);
                break;
            case 6:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.GONE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                initImg(widgetGridImagelayoutImg5, item.get(3), 3);
                initImg(widgetGridImagelayoutImg6, item.get(4), 4);
                initImg(widgetGridImagelayoutImg7, item.get(5), 5);
                break;
            case 7:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.VISIBLE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                initImg(widgetGridImagelayoutImg5, item.get(3), 3);
                initImg(widgetGridImagelayoutImg6, item.get(4), 4);
                initImg(widgetGridImagelayoutImg7, item.get(5), 5);
                initImg(widgetGridImagelayoutImg8, item.get(6), 6);
                hideImg(widgetGridImagelayoutImg9);
                hideImg(widgetGridImagelayoutImg10);
                break;
            case 8:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.VISIBLE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                initImg(widgetGridImagelayoutImg5, item.get(3), 3);
                initImg(widgetGridImagelayoutImg6, item.get(4), 4);
                initImg(widgetGridImagelayoutImg7, item.get(5), 5);
                initImg(widgetGridImagelayoutImg8, item.get(6), 6);
                initImg(widgetGridImagelayoutImg9, item.get(7), 7);
                hideImg(widgetGridImagelayoutImg10);
                break;
            case 9:
                widgetGridImagelayoutImg1.setVisibility(View.GONE);
                widgetGridImagelayoutImageLin2.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin1.setVisibility(View.VISIBLE);
                widgetGridImagelayoutImageLin3.setVisibility(View.VISIBLE);
                initImg(widgetGridImagelayoutImg2, item.get(0), 0);
                initImg(widgetGridImagelayoutImg3, item.get(1), 1);
                initImg(widgetGridImagelayoutImg4, item.get(2), 2);
                initImg(widgetGridImagelayoutImg5, item.get(3), 3);
                initImg(widgetGridImagelayoutImg6, item.get(4), 4);
                initImg(widgetGridImagelayoutImg7, item.get(5), 5);
                initImg(widgetGridImagelayoutImg8, item.get(6), 6);
                initImg(widgetGridImagelayoutImg9, item.get(7), 7);
                initImg(widgetGridImagelayoutImg10, item.get(8), 8);
                break;
            default:
                hideImgs();
                break;
        }
    }

}
