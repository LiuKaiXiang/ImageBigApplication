package com.yykaoo.photolibrary.photo.widget.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yykaoo.photolibrary.R;


/**
 * TIME: 2016/5/6.
 * NAME:jony
 */
public class AsPhoToolbarText extends LinearLayout {

    private Context mContext;
    private TextView textView;
    private LinearLayout linearLayout;

    public AsPhoToolbarText(Context context) {
        this(context, null);
    }

    public AsPhoToolbarText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsPhoToolbarText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        bindViews(attrs);

    }

    private void bindViews(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.actionbar_pho_text, this);
        linearLayout = (LinearLayout) findViewById(R.id.actionbar_lin);
        textView = (TextView) findViewById(R.id.actionbar_tv);

    }

    public void initializeViews(String text, OnClickListener clickListener) {
        textView.setText(text);
        linearLayout.setOnClickListener(clickListener);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public TextView getTextView(){
        return textView;
    }
}
