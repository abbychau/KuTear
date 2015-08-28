package com.kutear.app.bean;

import android.os.Parcelable;

/**
 * Created by kutear.guo on 2015/8/16.
 * 所有bean的基类
 */
public abstract class BaseBean implements Parcelable {
    protected int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
