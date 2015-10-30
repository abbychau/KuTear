package com.kutear.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.callback.OnBackPressed;
import com.kutear.app.swipebacklayout.SwipeBackActivity;
import com.kutear.app.swipebacklayout.SwipeBackLayout;
import com.kutear.app.utils.Constant;

import java.util.List;


public abstract class BaseActivity extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;
    protected AppApplication mApp;
    protected boolean isFullScreen;
    public static final String SCREEN_FLAG = "is_full_screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = getSwipeBackLayout();
        //允许左侧滑动返回
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mApp = (AppApplication) getApplication();
        getScreenFlag();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isFullScreen) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    protected int getSettingTheme() {
        if (isLightTheme()) {
            return R.style.AppTheme;
        }
        return R.style.AppTheme_Dark;
    }

    protected boolean isLightTheme() {
        if (mApp == null) {
            mApp = (AppApplication) getApplication();
        }
        SharedPreferences sp = mApp.getSettingPreference();
        String theme = sp.getString(Constant.THEME_PREFERENCE, "0");
        if (TextUtils.equals(theme, "0")) {
            return true;
        }
        return false;
    }

    private void getScreenFlag() {
        Intent intent = getIntent();
        if (intent != null) {
            isFullScreen = intent.getBooleanExtra(SCREEN_FLAG, false);
        }
    }

    /**
     * 获取系统的Application
     *
     * @return mApp
     */
    public AppApplication getAppApplication() {
        return mApp;
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
