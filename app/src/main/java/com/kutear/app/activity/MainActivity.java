package com.kutear.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.fragment.ArchiveFragment;
import com.kutear.app.fragment.MainFragment;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;
import com.kutear.app.view.CircleImageView;


/**
 * Created by kutear.guo on 2015/8/4.
 * 首页视图
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolBar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView mTvNickName;
    private CircleImageView mAvater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getSettingTheme() > 0) {
            setTheme(getSettingTheme());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        createBroadCast();
        mSwipeBackLayout = getSwipeBackLayout();
        //禁用滑动
        mSwipeBackLayout.setEnableGesture(false);
    }

    @Override
    protected int getSettingTheme() {
        if (isLightTheme()) {
            return R.style.AppTheme_Main;
        }
        return R.style.AppTheme_Main_Dark;
    }

    /**
     * 注册登录监听广播
     */
    private void createBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_LOGIN);
        registerReceiver(loginBroadcastReceiver, filter);
    }

    @Override
    protected void onResume() {
        Menu menu = mNavigationView.getMenu();
        if (menu != null) {
            menu.getItem(0).setCheckable(true);
        }
        super.onResume();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTvNickName = (TextView) findViewById(R.id.nav_name);
        mAvater = (CircleImageView) findViewById(R.id.nav_avatar);
        initToolBar();
        initNavigationView();
        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, MainFragment.newInstance()).commit();
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        View headerView = mNavigationView.inflateHeaderView(R.layout.nav_header);
        headerView.setOnClickListener(this);
        if (mApp.getUserManager().getUserInfo() != null) {
            if (mAvater == null) {
                mAvater = (CircleImageView) findViewById(R.id.nav_avatar);
            }
            if (mTvNickName == null) {
                mTvNickName = (TextView) findViewById(R.id.nav_name);
            }
            if (mApp.getUserManager().getUserInfo().getAvater() != null) {
                mAvater.setImageURI(Uri.parse(mApp.getUserManager().getUserInfo().getAvater()));
            }
            if (mApp.getUserManager().getUserInfo().getNickName() != null) {
                mTvNickName.setText(mApp.getUserManager().getUserInfo().getNickName());

            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        menuItem.setChecked(true);

        if (menuItem.getItemId() == R.id.menu_main) {
            return true;
        }
        int type = 0;
        Bundle bundle = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_archive:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.ARCHIVE);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            case R.id.menu_manager:
                type = Constant.ACTIVITY_MANAGER;
                break;
            case R.id.menu_setting:
                type = Constant.ACTIVITY_SETTING;
                break;
            case R.id.menu_about:
                type = Constant.ACTIVITY_ABOUT;
                break;
            case R.id.menu_tab:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.TAB);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            case R.id.menu_category:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.CATEGORY);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            default:
                break;
        }
        mApp.startActivity(this, type, bundle);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (mApp.getUserManager().isLogin()) {
            mApp.startActivity(this, Constant.ACTIVITY_USER_CENTER, null);
        } else {
            mApp.startActivity(this, Constant.ACTIVITY_LOGIN, null);
        }
        mDrawerLayout.closeDrawers();
    }

    private BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.BROADCAST_LOGIN) && mApp.getUserManager().getUserInfo() != null) {
                L.v(TAG,"登陆成功...");
                if (mAvater == null) {
                    mAvater = (CircleImageView) findViewById(R.id.nav_avatar);
                }
                if (mTvNickName == null) {
                    mTvNickName = (TextView) findViewById(R.id.nav_name);
                }
                if (mAvater != null && mTvNickName != null) {
                    loadImage(mApp.getUserManager().getUserInfo().getAvater());
                    mTvNickName.setText(mApp.getUserManager().getUserInfo().getNickName());
                }
            }
        }
    };

    /**
     * 加载头像
     *
     * @param url 头像URL
     */
    private void loadImage(String url) {
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap arg0) {
                mAvater.setImageBitmap(arg0);
            }
        }, 300, 200, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                mAvater.setImageResource(R.drawable.kutear_avatar);
            }
        });
        AppApplication.startRequest(imgRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(loginBroadcastReceiver);
    }
}
