package com.kutear.app.bean;

import android.os.Parcel;

/**
 * Created by kutear.guo on 2015/8/24.
 */
public class ManagerCategory extends BaseBean {
    private int index;
    private String categoryName;
    private String categoryUrl;
    private String childCategory;
    private String simpleName;
    private int articleCount;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    @Override
    public String toString() {
        return "ManagerCategory{" +
                "index=" + index +
                ", categoryName='" + categoryName + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", childCategory='" + childCategory + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", articleCount=" + articleCount +
                '}';
    }

    public ManagerCategory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryUrl);
        dest.writeString(this.childCategory);
        dest.writeString(this.simpleName);
        dest.writeInt(this.articleCount);
    }

    protected ManagerCategory(Parcel in) {
        this.index = in.readInt();
        this.categoryName = in.readString();
        this.categoryUrl = in.readString();
        this.childCategory = in.readString();
        this.simpleName = in.readString();
        this.articleCount = in.readInt();
    }

    public static final Creator<ManagerCategory> CREATOR = new Creator<ManagerCategory>() {
        public ManagerCategory createFromParcel(Parcel source) {
            return new ManagerCategory(source);
        }

        public ManagerCategory[] newArray(int size) {
            return new ManagerCategory[size];
        }
    };
}
