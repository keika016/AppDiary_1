package com.example.son_auto.appdiary_1;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.fragment.FragmentAdd;
import com.example.son_auto.appdiary_1.fragment.FragmentListPageDiary;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;

    private FragmentListPageDiary fragmentListPageDiary;
    private FragmentAdd fragmentAdd;

    private static DiaryDatabase diaryDatabase;

    private static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    private static final String FRAGMENT_ADD = "FRAGMENT_ADD";
    private static final String COMMAND_SHOW_FRAGMENT_ADD = "showFragmentAdd";
    private static final String COMMAND_FLOATBUTTON_SHOW = "show";
    private static final String COMMAND_FLOATBUTTON_HIDE = "hide";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void setCommand(String command) {
        switch (command) {
            case COMMAND_SHOW_FRAGMENT_ADD:
                showFragmentAdd();
                break;
            case COMMAND_FLOATBUTTON_SHOW:
                changeOfFloatButton(COMMAND_FLOATBUTTON_SHOW);
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
    private void changeOfFloatButton(String command){
        switch (command){
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
        if (fragmentAdd != null) {
            if (fragmentAdd.isVisible())
                outState.putString(KEY_FRAGMENT, FRAGMENT_ADD);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(KEY_FRAGMENT)) {
            switch (savedInstanceState.getString(KEY_FRAGMENT)) {
                case FRAGMENT_ADD:
                    showFragmentAdd();
                    break;
                default:
                    break;
            }
        }
    }


}
