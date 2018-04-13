package com.example.son_auto.appdiary_1.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 4/11/2018.
 */

public class AdapterForListEditTextBackground extends RecyclerView.Adapter<AdapterForListEditTextBackground.DataViewHolder> {

    private ArrayList<String> listEditTextBackGround;
    private Context context;


    public AdapterForListEditTextBackground(Context context, ArrayList<String> listEditTextBackGround) {
        this.listEditTextBackGround = listEditTextBackGround;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_edittextbackground, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        int id = Integer.parseInt(listEditTextBackGround.get(position));
        holder.imageViewEmotion.setBackgroundColor(ContextCompat.getColor(context, id));
        holder.imageViewEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).getFragmentAdd().setPageDiary_EditBackground(listEditTextBackGround.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listEditTextBackGround == null)
            return 0;
        return listEditTextBackGround.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEmotion;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_edittextbackgrond_imageview);
        }

    }
}
