package com.fareway.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Product;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAdapterPersonalPrices extends RecyclerView.Adapter<CustomAdapterPersonalPrices.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Product> productList;
    private List<Product> productListFiltered;
    private CustomAdapterPersonalPricesListener listener;
    private CustomAdapterPersonalPricesListener listener2;
    public MainFwActivity activate = new MainFwActivity();
    public MainFwActivity varites = new MainFwActivity();
    public static AppUtilFw appUtil;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_status,tv_status_mul,tv_remove,tv_remove_mul,tv_price,tv_unit,tv_saving,tv_detail,tv_deal_type,
                tv_coupon_flag,tv_varieties,limit,must,add_plus,add_minus;
        public ImageView imv_item, imv_info,imv_status,imv_status_mul;
        private LinearLayout circular_layout,circular_layout_mul,bottomLayout,liner_save,count_product_number,remove_layout,
                remove_layout_mul;
        private CardView card_view;
        private RelativeLayout imv_layout;
        public MyViewHolder(View view) {

            super(view);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            card_view=(CardView) view.findViewById(R.id.card_view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_status_mul = (TextView) view.findViewById(R.id.tv_status_mul);
            tv_remove = (TextView) view.findViewById(R.id.tv_remove);
            tv_remove_mul = (TextView) view.findViewById(R.id.tv_remove_mul);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
            tv_coupon_flag=(TextView)view.findViewById(R.id.tv_coupon_flag);
          /* tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            add_plus = (TextView) view.findViewById(R.id.add_plus);
            add_minus = (TextView) view.findViewById(R.id.add_minus); */


            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            limit = view.findViewById(R.id.limit);

            //  imv_more = (ImageView) view.findViewById(R.id.imv_more);
            imv_status = (ImageView) view.findViewById(R.id.imv_status);
            imv_status_mul = (ImageView) view.findViewById(R.id.imv_status_mul);
            circular_layout= (LinearLayout) view.findViewById(R.id.circular_layout);
            circular_layout_mul= (LinearLayout) view.findViewById(R.id.circular_layout_mul);
            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);
           // count_product_number = (LinearLayout)view.findViewById(R.id.count_product_number);
            remove_layout = (LinearLayout)view.findViewById(R.id.remove_layout);
            remove_layout_mul = (LinearLayout)view.findViewById(R.id.remove_layout_mul);


            tv_varieties.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onProductVeritiesSelected(productListFiltered.get(getAdapterPosition()));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onProductSelected(productListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public CustomAdapterPersonalPrices(Context mContext, List<Product> ProductList, CustomAdapterPersonalPricesListener listener,
                                       CustomAdapterPersonalPricesListener listener2) {
        this.mContext = mContext;
        this.listener = listener;
        this.listener2 = listener2;
        this.productList = ProductList;
        this.productListFiltered = productList;
        appUtil=new AppUtilFw(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Product product = productListFiltered.get(position);
        if(MainFwActivity.singleView) {
            holder.circular_layout_mul.setVisibility(View.INVISIBLE);
            holder.remove_layout_mul.setVisibility(View.INVISIBLE);
            holder.circular_layout.setVisibility(View.VISIBLE);
            holder.remove_layout.setVisibility(View.VISIBLE);

            /*ViewGroup.LayoutParams active_button_params = holder.circular_layout.getLayoutParams();
            active_button_params.height = (int) mContext.getResources().getDimension(R.dimen.circularlayout_size);
            active_button_params.width = (int) mContext.getResources().getDimension(R.dimen.circularlayout_size);

            ViewGroup.LayoutParams remove = holder.remove_layout.getLayoutParams();
            remove.height = (int) mContext.getResources().getDimension(R.dimen.remove_layout_height_size);
            remove.width = (int) mContext.getResources().getDimension(R.dimen.remove_layout_width_size);*/

            /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.remove_layout.getLayoutParams();
            params.setMargins(0, 0, 10, 0);
            holder.remove_layout.setLayoutParams(params); */

            //holder.tv_varieties.setTextSize((int) mContext.getResources().getDimension(R.dimen.verites_size));
            //holder.tv_coupon_flag.setTextSize((int) mContext.getResources().getDimension(R.dimen.coupon_flag_size));
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
            holder.tv_remove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);




            if (product.getInCircular()==0){
                Log.i("ifincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_coupon_flag.setText("With MyFareway");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" Varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                                // holder.tv_quantity.setText(product.getQuantity());
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout.setVisibility(View.GONE);
                                }else {
                                    holder.tv_status.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout.setVisibility(View.GONE);
                                }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add");
                                // holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText("Activate");
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                            holder.tv_status.setText("Add");
                        }

                    }
                  /*  holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.limit.setText("");
                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }


                    if (product.getClickCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        if (product.getListCount()>0){
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activated));
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }
                        }else {
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activate));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add\nTo Item");
                            }
                        }


                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText(mContext.getString(R.string.activate));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo anshu");
                        }

                    }*/

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection();
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection();
                    }
                    if(product.getLimitPerTransection()>0)
                    {
                        if(headerContent != "")
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = headerContent + "\nLimit : " + product.getLimitPerTransection();
                            }
                        }
                        else
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = "Limit " + String.valueOf(product.getLimitPerTransection());
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            Log.i("anshu", String.valueOf(result));
                            holder.tv_price.setText(result);

                        }else if(product.getRewardType().equalsIgnoreCase("2")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small> *</small></sup>");
                                if (product.getOfferDefinitionId()==3){
                                    holder.tv_price.setText("FREE");
                                }else {
                                    Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                    holder.tv_price.setText(result);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.limit.setText(headerContent);

                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize("*"+product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }


                    try {
                        if (product.getOfferDefinitionId()==4){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_price.setText("Buy "+product.getRequiredQty());

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                            }else {
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            //coupon image hai
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_detail.setText("* "+product.getoCouponShortDescription());
                        }
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText("Participate Item");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getMinAmount()>0){
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                            holder.remove_layout.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status.setText("Activate");
                            holder.remove_layout.setVisibility(View.GONE);
                        }
                    }else {
                        Log.i("elseanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                            holder.remove_layout.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status.setText("Activate");
                            holder.remove_layout.setVisibility(View.GONE);
                        }
                    }
                    /*
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.INVISIBLE);
                    if (product.getLimitPerTransection()>0){
                        holder.limit.setText("LIMIT "+product.getLimitPerTransection());
                        if (product.getRequiredQty()>1){
                            holder.limit.setText("MUST BUY "+product.getRequiredQty()+"\n"+"LIMIT "+product.getLimitPerTransection());
                        }
                    }else if (product.getRequiredQty()>1){
                        holder.limit.setText("MUST BUY "+product.getRequiredQty());
                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activated));
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);

                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status.setText("Add\nTo List");
                                }else {

                                    holder.tv_status.setText(mContext.getString(R.string.activate));
                                    holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout.setVisibility(View.GONE);
                                }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add\nTo List");
                            }
                        }


                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText(mContext.getString(R.string.activate));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }*/

                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.tv_coupon_flag.setText("");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);
                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }


                    if (product.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                        holder.remove_layout.setVisibility(View.VISIBLE);
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");
                        holder.remove_layout.setVisibility(View.GONE);

                    }
                    /*holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                        holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add\nTo List");
                    }*/
                }
            }
            else {
                Log.i("elseincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {

                    holder.tv_coupon_flag.setText("With MyFareway");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText("Added");
                               // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                               // holder.tv_quantity.setText(product.getQuantity());
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                               // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status.setText("Add");
                                   // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout.setVisibility(View.GONE);
                                }else {
                                    holder.tv_status.setText("Add");
                                   // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout.setVisibility(View.GONE);
                            }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add");
                               // holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText("Activate");
                           // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                           // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                            holder.tv_status.setText("Add");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection();
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection();
                    }
                    if(product.getLimitPerTransection()>0)
                    {
                        if(headerContent != "")
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = headerContent + "\nLimit : " + product.getLimitPerTransection();
                            }
                        }
                        else
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = "Limit " + String.valueOf(product.getLimitPerTransection());
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            Log.i("anshu", String.valueOf(result));
                            holder.tv_price.setText(result);

                        }else if(product.getRewardType().equalsIgnoreCase("2")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small> *</small></sup>");
                                if (product.getOfferDefinitionId()==3){
                                    holder.tv_price.setText("FREE");
                                }else {
                                    Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                    holder.tv_price.setText(result);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.limit.setText(headerContent);

                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize("*"+product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }


                    try {
                        if (product.getOfferDefinitionId()==4){
                            /*DecimalFormat dF = new DecimalFormat("00.00");
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(saving));
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip)); */
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());

                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_price.setText("Buy "+product.getRequiredQty());

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                            }else {
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            //coupon image hai
                            DecimalFormat dF = new DecimalFormat("00.00");
                            //Number adPrice = dF.parse(product.getAdPrice());
                            Number savings = dF.parse(product.getSavings());
                           /* holder.tv_saving.setText("SAVE $"+new DecimalFormat("##.##").format(savings));
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                            holder.tv_saving.setText("");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_detail.setText("* "+product.getoCouponShortDescription());
                        }
                       /* if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(regularPrice)+"\nSave $"+new DecimalFormat("##.##").format(saving)+" Off");
                        }else if (product.getRewardType().equalsIgnoreCase("1")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number rewardValue = dF.parse(product.getRewardValue());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(regularPrice)+"\nCoupon $"+new DecimalFormat("##.##").format(rewardValue)+" Off");
                        }else {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(saving));
                        }*/
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>Participate Item</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getMinAmount()>0){
                        Log.i("ifanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                           // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status.setText("Activate");
                          //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }
                    }else {
                        Log.i("elseanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                          //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status.setText("Activate");
                          //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }
                       /* if (product.getClickCount()>0){
                            if (product.getListCount()>0){
                                holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                                if (product.getRequiresActivation().contains("True")){
                                    holder.tv_status.setText(mContext.getString(R.string.activated));
                                    holder.count_product_number.setVisibility(View.VISIBLE);
                                    holder.remove_layout.setVisibility(View.VISIBLE);
                                    holder.tv_quantity.setText(product.getQuantity());

                                }else if(product.getRequiresActivation().contains("False")){
                                    holder.tv_status.setText("Added");
                                    holder.count_product_number.setVisibility(View.VISIBLE);
                                    holder.remove_layout.setVisibility(View.VISIBLE);
                                }
                            }else {
                                holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                                holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);

                                if (product.getRequiresActivation().contains("True")){
                                    if (product.getClickCount()>0){
                                        holder.tv_status.setText("Add\nTo List");
                                    }else {
                                        holder.tv_status.setText(mContext.getString(R.string.activate));
                                    }
                                }else if (product.getRequiresActivation().contains("False")){
                                    holder.tv_status.setText("Add\nTo List");
                                }
                            }
                        }else if (product.getClickCount()==0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activate));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                                holder.tv_quantity.setText("0");
                                holder.tv_status.setText("Add\nTo List");
                            }

                        }*/
                    }

                }else if (product.getPrimaryOfferTypeId() == 1) {

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    holder.tv_coupon_flag.setText("");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }


                    if (product.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                      //  holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);
                      //  holder.tv_quantity.setText(product.getQuantity());
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");
                      //  holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout.setVisibility(View.GONE);

                    }

                }
            }
            if (product.getOfferDefinitionId()==5){
                if (product.getLargeImagePath().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(product.getCouponImageURl())
                            .into(holder.imv_item);
                }
            }else {
                if (product.getLargeImagePath().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(product.getLargeImagePath())
                            .into(holder.imv_item);
                }
            }
         /*   if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("http://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(product.getLargeImagePath())
                        .into(holder.imv_item);
            }*/

        }else {
            holder.circular_layout.setVisibility(View.INVISIBLE);
            holder.remove_layout.setVisibility(View.INVISIBLE);
            holder.circular_layout_mul.setVisibility(View.VISIBLE);
            holder.remove_layout_mul.setVisibility(View.VISIBLE);

            //multi view code

          /*  ViewGroup.LayoutParams active_button_params = holder.circular_layout.getLayoutParams();
            active_button_params.height = (int) mContext.getResources().getDimension(R.dimen.circularlayout_size2);
            active_button_params.width = (int) mContext.getResources().getDimension(R.dimen.circularlayout_size2);

            ViewGroup.LayoutParams remove = holder.remove_layout.getLayoutParams();
            remove.height = (int) mContext.getResources().getDimension(R.dimen.remove_layout_height_size2);
            remove.width = (int) mContext.getResources().getDimension(R.dimen.remove_layout_width_size2);*/

            //holder.tv_varieties.setTextSize((int) mContext.getResources().getDimension(R.dimen.verites_size2));
            //holder.tv_coupon_flag.setTextSize((int) mContext.getResources().getDimension(R.dimen.coupon_flag_size2));
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_status_mul.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_remove_mul.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);


            if (product.getInCircular()==0){
                Log.i("ifincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_coupon_flag.setText("With MyFareway");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status_mul.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                                // holder.tv_quantity.setText(product.getQuantity());
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status_mul.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout_mul.setVisibility(View.GONE);
                                }else {
                                    holder.tv_status_mul.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout_mul.setVisibility(View.GONE);
                                }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Add");
                                // holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout_mul.setVisibility(View.GONE);
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status_mul.setText("Activate");
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                            holder.tv_status_mul.setText("Add");
                        }

                    }
                   /* holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.limit.setText("");
                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }


                    if (product.getClickCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        if (product.getListCount()>0){
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status_mul.setText(mContext.getString(R.string.activated));
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Added");
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }
                        }else {
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status_mul.setText(mContext.getString(R.string.activate));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout_mul.setVisibility(View.GONE);
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Add\nTo Item");
                            }
                        }


                    }else if (product.getClickCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status_mul.setText(mContext.getString(R.string.activate));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status_mul.setText("Add\nTo anshu");
                        }

                    }*/

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection();
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection();
                    }
                    if(product.getLimitPerTransection()>0)
                    {
                        if(headerContent != "")
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = headerContent + "\nLimit : " + product.getLimitPerTransection();
                            }
                        }
                        else
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = "Limit " + String.valueOf(product.getLimitPerTransection());
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            Log.i("anshu", String.valueOf(result));
                            holder.tv_price.setText(result);

                        }else if(product.getRewardType().equalsIgnoreCase("2")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small> *</small></sup>");
                                if (product.getOfferDefinitionId()==3){
                                    holder.tv_price.setText("FREE");
                                }else {
                                    Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                    holder.tv_price.setText(result);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.limit.setText(headerContent);
                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize("*"+product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }


                    try {
                        if (product.getOfferDefinitionId()==4){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_price.setText("Buy "+product.getRequiredQty());
                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                            }else {
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            //coupon image hai
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_detail.setText("* "+product.getoCouponShortDescription());
                        }
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>Participate Item</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getMinAmount()>0){
                        Log.i("ifanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status_mul.setText("Activated");
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status_mul.setText("Activate");
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }
                    }else {
                        Log.i("elseanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status_mul.setText("Activated");
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status_mul.setText("Activate");
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }
                    }
                   /* holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.INVISIBLE);
                    if (product.getLimitPerTransection()>0){
                        holder.limit.setText("LIMIT "+product.getLimitPerTransection());
                        if (product.getRequiredQty()>1){
                            holder.limit.setText("MUST BUY "+product.getRequiredQty()+"\n"+"LIMIT "+product.getLimitPerTransection());
                        }
                    }else if (product.getRequiredQty()>1){
                        holder.limit.setText("MUST BUY "+product.getRequiredQty());
                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status_mul.setText(mContext.getString(R.string.activated));
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Added");
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);

                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status_mul.setText("Add\nTo List");
                                }else {
                                    holder.tv_status_mul.setText(mContext.getString(R.string.activate));
                                    holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout_mul.setVisibility(View.GONE);
                                }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Add\nTo List");
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status_mul.setText(mContext.getString(R.string.activate));
                            holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status_mul.setText("Add\nTo List");
                        }

                    }*/

                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.tv_coupon_flag.setText("");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);
                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    }else if (product.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                        holder.remove_layout_mul.setVisibility(View.GONE);

                    }
                }
                    /* holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            holder.tv_varieties.setText(product.getRelatedItemCount()+" varieties");
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                        holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    }else if (product.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add\nTo List");
                    }
                }*/
            }else {
                Log.i("elseincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {

                    holder.tv_coupon_flag.setText("With MyFareway");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);
                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status_mul.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                                // holder.tv_quantity.setText(product.getQuantity());
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Added");
                                // holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout_mul.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status_mul.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout_mul.setVisibility(View.GONE);
                                }else {
                                    holder.tv_status_mul.setText("Add");
                                    // holder.count_product_number.setVisibility(View.GONE);
                                    holder.remove_layout_mul.setVisibility(View.GONE);
                                }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status_mul.setText("Add");
                                // holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout_mul.setVisibility(View.GONE);
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status_mul.setText("Activate");
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                            holder.tv_status_mul.setText("Add");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection();
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection();
                    }
                    if(product.getLimitPerTransection()>0)
                    {
                        if(headerContent != "")
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = headerContent + "\nLimit : " + product.getLimitPerTransection();
                            }
                        }
                        else
                        {
                            if (product.getLimitPerTransection()>1){
                                headerContent = "Limit " + String.valueOf(product.getLimitPerTransection());
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            Log.i("anshu", String.valueOf(result));
                            holder.tv_price.setText(result);

                        }else if(product.getRewardType().equalsIgnoreCase("2")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small> *</small></sup>");
                                if (product.getOfferDefinitionId()==3){
                                    holder.tv_price.setText("FREE");
                                }else {
                                    Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                    holder.tv_price.setText(result);
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"*");
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.limit.setText(headerContent);

                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize("*"+product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }


                    try {
                        if (product.getOfferDefinitionId()==4){
                            /*DecimalFormat dF = new DecimalFormat("00.00");
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(saving));
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip)); */
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());

                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_price.setText("Buy "+product.getRequiredQty());

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                            }else {
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            //coupon image hai
                            DecimalFormat dF = new DecimalFormat("00.00");
                            //Number adPrice = dF.parse(product.getAdPrice());
                            Number savings = dF.parse(product.getSavings());
                           /* holder.tv_saving.setText("SAVE $"+new DecimalFormat("##.##").format(savings));
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                            holder.tv_saving.setText("");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_detail.setText("* "+product.getoCouponShortDescription());
                        }
                       /* if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(regularPrice)+"\nSave $"+new DecimalFormat("##.##").format(saving)+" Off");
                        }else if (product.getRewardType().equalsIgnoreCase("1")&&product.getPrimaryOfferTypeId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number rewardValue = dF.parse(product.getRewardValue());
                            holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(regularPrice)+"\nCoupon $"+new DecimalFormat("##.##").format(rewardValue)+" Off");
                        }else {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number saving = dF.parse(product.getSavings());
                            holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(saving));
                        }*/
                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>Participate Item</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }
                    if (product.getMinAmount()>0){
                        Log.i("ifanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status_mul.setText("Activated");
                            // holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status_mul.setText("Activate");
                            //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }
                    }else {
                        Log.i("elseanshu", String.valueOf(product.getMinAmount()));
                        if (product.getClickCount()>0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status_mul.setText("Activated");
                            //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else {
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            holder.tv_status_mul.setText("Activate");
                            //  holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }
                       /* if (product.getClickCount()>0){
                            if (product.getListCount()>0){
                                holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                                if (product.getRequiresActivation().contains("True")){
                                    holder.tv_status_mul.setText(mContext.getString(R.string.activated));
                                    holder.count_product_number.setVisibility(View.VISIBLE);
                                    holder.remove_layout.setVisibility(View.VISIBLE);
                                    holder.tv_quantity.setText(product.getQuantity());

                                }else if(product.getRequiresActivation().contains("False")){
                                    holder.tv_status.setText("Added");
                                    holder.count_product_number.setVisibility(View.VISIBLE);
                                    holder.remove_layout.setVisibility(View.VISIBLE);
                                }
                            }else {
                                holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                                holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);

                                if (product.getRequiresActivation().contains("True")){
                                    if (product.getClickCount()>0){
                                        holder.tv_status.setText("Add\nTo List");
                                    }else {
                                        holder.tv_status.setText(mContext.getString(R.string.activate));
                                    }
                                }else if (product.getRequiresActivation().contains("False")){
                                    holder.tv_status.setText("Add\nTo List");
                                }
                            }
                        }else if (product.getClickCount()==0){
                            holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activate));
                                holder.count_product_number.setVisibility(View.GONE);
                                holder.remove_layout.setVisibility(View.GONE);
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.count_product_number.setVisibility(View.VISIBLE);
                                holder.remove_layout.setVisibility(View.VISIBLE);
                                holder.tv_quantity.setText("0");
                                holder.tv_status.setText("Add\nTo List");
                            }

                        }*/
                    }

                }else if (product.getPrimaryOfferTypeId() == 1) {

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));

                    holder.tv_coupon_flag.setText("");
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    // old display price
                    String displayPrice=product.getDisplayPrice().toString();
                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    try {
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                            holder.tv_varieties.setText(varietiesUnderline);
                        }else {
                            holder.tv_varieties.setVisibility(View.GONE);
                        }
                    }else if (product.getHasRelatedItems()==0){
                        holder.tv_varieties.setVisibility(View.INVISIBLE);
                    }


                    if (product.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                        //  holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                        //  holder.tv_quantity.setText(product.getQuantity());
                    }else if (product.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                        //  holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout_mul.setVisibility(View.GONE);

                    }

                }
            }
            if (product.getOfferDefinitionId()==5){
                if (product.getLargeImagePath().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(product.getCouponImageURl())
                            .into(holder.imv_item);
                }
            }else {
                if (product.getLargeImagePath().contains("https://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(product.getLargeImagePath())
                            .into(holder.imv_item);
                }
            }
         /*   if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("http://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(product.getLargeImagePath())
                        .into(holder.imv_item);
            }*/
        }
        ///////////////////////

        holder.remove_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("remove","remove");

                RequestQueue mQueue;
                mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();



                String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                holder.remove_layout.setVisibility(View.GONE);
                                //    holder.count_product_number.setVisibility(View.GONE);
                                //product.setClickCount(0);
                                product.setListCount(0);
                                product.setQuantity("0");
                                holder.tv_status.setText("Add");
                                holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                                holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                                // holder.tv_quantity.setText("1");
                                //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                //holder.tv_quantity.setText(product.getQuantity());
                                //serverResp.setText("String Response : "+ response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        // serverResp.setText("Error getting response");
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
        });

        holder.circular_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getClickCount()>0){
                    Log.i("if click", String.valueOf(product.getClickCount()));
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status.setText("Added");
                        //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);

                    }else if (product.getRequiresActivation().contains("True")){
                        holder.tv_status.setText(mContext.getString(R.string.activated));
                        //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);

                    }else {
                        holder.tv_status.setText("Added");
                        //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);

                    }

                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();
                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            product.setQuantity("1");
                                            if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            }
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                        }else if (product.getPrimaryOfferTypeId()==1){
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
                            }
                        })
                        {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("UPCCode", product.getUPC());
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
                                params.put("SalePrice", product.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                params.put("ExpirationDate", product.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", product.getPackagingSize());
                                params.put("DisplayPrice", product.getDisplayPrice());
                                params.put("PageID", String.valueOf(product.getPageID()));
                                params.put("Description", product.getDescription());
                                params.put("CouponID", String.valueOf(product.getCouponID()));
                                params.put("MemberID", String.valueOf(product.getMemberID()));
                                params.put("DeviceId", "1");
                                params.put("ClickType", "1");
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");

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

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                }else if (product.getClickCount()==0){
                    Log.i("else click", String.valueOf(product.getClickCount()));
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));

                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status.setText("Added");
                        //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);
                    }else if (product.getRequiresActivation().contains("True")){
                        if (product.getPrimaryOfferTypeId()==2){
                            holder.tv_status.setText("Activated");
                            //       holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout.setVisibility(View.GONE);
                        }else {
                            holder.tv_status.setText(mContext.getString(R.string.activated));
                            //       holder.count_product_number.setVisibility(View.VISIBLE);
                            holder.remove_layout.setVisibility(View.VISIBLE);
                        }

                    }else {
                        holder.tv_status.setText("Added");
                        //     holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.VISIBLE);
                    }


                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            //  holder.tv_quantity.setText("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                        }else if (product.getPrimaryOfferTypeId()==1){
                                            //  holder.tv_quantity.setText("1");
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
                            }
                        })
                        {

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("UPCCode", product.getUPC());
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
                                params.put("SalePrice", product.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                params.put("ExpirationDate", product.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", product.getPackagingSize());
                                params.put("DisplayPrice", product.getDisplayPrice());
                                params.put("PageID", String.valueOf(product.getPageID()));
                                params.put("Description", product.getDescription());
                                params.put("CouponID", String.valueOf(product.getCouponID()));
                                params.put("MemberID", String.valueOf(product.getMemberID()));
                                params.put("DeviceId", "1");
                                if (product.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");

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

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                }

            }
        });

        ///////////////////////

        holder.remove_layout_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("remove","remove");

                RequestQueue mQueue;
                mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();



                String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                holder.remove_layout_mul.setVisibility(View.GONE);
                            //    holder.count_product_number.setVisibility(View.GONE);
                                //product.setClickCount(0);
                                product.setListCount(0);
                                product.setQuantity("0");
                                holder.tv_status_mul.setText("Add");
                                holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                                holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                               // holder.tv_quantity.setText("1");
                                //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                //holder.tv_quantity.setText(product.getQuantity());
                                //serverResp.setText("String Response : "+ response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        // serverResp.setText("Error getting response");
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
        });
