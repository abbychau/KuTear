package com.kutear.app.api;

/**
 * Created by kutear.guo on 2015/8/12.
 */
public interface ICallBack {
    public static final int RESPONE_OK = 200;
    public static final int RESPONE_FAILED = 404;
    public static final int RESPONE_NO_URI = 0x0000;

    public void onSuccess(int statusCode, String str);

    public void onError(int statusCode, String str);
}
