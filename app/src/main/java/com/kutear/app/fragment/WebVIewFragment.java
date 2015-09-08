package com.kutear.app.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebVIewFragment extends BaseToolBarFragment {

    private static final String KEY = "key";

    public static WebVIewFragment newInstance(String url) {
        Bundle args = new Bundle();
        WebVIewFragment fragment = new WebVIewFragment();
        args.putString(KEY, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected View setContentView() {
        return null;
    }


}
