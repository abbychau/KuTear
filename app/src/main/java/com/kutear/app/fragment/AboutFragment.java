package com.kutear.app.fragment;

import android.os.Bundle;

/**
 * Created by kutear.guo on 2015/8/18.
 */
public class AboutFragment extends BaseFragment {
    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
