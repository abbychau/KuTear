package com.kutear.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.fragment.ArchiveFragment;
import com.kutear.app.fragment.MainFragment;
import com.kutear.app.utils.Constant;
import com.kutear.app.view.CircleImageView;


/**
 * Created by kutear.guo on 2015/8/4.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolBar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView mTvNickName;
    private CircleImageView mAvater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        createBroadCast();
    }

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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
            mDrawerLayout.closeDrawers();
            return false;
        }
        if (menuItem.getItemId() == R.id.menu_main) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return false;
        }
        int type = 0;
        Bundle bundle = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_archive:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.ARCHIVE);
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            case R.id.menu_manager:
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_MANAGER;
                break;
            case R.id.menu_setting:
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_SETTING;
                break;
            case R.id.menu_about:
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_ABOUT;
                break;
            case R.id.menu_tab:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.TAB);
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            case R.id.menu_category:
                bundle = new Bundle();
                bundle.putInt(ArchiveFragment.KEY, ArchiveFragment.CATEGORY);
                menuItem.setChecked(true);
                type = Constant.ACTIVITY_ARCHIVE;
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
        AppApplication.startActivity(this, type, bundle);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (AppApplication.getUserManager().isLogin()) {
            AppApplication.startActivity(this, Constant.ACTIVITY_USER_CENTER, null);
        } else {
            AppApplication.startActivity(this, Constant.ACTIVITY_LOGIN, null);
        }
        mDrawerLayout.closeDrawers();
    }

    private BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.BROADCAST_LOGIN)) {
                mAvater.setImageURI(Uri.parse(AppApplication.getUserManager().getUserInfo().getAvater()));
                mTvNickName.setText(AppApplication.getUserManager().getUserInfo().getNickName());
            }
        }
    };
}
