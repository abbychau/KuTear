package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.R;
import com.kutear.app.bean.BaseBean;

/**
 * Created by Kutear on 2015/8/10 in KuTear.
 * Fragment
 */
public abstract class BaseNoBarFragment extends BaseFragment {
    protected ViewGroup loadingLayout;
    protected ViewGroup contentLayout;
    private View mLoadFailedView;
    private View mLoadProcessView;


    protected void hiddenLoadingLayout() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (contentLayout != null) {
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void showLoadingLayout() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            mLoadProcessView.setVisibility(View.VISIBLE);
            mLoadFailedView.setVisibility(View.GONE);
        }
        if (contentLayout != null) {
            contentLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 请求失败调用
     */
    protected void showErrorLayout() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            mLoadProcessView.setVisibility(View.GONE);
            mLoadFailedView.setVisibility(View.VISIBLE);
        }
        if (contentLayout != null) {
            contentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View bodyView = inflater.inflate(R.layout.fragment_nobar_base, container, false);
        loadingLayout = (ViewGroup) bodyView.findViewById(R.id.loading_layout);
        contentLayout = (ViewGroup) bodyView.findViewById(R.id.content_layout);
        mLoadFailedView = bodyView.findViewById(R.id.base_load_fialed);
        mLoadProcessView = bodyView.findViewById(R.id.progressBar);
        View view = setContentView();
        if (view != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeAllViews();
            }
            if (contentLayout != null) {
                contentLayout.removeAllViews();
                contentLayout.addView(view);
            }
        }
        showLoadingLayout();
        initView(view);
        return bodyView;
    }


    /**
     * 通过该函数返回整个view布局
     *
     * @return 主视图
     */
    protected abstract View setContentView();


    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        hiddenLoadingLayout();
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
        hiddenLoadingLayout();
        showErrorLayout();
    }
}
