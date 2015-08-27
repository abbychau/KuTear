package com.kutear.app.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.netutils.KStringRequest;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/21.
 * 所有后台
 */
public class ApiAdmin {
    private static final String TAG = ApiAdmin.class.getSimpleName();

    protected static void getRequest(String url, final ICallBack callBack) {
        baseRequest(Request.Method.GET, url, null, callBack);
    }

    protected static void postRequest(String url, Map<String, String> params, final ICallBack callBack) {
        baseRequest(Request.Method.POST, url, params, callBack);
    }

    protected static void baseRequest(final int requestType, final String url, final Map<String, String> params, final ICallBack callBack) {
        // TODO: 2015/8/23 首先需要判断是否已经登陆
        KStringRequest checkLogin = new KStringRequest(Request.Method.GET, Constant.URI_ADMIN, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (isLogin(s)) {
                    onRequest(requestType, url, params, callBack);
                } else {
                    ApiUser.autoLogin(new ICallBack() {
                        @Override
                        public void onSuccess(int statusCode, String str) {
                            onRequest(requestType, url, params, callBack);
                        }

                        @Override
                        public void onError(int statusCode, String str) {
                            callBack.onError(statusCode, AppApplication.getKString(R.string.can_not_web_admin));
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.onError(ICallBack.RESPONSE_FAILED, AppApplication.getKString(R.string.can_not_web_admin));
            }
        });
        AppApplication.startRequest(checkLogin);
    }

    /**
     * 通过返回的title来判断是否已经是登陆
     *
     * @param str doc
     * @return isLogin
     */
    private static boolean isLogin(String str) {
        Document document = Jsoup.parse(str);
        if (document != null) {
            String title = document.title();
            return title.startsWith(AppApplication.getKString(R.string.web_admin_title));
        }
        return false;
    }

    private static void onRequest(int requestType, final String url, Map<String, String> params, final ICallBack callBack) {
        KStringRequest request = new KStringRequest(requestType, url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                L.v(TAG, "请求成功(URL:" + url + ")");
                if (callBack != null) {
                    callBack.onSuccess(ICallBack.RESPONSE_OK, s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                L.v(TAG, "请求失败(URL:" + url + ")");
                if (callBack != null) {
                    callBack.onError(ICallBack.RESPONSE_FAILED, volleyError.getMessage());
                }
            }
        });
        request.setShouldCache(true);
        AppApplication.startRequest(request);
    }
}
