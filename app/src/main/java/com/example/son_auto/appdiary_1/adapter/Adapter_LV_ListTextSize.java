package com.example.son_auto.appdiary_1.adapter;

import android.app.Activity;
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

public class Adapter_LV_ListTextSize extends BaseAdapter {
    private Activity activity;
    private ArrayList<Integer> listItem;
    private ArrayList<String> listName;

    public Adapter_LV_ListTextSize(Activity activity, ArrayList<Integer> items, ArrayList<String> listName) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_list_textoption_size, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTextFont = (TextView) convertView.findViewById(R.id.item_list_textoption_size_textView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvTextFont.setText(listName.get(position).toString() + "");
        viewHolder.tvTextFont.setTextSize(listItem.get(position));
        viewHolder.tvTextFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).getFragmentAdd().setPageDiary_TextSize(listItem.get(position) + "");
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private TextView tvTextFont;
    }
}
