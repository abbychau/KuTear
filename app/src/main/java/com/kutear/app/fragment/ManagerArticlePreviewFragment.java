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
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.utils.L;

/**
 * `
 * Created by kutear.guo on 2015/8/13.
 * 文章预览
 */
public class ManagerArticlePreviewFragment extends BaseToolBarFragment {
    public static String KEY = "key";
    public static String TAG = ManagerArticlePreviewFragment.class.getSimpleName();
    private WebView mWebView;
    private ManagerArticleDetails details;

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
        details = getArguments().getParcelable(KEY);
        if (details != null) {
            AndDown andDown = new AndDown();
            String html = andDown.markdownToHtml(details.getContent());
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDefaultTextEncodingName("utf-8");
            mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        mActivity.setTitle(R.string.article_preview_fragment_title);
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
            showLoading(mActivity.getString(R.string.article_posting));
            details.setDoAction("publish");//TODO 保存草稿save
            ApiArticleDetailsManager.postArticle(details, this);
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
