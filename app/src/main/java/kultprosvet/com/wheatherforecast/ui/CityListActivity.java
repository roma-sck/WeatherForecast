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

public class CityListActivity extends AppCompatActivity {
    private CityListAdapter mAdapter;
    private ActivityCityListBinding mBinding;
    private List<CityDb> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_list);
        setTitle(getString(R.string.city_list_activity_title));

        setAdapter();
    }

    private void setAdapter() {
        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();
        mData = cityDao.queryBuilder().build().list();
        mAdapter = new CityListAdapter();
        mAdapter.setItems(mData);
        mBinding.recycleviewCitylist.setAdapter(mAdapter);
        mBinding.recycleviewCitylist.addOnItemTouchListener(
                new CityDbItemClickListener(this, new OnRecyclerItemClickListener()));
    }


    private void returnResult(String cityName) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.intent_extra_city_name), cityName);
        setResult(RESULT_OK, returnIntent);
        finish();
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
}
