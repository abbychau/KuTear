package com.kutear.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kutear.guo on 2015/8/16.
 */
public class BaseBean implements Parcelable {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
        this.id = in.readInt();
    }

}
