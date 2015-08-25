package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiLinkManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.Link;

/**
 * Created by kutear.guo on 2015/8/24.
 * 固定连接管理
 */
public class ManagerLinkFragment extends BaseNoBarFragment implements View.OnClickListener {

    private TextView mTvPager;
    private TextView mTvCategory;
    private Link links;

    public static ManagerLinkFragment newInstance() {
        Bundle args = new Bundle();
        ManagerLinkFragment fragment = new ManagerLinkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {
        mTvPager = (TextView) v.findViewById(R.id.manager_link_pager);
        mTvCategory = (TextView) v.findViewById(R.id.manager_link_category);
        v.findViewById(R.id.manager_link_post).setOnClickListener(this);
    }


    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_manager_link);
        initView(view);
        doRequest();
        return view;
    }

    private void doRequest() {
        ApiLinkManager.getLink(this);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
        showSnack(mTvCategory, error);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        links = (Link) result;
        mTvCategory.setText(links.getCategoryPath());
        mTvPager.setText(links.getPagerPath());
    }

    @Override
    public void onClick(View v) {
        links.setCategoryPath(mTvCategory.getText().toString());
        links.setPagerPath(mTvPager.getText().toString());
        ApiLinkManager.postLink(links, this);
    }

    @Override
    public void onPostError(String msg) {
        super.onPostError(msg);
    }

    @Override
    public void onPostSuccess(String msg) {
        super.onPostSuccess(msg);
    }
}
