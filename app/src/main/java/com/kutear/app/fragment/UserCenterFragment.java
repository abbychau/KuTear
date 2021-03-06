package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.R;
import com.kutear.app.api.ApiUser;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.UserInfo;

/**
 * Created by kutear.guo on 2015/8/19.
 * 用户中心
 */
public class UserCenterFragment extends BaseFragment {
    private static final String TAG = UserCenterFragment.class.getSimpleName();
    private NetworkImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private UserInfo mUserInfo;
    private TextView mMainPager;
    private TextView mMail;
    private TextView mUserName;

    public static UserCenterFragment newInstance() {
        Bundle args = new Bundle();
        UserCenterFragment fragment = new UserCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = mApp.getUserManager() != null ? mApp.getUserManager().getUserInfo() : null;
        if (mUserInfo == null) {
            ApiUser.getUserInfo(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center,container,false);
        initView(view);
        return view;
    }

    protected void initView(View v) {
        Toolbar mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImageView = (NetworkImageView) v.findViewById(R.id.title_background);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        mMail = (TextView) v.findViewById(R.id.user_center_email);
        mMainPager = (TextView) v.findViewById(R.id.user_center_main_pager);
        mUserName = (TextView) v.findViewById(R.id.user_center_username);
        if (mUserInfo != null) {
            bindData();
        }
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        this.mUserInfo = (UserInfo) result;
        bindData();
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }

    private void bindData() {
        mImageView.setImageUrl(mUserInfo.getAvater(), mApp.getImageLoader());
        mCollapsingToolbarLayout.setTitle(mUserInfo.getNickName());
        mCollapsingToolbarLayout.setExpandedTitleColor(mActivity.getResources().getColor(R.color.teal_500));
        mMainPager.setText(mUserInfo.getMainPager());
        mMail.setText(mUserInfo.getEMail());
        mUserName.setText(mUserInfo.getNickName());
    }
}
