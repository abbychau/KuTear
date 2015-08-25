package com.kutear.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.bean.ManagerCategory;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/24.
 * 适配器
 */
public class ManagerCategoryAdapter extends BaseAdapter {
    private List<ManagerCategory> lists;
    private Context mContent;

    public ManagerCategoryAdapter(List<ManagerCategory> lists, Context context) {
        this.lists = lists;
        this.mContent = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContent, R.layout.category_list_item, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.manager_category_item_title);
            holder.mSimpleTitle = (TextView) convertView.findViewById(R.id.manager_category_item_simple_title);
            holder.line = convertView.findViewById(R.id.manager_category_item_line);
            holder.mCount = (TextView) convertView.findViewById(R.id.manager_category_item_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.bindData(lists.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView mTitle;
        public TextView mSimpleTitle;
        public View line;
        public TextView mCount;

        public void bindData(ManagerCategory category) {
            mTitle.setText(category.getCategoryName());
            mSimpleTitle.setText(category.getSimpleName());
            mCount.setText(category.getArticleCount() + "");
        }
    }
}
