package com.yykaoo.photolibrary.photo.widget.actionbar;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.tool.DeviceUtil;
import com.yykaoo.photolibrary.photo.tool.ViewUtil;


/**
 * 自定义头部的Toolbar,可实现双击效果 name: AsPhoToolbar <BR>
 * description: please write your description <BR>
 * create date: 2016-1-12
 *
 * @author: HAOWU){Jony}
 */
public class AsPhoToolbar extends RelativeLayout {

    static final String TAG = "AsPhoToolbar";

    private Context mContext;

    private Toolbar mToolbar;

    private TextView mToolbarTitle;

    private LinearLayout mToolbarRightLin;

    private LinearLayout mToolbarLeftLin;

    private RelativeLayout mToolbarContent;

    private View mToolbarLine;
    private long lastClickTime = 0;

    public AsPhoToolbar(Context context) {
        this(context, null);
    }

    public AsPhoToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsPhoToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public static void paddingStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            view.setPadding(view.getPaddingLeft(), DeviceUtil.getStatusBarHeight(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public void paddingStatusBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            mToolbarContent.setPadding(mToolbarContent.getPaddingLeft(), DeviceUtil.getStatusBarHeight(), mToolbarContent.getPaddingRight(), mToolbarContent.getPaddingBottom());
        }
    }

    private void init() {

        LayoutInflater.from(mContext).inflate(R.layout.comm_pho_toolbar, this);

        mToolbarContent = (RelativeLayout) findViewById(R.id.toolbar_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        mToolbarRightLin = (LinearLayout) findViewById(R.id.toolbar_rightLin);

        mToolbarLeftLin = (LinearLayout) findViewById(R.id.toolbar_leftLin);
        mToolbarLine = (View) findViewById(R.id.toolbar_line);
        setLayoutTransition(ViewUtil.getLayoutTransition());
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        mToolbarContent.setBackgroundResource(resid);
        mToolbar.setBackgroundResource(resid);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        mToolbarContent.setBackgroundColor(color);
        mToolbar.setBackgroundColor(color);
    }

    public void setTitle(String title) {
        if (null != mToolbarTitle) {
            mToolbarTitle.setText(title);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    public LinearLayout getToolbarRightLin() {
        return mToolbarRightLin;
    }

    public LinearLayout getToolbarLeftLin() {
        return mToolbarLeftLin;
    }

    /**
     * 获取两个title的线性布局，左边id：toolbar_title_left,右边id:toolbar_title_right
     *右侧小红点id:fragment_inquiry_red_point
     * @return
     */
    public LinearLayout getTwoTitleToolbatTitle() {
        LinearLayout ll_two_title = (LinearLayout) findViewById(R.id.ll_two_title);
        ll_two_title.setVisibility(VISIBLE);
        int v = (int) (DeviceUtil.getScreenWidth() * 0.6);
        ViewGroup.LayoutParams layoutParams = ll_two_title.getLayoutParams();
        layoutParams.width = v;
        ll_two_title.setLayoutParams(layoutParams);
        return ll_two_title;
    }

    public void setBottomLineVisible() {
        mToolbarLine.setVisibility(View.VISIBLE);
    }

    public void setBottomLineGone() {
        mToolbarLine.setVisibility(View.GONE);
    }

    public RelativeLayout getToolbarContent() {
        return mToolbarContent;
    }

    public void setmToolbarContent(RelativeLayout mToolbarContent) {
        this.mToolbarContent = mToolbarContent;
    }

    public void setBottomLineVisibility(int v) {
        mToolbarLine.setVisibility(v);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handler = super.onTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (lastClickTime != 0) {
                if (System.currentTimeMillis() - lastClickTime <= 500) {
                    BasePhotoActivity activity = (BasePhotoActivity) mContext;
                    if (activity != null && activity instanceof OnToolbarDoubleClick)
                        ((OnToolbarDoubleClick) activity).onToolbarDoubleClick();
                }
            }

            lastClickTime = System.currentTimeMillis();
        }

        return handler;
    }


    public interface OnToolbarDoubleClick {

        public boolean onToolbarDoubleClick();

    }

}
