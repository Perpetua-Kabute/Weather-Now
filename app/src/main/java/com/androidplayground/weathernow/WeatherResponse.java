package com.androidplayground.weathernow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {
    @SerializedName("coord")
    public Coord coord;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("weather")
    public ArrayList<Weather> weather= new ArrayList<Weather>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("name")
    public String name;

    class Coord{
        @SerializedName("lon")
        public float lon;
        @SerializedName("lat")
        public float lat;
    }
    class Weather{
        @SerializedName("main")
        public String main;

    }
    class Main{
        @SerializedName("temp")
        public float temp;
        @SerializedName("humidity")
        public float humidity;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;
    }
   class Wind{
        @SerializedName("speed")
        public  float speed;
   }
   class Sys{
        @SerializedName("country")
       public String country;
   }
}
