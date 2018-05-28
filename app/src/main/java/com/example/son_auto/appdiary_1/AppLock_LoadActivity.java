package com.example.son_auto.appdiary_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class AppLock_LoadActivity extends AppCompatActivity {
    private Button btnNhap;
    private EditText edtNhap;
    private TextView tvForgotPassword;
    private final static String APP_LOCK = "applock";
    private final static String APP_LOCK_STATUS = "status";
    private final static String APP_LOCK_LOCKED = "locked";
    private final static String APP_LOCK_UNLOCKED = "unlocked";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;
    private String email_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock__load);
        btnNhap = (Button) findViewById(R.id.activity_app_lock_load_button2);
        edtNhap = (EditText) findViewById(R.id.activity_app_lock_load_editText);
        tvForgotPassword = (TextView) findViewById(R.id.activity_app_lock_load_textview_forgotpass);
        initFirebaseAuthen();
        Bundle bundle = getIntent().getExtras();
        email_pass = bundle.getString("email_pass");
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = email_pass.split(",");
                String email = parts[0]; // 004
                String password = parts[1];
                if (password.compareToIgnoreCase("1") == 0) {
                    dangNhap(email, password);
                } else {
                    String nhap = edtNhap.getText().toString().trim();
                    String s = email + "," + nhap;
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
                    } else
                        Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_load_app_lock_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogResetKey();
            }
        });
    }

    private void alertDialogResetKey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppLock_LoadActivity.this);
        builder.setTitle("" + getResources().getString(R.string.fragment_listpage_delete_attention));
        builder.setMessage("" + getResources().getString(R.string.activity_app_lock_delete_key_areyousure));
        builder.setPositiveButton("" + getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetKey();
            }
        });
        builder.setNegativeButton("" + getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetKey() {
        mAuth = FirebaseAuth.getInstance();
        String[] parts = email_pass.split(",");
        String email = parts[0];
        final String emailAddress = email;

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_weakpass), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailwrong), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailexist), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_load_app_lock_emailsent), Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            xoaTatCaDuLieu();
                            String getEmail = emailAddress + ",1";
                            themAppLockForReset(getEmail);
                            finish();
                        }
                    }
                });
    }


    private String docDuLieu() {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        return shared.getString(APP_LOCK, "NULL");
    }

    private void xoaTatCaDuLieu() {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }

    private void themAppLockForReset(String key) {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(APP_LOCK, key);
        editor.putString(APP_LOCK_STATUS, APP_LOCK_LOCKED);
        editor.apply();
    }


    private void themAppLock(String key) {
        SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(APP_LOCK, key);
        editor.putString(APP_LOCK_STATUS, APP_LOCK_UNLOCKED);
        editor.apply();
    }

    private void initFirebaseAuthen() {
        mAuth = FirebaseAuth.getInstance();
        //cho biến mAuth
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Log.e(APP_LOCK, "onAuthStateChanged");
                }
                return;
            }
        };
    }

    private void dangNhap(final String email, String password) {
        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiCheck.isConnected() || mobileInfo.isConnected()) {
            String getPassword = edtNhap.getText().toString().trim();
            final String newPassword = getPassword;

            mAuth.signInWithEmailAndPassword(email, getPassword).addOnCompleteListener(AppLock_LoadActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(AppLock_LoadActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_in_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AppLock_LoadActivity.this, ""+ getResources().getString(R.string.activity_app_lock_success), Toast.LENGTH_SHORT).show();
                        String s = email + "," + newPassword;
                        xoaTatCaDuLieu();
                        themAppLock(s);
                        Intent i = new Intent(AppLock_LoadActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        } else {
            Toast.makeText(this, "" + getResources().getString(R.string.activity_load_app_lock_connectwifi), Toast.LENGTH_SHORT).show();
        }


    }

    public void checkWiFi(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
