package com.twtstudio.tjwhm.lostfound.mylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twtstudio.tjwhm.lostfound.R;
import com.twtstudio.tjwhm.lostfound.detail.DetailActivity;
import com.twtstudio.tjwhm.lostfound.release.ReleaseActivity;
import com.twtstudio.tjwhm.lostfound.support.Utils;
import com.twtstudio.tjwhm.lostfound.waterfall.WaterfallBean;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tjwhm on 2017/7/4.
 **/

public class MylistTableAdapter extends RecyclerView.Adapter {

    MylistBean mylistBean;
    Context context;
    String lostOrFound;

    public MylistTableAdapter(MylistBean mylistBean, Context context, String lostOrFound) {
        this.mylistBean = mylistBean;
        this.context = context;
        this.lostOrFound = lostOrFound;
    }

    public class MylistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mylist_item_status)
        TextView mylist_item_status;
        @BindView(R.id.mylist_item_title)
        TextView mylist_item_title;
        @BindView(R.id.mylist_item_type)
        TextView mylist_item_type;
        @BindView(R.id.mylist_item_time)
        TextView mylist_item_time;
        @BindView(R.id.mylist_item_place)
        TextView mylist_item_place;
        @BindView(R.id.mylist_item_back_blue)
        ImageView mylist_item_back_blue;
        @BindView(R.id.mylist_item_back_grey)
        ImageView mylist_item_back_grey;
        @BindView(R.id.mylist_item_pencil)
        ImageView mylist_item_pencil;

        public MylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_mylist, parent, false);
        return new MylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MylistViewHolder viewHolder = (MylistViewHolder) holder;
        WaterfallBean.DataBean dataBean = mylistBean.data.get(position);
        viewHolder.mylist_item_title.setText(dataBean.title);
        viewHolder.mylist_item_type.setText(Utils.getType(dataBean.detail_type));
        viewHolder.mylist_item_time.setText(dataBean.time);
        viewHolder.mylist_item_place.setText(dataBean.place);

        if (dataBean.isback == 1) {
            viewHolder.mylist_item_back_blue.setVisibility(View.VISIBLE);
            viewHolder.mylist_item_back_grey.setVisibility(View.GONE);
            if (Objects.equals(lostOrFound, "found")) {
                viewHolder.mylist_item_status.setText("已交还");
            } else {
                viewHolder.mylist_item_status.setText("已找到");
            }
        } else {
            viewHolder.mylist_item_back_blue.setVisibility(View.GONE);
            viewHolder.mylist_item_back_grey.setVisibility(View.VISIBLE);
            if (Objects.equals(lostOrFound, "found")) {
                viewHolder.mylist_item_status.setText("未交还");
            } else {
                viewHolder.mylist_item_status.setText("未找到");
            }
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        viewHolder.itemView.setOnClickListener(view -> {
            bundle.putInt("id", dataBean.id);
            intent.putExtras(bundle);
            intent.setClass(context, DetailActivity.class);
            context.startActivity(intent);
        });
        viewHolder.mylist_item_pencil.setOnClickListener(view -> {
            if (Objects.equals(lostOrFound, "lost")) {
                bundle.putString("lostOrFound", "editLost");
            }else{
                bundle.putString("lostOrFound","editFound");
            }
            bundle.putInt("id", dataBean.id);
            bundle.putInt("type",dataBean.detail_type);
            intent.putExtras(bundle);
            intent.setClass(context, ReleaseActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mylistBean.data.size();
    }
}
