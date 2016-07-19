package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.squareup.picasso.Picasso;

import kultprosvet.com.wheatherforecast.api.Forecast16;
import kultprosvet.com.wheatherforecast.api.OpenWeatherApi;
import kultprosvet.com.wheatherforecast.api.TodayForecast;
import kultprosvet.com.wheatherforecast.databinding.ActivityMainBinding;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Retrofit retrofit;
    private OpenWeatherApi service;
    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OpenWeatherApi.class);

        getTodayForecast();

        getForecast16();
    }

    public void getTodayForecast() {
        service.getTodayForecast(Config.LOCATION_DNEPR, Config.WEATHER_UNITS, Config.API_KEY)
                .enqueue(new Callback<TodayForecast>() {
                    @Override
                    public void onResponse(Call<TodayForecast> call, Response<TodayForecast> response) {
                        binding.setForecast(response.body());
                        System.out.println(response.body().getWeather().get(0).getMain());
                        getImage();
                    }
                    @Override
                    public void onFailure(Call<TodayForecast> call, Throwable t) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Error")
                                .setMessage(t.getLocalizedMessage())
                                .setPositiveButton("Close", null)
                                .show();
                    }
                });
    }

    public void getForecast16() {
        service.getForecast16(Config.LOCATION_DNEPR, Config.WEATHER_UNITS, Config.API_KEY)
                .enqueue(new Callback<Forecast16>() {
                             @Override
                             public void onResponse(Call<Forecast16> call, Response<Forecast16> response) {
                                 adapter = new ForecastAdapter();
                                 adapter.setItems(response.body().getForecastList());
                                 binding.recycleview.setAdapter(adapter);
                             }

                             @Override
                             public void onFailure(Call<Forecast16> call, Throwable t) {

                             }
                         }
                );
    }

    public void getImage() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int size = Math.round(metrics.density * 100);
        Picasso.with(this).load(R.drawable.cloud)
                .resize(size, size)
                .centerInside()
                .into(binding.icon);
    }
}