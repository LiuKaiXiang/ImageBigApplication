package com.yykaoo.photolibrary.photo.widget;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.PhotoPagerAdapter;
import com.yykaoo.photolibrary.photo.PhotoUtil;
import com.yykaoo.photolibrary.photo.bean.PhotoItem;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbar;
import com.yykaoo.photolibrary.photo.widget.actionbar.AsPhoToolbarImage;

import java.util.ArrayList;


/**
 * Created by donglua on 15/6/21.
 */
public class ImagePagerFragment extends Fragment {

    public final static String ARG_PATH = "PATHS";
    public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";

    private ArrayList<PhotoItem> paths = new ArrayList<PhotoItem>();
    ;
    private PhotoPagerAdapter mPagerAdapter;

    public final static long ANIM_DURATION = 200L;

    public final static String ARG_THUMBNAIL_TOP = "THUMBNAIL_TOP";
    public final static String ARG_THUMBNAIL_LEFT = "THUMBNAIL_LEFT";
    public final static String ARG_THUMBNAIL_WIDTH = "THUMBNAIL_WIDTH";
    public final static String ARG_THUMBNAIL_HEIGHT = "THUMBNAIL_HEIGHT";
    public final static String ARG_HAS_ANIM = "HAS_ANIM";

    private int thumbnailTop = 0;
    private int thumbnailLeft = 0;
    private int thumbnailWidth = 0;
    private int thumbnailHeight = 0;

    private boolean hasAnim = false;

    private final ColorMatrix colorizerMatrix = new ColorMatrix();

    private int currentItem = 0;

    private ViewPager mViewPager;

    // private FrameLayout imgviewmianFrameLayout;
    private int type;
    private int imgType;

    // private ImageView selected;
    // private RelativeLayout selectedLin;
    // private RelativeLayout bottomRl;

    public static ImagePagerFragment newInstance(ArrayList<PhotoItem> paths, int currentItem, int[] screenLocation, int thumbnailWidth, int thumbnailHeight, int imgType, int type) {
        ImagePagerFragment f = new ImagePagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PATH, paths);
        args.putInt(ARG_CURRENT_ITEM, currentItem);
        args.putInt(ARG_THUMBNAIL_LEFT, screenLocation[0]);
        args.putInt(ARG_THUMBNAIL_TOP, screenLocation[1]);
        args.putInt(ARG_THUMBNAIL_WIDTH, thumbnailWidth);
        args.putInt(ARG_THUMBNAIL_HEIGHT, thumbnailHeight);
        args.putInt(PhotoUtil.IMGTYPE, imgType);
        args.putInt(PhotoUtil.TYPE, PhotoUtil.PhotoType.DELETE.getIntVlue());
        args.putBoolean(ARG_HAS_ANIM, true);

