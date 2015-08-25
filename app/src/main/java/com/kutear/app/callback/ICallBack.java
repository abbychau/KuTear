package com.kutear.app.callback;

/**
 * Created by kutear.guo on 2015/8/12.
 * 最基本的回调接口
 */
public interface ICallBack {
    int RESPONSE_OK = 200;
    int RESPONSE_FAILED = 404;
    int RESPONSE_NO_URI = 0x0000;
    int NOT_LOGIN = 0x0001;
    int HAS_LOGIN = 0x0002;

    void onSuccess(int statusCode, String str);

    void onError(int statusCode, String str);
}
