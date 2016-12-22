package com.yykaoo.photolibrary.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.camera.ImageUtility;
import com.yykaoo.photolibrary.photo.tool.DataCleanManager;
import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.tool.ViewUtil;
import com.yykaoo.photolibrary.photo.widget.HackyViewPager;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbar;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarImage;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarText;
import com.yykaoo.photolibrary.photo.widget.dialog.DialogHelper;
import com.yykaoo.photolibrary.photo.widget.dialog.IDialogListener;

import java.io.File;
import java.util.ArrayList;

/**
 * 预览 name: PhotoPreViewAct <BR>
 * description: please write your description <BR>
 * create date: 2015-6-9
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoPreViewAct extends BasePhotoActivity implements OnClickListener {

    public static final String EXTRA_IMAGE = "PreView:image";
    private static final String TAG = "PicViewPagerActivity";
    public ArrayList<PhotoItem> sidList;
    private HackyViewPager mViewPager;
    private PhotoPagerAdapter spAdapter;
    private FrameLayout imgviewmianFrameLayout;
    private int position;
    private int type;
    private int imgType;
    private ImageView selected;
    private ImageView photo_act_preview_isselected_raw;
    private RelativeLayout selectedLin;
    private RelativeLayout bottomRl;
    private RelativeLayout photo_act_preview_isselectedlin_raw;
    private AsPhoToolbarText asSumbit;
    private AsPhoToolbarImage asDelete;
    private int initCurrent;
    private boolean formWhere;
    private TextView photo_act_preview_former;
    private boolean isSendPho;
    private String rightTitlr;
    private TextView photo_act_preview_former_raw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_act_preview);
        getIntentData();
        initView();
        initData();
    }

    @Override
    protected void initToolbar() {
        hideToolBar();
    }

    /**
     * 获取Intent传递过来的数据 Method name: getIntentData <BR>
     * Description: getIntentData <BR>
     * void<BR>
     */
    private void getIntentData() {
        position = getIntent().getIntExtra(PhotoUtil.POSITION, 0);
        initCurrent = position;
        sidList = getIntent().getParcelableArrayListExtra(PhotoUtil.IMGS);
        if (PhotoUtil.camera.equals(sidList.get(0).getImgPath())) {
            sidList.remove(0);
            initCurrent = initCurrent - 1;
        }
        formWhere = getIntent().getBooleanExtra(PhotoUtil.FromWhere, true);
        type = getIntent().getIntExtra(PhotoUtil.TYPE, PhotoUtil.PhotoType.LOOK.getIntVlue());
        imgType = getIntent().getIntExtra(PhotoUtil.IMGTYPE, PhotoUtil.PhotoImgType.NETWORK.getIntVlue());
        isSendPho = getIntent().getBooleanExtra(PhotoUtil.SEND_PHOTO, false);

        if (isSendPho) {
            rightTitlr = "发送";
        } else {
            rightTitlr = "完成";
        }
    }

    private void initView() {
        mToolbar = (AsPhoToolbar) findViewById(R.id.photo_act_preview_tool);
        mToolbar.setBackgroundResource(R.color.color_default_pressed);
        setSupportActionBar(mToolbar.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        initNavigationIcon(mToolbar);
        if (Build.VERSION.SDK_INT >= 19) {
            mToolbar.paddingStatusBar();
        }

        mToolbar.getToolbarTitle().setTextColor(Color.WHITE);
        imgviewmianFrameLayout = (FrameLayout) findViewById(R.id.photo_act_preview_fl);
        selected = (ImageView) findViewById(R.id.photo_act_preview_isselected);
        photo_act_preview_isselected_raw = (ImageView) findViewById(R.id.photo_act_preview_isselected_raw);
        photo_act_preview_former_raw = (TextView) findViewById(R.id.photo_act_preview_former_raw);
        if (formWhere) {
            selected.setImageResource(PhotoActivity.iconSelected);
        } else {
            if (position > 0) {
                position -= 1;//viewgroup里position比集合index大一
            }
            refreshSelectType();
        }
        selectedLin = (RelativeLayout) findViewById(R.id.photo_act_preview_isselectedlin);
        bottomRl = (RelativeLayout) findViewById(R.id.photo_act_preview_isselectedrl);
        photo_act_preview_isselectedlin_raw = (RelativeLayout) findViewById(R.id.photo_act_preview_isselectedlin_raw);
        photo_act_preview_former = (TextView) findViewById(R.id.photo_act_preview_former);
        if (isSendPho) {
            photo_act_preview_former.setVisibility(View.VISIBLE);
            photo_act_preview_isselectedlin_raw.setVisibility(View.VISIBLE);
            photo_act_preview_former.setOnClickListener(this);
            photo_act_preview_isselectedlin_raw.setOnClickListener(this);
        }
        mViewPager = new HackyViewPager(this);
        imgviewmianFrameLayout.addView(mViewPager);
        selectedLin.setOnClickListener(this);
        mViewPager.setOffscreenPageLimit(3);
        if (type == PhotoUtil.PhotoType.PREVIEW.getIntVlue()) {
            bottomRl.setVisibility(View.VISIBLE);
        } else {
            bottomRl.setVisibility(View.GONE);
        }
        onCreateMenu();

    }

    @Override
    protected void initNavigationIconClick(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == PhotoUtil.PhotoType.DELETE.getIntVlue()) {
                    Intent intent = new Intent();
                    intent.putExtra(PhotoUtil.IMGS, sidList);
                    PhotoPreViewAct.this.setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    /**
     * 初始化数据 Method name: initData <BR>
     * Description: initData <BR>
     * void<BR>
     */
    private void initData() {
        spAdapter = new PhotoPagerAdapter(sidList, PhotoPreViewAct.this, imgType, initCurrent);
        spAdapter.setmClickPhotoListener(new PhotoPagerAdapter.ClickPhotoListener() {

            @Override
            public void onClickPhoto() {

                if (type == PhotoUtil.PhotoType.LOOK.getIntVlue()) {
                    finish();
                }
            }
        });
        mViewPager.setAdapter(spAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                spAdapter.setPresentVisibilityItem(arg0);
                position = arg0;
                setTitle(position + 1 + "/" + sidList.size());
                refreshSelectType();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        if (initCurrent == 0) {
            setTitle(1 + "/" + sidList.size());
        }
        mViewPager.setCurrentItem(initCurrent, true);// false平滑过渡取消，效果不显示
        mViewPager.setLayoutTransition(ViewUtil.getLayoutTransition());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.photo_act_preview_isselectedlin) {
            if (PhotoActivity.getImgs().containsKey(getPhotoItem().getImgPath())) {//移除缩略图
                PhotoActivity.getImgs().remove(getPhotoItem().getImgPath());
                selected.setImageResource(PhotoActivity.iconUncheck);
            } else {
                if (PhotoActivity.getImgs().size() < PhotoActivity.maxPic) {//添加缩略图
                    PhotoActivity.getImgs().put(getPhotoItem().getImgPath(), putTypePhotoItem(PhotoActivity.TYPETHUMBNAILPATH));
                    selected.setImageResource(PhotoActivity.iconSelected);
                } else {
                    ToastUtil.show("最多选择" + PhotoActivity.maxPic + "张图片");
                }
            }
            refreshBtn();

        } else if (i == R.id.photo_act_preview_isselectedlin_raw) {//如果"选中"没选就选中，已选中则不管
            if (PhotoActivity.getImgs().size() < PhotoActivity.maxPic) {
                selected.setImageResource(PhotoActivity.iconSelected);
            }

            //选择原图
            if (PhotoActivity.getImgs().containsKey(getPhotoItem().getImgPath())) {
                if (getPhotoItem().getImgPathType() == PhotoActivity.TYPEPATH) {//已选原图
                    PhotoActivity.getImgs().put(getPhotoItem().getImgPath(), putTypePhotoItem(PhotoActivity.TYPETHUMBNAILPATH));
                    photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconUncheck);
                } else {//已选缩略图
                    PhotoActivity.getImgs().put(getPhotoItem().getImgPath(), putTypePhotoItem(PhotoActivity.TYPEPATH));
                    photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconSelected);
                }
            } else {
                if (PhotoActivity.getImgs().size() < PhotoActivity.maxPic) {
                    PhotoActivity.getImgs().put(getPhotoItem().getImgPath(), putTypePhotoItem(PhotoActivity.TYPEPATH));
                    photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconSelected);
                } else {
                    ToastUtil.show("最多选择" + PhotoActivity.maxPic + "张图片");
                }
            }
            refreshSelectType();
            refreshBtn();

        } else {
        }
    }

    private void refreshBtn() {
        if (PhotoActivity.getImgs().size() != 0) {
            if (isSendPho) {
                asSumbit.setText(String.format(getString(R.string.photos_send), PhotoActivity.getImgs().size(), PhotoActivity.maxPic));
            } else {
                asSumbit.setText(String.format(getString(R.string.photos_imgcnt), PhotoActivity.getImgs().size(), PhotoActivity.maxPic));
            }
            asSumbit.setEnabled(true);
        } else {
            asSumbit.setText(rightTitlr);
            if (!isSendPho) {
                asSumbit.setEnabled(false);
            }
        }
    }

    //当前图片实体
    private PhotoItem getPhotoItem() {
        return sidList.get(position);
    }

    private PhotoItem putTypePhotoItem(int type) {
        PhotoItem photoItem = getPhotoItem();
        photoItem.setImgPathType(type);
        return photoItem;
    }

    private void refreshSelectType() {
        if (isSendPho) {
            if (PhotoActivity.getImgs().containsKey(getPhotoItem().getImgPath())) {
                selected.setImageResource(PhotoActivity.iconSelected);
                if (PhotoActivity.getImgs().get(getPhotoItem().getImgPath()).getImgPathType() == PhotoActivity.TYPEPATH) {
                    String imageSize = getImageSize();
                    photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconSelected);
                    photo_act_preview_former_raw.setText("原图（" + imageSize + ")");
                } else {
                    photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconUncheck);
                    photo_act_preview_former_raw.setText("原图");
                }
            } else {
                selected.setImageResource(PhotoActivity.iconUncheck);
                photo_act_preview_isselected_raw.setImageResource(PhotoActivity.iconUncheck);
                photo_act_preview_former_raw.setText("原图");
            }
        } else {
            if (PhotoActivity.getImgs().containsKey(getPhotoItem().getImgPath())) {
                selected.setImageResource(PhotoActivity.iconSelected);
            } else {
                selected.setImageResource(PhotoActivity.iconUncheck);
            }
        }
    }

    private void onCreateMenu() {
        if (type == PhotoUtil.PhotoType.DELETE.getIntVlue()) {
            asDelete = new AsPhoToolbarImage(getContext());
            asDelete.initializeViews(R.drawable.ease_mm_title_remove, new OnClickListener() {
                @Override
                public void onClick(View v) {

                    alert("提示", "您确定要删除这张图片吗？", "取消", "确定", new IDialogListener() {

                        @Override
                        public void onPositiveClick() {

                        }

                        @Override
                        public void onNegativeClick() {
                            spAdapter.remove(position);
                            sidList.remove(position);

                            if (sidList.size() == 0) {
                                setTitle((position) + "/" + sidList.size());
                                onBackPressed();
                            }
                            setTitle((position + 1) + "/" + sidList.size());
                        }
                    });
                }
            });
            mToolbar.getToolbarRightLin().addView(asDelete, 0);
            AsPhoToolbarImage asBack = new AsPhoToolbarImage(getContext());
            asBack.initializeViews(R.drawable.icon_back, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mToolbar.getToolbarLeftLin().addView(asBack, 0);//加个0 就靠边了
        } else if (type == PhotoUtil.PhotoType.PREVIEW.getIntVlue()) {
            asSumbit = new AsPhoToolbarText(getContext());
            asSumbit.initializeViews(rightTitlr, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSendPho) {
                        if (PhotoActivity.getImgList().isEmpty()) {
                            PhotoActivity.getImgs().put(sidList.get(position).getThumbnailPath(), sidList.get(position));
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra(PhotoUtil.IMGS, PhotoActivity.getImgList());
                    PhotoPreViewAct.this.setResult(RESULT_OK, intent);
                    finish();
                }
            });
            refreshBtn();
            mToolbar.getToolbarRightLin().addView(asSumbit, 0);

            AsPhoToolbarImage asBack = new AsPhoToolbarImage(getContext());
            asBack.initializeViews(R.drawable.icon_back, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mToolbar.getToolbarLeftLin().addView(asBack, 0);//加个0 就靠边了
        } else if (type == PhotoUtil.PhotoType.LOOK.getIntVlue()) {
            AsPhoToolbarImage asBack = new AsPhoToolbarImage(getContext());
            asBack.initializeViews(R.drawable.icon_back, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mToolbar.getToolbarLeftLin().addView(asBack, 0);//加个0 就靠边了
            asSumbit = new AsPhoToolbarText(getContext());

            asSumbit.initializeViews("保存", new OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveImageToLoacl(sidList.get(position).getImgPath());
                }
            });
            mToolbar.getToolbarRightLin().addView(asSumbit, 0);
        }
    }

    private void saveImageToLoacl(final String thumbPath) {
        new DialogHelper().alert("", "您是否要将该图片保存到本地？", "取消", "确定", new IDialogListener() {
            @Override
            public void onNegativeClick() {
                Glide.with(getContext())
                        .load(thumbPath)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Uri uri = ImageUtility.savePicture(getContext(), resource);
                                if (uri != null) {
                                    ToastUtil.show("图片已保存至相册");
                                }
                            }
                        });
            }

            @Override
            public void onPositiveClick() {
            }
        },this);
    }

    @Override
    public void onBackPressed() {
        if (type == PhotoUtil.PhotoType.DELETE.getIntVlue()) {
            Intent intent = new Intent();
            intent.putExtra(PhotoUtil.IMGS, sidList);
            PhotoPreViewAct.this.setResult(RESULT_OK, intent);

        }
        finish();
    }

    @Override
    public void onReload() {

    }

    @Override
    protected void initNavigationIcon(AsPhoToolbar AsPhoToolbar) {
        AsPhoToolbarImage AsPhoToolbarImage = new AsPhoToolbarImage(getContext());
        initNavigationIconClick(AsPhoToolbarImage);
        AsPhoToolbar.getToolbarLeftLin().addView(AsPhoToolbarImage);
    }

    public String getImageSize() {
        PhotoItem photoItem;
        if (initCurrent == 0) {
            photoItem = sidList.get(initCurrent);
        } else {
            photoItem = sidList.get(position);
        }

        String imgPath = photoItem.getImgPath();
        File file = new File(imgPath);
        long l = file.length();
        String imageSize = DataCleanManager.getFormatSize(l);
        return imageSize;
    }
}
