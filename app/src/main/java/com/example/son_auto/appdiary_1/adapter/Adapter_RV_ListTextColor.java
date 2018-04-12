package com.example.son_auto.appdiary_1.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.son_auto.appdiary_1.R;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 4/12/2018.
 */

public class Adapter_RV_ListTextColor extends RecyclerView.Adapter<Adapter_RV_ListTextColor.DataViewHolder> {
    private ArrayList<String> listEditTextColor;
    private Context context;


    public Adapter_RV_ListTextColor(Context context, ArrayList<String> listEditTextColor) {
        this.listEditTextColor = listEditTextColor;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_texoption_color, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        int id = Integer.parseInt(listEditTextColor.get(position));
        holder.imageViewEmotion.setBackgroundColor(ContextCompat.getColor(context, id));
    }

    @Override
    public int getItemCount() {
        if (listEditTextColor == null)
            return 0;
        return listEditTextColor.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEmotion;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_list_textoption_color_imageview);
        }

    }
}
