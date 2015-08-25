package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/19.
 */
public class Tab extends BaseBean {
    private String name;
    private String url;
    private int count;

    public Tab() {
    }

    @Override
    public String toString() {
        return "Tab{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", count=" + count +
                '}';
    }

    public Tab(String name, String url, int count) {
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

    protected Tab(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.count = in.readInt();
    }

    public static final Creator<Tab> CREATOR = new Creator<Tab>() {
        public Tab createFromParcel(Parcel source) {
            return new Tab(source);
        }

        public Tab[] newArray(int size) {
            return new Tab[size];
        }
    };
}
