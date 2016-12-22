/**
 * Copyright (c) 2014 CoderKiss
 * <p/>
 * CoderKiss[AT]gmail.com
 */

package com.yykaoo.photolibrary.photo.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.yykaoo.photolibrary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 获取本地
 *
 * @author getao
 */
public class ResourceUtil {

    public static final String getString(int resId) {
        Context context = ToolsUtil.getApplicationContext();
        if (context == null || resId <= 0) {
            return null;
        }
        try {
            return context.getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final int getColor(int resId) {
        Context context = ToolsUtil.getApplicationContext();
        if (context == null || resId <= 0) {
            return 0;
        }
        try {
            return context.getResources().getColor(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final String getString(int resId, Object... args) {
        if (resId <= 0) {
            return "";
        }
        try {
            return ToolsUtil.getApplicationContext().getString(resId, args);
        } catch (Exception e) {
            LogUtil.e("resourceUtil", e.getMessage());
        }
        return "";
    }

    public static final Drawable getDrawable(int resId) {
        if (resId <= 0) {
            return null;
        }
        try {
            return ToolsUtil.getApplicationContext().getResources().getDrawable(resId);
        } catch (Exception e) {
            LogUtil.e("resourceUtil", e.getMessage());
        }
        return null;
    }

    /**
     * 获取assets下文件的内容
     *
     * @param path 文件路径
     * @return
     */
    public static String getAssetFileContent(String path) {
        BufferedReader bufferedReader = null;
        StringBuilder builder = null;
        builder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(ToolsUtil.getApplicationContext().getAssets().open(path)));
            for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                builder.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static Bitmap getImageFromAssetFile(String path, String fileName) {
        String file = path + "/" + fileName;
        return getImageFromAssetFile(file);
    }

    public static Bitmap getImageFromAssetFile(String fileName) {

        InputStream inStream = null;
        try {
            inStream = ToolsUtil.getApplicationContext().getAssets().open(fileName);
            Bitmap image = BitmapFactory.decodeStream(inStream);
            return image;
        } catch (Exception e) {
            // Log2345.d("error", e.toString());
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static BitmapDrawable getDrawableFromAssetFile(String fileName, Context context) {

        InputStream inStream = null;
        try {
            inStream = context.getAssets().open(fileName);
            BitmapDrawable d = new BitmapDrawable(inStream);
            return d;
        } catch (Exception e) {
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap getBitmapFromAssetFile(String fileName, Context context) {

        InputStream inStream = null;
        try {
            inStream = context.getAssets().open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            return bitmap;
        } catch (Exception e) {
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static int getDimensionPixelOffset(int id) {
        return ToolsUtil.getApplicationContext().getResources().getDimensionPixelOffset(id);
    }

    public static int getDimenDefalutSpacing() {
        return ResourceUtil.getDimensionPixelOffset(R.dimen.default_spacing);
    }

}
