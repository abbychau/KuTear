package com.kutear.app.utils;

/**
 * Created by kutear.guo on 2015/8/4.
 *
 * 常量
 */
public class Constant {

    /*************************************
     * 模式
     *************************************/
    public static final boolean isDeBug = true;

    /*************************************
     * URL 相关
     *************************************/
    public static final String URI_HOST = "http://www.kutear.com";
    //登陆接口 post
    public static final String URI_ADMIN = URI_HOST + "/admin";
    //归档接口
    public static final String URI_ARCHIVE = URI_HOST + "/index.php/archive.html";
    //App接口
    public static final String URI_APP = URI_HOST + "/index.php/app.html";
    //关于接口
    public static final String URI_ABOUT = URI_HOST + "/index.php/about.html";
    //文章列表接口
    public static final String URI_ARTICLE = URI_HOST + "/index.php/page/%d/";
    //用户信息主页
    public static final String URI_USER_CENTER = URI_HOST + "/admin/profile.php";
    //基本设置
    public static final String URI_BASE_SETTING = URI_HOST + "/admin/options-general.php";

    /*************************************
     * 广播 相关
     *************************************/
    public static final String BROADCAST_LOGIN = "login_broadcast";

    /*************************************
     * 配置 相关
     *************************************/
    public static final String CACHE_DIR_NAME = "kutear";
    public static final String PREFERENCES_NAME = "kutear";
    public static final String USER = "user";
    public static final String PASS = "pass";
    public static final String COOKIES = "cookies";


    /**************************************
     * 根据Type加载Fragment到CommonActivity
     **************************************/
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final int ACTIVITY_LOGIN = 0x00001;
    public static final int ACTIVITY_DETAILS = 0x00002;
    public static final int ACTIVITY_ARCHIVE = 0x00003;
    public static final int ACTIVITY_PREVIEW = 0x00004;
    public static final int ACTIVITY_MANAGER = 0x00005;
    public static final int ACTIVITY_USER_CENTER = 0x00006;
    public static final int ACTIVITY_SETTING = 0x00007;
    public static final int ACTIVITY_ABOUT = 0x00008;
}
