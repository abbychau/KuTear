package com.kutear.app.fragment;

import android.view.View;

import com.kutear.app.api.ApiUser;
import com.kutear.app.api.ICallBack;

/**
 * Created by kutear.guo on 2015/8/21.
 * 主要擢用是用来控制用户的登陆状态
 */
public abstract class BaseManagerFragment extends BaseFragment implements ICallBack {
    protected View mainView;

    public BaseManagerFragment() {
        // TODO: 2015/8/21 检查是否已经登陆,在没登陆的情况下需要重新登陆,以便后面的请求正常
        ApiUser.checkUserLogin(this);
    }


    @Override
    public void onSuccess(int statusCode, String str) {
        initView(mainView);
        bindData();
    }

    @Override
    public void onError(int statusCode, String str) {
        ApiUser.autoLogin(null);
    }

    protected abstract void bindData();
}
