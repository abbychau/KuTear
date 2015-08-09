package com.kutear.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kutear.app.activity.CommonActivity;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear.guo on 2015/8/3.
 */
public class AppApplication extends Application {
    private static AppApplication app;
    private static RequestQueue mQueue;

    public static AppApplication getApplication() {
        return app;
    }

    public static void startRequest(Request request) {
        mQueue.add(request);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mQueue = Volley.newRequestQueue(this);
    }


    public static void startActivity(Activity activity, int type, Bundle bundle) {
        Intent intent = new Intent(activity, CommonActivity.class);
        intent.putExtra(Constant.ACTIVITY_TYPE, type);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
//        activity.overridePendingTransition();
    }

}
