package com.kutear.app.api;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.Article;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by kutear.guo on 2015/8/16.
 * 获取文章列表
 */
public class ApiArticleList extends BaseRequest {

    private static final String TAG = ApiArticleList.class.getSimpleName();


    /**
     * @param pager    当前的页码
     * @param callBack 回调
     */
    public static void getArticle(int pager, final IGetListCallBack callBack) {
        getArticle(Constant.URI_ARTICLE, pager, callBack);
    }


    public static void getArticle(String url, int pager, final IGetListCallBack callBack) {
        // TODO: 2015/8/25 对于pager的url重组
        getRequest(url, new ICallBack() {
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

    /**
     * 解析HTML,返回Article列表
     *
     * @param str      html
     * @param callBack 回调
     */
    private static void parseArticle(String str, IGetListCallBack callBack) {
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("article");
        ArrayList<Article> lists = new ArrayList<>();
        for (Element element : elements) {
            lists.add(getArticleFromHtml(element));
        }
        //后面的页面已经没有文章
        if (lists.size() == 0) {
            if (callBack != null) {
                callBack.onError(AppApplication.getKString(R.string.no_more_article));
            }
            return;
        }
        if (callBack != null) {
            callBack.onSuccess(lists);
        }
    }

    /**
     * @param element 数据格式
     *                <article class="post">
     *                <div class="date">
     *                <span class="day">10</span>
     *                <span class="time">May</span>
     *                <span class="year">2015</span>
     *                </div>
     *                <header>
     *                <h2><a href="http://www.kutear.com/index.php/archives/net_note.html">网络摘抄</a></h2>
     *                </header>
     *                <div class="con">
     *                <p>
     *                <p/>
     *                之所以我爱你，无非是一个爱字，前面加我，后面加你，组合在一起的时候，我变成不一样的一个人，而你还是你。
     *                笑，众人陪笑，哭，独自垂泪。
     *                诺不轻许，故我不负人；诺不轻信，故人不负我。
     *                <p/>
     *                <p/>
     *                <a class="more" href="http://www.kutear.com/index.php/archives/net_note.html">[继续阅读]</a>
     *                </p>
     *                </div>
     *                </article>
     * @return Article
     */
    private static Article getArticleFromHtml(Element element) {
        Article article = new Article();
        Elements dayElements = element.getElementsByClass("day");
        if (dayElements.size() > 0) {
            Element dayElement = dayElements.get(0);
            article.setDay(dayElement.ownText());
        }
        Elements timeElements = element.getElementsByClass("time");
        if (timeElements.size() > 0) {
            Element timeElement = timeElements.get(0);
            article.setTime(timeElement.ownText());
        }
        Elements yearElements = element.getElementsByClass("year");
        if (yearElements.size() > 0) {
            Element yearElement = yearElements.get(0);
            article.setYear(yearElement.ownText());
        }
        Elements headerElements = element.getElementsByTag("header");
        if (headerElements.size() > 0) {
            Element headerElement = headerElements.get(0);
            Elements titleElements = headerElement.getElementsByTag("a");
            if (titleElements.size() > 0) {
                Element titleElement = titleElements.first();
                article.setUrl(titleElement.attr("href"));
                article.setTitle(titleElement.ownText());
            }
        }

        Elements contentElements = element.getElementsByTag("p");
        if (contentElements.size() > 0) {
            Element contentElement = contentElements.first();
            article.setContent(contentElement.ownText());
        }
        return article;
    }

}
