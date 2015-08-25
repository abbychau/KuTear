package com.kutear.app.bean;

/**
 * Created by kutear.guo on 2015/8/23.
 */
public class ReadInfo extends BaseBean {
    private String dateFormat;
    private int listsCount;
    private int pagerCount;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getListsCount() {
        return listsCount;
    }

    public void setListsCount(int listsCount) {
        this.listsCount = listsCount;
    }

    public int getPagerCount() {
        return pagerCount;
    }

    public void setPagerCount(int pagerCount) {
        this.pagerCount = pagerCount;
    }
}
