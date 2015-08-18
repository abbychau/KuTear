package com.kutear.app.bean;

import android.text.TextUtils;

/**
 * Created by kutear.guo on 2015/8/16.
 */
public class Article extends BaseBean {
    private String day;
    private String time;
    private String year;
    private String title;
    private String content;
    private String url;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param o Article
     * @return if equals return true;
     */
    public boolean equals(Article o) {
        return TextUtils.equals(o.getTime(), getTime()) && TextUtils.equals(o.getContent(), getContent()) && TextUtils.equals(o.getDay(), getDay()) &&
                TextUtils.equals(o.getTitle(), getTitle()) && TextUtils.equals(o.getUrl(), getUrl());
    }
}
