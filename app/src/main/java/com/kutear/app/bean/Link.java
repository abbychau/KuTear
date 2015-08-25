package com.kutear.app.bean;

import android.os.Parcel;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.articlePath);
        dest.writeString(this.pagerPath);
        dest.writeString(this.categoryPath);
        dest.writeByte(isReWrite ? (byte) 1 : (byte) 0);
    }

    public Link() {
    }

    protected Link(Parcel in) {
        this.articlePath = in.readString();
        this.pagerPath = in.readString();
        this.categoryPath = in.readString();
        this.isReWrite = in.readByte() != 0;
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        public Link createFromParcel(Parcel source) {
            return new Link(source);
        }

        public Link[] newArray(int size) {
            return new Link[size];
        }
    };
}
