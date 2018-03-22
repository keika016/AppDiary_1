package com.example.son_auto.appdiary_1;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
    }

    private void init() {
        initView();
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
                            textView.setText(s+"");
                            textView.setTextSize(20f);
                            textView.setTextColor(ContextCompat.getColor(AboutActivity.this,R.color.colorAccent));
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
                Intent i = new Intent(AboutActivity.this, MainActivity.class);
                finish();
                startActivity(i);
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
