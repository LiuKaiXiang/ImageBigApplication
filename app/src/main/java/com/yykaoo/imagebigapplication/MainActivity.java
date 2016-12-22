package com.yykaoo.imagebigapplication;

import android.os.Bundle;
import android.view.View;

import com.yykaoo.photolibrary.photo.PhotoHelper;
import com.yykaoo.photolibrary.photo.basic.BasePhotoActivity;

public class MainActivity extends BasePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoHelper.sendPhoto(MainActivity.this, 9);
            }
        });
    }
}
