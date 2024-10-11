package com.abhishekbansode.stockwalkapp.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static String apiKey;
    private static String apiHost;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("X-RapidAPI-Key", apiKey)
                                .addHeader("X-RapidAPI-Host", apiHost)
                                .build();
                        return chain.proceed(request);
                    }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://yahoo-finance15.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            Log.d("ApiClient", "Retrofit client created.");
        }
        return retrofit;
    }

    public void init(String apiKey, String apiHost) {
        ApiClient.apiKey = apiKey;
        ApiClient.apiHost = apiHost;
    }
}
