package kultprosvet.com.wheatherforecast.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kultprosvet.com.wheatherforecast.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int SET_CITY_REQ_CODE = 1;
    private MainFragment mMainFragment;
    private Toolbar mToolbar;
    private static final String BACKSTACK_TAG = "BACKSTACK_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpDrawer();

        showMainFragment();
    }

    private void setUpDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showMainFragment() {
        mMainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mMainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        switch(id) {
            case R.id.nav_add_city :
                showAddCityFragment();
                break;
            case R.id.nav_city_list :
                intent = new Intent(this, CityListActivity.class);
                startActivityForResult(intent, SET_CITY_REQ_CODE);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAddCityFragment() {
        AddCityFragment addCityFragment = new AddCityFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, addCityFragment);
        fragmentTransaction.addToBackStack(BACKSTACK_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
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

    public void onFragmentViewClick(View v) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && fragment.isVisible()) {
            if (fragment instanceof AddCityFragment) {
                ((AddCityFragment) fragment).saveCityToDb(v);
            }
        }
    }
}