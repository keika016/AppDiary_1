package com.example.son_auto.appdiary_1.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;
import com.example.son_auto.appdiary_1.adapter.AdapterForListEditTextBackground;
import com.example.son_auto.appdiary_1.adapter.AdapterForListEmotion;
import com.example.son_auto.appdiary_1.adapter.AdapterForListImageBackground;
import com.example.son_auto.appdiary_1.adapter.Adapter_LV_ListTestPostion;
import com.example.son_auto.appdiary_1.adapter.Adapter_LV_ListTextFont;
import com.example.son_auto.appdiary_1.adapter.Adapter_LV_ListTextSize;
import com.example.son_auto.appdiary_1.adapter.Adapter_LV_ListTextStyle;
import com.example.son_auto.appdiary_1.adapter.Adapter_RV_ListTextColor;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Son-Auto on 3/6/2018.
 */

public class FragmentAdd extends Fragment implements View.OnClickListener {

    //Layout Fragment Add
    private View mRootView;
    private EditText mContent;
    private TextView mTextViewDateAndTime;
    // private Button  mBtnConfig;
    private LinearLayout mLnLayoutContainer_Config, mLnLayout_main;

    //Layout Config-----------
    private LinearLayout mLinearLayoutForFragmentAndBackGroud;
    //list Emotion
    private RecyclerView mRecyclerViewEmotion1;
    private LinearLayout mContainListEmotion_LnLayout;
    private ImageView imgView_ShowListEmotion;
    //list Image Back Ground
    private RecyclerView mRecyclerViewImageBackground;
    private LinearLayout mContainListBackGround_LnLayout;
    private ImageView imgView_ShowListImageBackGround;
    //list EditText backGround
    private RecyclerView mRecyclerViewEditTextBackground;
    private LinearLayout mContainListEditTextBackGround_LnLayout;
    private ImageView imgView_ShowListEditTextBackGround;
    //list Text Option
    private ImageView imgView_ShowListTextOption;
    private LinearLayout mContainListTextOption_LnLayout;
    // . list Text Option font
    private ImageView imgView_ShowListTextFonts;
    private ListView lv_ListTextFonts;
    // . list Text Option style
    private ImageView imgView_ShowListTextStyles;
    private ListView lv_ListTextStyles;
    // . list Text Option style
    private ImageView imgView_ShowListTextColors;
    private RecyclerView rv_ListTextColor;
    // . list Text Option size
    private ImageView imgView_ShowListTextSizes;
    private ListView lv_ListTextSizes;
    // . list Text Option postion
    private ImageView imgView_ShowListTextPostions;
    private ListView lv_ListTextPositions;

    //Page Diary Tam
    private PageDiary mPagaDiaryTam;
    private ImageView mImageViewEmotion;
    private ImageView mImgViewBackGround;

    //Command and Key
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_ZERO_BACK = "contentzeroback";
    private static final String FRAGMENT_ADD_COMMAND_CONTENT_RESTORE = "contentzerorestore";
    private static final String FRAGMENT_ADD_COMMAND_SAVE_DIARY = "savediary";
    private static final String FRAGMENT_ADD_KEY_MCONTENT = "key_mcontent";
    private static final String FRAGMENT_ADD_KEY_LOADPAGE = "loadpage";

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
        mPagaDiaryTam.setContent(mContent.getText().toString());
        if (mPagaDiaryTam.getId() == 0) {
            MainActivity.getDiaryDatabase().addDiary(mPagaDiaryTam);
            Toast.makeText(getContext(), getContext().getString(R.string.fragment_add_adding) + "", Toast.LENGTH_SHORT).show();
        } else {
            MainActivity.getDiaryDatabase().updateDiary(mPagaDiaryTam);
            Toast.makeText(getContext(), getContext().getString(R.string.fragment_add_edting) + "", Toast.LENGTH_SHORT).show();
        }

        Log.e("Fragment Add haha", "AddPage: " + mPagaDiaryTam);

        clearEditText();
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
        mTextViewDateAndTime = (TextView) mRootView.findViewById(R.id.fragment_add_textView);

        // mBtnConfig = (Button) mRootView.findViewById(R.id.fragment_add_buttonconfig);
        mContent = (EditText) mRootView.findViewById(R.id.fragment_add_edittextContent);
        mContent.requestFocus();
        mContent.setOnClickListener(this);
        // mLnLayoutContainer_Config = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_container_config);
        mLnLayout_main = (LinearLayout) mRootView.findViewById(R.id.fragment_add_lnlayout_main);

//        mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
        mTextViewDateAndTime.setText(getDateAndTime());
        // mBtnConfig.setOnClickListener(this);
        // mLnLayoutContainer_Config.setOnClickListener(this);
        //initViewLayoutConfig();

