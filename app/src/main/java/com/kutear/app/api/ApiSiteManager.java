package com.kutear.app.api;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.SiteInfo;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by kutear.guo on 2015/8/21.
 */
public class ApiSiteManager extends ApiAdmin {
    private static final String TAG = ApiSiteManager.class.getSimpleName();

    public interface ISiteManager {
        void onSuccess(SiteInfo info);

        void onError(String msg);
    }

    public static void getSiteInfo(final ISiteManager callBack) {
        getRequest(Constant.URI_BASE_SETTING, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                praseHtml(str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onError(AppApplication.getKString(R.string.can_not_get_site_info));
                }
            }
        });
    }

    /**
     * 解析数据
     *
     * @param str      doc
     * @param callBack 回调
     */
    private static void praseHtml(String str, ISiteManager callBack) {
        SiteInfo info = new SiteInfo();
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByTag("form");
        Element formElement = elements != null ? elements.first() : null;
        if (formElement != null) {
            info.setSitePostUrl(formElement.attr("action"));
        }
        Element nameElement = document.getElementById("title-0-1");
        if (nameElement != null) {
            info.setSiteName(nameElement.attr("value"));
        }
        Element keyElement = document.getElementById("keywords-0-4");
        if (nameElement != null) {
            info.setSiteKeyWord(keyElement.attr("value"));
        }
        Element descriptElement = document.getElementById("description-0-3");
        if (nameElement != null) {
            info.setSiteDescription(descriptElement.attr("value"));
        }
        Element addressElement = document.getElementById("siteUrl-0-2");
        if (nameElement != null) {
            info.setSiteAddress(addressElement.attr("value"));
        }
        if (callBack != null) {
            callBack.onSuccess(info);
        }
    }

    /**
     * title=KuTear&
     * siteUrl=http://www.kutear.com&
     * description=本站是一个技术类型的个人博客,包含一些个人的见解,希望通过网络和志同之士分享&
     * keywords=android,github,develop,SEO&
     * allowRegister=0&
     * timezone=28800&
     * attachmentTypes[]=@image@&
     * attachmentTypes[]=@media@&
     * attachmentTypes[]=@doc@&
     * attachmentTypes[]=@other@&
     * attachmentTypesOther=mp4,cpp,java,h
     */
    public static void postSiteInfo(SiteInfo info) {
        HashMap<String, String> params = new HashMap<>();
        params.put("title", info.getSiteName());
        params.put("siteUrl", info.getSiteAddress());
        params.put("description", info.getSiteDescription());
        params.put("keywords", info.getSiteKeyWord());
        params.put("allowRegister", "0");
        params.put("timezone", "28800");
        params.put("attachmentTypes[]", "@image@");
        params.put("attachmentTypes[]", "@media@");
        params.put("attachmentTypes[]", "@doc@");
        params.put("attachmentTypes[]", "@other@");
        params.put("attachmentTypesOther", "mp4,cpp,java,h");
        postRequest(info.getSitePostUrl(), params, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                L.v(TAG, str);
            }

            @Override
            public void onError(int statusCode, String str) {

            }
        });
    }

}
