package com.kutear.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Response;
import com.kutear.app.AppApplication;
import com.kutear.app.api.ApiCarousel;

import org.json.JSONArray;

public class CarouselServices extends Service implements Response.Listener<JSONArray> {
    public CarouselServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApiCarousel.getCarousel(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResponse(JSONArray jsonArray) {
        ((AppApplication) getApplication()).getCarouselManager().setJsonArray(jsonArray);
        stopSelf();
    }
}
