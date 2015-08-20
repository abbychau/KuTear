package com.kutear.app.api;

import android.text.TextUtils;

import com.kutear.app.AppApplication;
import com.kutear.app.R;
import com.kutear.app.bean.Archive;
import com.kutear.app.bean.Category;
import com.kutear.app.bean.Tab;
import com.kutear.app.utils.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kutear.guo on 2015/8/12.
 * 归档请求
 */
public class ApiArchive extends BaseRequest {

    private static final String TAG = ApiArchive.class.getSimpleName();
    private static String mResult;

    public interface IArchive {
        void onSuccess(List<Archive> lists);

        void onError(String msg);
    }

    public interface ICategory {
        void onSuccess(List<Category> lists);

        void onError(String msg);
    }

    public interface ITab {
        void onSuccess(List<Tab> lists);

        void onError(String msg);
    }

    public static void getArchive(final IArchive callBack) {
        doRequest(callBack);
    }

    private static void parseArchive(final IArchive callBack) {
        List<Archive> lists = new ArrayList<>();
        Document document = Jsoup.parse(mResult);
        Element element = document.getElementById("archives");
        if (element != null) {
            Elements elements = element.getElementsByTag("li");
            if (elements != null) {
                for (Element item : elements) {
                    Elements links = item.getElementsByTag("a");
                    Element link = links.first();
                    if (link != null) {
                        Archive archive = new Archive(link.ownText(), link.attr("href"));
                        lists.add(archive);
                    }
                }
            }
        }
        if (lists.size() > 0) {
            callBack.onSuccess(lists);
            return;
        }
        callBack.onError(AppApplication.getKString(R.string.can_not_find_archive_list));
    }

    /**
     * 三个页面的数据是一致的,只需要同样的数据做不同的处理
     *
     * @param o interface
     */
    private static void doRequest(final Object o) {
        getRequest(Constant.URI_ARCHIVE, new ICallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                if (statusCode == ICallBack.RESPONE_OK) {
                    mResult = str;
                    if (o instanceof IArchive) {
                        parseArchive((IArchive) o);
                    } else if (o instanceof ITab) {
                        parseTabs((ITab) o);
                    } else if (o instanceof ICategory) {
                        parseCategory((ICategory) o);
                    }
                }
            }

            @Override
            public void onError(int statusCode, String str) {
                if (o instanceof IArchive) {
                    ((IArchive) o).onError(str + "");
                } else if (o instanceof ITab) {
                    ((ITab) o).onError(str + "");
                } else if (o instanceof ICategory) {
                    ((ICategory) o).onError(str + "");
                }
            }
        });
    }

    public static void getTabs(ITab callBack) {
        doRequest(callBack);
    }

    private static void parseTabs(ITab callBack) {
        List<Tab> lists = new ArrayList<>();
        Document document = Jsoup.parse(mResult);
        Element element = document.getElementById("tags");
        Elements elements = element.getElementsByClass("tagname");
        for (Element item : elements) {
            Tab mTab = new Tab();
            Elements links = item.getElementsByTag("a");
            Element link = links != null ? links.first() : null;
            if (link != null) {
                mTab.setName(link.ownText());
                mTab.setUrl(link.attr("href"));
            }
            Elements counts = item.getElementsByTag("sup");
            if (counts.first() != null) {
                mTab.setCount(Integer.parseInt(counts.first().ownText()));
            }
            lists.add(mTab);
        }
        if (lists.size() > 0) {
            callBack.onSuccess(lists);
            return;
        }
        callBack.onError(AppApplication.getKString(R.string.can_not_read_tab_list));
    }

    public static void getCategory(ICategory callBack) {
        doRequest(callBack);
    }

    private static void parseCategory(ICategory callBack) {
        List<Category> lists = new ArrayList<>();
        Document document = Jsoup.parse(mResult);
        Element element = document.getElementById("category");
        Elements elements = element.getElementsByTag("li");
        if (elements != null) {
            for (Element item : elements) {
                if (item != null) {
                    Elements categorys = item.getElementsByClass("categoryname");
                    Element category = categorys.first();
                    if (category != null) {
                        Category categorybean = new Category();
                        Elements links = category.getElementsByTag("a");
                        Element link = links != null ? links.first() : null;
                        if (link != null) {
                            categorybean.setUrl(link.attr("href"));
                            categorybean.setName(link.ownText());
                        }
                        Elements counts = category.getElementsByTag("sup");
                        if (counts.first() != null) {
                            categorybean.setCount(Integer.parseInt(counts.first().ownText()));
                        }
                        lists.add(categorybean);
                    }
                }
            }
        }
        if (lists.size() > 0) {
            callBack.onSuccess(lists);
            return;
        }
        callBack.onError(AppApplication.getKString(R.string.can_not_get_category_info));
    }


}
