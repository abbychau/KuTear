package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.api.ApiPagerDetailsManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerPagerDetails;
import com.kutear.app.bean.Pager;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear on 15-9-17.
 * 独立页面编辑
 */
public class ManagerPagerEditFragment extends BaseToolBarFragment implements View.OnClickListener {
    private static final String TAG = ManagerPagerEditFragment.class.getSimpleName();
    public static final String KEY = "pager_url";
    public static final String IS_MODIFY = "is_modify";
    private EditText mETTitle;
    private EditText mETContent;
    private Button mBtPostPager;
    private ManagerPagerDetails details;

    public static ManagerPagerEditFragment newInstance(Bundle args) {
        ManagerPagerEditFragment fragment = new ManagerPagerEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        setTitle(mActivity.getString(R.string.pager_edit));
        View mBodyView = inflate(R.layout.fragment_manager_pager_edit);
        initView(mBodyView);
        doRequest();
        return mBodyView;
    }

    private void doRequest() {
        if (getArguments().getBoolean(IS_MODIFY, false)) {
            Pager pager = getArguments().getParcelable(KEY);
            if (pager != null) {
                mETTitle.setText(pager.getName());
                ApiPagerDetailsManager.getPagerDetails(Constant.URI_PAGER_DETAILS + "?cid=" + pager.getIndex(), this);
            }
        } else {
            hiddenLoadingLayout();
        }
    }

    @Override
    protected void initView(View v) {
        mETTitle = (EditText) v.findViewById(R.id.pager_et_title);
        mETContent = (EditText) v.findViewById(R.id.pager_et_content);
        mBtPostPager = (Button) v.findViewById(R.id.pager_post_btn);
        mBtPostPager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (details == null) {
            details = new ManagerPagerDetails();
            //TODO 其它数据
        }
        details.setTitle(mETTitle.getText().toString().trim());
        details.setContent(mETContent.getText().toString().trim());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ManagerArticlePreviewFragment.KEY, details);
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_PREVIEW_ARTICLE, bundle);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        details = (ManagerPagerDetails) result;
        if (details != null) {
            mETContent.setText(details.getContent());
        }
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }
}
