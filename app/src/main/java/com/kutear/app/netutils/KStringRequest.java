package com.kutear.app.netutils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.kutear.app.AppApplication;
import com.kutear.app.utils.L;
import com.kutear.app.utils.SaveData;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kutear.guo on 2015/8/4.
 */
public class KStringRequest extends StringRequest {
    private static final String TAG = KStringRequest.class.getSimpleName();
    private Map<String, String> params;


    /**
     * Just For Method.POST
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public KStringRequest(String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
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
            String dataString = new String(response.data, "UTF-8");
            return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return NetConfig.getRequestHeader();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }
}
