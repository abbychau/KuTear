package com.kutear.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kutear.app.activity.CommonActivity;
import com.kutear.app.manager.UserManager;
import com.kutear.app.utils.Constant;
import com.squareup.leakcanary.LeakCanary;

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

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        LeakCanary.install(this);
        mCookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
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
    }

    public static void startActivity(Activity activity, int type, Bundle bundle) {
        Intent intent = new Intent(activity, CommonActivity.class);
        intent.putExtra(Constant.ACTIVITY_TYPE, type);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public static UserManager getUserManager() {
        return mUserManager;
    }

    public static void startBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        app.sendBroadcast(intent);
    }
}
