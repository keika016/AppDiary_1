package com.example.son_auto.appdiary_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AppLockActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNhap, btnDelete;
    private EditText edtPass, edtEmail;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;

    private final static String APP_LOCK = "applock";
    private final static String APP_LOCK_STATUS = "status";
    private final static String APP_LOCK_LOCKED = "locked";
    private final static String APP_LOCK_UNLOCKED = "unlocked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);
        initFirebaseAuthen();
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
                String email_pass = docDuLieu();
                String[] parts = email_pass.split(",");
                String email = parts[0]; // 004
                String nhap = edtNhap.getText().toString().trim();

                String s = email + "," + nhap;
                if (s.compareToIgnoreCase(docDuLieu()) == 0) {
                    edtEmail.setText(email + "");
                    edtEmail.setEnabled(false);
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

    private void dangKy(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AppLockActivity.this, new OnCompleteListener<AuthResult>() {
            boolean b1;

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException weakPassword) {
                        Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_weakpass), Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                        Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailwrong), Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthUserCollisionException existEmail) {
                        Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailexist), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_success), Toast.LENGTH_SHORT).show();
                    dangNhap(email, password);
                    String s = email + "," + password;
                    themAppLock(s);
                    Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });


    }

    private void dangNhap(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AppLockActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_in_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePassword(final String email, final String newPassword) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_weakpass), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailwrong), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailexist), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_success), Toast.LENGTH_SHORT).show();
                            String s = email + "," + newPassword;
                            themAppLock(s);
                            Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });

    }

    private void initView() {
        btnNhap = (Button) findViewById(R.id.activity_applock_button);
        btnDelete = (Button) findViewById(R.id.activity_applock_button_delete);
        edtPass = (EditText) findViewById(R.id.activity_applock_editText_pass);
        edtEmail = (EditText) findViewById(R.id.activity_applock_editText_email);
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

    private void deleteUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_weakpass), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailwrong), Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error_emailexist), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_sign_up_error), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                });
    }

    private void alertDialogXoaKey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AppLockActivity.this);
        builder.setTitle("" + getResources().getString(R.string.fragment_listpage_delete_attention));
        builder.setMessage("" + getResources().getString(R.string.activity_app_lock_delete_key_areyousure));
        builder.setPositiveButton("" + getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
                clearSharedPreferences(getApplicationContext());
                Toast.makeText(AppLockActivity.this, "" + getResources().getString(R.string.activity_app_lock_success), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AppLockActivity.this, MainActivity.class);
                startActivity(i);
                finish();
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

    public static void clearSharedPreferences(Context ctx) {
        File dir = new File(ctx.getFilesDir().getParent() + "/shared_prefs/");
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            // clear each of the prefrances
            ctx.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
        }
        // Make sure it has enough time to save all the commited changes
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < children.length; i++) {
            // delete the files
            new File(dir, children[i]).delete();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_applock_button:
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (checkPreferenceFileExist(APP_LOCK) == true) {
                    //file tồn tại
                    xoaTatCaDuLieu();
                    updatePassword(email, pass);
                } else {
                    //file chưa tồn tại
                    if (email.compareToIgnoreCase("") == 0 || pass.compareToIgnoreCase("") == 0) {
                        Toast.makeText(this, "" + getResources().getString(R.string.activity_app_lock_must_enter), Toast.LENGTH_SHORT).show();
                    } else {
                        dangKy(email, pass);
                    }

                }
                break;
            case R.id.activity_applock_button_delete:
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    alertDialogXoaKey();
                } else {
                    // No user is signed in
                    Toast.makeText(this, "" + getResources().getString(R.string.activity_app_lock_no_key_delete), Toast.LENGTH_SHORT).show();
                }

                break;
        }
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
