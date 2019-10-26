package fpt.edu.vn.hoaiduc.Controller;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import fpt.edu.vn.hoaiduc.Common.AppConfig;
import fpt.edu.vn.hoaiduc.R;

public class Setting extends AppCompatActivity {

    EditText trenCung;
    EditText donVi;
    EditText name1;
    EditText name2;
    EditText age1;
    EditText age2;
    EditText kiNiem;
    EditText ava1Text;
    EditText ava2Text;
    EditText backgroundText;
    EditText passwordText;
    LinearLayout passwordLayout;
    Switch passwordSwitch;
    NestedScrollView nestedScrollView;
    final int AVATAR1 = 19;
    final int AVATAR2 = 29;
    final int BACKGROUND = 39;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fill to UI
        SetupUI();

        // Save button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfig.save(
                        trenCung.getText().toString(),
                        donVi.getText().toString(),
                        name1.getText().toString(),
                        name2.getText().toString(),
                        age1.getText().toString(),
                        age2.getText().toString(),
                        kiNiem.getText().toString(),
                        ava1Text.getText().toString(),
                        ava2Text.getText().toString(),
                        backgroundText.getText().toString()
                );
                if (passwordSwitch.isChecked()) {
                    if (passwordText.getText().toString().length() != 6) {
                        Toast.makeText(AppConfig.context, "Password must be 6 characters. Not enough nên là removed.", Toast.LENGTH_SHORT).show();
                    } else {
                        AppConfig.savePassword(true, passwordText.getText().toString());
                    }
                }
                final Intent data = new Intent();
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    private void pickFromGallery(int code) {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String selectedImagePath = getPath(this, selectedImage);
            switch (requestCode) {
                case AVATAR1:
                    ava1Text.setText(selectedImagePath);
                    break;
                case AVATAR2:
                    ava2Text.setText(selectedImagePath);
                    break;
                case BACKGROUND:
                    backgroundText.setText(selectedImagePath);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private void SetupUI() {
        trenCung = findViewById(R.id.trenCungEdit);
        donVi = findViewById(R.id.donViEdit);
        name1 = findViewById(R.id.name1Edit);
        name2 = findViewById(R.id.name2Edit);
        age1 = findViewById(R.id.ngaySinh1Edit);
        age2 = findViewById(R.id.ngaySinh2Edit);
        kiNiem = findViewById(R.id.kiNiemEdit);
        ava1Text = findViewById(R.id.ava1);
        ava2Text = findViewById(R.id.ava2);
        backgroundText = findViewById(R.id.backgroundText);
        passwordText = findViewById(R.id.passwordText);
        passwordLayout = findViewById(R.id.passwordLayout);
        passwordSwitch = findViewById(R.id.passwordSwitch);
        nestedScrollView = findViewById(R.id.ahihi);

        // Texts
        trenCung.setText(AppConfig.trenCung);
        donVi.setText(AppConfig.donVi);
        name1.setText(AppConfig.name1);
        name2.setText(AppConfig.name2);
        age1.setText(AppConfig.birthday1);
        age2.setText(AppConfig.birthday2);
        kiNiem.setText(AppConfig.anniversaryDate);

        // Images
        ava1Text.setText(AppConfig.ava1.equals("") ? "Mặc định" : AppConfig.ava1);
        ava2Text.setText(AppConfig.ava2.equals("") ? "Mặc định" : AppConfig.ava2);
        backgroundText.setText(AppConfig.background.equals("") ? "Mặc định" : AppConfig.background);
        // 3 cai anh
        ava1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery(AVATAR1);
            }
        });
        ava2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery(AVATAR2);
            }
        });
        backgroundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery(BACKGROUND);
            }
        });

        // Security
        passwordSwitch.setChecked(AppConfig.isSecurity());
        passwordLayout.setVisibility(AppConfig.isSecurity() ? View.VISIBLE : View.GONE);
        passwordText.setText(AppConfig.getPassword());

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passwordText.getText().toString().length() == 6) {
                    AppConfig.savePassword(true, passwordText.getText().toString());
                    passwordText.setEnabled(false);
                    Toast.makeText(Setting.this, "Password was saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        passwordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Make color
                    makeColor(true);
                    final int scrollViewHeight = nestedScrollView.getHeight();
                    if (scrollViewHeight > 0) {
                        //nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        final View lastView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                        final int lastViewBottom = lastView.getBottom() + nestedScrollView.getPaddingBottom();
                        final int deltaScrollY = lastViewBottom - scrollViewHeight - nestedScrollView.getScrollY();
                        /* If you want to see the scroll animation, call this. */
                        nestedScrollView.smoothScrollBy(0, deltaScrollY);
//                        /* If you don't want, call this. */
//                        nestedScrollView.scrollBy(0, deltaScrollY);
                    }
                } else {
                    makeColor(false);
                    AppConfig.savePassword(false, "123456");
                    Toast.makeText(Setting.this, "Password has been removed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void makeColor(boolean visible) {
        passwordLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        Animation fade = visible ? new AlphaAnimation(0, 1) : new AlphaAnimation(1, 0);
        fade.setDuration(1000);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                passwordText.setEnabled(false);
                passwordLayout.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                passwordLayout.setEnabled(true);
                passwordText.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        passwordLayout.startAnimation(fade);
    }

}
