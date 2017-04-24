package com.example.nocturnal.swapingtab;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nocturnal.SearchCountry;
import com.example.nocturnal.WeatherApi.WeatherApi;
import com.example.nocturnal.WeatherApi.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchCountry.MyItemListener {

    private TextView cityNameTV;
    private String city = "";
    private TextView ct;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private FrameLayout third_frameLayout;
    private LinearLayout linearLayout;

    private AlertDialog.Builder myCustomAlert;

    private String newCityNmae;
    private ListView searchListView;
    private ArrayAdapter<String> search_country_adapter;
    private ArrayList<String> arrayCountry;

    private Boolean exit = false;


    WeatherInfo info;

    private Geocoder geocoder;
    private List<Address> addresses;

    private String newCityFromList;


    double latitude;
    double longitude;
    WeatherApi weatherApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, Gpstracker.class);
        startActivityForResult(intent,1);
        geocoder = new Geocoder(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
             longitude = extras.getDouble("Longitude");
             latitude = extras.getDouble("Latitude");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        cityNameTV = (TextView) findViewById(R.id.cityName);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {

        super.onStop();
    }

    public void changeFragment(View view) {
        Fragment fragment = null;
        Bundle b = new Bundle();

        switch (view.getId()){
            case R.id.detaile_fragment:
                fragment = new thirdFragment();
                b.putDouble("log",latitude);
                b.putDouble("lon",longitude);
                break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        linearLayout = (LinearLayout) findViewById(R.id.fragmentOne_container);
        linearLayout.setVisibility(View.INVISIBLE);
        fragment.setArguments(b);
        ft.replace(R.id.first_frame,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void getItem(String item) {
        Intent intent  = new Intent(MainActivity.this,Gpstracker.class);
        intent.putExtra("city",item);
        newCityFromList = item;
        Toast.makeText(this,newCityFromList, Toast.LENGTH_LONG).show();
        startActivityForResult(intent,1);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle b = new Bundle();
        geocoder = new Geocoder(this);
        firstFragment fragmnetOne =new firstFragment();

        if (newCityFromList != null){
            b.putString("city",newCityFromList);
        }
        else{
            b.putString("city",null);
        }

        b.putDouble("log",latitude);
        b.putDouble("lon",longitude);
        fragmnetOne.setArguments(b);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle b = new Bundle();

            switch (position) {

                case 0:
                    SearchCountry searchCountry =new SearchCountry();
                    return searchCountry;

                case 1:
                    firstFragment fragmnetOne =new firstFragment();

                    if (newCityFromList != null){
                        b.putString("city",newCityFromList);
                    }
                    else{
                        b.putString("city",null);
                    }

                    b.putDouble("log",latitude);
                    b.putDouble("lon",longitude);
                    fragmnetOne.setArguments(b);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                    return fragmnetOne;


                case 2:
                    seconfFragment seconfFragment=new seconfFragment();
                    b.putDouble("log",latitude);
                    b.putDouble("lon",longitude);
                    seconfFragment.setArguments(b);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                    return seconfFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Curent Weather";
                case 1:
                    return "Forecast";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        third_frameLayout = (FrameLayout) findViewById(R.id.third_frame);
        linearLayout.setVisibility(View.VISIBLE);
        third_frameLayout.setVisibility(View.INVISIBLE);

        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

}
