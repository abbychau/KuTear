package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.R;
import com.kutear.app.adapter.ManagerAdapter;

/**
 * Created by kutear.guo on 2015/8/19.
 * 后台管理页面
 * <p/>
 * 1.站点管理 /admin/options-general.php
 * 2.阅读设置 /admin/options-reading.php
 * 3.永久链接设置 /admin/options-permalink.php
 * 4.管理分类 /admin/manage-categories.php
 * 5.管理独立页面 /admin/manage-pages.php
 * 6.管理文章 /admin/manage-posts.php
 */
public class ManageFragment extends BaseFragment {
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ManagerAdapter mAdapter;

    public static ManageFragment newInstance() {
        Bundle args = new Bundle();
        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        initView(view);
        bindData();
        initTab();
        return view;
    }

    @Override
    protected void initView(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mActivity.setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.setTitle(R.string.menu_string_manager);
    }

    private void initTab() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void bindData() {
        mAdapter = new ManagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
