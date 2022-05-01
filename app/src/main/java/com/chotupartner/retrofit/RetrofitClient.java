package com.chotupartner.retrofit;
import com.chotupartner.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static ApiInterface getClient() {
        return retrofitBuilder().create(ApiInterface.class);
    }

    public static ApiInterface getClientnew() {
        return retrofitloginBuilder().create(ApiInterface.class);
    }



    public static Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-M  M-dd'T'HH:mm:ssZ").create();
    }

    private static OkHttpClient okHttp() {
// set your desired log level

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(2, TimeUnit.HOURS)
                    .readTimeout(2, TimeUnit.HOURS)
                    .writeTimeout(2, TimeUnit.HOURS)
                    .addInterceptor(logging)
                    .build();
        } else {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(2, TimeUnit.HOURS)
                    .readTimeout(2, TimeUnit.HOURS)
                    .writeTimeout(2, TimeUnit.HOURS)
                    .build();
        }

    }

    private static Retrofit retrofitBuilder() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl(BuildConfig.API_SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }

    private static Retrofit retrofitloginBuilder() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl(BuildConfig.API_SERVER_IP)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }

}
