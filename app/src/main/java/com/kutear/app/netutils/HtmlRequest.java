package com.kutear.app.netutils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;

/**
 * Created by kutear.guo on 2015/12/8.
 *
 *  结合jsoup使用
 */
public class HtmlRequest extends Request<Document> {
    private final Response.Listener<Document> mListener;
    public HtmlRequest(String url, Response.Listener<Document> listener,Response.ErrorListener errorListener) {
        super(url, errorListener);
        this.mListener = listener;
    }

    public HtmlRequest(int method, String url, Response.Listener<Document> listener,Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }

    @Override
    protected Response<Document> parseNetworkResponse(NetworkResponse networkResponse) {
        Document parsed;
        String string;
        try {
            string = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException var4) {
            string = new String(networkResponse.data);
        }
        parsed = new Document(string);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(Document document) {
        this.mListener.onResponse(document);
    }
}
