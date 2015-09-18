package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ManagerPagerAdapter;
import com.kutear.app.api.ApiPagerManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.Pager;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/25.
 * 独立页面试图
 */
public class ManagerPagerFragment extends BaseNoBarFragment implements IGetListCallBack, AdapterView.OnItemClickListener {
    private static final String TAG = ManagerPagerFragment.class.getSimpleName();
    private ListView mListView;
    private List<Pager> mLists;
    private BaseAdapter mAdapter;

    public static ManagerPagerFragment newInstance() {
        Bundle args = new Bundle();
        ManagerPagerFragment fragment = new ManagerPagerFragment();
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
        mLists = new ArrayList<>();
        mAdapter = new ManagerPagerAdapter(mLists, mActivity);
        mListView.setAdapter(mAdapter);
        ApiPagerManager.getPagerList(this);
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
        mLists.addAll((Collection<? extends Pager>) lists);
    }

    @Override
    public void onError(String msg) {
        showSnack(mListView, msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ManagerPagerEditFragment.KEY, mLists.get(position));
        bundle.putBoolean(ManagerPagerEditFragment.IS_MODIFY, true);
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_EDIT_PAGER, bundle);
    }
}
