package com.yykaoo.photolibrary.photo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.bean.PhotoBucket;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.callback.ResultCallback;
import com.yykaoo.photolibrary.photo.tool.DeviceUtil;
import com.yykaoo.photolibrary.photo.tool.LogUtil;
import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.tool.ViewUtil;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 * 选择相册类 name: PhotoActivity <BR>
 * description: please write your description <BR>
 * create date: 2015-6-5
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoActivity extends BasePhotoActivity implements OnClickListener {

    public static final String EXTRA_IMAGE_LIST = "PhotoActivity";

    private List<PhotoBucket> photolist;

    private PhotoBucket imageBucket;
    private GridView gridView;
    private PhotoGridAdapter adapter;
    private PhotoAlbum helper;
    private TextView photoName;
    protected static int maxPic = 3;
    private ImageView bt;
    private TextView preview;
    private RelativeLayout layoutSelectImg;
    private LinearLayout layoutPhotoList;
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";
    private PhotoPopupWindow mPhotoPopupWindow;
    protected static HashMap<String, PhotoItem> imgs;
    private String rightTitlr;
    private boolean isFries = true;
    ArrayList<String> selectPicture = new ArrayList<String>();
    ArrayList<PhotoItem> photoLists = new ArrayList<PhotoItem>();
    public static int iconSelected = R.drawable.photo_check;
    public static int iconUncheck = R.drawable.photo_uncheck;
    private boolean isSendPho;
    public static final int TYPEONE = 1;
    public static final int TYPEPATH = 3;//原图
    public static final int TYPETHUMBNAILPATH = 2;//缩略图

    /**
     * 保存已选中的图片 Method name: getImgs <BR>
     * Description: getImgs <BR>
     *
     * @return HashMap<String,PhotoItem><BR>
     */
    protected static HashMap<String, PhotoItem> getImgs() {
        if (imgs == null) {
            imgs = new HashMap<String, PhotoItem>();
        }
        return imgs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_act_image);
        if (imgs != null) {
            imgs.clear();
            imgs = new HashMap<String, PhotoItem>();
        }
        maxPic = getIntent().getIntExtra(PhotoUtil.MAXPIC, 3);
        isSendPho = getIntent().getBooleanExtra(PhotoUtil.SEND_PHOTO, false);

        if (isSendPho) {
            rightTitlr = "发送";
        } else {
            rightTitlr = "完成";
        }

        setTitle("相册");
        initView();
        initData();
        inttCallBack();
        initIcon();
        // initAlbumPopupWindw();
        new GetPhotoAlbumTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFries) {
            isFries = false;
        } else {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                refreshBtn();
            }
        }
    }

    /**
     * 初始化回调与监听 Method name: inttCallBack <BR>
     * Description: inttCallBack <BR>
     * void<BR>
     */
    private void inttCallBack() {
        preview.setOnClickListener(this);
        layoutPhotoList.setOnClickListener(this);
        adapter.setSelectedCallback(new PhotoGridAdapter.SelectedCallback() {

            public void onListen() {
                refreshBtn();
            }
        });
    }

    /**
     * 初始化View Method name: initView <BR>
     * Description: initView <BR>
     * void<BR>
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.photo_act_image_gridview);
        bt = (ImageView) findViewById(R.id.show_photos);
        photoName = (TextView) findViewById(R.id.photo_name);
        layoutSelectImg = (RelativeLayout) findViewById(R.id.photo_btn);
        preview = (TextView) findViewById(R.id.photo_act_image_preview);
        layoutPhotoList = (LinearLayout) findViewById(R.id.layout_photo_list);
        preview.setEnabled(false);
        if (isSendPho)
            preview.setText("发送");
        gridView.setLayoutTransition(ViewUtil.getLayoutTransition());
    }

    /**
     * 初始化数据 Method name: initData <BR>
     * Description: initData <BR>
     * void<BR>
     */
    private void initData() {
        adapter = new PhotoGridAdapter(new ArrayList<PhotoItem>(), PhotoActivity.this, maxPic);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (PhotoUtil.camera.equals(imageBucket.getImageItems().get(position).getImgPath())) {
                    PhotoHelper.sendCamera(PhotoActivity.this);
                /*} else if (maxPic == 1) {
                    String path = imageBucket.getImageItems().get(position).getImgPath();
                    PhotoActivity.getImgs().put(path, imageBucket.getImageItems().get(position));
                    Intent intent = new Intent();
                    intent.putExtra(PhotoUtil.IMGS, getImgList());
                    PhotoActivity.this.setResult(RESULT_OK, intent);
                    finish();*/
                } else {
                    PhotoHelper.sendPhotoPreView(PhotoActivity.this, (ArrayList<PhotoItem>) imageBucket.getImageItems()
                            , position, PhotoUtil.PhotoImgType.LOCAL, false, isSendPho);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == RESULT_OK) {
            if (arg0 == PhotoUtil.PhotoCode.PREVIEW.getCode()) {
                setResult(RESULT_OK, arg2);
                finish();

            } else if (arg0 == PhotoUtil.PhotoCode.CAMERA.getCode()) {
                if (arg2 != null) {
                    Uri mImageCaptureUri = arg2.getData();
                    if (mImageCaptureUri != null) {
                        PhotoItem item = helper.getImage(mImageCaptureUri);
                        String pat = PhotoAlbum.getPath(PhotoActivity.this, mImageCaptureUri);
                        item.setImgPath(pat);
                        ArrayList<PhotoItem> items = new ArrayList<PhotoItem>();
                        items.add(item);
                        Intent intent = new Intent();
                        intent.putExtra(PhotoUtil.IMGS, items);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_photo_list) {
            bt.setBackgroundResource(R.drawable.phone_big1);
            try {
                mPhotoPopupWindow.setAnimationStyle(R.style.alertAnimation);
            } catch (Exception e) {
                LogUtil.e(e);
            }
            mPhotoPopupWindow.showAsDropDown(layoutSelectImg, 0, 0);
            // 设置背景颜色变暗
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = .3f;
            getWindow().setAttributes(lp);

            /**
             * 预览
             */
        } else if (i == R.id.photo_act_image_preview) {//PhotoHelper.sendPhotoPreView(PhotoActivity.this, getImgList(), 0, PhotoUtil.PhotoImgType.LOCAL);
            Intent intent = new Intent();
            intent.putExtra(PhotoUtil.IMGS, getImgList());
            PhotoActivity.this.setResult(RESULT_OK, intent);
            finish();

        } else {
        }
    }

    /**
     * 刷新相册名称 Method name: refreshlable <BR>
     * Description: refreshlable <BR>
     * void<BR>
     */
    private void refreshlable() {
        photoName.setText(imageBucket.getImageBucket());
    }

    private void refreshBtn() {
        if (getImgs().size() == 0) {
            if (mSubmit != null) {
                mSubmit.setEnabled(false);
                mSubmit.setText(rightTitlr);
            }
            if (isSendPho)
                preview.setText("发送");
            else
                preview.setText("完成");
            preview.setEnabled(false);
        } else {
            preview.setEnabled(true);
            if (isSendPho) {
                mSubmit.setText(String.format(getString(R.string.photos_send), getImgs().size(), maxPic));
                preview.setText(String.format(getString(R.string.photos_send), getImgs().size(), maxPic));
            }else {
                mSubmit.setText(String.format(getString(R.string.photos_imgcnt), getImgs().size(), maxPic));
                preview.setText(String.format(getString(R.string.photos_imgcnt), getImgs().size(), maxPic));
            }
        }
    }

    /**
     * 初始化弹出相册列表 Method name: initAlbumPopupWindw <BR>
     * Description: initAlbumPopupWindw <BR>
     * void<BR>
     */
    private void initAlbumPopupWindw() {
        mPhotoPopupWindow = new PhotoPopupWindow(LayoutParams.MATCH_PARENT, (int) (DeviceUtil.getScreenHight() * 0.7), photolist, LayoutInflater.from(PhotoActivity.this).inflate(R.layout.photo_dialog_selectalbum, null));
        mPhotoPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                bt.setBackgroundResource(R.drawable.phone_big2);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mPhotoPopupWindow.setOnImageDirSelected(new ResultCallback<PhotoBucket>() {
            @Override
            public void onResult(PhotoBucket result) {
                super.onResult(result);
                imageBucket = result;
                adapter.refresh(result.getImageItems());
                result.getImageItems();
                refreshlable();
                mPhotoPopupWindow.dismiss();
            }
        });
    }

    /**
     * 获取已选中的图片列表 Method name: getImgList <BR>
     * Description: getImgList <BR>
     *
     * @return ArrayList<PhotoItem><BR>
     */
    protected static ArrayList<PhotoItem> getImgList() {
        ArrayList<PhotoItem> arrayList = new ArrayList<>();
        Iterator<Entry<String, PhotoItem>> itr = getImgs().entrySet().iterator();
        while (itr.hasNext()) {
            Entry<String, PhotoItem> entry = itr.next();
            arrayList.add(entry.getValue());
        }
        return arrayList;
    }


    private AsPhoToolbarText mSubmit;

    private void initIcon() {
        mSubmit = new AsPhoToolbarText(getContext());
        mSubmit.setEnabled(false);
        mSubmit.initializeViews(rightTitlr, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getImgList().size() == 0) {
                    ToastUtil.show("请选择照片");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(PhotoUtil.IMGS, getImgList());
                    PhotoActivity.this.setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        mToolbar.getToolbarRightLin().addView(mSubmit);
    }

    public static ArrayList<PhotoItem> getYourNeedImg(Intent intent) {
        ArrayList<PhotoItem> listExtra = intent.getParcelableArrayListExtra(PhotoUtil.IMGS);
        return listExtra;
    }


    /**
     * 获取所有相册图集 name: GetPhotoAlbumTask <BR>
     * description: please write your description <BR>
     * create date: 2015-6-11
     *
     * @author: HAOWU){GeTao}
     */
    class GetPhotoAlbumTask extends AsyncTask<String, Integer, List<PhotoBucket>> {

        @Override
        protected List<PhotoBucket> doInBackground(String... arg0) {
            helper = PhotoAlbum.newInstance();
            return helper.getImagesBucketList(getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<PhotoBucket> result) {
            imageBucket = result.get(0);
            photolist = result;
            refreshlable();
            adapter.refresh(imageBucket.getImageItems());
            initAlbumPopupWindw();
        }

    }

    @Override
    public void onReload() {

    }
}
