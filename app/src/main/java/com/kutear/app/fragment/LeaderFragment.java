package com.kutear.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.kutear.app.R;
import com.kutear.app.activity.MainActivity;
import com.kutear.app.service.CarouselServices;

/**
 * Created by kutear.guo on 2015/10/21.
 * 启动页
 */
public class LeaderFragment extends BaseNoBarFragment {


    public static LeaderFragment newInstance() {
        Bundle args = new Bundle();
        LeaderFragment fragment = new LeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView() {
        View mBodyView = inflate(R.layout.fragment_leader);
        initView(mBodyView);
        //启动时拉去轮播图
        Intent carouselIntent = new Intent();
        carouselIntent.setClass(mActivity, CarouselServices.class);
        mActivity.startService(carouselIntent);
        return mBodyView;
    }

    @Override
    protected void initView(View v) {
        hiddenLoadingLayout();
        Animation animation = new AlphaAnimation(0.5f, 1f);
        animation.setDuration(2 * 1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.setAnimation(animation);
    }
}
