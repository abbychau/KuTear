package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ArchiveAdapter;
import com.kutear.app.adapter.ArticleAdapter;
import com.kutear.app.api.ApiArchive;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/18.
 * 归档页面
 */
public class ArchiveFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, ArticleAdapter.OnItemClickListener, IGetListCallBack {
    private static final String TAG = ArchiveFragment.class.getSimpleName();
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private int type = ARCHIVE;
    private RecyclerView mRecycleView;
    private ArchiveAdapter mAdapter;
    private List<BaseBean> mlist = new ArrayList<>();
    public static final String KEY = "type";
    public static final int TAB = 0x002;
    public static final int ARCHIVE = 0x000;
    public static final int CATEGORY = 0x001;


    public static ArchiveFragment newInstance(Bundle args) {
        ArchiveFragment fragment = new ArchiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_archive, container, false);
        initView(mBodyView);
        initTabs();
        initData();
        return mBodyView;
    }

    private void initTabs() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.menu_string_archive));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.menu_string_category));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.menu_string_tab));
        mTabLayout.setOnTabSelectedListener(this);
    }

    protected void initView(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.setTitle(R.string.menu_string_archive);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mRecycleView = (RecyclerView) v.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//表示两列，并且是竖直方向的瀑布流
        mRecycleView.setLayoutManager(mStaggeredGridLayoutManager);
    }

    private void initData() {
        selectTabs();
    }

    private void selectTabs() {
        Bundle bundle = getArguments();
        type = bundle != null ? bundle.getInt(KEY) : ARCHIVE;
        mTabLayout.setScrollPosition(type, 0, true);
        doRequest();
        mAdapter = new ArchiveAdapter(mlist, ArchiveFragment.this);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        type = tab.getPosition();
        doRequest();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void doRequest() {
        //根据Tab选择不同的请求
        showLoading("");
        switch (type) {
            case TAB:
                requestTab();
                break;
            case ARCHIVE:
                requestArchive();
                break;
            case CATEGORY:
                requestCategory();
                break;
            default:
                break;
        }
    }

    private void requestTab() {
        ApiArchive.getTabs(this);
    }

    private void requestArchive() {
        ApiArchive.getArchive(this);
    }

    private void requestCategory() {
        ApiArchive.getCategory(this);
    }

    @Override
    public void onClick(View v, int position) {
        switch (type) {
            case TAB:
                onItemClickTab(v, position);
                break;
            case ARCHIVE:
                onItemClickArchive(v, position);
                break;
            case CATEGORY:
                onItemClickCategory(v, position);
                break;
            default:
                break;
        }
    }

    private void onItemClickArchive(View v, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.KEY, mlist.get(position));
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_DETAILS, bundle);
    }

    private void onItemClickTab(View v, int position) {

    }

    private void onItemClickCategory(View v, int position) {

    }


    private void resetData(List<? extends BaseBean> lists) {
        mlist.clear();
        mlist.addAll(lists);
        mAdapter.notifyDataSetChanged();
        hiddenLoad();
    }

    private void showError(String msg) {
        mlist.clear();
        mAdapter.notifyDataSetChanged();
        showSnack(mRecycleView, msg);
        hiddenLoad();
    }

    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        resetData(lists);
    }

    @Override
    public void onError(String msg) {
        showError(msg);
    }
}
