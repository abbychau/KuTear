package com.kutear.app.activity;

import android.os.Bundle;

import com.kutear.app.swipebacklayout.SwipeBackActivity;
import com.kutear.app.swipebacklayout.SwipeBackLayout;


public abstract class BaseActivity extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = getSwipeBackLayout();
        //允许左侧滑动返回
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

//    /**
//     * 默认是允许的，但是在viewhelp中可能存在冲突
//     */
//    public void disableSwipeBackLayout() {
//        mSwipeBackLayout.setEnableGesture(false);
//    }
//
//    public void enableSwipeBackLayout() {
//        mSwipeBackLayout.setEnableGesture(true);
//    }
}
