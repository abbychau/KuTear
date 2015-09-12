package com.kutear.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.fragment.AboutFragment;
import com.kutear.app.fragment.ArchiveFragment;
import com.kutear.app.fragment.ArticleListFragment;
import com.kutear.app.fragment.DetailsFragment;
import com.kutear.app.fragment.LoginFragment;
import com.kutear.app.fragment.ManageFragment;
import com.kutear.app.fragment.ManagerCategoryMdFragment;
import com.kutear.app.fragment.ManagerEditArticleFragment;
import com.kutear.app.fragment.ManagerArticlePreviewFragment;
import com.kutear.app.fragment.SettingFragment;
import com.kutear.app.fragment.UserCenterToolBarFragment;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear.guo on 2015/8/4.
 * 所有Fragment的依附点
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

    /**
     * 根据type来选择对应的Fragment
     *
     * @param type
     */
    private void chooseFragment(int type) {
        Bundle bundle = getIntent().getExtras();
        switch (type) {
            case Constant.ACTIVITY_LOGIN:
                mFragment = LoginFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_ARCHIVE:
                mFragment = ArchiveFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_DETAILS:
                mFragment = DetailsFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_MANAGER:
                if (AppApplication.getUserManager().isLogin()) {
                    mFragment = ManageFragment.newInstance();
                } else {
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    //使得登陆后跳转到要调往的页面
                    bundle.putInt(LoginFragment.TO_FRAGMENT, Constant.ACTIVITY_MANAGER);
                    mFragment = LoginFragment.newInstance(bundle);
                }
                break;
            case Constant.ACTIVITY_USER_CENTER:
                mFragment = UserCenterToolBarFragment.newInstance();
                break;
            case Constant.ACTIVITY_PREVIEW:
                mFragment = ManagerArticlePreviewFragment.newInstance();
                break;
            case Constant.ACTIVITY_SETTING:
                mFragment = SettingFragment.newInstance();
                break;
            case Constant.ACTIVITY_ABOUT:
                mFragment = AboutFragment.newInstance();
                break;
            case Constant.ACTIVITY_ARTICLE_LIST:
                mFragment = ArticleListFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_CATEGORY_MD:
                mFragment = ManagerCategoryMdFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_EDIT_ARTICLE:
                mFragment = ManagerEditArticleFragment.newInstance(bundle);
                break;
            default:
                break;
        }
        mManager = getSupportFragmentManager();
        if (mFragment != null) {
            mManager.beginTransaction().replace(R.id.common_fragment, mFragment).commitAllowingStateLoss();
        }
    }
}
