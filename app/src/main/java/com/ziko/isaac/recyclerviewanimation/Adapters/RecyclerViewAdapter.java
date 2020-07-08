package com.ziko.isaac.recyclerviewanimation.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ziko.isaac.recyclerviewanimation.Model.News;
import com.ziko.isaac.recyclerviewanimation.Model.YellowFlowerModel;
import com.ziko.isaac.recyclerviewanimation.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<String> creatorList, imgUrlList;
    private ArrayList<Integer> likesList;
    private Cursor cursor;
    private List<YellowFlowerModel> mData;
    private List<YellowFlowerModel> mDataFilter;
    private onItemClickListener mListener;
    private boolean isDark = false;

    public interface onItemClickListener {
        void onItemclick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerViewAdapter(Context context,
                               ArrayList<String> creatorList, ArrayList<String> imgUrlList, ArrayList<Integer> likesList,
                               boolean isDark) {
        this.context = context;
        this.creatorList = creatorList;
        this.imgUrlList = imgUrlList;
        this.likesList = likesList;
        this.isDark = isDark;
    }

    public RecyclerViewAdapter(Context context, List<YellowFlowerModel> mData, boolean isDark) {
        this.context = context;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFilter = mData;
    }

    public RecyclerViewAdapter(Context context, List<YellowFlowerModel> mData) {
        this.context = context;
        this.mData = mData;
        this.mDataFilter = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        if(!cursor.moveToPosition(position)){
//            return;
//        }
//
//        String creator_two = cursor.getString(cursor.getColumnIndex("creator"));
//        String image_url__two = cursor.getString(cursor.getColumnIndex("image_url"));
//        int likes__two = cursor.getInt(cursor.getColumnIndex("likes"));
//
//        holder.tv_creator.setText(creator_two);
//        holder.tv_description.setText(image_url__two);
//        holder.tv_likes.setText(String.valueOf(likes__two));

//        String creator = mDataFilter.get(position).getmCreator();
//        String imageURL = mDataFilter.get(position).getmImageUrl();
//        int likeCount = mData.get(position).getmLikes();

        //Animation for Image
        holder.img_user.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        //Animation for the whole card
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

//        holder.tv_creator.setText(mDataFilter.get(position).getmCreator());
//        holder.tv_description.setText(mDataFilter.get(position).getmImageUrl());
//        holder.tv_likes.setText("Likes " +mDataFilter.get(position).getmLikes());
//        Picasso.get().load(imageURL).fit().centerInside().into(holder.img_user);

        holder.tv_creator.setText(String.valueOf(creatorList.get(position)));
        holder.tv_description.setText(String.valueOf(imgUrlList.get(position)));
        holder.tv_likes.setText(String.valueOf(likesList.get(position)));
        Picasso.get().load(String.valueOf(imgUrlList.get(position))).fit().centerInside().into(holder.img_user);


    }

    @Override
    public int getItemCount() {
        return likesList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()) {
                    mDataFilter = mData;
                } else {
                    List<YellowFlowerModel> list_filtered = new ArrayList<>();
                    for (YellowFlowerModel row : mData) {
                        if (row.getmCreator().toLowerCase().contains(key.toLowerCase())) {
                            list_filtered.add(row);
                        }
                    }

                    mDataFilter = list_filtered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFilter = (List<YellowFlowerModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_creator;
        private TextView tv_description;
        private TextView tv_likes;
        private ImageView img_user;
        private RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_creator = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_likes = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemclick(position);
                        }
                    }
                }
            });

            if (isDark) {
                setDarkMode();
            }
        }

        private void setDarkMode() {
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }

    }
}
