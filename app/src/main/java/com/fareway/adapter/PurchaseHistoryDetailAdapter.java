package com.fareway.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fareway.R;
import com.fareway.model.PurchaseModelHistory;
import com.fareway.utility.AppUtilFw;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class PurchaseHistoryDetailAdapter extends RecyclerView.Adapter<PurchaseHistoryDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<PurchaseModelHistory> purchaseArrayList;
    private List<PurchaseModelHistory> purchaseListSelected;
    public static AppUtilFw appUtil;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_description,tv_qty,tv_total_price,tv_unit_price,tv_coupon_flag,tv_sub_save;
        private ImageView imv_purchase_item;

        public MyViewHolder(View view) {
            super(view);
            tv_item_description = (TextView) view.findViewById(R.id.tv_item_description);
            tv_qty = (TextView) view.findViewById(R.id.tv_qty);
            tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
            tv_unit_price = (TextView) view.findViewById(R.id.tv_unit_price);
            tv_coupon_flag = (TextView) view.findViewById(R.id.tv_coupon_flag);
            tv_sub_save = (TextView) view.findViewById(R.id.tv_sub_save);
            imv_purchase_item = (ImageView)view.findViewById(R.id.imv_purchase_item);


        }
    }


    public PurchaseHistoryDetailAdapter(Context mContext, List<PurchaseModelHistory> purchaseArrayList) {
        this.mContext = mContext;
        this.purchaseArrayList = purchaseArrayList;
        this.purchaseListSelected = purchaseArrayList;
        appUtil=new AppUtilFw(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_purchase_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PurchaseHistoryDetailAdapter.MyViewHolder holder, final int position) {
        final PurchaseModelHistory purchase = purchaseArrayList.get(position);
        holder.tv_total_price.setVisibility(View.GONE);
        holder.tv_sub_save.setVisibility(View.GONE);
        //Log.i("purchase", String.valueOf(purchase.getStorelocation()));
            holder.tv_item_description.setText(purchase.getvDescription()+"\nUPC:"+purchase.getvUPCCode());
            holder.tv_qty.setText(purchase.getiQuantity());

        try {
            DecimalFormat dF = new DecimalFormat("00.00");
            Number num = dF.parse(purchase.getSubsavingamount());
            holder.tv_unit_price.setText("$" + new DecimalFormat("##.##").format(num));

        } catch (Exception e) {

        }

        holder.tv_total_price.setText("$"+purchase.getSubtotalamount());

            if (purchase.getPrimaryoffertypeid()==1){
                holder.tv_coupon_flag.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.tv_coupon_flag.setText("SALE ITEM");
            }else if (purchase.getPrimaryoffertypeid()==2){
                holder.tv_coupon_flag.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.tv_coupon_flag.setText("DIGITAL COUPON");
            }else if(purchase.getPrimaryoffertypeid()==3){
                holder.tv_coupon_flag.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                holder.tv_coupon_flag.setText("PERSONAL DEAL");
            }else {
                holder.tv_coupon_flag.setVisibility(View.GONE);
            }
            holder.tv_coupon_flag.setText(purchase.getCouponflag());

            holder.tv_sub_save.setText("Your Savings: $"+purchase.getRemainamount());
       /* holder.tv_total_save.setText(purchase.getRemainamount()); */

    if (purchase.getImage()==""){
        Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_purchase_item);
        }else {
        Picasso.get().load(purchase.getImage()).into(holder.imv_purchase_item);
        }
    if (purchase.getImage().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")||purchase.getImage().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){

        Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_purchase_item);
        }else if (purchase.getImage().equalsIgnoreCase("")){
            Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_purchase_item);
        }else {
            Picasso.get().load(purchase.getImage()).into(holder.imv_purchase_item);
        }


    }
    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }


}
