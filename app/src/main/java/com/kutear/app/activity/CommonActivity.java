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
import com.kutear.app.fragment.ArticleDetailsFragment;
import com.kutear.app.fragment.LeaderFragment;
import com.kutear.app.fragment.LoginFragment;
import com.kutear.app.fragment.ManageFragment;
import com.kutear.app.fragment.ManagerArticlePreviewFragment;
import com.kutear.app.fragment.ManagerCategoryMdFragment;
import com.kutear.app.fragment.ManagerArticleEditFragment;
import com.kutear.app.fragment.ManagerPagerEditFragment;
import com.kutear.app.fragment.SettingFragment;
import com.kutear.app.fragment.UserCenterToolBarFragment;
import com.kutear.app.fragment.WebViewFragment;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

/**
 * Created by kutear.guo on 2015/8/4.
 * 所有Fragment的依附点
 */
public class CommonActivity extends BaseActivity {

    private FragmentManager mManager;
    private int mFragmentType;
    private Fragment mFragment;

    private static final String TAG = CommonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLayout();
        initView();
        startFragment();
    }

    protected void loadLayout() {
        if (isFullScreen) {
            setContentView(R.layout.common_full_screen_activity);
        } else {
            setContentView(R.layout.common_activity);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initView() {
    }

    private void startFragment() {
        Intent intent = getIntent();
        if (intent != null) {
            mFragmentType = intent.getIntExtra(Constant.ACTIVITY_TYPE, -1);
        }
        //默认为启动页
        if (mFragmentType == 0 || mFragmentType == -1) {
            mFragmentType = Constant.ACTIVITY_LEADER_PAGER;
        }
        chooseFragment(mFragmentType);
    }

    /**
     * 根据type来选择对应的Fragment
     *
     * @param type Fragment {@link Constant#ACTIVITY_ABOUT etc..}
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
                mFragment = ArticleDetailsFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_MANAGER:
                if (mApp.getUserManager().isLogin()) {
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
                mFragment = ManagerArticleEditFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_PREVIEW_ARTICLE:
                mFragment = ManagerArticlePreviewFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_EDIT_PAGER:
                mFragment = ManagerPagerEditFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_WEB_VIEW:
                mFragment = WebViewFragment.newInstance(bundle);
                break;
            case Constant.ACTIVITY_LEADER_PAGER:
                mFragment = LeaderFragment.newInstance();
                break;
            case Constant.ACTIVITY_IMAGE_PREVIEW:
                mFragment = LeaderFragment.newInstance();
                break;
            default:
                break;
        }
        mManager = getSupportFragmentManager();
        if (mFragment != null) {
            mManager.beginTransaction().replace(R.id.common_fragment, mFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.v(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
