package com.example.son_auto.appdiary_1.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import java.lang.reflect.Type;
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
    private Button  mBtnConfig;
    private LinearLayout mLnLayoutContainer_Config, mLnLayout_main;

    //Layout Config
    private LinearLayout mConfig_Lnlayout;
    private RecyclerView mRecyclerViewEmotion1;
    //Command and Key
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK = "contentzeroback";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_RESTORE = "contentzerorestore";
    private static final String FRAGMENT_ADD_COMMAND_SAVE_DIARY = "savediary";
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

    public void getCommand() {
        Log.e("Fragment Add haha", " getcommand " + mCommand);
        switch (this.mCommand) {
            case FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK:
                clearEditText();
                break;
            case FRAGMENT_ADD_COMMAND_CONTENT_RESTORE:
                clearEditText();
                if (this.getArguments() != null) {
                    if (this.getArguments().getString(FRAGMENT_ADD_KEY_MCONTENT) != null) {
                        Log.e("Fragment Add haha", " getcommand Argument " + mCommand);
                        mContent.setText(this.getArguments().getString(FRAGMENT_ADD_KEY_MCONTENT));
                        this.getArguments().remove(FRAGMENT_ADD_KEY_MCONTENT);
                    } else if (mObject != null) {
                        //dùng cho các làn sau lần đầu tiên, từ Fragment Add, edittext có chữ, ra màn hình chính
                        Log.e("Fragment Add haha", " getcommand Content 2" + mCommand);
                        mContent.setText(mObject + "");
                    }
                } else {
                    //dùng cho khi làn đầu tiên từ Fragment Add, edittext có chữ, ra màn hình chính
                    //Cái này dành cho nhấn nút đa nhiệm mà ko nhất text trong EditText
                    //nhưng coi chừng biến mObject, lỡ nó ko phải chữ thì mệt
                    if (mObject != null) {
                        mContent.setText(mObject + "");
                        Log.e("Fragment Add haha", " getcommand Content " + mObject);
                    }
                }
                break;
            case FRAGMENT_ADD_COMMAND_SAVE_DIARY:
                AddPageDiary();
                break;
            default:
                break;
        }
        clearCommand();
    }

    private void clearEditText() {
        if (mContent != null)
            mContent.setText("");
    }

    private void clearCommand() {
        mCommand = "";
        mObject = null;
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

        mBtnConfig = (Button) mRootView.findViewById(R.id.fragment_add_buttonconfig);
        mContent = (EditText) mRootView.findViewById(R.id.fragment_add_edittextContent);
        mLnLayoutContainer_Config = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_container_config);
        mLnLayout_main = (LinearLayout) mRootView.findViewById(R.id.fragment_add_lnlayout_main);

        mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
        mTextViewDateAndTime.setText(getDateAndTime());
        mBtnConfig.setOnClickListener(this);
        mLnLayoutContainer_Config.setOnClickListener(this);
        mContent.requestFocus();
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
        mRecyclerViewEmotion1.setAdapter(new AdapterForListEmotion(getContext(), listIcon));


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Fragment Add haha", "onStart");
        if (this.getArguments() != null)
            if (this.getArguments().getString(FRAGMENT_ADD_KEY_MCONTENT) != null) {
                Log.e("Fragment Add haha", "onStart 2" + this.mCommand);
                setCommand(FRAGMENT_ADD_COMMAND_CONTENT_RESTORE);
                Log.e("Fragment Add haha", "onStart 2" + this.mCommand);
            }

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
        Log.e("Fragment Add haha", "onSave " + mObject + " " + mContent.getText().toString());
        if (mObject == null) {
            mObject = "";
            if (mContent.getText().toString().length() != 0)
                mObject = mContent.getText().toString();
        }

        Log.e("Fragment Add haha", "onSave 2" + mObject + " " + mContent.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Fragment Add haha", "onActivityCreated" + mContent.getText());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
