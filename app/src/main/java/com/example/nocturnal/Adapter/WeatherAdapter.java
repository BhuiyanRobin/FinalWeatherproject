package com.example.nocturnal.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nocturnal.WeatherApi.Forecast;
import com.example.nocturnal.swapingtab.R;

import java.util.ArrayList;

/**
 * Created by bhuiy on 4/20/2017.
 */

public class WeatherAdapter extends ArrayAdapter<Forecast> {
    ArrayList<Forecast> forecasts;
    Forecast forecast;
    Context context;
    View view;
    ViewHolder viewHolder;

    public WeatherAdapter(Context context, ArrayList<Forecast> forecastArrayList) {
        super(context, R.layout.weather_forecast,forecastArrayList);
        this.forecasts=forecastArrayList;
        this.context=context;
        this.viewHolder=new ViewHolder();
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        view=convertView;

        final LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.weather_forecast,parent,false);

        viewHolder.condition=(TextView)view.findViewById(R.id.conditionText);
        viewHolder.date=(TextView)view.findViewById(R.id.date);
        viewHolder.high= (TextView) view.findViewById(R.id.high);
        viewHolder.low=(TextView)view.findViewById(R.id.low);
        //viewHolder.day=(TextView)view.findViewById(R.id.Day);
       // viewHolder.image=(ImageView)view.findViewById(R.id.condition);

        viewHolder.condition.setText(forecasts.get(position).getText().toString());
        viewHolder.date.setText(forecasts.get(position).getDate().toString());
        viewHolder.high.setText(forecasts.get(position).getHigh().toString());
        viewHolder.low.setText(forecasts.get(position).getLow().toString());
        //viewHolder.day.setText(forecasts.get(position).getDay().toString());
        Bitmap bitmap;
       // viewHolder.image.setImageBitmap(bitmap);
        return view;
    }


    static class ViewHolder{

        TextView date;
        TextView day;
        TextView high;
        TextView low;
        ImageView image;
        TextView condition;
    }
}
