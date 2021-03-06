package kultprosvet.com.wheatherforecast.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import kultprosvet.com.wheatherforecast.R;

public class WeatherIconSwitcher {
    private static final int ICON_SIZE_COEFF = 100;
    public static final String STORM = "Thunderstorm";
    public static final String RAIN = "Rain";
    public static final String CLOUDS = "Clouds";
    public static final String CLEAR = "Clear";
    public static final String ATMOSPHERE = "Atmosphere";
    public static final String MIST = "Mist";
    public static final String SNOW = "Snow";

    public static int getIconSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return Math.round(metrics.density * ICON_SIZE_COEFF);
    }

    public static int switchIcon(String status, int iconSetId) {
        int statusIcon;
        if(iconSetId == 1) {
            switch (status) {
                case STORM:
                    statusIcon = R.drawable.storm;
                    break;
                case RAIN:
                    statusIcon = R.drawable.rain;
                    break;
                case CLOUDS:
                    statusIcon = R.drawable.cloudy;
                    break;
                case CLEAR:
                    statusIcon = R.drawable.sun;
                    break;
                case ATMOSPHERE:
                    statusIcon = R.drawable.tide;
                    break;
                case MIST:
                    statusIcon = R.drawable.tide;
                    break;
                case SNOW:
                    statusIcon = R.drawable.snowing;
                    break;
                default:
                    statusIcon = R.drawable.calm;
                    break;
            }
        } else {
            switch (status) {
                case STORM:
                    statusIcon = R.drawable.storm2;
                    break;
                case RAIN:
                    statusIcon = R.drawable.rain2;
                    break;
                case CLOUDS:
                    statusIcon = R.drawable.cloudy2;
                    break;
                case CLEAR:
                    statusIcon = R.drawable.sun2;
                    break;
                case ATMOSPHERE:
                    statusIcon = R.drawable.tide2;
                    break;
                case MIST:
                    statusIcon = R.drawable.tide2;
                    break;
                case SNOW:
                    statusIcon = R.drawable.snowing2;
                    break;
                default:
                    statusIcon = R.drawable.calm2;
                    break;
            }
        }
        return statusIcon;
    }
}
