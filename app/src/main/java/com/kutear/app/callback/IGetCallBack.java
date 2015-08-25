package com.kutear.app.callback;

import com.kutear.app.bean.BaseBean;

/**
 * Created by kutear.guo on 2015/8/23.
 */
public interface IGetCallBack {
    void onGetSuccess(BaseBean result);

    void onGetError(String error);
}
