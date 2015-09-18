package com.kutear.app.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends BaseToolBarFragment {

    private static final String KEY = "key";

    public static WebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        WebViewFragment fragment = new WebViewFragment();
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
