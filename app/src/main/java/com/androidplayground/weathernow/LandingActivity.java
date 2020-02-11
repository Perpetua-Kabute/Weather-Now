package com.androidplayground.weathernow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
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
                    // System.out.println(response.body().weather);
                    Log.d("myTag", String.valueOf(response.body().weather));
                    WeatherResponse weatherResponse = response.body();
                    Gson gson = new Gson();
                    String weatherResponseJson = gson.toJson(weatherResponse);
                    Log.d("weatherResponse", weatherResponseJson);
                    assert weatherResponse != null;


                    TextView tempValue = (TextView) findViewById(R.id.temperature_status);
                    tempValue.setText(String.valueOf((int)(weatherResponse.main.temp - 273.15)) + " °C");

                    TextView skyStatus = findViewById(R.id.sky_status);
                    TextView windSpeed = findViewById(R.id.wind_speed);
                    TextView humidityValue = findViewById(R.id.humidity_value);
                    TextView precipitationValue = findViewById(R.id.precipitation_value);

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

//    // Here, thisActivity is the current activity
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
//            != PackageManager.PERMISSION_GRANTED) {
//
//        // Permission is not granted
//        // Should we show an explanation?
//        if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
//                Manifest.permission.READ_CONTACTS)) {
//            // Show an explanation to the user *asynchronously* -- don't block
//            // this thread waiting for the user's response! After the user
//            // sees the explanation, try again to request the permission.
//        } else {
//            // No explanation needed; request the permission
//            ActivityCompat.requestPermissions(thisActivity,
//                    new String[]{Manifest.permission.INTERNET},
//                    MY_PERMISSIONS_REQUEST_INTERNET);
//
//            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//            // app-defined int constant. The callback method gets the
//            // result of the request.
//        }
//    } else {
//        // Permission has already been granted
//    }
}
