package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.AppApplication;

/**
 * Created by kutear.guo on 2015/10/27.
 * 单张图片显示
 */
public class ImageSingleFragment extends BaseNoBarFragment {
    private NetworkImageView mImageView;
    private static final String KEY = "url";

    public static ImageSingleFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(KEY, url);
        ImageSingleFragment fragment = new ImageSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return new ImageView(mActivity);
    }

    @Override
    protected void initView(View v) {
        mImageView = (NetworkImageView) v;
        getData();
    }

    private void getData() {
       String url = getArguments().getString(KEY);
        mImageView.setImageUrl(url, AppApplication.getImageLoader());
    }
}
