/*
package com.yykaoo.common.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yykaoo.client.R;


public class ToastUtil {

    private static final String ERRORSTRING = "网络打瞌睡了，稍后再试";

    private volatile static Toast mToast;

    public static void show(int resId) {
        if (resId < 0) {
            return;
        }
        showToast(ResourceUtil.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        if (resId < 0) {
            return;
        }
        showToast(ResourceUtil.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void show(String message, int duration) {
        showToast(message, duration);
    }


    public static void dismiss() {
        if (mToast == null) {
            return;
        }
        mToast.cancel();
    }

    private static Toast getToast() {
        if (mToast == null) {
            synchronized (ToastUtil.class) {
                if (mToast == null) {
                    mToast = new Toast(ToolsUtil.getApplicationContext());
                    View view = LayoutInflater.from(ToolsUtil.getApplicationContext()).inflate(R.layout.comm_item_toast, null);
                    mToast.setView(view);
                    mToast.setGravity(Gravity.BOTTOM, 0, DeviceUtil.dip2px(100));
                }
            }
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        return mToast;
    }

    private static void showToast(String infoStr, int duration) {
        if (!CheckUtil.isEmpty(infoStr)) {
            mToast = getToast();
            TextView textView = (TextView) mToast.getView().findViewById(android.R.id.message);
            textView.setText(infoStr);
            mToast.setDuration(duration);
            mToast.show();
        }
    }
}
*/
package com.yykaoo.photolibrary.photo.tool;


import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

    private static final String ERRORSTRING = "网络打瞌睡了，稍后再试";

    private volatile static Toast mToast;

    public static void show(int resId) {
        if (resId < 0) {
            return;
        }
        showToast(ResourceUtil.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        if (resId < 0) {
            return;
        }
        showToast(ResourceUtil.getString(resId), duration);
    }

    public static void show(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void show(String message, int duration) {
        showToast(message, duration);
    }


    public static void dismiss() {
        if (mToast == null) {
            return;
        }
        mToast.cancel();
    }

    private static Toast getToast() {
        if (mToast == null) {
            synchronized (ToastUtil.class) {
                if (mToast == null) {
                    mToast = new Toast(ToolsUtil.getApplicationContext());
                }
            }
        }
        return mToast;
    }

    private static void showToast(String infoStr, int duration) {
        if (!TextUtils.isEmpty(infoStr)) {
            mToast = getToast();
            mToast.makeText(ToolsUtil.getApplicationContext(),infoStr,duration).show();
        }
    }
}
