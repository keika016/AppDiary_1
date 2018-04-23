package com.example.son_auto.appdiary_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class AppLockActivity extends AppCompatActivity implements View.OnClickListener {

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
        if (checkPreferenceFileExist(APP_LOCK) == true) {
            SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
            if (docDuLieu().compareTo("NULL") != 0) {
                showDialog();
            }
        }
        initView();
        btnNhap.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(AppLockActivity.this);
        dialog.setTitle(this.getResources().getString(R.string.fragment_listpage_delete_attention));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_check_lock_layout);
        Button btnOK = (Button) dialog.findViewById(R.id.dialog_app_lock_check_buttonenter);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_app_lock_check_buttoncancel);
        final EditText edtNhap = (EditText) dialog.findViewById(R.id.dialog_app_lock_check_editText);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNhap.getText().toString().trim();
                if (s.compareToIgnoreCase(docDuLieu()) == 0) {
                    dialog.dismiss();
                } else
                    Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_load_app_lock_toast), Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLockStatus();
                Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void setLockStatus() {
        if (checkPreferenceFileExist(APP_LOCK) == true) {
            SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
            String s = shared.getString(APP_LOCK_STATUS, "NULL");
            SharedPreferences.Editor editor = shared.edit();
            editor.remove(APP_LOCK_STATUS);
            editor.putString(APP_LOCK_STATUS, APP_LOCK_UNLOCKED);
            editor.apply();
            Log.e("Main Activity", "App Lock in");
        }
    }

    private void initView() {
        btnNhap = (Button) findViewById(R.id.activity_applock_button);
        btnDelete = (Button) findViewById(R.id.activity_applock_button_delete);
        edtNhap = (EditText) findViewById(R.id.activity_applock_editText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_applock_button:
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
                break;
            case R.id.activity_applock_button_delete:
                xoaTatCaDuLieu();
                i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
