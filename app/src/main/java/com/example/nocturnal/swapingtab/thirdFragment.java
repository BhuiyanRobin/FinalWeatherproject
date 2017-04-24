package com.example.nocturnal.swapingtab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.nocturnal.WeatherApi.WeatherApi;
import com.example.nocturnal.WeatherApi.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class thirdFragment extends Fragment {

    private String city;
    private TextView date_timeTV;
    private TextView tempTv;
    private TextView cityNameTV;
    private TextView temp_highTV;
    private TextView temp_lowTV;
    private TextView climate_conditionTV;
    private TextView sun_riseTV;
    private TextView sun_setTV;
    private TextView humidityTV;
    private TextView windTV;
    private TextView wind_directionTV;
    WeatherApi weatherApi;


    private int height;


    public thirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v  = inflater.inflate(R.layout.fragment_third, container, false);
        date_timeTV = (TextView) v.findViewById(R.id.date_time);
        tempTv = (TextView) v.findViewById(R.id.temp);
        cityNameTV= (TextView) v.findViewById(R.id.cityName);
        climate_conditionTV= (TextView) v.findViewById(R.id.climate_condition);
        temp_highTV= (TextView) v.findViewById(R.id.temp_high);
        temp_lowTV= (TextView) v.findViewById(R.id.temp_low);
        sun_riseTV= (TextView) v.findViewById(R.id.sun_rise);
        sun_setTV= (TextView) v.findViewById(R.id.sun_set);
        humidityTV= (TextView) v.findViewById(R.id.humidity);
        windTV= (TextView) v.findViewById(R.id.wind);
        wind_directionTV= (TextView) v.findViewById(R.id.wind_direction);


        double log=getArguments().getDouble("log",0.0);
        double lon =getArguments().getDouble("lon",0.0);

        YahooUrl.ChangeingUrl+="select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"("+log+","+lon+")\")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
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
                cityNameTV.setText(weatherData.getQuery().getResults().getChannel().getLocation().getCity().toString());
                date_timeTV.setText(weatherData.getQuery().getResults().getChannel().getLastBuildDate().toString());
                String temp = weatherData.getQuery().getResults().getChannel().getItem().getCondition().getTemp().toString();
                int tempDeg = (Integer.parseInt(temp)-32)/9*5;
                temp = Integer.toString(tempDeg);
                tempTv.setText(temp);
                climate_conditionTV.setText(weatherData.getQuery().getResults().getChannel().getItem().getCondition().getText().toString());
                String highTempFar = weatherData.getQuery().getResults().getChannel().getItem().getForecast().get(0).getHigh();
                int highTempCel = (Integer.parseInt(highTempFar)-32)/9*5;
                String highTemp = Integer.toString(highTempCel);
                temp_highTV.setText(highTemp);
                String lowTempFar = weatherData.getQuery().getResults().getChannel().getItem().getForecast().get(0).getLow();
                int lowTempCel = (Integer.parseInt(lowTempFar)-32)/9*5;
                String lowTemp = Integer.toString(lowTempCel);
                temp_lowTV.setText(lowTemp);
                sun_riseTV.setText(weatherData.getQuery().getResults().getChannel().getAstronomy().getSunrise().toString());
                sun_setTV.setText(weatherData.getQuery().getResults().getChannel().getAstronomy().getSunset().toString());
                humidityTV.setText(weatherData.getQuery().getResults().getChannel().getAtmosphere().getHumidity().toString()+"%");
                windTV.setText( String.format("%.2f",Double.parseDouble(weatherData.getQuery().getResults().getChannel().getWind().getSpeed())*1.609344)+" Kmp");

                int winddirection = Integer.parseInt(weatherData.getQuery().getResults().getChannel().getWind().getDirection());
                String direction = "";

                if (winddirection == 0) {
                    direction="North";
                }else if(winddirection<90){
                    direction= "North East";
                }else if(winddirection==90){
                    direction= "East";
                }else if(winddirection < 180){
                    direction= "South East";
                }else if(winddirection == 180){
                    direction= "South";
                }else if(winddirection < 270){
                    direction= "South West";
                }else if(winddirection == 270){
                    direction= "West";
                }else if(winddirection < 360){
                    direction= "North West";
                }else if(winddirection == 360){
                    direction= "North";
                }

                wind_directionTV.setText(direction);





                /*if (weatherData.getQuery().getResults().getChannel().getItem().getCondition().getText().toString() == "Cloudy"){
                    linearLayout.setBackgroundResource(R.drawable.cloudy_sky);
                }*///

            }


            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
    }
}
