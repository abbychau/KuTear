package com.kutear.app.utils;

/**
 * Created by kutear.guo on 2015/8/4.
 * <p/>
 * 常量
 */
public class Constant {

    /*************************************
     * 模式
     *************************************/
    public static final boolean isDeBug = false;

    /*************************************
     * URL 相关
     *************************************/
    public static final String URI_HOST = "http://kutearforte.sinaapp.com";
    //登陆接口 post
    public static final String URI_ADMIN = URI_HOST + "/admin";
    //归档接口
    public static final String URI_ARCHIVE = URI_HOST + "/index.php/archive.html";
    //App接口
    public static final String URI_APP = URI_HOST + "/index.php/app.html";
    //关于接口
    public static final String URI_ABOUT = URI_HOST + "/index.php/about.html";
    //文章列表接口
    public static final String URI_ARTICLE = URI_HOST + "/index.php/page/";
    //用户信息主页
    public static final String URI_USER_CENTER = URI_HOST + "/admin/profile.php";
    //基本设置
    public static final String URI_BASE_SETTING = URI_HOST + "/admin/options-general.php";
    //阅读设置
    public static final String URI_READ_SETTING = URI_HOST + "/admin/options-reading.php";
    //链接设置
    public static final String URI_LINKS_SETTING = URI_HOST + "/admin/options-permalink.php";
    //分类管理
    public static final String URI_CATEGORY_MANAGER = URI_HOST + "/admin/manage-categories.php";
    //分类编辑
    public static final String URI_CATEGORY_EDIT = URI_HOST + "/admin/category.php?mid=%d";
    //独立页面管理
    public static final String URI_PAGER_MANAGER = URI_HOST + "/admin/manage-pages.php";
    //文章管理页面
    public static final String URI_ARTICLE_MANAGER = URI_HOST + "/admin/manage-posts.php?page=%d";
    //文章详情页面
    public static final String URI_ARTICLE_DETAILS = URI_HOST + "/admin/write-post.php?cid=%d";
    //发布文章URL
    public static final String URI_ARTICLE_POST = URI_HOST + "/admin/write-post.php";
    //独立页面URL
    public static final String URI_PAGER_DETAILS = URI_HOST + "/admin/write-page.php";
    //轮播图URL
    public static final String URI_CAROUSEL = "http://appkutear.sinaapp.com/index.php/Home/Index/index";



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
    public static final String THEME_PREFERENCE = "theme_preference";


    /**************************************
     * 根据Type加载Fragment到CommonActivity
     **************************************/
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final int ACTIVITY_LOGIN = 0x00001;
    public static final int ACTIVITY_DETAILS = 0x00002;
    public static final int ACTIVITY_ARCHIVE = 0x00003;
    public static final int ACTIVITY_PREVIEW_ARTICLE = 0x0004;
    public static final int ACTIVITY_MANAGER = 0x00005;
    public static final int ACTIVITY_USER_CENTER = 0x00006;
    public static final int ACTIVITY_SETTING = 0x00007;
    public static final int ACTIVITY_ABOUT = 0x00008;
    public static final int ACTIVITY_ARTICLE_LIST = 0x00009;
    public static final int ACTIVITY_CATEGORY_MD = 0x00010;
    public static final int ACTIVITY_EDIT_ARTICLE = 0x00011;
    public static final int ACTIVITY_EDIT_PAGER = 0x00012;
    public static final int ACTIVITY_WEB_VIEW = 0x00013;
    public static final int ACTIVITY_LEADER_PAGER = 0x00014;
    public static final int ACTIVITY_IMAGE_PREVIEW = 0x00015;

}
