package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiReadManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ReadInfo;

/**
 * Created by kutear.guo on 2015/8/21.
 * 阅读设置页面
 */
public class ManagerReadFragment extends BaseNoBarFragment implements View.OnClickListener {
    private TextView mTvDateFormat;
    private TextView mTvListsSize;
    private TextView mTvPageSize;

    public static ManagerReadFragment newInstance() {
        Bundle args = new Bundle();
        ManagerReadFragment fragment = new ManagerReadFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_manager_read);
        initView(view);
        bindData();
        return view;
    }

    @Override
    protected void initView(View v) {
        mTvDateFormat = (TextView) v.findViewById(R.id.manager_read_article_date_format);
        mTvListsSize = (TextView) v.findViewById(R.id.manager_read_article_list_count);
        mTvPageSize = (TextView) v.findViewById(R.id.manager_read_each_pager_article_count);
        loadingLayout = (ViewGroup) v.findViewById(R.id.loading_layout);
        contentLayout = (ViewGroup) v.findViewById(R.id.content_layout);
        v.findViewById(R.id.manager_read_tips).setOnClickListener(this);
        v.findViewById(R.id.manager_read_post).setOnClickListener(this);
    }

    private void bindData() {
        ApiReadManager.getReadInfo(this);
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        ReadInfo info = (ReadInfo) result;
        mTvListsSize.setText(info.getListsCount() + "");
        mTvPageSize.setText(info.getPagerCount() + "");
        mTvDateFormat.setText(info.getDateFormat());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manager_read_post:
                showLoading("");
                ReadInfo info = new ReadInfo();
                info.setDateFormat(mTvDateFormat.getText().toString());
                info.setListsCount(Integer.parseInt(mTvListsSize.getText().toString()));
                info.setPagerCount(Integer.parseInt(mTvPageSize.getText().toString()));
                ApiReadManager.postReadInfo(info, this);
                break;
            case R.id.manager_read_tips:
                // TODO: 2015/8/23 弹出对话框显示规则
                break;
            default:
                break;
        }
    }
}
