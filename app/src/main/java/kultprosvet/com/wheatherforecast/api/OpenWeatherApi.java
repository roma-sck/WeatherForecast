package kultprosvet.com.wheatherforecast.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Stanislav Volnyansky on 12.07.16.
 */
public interface OpenWeatherApi {
    @GET("weather")
    Call<TodayForecast> getTodayForecast(@Query("q") String q, @Query("units") String units, @Query("APP_ID") String appid);
}
