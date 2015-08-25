package com.kutear.app.api;

import com.kutear.app.bean.Article;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by kutear.guo on 2015/8/18.
 * 获取文章详情的接口
 */
public class ApiArticleDetails extends BaseRequest {
    public static void getArticleDetails(String url, final IGetCallBack callBack) {
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
                    callBack.onGetError(str);
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
    private static void parseArticle(Element element, IGetCallBack callBack) {
        if (element == null) {
            callBack.onGetError("Sorry,文章解析出错");
            return;
        }
        Article article = new Article();
        article.setDetail(element.html());
        callBack.onGetSuccess(article);
    }
}
