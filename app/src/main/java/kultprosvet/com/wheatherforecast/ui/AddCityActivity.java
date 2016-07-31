package kultprosvet.com.wheatherforecast.ui;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.databinding.ActivityAddCityBinding;
import kultprosvet.com.wheatherforecast.db.CityDb;
import kultprosvet.com.wheatherforecast.db.CityDbDao;
import kultprosvet.com.wheatherforecast.db.DBHelper;

public class AddCityActivity extends AppCompatActivity {
    private ActivityAddCityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_city);
    }

    public void saveCityToDb(View view) {
        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();
        CityDb city = new CityDb();
        city.setName(binding.text.getText().toString());
        cityDao.insert(city);
        Toast.makeText(this,"City saved", Toast.LENGTH_LONG).show();
        finish();
    }
}