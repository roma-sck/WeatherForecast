package kultprosvet.com.wheatherforecast.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("weather")
    Call<TodayForecast> getTodayForecast(@Query("q") String q, @Query("units") String units, @Query("APPID") String appid);

    @GET("forecast/daily")
    Call<Forecast16> getForecast16(@Query("q") String q, @Query("units") String units, @Query("APPID") String appid);

//    @GET("weather")
//    Call<JsonElement> getTodayForecastJson(@Query("q") String q, @Query("units") String units, @Query("APPID") String appid);
}
