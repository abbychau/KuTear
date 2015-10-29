package com.kutear.app.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.commonsware.cwac.anddown.AndDown;
import com.kutear.app.R;
import com.kutear.app.api.ApiArticleDetailsManager;
import com.kutear.app.api.ApiPagerDetailsManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.bean.ManagerPagerDetails;
import com.kutear.app.netutils.UrlImageParser;
import com.kutear.app.utils.Constant;
import com.kutear.app.view.RichTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * `
 * Created by kutear.guo on 2015/8/13.
 * 文章/独立页面预览
 */
public class ManagerArticlePreviewFragment extends BaseToolBarFragment implements RichTextView.OnImageClickListener, RichTextView.OnUrlClickListener {
    public static String KEY = "key";
    public static String TAG = ManagerArticlePreviewFragment.class.getSimpleName();
    private RichTextView mTextView;
    private BaseBean mBaseBean;

    public static ManagerArticlePreviewFragment newInstance(Bundle args) {
        ManagerArticlePreviewFragment fragment = new ManagerArticlePreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return inflate(R.layout.fragment_preview_article);
    }

    private void bindData() {
        mBaseBean = getArguments().getParcelable(KEY);
        if (mBaseBean != null) {
            AndDown andDown = new AndDown();
            if (mBaseBean instanceof ManagerArticleDetails) {
                setTitle(R.string.article_preview_fragment_title);
                ManagerArticleDetails details = (ManagerArticleDetails) mBaseBean;
                String html = andDown.markdownToHtml(details.getContent());
                mTextView.setHtml(html);
            } else if (mBaseBean instanceof ManagerPagerDetails) {
                setTitle(R.string.pager_preview_fragment_title);
                ManagerPagerDetails details = (ManagerPagerDetails) mBaseBean;
                String html = andDown.markdownToHtml(details.getContent());
                mTextView.setHtml(html);
            }
        }
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        mTextView = (RichTextView) v.findViewById(R.id.article_details_text_view);
        mTextView.setOnImageClickListener(this);
        mTextView.setOnUrlClickListener(this);
        bindData();
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_preview;
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
    public void onDestroy() {
        super.onDestroy();
        if (mTextView != null) {
            mTextView.recycle();
        }
    }

    @Override
    public void onPostSuccess(String msg) {
        super.onPostSuccess(msg);
    }

    @Override
    public void onPostError(String msg) {
        super.onPostError(msg);
    }

    @Override
    public void imageClicked(List<String> imageUrls, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImagePreviewFragment.INDEX, position);
        bundle.putStringArrayList(ImagePreviewFragment.KEY, (ArrayList<String>) imageUrls);
        mApp.startActivity(mActivity, Constant.ACTIVITY_IMAGE_PREVIEW, bundle);
    }

    @Override
    public void urlClicked(String url) {
        startWebFragment(url);
    }
}
