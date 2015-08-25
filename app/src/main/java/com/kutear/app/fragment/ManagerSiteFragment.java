package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiSiteManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.SiteInfo;

/**
 * Created by kutear.guo on 2015/8/21.
 * 站点管理页面
 **/
public class ManagerSiteFragment extends BaseNoBarFragment implements View.OnClickListener {
    private Button mBtnPost;
    private TextView mTvSiteName;
    private TextView mTvSiteAddress;
    private TextView mTvSiteDescription;
    private TextView mTvSiteKeyWord;
    private SiteInfo mInfo;

    public static ManagerSiteFragment newInstance() {
        Bundle args = new Bundle();
        ManagerSiteFragment fragment = new ManagerSiteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_manager_site);
        initView(view);
        hiddenInputMethod();
        doRequest();
        showLoadingLayout();
        return view;
    }

    private void hiddenInputMethod() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void initView(View v) {
        mBtnPost = (Button) v.findViewById(R.id.manager_site_post);
        mTvSiteAddress = (TextView) v.findViewById(R.id.manager_site_address_url);
        mTvSiteDescription = (TextView) v.findViewById(R.id.manager_site_description);
        mTvSiteName = (TextView) v.findViewById(R.id.manager_site_name);
        mTvSiteKeyWord = (TextView) v.findViewById(R.id.manager_site_keyword);
        mBtnPost.setOnClickListener(this);
    }

    private void doRequest() {
        ApiSiteManager.getSiteInfo(this);
    }

    protected void bindData() {
        if (mInfo != null) {
            mTvSiteName.setText(mInfo.getSiteName());
            mTvSiteKeyWord.setText(mInfo.getSiteKeyWord());
            mTvSiteDescription.setText(mInfo.getSiteDescription());
            mTvSiteAddress.setText(mInfo.getSiteAddress());
        }
    }

    @Override
    public void onClick(View v) {
        showLoading("");
        mInfo.setSiteAddress(mTvSiteAddress.getText().toString());
        mInfo.setSiteDescription(mTvSiteDescription.getText().toString());
        mInfo.setSiteKeyWord(mTvSiteKeyWord.getText().toString());
        mInfo.setSiteName(mTvSiteName.getText().toString());
        ApiSiteManager.postSiteInfo(mInfo, this);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        mInfo = (SiteInfo) result;
        bindData();
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