        f.setArguments(args);
        return f;
    }

    private int initCurrent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (paths != null) {
                paths.clear();
            }
            paths = (ArrayList<PhotoItem>) bundle.getSerializable(ARG_PATH);
            hasAnim = bundle.getBoolean(ARG_HAS_ANIM);
            currentItem = bundle.getInt(ARG_CURRENT_ITEM);
            thumbnailTop = bundle.getInt(ARG_THUMBNAIL_TOP);
            thumbnailLeft = bundle.getInt(ARG_THUMBNAIL_LEFT);
            thumbnailWidth = bundle.getInt(ARG_THUMBNAIL_WIDTH);
            thumbnailHeight = bundle.getInt(ARG_THUMBNAIL_HEIGHT);
            type = bundle.getInt(PhotoUtil.TYPE, PhotoUtil.PhotoType.LOOK.getIntVlue());
            imgType = bundle.getInt(PhotoUtil.IMGTYPE, PhotoUtil.PhotoImgType.NETWORK.getIntVlue());

        }

        initCurrent = currentItem;
        if (PhotoUtil.camera.equals(paths.get(0).getImgPath())) {
            paths.remove(0);
            initCurrent = initCurrent - 1;
        }

        mPagerAdapter = new PhotoPagerAdapter(paths, getActivity(), imgType, initCurrent);
        mPagerAdapter.setmClickPhotoListener(new PhotoPagerAdapter.ClickPhotoListener() {

            @Override
            public void onClickPhoto() {

                if (type == PhotoUtil.PhotoType.LOOK.getIntVlue()) {
                    getActivity().onBackPressed();
                }
            }
        });
    }

    /**
     * 初始化数据 Method name: initData <BR>
     * Description: initData <BR>
     * void<BR>
     */
    private void initData() {

        mViewPager.setAdapter(mPagerAdapter);

        if (initCurrent == 0) {
            mAsPhoToolbar.setTitle(1 + "/" + paths.size());
        }
        mViewPager.setCurrentItem(initCurrent, true);// false平滑过渡取消，效果不显示

        // mViewPager.setLayoutTransition(ViewUtil.getLayoutTransition());
    }

    private AsPhoToolbar mAsPhoToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.photo_fragment_image_pager, container, false);

        mAsPhoToolbar = (AsPhoToolbar) rootView.findViewById(R.id.vp_photos_tool);
        mAsPhoToolbar.setBackgroundResource(android.R.color.transparent);
        initNavigationIcon();

        // imgviewmianFrameLayout = (FrameLayout)
        // rootView.findViewById(R.id.photo_act_preview_fl);
        // selected = (ImageView)
        // rootView.findViewById(R.id.photo_act_preview_isselected);
        // selectedLin = (RelativeLayout)
        // rootView.findViewById(R.id.photo_act_preview_isselectedlin);
        // bottomRl = (RelativeLayout)
        // rootView.findViewById(R.id.photo_act_preview_isselectedrl);

        mViewPager = (ViewPager) rootView.findViewById(R.id.vp_photos);
        // imgviewmianFrameLayout.addView(mViewPager);
        // selectedLin.setVisibility(View.GONE);
        // mViewPager.setBackgroundResource(android.R.color.black);
        // imgviewmianFrameLayout.setBackgroundResource(android.R.color.black);
        mViewPager.setOffscreenPageLimit(3);
        // if (type == PhotoUtil.PhotoType.PREVIEW.getIntVlue()) {
        // bottomRl.setVisibility(View.VISIBLE);
        // } else {
        // bottomRl.setVisibility(View.GONE);
        // }

        // mViewPager = (ViewPager) rootView.findViewById(R.id.vp_photos);
        // mViewPager.setAdapter(mPagerAdapter);
        // mViewPager.setCurrentItem(currentItem);
        // mViewPager.setOffscreenPageLimit(5);

        initData();

        // Only run the animation if we're coming from the parent activity, not
        // if
        // we're recreated automatically by the window manager (e.g., device
        // rotation)
        if (savedInstanceState == null && hasAnim) {
            ViewTreeObserver observer = mViewPager.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {

                    mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions
                    // are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mViewPager.getLocationOnScreen(screenLocation);
                    thumbnailLeft = thumbnailLeft - screenLocation[0];
                    thumbnailTop = thumbnailTop - screenLocation[1];

                    runEnterAnimation();

                    return true;
                }
            });
        }

        // mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
        //
        // @Override
        // public void onPageSelected(int arg0) {
        // // if
        // (PhotoActivity.getImgs().containsKey(paths.get(arg0).getImgPath())) {
        // // selected.setImageResource(PhotoActivity.iconSelected);
        // // } else {
        // // selected.setImageResource(PhotoActivity.iconUncheck);
        // // }
        //
        // currentItem = arg0;
        // mAsPhoToolbar.setTitle(currentItem + 1 + "/" + paths.size());
        // }
        //
        // @Override
        // public void onPageScrolled(int arg0, float arg1, int arg2) {
        //
        // }
        //
        // @Override
        // public void onPageScrollStateChanged(int arg0) {
        // }
        // });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hasAnim = currentItem == position;
                currentItem = position;
                mAsPhoToolbar.setTitle(currentItem + 1 + "/" + paths.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    protected void initNavigationIcon() {
        AsPhoToolbarImage AsPhoToolbarImage = new AsPhoToolbarImage(getActivity());
        AsPhoToolbarImage.initializeViews(R.drawable.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mAsPhoToolbar.getToolbarLeftLin().addView(AsPhoToolbarImage);
    }


    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location, colorizing it in parallel. In parallel, the background of
     * the activity is fading in. When the pictue is in place, the text
     * description drops down.
     */
    private void runEnterAnimation() {
        final long duration = ANIM_DURATION;

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        ViewHelper.setPivotX(mViewPager, 0);
        ViewHelper.setPivotY(mViewPager, 0);
        ViewHelper.setScaleX(mViewPager, (float) thumbnailWidth / mViewPager.getWidth());
        ViewHelper.setScaleY(mViewPager, (float) thumbnailHeight / mViewPager.getHeight());
        ViewHelper.setTranslationX(mViewPager, thumbnailLeft);
        ViewHelper.setTranslationY(mViewPager, thumbnailTop);

        // Animate scale and translation to go from thumbnail to full size
        ViewPropertyAnimator.animate(mViewPager).setDuration(duration).scaleX(1).scaleY(1).translationX(0).translationY(0).setInterpolator(new DecelerateInterpolator());

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mViewPager.getBackground(), "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate a color filter to take the image from grayscale to full
        // color.
        // This happens in parallel with the image scaling and moving into
        // place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(ImagePagerFragment.this, "saturation", 0, 1);
        colorizer.setDuration(duration);
        colorizer.start();

    }

    /**
     * The exit animation is basically a reverse of the enter animation, except
     * that if the orientation has changed we simply scale the picture back into
     * the center of the screen.
     *
     * @param endAction This action gets run after the animation completes (this is
     *                  when we actually switch activities)
     */
    public void runExitAnimation(final Runnable endAction) {

        if (!getArguments().getBoolean(ARG_HAS_ANIM, false) || !hasAnim) {
            endAction.run();
            return;
        }

        final long duration = ANIM_DURATION;

        // Animate image back to thumbnail size/location
        ViewPropertyAnimator.animate(mViewPager).setDuration(duration).setInterpolator(new AccelerateInterpolator()).scaleX((float) thumbnailWidth / mViewPager.getWidth()).scaleY((float) thumbnailHeight / mViewPager.getHeight()).translationX(thumbnailLeft).translationY(thumbnailTop).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                endAction.run();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mViewPager.getBackground(), "alpha", 0);
        bgAnim.setDuration(duration);
        bgAnim.start();

        // Animate a color filter to take the image back to grayscale,
        // in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(ImagePagerFragment.this, "saturation", 1, 0);
        colorizer.setDuration(duration);
        colorizer.start();
    }

    /**
     * This is called by the colorizing animator. It sets a saturation factor
     * that is then passed onto a filter on the picture's drawable.
     *
     * @param value saturation
     */
    public void setSaturation(float value) {
        colorizerMatrix.setSaturation(value);
        ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(colorizerMatrix);
        mViewPager.getBackground().setColorFilter(colorizerFilter);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public ArrayList<PhotoItem> getPaths() {
        return paths;
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

}
