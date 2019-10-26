package fpt.edu.vn.hoaiduc.Controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.civ.CircleImageView;

import java.io.File;

import fpt.edu.vn.hoaiduc.Common.AppConfig;
import fpt.edu.vn.hoaiduc.Common.Horoscope;
import fpt.edu.vn.hoaiduc.R;

public class CounterActivity extends AppCompatActivity {

    private TextView trenCungText, donViText, counterText, nameText1, nameText2, ageText1, ageText2, zodiacText1, zodiacText2;
    private CircleImageView ava1, ava2;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        AppConfig.context = this;
        AssignAllResources();
        SetupUI();
        ImageButton settingButton = (ImageButton) findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mo ra 1 trang moi de setting
                Intent intent = new Intent();
                intent.setClass(AppConfig.context, Setting.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void AssignAllResources() {

        // Text View
        trenCungText = (TextView) findViewById(R.id.trenCungText);
        donViText = (TextView) findViewById(R.id.donViText);
        counterText = (TextView) findViewById(R.id.counterText);
        nameText1 = (TextView) findViewById(R.id.nameText1);
        nameText2 = (TextView) findViewById(R.id.nameText2);
        ageText1 = (TextView) findViewById(R.id.age1);
        ageText2 = (TextView) findViewById(R.id.age2);
        zodiacText1 = (TextView) findViewById(R.id.holo1);
        zodiacText2 = (TextView) findViewById(R.id.holo2);

        // Avatar
        ava1 = (CircleImageView) findViewById(R.id.avatar1);
        ava2 = (CircleImageView) findViewById(R.id.avatar2);
    }

    private void SetupUI() {
        // Setup text
        AppConfig.load();
        trenCungText.setText(AppConfig.trenCung);
        donViText.setText(AppConfig.donVi);
        counterText.setText(AppConfig.countDate() + "");
        nameText1.setText(AppConfig.name1);
        nameText2.setText(AppConfig.name2);
        ageText1.setText(AppConfig.countYear(AppConfig.birthday1));
        ageText2.setText(AppConfig.countYear(AppConfig.birthday2));
        zodiacText1.setText(Horoscope.getInstance().getZodiac(AppConfig.birthday1));
        zodiacText2.setText(Horoscope.getInstance().getZodiac(AppConfig.birthday2));
        // Setup Mau

        // Setup Anh
        CheckPermission();
        File imgFile1 = new File(AppConfig.ava1);
        if (imgFile1.exists()) {
            ava1.setImageURI(Uri.fromFile(imgFile1));
        }
        File imgFile2 = new File(AppConfig.ava2);
        if (imgFile2.exists()) {
            ava2.setImageURI(Uri.fromFile(imgFile2));
        }

        File backgroundFile = new File(AppConfig.background);
        if (backgroundFile.exists()) {
            Drawable d = Drawable.createFromPath(backgroundFile.getAbsolutePath());
            findViewById(R.id.background).setBackground(d);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                SetupUI();
            } else {
                Toast.makeText(this, "Cài đặt chưa được lưu", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
