package com.kutear.app.callback;

import com.kutear.app.bean.BaseBean;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/23.
 */
public interface IGetListCallBack {
    void onSuccess(List<? extends BaseBean> lists);

    void onError(String msg);
}
