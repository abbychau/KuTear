package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.R;


public class LoginFragment extends Fragment {
    private AppCompatActivity mActivity;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_login, null);
        mActivity = (AppCompatActivity) getActivity();
        initView(mBodyView);
        return mBodyView;
    }

    @SuppressWarnings("ConstantConditions")
    private void initView(View v) {

    }
}
