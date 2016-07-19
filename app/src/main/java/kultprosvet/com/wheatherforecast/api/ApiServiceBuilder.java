package kultprosvet.com.wheatherforecast.api;

import kultprosvet.com.wheatherforecast.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceBuilder {

    private static final Retrofit.Builder RETROFIT = new Retrofit.Builder()
            .baseUrl(Config.API_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static OpenWeatherApi getApiService() {
        return RETROFIT.build().create(OpenWeatherApi.class);
    }
}
