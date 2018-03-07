package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment {
    private View mRootView;
    private EditText content;
    private static final String COMMAND_FLOATBUTTON_ADD = "add";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_layout,container,false);
        initView();
        return mRootView;
    }
    private void initView(){
        content = (EditText)mRootView.findViewById(R.id.fragment_add_edittextContent);
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

        //Dành cho khi từ Fragment trở lại Activity thì EditText không hiển thị nổi dung nữa
        content.setText("");
        //Thay đổi FloatButton thành Add
        ((MainActivity)getActivity()).setCommand(COMMAND_FLOATBUTTON_ADD);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}
