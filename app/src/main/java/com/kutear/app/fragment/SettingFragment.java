package com.kutear.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.kutear.app.R;
import com.kutear.app.callback.IUploadCallBack;
import com.kutear.app.upload.QiniuUpload;
import com.kutear.app.utils.L;
import com.kutear.app.viewhelper.EditTextViewHelper;

import java.io.File;

/**
 * Created by kutear.guo on 2015/8/18.
 * 设置页面
 */
public class SettingFragment extends BaseToolBarFragment {
    private static final String TAG = SettingFragment.class.getSimpleName();
    private EditTextViewHelper mEditTextViewHelper;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        initView(null);
        hiddenLoadingLayout();
        mEditTextViewHelper = new EditTextViewHelper(this);
        return mEditTextViewHelper.getMainView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mEditTextViewHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initView(View v) {
        setTitle(R.string.menu_string_setting);
    }
}
