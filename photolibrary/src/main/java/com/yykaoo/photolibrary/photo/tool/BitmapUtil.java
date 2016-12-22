package com.yykaoo.photolibrary.photo.tool;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by MDC_004 on 2016/12/14.
 */
public class BitmapUtil {
    private static String TAG = "BitmapUtil";

    public static boolean saveImage(Bitmap bitmap, String absPath) {
        return saveImage(bitmap, absPath, 100);
    }

    public static boolean saveImage(Bitmap bitmap, String absPath, int quality) {
        if (!FileUtil.create(absPath)) {
            LogUtil.w(TAG, "create file failed.");
            return false;
        }

        try {
            File outFile = new File(absPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            LogUtil.e(TAG, "failed to write image content");
            return false;
        }

        return true;
    }
}
