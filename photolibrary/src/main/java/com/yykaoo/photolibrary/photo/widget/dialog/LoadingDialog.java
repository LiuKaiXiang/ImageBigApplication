package com.yykaoo.photolibrary.photo.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.animation.ZoomInEnter;
import com.yykaoo.photolibrary.photo.animation.ZoomOutExit;
import com.yykaoo.photolibrary.photo.widget.GradientTextView;
import com.yykaoo.photolibrary.photo.widget.YYKLoadingView;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.base.BaseDialog;

/**
 * 等待框 name: LoadingDialog <BR>
 * description: please write your description <BR>
 * create date: 2015-12-17
 */
public class LoadingDialog extends BaseDialog<LoadingDialog> {

    private GradientTextView mGradientTextView;

    private String content;

    private YYKLoadingView mLoading;

    public LoadingDialog(Context context, String content) {
        super(context);
        this.content = content;
    }

    @Override
    public View onCreateView() {
        showAnim(new ZoomInEnter());
        dismissAnim(new ZoomOutExit());
        View contentView = getLayoutInflater().inflate(R.layout.comm_pho_dialog_progress,
                null);
        mLoading = (YYKLoadingView) contentView.findViewById(R.id.common_dialog_progress_cp);
        mGradientTextView = (GradientTextView) contentView.findViewById(R.id.common_dialog_progress_tv);
        return contentView;
    }

    @Override
    public void setUiBeforShow() {
        dimEnabled(false);
        if (!TextUtils.isEmpty(content)) {
            mGradientTextView.setText(content);
        } else {
            mGradientTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        mLoading.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoading.stop();
    }
}
