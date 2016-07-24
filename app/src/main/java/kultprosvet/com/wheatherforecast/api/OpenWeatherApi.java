package kultprosvet.com.wheatherforecast.api;

import kultprosvet.com.wheatherforecast.models.Forecast16;
import kultprosvet.com.wheatherforecast.models.TodayForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("weather")
    Call<TodayForecast> getTodayForecast(@Query("lat") String lat,
                                         @Query("lon") String lon,
                                         @Query("units") String units,
                                         @Query("APPID") String appid);

    @GET("forecast/daily")
    Call<Forecast16> getForecast16(@Query("lat") String lat,
                                   @Query("lon") String lon,
                                   @Query("units") String units,
                                   @Query("APPID") String appid);
}
