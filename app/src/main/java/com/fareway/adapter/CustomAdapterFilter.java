package com.fareway.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.model.Category;

import java.util.List;

public class CustomAdapterFilter extends RecyclerView.Adapter<CustomAdapterFilter.MyViewHolder> {

    private Context mContext;
    private List<Category> categoryList;
    public MainFwActivity activate = new MainFwActivity();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_type;
        private ImageView imv_go;
        private LinearLayout rowLayout;

        public MyViewHolder(View view) {
            super(view);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            imv_go = (ImageView) view.findViewById(R.id.imv_go);
            rowLayout= (LinearLayout) view.findViewById(R.id.rowLayout);
        }
    }


    public CustomAdapterFilter(Context mContext, List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public CustomAdapterFilter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_item_row, parent, false);

        return new CustomAdapterFilter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterFilter.MyViewHolder holder, final int position) {
        final Category category = categoryList.get(position);
       /* if (category.getCategoryName().equalsIgnoreCase("")){

        }else {

        }*/
       try {
           if (category.getCategoryID()==1000){
               Log.i("ifcat","test");
               holder.tv_type.setText("");
               holder.rowLayout.setVisibility(View.GONE);
           }else {
               Log.i("elsecat",category.getCategoryName());
               holder.tv_type.setText(category.getCategoryName());
               holder.rowLayout.setVisibility(View.VISIBLE);
           }

       }catch (Exception e){

       }
        //holder.tv_type.setText(category.getCategoryName());

        // Log.i("test",categoryList.get(position));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate.OtherCoupon=0;
                //activate.OtherCouponmulti=0;
                if (position==0){
                    if (MainFwActivity.tmp==0 && MainFwActivity.searchLable==false){
                        if (MainFwActivity.savingsShort==true||MainFwActivity.offferShort==true){
                            activate.tv_category_name.setVisibility(View.GONE);
                            activate.img_category_cross_button.setVisibility(View.GONE);
                            activate.filter_offer_label.setVisibility(View.GONE);
                            //filter_label is sort
                            activate.filter_label.setVisibility(View.VISIBLE);
                            MainFwActivity.pdView=false;
                            MainFwActivity.couponTile=false;
                            MainFwActivity.linearLayout.setVisibility(View.GONE);
                        }else {
                            activate.filter_label.setVisibility(View.GONE);
                            activate.filter_offer_label.setVisibility(View.GONE);
                            MainFwActivity.pdView=true;
                            MainFwActivity.couponTile=true;
                            MainFwActivity.linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (MainFwActivity.searchLable==false){
                        activate.tv_category_name.setVisibility(View.GONE);
                        activate.img_category_cross_button.setVisibility(View.GONE);

                        if (MainFwActivity.savingsShort==true||MainFwActivity.offferShort==true){
                            activate.tv_category_name.setVisibility(View.GONE);
                            activate.img_category_cross_button.setVisibility(View.GONE);
                            activate.filter_offer_label.setVisibility(View.VISIBLE);
                            //filter_label is sort
                            activate.filter_label.setVisibility(View.VISIBLE);
                            /*MainFwActivity.pdView=false;
                            MainFwActivity.linearLayout.setVisibility(View.GONE);*/
                        }else {
                            activate.filter_label.setVisibility(View.GONE);
                            activate.filter_offer_label.setVisibility(View.VISIBLE);
                            /*MainFwActivity.pdView=true;
                            MainFwActivity.linearLayout.setVisibility(View.VISIBLE);*/
                        }
                    }
                    else if (MainFwActivity.searchLable==true){
                        activate.tv_category_name.setVisibility(View.GONE);
                        activate.img_category_cross_button.setVisibility(View.GONE);

                        if (MainFwActivity.savingsShort==true||MainFwActivity.offferShort==true){
                            activate.tv_category_name.setVisibility(View.GONE);
                            activate.img_category_cross_button.setVisibility(View.GONE);
                            activate.filter_offer_label.setVisibility(View.VISIBLE);
                            //filter_label is sort
                            activate.filter_label.setVisibility(View.VISIBLE);
                            /*MainFwActivity.pdView=false;
                            MainFwActivity.linearLayout.setVisibility(View.GONE);*/
                        }else {
                            activate.filter_label.setVisibility(View.VISIBLE);
                            activate.filter_offer_label.setVisibility(View.VISIBLE);
                            /*MainFwActivity.pdView=true;
                            MainFwActivity.linearLayout.setVisibility(View.VISIBLE);*/
                        }
                    }
                    //MainFwActivity.pdView=true;
                }
                else {
                    MainFwActivity.pdView=false;
                    MainFwActivity.couponTile=false;
                    //MainFwActivity.pdView=false;
                    MainFwActivity.linearLayout.setVisibility(View.GONE);

                    /*activate.filter_offer_label.setVisibility(View.VISIBLE);*/
                    if (MainFwActivity.savingsShort==true||MainFwActivity.offferShort==true){
                        activate.filter_offer_label.setVisibility(View.VISIBLE);
                        //filter_label is sort
                        activate.filter_label.setVisibility(View.VISIBLE);
                    }else {
                        activate.filter_label.setVisibility(View.GONE);
                        activate.filter_offer_label.setVisibility(View.VISIBLE);
                    }

                    activate.tv_category_name.setVisibility(View.VISIBLE);
                    activate.img_category_cross_button.setVisibility(View.VISIBLE);
                    activate.tv_category_name.setText(category.getCategoryName());
                }

                try {
                    Log.i("test", String.valueOf(category.getCategoryID()));
                    /*if (MainFwActivity.x==3){
                        activate.filter_offer_label.setVisibility(View.GONE);
                        activate.filter_label.setVisibility(View.GONE);

                    }*/
                    MainFwActivity.getDate(category.getCategoryID());
                    MainFwActivity.categoryShort=true;
                }catch (Exception e){

                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}