package com.kutear.app.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.activity.CommonActivity;
import com.kutear.app.adapter.ArticleAdapter;
import com.kutear.app.api.ApiArticleList;
import com.kutear.app.bean.Article;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.listener.EndlessRecyclerOnScrollListener;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseNoBarFragment implements SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.OnItemClickListener, IGetListCallBack {

    private RecyclerView mRecyclerView;
    private List<Article> mLists = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int pager = 0;
    private boolean isRefresh;
    private boolean loading = true;
    private EndlessRecyclerOnScrollListener loadMoreListener;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View setContentView() {
        return inflate(R.layout.fragment_main);
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_layout);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(mActivity.getResources().getColor(R.color.teal_500));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        loadMoreListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                if (loading) {
                    loading = false;
                    ApiArticleList.getArticle(++pager, MainFragment.this);
                }
            }
        };
        mRecyclerView.addOnScrollListener(loadMoreListener);
        bindData();
    }

    private void bindData() {
        mAdapter = new ArticleAdapter(mLists, this);
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }

    private void requestData() {
        ApiArticleList.getArticle(++pager, this);
    }

    /**
     * 下拉刷新时
     *
     * @param list article 列表
     */
    private void addListToHeader(List<Article> list) {
        if (getNotRepeatList(list).size() == 0) {
            Snackbar.make(mSwipeRefreshLayout, R.string.no_more_data, Snackbar.LENGTH_SHORT).show();
            return;
        }
        mLists.addAll(0, getNotRepeatList(list));
    }

    /**
     * 上拉加载时使用
     *
     * @param list article 列表
     */
    private void addListToFooter(List<Article> list) {
        if (getNotRepeatList(list).size() == 0) {
            Snackbar.make(mSwipeRefreshLayout, R.string.no_more_data, Snackbar.LENGTH_SHORT).show();
            return;
        }
        mLists.addAll(getNotRepeatList(list));
    }

    /**
     * 获取不重复的部分
     *
     * @param list
     * @return
     */
    private List<Article> getNotRepeatList(List<Article> list) {
        if (mLists.size() == 0) {
            return list;
        }
        List<Article> tempList = new ArrayList<>();
        for (Article article : list) {
            boolean without = true;
            for (Article article2 : mLists) {
                if (article.equals(article2)) {
                    without = false;
                    break;
                }
            }
            if (without) {
                tempList.add(article);
            }
        }
        return tempList;
    }


    /**
     * 获取首页内容
     */
    @Override
    public void onRefresh() {
        isRefresh = true;
        ApiArticleList.getArticle(1, this);
    }

    @Override
    public void onClick(View v, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ArticleDetailsFragment.KEY, mLists.get(position));
        bundle.putBoolean(BaseActivity.SCREEN_FLAG, true);
        mApp.startActivity(mActivity, Constant.ACTIVITY_DETAILS, bundle);
    }

    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        ArrayList<Article> list = (ArrayList<Article>) lists;
        loading = true;
        if (isRefresh) {
            isRefresh = false;
            addListToHeader(list);
        } else {
            addListToFooter(list);
        }
        mAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hiddenLoadingLayout();
    }

    @Override
    public void onError(String msg) {
        hiddenLoadingLayout();
        if(!msg.endsWith(getString(R.string.no_more_article))){
            showErrorLayout();
        }
        loading = true;
        if (!isRefresh) {
            pager--;
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (!TextUtils.isEmpty(msg)) {
            showSnack(mSwipeRefreshLayout, msg);
        }
    }
}
