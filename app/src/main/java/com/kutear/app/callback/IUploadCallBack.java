package com.kutear.app.callback;

/**
 * Created by kutear on 15-10-7.
 * 七牛上传模块回调
 */
public interface IUploadCallBack {
    void onSuccess(String url);

    void onError(String error);

    void onProcess(double process);
}
