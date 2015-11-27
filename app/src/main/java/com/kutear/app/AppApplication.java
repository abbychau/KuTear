package com.kutear.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kutear.app.activity.CommonActivity;
import com.kutear.app.cookies.SiCookieStore2;
import com.kutear.app.manager.CarouselManager;
import com.kutear.app.manager.UserManager;
import com.kutear.app.utils.Constant;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by kutear.guo on 2015/8/3.
 * Application
 */
public class AppApplication extends Application {
    private static AppApplication app;
    private static RequestQueue mQueue;
    private static CookieManager mCookieManager;
    private static ImageLoader mImageLoader;
    private static UserManager mUserManager;
    private static UploadManager mUploadManager;
    private static final String APP_PATH = "KuTear";
    private static SharedPreferences mSharedPreferences;
    private static CarouselManager carouselManager;

    public static AppApplication getApplication() {
        return app;
    }

    public static void startRequest(Request request) {
        mQueue.add(request);
    }

    /**
     * @param resId
     * @return
     * @see Application#getString
     */
    public static String getKString(int resId) {
        return app.getString(resId);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public String getAppPath() {
        File file = new File(Environment.getExternalStorageDirectory(), APP_PATH);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(getAppPath(), APP_PATH);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mCookieManager = new CookieManager(new SiCookieStore2(this), CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(mCookieManager);
        mQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        mUserManager = new UserManager();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        carouselManager = new CarouselManager();
        initUploadManager();
    }


    /**
     * 获取轮播图管理
     *
     * @return
     */
    public CarouselManager getCarouselManager() {
        return carouselManager;
    }

    /**
     * 初始化七牛上传组件   uploadManager
     */
    private void initUploadManager() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
                .recorder(null)  // recorder 分片上传时，已上传片记录器。默认 null
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        mUploadManager = new UploadManager(config);
    }

    public UploadManager getUploadManager() {
        return mUploadManager;
    }

    public void startActivity(Activity activity, int type, Bundle bundle) {
        Intent intent = new Intent(activity, CommonActivity.class);
        intent.putExtra(Constant.ACTIVITY_TYPE, type);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public SharedPreferences getSettingPreference() {
        return mSharedPreferences;
    }

    public UserManager getUserManager() {
        return mUserManager;
    }

    public static void startBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        app.sendBroadcast(intent);
    }
}
