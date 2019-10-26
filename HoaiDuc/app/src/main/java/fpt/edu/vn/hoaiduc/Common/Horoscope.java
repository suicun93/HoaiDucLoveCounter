package fpt.edu.vn.hoaiduc.Common;

import java.util.Date;

public class Horoscope {
    private static final Horoscope ourInstance = new Horoscope();

    public static Horoscope getInstance() {
        return ourInstance;
    }

    private Horoscope() {
    }

    public String getZodiac(String dateTime) {
        int month = 1;
        int day = 1;
        String sign = "";
        Date date = DateTimeFormat.strToDate(dateTime);
        month = date.getMonth() + 1;
        day = date.getDate();
        if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day >= 1 && day <= 19))
            sign = "Capricorn";
        else if ((month == 1 && day >= 20 && day <= 31) || (month == 2 && day >= 1 && day <= 17))
            sign = "Aquarius";
        else if ((month == 2 && day >= 18 && day <= 29) || (month == 3 && day >= 1 && day <= 19))
            sign = "Pisces";
        else if ((month == 3 && day >= 20 && day <= 31) || (month == 4 && day >= 1 && day <= 19))
            sign = "Aries";
        else if ((month == 4 && day >= 20 && day <= 30) || (month == 5 && day >= 1 && day <= 20))
            sign = "Taurus";
        else if ((month == 5 && day >= 21 && day <= 31) || (month == 6 && day >= 1 && day <= 20))
            sign = "Gemini";
        else if ((month == 6 && day >= 21 && day <= 30) || (month == 7 && day >= 1 && day <= 22))
            sign = "Cancer";
        else if ((month == 7 && day >= 23 && day <= 31) || (month == 8 && day >= 1 && day <= 22))
            sign = "Leo";
        else if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day >= 1 && day <= 22))
            sign = "Virgo";
        else if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22))
            sign = "Libra";
        else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21))
            sign = "Scorpio";
        else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21))
            sign = "Sagittarius";
        else
            sign = "Illegal date";
        return sign;
    }
}
