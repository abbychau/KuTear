package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.bean.Link;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.callback.IPostCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by kutear.guo on 2015/8/23.
 * 固定连接管理
 */
public class ApiLinkManager extends ApiAdmin {

    public static void getLink(final IGetCallBack callBack) {
        getRequest(Constant.URI_LINKS_SETTING, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseLink(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onGetError(str);
                }
            }
        });
    }

    private static void parseLink(String doc, IGetCallBack callBack) {
        Link link = new Link();
        Document document = Jsoup.parse(doc);
        Element reWriteElement = document.getElementById("rewrite-0");
        if (reWriteElement != null) {
            link.setIsReWrite(!TextUtils.equals(reWriteElement.attr("checked"), "true"));
        }
        Element articlePathDiv = document.getElementById("typecho-option-item-postPattern-1");
        if (articlePathDiv != null) {
            Elements articlePathElement = articlePathDiv.getElementsByAttributeValue("checked", "true");
            //获取id,通过Id判断是哪一个
            String value = articlePathElement.attr("value");
            if (TextUtils.equals(value, "custom")) {
                value = document.getElementsByAttributeValue("name", "customPattern").first().attr("value");
            }
            link.setArticlePath(value);
        }
        Element pageElement = document.getElementById("pagePattern-0-3");
        String pagelink = pageElement != null ? pageElement.attr("value") : "";
        link.setPagerPath(pagelink);
        Element categoryElement = document.getElementById("categoryPattern-0-4");
        String categorylink = categoryElement != null ? categoryElement.attr("value") : "";
        link.setCategoryPath(categorylink);
        if (callBack != null) {
            callBack.onGetSuccess(link);
        }

    }

    /**
     * rewrite=0 &
     * postPattern=%2Farchives%2F%5Bslug%5D.html &
     * customPattern= &
     * pagePattern=%2F%7Bslug%7D.html &
     * categoryPattern=%2Fcategory%2F%7Bslug%7D%2F
     */
    public static void postLink(final Link link, final IPostCallBack callBack) {
        //首先要获取post url
        getRequest(Constant.URI_LINKS_SETTING, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document document = Jsoup.parse(str);
                Elements elements = document.getElementsByTag("form");
                if (elements.first() != null) {
                    String url = elements.first().attr("action");
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("rewrite", link.isReWrite() ? "1" : "0");
                    params.put("postPattern", link.getArticlePath());
                    params.put("customPattern", "");
                    params.put("pagePattern", link.getPagerPath());
                    params.put("categoryPattern", link.getCategoryPath());


                    postRequest(url, params, new ICallBack() {
                        @Override
                        public void onSuccess(int statusCode, String str) {
                            if (callBack != null) {
                                callBack.onPostSuccess(str);
                            }
                        }

                        @Override
                        public void onError(int statusCode, String str) {
                            if (callBack != null) {
                                callBack.onPostError(str);
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostError(str);
                }
            }
        });
    }
}
