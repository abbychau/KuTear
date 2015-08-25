package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/21.
 */
public class SiteInfo extends BaseBean {
    private String siteName;
    private String siteDescription;
    private String siteKeyWord;
    private String siteAddress;
    private String sitePostUrl;

    public String getSitePostUrl() {
        return sitePostUrl;
    }

    public void setSitePostUrl(String sitePostUrl) {
        this.sitePostUrl = sitePostUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteKeyWord() {
        return siteKeyWord;
    }

    public void setSiteKeyWord(String siteKeyWord) {
        this.siteKeyWord = siteKeyWord;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.siteName);
        dest.writeString(this.siteDescription);
        dest.writeString(this.siteKeyWord);
        dest.writeString(this.siteAddress);
        dest.writeString(this.sitePostUrl);
    }

    public SiteInfo() {
    }

    protected SiteInfo(Parcel in) {
        this.siteName = in.readString();
        this.siteDescription = in.readString();
        this.siteKeyWord = in.readString();
        this.siteAddress = in.readString();
        this.sitePostUrl = in.readString();
    }

    public static final Creator<SiteInfo> CREATOR = new Creator<SiteInfo>() {
        public SiteInfo createFromParcel(Parcel source) {
            return new SiteInfo(source);
        }

        public SiteInfo[] newArray(int size) {
            return new SiteInfo[size];
        }
    };
}
