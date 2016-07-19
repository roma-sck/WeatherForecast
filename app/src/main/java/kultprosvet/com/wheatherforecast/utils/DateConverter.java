package kultprosvet.com.wheatherforecast.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final String DATE_FORMAT = "d MMM yyyy";
    private static final int SEC_TO_MILLIS_COEFFICIENT = 1000;
    private static final String LOCALE_LANGUAGE = "en";
    private static final String LOCALE_REGION = "UA";

    public static String convertDate(long dateInSeconds) {
        long dateInMillis = dateInSeconds * SEC_TO_MILLIS_COEFFICIENT;
        Date date = new Date(dateInMillis);
        Locale locale = new Locale(LOCALE_LANGUAGE, LOCALE_REGION);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
        return simpleDateFormat.format(date);
    }
}
