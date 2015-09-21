package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.bean.Pager;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by kutear on 15-8-28.
 * 页面管理
 */
public class ApiPagerManager extends ApiAdmin {
    private static final String TAG = ApiPagerManager.class.getSimpleName();

    public static void getPagerList(final IGetListCallBack callBack) {
        getRequest(Constant.URI_PAGER_MANAGER, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parsePager(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(str);
                }
            }
        });
    }

    /**
     * 数据解析 在回调中返回数据
     *
     * @param str      html
     * @param callBack 回调
     */
    private static void parsePager(String str, IGetListCallBack callBack) {
        if (TextUtils.isEmpty(str)) {
            if (callBack != null) {
                callBack.onError("获取独立页面失败");
            }
            return;
        }
        ArrayList<Pager> lists = new ArrayList<>();
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("tbody");
        if (elements != null) {
            for (Element item : elements.first().children()) {
                Pager pager = new Pager();
                //获取index
                Element indexElement = item.getElementsByAttributeValue("type", "checkbox") != null ?
                        item.getElementsByAttributeValue("type", "checkbox").first() : null;
                if (indexElement != null) {
                    pager.setIndex(Integer.parseInt(indexElement.attr("value")));
                }
                if (item.childNodeSize() > 3) {
                    //获取name
                    Element nameElement = item.child(2);
                    if (nameElement != null) {
                        Element linkElement = nameElement.child(0);
                        if (linkElement != null) {
                            pager.setName(linkElement.ownText());
                        }
                    }
                }
                if (item.childNodeSize() > 4) {
                    //获取缩写名字
                    Element simpleElement = item.child(3);
                    if (simpleElement != null) {
                        pager.setSimpleName(simpleElement.ownText());
                    }
                }

                if (item.childNodeSize() > 5) {
                    //获取作者名字
                    Element authorElement = item.child(4);
                    if (authorElement != null) {
                        pager.setAuthor(authorElement.ownText());
                    }
                }
                if (item.childNodeSize() > 6) {
                    //获取日期
                    Element dateElement = item.child(5);
                    if (dateElement != null) {
                        pager.setDate(dateElement.ownText());
                    }
                }
                lists.add(pager);
            }
        }
        if (callBack != null) {
            callBack.onSuccess(lists);
        }

    }
}
