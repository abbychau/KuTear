package com.kutear.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kutear.app.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
