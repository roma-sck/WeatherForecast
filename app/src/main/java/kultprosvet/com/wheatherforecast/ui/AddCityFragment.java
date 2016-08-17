package kultprosvet.com.wheatherforecast.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.databinding.FragmentAddCityBinding;
import kultprosvet.com.wheatherforecast.db.CityDb;
import kultprosvet.com.wheatherforecast.db.CityDbDao;
import kultprosvet.com.wheatherforecast.db.DBHelper;

public class AddCityFragment extends Fragment {
    private static final String CITY_NAME_CHECK_REGEX = "^[A-Z][a-z]+(?: [A-Z]\\.[A-Z]\\.?|(?: [A-Z][a-z]+)*)";
    private FragmentAddCityBinding mBinding;

    public AddCityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_city, container, false);
        return mBinding.getRoot();
    }

    public void saveCityToDb(View view) {
        if(view.getId() == R.id.button_add_city) {
            CityDbDao cityDao = DBHelper.getSession(getActivity()).getCityDbDao();

            String enteredValue = mBinding.enteredCityName.getText().toString();
            if (validateCity(enteredValue)) {
                CityDb city = new CityDb();
                city.setName(enteredValue);
                cityDao.insert(city);

                removeFragment();
            } else {
                wrongInputMsg();
            }
        }
    }

    private void removeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
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
        Toast.makeText(getActivity(), getString(R.string.wrong_city_name_msg_text), Toast.LENGTH_SHORT).show();
    }
}
