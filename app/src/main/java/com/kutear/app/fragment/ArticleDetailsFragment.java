package com.kutear.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.activity.BaseActivity;
import com.kutear.app.api.ApiArticleDetails;
import com.kutear.app.bean.Archive;
import com.kutear.app.bean.Article;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;
import com.kutear.app.view.RichTextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/5.
 * 文章详情
 */
public class ArticleDetailsFragment extends BaseFragment implements IGetCallBack, RichTextView.OnUrlClickListener, RichTextView.OnImageClickListener {
    public static final String KEY = "Article";
    private static final String TAG = ArticleDetailsFragment.class.getSimpleName();
    private Toolbar mToolBar;
    private NetworkImageView mIvTitleBg;
    private Article mArticle;
    private RichTextView mTvContent;
    private CollapsingToolbarLayout mToolBarLayout;

    public static ArticleDetailsFragment newInstance(Bundle args) {
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_details, container, false);
        initView(mBodyView);
        bindData();
        loadDefaultBackground();
        showLoading(mActivity.getString(R.string.loading));
        return mBodyView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.article_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_open_in_brown) {
            openInBrowser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openInBrowser() {
        if (getArguments() != null) {
            if (getArguments().getParcelable(KEY) instanceof Article) {
                mArticle = getArguments().getParcelable(KEY);
                if (mArticle != null) {
                    startWebFragment(mArticle.getUrl());
                }
            }
            if (getArguments().getParcelable(KEY) instanceof Archive) {
                Archive mArchive = getArguments().getParcelable(KEY);
                if (mArchive != null) {
                    startWebFragment(mArchive.getUrl());
                }
            }
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
        mTvContent = (RichTextView) v.findViewById(R.id.article_details_content);
        mIvTitleBg = (NetworkImageView) v.findViewById(R.id.title_background);
        mTvContent.setOnImageClickListener(this);
        mTvContent.setOnUrlClickListener(this);
    }

    /**
     * 加载文章详情页面
     */
    private void requestData(String url) {
        ApiArticleDetails.getArticleDetails(url, this);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        String str = ((Article) result).getDetail();
//        setFirstImage(str);
        str += getCssFromAsset();
        mTvContent.setHtml(str);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
        showSnack(mTvContent, error);
    }

    /**
     * 加载本地CSS
     *
     * @return
     */
    //TODO 存在Bug
    private String getCssFromAsset() {
        String cssUrl = "file://android_asset/style.css";
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssUrl + "\" />";
    }

    private void loadDefaultBackground() {
        mIvTitleBg.setImageUrl(mApp.getCarouselManager().getRandomUrl(), mApp.getImageLoader());
    }

//    private void setFirstImage(String str) {
//        if (TextUtils.isEmpty(str)) {
//            return;
//        }
//        Document document = Jsoup.parse(str);
//        Elements elements = document.getElementsByTag("img");
//        if (elements.first() != null) {
//            String url = elements.first().attr("src");
//            if (!TextUtils.isEmpty(url)) {
//                mIvTitleBg.setImageUrl(url, mApp.getImageLoader());
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        mIvTitleBg.destroyDrawingCache();
        if (mTvContent != null) {
            mTvContent.recycle();
        }
        super.onDestroy();
    }

    @Override
    public void urlClicked(String url) {
        startWebFragment(url);
    }

    @Override
    public void imageClicked(List<String> imageUrls, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImagePreviewFragment.INDEX, position);
        bundle.putStringArrayList(ImagePreviewFragment.KEY, (ArrayList<String>) imageUrls);
        mApp.startActivity(mActivity, Constant.ACTIVITY_IMAGE_PREVIEW, bundle);
    }
}
