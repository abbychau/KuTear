package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kutear.app.R;

import java.util.ArrayList;

/**
 * Created by kutear.guo on 2015/10/27.
 * 网络图片预览
 */
public class ImagePreviewFragment extends BaseNoBarFragment {
    private ViewPager mViewPager;
    private ArrayList<String> urls;
    private PagerAdapter mAdapter;
    public static final String KEY = "urls";
    public static final String INDEX = "index";

    public static ImagePreviewFragment newInstance(Bundle args) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        return inflate(R.layout.fragment_image_previve);
    }

    @Override
    protected void initView(View v) {
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        getData();
    }

    private void getData() {
        urls = getArguments().getStringArrayList(KEY);
        mAdapter = new GrallyViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(getArguments().getInt(INDEX, 0));
        mAdapter.notifyDataSetChanged();
    }

    public class GrallyViewPagerAdapter extends FragmentPagerAdapter {


        public GrallyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return ImageSingleFragment.newInstance(urls.get(position));
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }
}
