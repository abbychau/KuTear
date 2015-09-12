package com.kutear.app.netutils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kutear.guo on 2015/8/4.
 * 所有的网络请求都是通过该类实现
 */
public class KStringRequest extends StringRequest {
    private static final String TAG = KStringRequest.class.getSimpleName();
    private Map<String, String> params;


    /**
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public KStringRequest(int type, String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(type, url, listener, errorListener);
        this.params = params;
    }

    public KStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public KStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            NetConfig.checkSessionCookie(response.headers);
            String dataString = new String(response.data, "UTF-8");
            return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        headers.putAll(NetConfig.getRequestHeader());
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }
}
