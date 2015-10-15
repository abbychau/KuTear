package com.kutear.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kutear.app.callback.OnBackPressed;
import com.kutear.app.swipebacklayout.SwipeBackActivity;
import com.kutear.app.swipebacklayout.SwipeBackLayout;
import com.kutear.app.utils.L;

import java.util.List;


public abstract class BaseActivity extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = getSwipeBackLayout();
        //允许左侧滑动返回
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof OnBackPressed) {
                ((OnBackPressed) fragment).onBackPressed();
                return;
            }
        }
        super.onBackPressed();
    }
}
