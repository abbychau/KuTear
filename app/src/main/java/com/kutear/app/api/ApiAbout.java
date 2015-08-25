package com.kutear.app.api;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.About;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by kutear.guo on 2015/8/12.
 * 关于页面的请求
 */
public class ApiAbout extends BaseRequest {
    public static void getAbout(final IGetCallBack callBack) {
        getRequest(Constant.URI_ABOUT, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseHtml(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onGetError(AppApplication.getKString(R.string.get_about_info_error));
                }
            }
        });
    }

    /**
     * 获取信息
     *
     * @param str      Doc
     * @param callBack 回调
     */
    private static void parseHtml(String str, IGetCallBack callBack) {
        Document document = Jsoup.parse(str);
        Element element = document.getElementById("content");
        if (callBack != null && element != null) {
            About about = new About();
            about.setContent(element.html());
            callBack.onGetSuccess(about);
        }
    }
}
