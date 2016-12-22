package com.yykaoo.photolibrary.photo.appconfig;

import android.os.Environment;

import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.tool.ToolsUtil;


public class MConfiguration {

    /**
     * 最基本的地址
     */
    private static String sBaseCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yykaoo/";

    /**
     * 图片地址
     */
    private static String sImageCachePath = "image/";

    /**
     * 文件地址
     */
    private static String sFileCachePath = "file/";

    /**
     * 录音地址
     */
    private static String sAudioCachePath = "audio/";

    public static boolean isDebug() {
        return ToolsUtil.getApplicationContext().getResources().getBoolean(R.bool.is_debug);
    }

    /**
     * 获取文件缓存的主要路径 Method name: getBaseCachePath <BR>
     * Description: getBaseCachePath <BR>
     *
     * @return String<BR>
     */
    public static String getBaseCachePath() {
        return sBaseCachePath;
    }

    /**
     * 获取缓存图片路径 Method name: getImageCachePath <BR>
     * Description: getImageCachePath <BR>
     *
     * @return String<BR>
     */
    public static String getImageCachePath() {
        return sBaseCachePath + sImageCachePath;
    }

    /**
     * 获取缓存文件路径 Method name: getFileCachePath <BR>
     * Description: getFileCachePath <BR>
     *
     * @return String<BR>
     */
    public static String getFileCachePath() {
        return sBaseCachePath + sFileCachePath;
    }

    /**
     * 获取缓存语音路径 Method name: getAudioCachePath <BR>
     * Description: getAudioCachePath <BR>
     *
     * @return String<BR>
     */
    public static String getAudioCachePath() {
        return sBaseCachePath + sAudioCachePath;
    }

}
