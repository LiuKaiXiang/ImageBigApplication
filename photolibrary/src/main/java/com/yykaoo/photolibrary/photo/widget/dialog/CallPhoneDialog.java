package com.yykaoo.photolibrary.photo.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;

import com.yykaoo.photolibrary.photo.tool.ToastUtil;
import com.yykaoo.photolibrary.photo.widget.dialog.listener.OnBtnClickL;
import com.yykaoo.photolibrary.photo.widget.dialog.widget.NormalDialog;

/**
 * Created by RENJIE on 16/7/16.
 */
public class CallPhoneDialog extends NormalDialog {


    public CallPhoneDialog(final Context context, final String phoneNumber) {
        super(context);

        content(" 拨打: " + phoneNumber);
        contentGravity(Gravity.CENTER);
        isTitleShow(false);
        btnText("取消", "呼叫");
        setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (null == context) {
                    dismiss();
                    return;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + phoneNumber);
                    intent.setData(data);
                    context.startActivity(intent);
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.show("拨打失败 请重试");
                    dismiss();
                }

            }
        });
    }

}
