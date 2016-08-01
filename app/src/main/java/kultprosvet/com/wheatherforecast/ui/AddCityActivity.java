package kultprosvet.com.wheatherforecast.ui;

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
    private static final String CITY_NAME_CHECK_REGEX = "^[A-Z][a-z]+(?: [A-Z]\\.[A-Z]\\.?|(?: [A-Z][a-z]+)*)";
    private ActivityAddCityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_city);
    }

    public void saveCityToDb(View view) {
        CityDbDao cityDao = DBHelper.getSession(this).getCityDbDao();

        String enteredValue = mBinding.enteredCityName.getText().toString();
        if(validateCity(enteredValue)) {
            CityDb city = new CityDb();
            city.setName(enteredValue);
            cityDao.insert(city);
            finish();
        } else {
            wrongInputMsg();
        }
    }

    /**
     * Simple validating entered city name
     *
     * @param cityName name
     * @return true if checked
     */
    public boolean validateCity(String cityName) {
        return cityName.matches(CITY_NAME_CHECK_REGEX);
    }

    public void wrongInputMsg() {
        Toast.makeText(this, getString(R.string.wrong_city_name_msg_text), Toast.LENGTH_SHORT).show();
    }
}