/*
        holder.add_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                RequestQueue mQueue;
                mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", product.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity())+1));
                    ShoppingListItems.put("DateAddedOn", currentDate);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONArray jsonArray = new JSONArray();

                jsonArray.put(ShoppingListItems);

                JSONObject studentsObj = new JSONObject();
                try {
                    studentsObj.put("ShoppingListItems", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String mRequestBody = "'"+studentsObj.toString()+"'";
                Log.i("test",mRequestBody);
                String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                holder.tv_quantity.setText(product.getQuantity());
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
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                            return null;
                        }
                    }

                  @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
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
     /*   holder.add_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Quantity",product.getQuantity());
                if (Integer.parseInt(product.getQuantity())>1){
                    Log.i("IfQuantity",product.getQuantity());
                    JSONObject json = new JSONObject();
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println(currentDate);
                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    JSONObject ShoppingListItems = new JSONObject();
                    try {
                        ShoppingListItems.put("UPC", product.getUPC());
                        ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity())-1));
                        ShoppingListItems.put("DateAddedOn", currentDate);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    JSONArray jsonArray = new JSONArray();

                    jsonArray.put(ShoppingListItems);

                    JSONObject studentsObj = new JSONObject();
                    try {
                        studentsObj.put("ShoppingListItems", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String mRequestBody = "'"+studentsObj.toString()+"'";
                    Log.i("test",mRequestBody);
                      String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("success", String.valueOf(response));
                                    product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));
                                    //product.setQuantity(String.valueOf(Integer.parseInt(product.getQuantity())));

                                    holder.tv_quantity.setText(product.getQuantity());
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
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }
                       @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
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

            }
        });*/

        holder.circular_layout_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getClickCount()>0){
                    Log.i("if click", String.valueOf(product.getClickCount()));
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status_mul.setText("Added");
                     //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);

                    }else if (product.getRequiresActivation().contains("True")){
                        holder.tv_status_mul.setText(mContext.getString(R.string.activated));
                     //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);

                    }else {
                        holder.tv_status_mul.setText("Added");
                     //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);

                    }

                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();
                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                          activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            product.setQuantity("1");
                                         if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            }
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                       }else if (product.getPrimaryOfferTypeId()==1){
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                       }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
                            }
                        })
                        {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("UPCCode", product.getUPC());
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
                                params.put("SalePrice", product.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                params.put("ExpirationDate", product.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", product.getPackagingSize());
                                params.put("DisplayPrice", product.getDisplayPrice());
                                params.put("PageID", String.valueOf(product.getPageID()));
                                params.put("Description", product.getDescription());
                                params.put("CouponID", String.valueOf(product.getCouponID()));
                                params.put("MemberID", String.valueOf(product.getMemberID()));
                                params.put("DeviceId", "1");
                                params.put("ClickType", "1");
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());
                                return params;
                            }

                          @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");

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

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                }else if (product.getClickCount()==0){
                    Log.i("else click", String.valueOf(product.getClickCount()));
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));

                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status_mul.setText("Added");
                     //   holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    }else if (product.getRequiresActivation().contains("True")){
                        if (product.getPrimaryOfferTypeId()==2){
                            holder.tv_status_mul.setText("Activated");
                     //       holder.count_product_number.setVisibility(View.GONE);
                            holder.remove_layout_mul.setVisibility(View.GONE);
                        }else {
                            holder.tv_status_mul.setText(mContext.getString(R.string.activated));
                     //       holder.count_product_number.setVisibility(View.VISIBLE);
                            holder.remove_layout_mul.setVisibility(View.VISIBLE);
                        }

                    }else {
                        holder.tv_status_mul.setText("Added");
                   //     holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    }


                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                          //  holder.tv_quantity.setText("1");
                                           activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            //product.setQuantity("1");
                                            //holder.tv_quantity.setText("1");
                                           /* if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            } */
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                        }else if (product.getPrimaryOfferTypeId()==1){
                                          //  holder.tv_quantity.setText("1");
                                            product.setListCount(1);
                                            product.setQuantity("1");
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                       }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
                            }
                        })
                        {

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("UPCCode", product.getUPC());
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
                                params.put("SalePrice", product.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                params.put("ExpirationDate", product.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", product.getPackagingSize());
                                params.put("DisplayPrice", product.getDisplayPrice());
                                params.put("PageID", String.valueOf(product.getPageID()));
                                params.put("Description", product.getDescription());
                                params.put("CouponID", String.valueOf(product.getCouponID()));
                                params.put("MemberID", String.valueOf(product.getMemberID()));
                                params.put("DeviceId", "1");
                                if (product.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");

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

                    } catch (Exception e) {

                        e.printStackTrace();


 }
                 /*   Log.i("else click", String.valueOf(product.getClickCount()));
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status.setText("Added");
                        holder.count_product_number.setVisibility(View.VISIBLE);
                    }else if (product.getRequiresActivation().contains("True")){
                        holder.tv_status.setText(mContext.getString(R.string.activated));
                    }else {
                        holder.tv_status.setText("Added");
                        holder.count_product_number.setVisibility(View.VISIBLE);
                    }
                    holder.remove_layout.setVisibility(View.VISIBLE);

                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {

                                        Log.i("Fareway text", response.toString());

                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            }
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                              }else if (product.getPrimaryOfferTypeId()==1){
                                            product.setListCount(1);
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {


                                    }
                                }
                            }
                        })
                        {

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("UPCCode", product.getUPC());
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
                                params.put("SalePrice", product.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                params.put("ExpirationDate", product.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", product.getPackagingSize());
                                params.put("DisplayPrice", product.getDisplayPrice());
                                params.put("PageID", String.valueOf(product.getPageID()));
                                params.put("Description", product.getDescription());
                                params.put("CouponID", String.valueOf(product.getCouponID()));
                                params.put("MemberID", String.valueOf(product.getMemberID()));
                                params.put("DeviceId", "1");
                                if (product.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");

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

                    } catch (Exception e) {

                        e.printStackTrace();

                    }*/
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    public static float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = productList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : productList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                      /*  if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) && row.getPrimaryOfferTypeName().toLowerCase().contains(charString.toLowerCase())&& row.getCategoryID().contains(charSequence)) {
                            filteredList.add(row);
                        } else if (row.getPrimaryOfferTypeName().equals(productListFiltered.get(i).getPrimaryOfferTypeName())&& row.getCategoryID().equals(charSequence)) {
                            Log.i("anshuman","singh");
                            filteredList.add(row);
                        }else*/  if (row.getDescription().toLowerCase().contains(charString.toLowerCase()) ||row.getOfferTypeTagName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }else if (row.getCategoryID().equals(charSequence)){
                            filteredList.add(row);
                        }
                    }

                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CustomAdapterPersonalPricesListener {
        void onProductSelected(Product product);
        void onProductVeritiesSelected(Product product);
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


