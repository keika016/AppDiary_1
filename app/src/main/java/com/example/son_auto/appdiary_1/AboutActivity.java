package com.example.son_auto.appdiary_1;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.database.DiaryDatabase;
import com.example.son_auto.appdiary_1.model.PageDiary;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView;
    private int soLan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
        init2();
    }

    private void init2() {
        imageView = (ImageView) findViewById(R.id.activity_about_2_imageView);
        soLan = 0;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLan += 1;
                if (soLan == 8) {
                    DiaryDatabase diaryDatabase = new DiaryDatabase(getApplicationContext());
                    PageDiary pageDiary_1 = new PageDiary("em_yellowface_1", R.color.color_mt_YellowLight_1 + "", "20 thg 4 2018, 08:45 SA", " 1 This is page Diary ", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_2 = new PageDiary("em_smileyforfun_15", R.color.colorTrang + "", "20 thg 4 2018, 08:46 SA", " 2 This is page Diary ", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_3 = new PageDiary("em_smileyforfun_14", R.color.colorXanhLa + "", "20 thg 4 2018, 08:47 SA", " 3 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_4 = new PageDiary("em_smileyforfun_11", R.color.color_mt_IdigoLight_1 + "", "15 thg 4 2018, 08:45 SA", " 4 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_5 = new PageDiary("em_streamline_emoji_6", R.color.color_mt_PurpleLight_1 + "", "15 thg 4 2018, 08:46 SA", " 5 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_6 = new PageDiary("em_streamline_emoji_10", R.color.color_mt_BlueLight + "", "18 thg 4 2018, 08:47 SA", " 6 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_7 = new PageDiary("em_streamline_emoji_8", R.color.color_mt_GreenLight_1 + "", "18 thg 4 2018, 08:48 SA", " 7 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_8 = new PageDiary("em_smileyforfun_8", R.color.colorTrang + "", "19 thg 4 2018, 09:45 SA", " 8 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_9 = new PageDiary("em_smileyforfun_11", R.color.colorAccent + "", "19 thg 4 2018, 09:49 SA", " 9 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_10 = new PageDiary("em_streamline_emoji_3", R.color.color_mt_YellowLight_1 + "", "25 thg 4 2018, 08:45 SA", " 10 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    PageDiary pageDiary_11 = new PageDiary("em_streamline_emoji_7", R.color.color_mt_YellowLight_1 + "", "25 thg 4 2018, 09:45 SA", " 11 This is page Diary", "fonts/opensans_regular.ttf", Typeface.NORMAL + "", R.color.colorDen + "", 20 + "", "Left Top", "2131034156");
                    diaryDatabase.addDiary(pageDiary_1);
                    diaryDatabase.addDiary(pageDiary_2);
                    diaryDatabase.addDiary(pageDiary_3);
                    diaryDatabase.addDiary(pageDiary_4);
                    diaryDatabase.addDiary(pageDiary_5);
                    diaryDatabase.addDiary(pageDiary_6);
                    diaryDatabase.addDiary(pageDiary_7);
                    diaryDatabase.addDiary(pageDiary_8);
                    diaryDatabase.addDiary(pageDiary_9);
                    diaryDatabase.addDiary(pageDiary_10);
                    diaryDatabase.addDiary(pageDiary_11);
                    Toast.makeText(AboutActivity.this, "Đã khởi tạo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        //initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.activity_about_imageview);
        textView = (TextView) findViewById(R.id.activity_about_textview);
        loadImageView();
        loadContent();
    }

    private void loadContent() {
        OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        final JsonAdapter<String> jsonAdapter = moshi.adapter(String.class);

        String s = "https://script.googleusercontent.com/macros/echo?user_content_key=Q3KI45wEASUYhACjY5RSpOhNTPMjbwQDuVlmNGFMKuRlq6wnyANf_cAuesZhRT3WBHA-ZWi_rwkQRKSem6MqMn5XUBkMH6fXm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnMxOfp2k0PLdzEeefhNLEdadZ6zFvMYdPlDq2dIJmQeRF8TnPVp8CyFQmnkGj-AB2KV-rV9yhQ38&lib=MCo5n2gQ3YrUBmE9-4DjFpTtFl1vSaZRj";
        Request request = new Request.Builder()
                .url(s)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("About Activity", "Khong the ket noi");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(json);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    final String s = jsonObject.getString("content");
                    //đặt textView.setText() trong đây vì Android không có các luồng trên mạng trực tiếp thay đổi giao diện UI main
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(s + "");
                            textView.setTextSize(20f);
                            textView.setTextColor(ContextCompat.getColor(AboutActivity.this, R.color.colorAccent));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;
                //List<String> contents = jsonAdapter.fromJson(json);
                // textView.setText(contents.get(0).toString() + "");
            }
        });
    }

    private void loadImageView() {
        String url = "https://drive.google.com/uc?id=1i4cerQk2b5ZvQXJSDEvqa4YoANGkcd0T";
        String url2 = "https://avatars0.githubusercontent.com/u/1?v=4";
        Picasso.with(this).load(url).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

}
