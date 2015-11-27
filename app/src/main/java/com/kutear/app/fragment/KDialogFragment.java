package com.kutear.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.adapter.DialogListAdapter;
import com.kutear.app.utils.DeviceInfo;

/**
 * Created by kutear.guo on 2015/8/16.
 * 所有的对话框均使用该类
 */
public class KDialogFragment extends DialogFragment {
    public static final String TAG = KDialogFragment.class.getSimpleName();
    public static final String DIALOG_TYPE = "type";
    public static final String MSG_KEY = "msg";
    public static final String LIST_KEY = "list";
    public static final String DIALOG_TITLE = "title";
    public static final int DIALOG_LOADING = 0x0000;  //Loading
    public static final int DIALOG_LIST = 0x0001;    //显示列表
    public static final int DIALOG_MSG_ONE = 0x0002;    //含一个按钮的Dialog
    public static final int DIALOG_EDIT_TEXT = 0x0003; //含EditText的对话框

    private int mDialogType;
    private View mBodyView;
    private LinearLayout mLoadLayout;
    private LinearLayout mListLayout;
    private LinearLayout mMsgLayout;
    private LinearLayout mButtonLayout;
    private LinearLayout mButtonOneLayout;
    private LinearLayout mButtonTwoLayout;
    private LinearLayout mEditTextLayout;
    private RelativeLayout mTitleLayout;
    private TextView mTitleText;
    private TextView mBtnOk;
    private TextView mBtnSingle;
    private TextView mBtnCancle;
    private TextView mLoadText;
    private TextView mMsgView;
    private ListView mListView;
    private EditText mEditText;
    private Bundle mBundle;
    private Activity mActivity;
    private AdapterView.OnItemClickListener mListener;
    private View.OnClickListener mSingleListener;
    private View.OnClickListener mOkListener;
    private IEditTextCallBack mIEditTextCallBack;

    public interface IEditTextCallBack {
        void onTextInput(String str);
    }

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

    /**
     * 显示加载对话框
     *
     * @param manager
     * @param msg
     */
    public static void showLoadingDialog(FragmentManager manager, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(MSG_KEY, msg);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_LOADING);
        dialogFragment.showLoading(msg);
        dialogFragment.show(manager, TAG);
    }

    /**
     * 显示列表对话框
     *
     * @param manager
     * @param lists
     * @param listener
     */
    public static void showListDialog(FragmentManager manager, String[] lists, AdapterView.OnItemClickListener listener) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LIST_KEY, lists);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_LIST);
        dialogFragment.setOnItemClickListener(listener);
        dialogFragment.show(manager, TAG);
    }


    /**
     * 显示含一个确定按钮的Msg对话框
     *
     * @param manager
     * @param msg
     * @param okListener
     */
    public static void showMsgOne(FragmentManager manager, String msg, View.OnClickListener okListener) {
        Bundle bundle = new Bundle();
        bundle.putString(MSG_KEY, msg);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_MSG_ONE);
        dialogFragment.setSingleListener(okListener);
        dialogFragment.show(manager, TAG);
    }

    /**
     * 显示含EditText的对话框
     *
     * @param manager
     * @param title      对话框标题
     * @param msg        EditText 默认
     * @param mIEditTextCallBack
     */
    public static void showEditText(FragmentManager manager, String title, String msg, IEditTextCallBack mIEditTextCallBack) {
        Bundle bundle = new Bundle();
        bundle.putString(MSG_KEY, msg);
        bundle.putString(DIALOG_TITLE, title);
        KDialogFragment dialogFragment = newInstance(bundle, DIALOG_EDIT_TEXT);
        dialogFragment.setIEditTextCallBack(mIEditTextCallBack);
        dialogFragment.show(manager, TAG);
    }

    public void setIEditTextCallBack(IEditTextCallBack mIEditTextCallBack) {
        this.mIEditTextCallBack = mIEditTextCallBack;
    }

    private void setOkOnClickListener(View.OnClickListener listener) {
        this.mOkListener = listener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
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

    @Override
    public void onResume() {
        super.onResume();
        //除了加载对话框外，其余的对话框宽度均为宽度的90%
        if (mDialogType != DIALOG_LOADING) {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = (int) (DeviceInfo.getScreenWidth(mActivity) * 0.9);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
    }

    private void showView() {
        String msg = mBundle.getString(MSG_KEY);
        switch (mDialogType) {
            case DIALOG_LIST:
                String[] lists = mBundle.getStringArray(LIST_KEY);
                BaseAdapter adapter = new DialogListAdapter(mActivity, null, lists);
                showList(adapter, mListener);
                break;
            case DIALOG_LOADING:
                showLoading(msg);
                break;
            case DIALOG_MSG_ONE:
                showMsgWithOneBtn(msg);
                break;
            case DIALOG_EDIT_TEXT:
                showEditText();
                break;
        }
    }

    /**
     * 显示EditText对话框
     */
    private void showEditText() {
        mTitleLayout.setVisibility(View.VISIBLE);
        mTitleText.setText(getArguments().getString(DIALOG_TITLE));
        mButtonLayout.setVisibility(View.VISIBLE);
        mEditTextLayout.setVisibility(View.VISIBLE);
        mEditText.setText(getArguments().getString(MSG_KEY));
        mButtonTwoLayout.setVisibility(View.VISIBLE);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIEditTextCallBack != null) {
                    mIEditTextCallBack.onTextInput(mEditText.getText().toString().trim());
                    hiddenDialog(getFragmentManager());
                }
            }
        });
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenDialog(getFragmentManager());
            }
        });
    }

    /**
     * 显示致函有一个按钮的MSG dialog
     *
     * @param msg
     */
    private void showMsgWithOneBtn(String msg) {
        if (mMsgLayout != null) {
            mMsgLayout.setVisibility(View.VISIBLE);
        }
        if (mMsgView != null) {
            mMsgView.setText(msg);
        }

        if (mButtonLayout != null) {
            mButtonLayout.setVisibility(View.VISIBLE);
        }
        if (mButtonOneLayout != null) {
            mButtonOneLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setSingleListener(View.OnClickListener listener) {
        this.mSingleListener = listener;
    }

    private void initView() {
        if (mBodyView != null) {
            mTitleLayout = (RelativeLayout) mBodyView.findViewById(R.id.dialog_header);
            mTitleText = (TextView) mBodyView.findViewById(R.id.dialog_header_title);
            mLoadLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_loading);
            mLoadText = (TextView) mBodyView.findViewById(R.id.dialog_loading_text);
            mListView = (ListView) mBodyView.findViewById(R.id.dialog_list);
            mListLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_list_layout);
            mMsgLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_msg_layout);
            mMsgView = (TextView) mBodyView.findViewById(R.id.dialog_msg_text);
            mButtonLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_bottom_layout);
            mButtonOneLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_bottom_single_layout);
            mButtonTwoLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_bottom_double_layout);
            mBtnCancle = (TextView) mBodyView.findViewById(R.id.dialog_bottom_cancle);
            mBtnOk = (TextView) mBodyView.findViewById(R.id.dialog_bottom_ok);
            mBtnSingle = (TextView) mBodyView.findViewById(R.id.dialog_bottom_single);
            mEditText = (EditText) mBodyView.findViewById(R.id.dialog_edit_text);
            mEditTextLayout = (LinearLayout) mBodyView.findViewById(R.id.dialog_edit_text_layout);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}
