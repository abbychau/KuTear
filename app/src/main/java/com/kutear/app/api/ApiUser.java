package com.kutear.app.api;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.UserInfo;
import com.kutear.app.manager.UserManager;
import com.kutear.app.netutils.KStringRequest;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;
import com.kutear.app.utils.SaveData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class ApiUser extends BaseRequest {
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

    public interface IUserInfo {
        void onSuccess(UserInfo user);

        void onError(String msg);
    }


    /**
     * App启动时登陆
     */
    public static void autoLogin(ICallBack callBack) {
        if (!TextUtils.isEmpty(SaveData.getString(Constant.USER)) &&
                !TextUtils.isEmpty(SaveData.getString(Constant.PASS))) {
            login(SaveData.getString(Constant.USER), SaveData.getString(Constant.PASS), callBack);
        }
    }


    public static void login(final String user, final String password, final ICallBack callBack) {
        getRequest(Constant.URI_ADMIN, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                getLoginUrl(str, callBack, user, password);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(statusCode, str);
                }
            }
        });
    }

    private static void getLoginUrl(String str, ICallBack callBack, String user, String password) {
        Document doc = Jsoup.parse(str);
        String title = doc.title();
        //表示已经登陆到后台
        if (title.startsWith(AppApplication.getKString(R.string.web_admin_title))) {
            if (callBack != null) {
                callBack.onSuccess(ICallBack.RESPONE_OK, AppApplication.getKString(R.string.login_succeed));
            }
            //根据URI来登录
        } else {
            Elements forms = doc.getElementsByAttribute(AppApplication.getKString(R.string.web_form_attribute));
            if (forms.size() > 0) {
                Element form = forms.get(0);
                String loginUrl = form.attr(AppApplication.getKString(R.string.web_form_attribute));
                onLogin(loginUrl, user, password, callBack);
            } else {
                if (callBack != null) {
                    callBack.onError(ICallBack.RESPONE_NO_URI, AppApplication.getKString(R.string.get_url_failed));
                }
            }
        }
    }

    private static void parseHtml(String str, ICallBack callBack) {
        // 这里会被重定向到首页 可以通过判断是不是首页来确定是否登陆
        Document doc = Jsoup.parse(str);
        String title = doc.title();
        if (TextUtils.equals(title, AppApplication.getKString(R.string.web_title))) {
            if (callBack != null) {
                callBack.onSuccess(ICallBack.RESPONE_OK, AppApplication.getKString(R.string.login_success));
            }
        } else if (title.startsWith(AppApplication.getKString(R.string.web_login_title))) {
            if (callBack != null) {
                callBack.onError(ICallBack.RESPONE_FAILED, AppApplication.getKString(R.string.username_or_password_is_error));
            }
        }
    }


    /**
     * @author kutear.guo
     */
    private static void onLogin(final String url, final String user, final String password, final ICallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("name", user);
        params.put("password", password);
        params.put("referer", Constant.URI_HOST);

        postRequest(url, params, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseHtml(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(statusCode, str);
                }
            }
        });
    }

    public static boolean logout() {
        return false;
    }

    /**
     * @param callBack 回调
     */
    public static void getUserInfo(final IUserInfo callBack) {
        getRequest(Constant.URI_USER_CENTER, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (!TextUtils.isEmpty(str)) {
                    parseUserInfo(str, callBack);
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(AppApplication.getKString(R.string.get_user_info_error));
                }
            }
        });
    }

    /**
     * class = profile-avatar 头像地址
     * id=screenName-0-1 昵称
     * id=url-0-2 主页
     * id= mail-0-3 邮箱
     *
     * @param str      html
     * @param callBack 回调
     */
    private static void parseUserInfo(String str, IUserInfo callBack) {
        UserInfo info = new UserInfo();
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByClass("profile-avatar");
        if (elements != null && elements.first() != null) {
            info.setAvater(elements.first().attr("src")); //头像URL
        }
        Element elementNick = document.getElementById("screenName-0-1");
        Element elementMainPager = document.getElementById("url-0-2");
        Element elementMail = document.getElementById("mail-0-3");
        if (elementNick != null) {
            info.setNickName(elementNick.attr("value"));
        }
        if (elementMainPager != null) {
            info.setMainPager(elementMainPager.attr("value"));
        }
        if (elementMail != null) {
            info.setEMail(elementMail.attr("value"));
        }
        if (callBack != null) {
            callBack.onSuccess(info);
        }
        //发送登录成功的广播
        AppApplication.startBroadcast(Constant.BROADCAST_LOGIN);
    }

    public static void checkUserLogin(final ICallBack callBack) {
        getRequest(Constant.URI_ADMIN, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document doc = Jsoup.parse(str);
                String title = doc.title();
                if (title.startsWith(AppApplication.getKString(R.string.web_login_title))) {
                    callBack.onError(NOT_LOGIN, AppApplication.getKString(R.string.not_login));
                } else if (title.startsWith(AppApplication.getKString(R.string.web_login_title))) {
                    callBack.onSuccess(HAS_LOGIN, "");
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                callBack.onError(RESPONE_FAILED, AppApplication.getKString(R.string.not_login));
            }
        });
    }
}
