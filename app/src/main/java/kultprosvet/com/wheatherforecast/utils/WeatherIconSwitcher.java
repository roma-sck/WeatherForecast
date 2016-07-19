package kultprosvet.com.wheatherforecast.utils;

import kultprosvet.com.wheatherforecast.R;

public class WeatherIconSwitcher {
    private static int mStatusIcon;

    public static int switchIcon(String status) {
        switch (status) {
            case "Thunderstorm":
                mStatusIcon = R.drawable.storm;
                break;
            case "Rain":
                mStatusIcon = R.drawable.rain;
                break;
            case "Clouds":
                mStatusIcon = R.drawable.cloudy;
                break;
            case "Clear":
                mStatusIcon = R.drawable.sun;
                break;
            case "Atmosphere":
                mStatusIcon = R.drawable.wind;
                break;
            case "Snow":
                mStatusIcon = R.drawable.snowflake;
                break;
            default: mStatusIcon = R.drawable.wind;
                break;
        }
        return mStatusIcon;
    }
}
