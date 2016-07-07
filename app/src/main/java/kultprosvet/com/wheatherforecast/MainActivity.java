package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
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

import kultprosvet.com.wheatherforecast.api.TodayForecast;
import kultprosvet.com.wheatherforecast.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
    }
    public void getData(View view){
        AsyncTask<String,Void,TodayForecast> task=new AsyncTask<String, Void, TodayForecast>() {
            String error;
            @Override
            protected TodayForecast doInBackground(String... strings) {
                URL url = null;
                try {
                    url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+strings[0]+"&units=metric&APPID=6549ddea7eea1f5e33c18d552b0c2837");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                    Gson gson=new Gson();
                    return gson.fromJson(total.toString(),TodayForecast.class);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    error=e.getLocalizedMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    error=e.getLocalizedMessage();
                }


                return null;
            }

            @Override
            protected void onPostExecute(TodayForecast forecast) {
                if (forecast==null){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage(error)
                            .setPositiveButton("Close",null)
                            .show();
                }else {
                    binding.setForecast(forecast);
                    binding.notifyChange();
                }


            }
        };
        task.execute("Dnipropetrovsk");
    }
}
