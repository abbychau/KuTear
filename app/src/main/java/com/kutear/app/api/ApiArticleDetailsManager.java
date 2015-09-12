package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.lang.annotation.ElementType;

/**
 * Created by kutear on 15-9-11.
 * 文章详情管理接口
 */
public class ApiArticleDetailsManager extends ApiAdmin {

    /**
     * 获取指定URL的文章详情内容
     *
     * @param url
     * @param callBack
     */
    public static void getArticleDetails(String url, final IGetCallBack callBack) {
        getRequest(url, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseArticleDetails(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onGetError(str);
                }
            }
        });
    }

    private static void parseArticleDetails(String str, IGetCallBack callBack) {
        if (TextUtils.isEmpty(str) && callBack != null) {
            callBack.onGetError(AppApplication.getKString(R.string.can_not_get_content));
        }
        //正常解析
        Document doc = Jsoup.parse(str);
        ManagerArticleDetails details = new ManagerArticleDetails();
        //TODO 解析数据
//        private String title;
//        private String content;
//        private String fieldNames;
//        private String fieldTypes;
//        private String cid;
//        private String doAction;
//        private String markdown;
//        private String date;
//        private String category;
//        private String tags;
//        private String visibility;
//        private String password;
//        private String allowComment;
//        private String allowPing;
//        private String allowFeed;
//        private String trackback;
        //标题
        Elements titleElements = doc.getElementsByAttributeValue("name", "title");
        if (callBack != null) {
            callBack.onGetSuccess(details);
        }

    }

}
/**
 * 更新文章的请求
 * title=Android Desigin Library&slug=Android_Desigin_Library&
 * text=# Android Desigin Library
 * fieldNames[]=&
 * fieldTypes[]=str&
 * fieldValues[]=&
 * cid=43&
 * do=publish&
 * markdown=1&
 * date=2015-09-05 16:21&
 * category[]=4&
 * category[]=14&
 * tags=android,desigin&
 * visibility=publish&
 * password=&
 * allowComment=1&
 * allowPing=1&
 * allowFeed=1&
 * trackback=
 **/