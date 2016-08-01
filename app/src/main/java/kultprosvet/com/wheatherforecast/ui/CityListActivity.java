package kultprosvet.com.wheatherforecast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.db.CityDb;
import kultprosvet.com.wheatherforecast.db.CityDbDao;
import kultprosvet.com.wheatherforecast.db.DBHelper;

public class CityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();
        List<CityDb> data = cityDao.queryBuilder().build().list();

        for(int i = 0; i < data.size(); i++) {
            data.get(i).getName();
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.intent_extra_city_name), "London");
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
