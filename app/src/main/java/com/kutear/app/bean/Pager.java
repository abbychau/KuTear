package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear on 15-8-28.
 * 独立页面
 */
public class Pager extends BaseBean {
    private int index;
    private String name;
    private String simpleName;
    private String author;
    private String date;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        dest.writeInt(this.index);
        dest.writeString(this.name);
        dest.writeString(this.simpleName);
        dest.writeString(this.author);
        dest.writeString(this.date);
    }

    public Pager() {
    }

    protected Pager(Parcel in) {
        this.index = in.readInt();
        this.name = in.readString();
        this.simpleName = in.readString();
        this.author = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Pager> CREATOR = new Creator<Pager>() {
        public Pager createFromParcel(Parcel source) {
            return new Pager(source);
        }

        public Pager[] newArray(int size) {
            return new Pager[size];
        }
    };

    @Override
    public String toString() {
        return "Pager{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
