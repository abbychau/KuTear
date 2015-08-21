package com.kutear.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.fragment.AboutFragment;
import com.kutear.app.fragment.ManagerSiteFragment;

/**
 * Created by kutear.guo on 2015/8/21.
 * 管理页面适配器
 */
public class ManagerAdapter extends FragmentPagerAdapter {
    private String title[] = AppApplication.getApplication().getResources().getStringArray(R.array.manager_title);

    public ManagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ManagerSiteFragment.newInstance();
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
