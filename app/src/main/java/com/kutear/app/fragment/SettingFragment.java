package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

import com.kutear.app.R;

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
        return null;
    }

    @Override
    protected void initView(View v) {
       mActivity.setTitle(R.string.menu_string_setting);
    }
}
