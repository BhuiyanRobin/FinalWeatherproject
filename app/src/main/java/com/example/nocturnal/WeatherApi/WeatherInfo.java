package com.example.nocturnal.WeatherApi;

import com.example.nocturnal.swapingtab.YahooUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bhuiy on 4/20/2017.
 */

public class WeatherInfo {
    WeatherData weatherDatas;

    public WeatherData getWeatherData(double log,double lon) {

               YahooUrl.ChangeingUrl+="select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"("+log+","+lon+")\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YahooUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi weatherApi= weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherData> weatherDataCall = weatherApi.getWeatherData(YahooUrl.ChangeingUrl);

        weatherDataCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData weatherData = response.body();
                String a=weatherData.getQuery().getResults().getChannel().getItem().getCondition().getTemp().toString();
                weatherDatas.setQuery(weatherData.getQuery());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
        return weatherDatas;
    }

}
