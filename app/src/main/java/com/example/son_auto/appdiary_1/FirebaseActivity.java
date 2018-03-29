package com.example.son_auto.appdiary_1;

import android.content.Intent;
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

public class FirebaseActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtName, mEdtPass;
    private Button mBtnRegister, mBtnLogin, mBtnLogOut;

    private FirebaseAuth mFBAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        initFirebaseAuth();
        initView();
    }

    private void initFirebaseAuth() {
        mFBAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(FirebaseActivity.this, "This is Firebase Listener", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        };
    }

    private void initView() {
        mEdtName = (EditText) findViewById(R.id.activity_firebase_editTextName);
        mEdtPass = (EditText) findViewById(R.id.activity_firebase_editTextPass);
        mBtnRegister = (Button) findViewById(R.id.activity_firebase_buttonDangKy);
        mBtnLogin = (Button) findViewById(R.id.activity_firebase_buttonDangNhap);
        mBtnLogOut = (Button) findViewById(R.id.activity_firebase_buttonlogout);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnLogOut.setOnClickListener(this);
        checkUserLogin();
    }

    private boolean checkUserLogin() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Toast.makeText(this, "User singed in", Toast.LENGTH_SHORT).show();
            mBtnLogin.setEnabled(false);
            mBtnLogOut.setEnabled(true);
            return true;
        } else {
            Toast.makeText(this, "No singed in", Toast.LENGTH_SHORT).show();
            mBtnLogOut.setEnabled(false);
            mBtnLogin.setEnabled(true);
            return false;
        }
    }

    private boolean checkEdittextNotEmpty() {
        if (mEdtPass.getText().toString().trim().compareTo("")!=0 && mEdtName.getText().toString().trim().compareTo("")!=0) {
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFBAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFBAuth.removeAuthStateListener(fireAuthStateListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_firebase_buttonDangKy:
                if (checkEdittextNotEmpty() == true) {
                    String name = mEdtName.getText().toString();
                    String pass = mEdtPass.getText().toString();
                    mFBAuth.createUserWithEmailAndPassword(name, pass).addOnCompleteListener(FirebaseActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText(FirebaseActivity.this, "Register Error" + getResources().getText(R.string.firebase_account_weakpass), Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(FirebaseActivity.this, "Register Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(FirebaseActivity.this, "Register Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(FirebaseActivity.this, "Register Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(FirebaseActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                checkUserLogin();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Phai nhap du", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_firebase_buttonDangNhap:
                if (checkEdittextNotEmpty() == true) {
                    String name = mEdtName.getText().toString();
                    String pass = mEdtPass.getText().toString();
                    mFBAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(FirebaseActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(FirebaseActivity.this, "Register Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FirebaseActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                                checkUserLogin();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Phai nhap du", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_firebase_buttonlogout:
                FirebaseAuth.getInstance().signOut();
                checkUserLogin();
//                Intent i = new Intent(FirebaseActivity.this, MainActivity.class);
//                finish();
//                startActivity(i);
                break;
        }
    }
}
