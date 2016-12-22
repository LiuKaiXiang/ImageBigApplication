
package com.yykaoo.photolibrary.photo.basic;

import android.content.Intent;

import com.yykaoo.photolibrary.photo.PhotoHelper;
import com.yykaoo.photolibrary.photo.PhotoUtil;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;

import java.util.ArrayList;


/**
 * 选择相册基类 name: BasePhotoAct <BR>
 * description: please write your description <BR>
 * create date: 2015-6-17
 *
 * @author: HAOWU){GeTao}
 */
public abstract class BasePhotoAct extends BasePhotoActivity {

    /**
     * 选择相册时返回数据 Method name: loadImg <BR>
     * Description: loadImg <BR>
     *
     * @param photoItems void<BR>
     */
    protected abstract void loadImg(ArrayList<PhotoItem> photoItems);

    /**
     * 预览时返回数据,涉及到删除图片 Method name: updateImg <BR>
     * Description: updateImg <BR>
     *
     * @param photoItems void<BR>
     */
    protected abstract void updateImg(ArrayList<PhotoItem> photoItems);

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == RESULT_OK) {
            if (arg0 == PhotoUtil.PhotoCode.PHOTOALBUM.getCode()) {
                ArrayList<PhotoItem> arrayList = (ArrayList<PhotoItem>) arg2.getSerializableExtra(PhotoUtil.IMGS);
                if (arrayList != null) {
                    loadImg(arrayList);
                }

            } else if (arg0 == PhotoUtil.PhotoCode.PREVIEW.getCode()) {
                ArrayList<PhotoItem> arrayList = (ArrayList<PhotoItem>) arg2.getSerializableExtra(PhotoUtil.IMGS);
                if (arrayList != null) {
                    updateImg(arrayList);
                }
            }
        }
    }

    /**
     * 进入预览删除界面 Method name: sendPhotoDelete <BR>
     * Description: sendPhotoDelete <BR>
     *
     * @param photoItems
     * @param position   void<BR>
     */
    protected void sendPhotoDelete(ArrayList<PhotoItem> photoItems, int position) {
        PhotoHelper.sendPhotoDelete(this, photoItems, position);
    }

    /**
     * s进入选择相册界面 Method name: sendPhoto <BR>
     * Description: sendPhoto <BR>
     *
     * @param maxPic void<BR>
     */
    protected void sendPhoto(int maxPic) {
        PhotoHelper.sendPhoto(this, maxPic);
    }

}
