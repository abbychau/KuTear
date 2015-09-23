package com.kutear.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.OnBackPressed;
import com.kutear.app.utils.L;

/**
 * Created by Kutear on 2015/8/10 in KuTear.
 * Fragment
 */
public abstract class BaseToolBarFragment extends BaseFragment implements OnBackPressed {
    protected BaseActivity mActivity;
    protected View bodyView;
    protected ViewGroup loadingLayout;
    protected ViewGroup contentLayout;
    protected Toolbar mToolBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        }
        if (contentLayout != null) {
            contentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected int getMenuRes() {
        return -1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bodyView = inflater.inflate(R.layout.fragment_base, container, false);
        loadingLayout = (ViewGroup) bodyView.findViewById(R.id.loading_layout);
        contentLayout = (ViewGroup) bodyView.findViewById(R.id.content_layout);
        mToolBar = (Toolbar) bodyView.findViewById(R.id.toolbar);
        mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getMenuRes() > 0) {
            mToolBar.inflateMenu(getMenuRes());
        }
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
//        mActivity.setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
//        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showLoadingLayout();
        View view = setContentView();
        if (view != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            contentLayout.removeAllViews();
            contentLayout.addView(view);
        }
        return bodyView;
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        hiddenLoadingLayout();
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
        // TODO: 2015/8/25 把加载视图改变为显示失败
    }

    /**
     * 通过该函数返回整个view布局
     *
     * @return 主视图
     */
    protected abstract View setContentView();

    protected final void setTitle(String title) {
        mToolBar.setTitle(title);
        mActivity.setTitle(title);
    }

    protected final void setTitle(int titleRes) {
        mToolBar.setTitle(titleRes);
        mActivity.setTitle(titleRes);
    }

    @Override
    public void onBackPressed() {
        mActivity.finish();
    }
}
