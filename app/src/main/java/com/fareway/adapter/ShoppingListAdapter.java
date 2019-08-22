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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Shopping> shoppingArrayList;
    public static AppUtilFw appUtil;
    private ShoppingListAdapterListener listener2;
    private ShoppingListAdapterListener addShoppingListener;
    private ShoppingListAdapterListener subShoppingListener;
    private ShoppingListAdapterListener ShoppingDetailListener;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView shopping_flag_dot,shopping_list_add,shopping_list_sub,tv_coupon_description,tv_expire_end,tv_personal_description,tv_qty_shopping,tv_header_title;
        private ImageView imv_go,imv_shopping_item,imv_remove_shopping;
        private LinearLayout rowLayout,liner_header_title,single_item_remove,liner_shopping_list_body,liner_coupon_description,imv_shopping_item_liner,
                liner_personal_description,linear_personal,linear_coupon;
        private View header_line_view;

        public MyViewHolder(View view) {
            super(view);
            shopping_list_sub= (TextView) view.findViewById(R.id.shopping_list_sub);
            shopping_list_add = (TextView) view.findViewById(R.id.shopping_list_add);
            tv_coupon_description = (TextView) view.findViewById(R.id.tv_coupon_description);
            tv_expire_end = (TextView) view.findViewById(R.id.tv_expire_end);

            tv_personal_description = (TextView) view.findViewById(R.id.tv_personal_description);
            tv_qty_shopping = (TextView) view.findViewById(R.id.tv_qty_shopping);


            tv_header_title = (TextView) view.findViewById(R.id.tv_header_title);
            shopping_flag_dot = (TextView) view.findViewById(R.id.shopping_flag_dot);


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
            shopping_list_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addShoppingListener.onShoppingaddSelected(shoppingArrayList.get(getAdapterPosition()));

                }
            });
            shopping_list_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subShoppingListener.onShoppingsubSelected(shoppingArrayList.get(getAdapterPosition()));
                }
            });
            tv_personal_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  ShoppingDetailListener.onShoppingDetailSelected(shoppingArrayList.get(getAdapterPosition()));
                }
            });
            tv_coupon_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   ShoppingDetailListener.onShoppingDetailSelected(shoppingArrayList.get(getAdapterPosition()));
                }
            });
        }
    }


    public ShoppingListAdapter(Context mContext, List<Shopping> shoppingArrayList,ShoppingListAdapterListener listener2,
                               ShoppingListAdapterListener addShoppingListener,ShoppingListAdapterListener subShoppingListener,
                               ShoppingListAdapterListener ShoppingDetailListener) {
        this.mContext = mContext;
        this.shoppingArrayList = shoppingArrayList;
        appUtil=new AppUtilFw(mContext);
        this.listener2 = listener2;
        this.addShoppingListener = addShoppingListener;
        this.subShoppingListener = subShoppingListener;
        this.ShoppingDetailListener = ShoppingDetailListener;


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
        holder.shopping_list_add.setVisibility(View.VISIBLE);
        holder.shopping_list_sub.setVisibility(View.VISIBLE);
        Log.i("primaryy", String.valueOf(shopping.getPrimaryOfferTypeId()));

        if (shopping.getPrimaryOfferTypeId()==3){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);

            String chars = capitalize(shopping.getLongDescription());
            holder.tv_personal_description.setText(chars);

            holder.tv_qty_shopping.setText(shopping.getQuantity());

            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.red));
                    holder.tv_header_title.setText("PERSONAL DEAL");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });

        }

        else if (shopping.getPrimaryOfferTypeId()==2){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            String chars = capitalize(shopping.getLongDescription());
            holder.tv_personal_description.setText(chars);
            holder.tv_qty_shopping.setText(shopping.getQuantity());

            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_green_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);

            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.green));
                    holder.tv_header_title.setText("DIGITAL COUPON");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_green_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });
        }

        else if (shopping.getPrimaryOfferTypeId()==1){
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            String chars = capitalize(shopping.getLongDescription());
            holder.tv_personal_description.setText(chars);
            holder.tv_qty_shopping.setText(shopping.getQuantity());

            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_blue_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.blue));
                    holder.tv_header_title.setText("SALE ITEM");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_blue_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });
        }

        else if (shopping.getPrimaryOfferTypeId()==0){
            holder.shopping_flag_dot.setVisibility(View.GONE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_list_add.setVisibility(View.VISIBLE);
            holder.shopping_list_sub.setVisibility(View.VISIBLE);
            holder.linear_personal.setVisibility(View.VISIBLE);
            holder.linear_coupon.setVisibility(View.GONE);
            holder.tv_personal_description.setText(shopping.getLongDescription());
            holder.tv_qty_shopping.setText(shopping.getQuantity());

            holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.white));
            holder.tv_header_title.setText("");
        }

        if (shopping.getPrimaryOfferTypeId()==3 || shopping.getPrimaryOfferTypeId()==2||shopping.getPrimaryOfferTypeId()==1||shopping.getPrimaryOfferTypeId()==0){
           if (shopping.getImageURL()==null){

            }else if (shopping.getImageURL().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")||shopping.getImageURL().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
               Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_shopping_item);
            }else if (shopping.getImageURL().equalsIgnoreCase("")){
               Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_shopping_item);
            }else {
               Picasso.get().load(shopping.getImageURL()).into(holder.imv_shopping_item);
            }
        }

        if (shopping.getPrimaryOfferTypeid()==2){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);

            String chars = capitalize(shopping.getDescription());
            holder.tv_coupon_description.setText(chars);
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

            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_green_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.green));
                    holder.tv_header_title.setText("DIGITAL COUPON");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_green_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });
        }

        else if (shopping.getPrimaryOfferTypeid()==3){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);
           // holder.tv_coupon_description.setText(shopping.getDescription()+"\n$ "+shopping.getPrice());

            String chars = capitalize(shopping.getDescription());
            holder.tv_coupon_description.setText(chars+"\n$ "+shopping.getPrice());
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
            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.red));
                    holder.tv_header_title.setText("PERSONAL DEAL");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });
        }

        else if (shopping.getPrimaryOfferTypeid()==1){
            holder.linear_personal.setVisibility(View.GONE);
            holder.linear_coupon.setVisibility(View.VISIBLE);
            //holder.tv_coupon_description.setText(shopping.getDescription());

            String chars = capitalize(shopping.getDescription());
            holder.tv_coupon_description.setText(chars);
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
            holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_blue_bg));
            holder.shopping_flag_dot.setVisibility(View.VISIBLE);
            holder.tv_header_title.setVisibility(View.GONE);
            holder.shopping_flag_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_header_title.setBackground(mContext.getResources().getDrawable(R.color.blue));
                    holder.tv_header_title.setText("SALE ITEM");
                    holder.shopping_flag_dot.setVisibility(View.GONE);
                    holder.tv_header_title.setVisibility(View.VISIBLE);
                }
            });
            holder.tv_header_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.shopping_flag_dot.setBackground(mContext.getResources().getDrawable(R.drawable.circular_blue_bg));
                    holder.shopping_flag_dot.setVisibility(View.VISIBLE);
                    holder.tv_header_title.setVisibility(View.GONE);
                }
            });
        }

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
                (5000,
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
        void onShoppingaddSelected(Shopping shopping);
        void onShoppingsubSelected(Shopping shopping);
        void onShoppingDetailSelected(Shopping shopping);
    }

    private String lowercase(String lowerString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(lowerString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toLowerCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

}
