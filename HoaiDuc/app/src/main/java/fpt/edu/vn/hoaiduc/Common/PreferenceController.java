package fpt.edu.vn.hoaiduc.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceController {

    private SharedPreferences sharedPreferences;

    public PreferenceController(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
