package com.example.son_auto.appdiary_1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.son_auto.appdiary_1.adapter.AdapterForListEmotion;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment implements View.OnClickListener {

    //Layout Fragment Add
    private View mRootView;
    private EditText mContent;
    private ImageView mImageViewEmotion;
    private TextView mTextViewDateAndTime;
    private Button mBtnSave, mBtnCancel, mBtnConfig;
    private LinearLayout mLnLayoutContainer_Config, mLnLayout_main;

    //Layout Config
    private LinearLayout mConfig_Lnlayout;
    private RecyclerView mRecyclerViewEmotion1;
    //Command and Key
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK = "contentzeroback";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_RESTORE = "contentzerorestore";
    private static final String FRAGMENT_ADD_KEY_MCONTENT = "key_mcontent";

    //thuoc tinh
    private String mCommand;
    private Object mObject;

    public FragmentAdd() {
        mCommand = "";
    }

    public void setCommand(String command) {
        this.mCommand = command;
    }

    public Object getmObject() {
        return mObject;
    }

    private void getCommand() {
        Log.e("Fragment Add haha", " getcommand " + mCommand);
        switch (this.mCommand) {
            case FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK:
                clearEditText();
                break;
            case FRAGMENT_ADD_COMMAND_CONTENT_RESTORE:
                if (this.getArguments() != null)
                    mContent.setText(this.getArguments().getString(FRAGMENT_ADD_KEY_MCONTENT));
                break;
        }
    }

    private void clearEditText() {
        if (mContent != null)
            mContent.setText("");
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_add_layout, container, false);
        Log.e("Fragment Add haha", "onCreateView");
        initViewLayoutFragmentAdd();
        return mRootView;
    }

    private void initViewLayoutFragmentAdd() {
        mImageViewEmotion = (ImageView) mRootView.findViewById(R.id.fragment_add_imageView);
        mTextViewDateAndTime = (TextView) mRootView.findViewById(R.id.fragment_add_textView);
        mBtnSave = (Button) mRootView.findViewById(R.id.fragment_add_buttonsave);
        mBtnCancel = (Button) mRootView.findViewById(R.id.fragment_add_buttoncancel);
        mBtnConfig = (Button) mRootView.findViewById(R.id.fragment_add_buttonconfig);
        mContent = (EditText) mRootView.findViewById(R.id.fragment_add_edittextContent);
        mLnLayoutContainer_Config = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_container_config);
        mLnLayout_main = (LinearLayout) mRootView.findViewById(R.id.fragment_add_lnlayout_main);

        mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
        mContent.requestFocus();
        mTextViewDateAndTime.setText(getDateAndTime());
        mBtnSave.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnConfig.setOnClickListener(this);
        mLnLayoutContainer_Config.setOnClickListener(this);

        initViewLayoutConfig();
    }

    private void initViewLayoutConfig() {
        mConfig_Lnlayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_config);
        mRecyclerViewEmotion1 = (RecyclerView) mRootView.findViewById(R.id.fragment_add_config_recyclerviewemotion);

        //không hiểu sao trên app cái này nó lại trượt qua được ??
        ArrayList<String> listIcon = new ArrayList<>();
        listIcon.add("pic1");
        listIcon.add("pic2");
        listIcon.add("pic3");
        listIcon.add("pic3");
        listIcon.add("pic3");
        listIcon.add("ic_sad");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sad");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sad");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sleep");
        listIcon.add("ic_sad");
        LinearLayoutManager linearLayoutManager_ListEmotion = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewEmotion1.setLayoutManager(linearLayoutManager_ListEmotion);
        mRecyclerViewEmotion1.setHasFixedSize(true);
        mRecyclerViewEmotion1.setAdapter(new AdapterForListEmotion(getContext(),listIcon));


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Fragment Add haha", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Fragment Add haha", "onResume" + this.mCommand);
        //Dùng cho EditText = ""
        getCommand();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Fragment Add haha", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Fragment Add haha", "onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Fragment Add haha", "onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Fragment Add haha", "onSave");
        mObject = "";
        mObject = mContent.getText().toString();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                if (mLnLayoutContainer_Config.isShown())
                    mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
