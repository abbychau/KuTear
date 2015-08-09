package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutear.app.R;

/**
 * Created by kutear.guo on 2015/8/5.
 */
public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance() {
        Bundle args = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mBodyView = inflater.inflate(R.layout.fragment_details, null);
        initView(mBodyView);
        return mBodyView;
    }

    private void initView(View v) {

    }
}
