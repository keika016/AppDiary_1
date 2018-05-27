package com.example.son_auto.appdiary_1;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.fragment.FragmentAdd;
import com.example.son_auto.appdiary_1.fragment.FragmentListPageDiary;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FloatingActionButton fabAdd;
    private Button btnOpenFirebase, btnOpenAbout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private Menu menu;
    private boolean showMenu;

    private FragmentListPageDiary fragmentListPageDiary;
    private FragmentAdd fragmentAdd;

    private static DiaryDatabase diaryDatabase;

    private static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    private static final String FRAGMENT_ADD = "FRAGMENT_ADD";

    private final static String APP_LOCK = "applock";
    private final static String APP_LOCK_STATUS = "status";
    private final static String APP_LOCK_LOCKED = "locked";
    private final static String APP_LOCK_UNLOCKED = "unlocked";

    //command
    private static final String COMMAND_SHOW_FRAGMENT_ADD = "showFragmentAdd";
    private static final String COMMAND_FLOATBUTTON_SHOW = "show";
    private static final String COMMAND_FLOATBUTTON_HIDE = "hide";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK = "contentzeroback";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_RESTORE = "contentzerorestore";
    private static final String FRAGMENT_ADD_COMMAND_SAVE_DIARY = "savediary";
    private static final String FRAGMENT_ADD_KEY_MCONTENT = "key_mcontent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPreferenceFileExist(APP_LOCK) == true) {
            SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
            String s = shared.getString(APP_LOCK_STATUS, "NULL");
            if (s.compareTo(APP_LOCK_LOCKED) == 0) {

                Intent i = new Intent(MainActivity.this, AppLock_LoadActivity.class);
                i.putExtra("email_pass", shared.getString(APP_LOCK, "NULL"));
                startActivity(i);
                finish();
            }
        }
        init();
        navigationDrawer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setLock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Main Activity", "onStop");
        setLock();
    }

    private void setLock() {
        if (checkPreferenceFileExist(APP_LOCK) == true) {
            SharedPreferences shared = getSharedPreferences(APP_LOCK, Context.MODE_PRIVATE);
            String s = shared.getString(APP_LOCK_STATUS, "NULL");
            if (s.compareTo(APP_LOCK_UNLOCKED) == 0) {
                SharedPreferences.Editor editor = shared.edit();
                editor.remove(APP_LOCK_STATUS);
                editor.putString(APP_LOCK_STATUS, APP_LOCK_LOCKED);
                editor.apply();
            }
        }

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

    public FragmentAdd getFragmentAdd() {
        return fragmentAdd;
    }

    private void navigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView = (NavigationView) findViewById(R.id.activity_main_navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }

    public void setBackButton(boolean b) {
        drawerToggle.setDrawerIndicatorEnabled(b);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                if (flagFind == false)
                    onBackPressed();
                else {
                    if (flagFind == true) {
                        drawerToggle.setDrawerIndicatorEnabled(true);
                        fragmentListPageDiary.refeshListForFind();
                        flagFind = false;
                    }
                }
                return true;
            case R.id.menu_add_edit_diray_done:
                fragmentAdd.setCommand(FRAGMENT_ADD_COMMAND_SAVE_DIARY);
                fragmentAdd.getCommand();
                break;
            case R.id.menu_find:
                showDialogFind();
                break;
            case R.id.menu_sort:
                showDialogSort();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean flagFind = false;

    private void showDialogFind() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle(this.getResources().getString(R.string.menu_find));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_find_layout);
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        final DatePickerDialog dialogDate = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DatePicker datePicker1;
        datePicker1 = (DatePicker) dialog.findViewById(R.id.dialog_find_datepicker);
        Calendar calendar = Calendar.getInstance();
        final String[] getDate = {""};
        datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)
                , new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int day = dayOfMonth;
                        int month = (monthOfYear + 1);
                        String day2 = day + " thg " + month + " " + year;
                        getDate[0] = day2;
                    }
                });
        Button btnOK = (Button) dialog.findViewById(R.id.dialog_find_buttonenter);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_find_buttoncancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fragmentListPageDiary.findDiary(getDate[0] + "") == false) {
                    Toast.makeText(MainActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                    flagFind = false;
                } else {
                    dialog.dismiss();
                    drawerToggle.setDrawerIndicatorEnabled(false);
                    flagFind = true;
                }
//                int   day  = datePicker1.getDayOfMonth();
//                int   month= datePicker1.getMonth();
//                int   year = datePicker1.getYear();
//                calendar.set(year, month, day);
//                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
//                String formatedDate = sdf.format(calendar.getTime());
//                Toast.makeText(MainActivity.this, ""+formatedDate, Toast.LENGTH_SHORT).show();
                //You can parse the String back to Date object by calling
                /*try {
                    Date date = sdf.parse(formatedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void showDialogSort() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Sắp xếp");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_sort_layout);
        DatePicker datePicker1;

        Button btnDay = (Button) dialog.findViewById(R.id.dialog_sort_buttonDay);
        Button btnMacDinh = (Button) dialog.findViewById(R.id.dialog_sort_buttonMacDinh);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_sort_buttoncancel);
        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListPageDiary.sort(1);
                dialog.dismiss();
            }
        });
        btnMacDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListPageDiary.sort(2);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static boolean kiemtraSoNguyenDuong(String str) {
        return str.matches("[+]?\\d+");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_diary, menu);
        this.menu = menu;
        showMenuDone(false);
        if (fragmentAdd != null) {
            if (showMenu == true)
                showMenuDone(true);
        }
        return super.onCreateOptionsMenu(this.menu);
    }

    public void setCommand(String command) {
        switch (command) {
            case COMMAND_SHOW_FRAGMENT_ADD:
                showFragmentAdd();
                changeHamburgerToBackForAdd_EditPageDiary();
                showMenuDone(true);
                showMenuFindSort(false);
                break;

        }
    }

    public void showMenuDone(boolean showMenu) {
        if (this.menu == null) {
            return;
        }
        this.menu.setGroupVisible(R.id.activity_main_group1, showMenu);
    }

    public void showMenuFindSort(boolean showMenu) {
        if (this.menu == null) {
            return;
        }
        this.menu.setGroupVisible(R.id.activity_main_group2, showMenu);
    }

    private void init() {
        initData();
        initView();
        initFragment();
        replaceFragment(fragmentListPageDiary, false);
        showMenu = false;
    }

    private void initView() {
        fabAdd = (FloatingActionButton) findViewById(R.id.activity_main_fabAdd);
        fabAdd.setOnClickListener(this);
    }

    private void initData() {
        diaryDatabase = new DiaryDatabase(getApplicationContext());
    }

    private void initFragment() {
        fragmentListPageDiary = new FragmentListPageDiary();
    }

    public static DiaryDatabase getDiaryDatabase() {
        return diaryDatabase;
    }

    public static void setDiaryDatabase(DiaryDatabase diaryDatabase) {
        MainActivity.diaryDatabase = diaryDatabase;
    }

    private void replaceFragment(Fragment fragment, boolean backStack) {
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.activity_main_content, fragment);
            if (backStack == true)
                ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    private void showFragmentAdd() {
        if (fragmentAdd == null)
            fragmentAdd = new FragmentAdd();
        if (!fragmentAdd.isVisible()) {
            replaceFragment(fragmentAdd, true);
            changeOfFloatButton(COMMAND_FLOATBUTTON_HIDE);
        }
    }

    private void changeOfFloatButton(String command) {
        switch (command) {
            case COMMAND_FLOATBUTTON_HIDE:
                fabAdd.setVisibility(View.GONE);
                break;
            case COMMAND_FLOATBUTTON_SHOW:
                fabAdd.setVisibility(View.VISIBLE);
        }
    }

    private void changeHamburgerToBackForAdd_EditPageDiary() {
        if (drawerToggle.isDrawerIndicatorEnabled() == true) {
            //nếu humburger button hiện lên thì ẩn đi
            // Remove hamburger
            drawerToggle.setDrawerIndicatorEnabled(false);
        } else {
            //nếu humburger button bị ẩn đi thì hiện lên
            drawerToggle.setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragmentAdd != null) {
            if (fragmentAdd.isVisible()) {
                ArrayList<String> list = new ArrayList<>();
                list.add(FRAGMENT_ADD);
                // dòng dưới dành cho lần đầu tiên đang ở Fragment Add, ra màn hình chính, trở lại app, text còn chữ
                fragmentAdd.setCommand(FRAGMENT_ADD_COMMAND_CONTENT_RESTORE);
                //dòng dưới dành cho khi xoay màn hình, EditText trong Fragment không mất text
                list.add(fragmentAdd.getmObject().toString());
                outState.putStringArrayList(KEY_FRAGMENT, list);
            }
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("MainActivitiy,", "Onrestore:");
        if (savedInstanceState != null)
            if (savedInstanceState.containsKey(KEY_FRAGMENT)) {
                String s = savedInstanceState.getStringArrayList(KEY_FRAGMENT).get(0).toString();
                switch (s) {
                    case FRAGMENT_ADD:
                        showFragmentAdd();
                        //dành cho khi xoay màn hình, EditText trong Fragment không mất text
                        Bundle b = new Bundle();
                        String text = savedInstanceState.getStringArrayList(KEY_FRAGMENT).get(1).toString();
                        b.putString(FRAGMENT_ADD_KEY_MCONTENT, text);
                        fragmentAdd.setArguments(b);
                        changeHamburgerToBackForAdd_EditPageDiary();
                        showMenu = true;
                        /////
                        break;
                    default:
                        break;
                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getFragmentManager().getBackStackEntryCount();
        switch (count) {
            case 0:
                // handle back press of fragment one
                if (fragmentAdd != null) {
                    changeOfFloatButton(COMMAND_FLOATBUTTON_SHOW);
                    fragmentAdd.setCommand(FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK);
                    changeHamburgerToBackForAdd_EditPageDiary();
                    showMenuDone(false);
                    showMenuFindSort(true);
                    showMenu = false;
                }
                break;
            case 1:
                // handle back press of fragment two
                break;
            case 2:
                // handle back press of fragment three
                break;
            default:
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_firebase) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this, FirebaseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_applock) {
            Intent i = new Intent(MainActivity.this, AppLockActivity.class);
            startActivity(i);
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_fabAdd:
                showFragmentAdd();
                changeHamburgerToBackForAdd_EditPageDiary();
                showMenuDone(true);
                showMenuFindSort(false);
                break;

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}
