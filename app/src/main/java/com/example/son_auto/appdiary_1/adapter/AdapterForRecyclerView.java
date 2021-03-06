package com.example.son_auto.appdiary_1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String FRAGMENT_ADD_KEY_LOADPAGE = "loadpage";

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
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        final PageDiary p = listPage.get(position);
        holder.tvContent.setText(p.getContent());
        // holder.imageViewBackGround.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(p.getBackground())));
        if (isNumericOnlyNumber(p.getBackground()) == true) {
            holder.imageViewBackGround.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(p.getBackground())));
        } else {
            int id = context.getResources().getIdentifier(p.getBackground(), "drawable", context.getPackageName());
            holder.imageViewBackGround.setBackgroundResource(id);
        }
        holder.imageViewEmotion.setImageResource(context.getResources().getIdentifier(p.getEmotion(), "drawable", context.getPackageName()));

        final boolean[] isLongClick = {false};
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLongClick[0] == false) {
                    ((MainActivity) context).setBackButton(true);
                    ((MainActivity) context).flagFind = false;
                    ((MainActivity) context).setCommand(COMMAND_SHOW_FRAGMENT_ADD);
                    Bundle b = new Bundle();
                    b.putSerializable(FRAGMENT_ADD_KEY_LOADPAGE, p);
                    ((MainActivity) context).getFragmentAdd().setArguments(b);

                }
                isLongClick[0] = false;
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick[0] = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.fragment_listpage_delete_attention));
                builder.setMessage(context.getResources().getString(R.string.fragment_listpage_delete_content));
                builder.setIcon(context.getResources().getIdentifier("em_androidmood_1_size32", "drawable", context.getPackageName()));
                builder.setPositiveButton(context.getResources().getString(R.string.fragment_listpage_delete_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.getDiaryDatabase().xoaPageDiary(p.getId());

                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                        ((Activity) context).finish();
                    }
                });
                builder.setNegativeButton(context.getResources().getString(R.string.fragment_listpage_delete_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });


    }

    public static boolean isNumericOnlyNumber(String str) {
        return str.matches("\\d+");
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
        private ImageView imageViewBackGround;
        private RelativeLayout relativeLayout;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.item_textView);
            imageViewEmotion = (ImageView) itemView.findViewById(R.id.item_imageView);
            imageViewBackGround = (ImageView) itemView.findViewById(R.id.item_imageViewBackGround);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_linear_layout);
        }

    }

}
