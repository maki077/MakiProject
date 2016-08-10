package com.maki.project.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maki.project.R;
import com.maki.project.model.bean.Meizi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Administrator on 2016/8/9.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziHolder> {
    List<Meizi> list;
    Context context;


    public MeiziAdapter(List<Meizi> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MeiziHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_meizi, parent, false);
        return new MeiziHolder(view);
    }

    @Override
    public void onBindViewHolder(MeiziHolder holder, int position) {
        Meizi meizi = list.get(position);
        holder.card.setTag(meizi);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        holder.ivMeizi.setBackgroundColor(Color.argb(204, red, green, blue)); //随机背景颜色
        Glide
                .with(context)
                .load(meizi.getUrl())
                .crossFade()
                .into(holder.ivMeizi);

        holder.tvWho.setText(meizi.getWho());

//        holder.tvAvatar.setText(meizi
//                .getWho()
//                .substring(0, 1)
//                .toUpperCase());
        holder.tvDesc.setText(meizi.getDesc());
        holder.tvTime.setText(meizi.getPublishedAt());
//        holder.tvAvatar.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    class MeiziHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_meizi)
        AppCompatImageView ivMeizi;
        @BindView(R.id.tv_avatar)
        TextView tvAvatar;
        @BindView(R.id.tv_who)
        TextView tvWho;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        View card;

        public MeiziHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
