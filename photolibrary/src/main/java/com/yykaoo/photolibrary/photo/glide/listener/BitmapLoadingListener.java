package com.yykaoo.photolibrary.photo.glide.listener;

import android.graphics.Bitmap;

/**
 * Created by MDC_004 on 2016/12/19.
 */
public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}