        //---Config---------------
        mLinearLayoutForFragmentAndBackGroud = (LinearLayout) mRootView.findViewById(R.id.fragment_add_content_LNforEdtAndBackGround);
        mLinearLayoutForFragmentAndBackGroud.setOnClickListener(this);
        //Emotion
        imgView_ShowListEmotion = (ImageView) mRootView.findViewById(R.id.fragment_add_imageViewShowListEmotion);
        imgView_ShowListEmotion.setOnClickListener(this);
        mContainListEmotion_LnLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_Contain_ListEmotion);
        setForContainer(mContainListEmotion_LnLayout);
        //ImageViewBackGround
        imgView_ShowListImageBackGround = (ImageView) mRootView.findViewById(R.id.fragment_add_imageViewShowListBackGround);
        imgView_ShowListImageBackGround.setOnClickListener(this);
        mContainListBackGround_LnLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_Contain_ListImageBackGround);
        setForContainer(mContainListBackGround_LnLayout);
        //EditText BackGround
        imgView_ShowListEditTextBackGround = (ImageView) mRootView.findViewById(R.id.fragment_add_imageViewShowListEditTextBackGround);
        imgView_ShowListEditTextBackGround.setOnClickListener(this);
        mContainListEditTextBackGround_LnLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_Contain_ListEditTextBackGround);
        setForContainer(mContainListEditTextBackGround_LnLayout);
        //TextOption
        imgView_ShowListTextOption = (ImageView) mRootView.findViewById(R.id.fragment_add_imageViewShowLisTextOption);
        imgView_ShowListTextOption.setOnClickListener(this);
        mContainListTextOption_LnLayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_Contain_ListTextOption);
        setForContainer(mContainListTextOption_LnLayout);
        //-------------------

        //Page Diary Tạm
        mImageViewEmotion = (ImageView) mRootView.findViewById(R.id.fragment_add_imageView);
        mImgViewBackGround = (ImageView) mRootView.findViewById(R.id.fragment_add_pagediary_imageBackground);

        mPagaDiaryTam = new PageDiary();
        mPagaDiaryTam.setEmotion("em_yellowface_1");
        mPagaDiaryTam.setBackground(R.color.color_mt_YellowLight_1 + "");
        mPagaDiaryTam.setEditTextBackGround(R.color.colorTransparent + "");
        mPagaDiaryTam.setDateTime(mTextViewDateAndTime.getText().toString());
        mPagaDiaryTam.setFont("fonts/opensans_regular.ttf");
        mPagaDiaryTam.setStyle(Typeface.NORMAL + "");
        mPagaDiaryTam.setColor(R.color.colorDen + "");
        mPagaDiaryTam.setSize(15 + "");
        mPagaDiaryTam.setPosition("Left Top");
        mContent.setTextColor(ContextCompat.getColor(getActivity(), Integer.parseInt(mPagaDiaryTam.getColor())));
        mContent.setTextSize(Float.parseFloat(mPagaDiaryTam.getSize()));
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), mPagaDiaryTam.getFont());
        mContent.setTypeface(type, Integer.parseInt(mPagaDiaryTam.getStyle()));
        mImgViewBackGround.setBackgroundColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getBackground())));
    }

    private void setForContainer(LinearLayout ln) {
        ln.setOnClickListener(this);
        ln.setEnabled(false);
        ln.setVisibility(View.GONE);
    }

    //    private void initViewLayoutConfig() {
