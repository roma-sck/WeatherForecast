package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    //private OpenWeatherApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    public void getData(View view) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                //.client(client)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherApi service = retrofit.create(OpenWeatherApi.class);

        service.getTodayForecast("Dnipropetrovsk", "metric", "6549ddea7eea1f5e33c18d552b0c2837").enqueue(new Callback<TodayForecast>() {
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
        AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://www.psdgraphics.com/file/weather-icon.jpg");
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                binding.icon.setImageBitmap(bitmap);
            }
        };
        task.execute();
    }
}
