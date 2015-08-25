package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/20.
 */
public class UserInfo extends BaseBean {
    private String nickName;
    private String avater;
    private String mainPager;
    private String eMail;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getMainPager() {
        return mainPager;
    }

    public void setMainPager(String mainPager) {
        this.mainPager = mainPager;
    }

    public String getEMail() {
        return eMail;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", avater='" + avater + '\'' +
                ", mainPager='" + mainPager + '\'' +
                ", eMail='" + eMail + '\'' +
                '}';
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public UserInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickName);
        dest.writeString(this.avater);
        dest.writeString(this.mainPager);
        dest.writeString(this.eMail);
    }

    protected UserInfo(Parcel in) {
        this.nickName = in.readString();
        this.avater = in.readString();
        this.mainPager = in.readString();
        this.eMail = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
