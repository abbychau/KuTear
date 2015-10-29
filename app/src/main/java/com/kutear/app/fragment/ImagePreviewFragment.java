package com.kutear.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.kutear.app.R;
import com.kutear.app.utils.SaveData;

import java.util.ArrayList;

/**
 * Created by kutear.guo on 2015/10/27.
 * 网络图片预览
 */
public class ImagePreviewFragment extends BaseToolBarFragment implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ArrayList<String> urls;
    private GalleryViewPagerAdapter mAdapter;
    public static final String KEY = "urls";
    public static final String INDEX = "index";
    private int index;

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
        hiddenLoadingLayout();
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        getData();
    }

    @Override
    protected int getMenuRes() {
        return R.menu.image_preview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_save_image_to_sd) {
            getDataFromFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取Fragment的图片并保存自SD卡
     */
    private void getDataFromFragment() {
        if (mAdapter != null) {

            final ImageSingleFragment mFragment = (ImageSingleFragment) mAdapter.getItem(index);

            final Bitmap bmp = mFragment.getBitmap();
            new Thread() {
                @Override
                public void run() {
                    if (bmp == null) {
                        return;
                    }
                    String fileName = System.currentTimeMillis() + urls.get(index).substring(urls.get(index).lastIndexOf("/") + 1, urls.get(index).length());
                    String absPath = SaveData.saveImage(bmp, fileName);
                    Snackbar.make(mViewPager,mActivity.getString(R.string.save_image_to_sd)+absPath,Snackbar.LENGTH_SHORT).show();
                    super.run();
                }
            }.start();

        }
    }

    private void getData() {
        urls = getArguments().getStringArrayList(KEY);
        if (urls == null) {
            return;
        }
        mAdapter = new GalleryViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        index = getArguments().getInt(INDEX, 0);
        mViewPager.setCurrentItem(index);
        setTitle(mActivity.getString(R.string.image_preview) + "(" + (index + 1) + "/" + urls.size() + ")");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        setTitle(mActivity.getString(R.string.image_preview) + "(" + (index + 1) + "/" + urls.size() + ")");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 图片适配器
     */
    public class GalleryViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<ImageSingleFragment> mFragmentLists;

        public GalleryViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentLists = new ArrayList<>();
            for (String url : urls) {
                mFragmentLists.add(ImageSingleFragment.newInstance(url));
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentLists.get(position);
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }
}
