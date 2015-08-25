package com.kutear.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kutear.app.R;
import com.kutear.app.bean.Article;

import java.util.List;

/**
 * Created by kutear.guo on 2015/8/16.
 * 文章列表适配器
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> lists;
    private OnItemClickListener mListener;

    public ArticleAdapter(List<Article> lists, OnItemClickListener mListener) {
        this.lists = lists;
        this.mListener = mListener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(v, this.mListener);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = lists.get(position);
        holder.bindData(article);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public TextView tvContent;
        private OnItemClickListener mListener;

        public ArticleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = listener;
            tvContent = (TextView) itemView.findViewById(R.id.article_item_content);
            tvTitle = (TextView) itemView.findViewById(R.id.article_item_title);
        }

        public void bindData(Article article) {
            tvContent.setText(article.getContent());
            tvTitle.setText(article.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }
}
