package com.example.son_auto.appdiary_1.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 4/12/2018.
 */

public class Adapter_LV_ListTextFont extends BaseAdapter {
    private Activity activity;
    private ArrayList<String> listItem;
    private ArrayList<String> listName;

    public Adapter_LV_ListTextFont(Activity activity, ArrayList<String> items, ArrayList<String> listName) {
        this.activity = activity;
        this.listItem = items;
        this.listName = listName;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_list_textoption_font, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTextFont = (TextView) convertView.findViewById(R.id.item_list_textoption_font_textView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        Typeface type = Typeface.createFromAsset(activity.getAssets(), listItem.get(position).toString());
        viewHolder.tvTextFont.setTypeface(type);
        viewHolder.tvTextFont.setText(listName.get(position).toString() + "");
        viewHolder.tvTextFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).getFragmentAdd().setPageDiary_TextFont(listItem.get(position));
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        private TextView tvTextFont;
    }
}
