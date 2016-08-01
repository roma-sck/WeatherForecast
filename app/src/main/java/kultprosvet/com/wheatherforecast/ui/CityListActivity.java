package kultprosvet.com.wheatherforecast.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.databinding.ActivityCityListBinding;
import kultprosvet.com.wheatherforecast.db.CityDb;
import kultprosvet.com.wheatherforecast.db.CityDbDao;
import kultprosvet.com.wheatherforecast.db.DBHelper;

public class CityListActivity extends AppCompatActivity {
    private CityListAdapter mAdapter;
    private ActivityCityListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_list);
        setTitle("Cities List");

        setAdapter();

//        returnResult();
    }

    private void setAdapter() {
        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();
        List<CityDb> data = cityDao.queryBuilder().build().list();
        mAdapter = new CityListAdapter();
        mAdapter.setItems(data);
        mBinding.recycleviewCitylist.setAdapter(mAdapter);
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.intent_extra_city_name), "London");
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
