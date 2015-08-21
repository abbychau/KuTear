package com.kutear.app.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kutear.app.AppApplication;
import com.kutear.app.netutils.KStringRequest;
import com.kutear.app.utils.L;

import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/21.
 */
public class ApiAdmin {
    private static final String TAG = ApiAdmin.class.getSimpleName();

    protected static void getRequest(String url, final ICallBack callBack) {
        baseRequest(Request.Method.GET, url, null, callBack);
    }

    protected static void postRequest(String url, Map<String, String> params, final ICallBack callBack) {
        baseRequest(Request.Method.POST, url, params, callBack);
    }

    protected static void baseRequest(int requestType, final String url, Map<String, String> params, final ICallBack callBack) {
        KStringRequest request = new KStringRequest(requestType, url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                L.v(TAG, "请求成功(URL:" + url + "):" + s);
                if (callBack != null) {
                    callBack.onSuccess(ICallBack.RESPONE_OK, s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                L.v(TAG, "请求失败(URL:" + url + "):" + volleyError.getMessage() + " " + volleyError.toString());
                if (callBack != null) {
                    callBack.onError(ICallBack.RESPONE_FAILED, volleyError.getMessage());
                }
            }
        });
        request.setShouldCache(true);
        AppApplication.startRequest(request);
    }
}
