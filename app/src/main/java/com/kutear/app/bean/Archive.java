package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/19.
 */
public class Archive extends BaseBean{
    private String title;

    private String url;

    public Archive(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Archive{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected Archive(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Archive> CREATOR = new Creator<Archive>() {
        public Archive createFromParcel(Parcel source) {
            return new Archive(source);
        }

        public Archive[] newArray(int size) {
            return new Archive[size];
        }
    };
}
