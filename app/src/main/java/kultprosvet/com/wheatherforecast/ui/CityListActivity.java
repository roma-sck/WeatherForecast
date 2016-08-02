package kultprosvet.com.wheatherforecast.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.databinding.ActivityCityListBinding;
import kultprosvet.com.wheatherforecast.db.CityDb;
import kultprosvet.com.wheatherforecast.db.CityDbDao;
import kultprosvet.com.wheatherforecast.db.DBHelper;
import kultprosvet.com.wheatherforecast.utils.CityDbItemClickListener;
import kultprosvet.com.wheatherforecast.utils.SimpleDividerItemDecoration;

public class CityListActivity extends AppCompatActivity {
    private ActivityCityListBinding mBinding;
    private List<CityDb> mData;
    public static final String CURRENT_LOCATION = "CLICKED_CURRENT_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_list);
        setTitle(getString(R.string.city_list_activity_title));

        setCurrentLocOnClick();
        setAdapter();
    }

    private void setCurrentLocOnClick() {
        mBinding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult(CURRENT_LOCATION);
            }
        });
    }

    private void setAdapter() {
        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();
        mData = cityDao.queryBuilder().build().list();

        CityListAdapter adapter = new CityListAdapter();
        adapter.setItems(mData);
        mBinding.recycleviewCitylist.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()));
        mBinding.recycleviewCitylist.setAdapter(adapter);
        mBinding.recycleviewCitylist.addOnItemTouchListener(
                new CityDbItemClickListener(this, new OnRecyclerItemClickListener()));
    }

    private class OnRecyclerItemClickListener extends CityDbItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
            if(mData != null && mData.size() > 0) {
                String name = mData.get(position).getName();
                returnResult(name);
            }
        }

        @Override
        public void onItemLongPress(View childView, int position) {
            CityDbDao cityDao = DBHelper.getSession(CityListActivity.this).getCityDbDao();
            cityDao.deleteByKey(mData.get(position).getId());
            setAdapter();
        }
    }

    private void returnResult(String cityName) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.intent_extra_city_name), cityName);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
