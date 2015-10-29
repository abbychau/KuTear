package com.kutear.app.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.utils.L;

/**
 * Created by kutear.guo on 2015/10/27.
 * 单张图片显示
 */
public class ImageSingleFragment extends BaseNoBarFragment {
    private NetworkImageView mImageView;
    private static final String KEY = "url";
    private String mUrl;
    private ImageLoader mImageLoader;
    private static final String TAG = ImageSingleFragment.class.getSimpleName();

    public static ImageSingleFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(KEY, url);
        ImageSingleFragment fragment = new ImageSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return new NetworkImageView(mActivity);
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        mImageView = (NetworkImageView) v;
        mImageView.setDefaultImageResId(R.drawable.kutear_load);
        mImageView.setErrorImageResId(R.drawable.kutear_fialed);
        getData();
    }

    private void getData() {
        mUrl = getArguments().getString(KEY);
        mImageLoader = mApp.getImageLoader();
        mImageView.setImageUrl(mUrl, mImageLoader);
    }

    public Bitmap getBitmap() {
        ImageLoader.ImageContainer container = (ImageLoader.ImageContainer) mImageView.getTag();
        return ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
    }

}
