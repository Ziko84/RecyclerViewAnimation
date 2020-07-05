package com.ziko.isaac.recyclerviewanimation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ziko.isaac.recyclerviewanimation.Model.News;
import com.ziko.isaac.recyclerviewanimation.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<News> mData;
    private boolean isDark = false;

    public RecyclerViewAdapter(Context context, List<News> mData, boolean isDark) {
        this.context = context;
        this.mData = mData;
        this.isDark = isDark;
    }

    public RecyclerViewAdapter(Context context, List<News> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Animation for Image
        holder.img_user.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        //Animation for the whole card
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));




        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_description.setText(mData.get(position).getDescription());
        holder.tv_date.setText(mData.get(position).getDate());

        holder.img_user.setImageResource(mData.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_description;
        private TextView tv_date;
        private ImageView img_user;
        private RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);

            if(isDark){
                setDarkMode();
            }
        }

        private void setDarkMode(){
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }

    }
}
