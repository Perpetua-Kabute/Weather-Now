package com.androidplayground.weathernow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "deea22f0e137903c04bad3ef2de3b319";
    public static String lat = "-1.09934";
    public static String lon = "37.0248";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        TextView timeNow = findViewById(R.id.time_now);
        timeNow.setText(date);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        getCurrentData();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    Gson gson = new Gson();
                    String weatherResponseJson = gson.toJson(weatherResponse);
                    Log.d("weatherResponse", weatherResponseJson);
                    assert weatherResponse != null;


                    TextView tempValue = (TextView) findViewById(R.id.temperature_status);
                    tempValue.setText(String.valueOf((int)(weatherResponse.main.temp - 273.15)) + " °C");

                    TextView skyStatus = findViewById(R.id.sky_status);
                    skyStatus.setText(weatherResponse.weather.get(0).description);
                    TextView windSpeed = findViewById(R.id.wind_speed);
                    windSpeed.setText(String.valueOf(weatherResponse.wind.speed) + "m/s");
                    TextView humidityValue = findViewById(R.id.humidity_value);
                    humidityValue.setText(String.valueOf(weatherResponse.main.humidity));
                    TextView tempRange = findViewById(R.id.temp_range);
                    tempRange.setText(String.valueOf((int)(weatherResponse.main.temp_max - 273.15)) +"/" + String.valueOf((int)(weatherResponse.main.temp_min - 273.15)) + " °C");
                    TextView locationName = findViewById(R.id.location_name);
                    locationName.setText(weatherResponse.name);

                    String iconUrl = "http://openweathermap.org/img/w/" + weatherResponse.weather.get(0).icon + ".png";
                    ImageView weatherIcon = findViewById(R.id.weather_icon);
                    Picasso.get().load(iconUrl).into(weatherIcon);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                TextView tempValue = (TextView) findViewById(R.id.temperature_status);
                tempValue.setText("123");
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                t.getMessage();
                // Log.e("1EXCEPTION", t.getMessage());
            }
        });
    }


}
