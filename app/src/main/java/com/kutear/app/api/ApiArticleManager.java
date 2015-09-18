package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.bean.ManagerArticle;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear on 15-8-28.
 * 获取文章列表
 */
public class ApiArticleManager extends ApiAdmin {
    private static final String TAG = ApiArticleManager.class.getSimpleName();

    /**
     * 获取指定页的文章
     *
     * @param pager    int 从0开始
     * @param callBack 回调
     */
    public static void getArticleList(int pager, final IGetListCallBack callBack) {
        getRequest(String.format(Constant.URI_ARTICLE_MANAGER, pager), new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseArticle(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(str);
                }
            }
        });
    }

    private static void parseArticle(String doc, IGetListCallBack callBacK) {
        if (TextUtils.isEmpty(doc)) {
            if (callBacK != null) {
                callBacK.onError("获取文章失败");
            }
            return;
        }
        Document document = Jsoup.parse(doc);
        Element tbodyElements = document.getElementsByTag("tbody").first();
        List<ManagerArticle> lists = new ArrayList<>();
        if (tbodyElements != null) {
            for (Element item : tbodyElements.children()) {
                ManagerArticle article = new ManagerArticle();
                //获取index
                Element indexElement = item.getElementsByAttributeValue("type", "checkbox") != null ?
                        item.getElementsByAttributeValue("type", "checkbox").first() : null;
                if (indexElement != null) {
                    try {
                        article.setIndex(Integer.parseInt(indexElement.attr("value")));
                    } catch (NumberFormatException e) {
                        article.setIndex(0);
                    }
                }
                if (item.childNodeSize() > 3) {
                    //获取name
                    Element nameElement = item.child(2);
                    if (nameElement != null) {
                        Element linkElement = nameElement.child(0);
                        if (linkElement != null) {
                            article.setName(linkElement.ownText());
                        }
                    }
                }
                if (item.childNodeSize() > 4) {
                    //获取作者名字
                    Element authorElement = item.child(3).child(0);
                    if (authorElement != null) {
                        article.setAuthor(authorElement.ownText());
                    }
                }

                if (item.childNodeSize() > 5 && item.child(4) != null) {
                    //获取分类名字
                    Element categoryElement = item.child(4).children().size() > 0 ?
                            item.child(4).child(0) : null;
                    if (categoryElement != null) {
                        article.setCategoryUrl(categoryElement.attr("href"));
                        article.setCategory(categoryElement.ownText());
                    }
                }
                if (item.childNodeSize() > 6) {
                    //获取日期
                    Element dateElement = item.child(5);
                    if (dateElement != null) {
                        article.setDate(dateElement.ownText());
                    }
                }
                lists.add(article);
            }
        }
        if (callBacK != null) {
            callBacK.onSuccess(lists);
        }
    }
}
