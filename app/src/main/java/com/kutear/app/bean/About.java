package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/23.
 */
public class About extends BaseBean {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }

    public About() {
    }

    protected About(Parcel in) {
        this.content = in.readString();
    }

    public static final Creator<About> CREATOR = new Creator<About>() {
        public About createFromParcel(Parcel source) {
            return new About(source);
        }

        public About[] newArray(int size) {
            return new About[size];
        }
    };
}
