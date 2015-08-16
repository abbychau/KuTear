package com.kutear.app.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.utils.Constant;


/**
 * Created by kutear.guo on 2015/8/4.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolBar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolBar();
        initNavigationView();
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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
            return false;
        }
        int type = 0;
        Bundle bundle = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_archive:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_DETAILS;
                break;
            case R.id.menu_setting:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_LOGIN;
                break;
            case R.id.menu_about:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_DETAILS;
                break;
            case R.id.menu_tab:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_LOGIN;
                break;
            case R.id.menu_category:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_LOGIN;
                break;
            default:
                break;
        }
        AppApplication.startActivity(this, type, bundle);
        return true;
    }
}
