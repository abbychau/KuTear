package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by kutear.guo on 2015/8/25.
 */
public class ManagerArticleFragment extends BaseNoBarFragment {

    public static ManagerArticleFragment newInstance() {
        Bundle args = new Bundle();
        ManagerArticleFragment fragment = new ManagerArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return null;
    }

    @Override
    protected void initView(View v) {

    }
}
