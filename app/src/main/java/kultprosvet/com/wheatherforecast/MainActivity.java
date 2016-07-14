package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
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

    ActivityMainBinding binding;
    private Retrofit retrofit;
    private OpenWeatherApi service;
    private ForecastAdapter adapter;
    //private OpenWeatherApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         service = retrofit.create(OpenWeatherApi.class);

        service.getForecast16("Dnipropetrovsk", "metric", Config.API_KEY).enqueue(
                new Callback<Forecast16>() {
                    @Override
                    public void onResponse(Call<Forecast16> call, Response<Forecast16> response) {
                        adapter=new ForecastAdapter();
                        adapter.setItems(response.body().getForecastList());
                        binding.recycleview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<Forecast16> call, Throwable t) {

                    }
                }
        );


    }

    public void getData(View view) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();





        service.getTodayForecast("Dnipropetrovsk", "metric", Config.API_KEY).enqueue(new Callback<TodayForecast>() {
            @Override
            public void onResponse(Call<TodayForecast> call, Response<TodayForecast> response) {
                binding.setForecast(response.body());
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
    public void getImage(View view) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int size=Math.round(metrics.density*200);
        Picasso.with(this).load("http://img.4k-wallpaper.net/wide_1610/mountains-landscape_120.jpeg")
                .resize(size,size)
                .centerInside()
                .into(binding.icon);
    }
}
