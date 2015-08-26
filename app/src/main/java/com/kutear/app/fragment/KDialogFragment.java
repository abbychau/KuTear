package com.kutear.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.adapter.DialogListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/16.
 * 所有的对话框均使用该类
 */
public class KDialogFragment extends DialogFragment {
    public static final String TAG = KDialogFragment.class.getSimpleName();
    public static final String DIALOG_TYPE = "type";
    public static final String MSG_KEY = "msg";
    public static final String LIST_KEY = "list";
    public static final int DIALOG_LOADING = 0x0000;
    public static final int DIALOG_LIST = 0x0001;    //显示列表
    private int mDialogType;
    private View mBodyView;
    private LinearLayout mLoadLayout;
    private LinearLayout mListLayout;
    private TextView mLoadText;
    private ListView mListView;
    private Bundle mBundle;
    private static Activity mActivity;
    private AdapterView.OnItemClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    private static KDialogFragment newInstance(Bundle args, int type) {
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(DIALOG_TYPE, type);
        KDialogFragment fragment = new KDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void showDialog(FragmentManager manager, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(MSG_KEY, msg);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_LOADING);
        dialogFragment.showLoading(msg);
        dialogFragment.show(manager, TAG);
    }

    public static void showListDialog(FragmentManager manager, String[] lists, AdapterView.OnItemClickListener listener) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LIST_KEY, lists);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_LIST);
        dialogFragment.setOnItemClickListener(listener);
        dialogFragment.show(manager, TAG);
    }

    public static void hiddenDialog(FragmentManager manager) {
        if (manager == null) {
            return;
        }
        KDialogFragment dialogFragment = (KDialogFragment) manager.findFragmentByTag(TAG);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBundle = getArguments();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogType = mBundle.getInt(DIALOG_TYPE);
        if (mDialogType == DIALOG_LOADING) {
            getDialog().setCanceledOnTouchOutside(false);
        }
        mBodyView = inflater.inflate(R.layout.fragment_dialog, container, false);
        initView();
        showView();
        return mBodyView;
    }

    private void showView() {
        switch (mDialogType) {
            case DIALOG_LIST:
                String[] lists = mBundle.getStringArray(LIST_KEY);
                BaseAdapter adapter = new DialogListAdapter(mActivity, null, lists);
                showList(adapter, mListener);
                break;
            case DIALOG_LOADING:
                String msg = mBundle.getString(MSG_KEY);
                showLoading(msg);
                break;
        }
    }

    private void initView() {
        if (mBodyView != null) {
            mLoadLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_loading);
            mLoadText = (TextView) mBodyView.findViewById(R.id.dialog_loading_text);
            mListView = (ListView) mBodyView.findViewById(R.id.dialog_list);
            mListLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_list_layout);
        }
    }

    private void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener;
        if (mListView != null) {
            mListView.setOnItemClickListener(mListener);
        }
    }

    /**
     * 控制Load对话框的显示
     *
     * @param msg msg为空显示缺省"等待中"
     */
    private void showLoading(String msg) {
        if (mLoadLayout != null) {
            mLoadLayout.setVisibility(View.VISIBLE);
        }
        if (mLoadText != null && !TextUtils.isEmpty(msg)) {
            mLoadText.setText(msg);
        }
    }

    private void showList(BaseAdapter adapter, AdapterView.OnItemClickListener listener) {
        if (mListLayout != null) {
            mListLayout.setVisibility(View.VISIBLE);
        }
        if (mListView != null) {
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(listener);
        }
    }


}
