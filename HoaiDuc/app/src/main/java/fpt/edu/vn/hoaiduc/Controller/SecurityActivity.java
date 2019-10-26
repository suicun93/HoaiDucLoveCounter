package fpt.edu.vn.hoaiduc.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;

import fpt.edu.vn.hoaiduc.Common.AppConfig;
import fpt.edu.vn.hoaiduc.R;

public class SecurityActivity extends AppCompatActivity {

    private TextInputLayout _TextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        AppConfig.context = this;

        // SetupUI
        _TextInputLayout = findViewById(R.id.layoutInput);

        // Make color
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!AppConfig.isSecurity()) {
                    zoManHinhCHinh();
                } else {
                    checkPassword();
                }
            }
        }, 1500);
    }

    private EditText _passwordText;
    private String currentPassword = "";

    private void checkPassword() {
        // Make color ^^
        _TextInputLayout.setVisibility(View.VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                _TextInputLayout.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                _TextInputLayout.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        _TextInputLayout.startAnimation(fadeIn);


        // Get current password
        currentPassword = AppConfig.getPassword();
        _passwordText = findViewById(R.id.input_password);

        _passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = _passwordText.getText().toString();
                if (_passwordText.getText().toString().length() == 6) {
                    if (password.equals(currentPassword)) {
                        zoManHinhCHinh();
                    } else {
                        Toast.makeText(AppConfig.context, "Sai password rồi bạn :v", Toast.LENGTH_SHORT).show();
                        _passwordText.setText("");
                    }
                }
            }
        });
    }

    private void zoManHinhCHinh() {
        Intent intent = new Intent(AppConfig.context, CounterActivity.class);
        startActivity(intent);
        finish();
    }
}
