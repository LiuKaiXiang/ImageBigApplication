package com.yykaoo.photolibrary.photo.basic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.appconfig.AppManager;
import com.yykaoo.photolibrary.photo.tool.DeviceUtil;
import com.yykaoo.photolibrary.photo.tool.LogUtil;
import com.yykaoo.photolibrary.photo.tool.StatusBarUtils;
import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.tool.ToolsUtil;
import com.yykaoo.photolibrary.photo.tool.ViewUtil;
import com.yykaoo.photolibrary.photo.widget.MultiStateView;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbar;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarImage;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarText;
import com.yykaoo.photolibrary.photo.widget.dialog.DialogHelper;
import com.yykaoo.photolibrary.photo.widget.dialog.IDialogListener;
import com.yykaoo.photolibrary.photo.widget.dialog.listener.OnOperItemClickL;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.base.BaseDialog;

/**
 * activity 基类
 *
 * @author getao
 */
public abstract class BasePhotoActivity extends AppCompatActivity {

    protected final static String TAG = BasePhotoActivity.class.getSimpleName();
    protected AsPhoToolbar mToolbar;
    private MultiStateView container;
    // 请求失败后的文字提醒
    private TextView tvFail;
    private TextView tvNoData;
    /**
     * 对话框帮助类
     */
    private DialogHelper mDialogHelper;

    private int requestAuthority = 0x001;

