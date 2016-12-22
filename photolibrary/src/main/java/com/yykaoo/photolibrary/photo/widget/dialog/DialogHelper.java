package com.yykaoo.photolibrary.photo.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.text.TextUtils;

import com.yykaoo.photolibrary.photo.animation.BounceEnter;
import com.yykaoo.photolibrary.photo.animation.ZoomOutExit;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.tool.LogUtil;
import com.yykaoo.photolibrary.photo.widget.dialog.listener.OnBtnClickL;
import com.yykaoo.photolibrary.photo.widget.dialog.listener.OnOperItemClickL;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.MaterialDialog;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.NormalListDialog;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.base.BaseDialog;


/**
 * 弹框帮助类 name: DialogHelper <BR>
 * description: please write your description <BR>
 * create date: 2015-12-17
 */
public class DialogHelper {

    private String tag = this.getClass().getSimpleName();

    private AlertDialog mAlertDialog;
    private Dialog mDialog;


    private ProgressDialog mProgressDialog;

    private BaseDialog mBaseDialog;

    public DialogHelper() {
    }

    /**
     * @param title
     * @param content
     * @param positive
     * @param negativer
     * @param callback
     * @param showingActivity
     */
    public void alert(final String title, final String content, final String positive, final String negativer, final IDialogListener callback,
                      BasePhotoActivity showingActivity) {
        alert(title, content, positive, negativer, callback, showingActivity, true);
    }

    /**
     * @param title
     * @param content
     * @param positive
     * @param negativer
     * @param callback
     * @param showingActivity
     */
    public void alert(final String title, final String content, final String positive, final String negativer, final IDialogListener callback,
                      BasePhotoActivity showingActivity, final boolean isAutoDismissDialog) {

        try {
            dismissDialog();

            if (showingActivity == null) {
                return;
            }

            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }

            if (showingActivity.isFinishing()) {
                return;
            }

            final MaterialDialog dialog = new MaterialDialog(showingActivity);
            if (!TextUtils.isEmpty(title)) {
                dialog.title(title);
                dialog.isTitleShow(true);
            } else {
                dialog.isTitleShow(false);
            }
            if (!TextUtils.isEmpty(content)) {
                dialog.content(content);
            }

            dialog.btnText(positive, negativer);

            if (TextUtils.isEmpty(negativer)) {
                dialog.setHideBtnRight();
            }
            dialog.showAnim(new BounceEnter());
            dialog.dismissAnim(new ZoomOutExit());
            dialog.show();

            dialog.setOnBtnClickL(new OnBtnClickL() {

                @Override
                public void onBtnClick() {
                    if (isAutoDismissDialog)
                        dialog.dismiss();
                    if (null != callback) {
                        callback.onPositiveClick();
                    }

                }
            }, new OnBtnClickL() {

                @Override
                public void onBtnClick() {
                    if (isAutoDismissDialog)
                        dialog.dismiss();
                    if (null != callback) {
                        callback.onNegativeClick();
                    }

                }
            });
            dialog.setCanceledOnTouchOutside(false);

            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }


    /**
     * 弹出单个按钮提示 Method name: alert <BR>
     * Description: alert <BR>
     * @param title
     * @param content
     * @param positive
     * @param onBtnClickL
     * @param showingActivity
     *         void<BR>
     */

