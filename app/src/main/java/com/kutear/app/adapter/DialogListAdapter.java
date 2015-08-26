package com.kutear.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kutear.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/26.
 * dialog中的listView使用的适配器
 */
public class DialogListAdapter extends BaseAdapter {
    private Context mContext;
    private int[] iconList;
    private String[] stringList;

    public DialogListAdapter(Context context, int[] icons, String[] strings) {
        this.iconList = icons;
        this.stringList = strings;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return stringList.length;
    }

    @Override
    public Object getItem(int position) {
        return stringList[position];
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
            holder.mDivider = convertView.findViewById(R.id.dialog_list_divider);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindData(iconList, stringList, position);
        return convertView;
    }

    private static class ViewHolder {
        public TextView mTitle;
        public ImageView mIcon;
        public View mDivider;

        public void bindData(int[] icons, String[] strings, int position) {
            if (icons != null && icons.length > position) {
                mIcon.setImageResource(icons[position]);
            } else {
                mIcon.setVisibility(View.GONE);
            }
            if (strings != null && strings.length > position) {
                mTitle.setText(strings[position]);
                if (position == strings.length - 1) {
                    mDivider.setVisibility(View.GONE);
                } else {
                    mDivider.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
