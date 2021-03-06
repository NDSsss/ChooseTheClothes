package com.example.nds.choosetheclothe.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherService {
    @GET("weather")
    Call<WeatherResponce> getWeather(@Query("q") String city, @Query("appid") String apiLey, @Query("units") String units);
    @GET("weather")
    Call<WeatherResponce> getWeatherById(@Query("id") int id, @Query("appid") String apiLey, @Query("units") String units);
    @GET("forecast")
    Call<WeatherResponce> getForecatById(@Query("id") int id, @Query("appid") String apiLey, @Query("units") String units);
}
