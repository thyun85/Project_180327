package com.thy.project_180327;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alofo on 2018-03-27.
 */

public class Adapter_All extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public Adapter_All(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);

        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;

        Item item = items.get(position);

//        item.getCodeName().equals("")

        vh.facName.setText(item.getFacName());
        vh.codeName.setText(item.getCodeName());
        vh.phne.setText(item.getPhne());
        vh.addr.setText(item.getAddr());

        if(item.getImgURL() == null){   //이미지가 없는가?
            vh.imgURL.setVisibility(View.GONE);
        }else{
            vh.imgURL.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getImgURL()).into(vh.imgURL);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView facName, codeName, phne, addr;
        ImageView imgURL;

        public VH(View itemView) {
            super(itemView);

            facName = itemView.findViewById(R.id.tv_facname);
            codeName = itemView.findViewById(R.id.tv_codename);
            phne = itemView.findViewById(R.id.tv_phne);
            addr = itemView.findViewById(R.id.tv_addr);
            imgURL = itemView.findViewById(R.id.iv_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String facName = items.get(getLayoutPosition()).getFacName();
                    String codeName = items.get(getLayoutPosition()).getCodeName();
                    String phne = items.get(getLayoutPosition()).getPhne();
                    String addr = items.get(getLayoutPosition()).getAddr();
                    String imgUrl = items.get(getLayoutPosition()).getImgURL();
                    String homepage = items.get(getLayoutPosition()).getHomepage();
                    String closeday = items.get(getLayoutPosition()).getCloseday();
                    String xcoord = items.get(getLayoutPosition()).getXcoord();
                    String ycoord = items.get(getLayoutPosition()).getYcoord();

                    Intent intent = new Intent(context, SelectitemActivity.class);
                    intent.putExtra("FacName", facName);
                    intent.putExtra("CodeName", codeName);
                    intent.putExtra("Phne", phne);
                    intent.putExtra("Addr", addr);
                    intent.putExtra("ImgURL", imgUrl);
                    intent.putExtra("Homepage", homepage);
                    intent.putExtra("Closeday", closeday);
                    intent.putExtra("Xcoord", xcoord);
                    intent.putExtra("Ycoord", ycoord);

                    context.startActivity(intent);
                    //전환효과
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)context, new Pair<View, String>(imgURL, "IMG"));
                        context.startActivity(intent, activityOptions.toBundle());
                    }else{
                        context.startActivity(intent);
                    }

                }
            });

        }

    }
}
