package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.son_auto.appdiary_1.R;
import com.example.son_auto.appdiary_1.adapter.AdapterForRecyclerView;
import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentListPageDiary extends Fragment {
    private View mRootView;
    private RecyclerView recyclerView01;
    private AdapterForRecyclerView adapterForRecyclerView;
    private DiaryDatabase diaryDatabase;
    private ArrayList<PageDiary> listPage;
    private static FragmentListPageDiary instance;

    public void setCommand(String command) {
        switch (command) {
            case "addPage":
                AddPage();
                break;
        }
    }

    public static FragmentListPageDiary getInstance() {
        if (instance == null)
            instance = new FragmentListPageDiary();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_listpagediary, container, false);
        init();
        return mRootView;
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
        diaryDatabase = new DiaryDatabase(getContext());
        listPage = new ArrayList<>();
        listPage.addAll(diaryDatabase.getAllPageDiary());
    }

    private void AddPage() {
        String content1 = "Content 1Content 1Content 1Content 1Content 1Content 1Content 1Content 1Content 1Content 1";
        String emotion1 = "pic1";
        int background1 = R.color.colorAccent;
        diaryDatabase.addDiary(new PageDiary(content1, emotion1, background1 + ""));
        listPage.clear();
        listPage.addAll(diaryDatabase.getAllPageDiary());
        adapterForRecyclerView.notifyDataSetChanged();
        recyclerView01.smoothScrollToPosition(listPage.size() - 1);

    }

}
