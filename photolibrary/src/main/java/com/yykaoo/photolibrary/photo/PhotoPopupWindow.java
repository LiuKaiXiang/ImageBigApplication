package com.yykaoo.photolibrary.photo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yykaoo.photolibrary.R;
import com.yykaoo.photolibrary.photo.basic.BasePopupWindowForListView;
import com.yykaoo.photolibrary.photo.bean.PhotoBucket;
import com.yykaoo.photolibrary.photo.callback.ResultCallback;

import java.util.List;


/**
 * 弹出相册列表 name: PhotoPopupWindow <BR>
 * description: please write your description <BR>
 * create date: 2015-6-9
 *
 * @author: HAOWU){GeTao}
 */
public class PhotoPopupWindow extends BasePopupWindowForListView<PhotoBucket> {

    private String tag = "PhotoPopupWindow";

    private ListView mListDir;
    private PhotoSelectAdapter adapter;

    public PhotoPopupWindow(int width, int height, List<PhotoBucket> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.photo_dialog_list);
        adapter = new PhotoSelectAdapter(mDatas, context);
        mListDir.setAdapter(adapter);
    }

    private ResultCallback<PhotoBucket> mImageDirSelected;

    public void setOnImageDirSelected(ResultCallback<PhotoBucket> mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setSelect(false);
                }
                mDatas.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                if (mImageDirSelected != null) {
                    mImageDirSelected.onResult(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
    }

}
