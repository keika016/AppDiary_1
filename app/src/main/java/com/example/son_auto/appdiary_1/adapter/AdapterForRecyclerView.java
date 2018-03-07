package com.example.son_auto.appdiary_1.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.son_auto.appdiary_1.MainActivity;
import com.example.son_auto.appdiary_1.R;
import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.DataViewHolder> {

    private ArrayList<PageDiary> listPage;
    private Context context;

    private static final String COMMAND_SHOW_FRAGMENT_ADD = "showFragmentAdd";

    public AdapterForRecyclerView(Context context, ArrayList<PageDiary> listPage) {
        this.listPage = listPage;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        PageDiary p = listPage.get(position);
        holder.tvContent.setText(p.getContent());
        holder.imageViewEmotion.setImageResource(context.getResources().getIdentifier(p.getEmotion(), "drawable", context.getPackageName()));
        holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(p.getBackground())));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).setCommand(COMMAND_SHOW_FRAGMENT_ADD);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listPage == null)
            return 0;
        return listPage.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;
        private ImageView imageViewEmotion;
        private LinearLayout linearLayout;
        int selected_position = 0;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.item_textView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_linear_layout);
        }

    }

}
