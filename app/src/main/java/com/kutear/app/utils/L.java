package com.kutear.app.utils;

import android.util.Log;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class L {
    public static void v(String tag, String value) {
        if (Constant.isDeBug) {
            Log.v(tag, value);
        }
    }

    public static void e(String tag, String value) {
        if (Constant.isDeBug) {
            Log.e(tag, value);
        }
    }

    public static void d(String tag, String value) {
        if (Constant.isDeBug) {
            Log.d(tag, value);
        }
    }

    public static void i(String tag, String value) {
        if (Constant.isDeBug) {
            Log.i(tag, value);
        }
    }
}
