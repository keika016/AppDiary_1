package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment implements View.OnClickListener {
    private View mRootView;
    private EditText mContent;
    private ImageView mImageViewEmotion;
    private TextView mTextViewDateAndTime;
    private Button mBtnSave, mBtnCancel, mBtnConfig;
    private LinearLayout mLnLayout_Config,mLnLayoutContainer_Config;

    private static final String COMMAND_FLOATBUTTON_SHOW = "show";
    private static final String COMMAND_ADD_PAGEDIARY = "addPage";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_layout, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mImageViewEmotion = (ImageView) mRootView.findViewById(R.id.fragment_add_imageView);
        mTextViewDateAndTime = (TextView) mRootView.findViewById(R.id.fragment_add_textView);
        mBtnSave = (Button) mRootView.findViewById(R.id.fragment_add_buttonsave);
        mBtnCancel = (Button) mRootView.findViewById(R.id.fragment_add_buttoncancel);
        mBtnConfig = (Button) mRootView.findViewById(R.id.fragment_add_buttonconfig);
        mContent = (EditText) mRootView.findViewById(R.id.fragment_add_edittextContent);
        mLnLayout_Config = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_config);
        mLnLayoutContainer_Config = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_container_config);


        mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
        mContent.requestFocus();
        mTextViewDateAndTime.setText(getDateAndTime());
        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnConfig.setOnClickListener(this);
        mTextViewDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "TextView", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getDateAndTime() {
        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    private void AddPageDiary() {
        String content1 = mContent.getText().toString();
        String emotion1 = "pic1";
        int background1 = R.color.colorAccent;
        String datetime = mTextViewDateAndTime.getText().toString();
        PageDiary p = new PageDiary(content1, emotion1, background1 + "", datetime);
        MainActivity.getDiaryDatabase().addDiary(p);
        Toast.makeText(getContext(), "Page Added", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Dành cho khi từ Fragment trở lại Activity thì EditText không hiển thị nổi dung nữa
        mContent.setText("");
        //Thay đổi FloatButton thành Add
        ((MainActivity) getActivity()).setCommand(COMMAND_FLOATBUTTON_SHOW);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_add_buttonsave:
                AddPageDiary();
                break;
            case R.id.fragment_add_buttoncancel:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_add_buttonconfig:
                if (!mLnLayoutContainer_Config.isShown())
                    mLnLayoutContainer_Config.setVisibility(View.VISIBLE);
                break;
            case R.id.fragment_add_layout_container_config:
                if(mLnLayoutContainer_Config.isShown())
                    mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
