package com.yykaoo.photolibrary.photo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.camera.CameraActivity;
import com.yykaoo.photolibrary.photo.tool.LogUtil;

import java.util.ArrayList;


/**
 * 相册模块帮助类 name: PhotoHelper <BR>
 * description: please write your description <BR>
 * create date: 2016-1-12
 *
 * @author: HAOWU){Jony}
 */
public class PhotoHelper {

    private static String tag = "PhotoHelper";

    /**
     * 跳转预览图片可删除 Method name: sendPhotoDelete <BR>
     * Description: sendPhotoDelete <BR>
     *
     * @param activity
     * @param photoItems
     * @param position   void<BR>
     */
    public static void sendPhotoDelete(Activity activity, ArrayList<PhotoItem> photoItems, int position) {
        Intent intent = new Intent(activity, PhotoPreViewAct.class);
        intent.putExtra(PhotoUtil.POSITION, position);
        intent.putExtra(PhotoUtil.IMGS, photoItems);
        intent.putExtra(PhotoUtil.TYPE, PhotoUtil.PhotoType.DELETE.getIntVlue());
        intent.putExtra(PhotoUtil.IMGTYPE, PhotoUtil.PhotoImgType.LOCAL.getIntVlue());
        activity.startActivityForResult(intent, PhotoUtil.PhotoCode.PREVIEW.getCode());
    }

    /**
     * 跳转预览图片 Method name: sendPhotoLook <BR>
     * Description: sendPhotoLook <BR>
     *
     * @param activity
     * @param photoItems
     * @param position
     * @param imgType    void<BR>
     */
    public static void sendPhotoLook(BasePhotoActivity activity, ArrayList<PhotoItem> photoItems, int position, View clickView, int imgType) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, clickView, PhotoPreViewAct.EXTRA_IMAGE);

        Intent intent = new Intent(activity, PhotoPreViewAct.class);
        intent.putExtra(PhotoUtil.IMGTYPE, imgType);
        intent.putExtra(PhotoUtil.POSITION, position);
        intent.putExtra(PhotoUtil.IMGS, photoItems);
        intent.putExtra(PhotoUtil.TYPE, PhotoUtil.PhotoType.LOOK.getIntVlue());
        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    /**
     * 跳转预览界面可选择 Method name: sendPhotoPreView <BR>
     * Description: sendPhotoPreView <BR>
     *
     * @param activity
     * @param photoItems
     * @param position
     * @param imgType    void<BR>
     */
    public static void sendPhotoPreView(Activity activity, ArrayList<PhotoItem> photoItems, int position
            , PhotoUtil.PhotoImgType imgType, boolean isPreview,boolean isSendPho) {
        Intent intent = new Intent(activity, PhotoPreViewAct.class);
        intent.putExtra(PhotoUtil.IMGTYPE, imgType.getIntVlue());
        intent.putExtra(PhotoUtil.POSITION, position);
        intent.putExtra(PhotoUtil.IMGS, photoItems);
        intent.putExtra(PhotoUtil.TYPE, PhotoUtil.PhotoType.PREVIEW.getIntVlue());
        intent.putExtra(PhotoUtil.FromWhere, isPreview);
        intent.putExtra(PhotoUtil.SEND_PHOTO, isSendPho);
        activity.startActivityForResult(intent, PhotoUtil.PhotoCode.PREVIEW.getCode());
    }

    /**
     * 跳转相册 Method name: sendPhoto <BR>
     * Description: sendPhoto <BR>
     *
     * @param activity
     * @param maxPic   void<BR>
     */
    public static void sendPhoto(Activity activity, int maxPic) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(PhotoUtil.MAXPIC, maxPic);
        activity.startActivityForResult(intent, PhotoUtil.PhotoCode.PHOTOALBUM.getCode());
    }


    /**
     * 跳转拍照，并没有设置拍照路径，在onActivityResult方法内根据Uri拿 Method name: sendCamera <BR>
     * Description: sendCamera <BR>
     *
     * @param activity void<BR>
     */
    public static void sendCamera(Activity activity) {
        try {
            Intent startCustomCameraIntent = new Intent(activity, CameraActivity.class);
            activity.startActivityForResult(startCustomCameraIntent, PhotoUtil.PhotoCode.CAMERA.getCode());
        } catch (Exception e) {
            LogUtil.e(tag, e.getMessage());
        }

    }
}
