package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.son_auto.appdiary_1.R;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_layout,container,false);
        return mRootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Trang thai","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Trang thai","onSTop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Trang thai","onSave");
    }
}
