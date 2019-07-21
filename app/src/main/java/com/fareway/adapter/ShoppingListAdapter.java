package com.fareway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Shopping> shoppingArrayList;
    public static AppUtilFw appUtil;
    private ShoppingListAdapterListener listener2;
    public MainFwActivity activate = new MainFwActivity();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_coupon_description,tv_expire_end,tv_personal_description,tv_qty_shopping,tv_amount,tv_header_title;
        private ImageView imv_go,imv_shopping_item,imv_remove_shopping;
        private LinearLayout rowLayout,liner_header_title,single_item_remove,liner_shopping_list_body,liner_coupon_description,imv_shopping_item_liner,
                liner_personal_description,linear_personal,linear_coupon;
        private View header_line_view;

        public MyViewHolder(View view) {
            super(view);
            tv_coupon_description = (TextView) view.findViewById(R.id.tv_coupon_description);
            tv_expire_end = (TextView) view.findViewById(R.id.tv_expire_end);

            tv_personal_description = (TextView) view.findViewById(R.id.tv_personal_description);
            tv_qty_shopping = (TextView) view.findViewById(R.id.tv_qty_shopping);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);

            tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);

            imv_shopping_item = (ImageView) view.findViewById(R.id.imv_shopping_item);

            header_line_view=(View)view.findViewById(R.id.header_line_view);
            liner_header_title= (LinearLayout) view.findViewById(R.id.liner_header_title);

            linear_personal= (LinearLayout) view.findViewById(R.id.linear_personal);
            linear_coupon= (LinearLayout) view.findViewById(R.id.linear_coupon);



            imv_remove_shopping=(ImageView)view.findViewById(R.id.imv_remove_shopping);
            imv_remove_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onShoppingItemSelected(shoppingArrayList.get(getAdapterPosition()));
                    //circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    //imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    //tv_status.setText("Added");
                }
            });
        }
    }


    public ShoppingListAdapter(Context mContext, List<Shopping> shoppingArrayList,ShoppingListAdapterListener listener2) {
        this.mContext = mContext;
        this.shoppingArrayList = shoppingArrayList;
        appUtil=new AppUtilFw(mContext);
        this.listener2 = listener2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShoppingListAdapter.MyViewHolder holder, final int position) {
        final Shopping shopping = shoppingArrayList.get(position);

        if (shopping.getPrimaryOfferTypeId()==3){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            holder.tv_personal_description.setText(shopping.getLongDescription());
            holder.tv_qty_shopping.setText(shopping.getQuantity());
            try {
                DecimalFormat dF = new DecimalFormat("00.00");
                Number num = dF.parse(shopping.getSalesPrice());
                holder.tv_amount.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.red));
            holder.tv_header_title.setText("PERSONAL DEAL");
        }else if (shopping.getPrimaryOfferTypeId()==1){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            holder.tv_personal_description.setText(shopping.getLongDescription());
            holder.tv_qty_shopping.setText(shopping.getQuantity());
            try {
                DecimalFormat dF = new DecimalFormat("00.00");
                Number num = dF.parse(shopping.getSalesPrice());
                holder.tv_amount.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.blue));
            holder.tv_header_title.setText("SALE ITEM");
        }else if (shopping.getPrimaryOfferTypeId()==0){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            holder.tv_personal_description.setText(shopping.getLongDescription());
            holder.tv_qty_shopping.setText(shopping.getQuantity());
            holder.tv_amount.setText("");
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.white));
            holder.tv_header_title.setText("");
        }

        if (shopping.getPrimaryOfferTypeId()==3 ||shopping.getPrimaryOfferTypeId()==1||shopping.getPrimaryOfferTypeId()==0){
            if (shopping.getImageURL()==null){

            }else if (shopping.getImageURL().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")||shopping.getImageURL().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_shopping_item);
            }else if (shopping.getImageURL().equalsIgnoreCase("")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_shopping_item);
            }else {
                Glide.with(mContext)
                        .load(shopping.getImageURL())
                        .into(holder.imv_shopping_item);
            }
        }

        if (shopping.getPrimaryOfferTypeid()==2){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);
            holder.tv_coupon_description.setText(shopping.getDescription());
            String saveDate = shopping.getExpirationDate();
            if (saveDate.length()==0){
                // getTokenkey();
            }else {
                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = null;
                try {
                    newDate = spf.parse(saveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String c = "MM/dd/yyyy";
                spf = new SimpleDateFormat(c);
                saveDate = spf.format(newDate);
                System.out.println(saveDate);
                holder.tv_expire_end.setText(saveDate);


            }
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.green));
            holder.tv_header_title.setText("DIGITAL COUPON");
        }else if (shopping.getPrimaryOfferTypeid()==3){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);
            holder.tv_coupon_description.setText(shopping.getDescription());
            String saveDate = shopping.getExpirationDate();
            if (saveDate.length()==0){
                // getTokenkey();
            }else {
                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = null;
                try {
                    newDate = spf.parse(saveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String c = "MM/dd/yyyy";
                spf = new SimpleDateFormat(c);
                saveDate = spf.format(newDate);
                System.out.println(saveDate);
                holder.tv_expire_end.setText(saveDate);


            }
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.red));
            holder.tv_header_title.setText("PERSONAL DEAL");
        }else if (shopping.getPrimaryOfferTypeid()==1){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);
            holder.tv_coupon_description.setText(shopping.getDescription());
            String saveDate = shopping.getExpirationDate();
            if (saveDate.length()==0){
                // getTokenkey();
            }else {
                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = null;
                try {
                    newDate = spf.parse(saveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String c = "MM/dd/yyyy";
                spf = new SimpleDateFormat(c);
                saveDate = spf.format(newDate);
                System.out.println(saveDate);
                holder.tv_expire_end.setText(saveDate);


            }
            holder.liner_header_title.setBackground(mContext.getResources().getDrawable(R.color.blue));
            holder.tv_header_title.setText("SALE ITEM");
        }


        //holder.tv_type.setText(shopping.getLongDescription());
        //holder.tv_purchase_amount.setText(" $"+shopping.getPurchaseAmount());
        //holder.tv_purchase_qty.setText(String.valueOf(shopping.getPurchaseQty()));

        /*
          if (shopping.getImageURL()==null){
                holder.imv_shopping_item.setVisibility(View.GONE);
                holder.tv_purchase_amount.setVisibility(View.GONE);
                holder.tv_coupon_description.setText(shopping.getDescription());

            }else if (shopping.getImageURL().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")||shopping.getImageURL().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_shopping_item);
            }else if (shopping.getImageURL().equalsIgnoreCase("")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_shopping_item);
            }else {
                Glide.with(mContext)
                        .load(shopping.getImageURL())
                        .into(holder.imv_shopping_item);
            }
         */

     /*    holder.single_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.i("remove", String.valueOf((shopping.toString().length())/2));
               RequestQueue mQueue;
                mQueue= FarewayApplication.getmInstance(mContext).getmRequestQueue();

                String url = Constant.WEB_URL+Constant.SHOPPINGLISTSINGAL+shopping.getShoppingListItemID()+"&MemberId="+appUtil.getPrefrence("MemberId");
                Log.i("url",url);
                StringRequest jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                holder.liner_shopping_list_body.setVisibility(View.GONE);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        params.put("Content-Type", "application/json ;charset=utf-8");
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (50000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return shoppingArrayList.size();
    }

    public void restoreItem(Shopping item, int position) {
        shoppingArrayList.add(position, item);
        notifyItemInserted(position);
    }
/*
    public List<Shopping> getData() {
        return shoppingArrayList;
    }*/

    public void removeItem(int position) {


        final Shopping shopping = shoppingArrayList.get(position);
        RequestQueue mQueue;
        mQueue= FarewayApplication.getmInstance(mContext).getmRequestQueue();
        String url = Constant.WEB_URL+Constant.SHOPPINGLISTSINGAL+shopping.getShoppingListItemID()+"&MemberId="+appUtil.getPrefrence("MemberId");
        Log.i("url",url);
        StringRequest jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
/*
                        for (int i = 0; i < message.length(); i++) {


                            try {
                                if (message.getJSONObject(i).getString("UPC").contains(shopping.getDisplayUPC().replace("UPC: ",""))) {
                                    message.getJSONObject(i).put("ListCount", 0);
                                    message.getJSONObject(i).put("ClickCount", 0);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        fetchProduct();*/


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy
                (50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        try {
            mQueue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        shoppingArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public interface ShoppingListAdapterListener {
        void onShoppingItemSelected(Shopping shopping);
    }

}
