package com.yykaoo.photolibrary.photo.bean;

import android.text.TextUtils;

/**
 * Time: 2016/3/8 16. NAME: jony.
 */
public class BaseResp implements java.io.Serializable {

    protected final int toLogin = -1011;
    protected final int isDataNull = -100;
    protected final int isSuccess = 1;
    protected final int isListPageNull = -104;
    public Integer states;
    private String msg;
    private Boolean success;

    public boolean isSuccess() {
        if (null == this.states) {
            return false;
        }
        if (isSuccess == this.states) {
            return true;
        }
        return false;
    }

    public boolean isToLogin() {
        if (null == this.states) {
            return false;
        }
        if (toLogin == this.states) {
            return true;
        }
        return false;
    }

    public Integer getStates() {
        if (null == this.states) {
            return -11;
        }
        return this.states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public boolean isEmpty() {
        if (null == this.states) {
            return false;
        }
        if (isDataNull == this.states) {
            return true;
        }
        return false;
    }

    public boolean isListPageEmpty() {
        if (null == this.states) {
            return false;
        }
        if (isListPageNull == this.states) {
            return true;
        }
        return false;
    }

    public String getMsg() {
        if (TextUtils.isEmpty(this.msg)) {
            return "";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
