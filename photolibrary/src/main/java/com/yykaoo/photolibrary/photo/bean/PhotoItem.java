package com.yykaoo.photolibrary.photo.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 相册图片实体类 name: ImageItem <BR>
 * description: please write your description <BR>
 * create date: 2015-6-3
 *
 * @author: HAOWU)GeTao
 */
public class PhotoItem extends BaseResp implements Parcelable {

    private static final long serialVersionUID = 1218598410388045290L;
    private String imgId;
    private String thumbnailPath;
    private String imgPath;
    private int imgPathType;//0：不用，1，用缩略，2，用原图

    public PhotoItem(String imgId, String thumbnailPath, String imgPath) {
        super();
        this.imgId = imgId;
        this.thumbnailPath = thumbnailPath;
        this.imgPath = imgPath;
    }

    public PhotoItem(String imgId, String thumbnailPath) {
        super();
        this.imgId = imgId;
        this.thumbnailPath = thumbnailPath;
    }

    public PhotoItem() {
        super();
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getImgPathType() {
        return imgPathType;
    }

    public void setImgPathType(int imgPathType) {
        this.imgPathType = imgPathType;
    }

    public PhotoItem(String imgPath) {
        super();
        this.imgPath = imgPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgId);
        dest.writeString(this.thumbnailPath);
        dest.writeString(this.imgPath);
        dest.writeInt(this.imgPathType);
    }

    protected PhotoItem(Parcel in) {
        this.imgId = in.readString();
        this.thumbnailPath = in.readString();
        this.imgPath = in.readString();
        this.imgPathType = in.readInt();
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        @Override
        public PhotoItem createFromParcel(Parcel source) {
            return new PhotoItem(source);
        }

        @Override
        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };
}