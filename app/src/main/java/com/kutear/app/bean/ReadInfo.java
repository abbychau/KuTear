package com.kutear.app.bean;

import android.os.Parcel;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateFormat);
        dest.writeInt(this.listsCount);
        dest.writeInt(this.pagerCount);
    }

    public ReadInfo() {
    }

    protected ReadInfo(Parcel in) {
        this.dateFormat = in.readString();
        this.listsCount = in.readInt();
        this.pagerCount = in.readInt();
    }

    public static final Creator<ReadInfo> CREATOR = new Creator<ReadInfo>() {
        public ReadInfo createFromParcel(Parcel source) {
            return new ReadInfo(source);
        }

        public ReadInfo[] newArray(int size) {
            return new ReadInfo[size];
        }
    };
}
