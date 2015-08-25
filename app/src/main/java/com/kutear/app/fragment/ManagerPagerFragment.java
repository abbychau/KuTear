package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by kutear.guo on 2015/8/25.
 */
public class ManagerPagerFragment extends BaseNoBarFragment {

    public static ManagerPagerFragment newInstance() {
        Bundle args = new Bundle();
        ManagerPagerFragment fragment = new ManagerPagerFragment();
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
