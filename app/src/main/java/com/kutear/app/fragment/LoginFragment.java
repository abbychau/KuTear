package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.commonsware.cwac.anddown.AndDown;
import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.api.ApiUser;


@SuppressWarnings("ConstantConditions")
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private BaseActivity mActivity;
    private Toolbar mToolBar;
    private EditText mEtUserName;
    private EditText mEtPassWord;
    private Button mBtnLogin;
    private TextView mTvForgetPass;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_login, null);
        mActivity = (BaseActivity) getActivity();
        initView(mBodyView);
        return mBodyView;
    }

    private void initView(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mActivity.setTitle(R.string.login);
        mEtPassWord = (EditText) v.findViewById(R.id.login_et_password);
        mEtUserName = (EditText) v.findViewById(R.id.login_et_username);
        mBtnLogin = (Button) v.findViewById(R.id.login_btn_login);
        mTvForgetPass = (TextView) v.findViewById(R.id.login_tv_forget_password);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPass.setOnClickListener(this);
        initToolBar();
    }

    private void initToolBar() {
        mActivity.setSupportActionBar(mToolBar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                ApiUser.login(mEtUserName.getText().toString().trim(), mEtPassWord.getText().toString().trim(), null);
                break;
            case R.id.login_tv_forget_password:
                Snackbar.make(mTvForgetPass, R.string.tips_not_support_find_password, Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
