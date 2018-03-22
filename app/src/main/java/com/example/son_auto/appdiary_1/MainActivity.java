package com.example.son_auto.appdiary_1;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.fragment.FragmentAdd;
import com.example.son_auto.appdiary_1.fragment.FragmentListPageDiary;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private Button btnOpenFirebase, btnOpenAbout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private FragmentListPageDiary fragmentListPageDiary;
    private FragmentAdd fragmentAdd;

    private static DiaryDatabase diaryDatabase;

    private static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    private static final String FRAGMENT_ADD = "FRAGMENT_ADD";

    private static final String COMMAND_SHOW_FRAGMENT_ADD = "showFragmentAdd";
    private static final String COMMAND_FLOATBUTTON_SHOW = "show";
    private static final String COMMAND_FLOATBUTTON_HIDE = "hide";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK = "contentzeroback";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_RESTORE = "contentzerorestore";
    private static final String FRAGMENT_ADD_KEY_MCONTENT = "key_mcontent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        navigationDrawer();
    }

    private void navigationDrawer(){
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCommand(String command) {
        switch (command) {
            case COMMAND_SHOW_FRAGMENT_ADD:
                showFragmentAdd();
                break;

        }
    }

    private void init() {
        initData();
        initView();
        initFragment();
        replaceFragment(fragmentListPageDiary, false);
    }

    private void initView() {
        fabAdd = (FloatingActionButton) findViewById(R.id.activity_main_fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentAdd();
            }
        });
        btnOpenFirebase = (Button)findViewById(R.id.activity_main_button_openfirebase);
        btnOpenAbout = (Button)findViewById(R.id.activity_main_button_openAbout);
        btnOpenFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FirebaseActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnOpenAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(i);
                finish();
            }
        });
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("MainActivity", "Main OnSave ");
        if (fragmentAdd != null) {
            if (fragmentAdd.isVisible()) {
                ArrayList<String> list = new ArrayList<>();
                list.add(FRAGMENT_ADD);
                // dòng dưới dành cho lần đầu tiên đang ở Fragment Add, ra màn hình chính, trở lại app, text còn chữ
                fragmentAdd.setCommand(FRAGMENT_ADD_COMMAND_CONTENT_RESTORE);
                //dòng dưới dành cho khi xoay màn hình, EditText trong Fragment không mất text
                list.add(fragmentAdd.getmObject().toString());
                Log.e("MainActivity", "Main OnSave 2 ");
                outState.putStringArrayList(KEY_FRAGMENT, list);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("MainActivity", "Main Retore");
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
                        /////
                        Log.e("MainActivity", "Main Retore 2");
                        break;
                    default:
                        break;
                }
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getFragmentManager().getBackStackEntryCount();
        switch (count) {
            case 0:
                // handle back press of fragment one
                if(fragmentAdd!=null){
                    changeOfFloatButton(COMMAND_FLOATBUTTON_SHOW);
                    fragmentAdd.setCommand(FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK);
                    Log.e("MainActivity","onBack");
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
}