//        mConfig_Lnlayout = (LinearLayout) mRootView.findViewById(R.id.fragment_add_layout_config);
//        mRecyclerViewEmotion1 = (RecyclerView) mRootView.findViewById(R.id.fragment_add_config_recyclerviewemotion);
//
//        //không hiểu sao trên app cái này nó lại trượt qua được ??
//        ArrayList<String> listIcon = new ArrayList<>();
//        listIcon.add("pic1");
//        listIcon.add("pic2");
//        LinearLayoutManager linearLayoutManager_ListEmotion = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerViewEmotion1.setLayoutManager(linearLayoutManager_ListEmotion);
//        mRecyclerViewEmotion1.setHasFixedSize(true);
//        mRecyclerViewEmotion1.setAdapter(new AdapterForListEmotion(getContext(), listIcon));
//
//
//    }
    private void showOrHideListEmotion() {
        if (mContainListEmotion_LnLayout.isEnabled() == false) {
            mContainListEmotion_LnLayout.setEnabled(true);
            mContainListEmotion_LnLayout.setVisibility(View.VISIBLE);
            initListEmotion();
            int id = getContext().getResources().getIdentifier("side_nav_bar_5", "drawable", getContext().getPackageName());
            imgView_ShowListEmotion.setBackgroundResource(id);

            //ẩn các List Khác đi
            hideListConfig(mContainListBackGround_LnLayout);
            hideListConfig(mContainListEditTextBackGround_LnLayout);
            hideListConfig(mContainListTextOption_LnLayout);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListImageBackGround.setBackgroundResource(id2);
            imgView_ShowListTextOption.setBackgroundResource(id2);
            imgView_ShowListEditTextBackGround.setBackgroundResource(id2);
        } else {
            hideListConfig(mContainListEmotion_LnLayout);
            int id = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListEmotion.setBackgroundResource(id);
        }
    }

    private void showOrHideListBackGround() {
        if (mContainListBackGround_LnLayout.isEnabled() == false) {
            mContainListBackGround_LnLayout.setEnabled(true);
            mContainListBackGround_LnLayout.setVisibility(View.VISIBLE);
            initListImageBackGround();
            int id = getContext().getResources().getIdentifier("side_nav_bar_5", "drawable", getContext().getPackageName());
            imgView_ShowListImageBackGround.setBackgroundResource(id);

            //ẩn các List Khác đi
            hideListConfig(mContainListEmotion_LnLayout);
            hideListConfig(mContainListEditTextBackGround_LnLayout);
            hideListConfig(mContainListTextOption_LnLayout);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListEmotion.setBackgroundResource(id2);
            imgView_ShowListTextOption.setBackgroundResource(id2);
            imgView_ShowListEditTextBackGround.setBackgroundResource(id2);
        } else {
            hideListConfig(mContainListBackGround_LnLayout);
            int id = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListImageBackGround.setBackgroundResource(id);
        }
    }

    private void showOrHideListEditTextBackGround() {
        if (mContainListEditTextBackGround_LnLayout.isEnabled() == false) {
            mContainListEditTextBackGround_LnLayout.setEnabled(true);
            mContainListEditTextBackGround_LnLayout.setVisibility(View.VISIBLE);
            initListEditTextBackGround();
            int id = getContext().getResources().getIdentifier("side_nav_bar_5", "drawable", getContext().getPackageName());
            imgView_ShowListEditTextBackGround.setBackgroundResource(id);

            //ẩn các List Khác đi
            hideListConfig(mContainListEmotion_LnLayout);
            hideListConfig(mContainListBackGround_LnLayout);
            hideListConfig(mContainListTextOption_LnLayout);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListEmotion.setBackgroundResource(id2);
            imgView_ShowListTextOption.setBackgroundResource(id2);
            imgView_ShowListImageBackGround.setBackgroundResource(id2);
        } else {
            hideListConfig(mContainListEditTextBackGround_LnLayout);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListEditTextBackGround.setBackgroundResource(id2);
        }
    }

    private void showOrHideListTextOption() {
        if (mContainListTextOption_LnLayout.isEnabled() == false) {
            mContainListTextOption_LnLayout.setEnabled(true);
            mContainListTextOption_LnLayout.setVisibility(View.VISIBLE);
            int id = getContext().getResources().getIdentifier("side_nav_bar_5", "drawable", getContext().getPackageName());
            imgView_ShowListTextOption.setBackgroundResource(id);

            //các list text option
            initListTextFont();
            initListTextStyle();
            initListTextColor();
            initListTextSize();
            initListTextPosition();
            showListTextFont();

            //ẩn các List Khác đi
            hideListConfig(mContainListEmotion_LnLayout);
            hideListConfig(mContainListBackGround_LnLayout);
            hideListConfig(mContainListEditTextBackGround_LnLayout);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListEmotion.setBackgroundResource(id2);
            imgView_ShowListEditTextBackGround.setBackgroundResource(id2);
            imgView_ShowListImageBackGround.setBackgroundResource(id2);
        } else {
            hideListConfig(mContainListTextOption_LnLayout);
            hideListConfig(lv_ListTextFonts);
            hideListConfig(lv_ListTextStyles);
            hideListConfig(rv_ListTextColor);
            hideListConfig(lv_ListTextSizes);
            hideListConfig(lv_ListTextPositions);
            int id2 = getContext().getResources().getIdentifier("side_nav_bar", "drawable", getContext().getPackageName());
            imgView_ShowListTextOption.setBackgroundResource(id2);
        }
    }

    private void initListTextFont() {
        imgView_ShowListTextFonts = (ImageView) mRootView.findViewById(R.id.fragment_add_textoption_imageView_ShowListFont);
        imgView_ShowListTextFonts.setOnClickListener(this);
        lv_ListTextFonts = (ListView) mRootView.findViewById(R.id.fragment_add_layout_listTextOption_ListView_Font);
    }

    private void initListTextStyle() {
        imgView_ShowListTextStyles = (ImageView) mRootView.findViewById(R.id.fragment_add_textoption_imageView_ShowListStyle);
        imgView_ShowListTextStyles.setOnClickListener(this);
        lv_ListTextStyles = (ListView) mRootView.findViewById(R.id.fragment_add_layout_listTextOption_ListView_Style);
    }

    private void initListTextColor() {
        imgView_ShowListTextColors = (ImageView) mRootView.findViewById(R.id.fragment_add_textoption_imageView_ShowListColor);
        imgView_ShowListTextColors.setOnClickListener(this);
        rv_ListTextColor = (RecyclerView) mRootView.findViewById(R.id.fragment_add_layout_listTextOption_RecyclerView_Color);
    }

    private void initListTextSize() {
        imgView_ShowListTextSizes = (ImageView) mRootView.findViewById(R.id.fragment_add_textoption_imageView_ShowListSize);
        imgView_ShowListTextSizes.setOnClickListener(this);
        lv_ListTextSizes = (ListView) mRootView.findViewById(R.id.fragment_add_layout_listTextOption_ListView_Size);
    }

    private void initListTextPosition() {
        imgView_ShowListTextPostions = (ImageView) mRootView.findViewById(R.id.fragment_add_textoption_imageView_ShowListPosition);
        imgView_ShowListTextPostions.setOnClickListener(this);
        lv_ListTextPositions = (ListView) mRootView.findViewById(R.id.fragment_add_layout_listTextOption_ListView_Position);
    }

    private void showListTextFont() {
        lv_ListTextFonts.setEnabled(false);
        hideListConfig(lv_ListTextStyles);
        hideListConfig(rv_ListTextColor);
        hideListConfig(lv_ListTextSizes);
        hideListConfig(lv_ListTextPositions);
        imgView_ShowListTextStyles.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextColors.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextSizes.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextPostions.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));

        if (lv_ListTextFonts.isEnabled() == false) {
            imgView_ShowListTextFonts.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            ArrayList<String> listTextFonts = new ArrayList<String>();
            ArrayList<String> listTextName = new ArrayList<String>();
            listTextFonts.add("fonts/opensans_regular.ttf");
            listTextName.add("Opensans Regular");
            listTextFonts.add("fonts/aller_regular.ttf");
            listTextName.add("Aller Regular");
            listTextFonts.add("fonts/amaticsc_regular.ttf");
            listTextName.add("Amaticsc Regular");

            listTextFonts.add("fonts/bebas_regular.ttf");
            listTextName.add("Bebas Regular");
            listTextFonts.add("fonts/cac_champagne.ttf");
            listTextName.add("CAC Champagne");
            listTextFonts.add("fonts/color_your_world.ttf");
            listTextName.add("Color Your World");

            listTextFonts.add("fonts/fff_tusj.ttf");
            listTextName.add("FFF Tusj");
            listTextFonts.add("fonts/lato_regular.ttf");
            listTextName.add("Lato Regular");
            listTextFonts.add("fonts/orange_juice_2.ttf");
            listTextName.add("Orange Juice 2");
            listTextFonts.add("fonts/ostrich_regular.ttf");
            listTextName.add("Ostrich Regular");

            listTextFonts.add("fonts/oswald_regular.ttf");
            listTextName.add("Oswald Regular");
            listTextFonts.add("fonts/pacifico.ttf");
            listTextName.add("Pacifico");
            listTextFonts.add("fonts/pt_sans.ttf");
            listTextName.add("Pt Sans");
            listTextFonts.add("fonts/raleway_regular.ttf");
            listTextName.add("Raleway Regular");


            listTextFonts.add("fonts/remachinescript_personal_use.ttf");
            listTextName.add("Remachinescript Personal Use");
            listTextFonts.add("fonts/roboto_regular.ttf");
            listTextName.add("Roboto Regular");
            listTextFonts.add("fonts/southernaire_personal_use_only.ttf");
            listTextName.add("Southernaire Personal Use Only");
            listTextFonts.add("fonts/waltographui.ttf");
            listTextName.add("Waltographui");
            listTextFonts.add("fonts/windsong.ttf");
            listTextName.add("Windsong");
            lv_ListTextFonts.setEnabled(true);
            lv_ListTextFonts.setVisibility(View.VISIBLE);
            Adapter_LV_ListTextFont adapter = new Adapter_LV_ListTextFont(getActivity(), listTextFonts, listTextName);
            lv_ListTextFonts.setAdapter(adapter);
        }
    }

    private void showListTextStyle() {
        hideListConfig(lv_ListTextFonts);
        hideListConfig(rv_ListTextColor);
        hideListConfig(lv_ListTextSizes);
        hideListConfig(lv_ListTextPositions);
        imgView_ShowListTextFonts.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextColors.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextSizes.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextPostions.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        if (lv_ListTextStyles.isEnabled() == false) {
            imgView_ShowListTextStyles.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            ArrayList<Integer> listTextStyles = new ArrayList<Integer>();
            ArrayList<String> listTextName = new ArrayList<String>();
            listTextStyles.add(Typeface.NORMAL);
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_style_normal) + "");
            listTextStyles.add(Typeface.BOLD);
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_style_bold) + "");
            listTextStyles.add(Typeface.ITALIC);
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_style_italic) + "");
            listTextStyles.add(Typeface.BOLD_ITALIC);
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_style_bold_italic) + "");
            lv_ListTextStyles.setEnabled(true);
            lv_ListTextStyles.setVisibility(View.VISIBLE);
            Adapter_LV_ListTextStyle adapter = new Adapter_LV_ListTextStyle(getActivity(), listTextStyles, listTextName);
            lv_ListTextStyles.setAdapter(adapter);
        }
    }

    private void showListTextColor() {
        hideListConfig(lv_ListTextFonts);
        hideListConfig(lv_ListTextStyles);
        hideListConfig(lv_ListTextSizes);
        hideListConfig(lv_ListTextPositions);
        imgView_ShowListTextFonts.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextStyles.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextSizes.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextPostions.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        if (rv_ListTextColor.isEnabled() == false) {
            imgView_ShowListTextColors.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            ArrayList<String> listIcon = new ArrayList<>();
            listIcon.add(R.color.colorTrang + "");
            listIcon.add(R.color.colorDen + "");
            listIcon.add(R.color.color_mt_YellowLight_1 + "");
            listIcon.add(R.color.color_mt_YellowPrimary_1 + "");
            listIcon.add(R.color.color_mt_YellowDark_1 + "");
            listIcon.add(R.color.colorXanhLa + "");
            listIcon.add(R.color.color_mt_GreenLight_1 + "");
            listIcon.add(R.color.color_mt_GreenPrimary_1 + "");
            listIcon.add(R.color.color_mt_GreenDark_1 + "");
            listIcon.add(R.color.colorXanh + "");
            listIcon.add(R.color.color_mt_BlueLight + "");
            listIcon.add(R.color.color_mt_BluePrimary + "");
            listIcon.add(R.color.color_mt_BlueDark + "");
            listIcon.add(R.color.colorPrimaryDark + "");
            listIcon.add(R.color.colorAccent + "");
            listIcon.add(R.color.colorTim + "");
            listIcon.add(R.color.color_mt_PurpleLight_1 + "");
            listIcon.add(R.color.color_mt_PurplePrimary_1 + "");
            listIcon.add(R.color.color_mt_PurpleDark_1 + "");

            rv_ListTextColor.setEnabled(true);
            rv_ListTextColor.setVisibility(View.VISIBLE);
            rv_ListTextColor.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
            rv_ListTextColor.setHasFixedSize(true);
            Adapter_RV_ListTextColor adapter = new Adapter_RV_ListTextColor(getContext(), listIcon);
            rv_ListTextColor.setAdapter(adapter);

        }
    }

    private void showListTextSize() {
        hideListConfig(lv_ListTextFonts);
        hideListConfig(rv_ListTextColor);
        hideListConfig(lv_ListTextStyles);
        hideListConfig(lv_ListTextPositions);
        imgView_ShowListTextFonts.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextStyles.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextColors.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextPostions.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        if (lv_ListTextSizes.isEnabled() == false) {
            imgView_ShowListTextSizes.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            ArrayList<Integer> listTextStyles = new ArrayList<Integer>();
            ArrayList<String> listTextName = new ArrayList<String>();
            for (int i = 15; i <= 51; i += 2) {
                listTextStyles.add(i);
                listTextName.add(i + "");
            }
            lv_ListTextSizes.setEnabled(true);
            lv_ListTextSizes.setVisibility(View.VISIBLE);
            Adapter_LV_ListTextSize adapter = new Adapter_LV_ListTextSize(getActivity(), listTextStyles, listTextName);
            lv_ListTextSizes.setAdapter(adapter);
        }
    }

    private void showListTextPosition() {
        hideListConfig(lv_ListTextFonts);
        hideListConfig(rv_ListTextColor);
        hideListConfig(lv_ListTextStyles);
        hideListConfig(lv_ListTextSizes);
        imgView_ShowListTextFonts.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextStyles.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextColors.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        imgView_ShowListTextSizes.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
        if (lv_ListTextPositions.isEnabled() == false) {
            imgView_ShowListTextPostions.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            ArrayList<String> listTextStyles = new ArrayList<String>();
            ArrayList<String> listTextName = new ArrayList<String>();
            listTextStyles.add("Left");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_left) + "");
            listTextStyles.add("Right");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_right) + "");
            listTextStyles.add("Center");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_center) + "");
            listTextStyles.add("Top");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_top) + "");
            listTextStyles.add("Bottom");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_bottom) + "");

            listTextStyles.add("Left Center");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_left_center) + "");
            listTextStyles.add("Left Top");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_left_top) + "");
            listTextStyles.add("Left Bottom");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_left_bottom) + "");

            listTextStyles.add("Right Center");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_right_center) + "");
            listTextStyles.add("Right Top");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_right_top) + "");
            listTextStyles.add("Right Bottom");
            listTextName.add(getContext().getString(R.string.fragment_add_textoption_position_right_bottom) + "");

            lv_ListTextPositions.setEnabled(true);
            lv_ListTextPositions.setVisibility(View.VISIBLE);
            Adapter_LV_ListTestPostion adapter = new Adapter_LV_ListTestPostion(getActivity(), listTextStyles, listTextName);
            lv_ListTextPositions.setAdapter(adapter);
        }
    }

    private void hideListConfig(View ln) {
        ln.setEnabled(false);
        ln.setVisibility(View.GONE);
    }

    private void initListEmotion() {
        mRecyclerViewEmotion1 = (RecyclerView) mRootView.findViewById(R.id.fragment_add_recyclerviewListEmotion);
        ArrayList<String> listIcon = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            listIcon.add("em_yellowface_" + i);
        }
        for (int i = 1; i <= 3; i++) {
            listIcon.add("em_smileyforfun_" + i);
        }
        for (int i = 1; i <= 3; i++) {
            listIcon.add("em_streamline_emoji_" + i);
        }
        for (int i = 1; i <= 3; i++) {
            listIcon.add("em_hero_" + i);
        }

        for (int i = 1; i <= 4; i++) {
            listIcon.add("em_starwar_" + i);
        }


        mRecyclerViewEmotion1.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        mRecyclerViewEmotion1.setHasFixedSize(true);
        mRecyclerViewEmotion1.setAdapter(new AdapterForListEmotion(getContext(), listIcon));
    }

    private void initListImageBackGround() {
        mRecyclerViewImageBackground = (RecyclerView) mRootView.findViewById(R.id.fragment_add_recyclerviewListImageBackground);
        ArrayList<String> listIcon = new ArrayList<>();
        listIcon.add(R.color.colorTransparent + "");
        listIcon.add(R.color.colorTrang + "");
        listIcon.add(R.color.colorDen + "");
        listIcon.add(R.color.color_mt_YellowLight_1 + "");
        listIcon.add(R.color.color_mt_YellowPrimary_1 + "");
        listIcon.add(R.color.color_mt_YellowDark_1 + "");
        listIcon.add("side_nav_bar_2");
        listIcon.add(R.color.colorXanhLa + "");
        listIcon.add(R.color.color_mt_GreenLight_1 + "");
        listIcon.add(R.color.color_mt_GreenPrimary_1 + "");
        listIcon.add(R.color.color_mt_GreenDark_1 + "");
        listIcon.add("side_nav_bar_3");
        listIcon.add(R.color.colorXanh + "");
        listIcon.add(R.color.color_mt_BlueLight + "");
        listIcon.add(R.color.color_mt_BluePrimary + "");
        listIcon.add(R.color.color_mt_BlueDark + "");
        listIcon.add(R.color.colorPrimaryDark + "");
        listIcon.add("side_nav_bar");
        listIcon.add(R.color.colorAccent + "");
        listIcon.add(R.color.colorTim + "");
        listIcon.add(R.color.color_mt_PurpleLight_1 + "");
        listIcon.add(R.color.color_mt_PurplePrimary_1 + "");
        listIcon.add(R.color.color_mt_PurpleDark_1 + "");
        listIcon.add("side_nav_bar_4");

        for (int i = 1; i <= 3; i++) {
            listIcon.add("imgbackground_" + i);
        }
        mRecyclerViewImageBackground.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        mRecyclerViewImageBackground.setHasFixedSize(true);
        mRecyclerViewImageBackground.setAdapter(new AdapterForListImageBackground(getContext(), listIcon));
    }

    private void initListEditTextBackGround() {
        mRecyclerViewEditTextBackground = (RecyclerView) mRootView.findViewById(R.id.fragment_add_recyclerviewLisEditTextBackground);
        ArrayList<String> listIcon = new ArrayList<>();
        listIcon.add(R.color.colorTransparent + "");
        listIcon.add(R.color.colorTrang + "");
        listIcon.add(R.color.colorDen + "");
        listIcon.add(R.color.color_mt_YellowLight_1 + "");
        listIcon.add(R.color.color_mt_YellowPrimary_1 + "");
        listIcon.add(R.color.color_mt_YellowDark_1 + "");
        listIcon.add("side_nav_bar_2");
        listIcon.add(R.color.colorXanhLa + "");
        listIcon.add(R.color.color_mt_GreenLight_1 + "");
        listIcon.add(R.color.color_mt_GreenPrimary_1 + "");
        listIcon.add(R.color.color_mt_GreenDark_1 + "");
        listIcon.add("side_nav_bar_3");
        listIcon.add(R.color.colorXanh + "");
        listIcon.add(R.color.color_mt_BlueLight + "");
        listIcon.add(R.color.color_mt_BluePrimary + "");
        listIcon.add(R.color.color_mt_BlueDark + "");
        listIcon.add(R.color.colorPrimaryDark + "");
        listIcon.add("side_nav_bar");
        listIcon.add(R.color.colorAccent + "");
        listIcon.add(R.color.colorTim + "");
        listIcon.add(R.color.color_mt_PurpleLight_1 + "");
        listIcon.add(R.color.color_mt_PurplePrimary_1 + "");
        listIcon.add(R.color.color_mt_PurpleDark_1 + "");
        listIcon.add("side_nav_bar_4");
        mRecyclerViewEditTextBackground.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        mRecyclerViewEditTextBackground.setHasFixedSize(true);
        mRecyclerViewEditTextBackground.setAdapter(new AdapterForListEditTextBackground(getContext(), listIcon));
    }

    //Cho Page Diary Tạm----------------------
    public void setPageDiary_Emotion(String iconName) {
        mPagaDiaryTam.setEmotion(iconName + "");
        int id = getContext().getResources().getIdentifier(iconName, "drawable", getContext().getPackageName());
        mImageViewEmotion.setImageResource(id);
    }

    public void setPageDiary_ImageBackground(String imageviewName) {
        mPagaDiaryTam.setBackground(imageviewName + "");
        if (isNumericOnlyNumber(imageviewName) == true) {
            mImgViewBackGround.setBackgroundColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getBackground())));
            Log.e("PADIARY ", "Color: " + mPagaDiaryTam.getColor());
        } else {
            int id = getContext().getResources().getIdentifier(imageviewName, "drawable", getContext().getPackageName());
            mImgViewBackGround.setBackgroundResource(id);
            Log.e("PADIARY ", "Image BackGround: " + mPagaDiaryTam.getBackground());
        }
    }

    public static boolean isNumericOnlyNumber(String str) {
        return str.matches("\\d+");
    }

    public void setPageDiary_EditBackground(String colorName) {
        mPagaDiaryTam.setEditTextBackGround(colorName + "");

        if (isNumericOnlyNumber(colorName) == true) {
            mContent.setBackgroundColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getEditTextBackGround())));
            Log.e("PADIARY ", "EditText BackGround: " + mPagaDiaryTam.getEditTextBackGround());
        } else {
            int id = getContext().getResources().getIdentifier(colorName, "drawable", getContext().getPackageName());
            mContent.setBackgroundResource(id);
            Log.e("PADIARY ", "EditText BackGround: " + mPagaDiaryTam.getEditTextBackGround());
        }
    }


    public void setPageDiary_TextFont(String fontName) {
        mPagaDiaryTam.setFont(fontName + "");
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), fontName);
        mContent.setTypeface(type, Integer.parseInt(mPagaDiaryTam.getStyle()));
        Log.e("PADIARY ", "Font: " + mPagaDiaryTam.getFont() + " - Style: " + mPagaDiaryTam.getStyle());
    }

    public void setPageDiary_TextStyle(String styleName) {
        mPagaDiaryTam.setStyle(styleName + "");
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), mPagaDiaryTam.getFont());
        mContent.setTypeface(type, Integer.parseInt(mPagaDiaryTam.getStyle()));
        Log.e("PADIARY ", "Font: " + mPagaDiaryTam.getFont() + " - Style: " + mPagaDiaryTam.getStyle());
    }

    public void setPageDiary_TextColor(String colorName) {
        mPagaDiaryTam.setColor(colorName + "");
        mContent.setTextColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getColor())));
        Log.e("PADIARY ", "Color: " + mPagaDiaryTam.getColor());
    }

    public void setPageDiary_TextSize(String sizeName) {
        mPagaDiaryTam.setSize(sizeName + "");
        mContent.setTextSize(Float.parseFloat(mPagaDiaryTam.getSize()));
        Log.e("PADIARY ", "Size: " + mPagaDiaryTam.getSize());
    }

    public void setPageDiary_TextPostion(String postionName) {
        mPagaDiaryTam.setPosition(postionName + "");
        SwicthPosition(mPagaDiaryTam.getPosition());
        Log.e("PADIARY ", "Position: " + mPagaDiaryTam.getPosition());
    }

    private void SwicthPosition(String position) {
        switch (position) {
            case "Left":
                mContent.setGravity(Gravity.LEFT);
                break;
            case "Right":
                mContent.setGravity(Gravity.RIGHT);
                break;
            case "Center":
                mContent.setGravity(Gravity.CENTER);
                break;
            case "Top":
                mContent.setGravity(Gravity.TOP);
                break;
            case "Bottom":
                mContent.setGravity(Gravity.BOTTOM);
                break;

            case "Left Center":
                mContent.setGravity(Gravity.LEFT | Gravity.CENTER);
                break;
            case "Left Top":
                mContent.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case "Left Bottom":
                mContent.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case "Right Center":
                mContent.setGravity(Gravity.RIGHT | Gravity.CENTER);
                break;
            case "Right Top":
                mContent.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case "Right Bottom":
                mContent.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
        }
    }
    //-----------------------------------------

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
        loadPageDiary();
    }

    private void loadPageDiary() {
        if (this.getArguments() != null) {
            if (this.getArguments().get(FRAGMENT_ADD_KEY_LOADPAGE) != null) {
                PageDiary p = (PageDiary) this.getArguments().get(FRAGMENT_ADD_KEY_LOADPAGE);
                mPagaDiaryTam = p;
                Log.e("Fragment Load Page", "" + p);
                int id = getContext().getResources().getIdentifier(mPagaDiaryTam.getEmotion(), "drawable", getContext().getPackageName());
                mImageViewEmotion.setImageResource(id);
                if (isNumericOnlyNumber(mPagaDiaryTam.getBackground()) == true) {
                    mImgViewBackGround.setBackgroundColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getBackground())));
                } else {
                    int id2 = getContext().getResources().getIdentifier(mPagaDiaryTam.getBackground(), "drawable", getContext().getPackageName());
                    mImgViewBackGround.setBackgroundResource(id2);
                }
                if (isNumericOnlyNumber(mPagaDiaryTam.getEditTextBackGround()) == true) {
                    mContent.setBackgroundColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getEditTextBackGround())));
                } else {
                    int id3 = getContext().getResources().getIdentifier(mPagaDiaryTam.getEditTextBackGround(), "drawable", getContext().getPackageName());
                    mContent.setBackgroundResource(id3);
                }
                Typeface type = Typeface.createFromAsset(getActivity().getAssets(), mPagaDiaryTam.getFont());
                mContent.setTypeface(type, Integer.parseInt(mPagaDiaryTam.getStyle()));
                mContent.setTextColor(ContextCompat.getColor(getContext(), Integer.parseInt(mPagaDiaryTam.getColor())));
                mContent.setTextSize(Float.parseFloat(mPagaDiaryTam.getSize()));
                SwicthPosition(mPagaDiaryTam.getPosition());
                mContent.setText(mPagaDiaryTam.getContent() + "");
                mTextViewDateAndTime.setText(mPagaDiaryTam.getDateTime()+"");
                Log.e("Fragment Load Page", "" + mPagaDiaryTam);
                this.getArguments().remove(FRAGMENT_ADD_KEY_LOADPAGE);
            }
        }
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
            case R.id.fragment_add_imageViewShowListEmotion:
                showOrHideListEmotion();
                break;
            case R.id.fragment_add_imageViewShowListBackGround:
                showOrHideListBackGround();
                break;
            case R.id.fragment_add_imageViewShowListEditTextBackGround:
                showOrHideListEditTextBackGround();
                break;
            case R.id.fragment_add_imageViewShowLisTextOption:
                showOrHideListTextOption();
                break;
            case R.id.fragment_add_layout_Contain_ListEmotion:
                hideListConfig(mContainListEmotion_LnLayout);
                break;
            case R.id.fragment_add_layout_Contain_ListImageBackGround:
                hideListConfig(mContainListBackGround_LnLayout);
                break;
            case R.id.fragment_add_layout_Contain_ListEditTextBackGround:
                hideListConfig(mContainListEditTextBackGround_LnLayout);
                break;
            case R.id.fragment_add_layout_Contain_ListTextOption:
                //hideListConfig(mContainListTextOption_LnLayout);
                break;
            case R.id.fragment_add_content_LNforEdtAndBackGround:
                hideListConfig(mContainListBackGround_LnLayout);
                hideListConfig(mContainListEmotion_LnLayout);
                hideListConfig(mContainListEditTextBackGround_LnLayout);
                hideListConfig(mContainListTextOption_LnLayout);
                break;
            case R.id.fragment_add_edittextContent:
                hideListConfig(mContainListBackGround_LnLayout);
                hideListConfig(mContainListEmotion_LnLayout);
                hideListConfig(mContainListEditTextBackGround_LnLayout);
                hideListConfig(mContainListTextOption_LnLayout);
                break;
            case R.id.fragment_add_textoption_imageView_ShowListFont:
                showListTextFont();
                break;
            case R.id.fragment_add_textoption_imageView_ShowListStyle:
                showListTextStyle();
                break;
            case R.id.fragment_add_textoption_imageView_ShowListColor:
                showListTextColor();
                break;
            case R.id.fragment_add_textoption_imageView_ShowListSize:
                showListTextSize();
                break;
            case R.id.fragment_add_textoption_imageView_ShowListPosition:
                showListTextPosition();
                break;
           /* case R.id.fragment_add_buttonconfig:
                if (!mLnLayoutContainer_Config.isShown())
                    mLnLayoutContainer_Config.setVisibility(View.VISIBLE);
                break;
            case R.id.fragment_add_layout_container_config:
                if (mLnLayoutContainer_Config.isShown())
                    mLnLayoutContainer_Config.setVisibility(View.INVISIBLE);
                break;*/
        }
    }
}
