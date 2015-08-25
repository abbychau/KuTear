package com.kutear.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.fragment.AboutFragment;
import com.kutear.app.fragment.BaseNoBarFragment;
import com.kutear.app.fragment.ManagerCategoryFragment;
import com.kutear.app.fragment.ManagerLinkFragment;
import com.kutear.app.fragment.ManagerReadFragment;
import com.kutear.app.fragment.ManagerSiteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/21.
 * 管理页面适配器
 */
public class ManagerAdapter extends FragmentPagerAdapter {
    private String title[] = AppApplication.getApplication().getResources().getStringArray(R.array.manager_title);
    private List<BaseNoBarFragment> fragments = new ArrayList<>();

    public ManagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(ManagerSiteFragment.newInstance());
        fragments.add(ManagerReadFragment.newInstance());
        //fragments.add(ManagerLinkFragment.newInstance());
        //fragments.add(ManagerCategoryFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return position < fragments.size() ? fragments.get(position) : AboutFragment.newInstance();
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
