package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ManagerArticleAdapter;
import com.kutear.app.api.ApiArticleManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerArticle;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/25.
 * 文章管理页面
 */
public class ManagerArticleFragment extends BaseNoBarFragment implements IGetListCallBack, AdapterView.OnItemClickListener {

    private static final String TAG = ManagerArticleFragment.class.getSimpleName();
    private List<ManagerArticle> mLists = new ArrayList<>();
    private BaseAdapter mAdapter;
    private ListView mListView;

    public static ManagerArticleFragment newInstance() {
        Bundle args = new Bundle();
        ManagerArticleFragment fragment = new ManagerArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_manager_pager);
        initView(view);
        doRequest();
        return view;
    }

    private void doRequest() {
        mAdapter = new ManagerArticleAdapter(mLists, mActivity);
        mListView.setAdapter(mAdapter);
        ApiArticleManager.getArticleList(1, this);
    }

    @Override
    protected void initView(View v) {
        mListView = (ListView) v.findViewById(R.id.manager_pager_list);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        hiddenLoadingLayout();
        mLists.clear();
        mLists.addAll((Collection<? extends ManagerArticle>) lists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ManagerArticleEditFragment.IS_MODIFY, true);
        bundle.putString(ManagerArticleEditFragment.KEY, String.format(Constant.URI_ARTICLE_DETAILS, mLists.get(position).getIndex()));
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_EDIT_ARTICLE, bundle);
    }
}
