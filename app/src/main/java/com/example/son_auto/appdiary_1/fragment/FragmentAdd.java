package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment implements View.OnClickListener{
    private View mRootView;
    private EditText mContent;
    private ImageView mImageViewEmotion;
    private TextView mTextViewDateAndTime;
    private Button mBtnSave, mBtnCancel;
    private static final String COMMAND_FLOATBUTTON_SHOW = "show";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_layout,container,false);
        initView();
        return mRootView;
    }
    private void initView(){
        mImageViewEmotion = (ImageView)mRootView.findViewById(R.id.fragment_add_imageView);
        mTextViewDateAndTime = (TextView)mRootView.findViewById(R.id.fragment_add_textView);
        mBtnSave = (Button)mRootView.findViewById(R.id.fragment_add_buttonsave);
        mBtnCancel = (Button)mRootView.findViewById(R.id.fragment_add_buttoncancel);
        mContent = (EditText)mRootView.findViewById(R.id.fragment_add_edittextContent);

        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Dành cho khi từ Fragment trở lại Activity thì EditText không hiển thị nổi dung nữa
        mContent.setText("");
        //Thay đổi FloatButton thành Add
        ((MainActivity)getActivity()).setCommand(COMMAND_FLOATBUTTON_SHOW);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_add_buttonsave:
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.fragment_add_buttoncancel:
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