    public void alert(final String title, final String content, final String positive, final OnBtnClickL onBtnClickL, BasePhotoActivity showingActivity) {
        try {
            dismissDialog();
            if (showingActivity == null) {
                return;
            }
            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }
            if (showingActivity.isFinishing()) {
                return;
            }
            final MaterialDialog dialog = new MaterialDialog(showingActivity);

            if (!TextUtils.isEmpty(title)) {
                dialog.title(title);
                //dialog.setTitle(title);
            } else {
                dialog.isTitleShow(false);
            }
            if (!TextUtils.isEmpty(content)) {
                dialog.content(content);
            }
            //dialog.btnText(positive);
            dialog.btnNum(1);
            dialog.btnText(new String[]{positive});
            dialog.showAnim(new BounceEnter());
            dialog.dismissAnim(new ZoomOutExit());
            dialog.show();
            dialog.setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    dialog.dismiss();
                    if (null != onBtnClickL) {
                        onBtnClickL.onBtnClick();
                    }

                }
            });
            dialog.setCanceledOnTouchOutside(false);
            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    /**
     * 弹出列表框 Method name: alert <BR>
     * Description: alert <BR>
     * @param title
     * @param items
     * @param callback
     * @param showingActivity
     *         void<BR>
     */
    public void alert(final String title, final String[] items, final OnOperItemClickL callback, BasePhotoActivity showingActivity) {

        try {
            dismissDialog();
            if (showingActivity == null) {
                return;
            }

            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }

            if (showingActivity.isFinishing()) {
                return;
            }

            NormalListDialog dialog = new NormalListDialog(showingActivity, items);
            dialog.showAnim(new BounceEnter());
            dialog.dismissAnim(new ZoomOutExit());
            dialog.layoutAnimation(null);
            if (!TextUtils.isEmpty(title)) {
                dialog.title(title);
            } else {
                dialog.isTitleShow(false);
            }

            dialog.show();
            // dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            dialog.setOnOperItemClickL(callback);

            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    /**
     * 弹出列表框提示 Method name: alert <BR>
     * Description: alert <BR>
     * @param title
     * @param items
     * @param callback
     * @param isCanceledOnTouch
     * @param showingActivity
     *         void<BR>
     */
    public void alert(final String title, final String[] items, final OnOperItemClickL callback, boolean isCanceledOnTouch,
                      BasePhotoActivity showingActivity) {

        try {
            dismissDialog();
            if (showingActivity == null) {
                return;
            }

            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }

            if (showingActivity.isFinishing()) {
                return;
            }

            NormalListDialog dialog = new NormalListDialog(showingActivity, items);
            dialog.showAnim(new BounceEnter());
            dialog.dismissAnim(new ZoomOutExit());
            if (!TextUtils.isEmpty(title)) {
                dialog.title(title);
            } else {
                dialog.isTitleShow(false);
            }

            dialog.show();
            dialog.setCanceledOnTouchOutside(isCanceledOnTouch);

            dialog.setOnOperItemClickL(callback);

            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    /**
     * 弹出列表框提示,可设置是否显示标题 Method name: alert <BR>
     * Description: alert <BR>
     * @param title
     * @param items
     * @param isTitleShow
     * @param callback
     * @param showingActivity
     *         void<BR>
     */
    public void alert(final String title, final String[] items, boolean isTitleShow, final OnOperItemClickL callback, BasePhotoActivity showingActivity) {
        try {
            dismissDialog();
            if (showingActivity == null) {
                return;
            }

            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }

            if (showingActivity.isFinishing()) {
                return;
            }

            NormalListDialog dialog = new NormalListDialog(showingActivity, items);
            dialog.showAnim(new BounceEnter());
            dialog.dismissAnim(new ZoomOutExit());
            dialog.layoutAnimation(null);
            if (!TextUtils.isEmpty(title)) {
                dialog.title(title);
            }
            dialog.isTitleShow(isTitleShow);

            dialog.show();
            dialog.setCanceledOnTouchOutside(true);

            dialog.setOnOperItemClickL(callback);

            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    /**
     * 显示可取消的进度对话框
     */
    public void showLoadingDialog(final String content, final boolean cancleFlag, BasePhotoActivity showingActivity) {
        try {
            dismissDialog();
            if (showingActivity == null) {
                return;
            }

            if (showingActivity != null && showingActivity != showingActivity) {
                return;
            }

            if (showingActivity.isFinishing()) {
                return;
            }
            LoadingDialog dialog = new LoadingDialog(showingActivity, content);

            dialog.show();
            dialog.setCanceledOnTouchOutside(cancleFlag);

            mBaseDialog = dialog;
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    public void dismissDialog(BaseDialog baseDialog) {
        try {
            if (baseDialog != null && mBaseDialog.isShowing()) {
                baseDialog.dismiss();
                baseDialog = null;
            }
            if (mAlertDialog != null && mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
            if (mBaseDialog != null && mBaseDialog.isShowing()) {
                mBaseDialog.dismiss();
                mBaseDialog = null;
            }
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());

        }

    }

    public void dismissDialog() {

        try {
            if (mAlertDialog != null && mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
                mAlertDialog = null;
            }
            if (mBaseDialog != null && mBaseDialog.isShowing()) {
                mBaseDialog.dismiss();
                mBaseDialog = null;
            }
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }

    public void progressdismissDialog(Activity activity) {
        if (mProgressDialog != null && mProgressDialog.isShowing() && !activity.isFinishing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
