package com.example.projectakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.HolderItem> {
    List<ModelList> mListItem;
    Context context;

    public AdapterList(List<ModelList> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_info, parent, false);
        HolderItem holder = new HolderItem(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holder, int position) {
        ModelList mlist = mListItem.get(position);

        holder.tv_title.setText(mlist.getTitle());
        holder.tv_detail.setText(mlist.getDetail());

        /*loading image*/
        Glide.with(context)
                .load(mlist.getImage())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder
    {
        ImageView thumbnail;
        TextView tv_title, tv_detail;

        public HolderItem(View v)
        {
            super(v);

            thumbnail = (ImageView) v.findViewById(R.id.img_item_photo);
            tv_title = (TextView) v.findViewById(R.id.tv_item_name);
            tv_detail = (TextView) v.findViewById(R.id.tv_item_detail);
        }
    }
}
