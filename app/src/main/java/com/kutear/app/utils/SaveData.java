package com.kutear.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.kutear.app.AppApplication;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by kutear.guo on 2015/8/4.
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


    /**
     * 图片保存
     *
     * @param bmp      BitMap
     * @param fileName
     * @return 保存后的绝对路径
     */
    public static String saveImage(Bitmap bmp, String fileName) {
        try {
            File tempFile = new File(app.getAppPath(), fileName);
            File parentFile = tempFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            FileOutputStream baos = new FileOutputStream(tempFile);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);//png类型
            baos.flush();
            baos.close();
            return tempFile.getAbsolutePath();
        } catch (Exception e) {

        }
        return null;
    }

}
