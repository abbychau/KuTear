package com.kutear.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.api.ApiArticleDetailsManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.utils.Constant;
import com.kutear.app.viewhelper.EditTextViewHelper;

/**
 * Created by kutear on 15-9-11.
 * 文章管理/编辑页面
 */
public class ManagerArticleEditFragment extends BaseToolBarFragment implements View.OnClickListener {

    public static final String KEY = "article_url";
    public static final String IS_MODIFY = "is_modify";
    public static final String TAG = ManagerArticleEditFragment.class.getSimpleName();
    private String mArticleUrl;
    private EditText mETTitle;
    private Button mBtPostArticle;
    private ManagerArticleDetails details;
    private EditTextViewHelper mEditTextViewHelper;
    private LinearLayout mContentLayout;

    /**
     * 传入文章的URL
     *
     * @param args
     * @return
     */
    public static ManagerArticleEditFragment newInstance(Bundle args) {
        ManagerArticleEditFragment fragment = new ManagerArticleEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View bodyView = inflate(R.layout.fragment_manager_article_edit);
        mEditTextViewHelper = new EditTextViewHelper(this);
        initView(bodyView);
        initResponse();
        doRequest();
        return bodyView;
    }

    private void initResponse() {
        mBtPostArticle.setOnClickListener(this);
    }

    private void doRequest() {
        mArticleUrl = getArguments().getString(KEY);
        ApiArticleDetailsManager.getArticleDetails(mArticleUrl, this);
    }

    @Override
    protected void initView(View v) {
        setTitle(R.string.article_edit_fragment_title);
        mETTitle = (EditText) v.findViewById(R.id.article_et_title);
        mBtPostArticle = (Button) v.findViewById(R.id.article_post_btn);
        mContentLayout = (LinearLayout) v.findViewById(R.id.article_et_content_layout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mEditTextViewHelper
                .getMainView().getLayoutParams();
        if (params == null) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
        }
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        mContentLayout.addView(mEditTextViewHelper.getMainView(), params);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        details = (ManagerArticleDetails) result;
        bindData();
    }

    private void bindData() {
        //修改
        if (getArguments().getBoolean(IS_MODIFY, false) && details != null) {
            mETTitle.setText(details.getTitle());
            mEditTextViewHelper.setContent(details.getContent());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_post_btn:
                getArticleDetails();
                previewArticle();
                break;
            default:
                break;
        }
    }

    /**
     * 根据信息重装ArticleDetails
     */
    private void getArticleDetails() {
        if (details == null) {
            details = new ManagerArticleDetails();
        }
        if (!TextUtils.isEmpty(mETTitle.getText().toString().trim())) {
            details.setTitle(mETTitle.getText().toString().trim());
            details.setContent(mEditTextViewHelper.getContent());
            //TODO 添加其他的信息
        } else {
            showSnack(mBtPostArticle, R.string.article_title_can_not_be_empty);
        }
    }

    /**
     * 文章预览
     */
    private void previewArticle() {
        if (details != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ManagerArticlePreviewFragment.KEY, details);
            AppApplication.startActivity(mActivity, Constant.ACTIVITY_PREVIEW_ARTICLE, bundle);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mEditTextViewHelper.onActivityResult(requestCode, resultCode, data);
    }
}
