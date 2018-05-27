package com.example.son_auto.appdiary_1.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;
import com.example.son_auto.appdiary_1.adapter.AdapterForRecyclerView;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentListPageDiary extends Fragment {
    private View mRootView;
    private RecyclerView recyclerView01;
    private AdapterForRecyclerView adapterForRecyclerView;
    private ArrayList<PageDiary> listPage;

    private static FragmentListPageDiary instance;

    private static final String FRAGMENT_LIST_COMMAND_ADD_PAGEDIARY = "addPage";
    private static final String FRAGMENT_LIST_COMMAND_FIND_PAGEDIARY = "findPage";

    public void setCommand(String command) {
        switch (command) {
            case FRAGMENT_LIST_COMMAND_ADD_PAGEDIARY:
                //refeshListPageDiary();
                break;
        }
    }

    public boolean findDiary(String day) {
        Log.e("Find DIalog", "hello day: " + day);
        ArrayList<PageDiary> listTimKiem = new ArrayList<PageDiary>();
        for (PageDiary item : listPage) {
            String str2[] = item.getDateTime().split(",");
            Log.e("Find DIalog", "hello: " + str2[0]);
            String s = str2[0];
            if (day.compareToIgnoreCase(s) == 0) {
                listTimKiem.add(item);
            }
        }
        if (listTimKiem.size() != 0) {
            listPage.clear();
            listPage.addAll(listTimKiem);
            adapterForRecyclerView.notifyDataSetChanged();
            return true;
        }

        return false;
    }

    public static FragmentListPageDiary getInstance() {
        if (instance == null)
            instance = new FragmentListPageDiary();
        return instance;
    }

    private void init() {
        initData();
        initView();
    }

    private void initView() {
        recyclerView01 = (RecyclerView) mRootView.findViewById(R.id.fragment_listpagediary_recyclerview);
        adapterForRecyclerView = new AdapterForRecyclerView(getContext(), listPage);
        recyclerView01.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));

        recyclerView01.setHasFixedSize(true);
        recyclerView01.setAdapter(adapterForRecyclerView);
    }

    private void initData() {
        listPage = new ArrayList<PageDiary>();
        if (MainActivity.getDiaryDatabase().checkDBHaveItem() == true) {
            listPage.addAll(MainActivity.getDiaryDatabase().getAllPageDiary());
        }
    }

    public void refeshListForFind() {
        if (MainActivity.getDiaryDatabase().checkDBHaveItem() == true) {
            listPage.clear();
            listPage.addAll(MainActivity.getDiaryDatabase().getAllPageDiary());
            adapterForRecyclerView.notifyDataSetChanged();
        }
    }

    public void sort(int sapxep) {
        switch (sapxep) {
            case 1:
                Collections.sort(listPage, new Comparator<PageDiary>() {
                    @Override
                    public int compare(PageDiary o1, PageDiary o2) {
                        return o1.getDateTime().compareToIgnoreCase(o2.getDateTime());
                    }
                });
                adapterForRecyclerView.notifyDataSetChanged();
                break;
            case 2:
                listPage.clear();
                listPage.addAll(MainActivity.getDiaryDatabase().getAllPageDiary());
                adapterForRecyclerView.notifyDataSetChanged();
                break;

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_listpagediary, container, false);
        init();
        return mRootView;
    }


//    private void refeshListPageDiary(){
//        listPage.clear();
//        listPage.addAll(MainActivity.getDiaryDatabase().getAllPageDiary());
//        adapterForRecyclerView.notifyDataSetChanged();
//        recyclerView01.smoothScrollToPosition(listPage.size() - 1);
//    }


}
