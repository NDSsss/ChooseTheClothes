package com.example.nds.choosetheclothe;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private Retrofit retrofit;
    private App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        instance = this;
    }

    private void initRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        //create an instance of Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public App getInstance() {
        return instance;
    }
}
