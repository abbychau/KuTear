package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear on 15-8-28.
 * 文章列表
 **/
public class ManagerArticle extends BaseBean {
    private String name;
    private int index;
    private String author;
    private String category;
    private String categoryUrl;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.index);
        dest.writeString(this.author);
        dest.writeString(this.category);
        dest.writeString(this.categoryUrl);
        dest.writeString(this.date);
    }

    public ManagerArticle() {
    }

    protected ManagerArticle(Parcel in) {
        this.name = in.readString();
        this.index = in.readInt();
        this.author = in.readString();
        this.category = in.readString();
        this.categoryUrl = in.readString();
        this.date = in.readString();
    }

    public static final Creator<ManagerArticle> CREATOR = new Creator<ManagerArticle>() {
        public ManagerArticle createFromParcel(Parcel source) {
            return new ManagerArticle(source);
        }

        public ManagerArticle[] newArray(int size) {
            return new ManagerArticle[size];
        }
    };

    @Override
    public String toString() {
        return "ManagerArticle{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
