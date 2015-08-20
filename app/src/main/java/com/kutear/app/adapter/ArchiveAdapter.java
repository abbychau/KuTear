package com.kutear.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.api.ApiArchive;
import com.kutear.app.bean.Archive;
import com.kutear.app.bean.BaseBean;
import com.kutear.app.bean.Category;
import com.kutear.app.bean.Tab;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/19.
 * <p/>
 * 归档适配器
 */
public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder> {

    private ArticleAdapter.OnItemClickListener mListener;
    private List<? extends BaseBean> mlist;

    public ArchiveAdapter(List<? extends BaseBean> list, ArticleAdapter.OnItemClickListener listener) {
        this.mlist = list;
        this.mListener = listener;
    }


    @Override
    public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.archive_item, parent, false);
        return new ArchiveViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ArchiveViewHolder holder, int position) {
        BaseBean bean = mlist.get(position);
        if (bean instanceof Tab) {
            holder.textView.setText(((Tab) bean).getName());
        } else if (bean instanceof Archive) {
            holder.textView.setText(((Archive) bean).getTitle());
        } else if (bean instanceof Category) {
            holder.textView.setText(((Category) bean).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    /**
     * 归档ViewHolder
     */
    public class ArchiveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        private ArticleAdapter.OnItemClickListener mListener;

        public ArchiveViewHolder(View itemView, ArticleAdapter.OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = listener;
            textView = (TextView) itemView.findViewById(R.id.archive_title);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
