package com.example.nds.choosetheclothe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("weather")
    Call<WeatherResponce> getWeather(@Query("q") String city, @Query("appid") String apiLey, @Query("units") String units);
}
