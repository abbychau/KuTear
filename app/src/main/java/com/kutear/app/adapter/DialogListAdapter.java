package com.kutear.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kutear.app.R;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/26.
 * dialog中的listView使用的适配器
 */
public class DialogListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Integer> iconList;
    private List<String> stringList;

    public DialogListAdapter(Context context, List<Integer> icons, List<String> strings) {
        this.iconList = icons;
        this.stringList = strings;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_item, null);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.dialog_list_item_icon);
            holder.mTitle = (TextView) convertView.findViewById(R.id.dialog_list_item_text);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindData(iconList.get(position), stringList.get(position));
        return null;
    }

    private static class ViewHolder {
        public TextView mTitle;
        public ImageView mIcon;

        public void bindData(int icon, String msg) {
            mTitle.setText(msg);
            if (icon == 0) {
                mIcon.setVisibility(View.GONE);
            } else {
                mIcon.setImageResource(icon);
            }
        }
    }
}
