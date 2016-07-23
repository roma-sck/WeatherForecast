package kultprosvet.com.wheatherforecast.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.api.ApiServiceBuilder;
import kultprosvet.com.wheatherforecast.api.Config;
import kultprosvet.com.wheatherforecast.models.Forecast16;
import kultprosvet.com.wheatherforecast.api.OpenWeatherApi;
import kultprosvet.com.wheatherforecast.models.TodayForecast;
import kultprosvet.com.wheatherforecast.databinding.ActivityMainBinding;
import kultprosvet.com.wheatherforecast.utils.WeatherIconSwitcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private ActivityMainBinding mBinding;
    private OpenWeatherApi mService;
    private ForecastAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int LOCATION_REQUEST_INTERVAL = 1000;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);

        mService = ApiServiceBuilder.getApiService();

        initSwipeToRefresh();

        getTodayForecast();
        getForecast16();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = null;
        try{
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException se) {
            System.out.println("permission is not available");
        }
        if (location != null) {
            showLocationData(location);
        }
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        } catch (SecurityException se) {
            System.out.println("permission is not available");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //not needed
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //not needed
    }

    @Override
    protected void onStart() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            initGoogleApiClient();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
        }
        super.onStart();
    }

    public void initGoogleApiClient() {
        if (mGoogleApiClient ==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        showLocationData(location);
    }

    public void showLocationData(Location location) {
        mBinding.latitude.setText(String.format("%.6f", location.getLatitude()));
        mBinding.longitude.setText(String.format("%.6f", location.getLongitude()));
        mBinding.height.setText(String.format("%.2f", location.getAltitude()));
        mBinding.accuracy.setText(String.format("%.2f", location.getAccuracy()));

        mLocation = location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allGranted = true;
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                }
            }

            if (allGranted) {
                initGoogleApiClient();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("Application wouldn't work witount location")
                        .setPositiveButton("Close app", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        })
                        .show();
            }

        }
    }

    public void getTodayForecast() {
        String latitude = Config.LOCATION_DNIPRO_LATITUDE;
        String longitude = Config.LOCATION_DNIPRO_LONGITUDE;
        if(mLocation != null) {
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLongitude());
        }

        mService.getTodayForecast(latitude, longitude, Config.WEATHER_UNITS, Config.API_KEY)
                .enqueue(new Callback<TodayForecast>() {
                    @Override
                    public void onResponse(Call<TodayForecast> call, Response<TodayForecast> response) {
                        mBinding.setForecast(response.body());
                        getIcon(response.body().getWeather().get(0).getMain());
                    }
                    @Override
                    public void onFailure(Call<TodayForecast> call, Throwable t) {
                        showAlertDialog(t);
                    }
                });
        mSwipeRefreshLayout.setRefreshing(false);
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void getIcon(String weatherMainStatus) {
        int size = WeatherIconSwitcher.getIconSize(this);
        int icon = WeatherIconSwitcher.switchIcon(weatherMainStatus);
        Picasso.with(this).load(icon)
                .resize(size, size)
                .centerInside()
                .into(mBinding.icon);
    }

    private void initSwipeToRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTodayForecast();
                getForecast16();
            }
        });
    }

    public void showAlertDialog(Throwable t) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.error_dailog_title))
                .setMessage(t.getLocalizedMessage())
                .setPositiveButton(getString(R.string.error_dailog_btn_text), null)
                .show();
    }
}