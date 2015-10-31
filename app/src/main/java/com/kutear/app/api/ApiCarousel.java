package com.kutear.app.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kutear.app.AppApplication;
import com.kutear.app.utils.Constant;

/**
 * Created by kutear on 15-10-31.
 * 获取文章轮播图
 */
public class ApiCarousel {

    public static void getCarousel(Response.Listener listener){
        Request mRequest  = new JsonArrayRequest(Constant.URI_CAROUSEL,listener,null);
        AppApplication.startRequest(mRequest);
    }
}
