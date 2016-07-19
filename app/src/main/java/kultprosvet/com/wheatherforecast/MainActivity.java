package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.squareup.picasso.Picasso;

import kultprosvet.com.wheatherforecast.api.ApiServiceBuilder;
import kultprosvet.com.wheatherforecast.models.Forecast16;
import kultprosvet.com.wheatherforecast.api.OpenWeatherApi;
import kultprosvet.com.wheatherforecast.models.TodayForecast;
import kultprosvet.com.wheatherforecast.databinding.ActivityMainBinding;
import kultprosvet.com.wheatherforecast.utils.WeatherIconSwitcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private OpenWeatherApi mService;
    private ForecastAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mService = ApiServiceBuilder.getApiService();

        getTodayForecast();

        getForecast16();
    }

    public void getTodayForecast() {
        mService.getTodayForecast(Config.LOCATION_DNEPR, Config.WEATHER_UNITS, Config.API_KEY)
                .enqueue(new Callback<TodayForecast>() {
                    @Override
                    public void onResponse(Call<TodayForecast> call, Response<TodayForecast> response) {
                        mBinding.setForecast(response.body());
                        getImage(response.body().getWeather().get(0).getMain());
                    }
                    @Override
                    public void onFailure(Call<TodayForecast> call, Throwable t) {
                        showAlertDialog(t);
                    }
                });
    }

    public void getForecast16() {
        mService.getForecast16(Config.LOCATION_DNEPR, Config.WEATHER_UNITS, Config.API_KEY)
                .enqueue(new Callback<Forecast16>() {
                             @Override
                             public void onResponse(Call<Forecast16> call, Response<Forecast16> response) {
                                 mAdapter = new ForecastAdapter();
                                 mAdapter.setItems(response.body().getForecastList());
                                 mBinding.recycleview.setAdapter(mAdapter);
                             }

                             @Override
                             public void onFailure(Call<Forecast16> call, Throwable t) {
                                 showAlertDialog(t);
                             }
                         }
                );
    }

    public void getImage(String weatherMainStatus) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int size = Math.round(metrics.density * 100);
        int icon = WeatherIconSwitcher.switchIcon(weatherMainStatus);
        Picasso.with(this).load(icon)
                .resize(size, size)
                .centerInside()
                .into(mBinding.icon);
    }

    public void showAlertDialog(Throwable t) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error")
                .setMessage(t.getLocalizedMessage())
                .setPositiveButton("Close", null)
                .show();
    }
}