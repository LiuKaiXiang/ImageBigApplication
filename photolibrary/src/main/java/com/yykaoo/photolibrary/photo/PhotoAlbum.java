package com.yykaoo.photolibrary.photo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextUtils;

import com.yykaoo.photolibrary.photo.bean.PhotoBucket;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.tool.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 * 获取相册帮助类 name: PhotoAlbum <BR>
 * description: please write your description <BR>
 * create date: 2015-6-5
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoAlbum {

    final String TAG = getClass().getSimpleName();
    private Context context;
    private ContentResolver cr;
    // private List<HashMap<String, String>> albumList;
    private List<PhotoItem> photoItems;
    private String friesImg;
    private static PhotoAlbum instance;

    private PhotoAlbum() {
        // albumList = new ArrayList<HashMap<String, String>>();
    }

    public static PhotoAlbum newInstance() {
        if (instance == null) {
            synchronized (PhotoAlbum.class) {
                instance = new PhotoAlbum();
            }
        }
        return instance;
    }

    /**
     * 初始化contentResolver Method name: init <BR>
     * Description: init <BR>
     *
     * @param context void<BR>
     */
    private void init(Context context) {
        if (this.context == null) {
            this.context = context;
            cr = context.getContentResolver();
        }
    }

    /**
     * 获取缩略图 Method name: getThumbnailColumnData <BR>
     * Description: getThumbnailColumnData <BR>
     *
     * @return HashMap<String,String><BR>
     */
    private HashMap<String, String> getThumbnailColumnData() {
        String[] projection = {Thumbnails.IMAGE_ID, Thumbnails.DATA};

        Cursor cursor = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        HashMap<String, String> thumbnails = new HashMap<String, String>();
        if (cursor.moveToFirst()) {
            int image_id;
            String image_path;
            int image_idColumn = cursor.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cursor.getColumnIndex(Thumbnails.DATA);
            do {
                image_id = cursor.getInt(image_idColumn);
                image_path = cursor.getString(dataColumn);
                thumbnails.put("" + image_id, image_path);
            } while (cursor.moveToNext());
        }
        return thumbnails;
    }

    private String getThumbnailPath(Uri uri) {
        String[] projection = {Thumbnails.DATA};

        Cursor cursor = cr.query(uri, projection, null, null, null);
        String thumbnailsPath = "";
        if (cursor.moveToFirst()) {
            int dataColumn = cursor.getColumnIndex(Thumbnails.DATA);
            do {
                thumbnailsPath = cursor.getString(dataColumn);
            } while (cursor.moveToNext());
        }
        return thumbnailsPath;
    }

    public PhotoItem getImage(Uri uri) {
        PhotoItem item = new PhotoItem();
        Cursor cur = null;
        try {
            String thumbnailsPath = getThumbnailPath(uri);
            String columns[] = new String[]{Media._ID, Media.DATA};
            // 得到一个游标
            cur = cr.query(uri, columns, null, null, null);
            if (cur.moveToFirst()) {
                // 获取指定列的索引
                int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
                int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
                do {
                    String _id = cur.getString(photoIDIndex);
                    String path = cur.getString(photoPathIndex);
                    item.setImgId(_id);
                    item.setImgPath(path);
                    item.setThumbnailPath(thumbnailsPath);

                } while (cur.moveToNext());
            }
            return item;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            return item;
        }
    }

    /**
     * 得到原图
     */
    // void getAlbum() {
    // String[] projection = { Albums._ID, Albums.ALBUM, Albums.ALBUM_ART, Albums.ALBUM_KEY, Albums.ARTIST,
    // Albums.NUMBER_OF_SONGS };
    // Cursor cursor = cr.query(Albums.EXTERNAL_CONTENT_URI, projection, null, null, null);
    // getAlbumColumnData(cursor);
    //
    // }

    /**
     * 从本地数据库中得到原图
     *
     * @param cur
     */
    // private void getAlbumColumnData(Cursor cur) {
    // if (cur.moveToFirst()) {
    // int _id;
    // String album;
    // String albumArt;
    // String albumKey;
    // String artist;
    // int numOfSongs;
    // int _idColumn = cur.getColumnIndex(Albums._ID);
    // int albumColumn = cur.getColumnIndex(Albums.ALBUM);
    // int albumArtColumn = cur.getColumnIndex(Albums.ALBUM_ART);
    // int albumKeyColumn = cur.getColumnIndex(Albums.ALBUM_KEY);
    // int artistColumn = cur.getColumnIndex(Albums.ARTIST);
    // int numOfSongsColumn = cur.getColumnIndex(Albums.NUMBER_OF_SONGS);
    //
    // do {
    // _id = cur.getInt(_idColumn);
    // album = cur.getString(albumColumn);
    // albumArt = cur.getString(albumArtColumn);
    // albumKey = cur.getString(albumKeyColumn);
    // artist = cur.getString(artistColumn);
    // numOfSongs = cur.getInt(numOfSongsColumn);
    //
    // Log.i(TAG, _id + " album:" + album + " albumArt:" + albumArt + "albumKey: " + albumKey + " artist: " + artist +
    // " numOfSongs: "
    // + numOfSongs + "---");
    // HashMap<String, String> hash = new HashMap<String, String>();
    // hash.put("_id", _id + "");
    // hash.put("album", album);
    // hash.put("albumArt", albumArt);
    // hash.put("albumKey", albumKey);
    // hash.put("artist", artist);
    // hash.put("numOfSongs", numOfSongs + "");
    // albumList.add(hash);
    //
    // } while (cur.moveToNext());
    //
    // }
    // }

    /**
     * 获取相册集合 Method name: buildImagesBucketList <BR>
     * Description: buildImagesBucketList <BR>
     *
     * @return HashMap<String,PhotoBucket><BR>
     */
    private HashMap<String, PhotoBucket> buildImagesBucketList() {
        long startTime = System.currentTimeMillis();
        HashMap<String, PhotoBucket> bucketLists = new HashMap<String, PhotoBucket>();
        // 构造缩略图索引
        HashMap<String, String> thumbnailList = getThumbnailColumnData();

        // 构造相册索引
        String columns[] = new String[]{Media._ID, Media.BUCKET_ID, Media.DATA, Media.BUCKET_DISPLAY_NAME};

        String sortOrder = Media.DATE_MODIFIED + " desc";
        // 得到一个游标
        Cursor cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null, sortOrder);
        if (cur.moveToFirst()) {
            // 获取指定列的索引
            int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
            int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
            // 获取图片总数
            int totalNum = cur.getCount();

            do {
                String _id = cur.getString(photoIDIndex);
                String path = cur.getString(photoPathIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);
                PhotoBucket bucket = bucketLists.get(bucketId);
                if (bucket == null) {
                    bucket = new PhotoBucket();
                    bucketLists.put(bucketId, bucket);
                    bucket.setImageItems(new ArrayList<PhotoItem>());
                    bucket.setImageBucket(bucketName);
                    bucket.setImgCount(totalNum);
                    if (TextUtils.isEmpty(thumbnailList.get(_id))) {
                        bucket.setFirstImg(path);
                        if (TextUtils.isEmpty(friesImg)) {
                            friesImg = path;
                        }
                    } else {
                        bucket.setFirstImg(thumbnailList.get(_id));
                        if (TextUtils.isEmpty(friesImg)) {
                            friesImg = thumbnailList.get(_id);
                        }
                    }
                }
                bucket.setImgCount(bucket.getImgCount() + 1);
                PhotoItem imageItem = new PhotoItem();
                imageItem.setImgId(_id);
                imageItem.setImgPath(path);
                imageItem.setThumbnailPath(thumbnailList.get(_id));
                bucket.getImageItems().add(imageItem);
                photoItems.add(imageItem);

            } while (cur.moveToNext());
        }
        long endTime = System.currentTimeMillis();
        LogUtil.d(TAG, "use time: " + (endTime - startTime) + " ms");
        return bucketLists;
    }

    /**
     * 得到相册
     *
     * @return
     */
    public List<PhotoBucket> getImagesBucketList(Context context) {
        init(context);
        photoItems = new ArrayList<PhotoItem>();
        HashMap<String, PhotoBucket> bucketList = buildImagesBucketList();
        List<PhotoBucket> tmpList = new ArrayList<PhotoBucket>();
        Iterator<Entry<String, PhotoBucket>> itr = bucketList.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<String, PhotoBucket> entry = (Entry<String, PhotoBucket>) itr.next();
            tmpList.add(entry.getValue());
        }
        PhotoBucket bucket = new PhotoBucket();
        bucket.setFirstImg(friesImg);
        bucket.setImageBucket("所有图片");
        bucket.setImgCount(photoItems.size());
        bucket.setSelect(true);

        PhotoItem item = new PhotoItem();
        item.setImgPath(PhotoUtil.camera);
        photoItems.add(0, item);
        bucket.setImageItems(photoItems);
        tmpList.add(0, bucket);
        return tmpList;
    }

    /**
     * 得到原始图像路径
     *
     * @param image_id
     * @return
     */
    public String getOriginalImagePath(String image_id) {
        String path = null;
        String[] projection = {Media._ID, Media.DATA};
        Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection, Media._ID + "=" + image_id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(Media.DATA));

        }
        return path;
    }

    /**
     * 4.4系统后相册的uri Method name: getPath <BR>
     * Description: getPath <BR>
     *
     * @param context
     * @param uri
     * @return String<BR>
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * 保存图片到手机 Method name: saveImageToGallery <BR>
     * Description: saveImageToGallery <BR>
     *
     * @param context
     * @param bmp
     * @param pash    void<BR>
     */
    //有问题，试试 ImageUtility.savePicture();
    public static void saveImageToGallery(Context context, Bitmap bmp, String pash) {
        // 首先保存图片
        File appDir = new File(pash);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + pash + fileName)));
    }

    /**
     * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other file-based
     * ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
