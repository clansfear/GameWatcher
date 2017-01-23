package com.gamehunter.lukasz.gamehunter.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamehunter.lukasz.gamehunter.fragment.WatcherFragment;
import com.gamehunter.lukasz.gamehunter.R;
import com.gamehunter.lukasz.gamehunter.model.Store;

import java.util.List;


/**
 * Created by Łukasz on 2017-01-07.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Store> mData;
    private LayoutInflater mInflater;
    WatcherFragment fragment;

    public RecyclerAdapter(Context context, List<Store> data, WatcherFragment frg)   {
        this.mData=data;
        this.fragment=frg;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Store cObj = mData.get(position);
        holder.setData(cObj,position);
    }



    class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView title, desc;
        ImageView imgThumb, imgDelete;
        int position;
        Store current;

    public MyViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.tvTitle);
        desc = (TextView) itemView.findViewById(R.id.tvDescription);
        imgThumb=(ImageView) itemView.findViewById(R.id.img_row);
        imgDelete=(ImageView) itemView.findViewById(R.id.img_row_delete);
        //imgAdd=(ImageView) itemView.findViewById(R.id.img_row_delete);
    }

        public void setData(final Store cObj, int position) {


            this.title.setText(cObj.getTitle());

            this.title.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cheapshark.com/redirect?dealID=" + cObj.getDealID()));
                    fragment.startActivity(browserIntent);
                }
            });

            this.imgDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Store.removeEntry(cObj);
                }
            });

            this.desc.setText(cObj.getDescription() + "$");
            Glide.with(fragment).load(cObj.getImageID()).into(this.imgThumb);
            //this.imgThumb.setImageResource(cObj.getImageID());
            this.position=position;
            this.current=cObj;
        }
    }

}
