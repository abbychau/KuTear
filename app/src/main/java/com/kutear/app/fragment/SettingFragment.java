package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by kutear.guo on 2015/8/18.
 */
public class SettingFragment extends BaseFragment {
    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {

    }
}
