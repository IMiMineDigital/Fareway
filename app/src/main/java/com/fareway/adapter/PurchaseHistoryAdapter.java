package com.fareway.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.fareway.R;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Purchase;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Purchase> purchaseArrayList;
    private List<Purchase> purchaseListSelected;
    private PurchaseHistoryAdapterListener listener;
    private PurchaseHistoryAdapterListener listener2;
    public static AppUtilFw appUtil;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date,tv_location,tv_total_save;

        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_location = (TextView) view.findViewById(R.id.tv_location);
            //tv_total_spent = (TextView) view.findViewById(R.id.tv_total_spent);
            tv_total_save = (TextView) view.findViewById(R.id.tv_total_save);

            tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onProductSelected(purchaseListSelected.get(getAdapterPosition()));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onProductSelected(purchaseListSelected.get(getAdapterPosition()));
                }
            });

        }
    }


    public PurchaseHistoryAdapter(Context mContext, List<Purchase> purchaseArrayList,PurchaseHistoryAdapterListener listener,
                                  PurchaseHistoryAdapterListener listener2) {
        this.mContext = mContext;
        this.listener = listener;
        this.listener2 = listener2;
        this.purchaseArrayList = purchaseArrayList;
        this.purchaseListSelected = purchaseArrayList;
        appUtil=new AppUtilFw(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_purchase, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PurchaseHistoryAdapter.MyViewHolder holder, final int position) {
        final Purchase purchase = purchaseArrayList.get(position);
        Log.i("purchase", String.valueOf(purchase.getStorelocation()));
        //holder.tv_date.setText(purchase.getPurchasedate());

        SpannableString content = new SpannableString(purchase.getPurchasedate());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tv_date.setText(content);

        holder.tv_location.setText(purchase.getStorelocation());

        try {
            /*DecimalFormat dF = new DecimalFormat("00.00");
            Number num2 = dF.parse(purchase.getRemainamount());
            holder.tv_total_save.setText("$" + new DecimalFormat("##.##").format(num2));*/

            float floatTotalSaving = Float.parseFloat(purchase.getRemainamount()+"f");
            String formattedTotalSaving = String.format("%.02f", floatTotalSaving);
            holder.tv_total_save.setText("$"+formattedTotalSaving);
        } catch (Exception e) {

        }
    }
    @Override
    public int getItemCount() {
        return purchaseArrayList.size();
    }

    public interface PurchaseHistoryAdapterListener {
        void onProductSelected(Purchase purchase);
        void onProductDateSelected(Purchase purchase);

    }



}
