package com.kutear.app.api;

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

    public static boolean login(String user, String password) {
        return false;
    }
}
