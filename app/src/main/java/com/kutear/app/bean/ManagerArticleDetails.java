package com.kutear.app.bean;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by kutear on 15-9-12.
 * * title=Android Desigin Library&
 * slug=Android_Desigin_Library&
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
    private String title = "";
    private String content = "";
    private String cid = "";
    private String doAction = "";
    private String markdown = "";
    private String date = "";
    private ArrayList<ManagerCategory> category;
    private String tags = "";
    private String visibility = "";
    private String password = "";
    private String allowComment = "";
    private String allowPing = "";
    private String allowFeed = "";
    private String trackback = "";
    private String slug = "";
    private ArrayList<Field> customField;

    //自定义的字段名字
    public static class Field extends BaseBean {
        private String filedName = "";
        private String filedValue = "";
        private String filedType = "";

        public String getFiledName() {
            return filedName;
        }

        public void setFiledName(String filedName) {
            this.filedName = filedName;
        }

        public String getFiledValue() {
            return filedValue;
        }

        public void setFiledValue(String filedValue) {
            this.filedValue = filedValue;
        }

        public String getFiledType() {
            return filedType;
        }

        public void setFiledType(String filedType) {
            this.filedType = filedType;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "filedName='" + filedName + '\'' +
                    ", filedValue='" + filedValue + '\'' +
                    ", filedType='" + filedType + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.filedName);
            dest.writeString(this.filedValue);
            dest.writeString(this.filedType);
        }

        public Field() {
        }

        protected Field(Parcel in) {
            this.filedName = in.readString();
            this.filedValue = in.readString();
            this.filedType = in.readString();
        }

        public static final Creator<Field> CREATOR = new Creator<Field>() {
            public Field createFromParcel(Parcel source) {
                return new Field(source);
            }

            public Field[] newArray(int size) {
                return new Field[size];
            }
        };
    }

    public ArrayList<Field> getCustomField() {
        return customField;
    }

    public void setCustomField(ArrayList<Field> customField) {
        this.customField = customField;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public ArrayList<ManagerCategory> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<ManagerCategory> category) {
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

    /**
     * 密码长度<=16
     *
     * @param password
     */
    public void setPassword(String password) {
        if (password == null || password.length() > 16) {
            System.err.println("Article password length must <=16");
            return;
        }
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
    public String toString() {
        return "ManagerArticleDetails{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", cid='" + cid + '\'' +
                ", doAction='" + doAction + '\'' +
                ", markdown='" + markdown + '\'' +
                ", date='" + date + '\'' +
                ", category=" + category +
                ", tags='" + tags + '\'' +
                ", visibility='" + visibility + '\'' +
                ", password='" + password + '\'' +
                ", allowComment='" + allowComment + '\'' +
                ", allowPing='" + allowPing + '\'' +
                ", allowFeed='" + allowFeed + '\'' +
                ", trackback='" + trackback + '\'' +
                ", slug='" + slug + '\'' +
                ", customField=" + customField +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.cid);
        dest.writeString(this.doAction);
        dest.writeString(this.markdown);
        dest.writeString(this.date);
        dest.writeTypedList(category);
        dest.writeString(this.tags);
        dest.writeString(this.visibility);
        dest.writeString(this.password);
        dest.writeString(this.allowComment);
        dest.writeString(this.allowPing);
        dest.writeString(this.allowFeed);
        dest.writeString(this.trackback);
        dest.writeString(this.slug);
        dest.writeTypedList(customField);
    }

    public ManagerArticleDetails() {
    }

    protected ManagerArticleDetails(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.cid = in.readString();
        this.doAction = in.readString();
        this.markdown = in.readString();
        this.date = in.readString();
        this.category = in.createTypedArrayList(ManagerCategory.CREATOR);
        this.tags = in.readString();
        this.visibility = in.readString();
        this.password = in.readString();
        this.allowComment = in.readString();
        this.allowPing = in.readString();
        this.allowFeed = in.readString();
        this.trackback = in.readString();
        this.slug = in.readString();
        this.customField = in.createTypedArrayList(Field.CREATOR);
    }

    public static final Creator<ManagerArticleDetails> CREATOR = new Creator<ManagerArticleDetails>() {
        public ManagerArticleDetails createFromParcel(Parcel source) {
            return new ManagerArticleDetails(source);
        }

        public ManagerArticleDetails[] newArray(int size) {
            return new ManagerArticleDetails[size];
        }
    };
}
