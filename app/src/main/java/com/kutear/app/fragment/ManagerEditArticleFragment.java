package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;

import com.kutear.app.R;
import com.kutear.app.bean.BaseBean;

/**
 * Created by kutear on 15-9-11.
 * 文章管理/编辑页面
 */
public class ManagerEditArticleFragment extends BaseToolBarFragment {

    public static final String KEY = "article_url";
    private String mArticleUrl;

    /**
     * 传入文章的URL
     *
     * @param args
     * @return
     */
    public static ManagerEditArticleFragment newInstance(Bundle args) {
        ManagerEditArticleFragment fragment = new ManagerEditArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View bodyView = inflate(R.layout.fragment_manager_article_edit);
        initView(bodyView);
        return bodyView;
    }

    @Override
    protected void initView(View v) {

    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
    }
}
