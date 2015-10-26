package com.kutear.app.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kutear.app.R;
import com.kutear.app.api.ApiAbout;
import com.kutear.app.bean.About;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.view.RichTextView;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/18.
 * 关于页面
 */
public class AboutFragment extends BaseToolBarFragment {
    private RichTextView mTvAbout;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View view = inflate(R.layout.fragment_about);
        bindData();
        showLoadingLayout();
        return view;
    }
    @Override
    protected void initView(View v) {
        setTitle(R.string.menu_string_about);
        mTvAbout = (RichTextView) v.findViewById(R.id.about_text);
        mTvAbout.setOnImageClickListener(new RichTextView.OnImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
                Toast.makeText(mActivity,imageUrls.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void bindData() {
        ApiAbout.getAbout(this);
    }

    @Override
    public void onGetSuccess(BaseBean result) {
        super.onGetSuccess(result);
        About about = (About) result;
        mTvAbout.setHtml(about.getContent());
    }

    @Override
    public void onGetError(String error) {
        super.onGetError(error);
    }
}
