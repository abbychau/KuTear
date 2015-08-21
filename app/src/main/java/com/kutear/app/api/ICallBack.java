package com.kutear.app.api;

/**
 * Created by kutear.guo on 2015/8/12.
 */
public interface ICallBack {
    static final int RESPONE_OK = 200;
    static final int RESPONE_FAILED = 404;
    static final int RESPONE_NO_URI = 0x0000;
    static final int NOT_LOGIN = 0x0001;
    static final int HAS_LOGIN = 0x0002;

    void onSuccess(int statusCode, String str);

    void onError(int statusCode, String str);
}
