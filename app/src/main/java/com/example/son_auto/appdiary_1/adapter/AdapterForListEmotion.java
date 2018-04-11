package com.example.son_auto.appdiary_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.son_auto.appdiary_1.R;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 3/11/2018.
 */

public class AdapterForListEmotion extends RecyclerView.Adapter<AdapterForListEmotion.DataViewHolder> {
    private ArrayList<String> listEmotion;
    private Context context;


    public AdapterForListEmotion(Context context, ArrayList<String> listEmotion) {
        this.listEmotion = listEmotion;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_emotion, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        int id = context.getResources().getIdentifier(listEmotion.get(position), "drawable", context.getPackageName());
        holder.imageViewEmotion.setImageResource(id);
        holder.imageViewEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hahaha "+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listEmotion == null)
            return 0;
        return listEmotion.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEmotion;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_emotion_imageview2);
        }

    }
}
