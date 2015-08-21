package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kutear.guo on 2015/8/21.
 * 阅读设置页面
 */
public class ManagerReadFragment extends BaseFragment {
    public static ManagerReadFragment newInstance() {
        Bundle args = new Bundle();
        ManagerReadFragment fragment = new ManagerReadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View v) {

    }
}
