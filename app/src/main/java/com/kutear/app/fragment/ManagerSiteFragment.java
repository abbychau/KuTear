package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiSiteManager;
import com.kutear.app.api.ApiUser;
import com.kutear.app.bean.SiteInfo;

/**
 * Created by kutear.guo on 2015/8/21.
 * 站点管理页面
 **/
public class ManagerSiteFragment extends BaseManagerFragment implements ApiSiteManager.ISiteManager, View.OnClickListener {
    private Button mBtnPost;
    private TextView mTvSiteName;
    private TextView mTvSiteAddress;
    private TextView mTvSiteDesription;
    private TextView mTvSiteKeyWord;
    private SiteInfo mInfo;

    public static ManagerSiteFragment newInstance() {
        Bundle args = new Bundle();
        ManagerSiteFragment fragment = new ManagerSiteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_site, container, false);
        initView(view);
        hiddenInputMethod();
        doRequest();
        return view;
    }

    private void hiddenInputMethod() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void initView(View v) {
        mBtnPost = (Button) v.findViewById(R.id.manager_site_post);
        mTvSiteAddress = (TextView) v.findViewById(R.id.manager_site_address_url);
        mTvSiteDesription = (TextView) v.findViewById(R.id.manager_site_description);
        mTvSiteName = (TextView) v.findViewById(R.id.manager_site_name);
        mTvSiteKeyWord = (TextView) v.findViewById(R.id.manager_site_keyword);
        mBtnPost.setOnClickListener(this);
    }

    private void doRequest() {
        ApiSiteManager.getSiteInfo(this);
    }

    @Override
    public void onSuccess(SiteInfo info) {
        mInfo = info;
        bindData();
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    protected void bindData() {
        if (mInfo != null) {
            mTvSiteName.setText(mInfo.getSiteName());
            mTvSiteKeyWord.setText(mInfo.getSiteKeyWord());
            mTvSiteDesription.setText(mInfo.getSiteDescription());
            mTvSiteAddress.setText(mInfo.getSiteAddress());
        }
    }

    @Override
    public void onClick(View v) {
        mInfo.setSiteAddress(mTvSiteAddress.getText().toString());
        mInfo.setSiteDescription(mTvSiteDesription.getText().toString());
        mInfo.setSiteKeyWord(mTvSiteKeyWord.getText().toString());
        mInfo.setSiteName(mTvSiteName.getText().toString());
        ApiSiteManager.postSiteInfo(mInfo);
        ApiSiteManager.getSiteInfo(this);
    }
}
