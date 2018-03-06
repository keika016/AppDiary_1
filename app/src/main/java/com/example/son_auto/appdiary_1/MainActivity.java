package com.example.son_auto.appdiary_1;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.son_auto.appdiary_1.adapter.AdapterForRecyclerView;
import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.fragment.FragmentAdd;
import com.example.son_auto.appdiary_1.fragment.FragmentListPageDiary;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    ;
    private FragmentListPageDiary fragmentListPageDiary;
    private FragmentAdd fragmentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
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
                //fragmentListPageDiary.setCommand("addPage");
            }
        });
    }

    private void initFragment() {
        fragmentListPageDiary = new FragmentListPageDiary();
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
        }
    }

    //dành cho Fragment cần nhấn gì đó để hiện thị lên
    private void showingAFragment(Fragment fragment, String classOfFragment) {

    }

}
