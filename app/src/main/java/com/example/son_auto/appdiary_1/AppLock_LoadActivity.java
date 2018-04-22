package com.example.son_auto.appdiary_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class AppLock_LoadActivity extends AppCompatActivity {
    private Button btnNhap;
    private EditText edtNhap;

    private final static String APP_LOCK = "applock";
    private final static String APP_LOCK_STATUS = "status";
    private final static String APP_LOCK_LOCKED = "locked";
    private final static String APP_LOCK_UNLOCKED = "unlocked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock__load);
        btnNhap = (Button) findViewById(R.id.activity_app_lock_load_button2);
        edtNhap = (EditText) findViewById(R.id.activity_app_lock_load_editText);
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNhap.getText().toString().trim();
                if (s.compareToIgnoreCase(docDuLieu()) == 0) {
                    SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
                    String s2 = shared.getString(APP_LOCK_STATUS, "NULL");
                    if (s2.compareTo(APP_LOCK_LOCKED) == 0) {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.remove(APP_LOCK_STATUS);
                        editor.putString(APP_LOCK_STATUS, APP_LOCK_UNLOCKED);
                        editor.apply();
                        Intent i = new Intent(AppLock_LoadActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }


    private String docDuLieu() {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        return shared.getString(APP_LOCK, "NULL");
    }

}
