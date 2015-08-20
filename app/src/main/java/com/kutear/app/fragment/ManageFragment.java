package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kutear.guo on 2015/8/19.
 * 后台管理页面
 */
public class ManageFragment extends BaseFragment {

    public static ManageFragment newInstance() {
        Bundle args = new Bundle();
        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
