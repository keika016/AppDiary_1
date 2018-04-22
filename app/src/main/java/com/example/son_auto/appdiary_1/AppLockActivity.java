package com.example.son_auto.appdiary_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class AppLockActivity extends AppCompatActivity {

    private Button btnNhap, btnDelete;
    private EditText edtNhap;
    private final static String APP_LOCK = "applock";
    private final static String APP_LOCK_STATUS = "status";
    private final static String APP_LOCK_LOCKED = "locked";
    private final static String APP_LOCK_UNLOCKED = "unlocked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);
        btnNhap = (Button) findViewById(R.id.activity_applock_button);
        btnDelete = (Button) findViewById(R.id.activity_applock_button_delete);
        edtNhap = (EditText) findViewById(R.id.activity_applock_editText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNhap.getText().toString().trim();
                if (checkPreferenceFileExist(APP_LOCK) == true) {
                    //file tồn tại
                    xoaTatCaDuLieu();
                    themAppLock(s);
                } else {
                    //file chưa tồn tại
                    themAppLock(s);
                }
                Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaTatCaDuLieu();
                Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(AppLockActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void themAppLock(String key) {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(APP_LOCK, key);
        editor.putString(APP_LOCK_STATUS, APP_LOCK_LOCKED);
        editor.apply();
    }

    private String docDuLieu() {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        return shared.getString(APP_LOCK, "NULL");
    }

    public boolean checkPreferenceFileExist(String fileName) {
        File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists();
    }

    private void xoaTatCaDuLieu() {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }


}
