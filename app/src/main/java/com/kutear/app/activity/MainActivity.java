package com.kutear.app.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.netutils.KStringRequest;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;


/**
 * Created by kutear.guo on 2015/8/4.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolBar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        testNet();
    }

    private void testNet() {
        KStringRequest request = new KStringRequest(Constant.URI_HOST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.v(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppApplication.startRequest(request);
    }


    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar();
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        initNavigationView();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int type = 0;
        Bundle bundle = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_test:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                type = Constant.ACTIVITY_DETAILS;
                break;
            default:
                break;
        }
        AppApplication.startActivity(this, type, bundle);
        return false;
    }
}
