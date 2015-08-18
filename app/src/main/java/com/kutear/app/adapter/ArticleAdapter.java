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
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> lists;

    public ArticleAdapter(List<Article> lists) {
        this.lists = lists;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(v);
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

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvContent;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.article_item_content);
            tvTitle = (TextView) itemView.findViewById(R.id.article_item_title);
        }

        public void bindData(Article article) {
            tvContent.setText(article.getContent());
            tvTitle.setText(article.getTitle());
        }
    }
}