    private LinearLayout mMultistateviewLin;
    private OnClickListener onReloadListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            showLoading();
            onReload();
        }
    };

    public void dismissDialog() {
        mDialogHelper.dismissDialog();
    }

    public void dismissDialog(BaseDialog<?> baseDialog) {
        mDialogHelper.dismissDialog(baseDialog);
    }

    public void progressdismissDialog() {
        mDialogHelper.progressdismissDialog(this);
    }

    /**
     * 获取弹框的帮助类 <BR>
     */
    public DialogHelper getDialogHelper() {
        return mDialogHelper;
    }

    /**
     * 弹对话框
     *
     * @param title    标题
     * @param msg      消息
     * @param positive 确定
     * @param negative 否定
     */
    public void alert(String title, String msg, String positive, String negative, IDialogListener callback) {
        mDialogHelper.alert(title, msg, positive, negative, callback, this);
    }

    /**
     * 弹出多选框 Method
     */
    public void alert(final String title, final String[] items, final OnOperItemClickL callback) {
        mDialogHelper.alert(title, items, callback, BasePhotoActivity.this);
    }

    /**
     * 弹出等待框
     */
    public void showLoadingDialog(String content, boolean cancleFlag) {
        mDialogHelper.showLoadingDialog(content, cancleFlag, this);
    }

    /**
     * 弹出等待框
     */
    public void showLoadingDialog(String content) {
        mDialogHelper.showLoadingDialog(content, false, this);
    }

    /**
     * 弹出等待框
     */
    public void showLoadingDialog(boolean cancleFlag) {
        mDialogHelper.showLoadingDialog("加载中", cancleFlag, this);
    }

    /**
     * 弹出等待框
     */
    public void showLoadingDialog() {
        mDialogHelper.showLoadingDialog("加载中", false, this);
    }

    /**
     * 失败页面的点击
     */
    public void onReload() {
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        View layout = View.inflate(this, R.layout.comm_pho_multistateview, null);

        mMultistateviewLin = (LinearLayout) layout.findViewById(R.id.common_multistateviewLin);
        mMultistateviewLin.setLayoutTransition(ViewUtil.getLayoutTransition());

        mToolbar = (AsPhoToolbar) layout.findViewById(R.id.common_multiStateView_photoolbar);
        container = (MultiStateView) layout.findViewById(R.id.common_multiStateView_phoview);
        container.getView(MultiStateView.ViewState.ERROR).setOnClickListener(onReloadListener);
        container.getView(MultiStateView.ViewState.EMPTY).setOnClickListener(onReloadListener);
        tvFail = (TextView) container.getView(MultiStateView.ViewState.ERROR).findViewById(R.id.common_errorview_fail);
        tvNoData = (TextView) container.getView(MultiStateView.ViewState.EMPTY).findViewById(R.id.common_emptyview_tv);
        if (view != null) {
            container.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        initToolbar();
        setLayoutTransition(container);
        setLayoutTransition(mToolbar);

        super.setContentView(layout, params);
    }

    /**
     * 在父类的主要View中添加头部 Method name: addHeadView <BR>
     * Description: addHeadView <BR>
     *
     * @param view void<BR>
     */
    public void addHeadView(View view) {
        mMultistateviewLin.addView(view, 0);
    }

    /**
     * 获取主页面的
     */
    public MultiStateView getContainer() {
        return container;
    }

    /**
     * 设置View的属性动画
     */
    private void setLayoutTransition(ViewGroup view) {
        view.setLayoutTransition(ViewUtil.getLayoutTransition());
    }

    /**
     * 显示主页面
     */
    public void showContent() {
        container.setViewState(MultiStateView.ViewState.CONTENT);
    }

    /**
     * 显示错误页面
     */
    public void showError() {
        tvFail.setText("请求失败，点击重新加载");
        container.setViewState(MultiStateView.ViewState.ERROR);
    }

    /**
     * 显示错误页面
     */
    public void showError(String errorStr) {
        tvFail.setText(TextUtils.isEmpty(errorStr) ? "请求失败，点击重新加载" : errorStr);
        container.setViewState(MultiStateView.ViewState.ERROR);
    }

    /**
     * 显示数据为空
     */
    public void showEmpty() {
        container.setViewState(MultiStateView.ViewState.EMPTY);
    }

    /**
     * 显示数据为空
     *
     * @param emptyStr 数据为空是界面提示<BR>
     */
    public void showEmpty(String emptyStr) {
        tvNoData.setText(emptyStr);
        container.setViewState(MultiStateView.ViewState.EMPTY);
    }

    /**
     * 显示数据为空
     *
     * @param emptyStr 数据为空是界面提示<BR>
     */
    public void showEmpty(String emptyStr, int imgid) {
        tvNoData.setText(emptyStr);
        Drawable drawable = getResources().getDrawable(imgid);
        if (drawable != null) {
            drawable.setBounds(0, 0, DeviceUtil.dip2px(120), DeviceUtil.dip2px(120));
            tvNoData.setCompoundDrawables(null, drawable, null, null);
        }
        container.setViewState(MultiStateView.ViewState.EMPTY);
    }

    /**
     * 显示正在加载
     */
    public void showLoading() {
        container.setViewState(MultiStateView.ViewState.LOADING);
    }

    protected void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar.getToolbar());
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle("");
            mToolbar.setBottomLineVisible();
            mToolbar.paddingStatusBar();
            initNavigationIcon(mToolbar);
        }
    }

    /**
     * 初始化返回按钮
     */
    protected void initNavigationIcon(AsPhoToolbar AsPhoToolbar) {
//        View view = getLayoutInflater().inflate(R.layout.actionbar_pho_image, null);
//        ImageView mArrow = (ImageView) view.findViewById(R.id.actionbar_pho_image);
//        mArrow.setImageResource(R.drawable.icon_back);
//        initNavigationIconClick(view);
//        AsPhoToolbar.getToolbarLeftLin().addView(view);

        AsPhoToolbarText AsPhoToolbarText = new AsPhoToolbarText(this);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_back);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        AsPhoToolbarText.getTextView().setCompoundDrawables(drawable, null, null, null);
        AsPhoToolbarText.getTextView().setTextColor(getResources().getColor(R.color.white));
        AsPhoToolbarText.setText("\t返回");
        initNavigationIconClick(AsPhoToolbarText);
        AsPhoToolbar.getToolbarLeftLin().addView(AsPhoToolbarText);
    }

    // 添加右边按钮
    public void addRightBtn(Object object, OnClickListener onClickListener) {
        if (mToolbar == null) {
            return;
        }
        if (object instanceof String) {
            // 添加textview
            AsPhoToolbarText AsPhoToolbarText = new AsPhoToolbarText(getApplicationContext());
            AsPhoToolbarText.setText(object.toString());
            AsPhoToolbarText.initializeViews(object.toString(), onClickListener);
            mToolbar.getToolbarRightLin().addView(AsPhoToolbarText);
        } else if (object instanceof Integer) {
            // 添加imaggeview
            AsPhoToolbarImage AsPhoToolbarImage = new AsPhoToolbarImage(getApplicationContext());
            AsPhoToolbarImage.initializeViews((int) object, onClickListener);
            mToolbar.getToolbarRightLin().addView(AsPhoToolbarImage);
        }
    }

    /**
     * 设置返回按钮的点击事件
     */
    protected void initNavigationIconClick(View view) {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(BasePhotoActivity.this);
            }
        });
    }

    protected void hideToolBar() {
        if (null != mToolbar)
            mToolbar.setVisibility(View.GONE);
    }

    protected void showToolBar() {
        if (null != mToolbar)
            mToolbar.setVisibility(View.VISIBLE);
    }

    protected void hideToolBarLeftBackView() {
        if (null != mToolbar)
            mToolbar.getToolbarLeftLin().removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));

        super.onCreate(savedInstanceState);
        ToolsUtil.setContext(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mDialogHelper = new DialogHelper();
        AppManager.getInstance().addActivity(this);

        StatusBarUtils.getInstance().setStatusBarDark(this);

        if (!checkAuthority(Manifest.permission.CAMERA) || !checkAuthority(Manifest.permission.READ_EXTERNAL_STORAGE) || !checkAuthority(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
                    ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestAuthority);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults == null || grantResults.length == 0) {
            return;
        }
        if (requestCode == requestAuthority) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.show("权限被拒绝");
            }
        }
    }

    /**
     * 检查是否已经拥有该权限
     *
     * @param authority
     * @return
     */
    public boolean checkAuthority(String authority) {
        if (ContextCompat.checkSelfPermission(this, authority) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    public void invalidateMyOptionsMenu() {
        supportInvalidateOptionsMenu();
    }


    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
        AppManager.getInstance().finishActivity(this);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    public BasePhotoActivity getContext() {
        return BasePhotoActivity.this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                LogUtil.i("Alan", "TRIM_MEMORY_UI_HIDDEN"); // 当某个app的所有UI都不显示时，就认为该app已经退到后台。
                break;
            case TRIM_MEMORY_RUNNING_LOW: // the device is running much lower on
                // memory so you should release unused
                // resources to improve system
                // performance
                LogUtil.i("Alan", "TRIM_MEMORY_RUNNING_LOW");

                // UniversalImageLoader.getImageLoader().clearMemoryCache();
                break;
        }
    }

    public AsPhoToolbar getToolbar() {
        return mToolbar;
    }

}
