package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.bean.ManagerPagerDetails;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetCallBack;
import com.kutear.app.callback.IPostCallBack;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by kutear on 15-9-17.
 * 获取独立页面详情
 **/
public class ApiPagerDetailsManager extends ApiAdmin {
    private static final String TAG = ApiPagerDetailsManager.class.getSimpleName();

    /**
     * 根据指定的URL获取
     *
     * @param url
     * @param callBack
     */
    public static void getPagerDetails(String url, final IGetCallBack callBack) {
        getRequest(url, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parsePagerDetails(str, callBack);
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
     * 数据解析
     *
     * @param html
     * @param callBack
     */
    private static void parsePagerDetails(String html, IGetCallBack callBack) {
        if (TextUtils.isEmpty(html)) {
            if (callBack != null) {
                callBack.onGetError(AppApplication.getKString(R.string.can_not_get_content));

            }
            return;
        }
        //正常解析
        Document doc = Jsoup.parse(html);
        ManagerPagerDetails details = new ManagerPagerDetails();
        //标题
        Elements titleElements = doc.getElementsByAttributeValue("name", "title");
        if (titleElements != null && titleElements.first() != null) {
            Element titleElement = titleElements.first();
            String title = titleElement.attr("value");
            details.setTitle(title);
        }
        //网址缩略名
        Elements slugElements = doc.getElementsByAttributeValue("name", "slug");
        if (slugElements != null && slugElements.first() != null) {
            Element slugElement = slugElements.first();
            String slug = slugElement.attr("value");
            details.setSlug(slug);
        }
        //内容
        Elements contentElements = doc.getElementsByAttributeValue("name", "text");
        if (contentElements != null && contentElements.first() != null) {
            Element contentElement = contentElements.first();
            String content = contentElement.ownText();
            details.setContent(content);
        }
        //自定义字段相关
        Element customElement = doc.getElementById("custom-field");
        if (customElement == null) {
            if (callBack != null) {
                callBack.onGetError("获取失败");
            }
            return;
        }
        Elements tbodyElements = customElement.getElementsByTag("tbody");
        //循环tr
        ArrayList<ManagerArticleDetails.Field> customField = new ArrayList<>();
        for (Element item : tbodyElements) {
            ManagerArticleDetails.Field field = new ManagerArticleDetails.Field();
            //字段名字
            Elements fieldNameElements = item.getElementsByAttributeValue("name", "fieldNames[]");
            if (fieldNameElements != null && fieldNameElements.first() != null) {
                Element fieldNameElement = fieldNameElements.first();
                String fieldName = fieldNameElement.attr("value");
                field.setFiledName(fieldName);
            }
            //字段类型
            Element fieldTypeElement = item.getElementById("fieldtype");
            for (Element fieldTypeItem : fieldTypeElement.children()) {
                if (fieldTypeItem != null && fieldTypeItem.hasAttr("selected")) {
                    String fieldType = fieldTypeItem.attr("value");
                    field.setFiledType(fieldType);
                    break;
                }
            }
            //字段值
            Elements fieldValueElements = item.getElementsByAttributeValue("name", "fieldValues[]");
            if (fieldValueElements != null && fieldValueElements.first() != null) {
                Element fieldValueElement = fieldValueElements.first();
                String fieldValue = fieldValueElement.attr("value");
                field.setFiledValue(fieldValue);
            }
            customField.add(field);
        }
        details.setCustomField(customField);
        //Cid文章序列号
        Elements cidElements = doc.getElementsByAttributeValue("name", "cid");
        if (cidElements != null && cidElements.first() != null) {
            Element cidElement = cidElements.first();
            String cid = cidElement.attr("value");
            details.setCid(cid);
        }
        //MarkDown
        Elements markDownElements = doc.getElementsByAttributeValue("name", "markdown");
        if (markDownElements != null && markDownElements.first() != null) {
            Element markDownElement = markDownElements.first();
            String markdown = markDownElement.attr("value");
            details.setMarkdown(markdown);
        }
        //右边部分-------------------------------------------------------
        Element rightElement = doc.getElementById("edit-secondary");
        //日期
        Elements dateElements = rightElement.getElementsByAttributeValue("name", "date");
        if (dateElements != null && dateElements.first() != null) {
            Element dateElement = dateElements.first();
            String date = dateElement.attr("value");
            details.setDate(date);
        }
        //visibility
        Element visibilityElement = rightElement.getElementById("visibility");
        for (Element item : visibilityElement.children()) {
            if (item != null && item.hasAttr("selected")) {
                String visibility = item.attr("value");
                details.setVisibility(visibility);
            }
        }
        //模板
        Element templateElement = rightElement.getElementById("template");
        for (Element item : templateElement.children()) {
            if (item != null && item.hasAttr("selected")) {
                String template = item.attr("value");
                details.setTemplate(template);
            }
        }
        //页面顺序
        Element idElement = rightElement.getElementById("order");
        if (idElement != null) {
            details.setOrder(idElement.attr("value"));
        }
        //允许引用
        Element allowPingElement = rightElement.getElementById("allowPing");
        if (allowPingElement != null) {
            String allowPing = allowPingElement.attr("value");
            details.setAllowPing(allowPing);
        }

        //允许评论
        Element allowCommentElement = rightElement.getElementById("allowComment");
        if (allowCommentElement != null) {
            String allowComment = allowCommentElement.attr("value");
            details.setAllowComment(allowComment);
        }
        //允许聚合出现
        Element allowFeedElement = rightElement.getElementById("allowFeed");
        if (allowFeedElement != null) {
            String allowFeed = allowFeedElement.attr("value");
            details.setAllowFeed(allowFeed);
        }
        if (callBack != null) {
            callBack.onGetSuccess(details);
        }
    }

    /**
     * 发表pager
     */
    public static void postPager(final ManagerPagerDetails details, final IPostCallBack callBack) {
        getRequest(Constant.URI_PAGER_DETAILS, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (!TextUtils.isEmpty(str)) {
                    Document doc = Jsoup.parse(str);
                    Element formElement = doc.getElementsByTag("form") != null ? doc.getElementsByTag("form").first() : null;
                    if (formElement != null) {
                        String url = formElement.attr("action");
                        onPostPager(url, details, callBack);
                    } else {
                        if (callBack != null) {
                            callBack.onPostError(AppApplication.getKString(R.string.get_url_error));
                        }
                    }
                } else {
                    if (callBack != null) {
                        callBack.onPostError(AppApplication.getKString(R.string.get_url_error));
                    }
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostError(AppApplication.getKString(R.string.get_url_error));
                }
            }
        });
    }

    /**
     * @param url
     * @param callBack
     */
    private static void onPostPager(String url, ManagerPagerDetails details, final IPostCallBack callBack) {
        IdentityHashMap params = new IdentityHashMap();
        params.put("title", details.getTitle());
        params.put("slug", details.getSlug());
        params.put("text", details.getContent());
        if (details.getCustomField() != null) {
            for (ManagerArticleDetails.Field item : details.getCustomField()) {
                params.put("fieldNames[]", item.getFiledName());
                params.put("fieldValues[]", item.getFiledValue());
                params.put("fieldTypes[]", item.getFiledType());
            }
        }
        params.put("order", details.getOrder());
        params.put("template", details.getTemplate());
        params.put("date", details.getDate());
        params.put("cid", details.getCid());
        params.put("do", details.getDoAction());
        params.put("markdown", details.getMarkdown());
        params.put("visibility", details.getVisibility());
        params.put("allowComment", details.getAllowComment());
        params.put("allowPing", details.getAllowPing());
        params.put("allowFeed", details.getAllowFeed());

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
                    callBack.onPostError(AppApplication.getKString(R.string.save_to_server_fail));
                }
            }
        });
    }
}
