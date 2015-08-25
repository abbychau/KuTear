package com.kutear.app.api;


import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.ManagerCategory;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

/**
 * Created by kutear.guo on 2015/8/24.
 * 获取分类管理信息
 */
public class ApiCategoryManager extends ApiAdmin {
    private static final String TAG = ApiCategoryManager.class.getSimpleName();

    public static void getCategory(final IGetListCallBack callBack) {
        getRequest(Constant.URI_CATEGORY_MANAGER, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseCategory(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {

            }
        });
    }

    private static void parseCategory(String doc, IGetListCallBack callBack) {
        Document document = Jsoup.parse(doc);
        Element tableElement = document.getElementsByTag("tbody") != null ?
                document.getElementsByTag("tbody").first() : null;
        ArrayList<ManagerCategory> lists = new ArrayList<>();
        if (tableElement != null) {
            for (Element item : tableElement.children()) {
                ManagerCategory category = new ManagerCategory();
                //获取分类index
                Element indexElement = item.getElementsByTag("input") != null ? item.getElementsByTag("input").first() : null;
                if (indexElement != null) {
                    category.setIndex(Integer.parseInt(indexElement.attr("value")));
                }
                //获取分类名称和对应的链接
                Element nameElement = item.child(1);
                if (nameElement != null) {
                    Element alink = nameElement.child(0);
                    if (alink != null) {
                        category.setCategoryName(alink.ownText());
                        category.setCategoryUrl(alink.attr("href"));
                    }
                }
                //子分类的链接
                Element childElement = item.child(2);
                if (childElement != null) {
                    Element alink = childElement.child(0);
                    if (alink != null) {
                        category.setChildCategory(alink.attr("href"));
                    }
                }
                //简称
                Element simpleElement = item.child(3);
                if (simpleElement != null) {
                    category.setSimpleName(simpleElement.ownText());
                }
                //文章数
                Element articleElement = item.child(5);
                if (articleElement != null) {
                    try {
                        category.setArticleCount(Integer.parseInt(articleElement.text()));
                    } catch (Exception e) {
                        category.setArticleCount(0);
                    }
                }
                lists.add(category);
            }
        } else {
            if (callBack != null) {
                callBack.onError(AppApplication.getKString(R.string.get_category_error));
            }
        }

        if (lists.size() > 0 && callBack != null) {
            callBack.onSuccess(lists);
        }
    }



}
