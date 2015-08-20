package com.kutear.app.fragment;

import android.os.Bundle;

/**
 * Created by kutear.guo on 2015/8/13.
 */
public class PreviewFragment extends BaseFragment {

    public static PreviewFragment newInstance() {
        Bundle args = new Bundle();
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String str1 = "";
        String str2 = "###Title";
        //for (str1 = str1 + "<html><head><style type=\"text/css\">html,body{padding:4px 8px 4px 8px;font-family:'sans-serif-light';color:#ffffff;background-color:#303030}h1,h2,h3,h4,h5,h6{font-family:'sans-serif'condensed';}a{color:#0099ff;text-decoration:none;border:0;}}</style></head><body>"; ; str1 = str1 + "<html><head><style type=\"text/css\">html,body{padding:4px 8px 4px 8px;font-family:'sans-serif-light';color:#303030}h1,h2,h3,h4,h5,h6{font-family:'sans-serif'condensed';}a{color:#0099ff;text-decoration:none;border:0;}}</style></head><body>")
        super.onCreate(savedInstanceState);
    }
}
