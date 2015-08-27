package com.kutear.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.adapter.ManagerCategoryAdapter;
import com.kutear.app.api.ApiCategoryManager;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.ManagerCategory;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by kutear.guo on 2015/8/24.
 * 分类管理页面
 */
public class ManagerCategoryFragment extends BaseNoBarFragment implements IGetListCallBack, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private static final String TAG = ManagerCategoryFragment.class.getSimpleName();
    private ListView mLvLists;
    private TextView mBack;
    private ManagerCategoryAdapter mAdapter;
    private List<ManagerCategory> mLists = new ArrayList<>();
    private Stack<Integer> urlStack = new Stack<>();  //只是存放parent的ID

    public static ManagerCategoryFragment newInstance() {
        Bundle args = new Bundle();
        ManagerCategoryFragment fragment = new ManagerCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {
        mLvLists = (ListView) v.findViewById(R.id.manager_category_list);
        mBack = (TextView) v.findViewById(R.id.manager_category_back);
        mBack.setOnClickListener(this);
        urlStack.clear();
        urlStack.add(0);
        ApiCategoryManager.getCategory(urlStack.peek(), this);
        mAdapter = new ManagerCategoryAdapter(mLists, mActivity);
        mLvLists.setAdapter(mAdapter);
        mLvLists.setOnItemClickListener(this);
        mLvLists.setOnItemLongClickListener(this);
        showBack();
    }

    private void showBack() {
        if (urlStack.size() == 1) {
            mBack.setVisibility(View.GONE);
        } else {
            mBack.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected View setContentView() {
        View v = inflate(R.layout.fragment_manager_category);
        initView(v);
        return v;
    }

    @Override
    public void onSuccess(List<? extends BaseBean> lists) {
        mLists.clear();
        mLists.addAll((ArrayList<ManagerCategory>) lists);
        mAdapter.notifyDataSetChanged();
        hiddenLoadingLayout();
        hiddenLoad();
        showBack();
    }

    @Override
    public void onError(String msg) {
        showSnack(mLvLists, msg);
        hiddenLoad();
        if (urlStack.size() > 1) {
            urlStack.pop();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        urlStack.add(mLists.get(position).getIndex());
        ApiCategoryManager.getCategory(urlStack.peek(), this);
        showLoading("等待中..");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        KDialogFragment.showListDialog(getFragmentManager(), mActivity.getResources().getStringArray(R.array.category_option), mDialogListener);
        clickPosition = position;
        return false;
    }

    private int clickPosition;
    private AdapterView.OnItemClickListener mDialogListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //隐藏ListDialog
            KDialogFragment.hiddenDialog(getFragmentManager());
            switch (position) {
                case 0://删除
                    deleteCategory();
                    break;
                case 1://修改
                    modifyCategory();
                    break;
                case 2://取消
                    break;
                default:
                    break;
            }
        }
    };

    private void deleteCategory() {
        showLoading("删除中");
        ApiCategoryManager.deteleCategory(mLists.get(clickPosition), this);
    }

    private void modifyCategory() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ManagerCategoryMdFragment.KEY, mLists.get(clickPosition));
        bundle.putInt(ManagerCategoryMdFragment.PARENT, urlStack.peek());
        AppApplication.startActivity(mActivity, Constant.ACTIVITY_CATEGORY_MD, bundle);
    }

    private void addCategory() {

    }

    @Override
    public void onPostError(String msg) {
        super.onPostError(msg);
        showSnack(mLvLists, msg);
    }

    @Override
    public void onPostSuccess(String msg) {
        L.v(TAG, "msg=" + msg);
        super.onPostSuccess(msg);
        mLists.remove(clickPosition);
        mAdapter.notifyDataSetChanged();
        showSnack(mLvLists, msg);
    }

    @Override
    public void onClick(View v) {
        urlStack.pop();
        ApiCategoryManager.getCategory(urlStack.peek(), this);
        showLoading("等待中");
    }
}
