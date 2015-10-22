package com.kutear.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.callback.IPostCallBack;

/**
 * Created by Kutear on 2015/8/10 in KuTear.
 * 该基类使用在包含特殊结构的ToolBar中,本身不包含任何视图
 */
public abstract class BaseFragment extends Fragment implements IGetCallBack, IPostCallBack {
    protected BaseActivity mActivity;
    protected LayoutInflater inflater;
    protected ViewGroup container;
    protected Bundle savedInstanceState;
    protected AppApplication mApp;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        mApp = mActivity.getAppApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    protected void showSnack(View hook, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Snackbar.make(hook, msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    protected void showSnack(View hook, int msgRes) {
        Snackbar.make(hook, msgRes, Snackbar.LENGTH_SHORT).show();
    }

    protected void showLoading(String msg) {
        KDialogFragment.showLoadingDialog(getFragmentManager(), msg);
    }

    protected void hiddenLoad() {
        KDialogFragment.hiddenDialog(getFragmentManager());
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        hiddenLoad();
    }

    @Override
    public void onGetError(String error) {
        hiddenLoad();
    }

    @Override
    public void onPostSuccess(String msg) {
        hiddenLoad();
    }

    @Override
    public void onPostError(String msg) {
        hiddenLoad();
    }

    /**
     * 如没有网络请求,主动调用函数关闭加载布局
     * @param v
     */
    protected abstract void initView(View v);


    /**
     * 加载视图
     *
     * @param res 资源文件
     * @return
     */
    protected final View inflate(int res) {
        if (inflater != null) {
            return inflater.inflate(res, container, false);
        } else {
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(res, container, false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        System.gc();
    }

}