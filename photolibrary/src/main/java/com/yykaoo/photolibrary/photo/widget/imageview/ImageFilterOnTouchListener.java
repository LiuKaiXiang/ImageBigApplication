package com.yykaoo.photolibrary.photo.widget.imageview;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageFilterOnTouchListener implements OnTouchListener {

    private boolean isActionDown = false;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                ((ImageView) view).setColorFilter(null);
                break;
            case MotionEvent.ACTION_DOWN:
                changeLight((ImageView) view, -60);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                ((ImageView) view).setColorFilter(null);
                break;
            default:
                break;
        }
        return false;
    }

    private void changeLight(ImageView imageview, int brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));

    }

}
