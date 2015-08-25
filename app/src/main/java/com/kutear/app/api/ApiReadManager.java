package com.kutear.app.api;

import com.kutear.app.bean.ReadInfo;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.callback.IPostCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by kutear.guo on 2015/8/23.
 */
public class ApiReadManager extends ApiAdmin {

    public static void getReadInfo(final IGetCallBack callBack) {
        getRequest(Constant.URI_READ_SETTING, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseReadInfo(str, callBack);
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
     * 解析ReadInfo
     *
     * @param doc      html
     * @param callBack 回调
     */
    private static void parseReadInfo(String doc, IGetCallBack callBack) {
        ReadInfo info = new ReadInfo();
        Document document = Jsoup.parse(doc);
        //postDateFormat-0-1
        Element dateFormatElement = document != null ? document.getElementById("postDateFormat-0-1") : null;
        if (dateFormatElement != null) {
            info.setDateFormat(dateFormatElement.attr("value"));
        }
        //postsListSize-0-3
        Element listsSizeElement = document != null ? document.getElementById("postsListSize-0-3") : null;
        if (dateFormatElement != null) {
            info.setListsCount(Integer.parseInt(listsSizeElement.attr("value")));
        }
        //pageSize-0-4
        Element pageSizeElement = document != null ? document.getElementById("pageSize-0-4") : null;
        if (dateFormatElement != null) {
            info.setPagerCount(Integer.parseInt(pageSizeElement.attr("value")));
        }
        if (callBack != null) {
            callBack.onGetSuccess(info);
        }
    }

    /**
     * postDateFormat=Y-m-d &
     * frontPage=recent &
     * frontPagePage=2 &
     * archivePattern=/blog/ &
     * postsListSize=10 &
     * pageSize=5 &
     * feedFullText=1
     *
     * @param info
     * @param callBack
     */
    public static void postReadInfo(final ReadInfo info, final IPostCallBack callBack) {
        getRequest(Constant.URI_READ_SETTING, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document doc = Jsoup.parse(str);
                Elements links = doc.getElementsByTag("form");
                Element postUrl = links != null ? links.first() : null;
                if (postUrl != null) {
                    String postlink = postUrl.attr("action");
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("postDateFormat", info.getDateFormat());
                    params.put("frontPage", "recent");
                    params.put("frontPagePage", "2");
                    params.put("archivePattern", "/blog/");
                    params.put("postsListSize", info.getListsCount() + "");
                    params.put("pageSize", info.getPagerCount() + "");
                    params.put("feedFullText", "1");
                    postRequest(postlink, params, new ICallBack() {
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

            }
        });
    }
}
