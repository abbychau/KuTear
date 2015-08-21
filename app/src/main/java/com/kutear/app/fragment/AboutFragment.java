package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiAbout;

/**
 * Created by kutear.guo on 2015/8/18.
 * 关于页面
 */
public class AboutFragment extends BaseFragment implements ApiAbout.IAbout {
    private Toolbar mToolBar;
    private TextView mTvAbout;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initView(view);
        bindData();
        return view;
    }

    protected void initView(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolBar);
        //noinspection ConstantConditions
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.setTitle(R.string.menu_string_about);
        mTvAbout = (TextView) v.findViewById(R.id.about_text);
    }

    private void bindData() {
        ApiAbout.getAbout(this);
    }

    @Override
    public void onSuccess(String string) {
        mTvAbout.setText(Html.fromHtml(string));
    }

    @Override
    public void onError(String string) {

    }
}
