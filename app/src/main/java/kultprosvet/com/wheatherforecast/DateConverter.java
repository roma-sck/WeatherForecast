package kultprosvet.com.wheatherforecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String DATE_FORMAT = "d MMM yyyy";
    private static final int SEC_TO_MILLIS_COEFFICIENT = 1000;
    private static final String LOCALE_LANGUAGE = "ru";
    private static final String LOCALE_REGION = "UA";

    public static String convertDate(long dateInSeconds) {
        long dateInMillis = dateInSeconds * SEC_TO_MILLIS_COEFFICIENT;
        Date date = new Date(dateInMillis);
        Locale ukrLocale = new Locale(LOCALE_LANGUAGE, LOCALE_REGION);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, ukrLocale);
        String result = simpleDateFormat.format(date);
        return result;
    }
}
