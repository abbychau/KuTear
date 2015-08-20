package com.kutear.app.bean;

/**
 * Created by kutear.guo on 2015/8/19.
 */
public class Tab extends BaseBean {
    private String name;
    private String url;
    private int count;

    public Tab() {
    }

    @Override
    public String toString() {
        return "Tab{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", count=" + count +
                '}';
    }

    public Tab(String name, String url, int count) {
        this.name = name;
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
