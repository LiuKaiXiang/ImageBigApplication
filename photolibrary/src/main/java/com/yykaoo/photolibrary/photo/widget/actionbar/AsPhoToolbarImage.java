package com.yykaoo.photolibrary.photo.widget.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yykaoo.photolibrary.R;


/**
 * TIME: 2016/4/14.
 * NAME:jony
 */
public class AsPhoToolbarImage extends LinearLayout {

    private Context mContext;
    private ImageView imageView;
    private LinearLayout linearLayout;

    public AsPhoToolbarImage(Context context) {
        this(context, null);
    }

    public AsPhoToolbarImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsPhoToolbarImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        bindViews(attrs);

    }

    private void bindViews(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.actionbar_pho_image, this);
        linearLayout = (LinearLayout) findViewById(R.id.actionbar_lin);
        imageView = (ImageView) findViewById(R.id.actionbar_pho_image);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AsPhoToolbarImage);
        int imageSrc = a.getResourceId(R.styleable.AsPhoToolbarImage_src, -1);
        if (imageSrc != -1) {
            imageView.setImageResource(imageSrc);
        }
    }

    public void initializeViews(int imageRes, OnClickListener clickListener) {
        imageView.setImageResource(imageRes);
        linearLayout.setOnClickListener(clickListener);
    }

    public void setImageViewRes(int resId) {
        imageView.setImageResource(resId);
    }
}
