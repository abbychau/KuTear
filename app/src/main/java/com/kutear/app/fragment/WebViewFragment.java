package com.kutear.app.fragment;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kutear.app.R;
import com.kutear.app.callback.OnBackPressed;

/**
 * A simple {@link Fragment} subclass.
 */

public class WebViewFragment extends BaseToolBarFragment {

    public static final String KEY = "key";
    private WebView mWebView;

    public static WebViewFragment newInstance(Bundle args) {
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_web_view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kutear_web_view_close:
                mActivity.finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView(View v) {
        mWebView = (WebView) v.findViewById(R.id.kutear_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setTitle(mWebView.getTitle());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress >= 90) {
                    hiddenLoadingLayout();
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });
        loadData();
    }

    private void loadData() {
        if (!TextUtils.isEmpty(getArguments().getString(KEY))) {
            mWebView.loadUrl(getArguments().getString(KEY));
        }
    }

    @Override
    protected View setContentView() {
        View mBodyView = inflate(R.layout.fragment_web_view);
        initView(mBodyView);
        return mBodyView;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mActivity.finish();
        }
    }
}