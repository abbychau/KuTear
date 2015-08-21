package com.kutear.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.api.ApiArticleDetails;
import com.kutear.app.bean.Archive;
import com.kutear.app.bean.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by kutear.guo on 2015/8/5.
 * 文章详情
 */
public class DetailsFragment extends BaseFragment implements ApiArticleDetails.IArticleDetails {
    public static final String KEY = "Article";
    private static final String TAG = DetailsFragment.class.getSimpleName();
    private Toolbar mToolBar;
    private NetworkImageView mIvTitleBg;
    private Article mArticle;
    private TextView mTvContent;
    private ShareActionProvider mShareActionProvider;
    private CollapsingToolbarLayout mToolBarLayout;

    public static DetailsFragment newInstance(Bundle args) {
        DetailsFragment fragment = new DetailsFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_details, null);
        initView(mBodyView);
        bindData();
        loadDefaultBackground();
        KDialogFragment.showDialog(getFragmentManager());
        return mBodyView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.article_details, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareIntent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setShareIntent();
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        if (mShareActionProvider != null && mArticle != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_TEXT, mArticle.getContent());
            mShareActionProvider.setShareIntent(intent);
        }
    }

    private void bindData() {
        if (getArguments() != null) {
            if (getArguments().getParcelable(KEY) instanceof Article) {
                mArticle = getArguments().getParcelable(KEY);
                if (mArticle != null) {
                    mToolBarLayout.setTitle(mArticle.getTitle());
                    requestData(mArticle.getUrl());
                }
            }
            if (getArguments().getParcelable(KEY) instanceof Archive) {
                Archive mArchive = getArguments().getParcelable(KEY);
                if (mArchive != null) {
                    mToolBarLayout.setTitle(mArchive.getTitle());
                    requestData(mArchive.getUrl());
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void initView(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(mToolBar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        mToolBarLayout.setExpandedTitleColor(mActivity.getResources().getColor(R.color.teal_500));
        mToolBarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mTvContent = (TextView) v.findViewById(R.id.article_details_content);
        mIvTitleBg = (NetworkImageView) v.findViewById(R.id.title_background);
    }

    /**
     * 加载文章详情页面
     */
    private void requestData(String url) {
        ApiArticleDetails.getArticleDetails(url, this);
    }

    @Override
    public void onSuccess(String str) {
        setFirstImage(str);
        str += getCssFromAsset();
        mTvContent.setText(Html.fromHtml(str));
        KDialogFragment.hiddenDialog(getFragmentManager());
    }

    @Override
    public void onError(String str) {
        showSnack(mTvContent, str);
        KDialogFragment.hiddenDialog(getFragmentManager());
    }

    private String getCssFromAsset() {
        String cssUrl = "file:///android_asset/style.css";
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssUrl + "\" />";
    }

    private void loadDefaultBackground() {
        //TODO 需要添加自己的图片
        mIvTitleBg.setImageUrl("http://kutearforte-uploads.stor.sinaapp.com/2802316513.png", AppApplication.getImageLoader());
    }

    private void setFirstImage(String str) {
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("img");
        if (elements.first() != null) {
            String url = elements.first().attr("src");
            if (!TextUtils.isEmpty(url)) {
                mIvTitleBg.setImageUrl(url, AppApplication.getImageLoader());
            }
        }
    }

    @Override
    public void onDestroy() {
        mIvTitleBg.destroyDrawingCache();
        super.onDestroy();
    }
}
