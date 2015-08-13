package com.kutear.app.api;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kutear.app.AppApplication;
import com.kutear.app.netutils.KStringRequest;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class ApiUser {
    /**
     * @param user
     * @param password
     * @return boolean
     * <p/>
     * 请求格式
     * POST /index.php/action/login?_=7ab6643d63f58092e0199fdf86ec1353
     * Host: www.kutear.com
     * Content-Length: 88
     * Cache-Control: max-age=0
     * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,
     * ;q=0.8
     * Origin:http://www.kutear.com
     * Upgrade-Insecure-Requests:1
     * User-Agent:Mozilla/5.0(
     * Windows NT
     * 6.1;WOW64)AppleWebKit/537.36(KHTML,
     * like Gecko
     * )Chrome/44.0.2403.125Safari/537.36
     * Content-Type:application/x-www-form-urlencoded
     * Referer:http://www.kutear.com/admin/login.php?referer=http%3A%2F%2Fwww.kutear.com%2Fadmin%2F
     * Accept-Encoding:gzip,deflate
     * Accept-Language:zh-CN,zh;q=0.8
     * Cookie:
     * saeut=45.78.13.119.1436173538419585;
     * PHPSESSID=59a45cb2f8af1132b88af1a086abe2db
     * <p/>
     * name = admin & password = *******&referer=http%3A%2F%2Fwww.kutear.com%2Fadmin%2F
     */

    private static final String TAG = ApiUser.class.getSimpleName();


    public static void login(final String user, final String password, final ICallBack callBack) {
        //获取登录URL部分
        KStringRequest request = new KStringRequest(Constant.URI_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Document doc = Jsoup.parse(s);
                Elements forms = doc.getElementsByAttribute("action");
                if (forms.size() > 0) {
                    Element form = forms.get(0);
                    String loginUrl = form.attr("action");
                    L.v(TAG, "URI=" + loginUrl);
                    onLogin(loginUrl, user, password, callBack);
                } else {
                    if (callBack != null) {
                        callBack.onError("can't get Url");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBack != null) {
                    callBack.onError(volleyError.getMessage());
                }
            }
        });
        request.setShouldCache(false);
        AppApplication.startRequest(request);
    }

    /**
     * @author kutear.guo
     */
    private static void onLogin(final String url, final String user, final String password, final ICallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("name", user);
        params.put("password", password);
        params.put("referer", Constant.URI_HOST);

        KStringRequest request = new KStringRequest(url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                L.v(TAG,s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBack != null) {
                    callBack.onError(volleyError.getMessage());
                }
            }
        });
        request.setShouldCache(false);
        AppApplication.startRequest(request);
    }

    public static boolean logout() {
        return false;
    }
}
