package com.yykaoo.photolibrary.photo.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * TIME: 2016/4/14.
 * NAME:jony
 */
public class AsPhoToolbarNotificationImage extends LinearLayout {

    private Context mContext;
    private ImageView imageView, imageViewRedPoint;
    private LinearLayout linearLayout;
    private TextView MessageNumberTv;

    public AsPhoToolbarNotificationImage(Context context) {
        this(context, null);
    }

    public AsPhoToolbarNotificationImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsPhoToolbarNotificationImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        bindViews(attrs);

    }

    private void bindViews(AttributeSet attrs) {
        //        LayoutInflater.from(mContext).inflate(R.layout.actionbar_notification, this);
        //        linearLayout = (LinearLayout) findViewById(R.id.actionbar_lin);
        //        imageView = (ImageView) findViewById(R.id.actionbar_pho_image);
        //        imageViewRedPoint = (ImageView) findViewById(R.id.redPoint);
        //        MessageNumberTv = (TextView) findViewById(R.id.messageNumber);
        //
        //
        //        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AsPhoToolbarImage);
        //        int imageSrc = a.getResourceId(R.styleable.AsPhoToolbarImage_src, -1);
        //        if (imageSrc != -1) {
        //            imageView.setImageResource(imageSrc);
        //            imageViewRedPoint.setImageResource(imageSrc);
        //        }
    }

    public void initializeViews(int imageRes, int imageRedPoint, String messageNumber, Boolean visiable, OnClickListener clickListener) {
        imageView.setImageResource(imageRes);
        imageViewRedPoint.setImageResource(imageRedPoint);
        MessageNumberTv.setText(messageNumber);
        linearLayout.setOnClickListener(clickListener);
        if (visiable == false) {
            imageViewRedPoint.setVisibility(GONE);
            MessageNumberTv.setVisibility(GONE);
        }
    }

}
