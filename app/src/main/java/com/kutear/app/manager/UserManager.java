package com.kutear.app.manager;

import com.kutear.app.api.ApiUser;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.bean.UserInfo;
import com.kutear.app.callback.IGetCallBack;

/**
 * Created by kutear.guo on 2015/8/20.
 * 用户管理
 */
public class UserManager implements ICallBack {
    private boolean isLogin;
    private UserInfo mUserInfo;

    public UserManager() {
        ApiUser.autoLogin(this);
    }

    public boolean isLogin() {
        return isLogin;
    }

    @Override
    public void onSuccess(int statusCode, String str) {
        isLogin = true;
        //继续获取用户的信息
        ApiUser.getUserInfo(new IGetCallBack() {
            @Override
            public void onGetSuccess(BaseBean result) {
                mUserInfo = (UserInfo) result;
            }

            @Override
            public void onGetError(String error) {
                mUserInfo = null;
            }
        });
    }

    public void setUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
        this.isLogin = true;
    }

    @Override
    public void onError(int statusCode, String str) {
        isLogin = false;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
