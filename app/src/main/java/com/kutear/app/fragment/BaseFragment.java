package com.kutear.app.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kutear.app.activity.BaseActivity;

/**
 * Created by Kutear on 2015/8/10 in KuTear.
 */
public class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }
}
