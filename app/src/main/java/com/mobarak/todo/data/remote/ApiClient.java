package com.mobarak.todo.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Gson gson = new GsonBuilder().setLenient().create();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl("BASE_API_URL")
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor interceptor =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <T> T createApiClient(Class<T> serviceClass) {
        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}