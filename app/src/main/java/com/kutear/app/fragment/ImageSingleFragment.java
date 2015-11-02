package com.kutear.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.utils.L;
import com.kutear.app.view.PinchImageView;

/**
 * Created by kutear.guo on 2015/10/27.
 * 单张图片显示
 */
public class ImageSingleFragment extends BaseNoBarFragment {
    private PinchImageView mImageView;
    private static final String KEY = "url";
    private String mUrl;
    private static final String TAG = ImageSingleFragment.class.getSimpleName();
    private Bitmap mBitMap;

    public static ImageSingleFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(KEY, url);
        ImageSingleFragment fragment = new ImageSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return new PinchImageView(mActivity);
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        mImageView = (PinchImageView) v;
//        mImageView.setDefaultImageResId(R.drawable.kutear_load);
//        mImageView.setErrorImageResId(R.drawable.kutear_fialed);
        mBitMap = BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.kutear_fialed);
        getData();
    }

    private void getData() {
        mUrl = getArguments().getString(KEY);
        ImageRequest imageRequest = new ImageRequest(mUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                mImageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mImageView.setImageBitmap(mBitMap);
            }
        });
        AppApplication.startRequest(imageRequest);
    }

    public Bitmap getBitmap() {
        return mBitMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBitMap!=null && mBitMap.isRecycled()){
            mBitMap.recycle();
        }
    }
}
