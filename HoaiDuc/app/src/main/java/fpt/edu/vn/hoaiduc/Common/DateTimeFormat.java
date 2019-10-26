package fpt.edu.vn.hoaiduc.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFormat {
    public static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static Date currentTime = Calendar.getInstance().getTime();

    public static Date strToDate(String date) {
        try {
            return SimpleDateFormat.parse(date);
        } catch (Exception e) {
            return currentTime;
        }
    }

    public static String dateToStr(Date date) {
        return SimpleDateFormat.format(date);
    }
}
