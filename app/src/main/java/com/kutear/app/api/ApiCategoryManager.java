package com.kutear.app.api;


import android.text.TextUtils;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.ManagerCategory;
import com.kutear.app.callback.ICallBack;
import com.kutear.app.callback.IGetListCallBack;
import com.kutear.app.callback.IPostCallBack;
import com.kutear.app.utils.Constant;
import com.kutear.app.utils.L;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kutear.guo on 2015/8/24.
 * 获取分类管理信息
 */
public class ApiCategoryManager extends ApiAdmin {
    private static final String TAG = ApiCategoryManager.class.getSimpleName();

    public static void getCategory(final int parent, final IGetListCallBack callBack) {
        String url = Constant.URI_CATEGORY_MANAGER;
        if (parent != 0) {
            url = url + "?parent=" + parent;
        }

        getRequest(url, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                parseCategory(parent, str, callBack);
            }

            @Override
            public void onError(int statusCode, String str) {

            }
        });
    }

    private static void parseCategory(int parent, String doc, IGetListCallBack callBack) {
        Document document = Jsoup.parse(doc);
        Element tableElement = document.getElementsByTag("tbody") != null ?
                document.getElementsByTag("tbody").first() : null;
        ArrayList<ManagerCategory> lists = new ArrayList<>();
        if (tableElement != null) {
            for (Element item : tableElement.children()) {
                ManagerCategory category = new ManagerCategory();
                category.setParentId(parent);
                //获取分类index
                Element indexElement = item.getElementsByTag("input") != null ? item.getElementsByTag("input").first() : null;
                if (indexElement != null) {
                    category.setIndex(Integer.parseInt(indexElement.attr("value")));
                }
                if (item.childNodeSize() < 6) {
                    callBack.onError("该分类下没有子分类");
                    return;
                }
                //获取分类名称和对应的链接
                Element nameElement = item.child(1);
                if (nameElement != null) {
                    Element alink = nameElement.child(0);
                    if (alink != null) {
                        category.setCategoryName(alink.ownText());
                        category.setCategoryUrl(alink.attr("href"));
                    }
                }
                //子分类的链接
                Element childElement = item.child(2);
                if (childElement != null) {
                    Element alink = childElement.child(0);
                    if (alink != null) {
                        category.setChildCategory(alink.attr("href"));
                    }
                }
                //简称
                Element simpleElement = item.child(3);
                if (simpleElement != null) {
                    category.setSimpleName(simpleElement.ownText());
                }
                //文章数
                Element articleElement = item.child(5);
                if (articleElement != null) {
                    try {
                        category.setArticleCount(Integer.parseInt(articleElement.text()));
                    } catch (Exception e) {
                        category.setArticleCount(0);
                    }
                }
                lists.add(category);
            }
        } else {
            if (callBack != null) {
                callBack.onError(AppApplication.getKString(R.string.get_category_error));
            }
        }

        if (lists.size() > 0 && callBack != null) {
            callBack.onSuccess(lists);
        }
    }

    public static void deteleCategory(final ManagerCategory category, final IPostCallBack callBack) {
        getRequest(Constant.URI_CATEGORY_MANAGER, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document document = Jsoup.parse(str);
                Elements ulElements = document.getElementsByClass("dropdown-menu");
                Element liElement = ulElements != null ? ulElements.first() : null;
                if (liElement != null) {
                    Element aElement = liElement.getElementsByTag("a").first();
                    String url = aElement != null ? aElement.attr("href") : null;
                    if (!TextUtils.isEmpty(url)) {
                        onDeteleCategory(url, category.getIndex(), callBack);
                        return;
                    }
                }
                if (callBack != null) {
                    callBack.onPostError("获取链接失败");
                }
            }

            @Override
            public void onError(int statusCode, String str) {

            }
        });
    }

    private static void onDeteleCategory(String url, int index, final IPostCallBack callBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mid[]", index + "");
        postRequest(url, params, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostSuccess("删除成功");
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostError("删除失败");
                }
            }
        });
    }

    /**
     * 编辑分类
     *
     * @param category
     * @param callBack
     */
    public static void editCategory(final ManagerCategory category, final String doAction, final IPostCallBack callBack) {
        getRequest(String.format(Constant.URI_CATEGORY_EDIT, category.getIndex()), new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Document document = Jsoup.parse(str);
                Element formElement = document.getElementsByTag("form").first();
                String url;
                if (formElement != null) {
                    url = formElement.attr("action");
                    if (!TextUtils.isEmpty(url)) {
                        onEditCategory(url, doAction, category, callBack);
                        return;
                    }
                }
                if (callBack != null) {
                    callBack.onPostError("获取链接失败");
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostError("获取链接失败");
                }
            }
        });
    }

    private static void onEditCategory(String url, String doAction, ManagerCategory category, final IPostCallBack callBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", category.getCategoryName());
        params.put("slug", category.getSimpleName());
        params.put("do", doAction);
        params.put("parent", category.getParentId() + "");
        if (category.getIndex() != 0) {
            params.put("mid", category.getIndex() + "");
        }
        postRequest(url, params, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostSuccess("修改成功");
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (callBack != null) {
                    callBack.onPostError("修改失败");
                }
            }
        });
    }


}
