package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.api.ApiUser;
import com.kutear.app.api.ICallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.SaveData;


@SuppressWarnings("ConstantConditions")
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private BaseActivity mActivity;
    private Toolbar mToolBar;
    private EditText mEtUserName;
    private EditText mEtPassWord;
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
        Button mBtnLogin = (Button) v.findViewById(R.id.login_btn_login);
        mTvForgetPass = (TextView) v.findViewById(R.id.login_tv_forget_password);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPass.setOnClickListener(this);
        initToolBar();
        initData();
    }

    private void initData() {
        mEtPassWord.setText(SaveData.getString(Constant.PASS));
        mEtUserName.setText(SaveData.getString(Constant.USER));
    }

    private void initToolBar() {
        mActivity.setSupportActionBar(mToolBar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_tv_forget_password:
                showSnack(mTvForgetPass, R.string.tips_not_support_find_password);
                break;
            default:
                break;
        }
    }

    private void login() {
        String user = mEtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            mEtUserName.setError(mActivity.getString(R.string.username_not_null));
            return;
        }
        String pass = mEtPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            mEtPassWord.setError(mActivity.getString(R.string.password_not_null));
            return;
        }
        saveUserAndPass(user, pass);
        KDialogFragment.showDialog(getFragmentManager());
        ApiUser.login(user, pass, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                KDialogFragment.hiddenDialog(getFragmentManager());
                showSnack(mEtPassWord, str);
                //mActivity.finish();
            }

            @Override
            public void onError(int statusCode, String str) {
                KDialogFragment.hiddenDialog(getFragmentManager());
                showSnack(mEtPassWord, str);
            }
        });
    }

    private void saveUserAndPass(String user, String pass) {
        SaveData.saveString(Constant.USER, user);
        SaveData.saveString(Constant.PASS, pass);
    }
}
