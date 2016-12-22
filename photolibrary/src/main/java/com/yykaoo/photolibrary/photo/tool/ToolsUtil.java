package com.yykaoo.photolibrary.photo.tool;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;

public class ToolsUtil {

    private static WeakReference<Context> contextRef;

    public static void setContext(Context context) {
        if (context == null) {
            throw new InvalidParameterException("Invalid context parameter!");
        }

        Context appContext = context.getApplicationContext();
        contextRef = new WeakReference<Context>(appContext);
    }

    public static Context getApplicationContext() {
        Context context = contextRef.get();
        if (context == null) {
            throw new InvalidParameterException("Context parameter not set!");
        } else {
            return context;
        }
    }

}
