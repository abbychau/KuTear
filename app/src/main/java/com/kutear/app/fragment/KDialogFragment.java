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

/**
 * Created by kutear.guo on 2015/8/16.
 * 所有的对话框均使用该类
 */
public class KDialogFragment extends DialogFragment {
    public static final String TAG = KDialogFragment.class.getSimpleName();
    public static final String DIALOG_TYPE = "type";
    public static final int DIALOG_LOADING = 0x0000;
    public static final int DIALOG_LIST = 0x0001;    //显示列表
    private int mDialogType;
    private View mBodyView;
    private LinearLayout mLoadLayout;
    private LinearLayout mListLayout;
    private TextView mLoadText;
    private ListView mListView;
    private static Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    private static KDialogFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(DIALOG_TYPE, type);
        KDialogFragment fragment = new KDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void showDialog(FragmentManager manager, String msg) {
        KDialogFragment dialogFragment = newInstance(DIALOG_LOADING);
        dialogFragment.showLoading(msg);
        dialogFragment.show(manager, TAG);
    }

    public static void showListDialog(FragmentManager manager, ArrayList<String> lists, AdapterView.OnItemClickListener listener) {
        KDialogFragment dialogFragment = newInstance(DIALOG_LIST);
        BaseAdapter adapter = new DialogListAdapter(mActivity, null, lists);
        dialogFragment.showList(adapter, listener);
        dialogFragment.show(manager,TAG);
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
        Bundle bundle = getArguments();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogType = bundle.getInt(DIALOG_TYPE);
        if (mDialogType == DIALOG_LOADING) {
            getDialog().setCanceledOnTouchOutside(false);
        }
        mBodyView = inflater.inflate(R.layout.fragment_dialog, container, false);
        initView();
        return mBodyView;
    }

    private void initView() {
        if (mBodyView != null) {
            mLoadLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_loading);
            mLoadText = (TextView) mBodyView.findViewById(R.id.dialog_loading_text);
            mListView = (ListView) mBodyView.findViewById(R.id.dialog_list);
            mListLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_list_layout);
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
