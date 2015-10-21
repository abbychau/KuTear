package com.kutear.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.api.ApiPagerDetailsManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerPagerDetails;
import com.kutear.app.bean.Pager;
import com.kutear.app.utils.Constant;
import com.kutear.app.viewhelper.EditTextViewHelper;

/**
 * Created by kutear on 15-9-17.
 * 独立页面编辑
 */
public class ManagerPagerEditFragment extends BaseToolBarFragment {
    private static final String TAG = ManagerPagerEditFragment.class.getSimpleName();
    public static final String KEY = "pager_url";
    public static final String IS_MODIFY = "is_modify";
    private EditText mETTitle;
    private ManagerPagerDetails details;
    private EditTextViewHelper mEditTextViewHelper;
    private LinearLayout mContentLayout;

    public static ManagerPagerEditFragment newInstance(Bundle args) {
        ManagerPagerEditFragment fragment = new ManagerPagerEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        setTitle(mActivity.getString(R.string.pager_edit));
        View mBodyView = inflate(R.layout.fragment_manager_pager_edit);
        mEditTextViewHelper = new EditTextViewHelper(this);
        return mBodyView;
    }

    private void doRequest() {
        if (getArguments().getBoolean(IS_MODIFY, false)) {
            Pager pager = getArguments().getParcelable(KEY);
            if (pager != null) {
                mETTitle.setText(pager.getName());
                ApiPagerDetailsManager.getPagerDetails(Constant.URI_PAGER_DETAILS + "?cid="
                        + pager.getIndex(), this);
            }
        } else {
            hiddenLoadingLayout();
        }
    }

    @Override
    protected void initView(View v) {
        mETTitle = (EditText) v.findViewById(R.id.pager_et_title);
        mContentLayout = (LinearLayout) v.findViewById(R.id.pager_et_content_layout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mEditTextViewHelper
                .getMainView().getLayoutParams();
        if (params == null) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
        }
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        mContentLayout.addView(mEditTextViewHelper.getMainView(), params);
        doRequest();
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_preview, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_preview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.article_post_menu) {
            if (details == null) {
                details = new ManagerPagerDetails();
                //TODO 其它数据
            }
            details.setTitle(mETTitle.getText().toString().trim());
            details.setContent(mEditTextViewHelper.getContent());
            Bundle bundle = new Bundle();
            bundle.putParcelable(ManagerArticlePreviewFragment.KEY, details);
            AppApplication.startActivity(mActivity, Constant.ACTIVITY_PREVIEW_ARTICLE, bundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        details = (ManagerPagerDetails) result;
        if (details != null) {
            mEditTextViewHelper.setContent(details.getContent());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mEditTextViewHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }
}
