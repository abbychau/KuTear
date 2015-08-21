package com.kutear.app.bean;

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
}
