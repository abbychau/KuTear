package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ArticleAdapter;
import com.kutear.app.api.ApiArticleList;
import com.kutear.app.bean.Article;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.Category;
import com.kutear.app.bean.Tab;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.listener.EndlessRecyclerOnScrollListener;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/25.
 * 用于显示指定Tag或category下的文章
 */
public class ArticleListFragment extends BaseToolBarFragment implements IGetListCallBack, ArticleAdapter.OnItemClickListener {

    public static final String KEY = "key";
    private static final String TAG = ArticleListFragment.class.getSimpleName();
    private Tab mTab;
    private Category mCategory;
    private RecyclerView mRecycleView;
    private ArticleAdapter mAdapter;
    private int pager = 1;
    private boolean loading = true;
    private List<Article> mLists = new ArrayList<>();

    public static ArticleListFragment newInstance(Bundle args) {
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_article);
        getData();
        return view;
    }

    private void getData() {
        BaseBean bean = getArguments().getParcelable(KEY);
        if (bean != null) {
            if (bean instanceof Tab) {
                mTab = (Tab) bean;
                ApiArticleList.getArticle(mTab.getUrl(), pager, this);
            }
            if (bean instanceof Category) {
                mCategory = (Category) bean;
                ApiArticleList.getArticle(mCategory.getUrl(), pager, this);
            }
        }
    }

    @Override
    protected void initView(View v) {
        mRecycleView = (RecyclerView) v.findViewById(R.id.recycle_layout);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(manager);
        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                if (loading) {
                    loading = false;
                    if (mCategory != null) {
                        ApiArticleList.getArticle(mCategory.getUrl(), ++pager, ArticleListFragment.this);
                    } else if (mTab != null) {
                        ApiArticleList.getArticle(mTab.getUrl(), ++pager, ArticleListFragment.this);
                    }
                }
            }
        });
        if (mTab != null) {
            setTitle(mTab.getName());
        } else if (mCategory != null) {
            setTitle(mCategory.getName());
        }
        mAdapter = new ArticleAdapter(mLists, this);
        mRecycleView.setAdapter(mAdapter);
    }


    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        ArrayList<Article> list = (ArrayList<Article>) lists;
        list.remove(0);
        loading = true;
        if (getNotRepeatList(list).size() == 0) {
            onError(mActivity.getString(R.string.no_more_data));
            return;
        }
        mLists.addAll(getNotRepeatList(list));
        mAdapter.notifyDataSetChanged();
        hiddenLoadingLayout();
    }

    @Override
    public void onError(String msg) {
        hiddenLoadingLayout();
        loading = true;
        pager--;
        if (!TextUtils.isEmpty(msg)) {
            Snackbar.make(mRecycleView, msg, Snackbar.LENGTH_SHORT).show();
        }
    }


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

    @Override
    public void onClick(View v, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ArticleDetailsFragment.KEY, mLists.get(position));
        mApp.startActivity(mActivity, Constant.ACTIVITY_DETAILS, bundle);
    }
}
