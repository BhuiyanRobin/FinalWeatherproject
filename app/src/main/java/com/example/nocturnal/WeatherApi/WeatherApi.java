package com.example.nocturnal.WeatherApi;

import com.example.nocturnal.swapingtab.YahooUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Nocturnal on 19-Apr-17.
 */

public interface WeatherApi {

    @GET
    Call<WeatherData> getWeatherData(@Url String url);
}
