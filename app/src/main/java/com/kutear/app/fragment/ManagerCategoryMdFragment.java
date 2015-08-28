package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kutear.app.R;
import com.kutear.app.api.ApiCategoryManager;
import com.kutear.app.bean.ManagerCategory;

/**
 * Created by kutear.guo on 2015/8/27.
 * 修改/添加分类
 */
public class ManagerCategoryMdFragment extends BaseToolBarFragment implements View.OnClickListener {
    private ManagerCategory mCategory;
    public static final String KEY = "category_key";
    public static final String PARENT = "parent";
    private EditText mCategoryName;
    private EditText mCategorySimpleName;
    private String type;
    private int parent;

    public static ManagerCategoryMdFragment newInstance(Bundle args) {
        ManagerCategoryMdFragment fragment = new ManagerCategoryMdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View mBody = inflate(R.layout.fragment_manager_category_md);
        initView(mBody);
        bindData();
        hiddenLoadingLayout();
        return mBody;
    }

    @Override
    protected void initView(View v) {
        mCategoryName = (EditText) v.findViewById(R.id.manager_category_title);
        mCategorySimpleName = (EditText) v.findViewById(R.id.manager_category_simple);
        v.findViewById(R.id.manager_category_post).setOnClickListener(this);
    }

    private void bindData() {
        mCategory = getArguments().getParcelable(KEY);
        parent = getArguments().getInt(PARENT, 0);
        if (mCategory != null) {
            mCategoryName.setText(mCategory.getCategoryName());
            mCategorySimpleName.setText(mCategory.getSimpleName());
            type = "update";
            mCategory.setParentId(parent);
        } else {
            mCategory = new ManagerCategory();
            mCategory.setParentId(parent);
            type = "insert";
        }
    }

    @Override
    public void onClick(View v) {
        showLoading("等待中");
        mCategory.setCategoryName(mCategoryName.getText().toString());
        mCategory.setSimpleName(mCategorySimpleName.getText().toString());
        ApiCategoryManager.editCategory(mCategory, type, this);
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
