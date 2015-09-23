package com.kutear.app.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.api.ApiUser;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.UserInfo;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;
import com.kutear.app.utils.SaveData;


@SuppressWarnings("ConstantConditions")
public class LoginFragment extends BaseToolBarFragment implements View.OnClickListener {
    private EditText mEtUserName;
    private EditText mEtPassWord;
    private TextView mTvForgetPass;
    private static final String TAG = LoginFragment.class.getSimpleName();
    public static final String TO_FRAGMENT = "to_fragment";

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected View setContentView() {
        View mBodyView = inflate(R.layout.fragment_login);
        initView(mBodyView);
        return mBodyView;
    }

    protected void initView(View v) {
        setTitle(R.string.login);
        mEtPassWord = (EditText) v.findViewById(R.id.login_et_password);
        mEtUserName = (EditText) v.findViewById(R.id.login_et_username);
        Button mBtnLogin = (Button) v.findViewById(R.id.login_btn_login);
        mTvForgetPass = (TextView) v.findViewById(R.id.login_tv_forget_password);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPass.setOnClickListener(this);
        initToolBar();
        initData();
        hiddenLoadingLayout();
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
        showLoading(mActivity.getString(R.string.login_ing));
        ApiUser.login(user, pass, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                L.v(TAG, str);
                ApiUser.getUserInfo(LoginFragment.this);
            }

            @Override
            public void onError(int statusCode, String str) {
                hiddenLoad();
                showSnack(mEtPassWord, str);
            }
        });
    }

    private void saveUserAndPass(String user, String pass) {
        SaveData.saveString(Constant.USER, user);
        SaveData.saveString(Constant.PASS, pass);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
        showSnack(mEtPassWord, error);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        AppApplication.getUserManager().setUserInfo((UserInfo) result);
        mActivity.finish();

        if (getArguments() != null) {
            int toFragment = getArguments().getInt(TO_FRAGMENT, 0);
            if (toFragment != 0) {
                AppApplication.startActivity(mActivity, toFragment, null);
            }
        }
    }

}
