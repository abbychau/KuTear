package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

import com.kutear.app.R;
import com.kutear.app.upload.QiniuUpload;

import java.io.File;

/**
 * Created by kutear.guo on 2015/8/18.
 * 设置页面
 */
public class SettingFragment extends BaseToolBarFragment {
    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        initView(null);
        hiddenLoadingLayout();
        QiniuUpload.upload(new File("/storage/emulated/0/Download/IMG_20151010_114340.jpg"),null);
        return null;
    }

    @Override
    protected void initView(View v) {
        setTitle(R.string.menu_string_setting);
    }
}
