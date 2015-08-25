package com.kutear.app.bean;

/**
 * Created by kutear.guo on 2015/8/24.
 */
public class Link extends BaseBean {
    private String articlePath;
    private String pagerPath;
    private String categoryPath;
    private boolean isReWrite;

    public boolean isReWrite() {
        return isReWrite;
    }

    public void setIsReWrite(boolean isReWrite) {
        this.isReWrite = isReWrite;
    }

    public String getArticlePath() {
        return articlePath;
    }

    public void setArticlePath(String articlePath) {
        this.articlePath = articlePath;
    }

    public String getPagerPath() {
        return pagerPath;
    }

    public void setPagerPath(String pagerPath) {
        this.pagerPath = pagerPath;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }
}
