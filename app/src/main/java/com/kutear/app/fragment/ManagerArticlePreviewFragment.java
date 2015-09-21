package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.commonsware.cwac.anddown.AndDown;
import com.kutear.app.R;
import com.kutear.app.api.ApiArticleDetailsManager;
import com.kutear.app.api.ApiPagerDetailsManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.bean.ManagerPagerDetails;

/**
 * `
 * Created by kutear.guo on 2015/8/13.
 * 文章/独立页面预览
 */
public class ManagerArticlePreviewFragment extends BaseToolBarFragment {
    public static String KEY = "key";
    public static String TAG = ManagerArticlePreviewFragment.class.getSimpleName();
    private WebView mWebView;
    private BaseBean mBaseBean;

    public static ManagerArticlePreviewFragment newInstance(Bundle args) {
        ManagerArticlePreviewFragment fragment = new ManagerArticlePreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View bodyView = inflate(R.layout.fragment_preview_article);
        initView(bodyView);
        bindData();
        return bodyView;
    }

    private void bindData() {
        mBaseBean = getArguments().getParcelable(KEY);
        if (mBaseBean != null) {
            AndDown andDown = new AndDown();
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDefaultTextEncodingName("utf-8");
            if (mBaseBean instanceof ManagerArticleDetails) {
                mActivity.setTitle(R.string.article_preview_fragment_title);
                ManagerArticleDetails details = (ManagerArticleDetails) mBaseBean;
                String html = andDown.markdownToHtml(details.getContent());
                mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
            } else if (mBaseBean instanceof ManagerPagerDetails) {
                mActivity.setTitle(R.string.pager_preview_fragment_title);
                ManagerPagerDetails details = (ManagerPagerDetails) mBaseBean;
                String html = andDown.markdownToHtml(details.getContent());
                mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
            }
        }
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        mWebView = (WebView) v.findViewById(R.id.article_details_webview);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_preview, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.article_post_menu) {
            if (mBaseBean instanceof ManagerArticleDetails) {
                showLoading(mActivity.getString(R.string.article_posting));
                ManagerArticleDetails details = (ManagerArticleDetails) mBaseBean;
                details.setDoAction("publish");//TODO 保存草稿save
                ApiArticleDetailsManager.postArticle(details, this);
            } else if (mBaseBean instanceof ManagerPagerDetails) {
                showLoading(mActivity.getString(R.string.pager_posting));
                ManagerPagerDetails details = (ManagerPagerDetails) mBaseBean;
                details.setDoAction("publish");//TODO 保存草稿save
                ApiPagerDetailsManager.postPager(details, this);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostSuccess(String msg) {
        super.onPostSuccess(msg);
    }

    @Override
    public void onPostError(String msg) {
        super.onPostError(msg);
    }
}
