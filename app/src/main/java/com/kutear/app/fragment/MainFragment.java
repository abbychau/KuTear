package com.kutear.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ArticleAdapter;
import com.kutear.app.api.ApiArticleList;
import com.kutear.app.bean.Article;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements ApiArticleList.IArticleList, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<Article> mLists = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int pager = 0;
    private Activity mActivity;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(mBodyView);
        bindData();
        return mBodyView;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(mActivity.getResources().getColor(R.color.teal_500));
    }

    private void bindData() {
        mAdapter = new ArticleAdapter(mLists, this);
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }

    private void requestData() {
        ApiArticleList.getArticle(1, this);
    }


    @Override
    public void onSuccess(List<Article> list) {
        if (pager == 2) {
            addListToHeader(list);
        } else {
            addListToFooter(list);
        }
        mAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 下拉刷新时
     *
     * @param list article 列表
     */
    private void addListToHeader(List<Article> list) {
        if (getNotRepeatList(list).size() == 0) {
            Snackbar.make(mSwipeRefreshLayout, "没有新数据啦", Snackbar.LENGTH_SHORT).show();
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
            Snackbar.make(mSwipeRefreshLayout, "没有更多数据啦", Snackbar.LENGTH_SHORT).show();
            return;
        }
        mLists.addAll(getNotRepeatList(list));
    }

    private List<Article> getNotRepeatList(List<Article> list) {
        if (mLists.size() == 0) {
            return list;
        }
        List<Article> tempList = new ArrayList<>();
        for (Article article : list) {
            for (Article article2 : mLists) {
                if (!article.equals(article2)) {
                    tempList.add(article);
                }
            }
        }
        return tempList;
    }


    @Override
    public void onFailed(String str) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (!TextUtils.isEmpty(str)) {
            Snackbar.make(mSwipeRefreshLayout, str, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        ApiArticleList.getArticle(++pager, this);
    }

    @Override
    public void onClick(View v, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.KEY, mLists.get(position));
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_DETAILS, bundle);
    }
}
