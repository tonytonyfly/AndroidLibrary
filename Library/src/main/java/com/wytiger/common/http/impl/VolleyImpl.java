package com.wytiger.common.http.impl;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wytiger.common.http.interfaces.IHttpCallback;
import com.wytiger.common.http.interfaces.IHttpInterface;
import com.wytiger.common.utils.HttpParamUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * description:
 * Created by wytiger on 2016-12-22.
 */

public class VolleyImpl implements IHttpInterface {
    public static RequestQueue mQueue;


    public static VolleyImpl getInstance(Application appContext) {
        mQueue = Volley.newRequestQueue(appContext);
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final VolleyImpl sInstance = new VolleyImpl();
    }

    @Override
    public void get(String baseUrl, Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback. onStart();
        String url = baseUrl+ HttpParamUtil.getKeyValue(params);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestCallback.onSuccess(s);
                        requestCallback.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        requestCallback.onFailure(volleyError);
                        requestCallback.onFinish();
                    }
                });
        mQueue.add(request);
    }

    @Override
    public void post(String url,  Map<String, Object> params, final IHttpCallback requestCallback) {
        requestCallback. onStart();
        requestWithBody(url, params.toString(), requestCallback, Request.Method.POST);
    }


    /**
     * 封装带请求体的请求方法
     *
     * @param url             url
     * @param requestBodyJson Json string请求体
     * @param requestCallback 回调接口
     * @param method          请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final IHttpCallback requestCallback, int method) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestCallback.onSuccess(response != null ? response.toString() : null);
                        requestCallback.onFinish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestCallback.onFailure(error);
                        requestCallback.onFinish();
                    }
                });
        mQueue.add(request);
    }

}