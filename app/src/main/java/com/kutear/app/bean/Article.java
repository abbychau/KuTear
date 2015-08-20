package com.kutear.app.bean;

import android.os.Parcel;
import android.os.Parcelable;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.day);
        dest.writeString(this.time);
        dest.writeString(this.year);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected Article(Parcel in) {
        this.day = in.readString();
        this.time = in.readString();
        this.year = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
