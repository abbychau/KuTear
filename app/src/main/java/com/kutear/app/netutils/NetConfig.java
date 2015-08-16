package com.kutear.app.netutils;

import com.kutear.app.utils.Constant;
import com.kutear.app.utils.SaveData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class NetConfig {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String TAG = NetConfig.class.getSimpleName();

    public static Map<String, String> getRequestHeader() {
        HashMap<String, String> map = new HashMap<>();
        String cookies = SaveData.getString(Constant.COOKIES);
//        L.v(TAG, "local Cookies: " + cookies);
//        if (!TextUtils.isEmpty(cookies)) {
//            String[] cookie = cookies.split(";");
//            for (String item : cookie) {
//                String[] itemCookie = item.split("=");
//                if (itemCookie.length > 1) {
//                    map.put(itemCookie[0], itemCookie[1]);
//                }
//            }
//        }
        map.put(SET_COOKIE_KEY, cookies);
        return map;
    }

    public static void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
//                L.v(TAG, "Save Cookies: " + cookie);
                SaveData.saveString(Constant.COOKIES, cookie);
            }
        }
    }
}
