package com.kutear.app.bean;

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

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
}
