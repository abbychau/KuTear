package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear on 15-9-12.
 * * title=Android Desigin Library&slug=Android_Desigin_Library&
 * text=# Android Desigin Library
 * fieldNames[]=&
 * fieldTypes[]=str&
 * fieldValues[]=&
 * cid=43&
 * do=publish&
 * markdown=1&
 * date=2015-09-05 16:21&
 * category[]=4&
 * category[]=14&
 * tags=android,desigin&
 * visibility=publish&
 * password=&
 * allowComment=1&
 * allowPing=1&
 * allowFeed=1&
 * trackback=
 */
public class ManagerArticleDetails extends BaseBean {
    private String title;
    private String content;
    private String fieldNames;
    private String fieldTypes;
    private String cid;
    private String doAction;
    private String markdown;
    private String date;
    private String category;
    private String tags;
    private String visibility;
    private String password;
    private String allowComment;
    private String allowPing;
    private String allowFeed;
    private String trackback;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }

    public String getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(String fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDoAction() {
        return doAction;
    }

    public void setDoAction(String doAction) {
        this.doAction = doAction;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public String getAllowPing() {
        return allowPing;
    }

    public void setAllowPing(String allowPing) {
        this.allowPing = allowPing;
    }

    public String getAllowFeed() {
        return allowFeed;
    }

    public void setAllowFeed(String allowFeed) {
        this.allowFeed = allowFeed;
    }

    public String getTrackback() {
        return trackback;
    }

    public void setTrackback(String trackback) {
        this.trackback = trackback;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
