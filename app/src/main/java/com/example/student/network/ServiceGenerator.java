package com.example.student.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static  final String BASE_URL = "https://script.google.com/macros/s/AKfycbxM8PsyDos75x1yxHIUCzjNJOBPa5MFT5qGhFT5g9RBbHhqdm429Rw-kcTvBAWPZali/";
    private static OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build();
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    public static  <S> S createService(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
