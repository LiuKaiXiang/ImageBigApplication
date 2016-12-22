package com.yykaoo.photolibrary.photo.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by MDC_004 on 2016/12/14.
 */
public class DeviceUtil {
    private static String TAG = DeviceUtil.class.getSimpleName();

    /**
     * 获取手机状态栏的高度
     *
     * @param mContext
     * @return
     */
    public static int getStatusBarHeight(Activity mContext) {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return ToolsUtil.getApplicationContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.d(TAG, "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = ToolsUtil.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 调用拨打电话 Method name: sendCall <BR>
     * Description: sendCall <BR>
     *
     * @param context
     * @param phoneNumber void<BR>
     */
    public static void sendCall(Context context, String phoneNumber) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 获取手机屏幕的像素高
     *
     * @return
     */

    public static int getScreenHight() {

        WindowManager wm = (WindowManager) ToolsUtil.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        return heightPixels;
    }

    /**
     * 获取手机屏幕的像素宽
     *
     * @return
     */
    public static int getScreenWidth() {
        // DisplayMetrics dm = new DisplayMetrics();
        // context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // return dm.widthPixels;
        WindowManager wm = (WindowManager) ToolsUtil.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // since SDK_INT = 1;
        // int widthPixels = metrics.widthPixels;
        // int heightPixels =
        return metrics.widthPixels;
    }
}
