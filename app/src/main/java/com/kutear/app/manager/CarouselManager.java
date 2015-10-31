package com.kutear.app.manager;

import com.kutear.app.bean.Carousel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear on 15-10-31.
 * 轮播图管理
 */
public class CarouselManager {
    private JSONArray mJsonArray;
    private ArrayList<Carousel> mList = new ArrayList<>();

    public void setJsonArray(JSONArray m) {
        this.mJsonArray = m;
        parse(mJsonArray);
    }

    private void parse(JSONArray mJsonArray) {
        if (mJsonArray != null) {
            for (int i = 0; i < mJsonArray.length(); i++) {
                try {
                    JSONObject mjo = mJsonArray.getJSONObject(i);
                    if(mjo.getInt("display") == 0){
                        continue;
                    }
                    Carousel mc = new Carousel();
                    mc.setId(mjo.getInt("id"));
                    mc.setUrl(mjo.getString("url"));
                    mc.setDisplay(mjo.getInt("display"));
                    mList.add(mc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 随机返回
     *
     * @return Carousel
     */
    public Carousel getRandomCarousel() {
        int index = 0;
        int total = mList.size();
        index = (int) (total * Math.random());
        return mList.get(index);
    }

    /**
     * 返回随机 URL
     * @return
     */
    public String getRandomUrl(){
        return getRandomCarousel().getUrl();
    }


}
