package com.kutear.app.bean;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by kutear on 15-9-17.
 * 页面管理页面
 */
public class ManagerPagerDetails extends BaseBean {
    private String title = "";
    private String content = "";
    private String cid = "";
    private String doAction = "";
    private String markdown = "";
    private String date = "";
    private String visibility = "";
    private String allowComment = "";
    private String allowPing = "";
    private String allowFeed = "";
    private String slug = "";
    private String template = "";
    private String order = "";
    private ArrayList<ManagerArticleDetails.Field> customField;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<ManagerArticleDetails.Field> getCustomField() {
        return customField;
    }

    public void setCustomField(ArrayList<ManagerArticleDetails.Field> customField) {
        this.customField = customField;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public String toString() {
        return "ManagerPagerDetails{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", cid='" + cid + '\'' +
                ", doAction='" + doAction + '\'' +
                ", markdown='" + markdown + '\'' +
                ", date='" + date + '\'' +
                ", visibility='" + visibility + '\'' +
                ", allowComment='" + allowComment + '\'' +
                ", allowPing='" + allowPing + '\'' +
                ", allowFeed='" + allowFeed + '\'' +
                ", slug='" + slug + '\'' +
                ", template='" + template + '\'' +
                ", order='" + order + '\'' +
                ", customField=" + customField +
                '}';
    }
}
