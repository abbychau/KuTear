package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.ManagerArticleDetails;
import com.kutear.app.bean.ManagerCategory;
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

    /**
     * 解析数据,返回 {@link ManagerArticleDetails}
     *
     * @param str
     * @param callBack
     */
    private static void parseArticleDetails(String str, IGetCallBack callBack) {
        if (TextUtils.isEmpty(str) && callBack != null) {
            callBack.onGetError(AppApplication.getKString(R.string.can_not_get_content));
        }
        //正常解析
        Document doc = Jsoup.parse(str);
        ManagerArticleDetails details = new ManagerArticleDetails();
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
        //分类
        Elements categoryElements = rightElement.getElementsByAttributeValue("name", "category[]");
        ArrayList<ManagerCategory> categories = new ArrayList<>();
        for (Element item : categoryElements) {
            ManagerCategory category = new ManagerCategory();
            if (item != null && !TextUtils.isEmpty(item.attr("checked"))) {
                String categoryIndex = item.attr("value");
                category.setIndex(Integer.parseInt(categoryIndex));
            }
            categories.add(category);
        }
        details.setCategory(categories);
        //标签
        Elements tagElements = rightElement.getElementsByAttributeValue("name", "tags");
        if (tagElements != null && tagElements.first() != null) {
            Element tagElement = tagElements.first();
            String tags = tagElement.attr("value");
            details.setTags(tags);
        }
        //密码
        Elements passwordElements = rightElement.getElementsByAttributeValue("name", "password");
        if (passwordElements != null && passwordElements.first() != null) {
            Element passwordElement = passwordElements.first();
            String password = passwordElement.attr("value");
            details.setPassword(password);
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
        //引用通告
        Element trackbackElement = rightElement.getElementById("trackback");
        if (trackbackElement != null) {
            String trackback = trackbackElement.ownText();
            details.setTrackback(trackback);
        }

        if (callBack != null) {
            callBack.onGetSuccess(details);
        }
    }


    /**
     * 发表文章,根据{@link ManagerArticleDetails#doAction}判断操作类型
     *
     * @param details
     * @param callBack
     */
    public static void postArticle(final ManagerArticleDetails details, final IPostCallBack callBack) {
        //获取URL
        getRequest(Constant.URI_ARTICLE_POST, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (!TextUtils.isEmpty(str)) {
                    Document doc = Jsoup.parse(str);
                    Element formElement = doc.getElementsByTag("form") != null ? doc.getElementsByTag("form").first() : null;
                    if (formElement != null) {
                        String url = formElement.attr("action");
                        onPostArticle(url, details, callBack);
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
     * 保存到服务器
     *
     * @param url
     * @param details
     * @param callBack
     */
    private static void onPostArticle(String url, ManagerArticleDetails details, final IPostCallBack callBack) {
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
        params.put("cid", details.getCid());
        params.put("do", details.getDoAction());
        params.put("markdown", details.getMarkdown());
        if (details.getCategory() != null) {
            for (ManagerCategory item : details.getCategory()) {
                params.put("category[]", item.getIndex() + "");
            }
        }
        params.put("date", details.getDate());
        params.put("tags", details.getTags());
        params.put("visibility", details.getVisibility());
        params.put("password", details.getPassword());
        params.put("allowComment", details.getAllowComment());
        params.put("allowPing", details.getAllowPing());
        params.put("allowFeed", details.getAllowFeed());
        params.put("trackback", details.getTrackback());
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
/**
 * 更新文章的请求
 * title=Android Desigin Library&
 * slug=Android_Desigin_Library&
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