package com.example.nocturnal.swapingtab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.nocturnal.Adapter.WeatherAdapter;
import com.example.nocturnal.WeatherApi.Forecast;
import com.example.nocturnal.WeatherApi.WeatherApi;
import com.example.nocturnal.WeatherApi.WeatherData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class seconfFragment extends Fragment {
    private String city;
    private TextView date_timeTV;
    private TextView tempTv;

    WeatherApi weatherApi;
    WeatherAdapter weatherAdapter;
ArrayList<Forecast> forecasts;
    private ListView listView;
    public seconfFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_seconf, container, false);
        listView= (ListView) v.findViewById(R.id.listView);
        forecasts=new ArrayList<Forecast>();
        date_timeTV = (TextView) v.findViewById(R.id.date_time);
        tempTv = (TextView) v.findViewById(R.id.temp);
        double log=getArguments().getDouble("log",0.0);
        double lon =getArguments().getDouble("lon",0.0);
        String city=getArguments().getString("city");
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.raining).into(imageViewTarget);
        if(city==null)
        {
            YahooUrl.ChangeingUrl+="select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"("+log+","+lon+")\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        }
        else{
            YahooUrl.ChangeingUrl+="select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+city+", ak\")";
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YahooUrl.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);

        getWeatherData();


        return v;
    }
    private void getWeatherData() {
        Call<WeatherData> weatherDataCall = weatherApi.getWeatherData(YahooUrl.ChangeingUrl);
        weatherDataCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData weatherData = response.body();

                for (Forecast forecast:weatherData.getQuery().getResults().getChannel().getItem().getForecast())
                {
                    Forecast forecast1=new Forecast();
                    forecast1.setCode(forecast.getCode());
                    forecast1.setDate(forecast.getDate());
                    forecast1.setDay(forecast.getDay());
                    forecast1.setHigh(forecast.getHigh());
                    forecast1.setLow(forecast.getLow());
                    forecast1.setText(forecast.getText());
                    forecasts.add(forecast1);
                }
                weatherAdapter=new WeatherAdapter(getActivity(),forecasts);
                listView.setAdapter(weatherAdapter);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
    }

}
