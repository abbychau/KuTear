package com.kutear.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.kutear.app.R;
import com.kutear.app.fragment.DetailsFragment;
import com.kutear.app.fragment.LoginFragment;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class CommonActivity extends BaseActivity {

    private FragmentManager mManager;
    private int mFragmentType;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initView();
        startFragment();
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {
    }

    private void startFragment() {
        Intent intent = getIntent();
        if (intent != null) {
            mFragmentType = intent.getIntExtra(Constant.ACTIVITY_TYPE, -1);
        }
        if (mFragmentType == 0 || mFragmentType == -1) {
            return;
        }
        chooseFragment(mFragmentType);
    }

    private void chooseFragment(int type) {
        switch (type) {
            case Constant.ACTIVITY_LOGIN:
                mFragment = LoginFragment.newInstance();
                break;
            case Constant.ACTIVITY_DETAILS:
                mFragment = DetailsFragment.newInstance();
                break;
            default:
                break;
        }
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.common_fragment, mFragment).commit();
    }
}
