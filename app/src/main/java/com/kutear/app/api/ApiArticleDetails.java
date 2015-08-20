package com.kutear.app.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by kutear.guo on 2015/8/18.
 * 获取文章详情的接口
 */
public class ApiArticleDetails extends BaseRequest {
    public static void getArticleDetails(String url, final IArticleDetails callBack) {
        getRequest(url, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document document = Jsoup.parse(str);
                Elements elements = document.getElementsByClass("con");
                parseArticle(elements.first(), callBack);
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
     * 解析文章
     *
     * @param element  文章内容
     * @param callBack 回调
     */
    private static void parseArticle(Element element, IArticleDetails callBack) {
        if (element == null) {
            callBack.onError("Sorry,文章解析出错");
            return;
        }
        callBack.onSuccess(element.html());
    }

    public interface IArticleDetails {
        void onSuccess(String str);

        void onError(String str);
    }
}
