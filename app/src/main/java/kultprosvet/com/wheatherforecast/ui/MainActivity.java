package kultprosvet.com.wheatherforecast.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import kultprosvet.com.wheatherforecast.R;

public class MainActivity extends AppCompatActivity {

    public static final int SET_CITY_REQ_CODE = 1;
    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragmentMain();
    }

    private void showFragmentMain() {
        mMainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mMainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.add_city:
                intent = new Intent(this, AddCityActivity.class);
                startActivity(intent);
                break;
            case R.id.city_list:
                intent = new Intent(this, CityListActivity.class);
                startActivityForResult(intent, SET_CITY_REQ_CODE);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SET_CITY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                String city = data.getStringExtra(getString(R.string.intent_extra_city_name));
                if(city.equals(CityListActivity.CURRENT_LOCATION)) {
                    mMainFragment.getTodayForecast(mMainFragment.getLatitude(), mMainFragment.getLongitude());
                    mMainFragment.getForecast16(mMainFragment.getLatitude(), mMainFragment.getLongitude());
                } else {
                    mMainFragment.getTodayForecast(city, null);
                    mMainFragment.getForecast16(city, null);
                }
            }
        }
    }
}