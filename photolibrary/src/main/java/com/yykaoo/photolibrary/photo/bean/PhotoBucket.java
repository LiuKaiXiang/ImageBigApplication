package com.yykaoo.photolibrary.photo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 图片的相册集合实体类 name: ImageBucket <BR>
 * description: please write your description <BR>
 * create date: 2015-6-3
 *
 * @author: HAOWU)GeTao
 */
public class PhotoBucket implements Serializable {

    /**
     * define a field serialVersionUID which type is long
     */
    private static final long serialVersionUID = 1L;
    private int imgCount;
    private String imageBucket;
    private List<PhotoItem> imageItems;
    private String firstImg;
    private boolean isSelect = false;
    ;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public PhotoBucket(int imgCount, String imageBucket, List<PhotoItem> imageItems) {
        super();
        this.imgCount = imgCount;
        this.imageBucket = imageBucket;
        this.imageItems = imageItems;
    }

    public PhotoBucket() {
        super();
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public String getImageBucket() {
        return imageBucket;
    }

    public void setImageBucket(String imageBucket) {
        this.imageBucket = imageBucket;
    }

    public List<PhotoItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<PhotoItem> imageItems) {
        this.imageItems = imageItems;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

}
