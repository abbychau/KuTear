package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/19.
 * 归档分类 管理分类看{@link ManagerCategory}
 */
public class Category extends BaseBean {
    private String name;
    private String url;
    private int count;

    public Category() {
    }

    public Category(String name, String url, int count) {
        this.name = name;
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {

        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", count=" + count +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeInt(this.count);
    }

    protected Category(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
