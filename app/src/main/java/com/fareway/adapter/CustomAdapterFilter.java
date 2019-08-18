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
           holder.tv_type.setText(category.getCategoryName());
       }catch (Exception e){

       }
        //holder.tv_type.setText(category.getCategoryName());

        // Log.i("test",categoryList.get(position));
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate.OtherCoupon=0;
                //activate.OtherCouponmulti=0;

                try {
                    MainFwActivity.getDate(category.getCategoryID());
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