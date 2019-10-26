package fpt.edu.vn.hoaiduc.Common;

import android.content.Context;
import android.widget.Toast;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import fpt.edu.vn.hoaiduc.KeyPair.Images;
import fpt.edu.vn.hoaiduc.KeyPair.Texts;

public class AppConfig {
    // Texts
    public static String trenCung = "Yêu thương đong đầy";
    public static String donVi = "Days";
    public static String name1 = "Đụt Đụt";
    public static String name2 = "Hoài Hoài";
    public static String birthday1 = "19-10-1993";
    public static String birthday2 = "29-08-1997";
    public static String anniversaryDate = "15-05-2019";
    // Images
    public static String ava1 = "";
    public static String ava2 = "";
    public static String background = "";

    public static Context context;

    public static void save(String trenCungTemp, String donViTemp, String name1Temp, String name2Temp, String birthday1Temp, String birthday2Temp, String anniversaryDateTemp, String ava1Temp, String ava2Temp, String backgroundTemp) {
        PreferenceController PreferenceController = new PreferenceController(context);
        // Save Texts
        PreferenceController.save(Texts.TEXT_TREN.name(), trenCungTemp);
        PreferenceController.save(Texts.DON_VI.name(), donViTemp);
        PreferenceController.save(Texts.ANNIVERSARY_DATE.name(), anniversaryDateTemp);
        PreferenceController.save(Texts.BIRTHDAY_1.name(), birthday1Temp);
        PreferenceController.save(Texts.BIRTHDAY_2.name(), birthday2Temp);
        PreferenceController.save(Texts.NAME_1.name(), name1Temp);
        PreferenceController.save(Texts.NAME_2.name(), name2Temp);
        // Save Images
        PreferenceController.save(Images.Avatar1.name(), ava1Temp);
        PreferenceController.save(Images.Avatar2.name(), ava2Temp);
        PreferenceController.save(Images.Background.name(), backgroundTemp);
    }

    public static void load() {
        PreferenceController PreferenceController = new PreferenceController(context);
        // Load Texts
        trenCung = PreferenceController.get(Texts.TEXT_TREN.name(), trenCung);
        donVi = PreferenceController.get(Texts.DON_VI.name(), donVi);
        anniversaryDate = PreferenceController.get(Texts.ANNIVERSARY_DATE.name(), anniversaryDate);
        name1 = PreferenceController.get(Texts.NAME_1.name(), name1);
        name2 = PreferenceController.get(Texts.NAME_2.name(), name2);
        birthday1 = PreferenceController.get(Texts.BIRTHDAY_1.name(), birthday1);
        birthday2 = PreferenceController.get(Texts.BIRTHDAY_2.name(), birthday2);
        // Load Images
        ava1 = PreferenceController.get(Images.Avatar1.name(), ava1);
        ava2 = PreferenceController.get(Images.Avatar2.name(), ava2);
        background = PreferenceController.get(Images.Background.name(), background);
    }

    public static long countDate() {
        // Start and End
        String dateBeforeString = anniversaryDate;
        // Parsing the date
        Date dateAfter = DateTimeFormat.currentTime;
        Date dateBefore = DateTimeFormat.strToDate(dateBeforeString);
        // Calculating number of days in between
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore.toInstant(), dateAfter.toInstant());
        // Displaying the number of days
        return noOfDaysBetween + 1;
    }

    public static String countYear(String date) {
        // Start and End
        String dateBeforeString = date;
        //Parsing the date
        Date dateAfter = DateTimeFormat.currentTime;
        Date dateBefore = DateTimeFormat.strToDate(dateBeforeString);
        //Calculating number of days in between
        long noOfDaysBetween = dateAfter.getYear() - dateBefore.getYear();
        //Displaying the number of days
        return noOfDaysBetween + "";
    }

    public static String getPassword() {
        PreferenceController PreferenceController = new PreferenceController(context);
        return PreferenceController.get(Texts.PASSWORD.name(), "123456");
    }

    public static boolean isSecurity() {
        PreferenceController PreferenceController = new PreferenceController(context);
        return PreferenceController.get(Texts.SECURITY.name(), "false").equals("true");
    }

    public static void savePassword(boolean security, String password) {
        PreferenceController PreferenceController = new PreferenceController(context);
        if (security) {
            if (password.length() != 6) {
                Toast.makeText(context, "Password can co 6 ki tu", Toast.LENGTH_SHORT).show();
            } else {
                PreferenceController.save(Texts.SECURITY.name(), "true");
                PreferenceController.save(Texts.PASSWORD.name(), password);
            }
        } else {
            PreferenceController.save(Texts.SECURITY.name(), "false");
        }
    }



}
