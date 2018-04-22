package com.example.son_auto.appdiary_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;

import java.util.ArrayList;

public class AdapterForListImageBackground extends RecyclerView.Adapter<AdapterForListImageBackground.DataViewHolder> {
    private ArrayList<String> listImageBackground;
    private Context context;


    public AdapterForListImageBackground(Context context, ArrayList<String> listImageBackground) {
        this.listImageBackground = listImageBackground;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_imagebackground, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        int id = context.getResources().getIdentifier(listImageBackground.get(position).toString(), "drawable", context.getPackageName());
        holder.imageViewEmotion.setImageResource(id);
        holder.imageViewEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).getFragmentAdd().setPageDiary_ImageBackground(listImageBackground.get(position)+"");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listImageBackground == null)
            return 0;
        return listImageBackground.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewEmotion;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_imageviewbackgrond_imageview2);
        }

    }
}
