package com.kutear.app.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiAbout;
import com.kutear.app.bean.About;
import com.kutear.app.bean.BaseBean;

/**
 * Created by kutear.guo on 2015/8/18.
 * 关于页面
 */
public class AboutFragment extends BaseToolBarFragment {
    private TextView mTvAbout;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_about);
        initView(view);
        bindData();
        showLoadingLayout();
        return view;
    }

    protected void initView(View v) {
        setTitle(R.string.menu_string_about);
        mTvAbout = (TextView) v.findViewById(R.id.about_text);
    }

    private void bindData() {
        ApiAbout.getAbout(this);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        About about = (About) result;
        mTvAbout.setText(Html.fromHtml(about.getContent()));
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }
}
