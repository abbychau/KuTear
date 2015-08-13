package com.kutear.app.netutils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class NetConfig {
    public static Map<String, String> getRequestHeader() {
        HashMap<String, String> map = new HashMap<>();
//        map.put("Cache-Control", " max-age=0");
//        map.put("Cache-Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        map.put("Upgrade-Insecure-Requests", "1");
//        map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
//        map.put("Content-Type", "application/x-www-form-urlencoded");
//        map.put("Referer", "http://www.kutear.com/admin/login.php?referer=http%3A%2F%2Fwww.kutear.com%2Fadmin%2F");
//        map.put("Accept-Encoding", "gzip, deflate");
//        map.put("Accept-Language", "zh-CN,zh;q=0.8");
        return map;
    }
}
