package com.kutear.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kutear.app.AppApplication;

/**
 * Created by kutear.guo on 2015/8/4.
 *
 **/
public class SaveData {
    private static AppApplication app = AppApplication.getApplication();
    private static SharedPreferences mPreferences = app.getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE);

    public static boolean saveString(String key, String value) {
        return mPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return mPreferences.getString(key, "");
    }

}
