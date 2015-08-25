package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kutear.app.R;
import com.kutear.app.adapter.ManagerCategoryAdapter;
import com.kutear.app.api.ApiCategoryManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerCategory;
import com.kutear.app.callback.IGetListCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/24.
 * 分类管理页面
 */
public class ManagerCategoryFragment extends BaseNoBarFragment implements IGetListCallBack, AdapterView.OnItemClickListener {
    private static final String TAG = ManagerCategoryFragment.class.getSimpleName();
    private ListView mLvLists;
    private ManagerCategoryAdapter mAdapter;
    private List<ManagerCategory> mLists = new ArrayList<>();

    public static ManagerCategoryFragment newInstance() {
        Bundle args = new Bundle();
        ManagerCategoryFragment fragment = new ManagerCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {
        mLvLists = (ListView) v.findViewById(R.id.manager_category_list);
        ApiCategoryManager.getCategory(this);
        mAdapter = new ManagerCategoryAdapter(mLists, mActivity);
        mLvLists.setAdapter(mAdapter);
        mLvLists.setOnItemClickListener(this);
    }


    @Override
    protected View setContentView() {
        View v = inflate(R.layout.fragment_manager_category);
        initView(v);
        return v;
    }

    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        mLists.clear();
        mLists.addAll((ArrayList<ManagerCategory>) lists);
        mAdapter.notifyDataSetChanged();
        hiddenLoadingLayout();
    }

    @Override
    public void onError(String msg) {
        showSnack(mLvLists, msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
