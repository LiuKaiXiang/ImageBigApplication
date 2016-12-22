package com.yykaoo.photolibrary.photo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 渐变的textView name: GradientTextView <BR>
 * description: please write your description <BR>
 * create date: 2015-7-28
 *
 * @author: HAOWU){Jony}
 */
public class GradientTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;

    private boolean mAnimating = true;

    /**
     * 线性键盘的颜色值
     */
    private int mGradientColors[] = new int[]{getContext().getResources().getColor(android.R.color.darker_gray), getContext().getResources().getColor(android.R.color.white), getContext().getResources().getColor(android.R.color.white), getContext().getResources().getColor(android.R.color.white), getContext().getResources().getColor(android.R.color.darker_gray)};
    /**
     * 线性渐变的颜色下标相对应的位置
     */
    private float mGradientPositions[] = new float[]{0, 0.3f, 0.5f, 0.7f, 1};

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            // getWidth得到是某个view的实际尺寸.
            // getMeasuredWidth是得到某view想要在parent view里面占的大小.
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                // 线性渐变
                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0, mGradientColors, mGradientPositions, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimating && mGradientMatrix != null) {
            // 每一次运动的递增值
            mTranslate += mViewWidth / 10;
            // 结束条件语句：当递增值大于两倍child view宽度时候，及回到屏幕的左边
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            // 50ms刷新一次
            postInvalidateDelayed(50);
        }
    }

}
