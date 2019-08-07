package com.fareway.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
/*
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;*/
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

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

import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Product;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;
import com.squareup.picasso.Picasso;

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
    private CustomAdapterPersonalPricesListener activateListener;
    private CustomAdapterPersonalPricesListener activateMultiListener;
    private CustomAdapterPersonalPricesListener removeListener;
    private CustomAdapterPersonalPricesListener removeMultiListener;
    public MainFwActivity activate = new MainFwActivity();
    public MainFwActivity varites = new MainFwActivity();
    public static AppUtilFw appUtil;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tv_status,tv_remove,tv_price,tv_unit,tv_saving,tv_saving_pri_fix,tv_promo_price__pri_fix,tv_promo_price,tv_detail,tv_deal_type,
                tv_coupon_flag,tv_varieties,limit,must,add_plus,add_minus,tv_quantity,additional_offers,add_item_flag;
        public ImageView imv_item, imv_info,imv_status,coupon_badge;
        private LinearLayout circular_layout,bottomLayout,liner_save,count_product_number,
                item_layout_tile;
        private CardView card_view;
        private RelativeLayout imv_layout,relative_badge;
        public MyViewHolder(View view) {

            super(view);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            relative_badge = (RelativeLayout) view.findViewById(R.id.relative_badge);

            card_view=(CardView) view.findViewById(R.id.card_view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            //tv_status_mul = (TextView) view.findViewById(R.id.tv_status_mul);
            tv_remove = (TextView) view.findViewById(R.id.tv_remove);
            //tv_remove_mul = (TextView) view.findViewById(R.id.tv_remove_mul);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_saving_pri_fix = (TextView) view.findViewById(R.id.tv_saving_pri_fix);
            tv_promo_price__pri_fix = (TextView) view.findViewById(R.id.tv_promo_price__pri_fix);
            tv_promo_price = (TextView) view.findViewById(R.id.tv_promo_price);

            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
            tv_coupon_flag=(TextView)view.findViewById(R.id.tv_coupon_flag);
            additional_offers=(TextView)view.findViewById(R.id.additional_offers);

            tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            add_item_flag = (TextView) view.findViewById(R.id.add_item_flag);

            add_plus = (TextView) view.findViewById(R.id.add_plus);
            add_minus = (TextView) view.findViewById(R.id.add_minus);


            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            coupon_badge = (ImageView) view.findViewById(R.id.coupon_badge);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            item_layout_tile = (LinearLayout) view.findViewById(R.id.item_layout_tile);

            limit = view.findViewById(R.id.limit);

            imv_status = (ImageView) view.findViewById(R.id.imv_status);

            circular_layout= (LinearLayout) view.findViewById(R.id.circular_layout);

            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);
            relative_badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onProductVeritiesSelected(productListFiltered.get(getAdapterPosition()));
                }
            });

            circular_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            imv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductSelected(productListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    public CustomAdapterPersonalPrices(Context mContext, List<Product> ProductList, CustomAdapterPersonalPricesListener listener,
                                       CustomAdapterPersonalPricesListener listener2, CustomAdapterPersonalPricesListener activateListener,
                                       CustomAdapterPersonalPricesListener activateMultiListener,  CustomAdapterPersonalPricesListener removeListener,
                                       CustomAdapterPersonalPricesListener removeMultiListener) {
        this.mContext = mContext;
        this.listener = listener;
        this.listener2 = listener2;
        this.activateListener = activateListener;
        this.activateMultiListener = activateMultiListener;
        this.removeListener = removeListener;
        this.removeMultiListener = removeMultiListener;

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
            holder.item_layout_tile.setVisibility(View.VISIBLE);

            holder.circular_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product.getPrimaryOfferTypeId()==3){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                        activateListener.onProductActivate(productListFiltered.get(position));
                    }else if (product.getPrimaryOfferTypeId()==2){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                        activateListener.onProductActivate(productListFiltered.get(position));
                    }else   if (product.getPrimaryOfferTypeId()==1){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");

                        activateListener.onProductActivate(productListFiltered.get(position));
                    }

                }
            });

            holder.circular_layout.setVisibility(View.VISIBLE);

            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            //holder.imv_status

            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);

            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            holder.circular_layout.getLayoutParams().height = 300;
            holder.circular_layout.getLayoutParams().width = 300;

            holder.relative_badge.getLayoutParams().width = 160;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            if (product.getInCircular()==0) {
                if (product.getTileNumber().equalsIgnoreCase("999")) {
                    Log.i("offercoupn", String.valueOf(activate.OtherCoupon)+"hhjj");
                    if (activate.OtherCoupon == 0) {
                        holder.additional_offers.setVisibility(View.VISIBLE);
                        holder.additional_offers.setText("Additional Offers");
                        activate.OtherCoupon=product.getCouponID();
                    }
                    else if (activate.OtherCoupon==product.getCouponID())
                    {
                        holder.additional_offers.setVisibility(View.VISIBLE);
                        holder.additional_offers.setText("Additional Offers");
                    }
                    else
                    {
                        holder.additional_offers.setVisibility(View.GONE);
                    }
                }else {
                    holder.additional_offers.setVisibility(View.GONE);
                }
                Log.i("elseincircular", String.valueOf(product.getInCircular()));

                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length()==0){
                        // getTokenkey();
                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    String headerContent = "";
                    headerContent = "Exp "+saveDate;
                    holder.limit.setText("\n"+headerContent);
                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
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
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                           /* Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");
                    }
                    /*if (product.getClickCount()>0){
                        if (product.getListCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText("Added");
                                holder.remove_layout.setVisibility(View.VISIBLE);

                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                                holder.remove_layout.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                            if (product.getRequiresActivation().contains("True")){
                                if (product.getClickCount()>0){
                                    holder.tv_status.setText("Add");
                                    holder.remove_layout.setVisibility(View.GONE);
                                }else {
                                    holder.tv_status.setText("Add");
                                    holder.remove_layout.setVisibility(View.GONE);
                            }
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add");
                                holder.remove_layout.setVisibility(View.GONE);
                            }
                        }
                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText("Activate");
                            holder.remove_layout.setVisibility(View.GONE);
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.remove_layout.setVisibility(View.GONE);
                            holder.tv_status.setText("Add");
                        }

                    }*/

                }

                else if (product.getPrimaryOfferTypeId() == 2) {
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length()==0){
                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    }else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }

                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                    holder.limit.setText(headerContent);
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
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
                                headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
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
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    holder.limit.setText(headerContent);

                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+", Exp "+saveDate);
                        holder.limit.setText(headerContent);
                        holder.limit.setGravity(Gravity.CENTER);
                    }else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }

                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize(product.getDescription());
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
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum= Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                            }else if(product.getRewardType().equalsIgnoreCase("3") && floatnum>0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                holder.liner_save.setVisibility(View.GONE);

                            }else {
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(coupon_discount)+" %OFF*");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_saving.setTypeface(typeface);
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")){


                                // holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                holder.tv_saving.setText("$"+everyDayPrice);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                holder.tv_promo_price__pri_fix.setText("Promo Price     ");
                                //holder.tv_promo_price.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                            }else {
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");
                                holder.tv_promo_price__pri_fix.setText("");
                                holder.tv_promo_price.setText("");

                            }

                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("");
                            holder.tv_saving_pri_fix.setText("");
                            holder.tv_promo_price__pri_fix.setText("");
                            holder.tv_promo_price.setText("");
                            holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }


                   /* try {
                        if (product.getOfferDefinitionId()==4){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum= Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                            }else if(product.getRewardType().equalsIgnoreCase("3") && floatnum>0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                holder.liner_save.setVisibility(View.GONE);

                            }else {
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(coupon_discount)+" %OFF*");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_saving.setTypeface(typeface);
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                            if (product.getIsbadged().equalsIgnoreCase("True")){
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto);
                                holder.tv_saving.setTypeface(typeface);
                                holder.tv_saving.setText("Sale Price  $" + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            }else {
                                holder.tv_saving.setText("");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            }

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
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            holder.tv_detail.setText("* "+product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }*/
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                   /* if (product.getMinAmount()>0){
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

                    }*/
                    if (product.getClickCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");
                    }

                }

                else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length()==0){

                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    String headerContent = "";
                    headerContent = "\nExp "+saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
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
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        /*Spanned varietiesUnderline = Html.fromHtml("Every Day Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                        holder.tv_saving.setText(varietiesUnderline);*/
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));


                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }


                   /* if (product.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                        holder.remove_layout.setVisibility(View.VISIBLE);

                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");
                        holder.remove_layout.setVisibility(View.GONE);

                    }*/

                }

                else if (product.getPrimaryOfferTypeId()==420){
                    holder.item_layout_tile.setVisibility(View.GONE);
                    // Gets linearlayout
                    //LinearLayout layout = findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
                    ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
// Changes the height and width to the specified *pixels*
                    params.height = 30;
                    // params.width = 100;
                    holder.item_layout_tile.setLayoutParams(params);
                }
            }

            else {
                if (product.getTileNumber().equalsIgnoreCase("999")) {
                    Log.i("offercoupn", String.valueOf(activate.OtherCoupon)+"hhjj");
                    if (activate.OtherCoupon == 0) {
                        holder.additional_offers.setVisibility(View.VISIBLE);
                        holder.additional_offers.setText("Additional Offers");
                        activate.OtherCoupon=product.getCouponID();
                    }
                    else if (activate.OtherCoupon==product.getCouponID())
                    {
                        holder.additional_offers.setVisibility(View.VISIBLE);
                        holder.additional_offers.setText("Additional Offers");
                    }
                    else
                    {
                        holder.additional_offers.setVisibility(View.GONE);
                    }
                }else {
                    holder.additional_offers.setVisibility(View.GONE);
                }
                Log.i("elseincircular", String.valueOf(product.getInCircular()));

                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length()==0){
                        // getTokenkey();
                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    String headerContent = "";
                    headerContent = "Exp "+saveDate;
                    holder.limit.setText("\n"+headerContent);
                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
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
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                           /* Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                           holder.tv_saving_pri_fix.setText("Everyday Price ");
                           //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                           holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }
                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");
                    }

                }

                else if (product.getPrimaryOfferTypeId() == 2) {
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length()==0){
                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    }else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }
                    holder.tv_coupon_flag.setText("With Coupon");
                    holder.liner_save.setVisibility(View.GONE);
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String headerContent = "";
                    headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                    holder.limit.setText(headerContent);
                    if (product.getMinAmount()>0){
                        headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
                    }else if (product.getRequiredQty()>1){
                        headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
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
                                headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                            }
                        }
                        if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                        {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
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
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                            holder.tv_price.setText(result);
                        }
                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    holder.limit.setText(headerContent);

                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+", Exp "+saveDate);
                        holder.limit.setText(headerContent);
                        holder.limit.setGravity(Gravity.CENTER);
                    }else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }

                    if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                        String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000")){
                            holder.liner_save.setVisibility(View.GONE);
                        }else {
                            holder.liner_save.setVisibility(View.VISIBLE);
                        }
                    }else {
                        String chars = capitalize(product.getDescription());
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
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum= Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")){
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                            }else if(product.getRewardType().equalsIgnoreCase("3") && floatnum>0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                holder.liner_save.setVisibility(View.GONE);

                            }else {
                                holder.tv_price.setText("Buy "+product.getRequiredQty());
                                holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(coupon_discount)+" %OFF*");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_saving.setTypeface(typeface);
                            }


                        }else if (product.getOfferDefinitionId()==1){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")){

                                // holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                holder.tv_saving.setText("$"+everyDayPrice);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                                //holder.tv_promo_price.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                            }else {
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");
                                holder.tv_promo_price__pri_fix.setText("");
                                holder.tv_promo_price.setText("");

                            }

                        }else if (product.getOfferDefinitionId()==3){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        }else if (product.getOfferDefinitionId()==2){
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        }else if (product.getOfferDefinitionId()==5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("");
                            holder.tv_saving_pri_fix.setText("");
                            holder.tv_promo_price__pri_fix.setText("");
                            holder.tv_promo_price.setText("");
                            holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                    if (product.getClickCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");

                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");
                    }

                }

                else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length()==0){

                    }else {
                        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "MM/dd";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println(saveDate);


                    }
                    String headerContent = "";
                    headerContent = "\nExp "+saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit =lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
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
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        /*Spanned varietiesUnderline = Html.fromHtml("Every Day Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                        holder.tv_saving.setText(varietiesUnderline);*/
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));


                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems()==1){
                        if (product.getRelatedItemCount()>1){
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }else if (product.getHasRelatedItems()==0){
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }



                }

                else if (product.getPrimaryOfferTypeId()==420){
                    holder.item_layout_tile.setVisibility(View.GONE);
                    // Gets linearlayout
                    //LinearLayout layout = findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
                    ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
// Changes the height and width to the specified *pixels*
                    params.height = 30;
                    // params.width = 100;
                    holder.item_layout_tile.setLayoutParams(params);
                }
            }

            if (product.getOfferDefinitionId()==5){
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(product.getCouponImageURl()).into(holder.imv_item);
                }
            }else {
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(product.getLargeImagePath()).into(holder.imv_item);
                }
            }


        }

        else {

                holder.item_layout_tile.setVisibility(View.VISIBLE);
                holder.circular_layout.setVisibility(View.VISIBLE);

                holder.circular_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (product.getPrimaryOfferTypeId()==3){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                            activateListener.onProductActivate(productListFiltered.get(position));
                        }else if (product.getPrimaryOfferTypeId()==2){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                            activateListener.onProductActivate(productListFiltered.get(position));
                        }else   if (product.getPrimaryOfferTypeId()==1){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");
                            activateListener.onProductActivate(productListFiltered.get(position));
                        }else {

                        }

                    }
                });



                //holder.imv_status


            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.circular_layout.getLayoutParams().height = 200;
            holder.circular_layout.getLayoutParams().width = 200;

            holder.relative_badge.getLayoutParams().width = 110;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);


                if (product.getInCircular()==0){
                    if (product.getTileNumber().equalsIgnoreCase("999")) {
                        if (activate.OtherCoupon == 0) {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                            activate.OtherCoupon=product.getCouponID();
                        }
                        else if (activate.OtherCoupon==product.getCouponID())
                        {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                        }
                        else
                        {
                            if (activate.OtherCouponmulti == 0) {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                                activate.OtherCouponmulti=product.getCouponID();
                            }
                            else if (activate.OtherCouponmulti==product.getCouponID())
                            {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                            }
                            else
                                holder.additional_offers.setVisibility(View.GONE);
                        }
                    }else {
                        holder.additional_offers.setVisibility(View.GONE);
                    }
                    Log.i("elseincircular", String.valueOf(product.getInCircular()));

                    if (product.getPrimaryOfferTypeId() == 3) {
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");
                        holder.limit.setGravity(Gravity.CENTER);
                        String saveDate = product.getValidityEndDate();
                        if (saveDate.length()==0){
                            // getTokenkey();
                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        String headerContent = "";
                        headerContent = "Exp "+saveDate;
                        holder.limit.setText("\n"+headerContent);
                        holder.coupon_badge.setVisibility(View.GONE);
                        holder.tv_coupon_flag.setText("With MyFareway");
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
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
                        //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                        //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                        //holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                        try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                           /* Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                        } catch (Exception e) {

                        }
                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");

                        }else if (product.getClickCount()==0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");
                        }

                    }

                    else if (product.getPrimaryOfferTypeId() == 2) {

                        String saveDate = product.getValidityEndDate();
                        if (saveDate.length()==0){
                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        holder.coupon_badge.setVisibility(View.VISIBLE);
                        if (product.getIsbadged().equalsIgnoreCase("True")){
                            Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        }else {
                            Picasso.get().load(R.color.white).into(holder.coupon_badge);
                        }
                        holder.tv_coupon_flag.setText("With Coupon");
                        holder.liner_save.setVisibility(View.GONE);
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
                        holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        holder.tv_deal_type.setText(product.getOfferTypeTagName());
                        String headerContent = "";
                        headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                        holder.limit.setText(headerContent);
                        if (product.getMinAmount()>0){
                            headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
                        }else if (product.getRequiredQty()>1){
                            headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
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
                                    headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                                }
                            }
                            if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                            {
                                String displayPrice=product.getDisplayPrice().toString();
                                if(product.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
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
                                    holder.tv_saving_pri_fix.setText("");
                                    holder.tv_saving.setText("");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                String displayPrice=product.getDisplayPrice().toString();
                                if(product.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                holder.tv_price.setText(result);
                            }
                        }
                        holder.coupon_badge.setVisibility(View.VISIBLE);
                        holder.limit.setGravity(Gravity.CENTER);
                        holder.limit.setText(headerContent);

                        if (product.getIsbadged().equalsIgnoreCase("True")){
                            Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                            headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+", Exp "+saveDate);
                            holder.limit.setText(headerContent);
                            holder.limit.setGravity(Gravity.RIGHT);
                        }else {
                            Picasso.get().load(R.color.white).into(holder.coupon_badge);
                        }

                        if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                            String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);
                            if (product.getSavings().equalsIgnoreCase("0.0000")){
                                holder.liner_save.setVisibility(View.GONE);
                            }else {
                                holder.liner_save.setVisibility(View.VISIBLE);
                            }
                        }else {
                            String chars = capitalize(product.getDescription());
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
                                DecimalFormat dF2 = new DecimalFormat("00.00");
                                Number coupon_discount = dF.parse(product.getCouponDiscount());
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                                DecimalFormat Dfinal = new DecimalFormat("00.00");
                                Number finalprice = Dfinal.parse(product.getFinalPrice());
                                Float floatnum= Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                                if (product.getRewardType().equalsIgnoreCase("1")){
                                    holder.tv_price.setText("Buy "+product.getRequiredQty());
                                    holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                                }else if(product.getRewardType().equalsIgnoreCase("3") && floatnum>0) {
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                    holder.liner_save.setVisibility(View.GONE);

                                }else {
                                    holder.tv_price.setText("Buy "+product.getRequiredQty());
                                    holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(coupon_discount)+" %OFF*");
                                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                    holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                    holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                    holder.tv_saving.setTypeface(typeface);
                                }


                            }else if (product.getOfferDefinitionId()==1){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number adPrice = dF.parse(product.getAdPrice());
                                Number everyDayPrice = dF.parse(product.getRegularPrice());
                                if (product.getIsbadged().equalsIgnoreCase("True")){
                                    holder.tv_saving.setText("$"+everyDayPrice);
                                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                                    holder.tv_promo_price__pri_fix.setText("Promo Price     ");
                                    //holder.tv_promo_price.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                    holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                                }else {
                                    holder.tv_saving_pri_fix.setText("");
                                    holder.tv_saving.setText("");
                                    holder.tv_promo_price__pri_fix.setText("");
                                    holder.tv_promo_price.setText("");

                                }

                            }else if (product.getOfferDefinitionId()==3){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number adPrice = dF.parse(product.getAdPrice());
                                Number couponDiscount = dF.parse(product.getCouponDiscount());
                               /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                            }else if (product.getOfferDefinitionId()==2){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number regularPrice = dF.parse(product.getRegularPrice());
                                Number savings = dF.parse(product.getSavings());
                                holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            }else if (product.getOfferDefinitionId()==5) {
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number savings = dF.parse(product.getSavings());
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");
                                holder.tv_promo_price__pri_fix.setText("");
                                holder.tv_promo_price.setText("");
                                holder.tv_detail.setText(product.getoCouponShortDescription());
                            }

                        } catch (Exception e) {

                        }

                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }

                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");

                        }else if (product.getClickCount()==0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");
                        }

                    }

                    else if (product.getPrimaryOfferTypeId() == 1) {
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");
                        holder.circular_layout.setVisibility(View.GONE);
                        String saveDate = product.getValidityEndDate();
                        holder.limit.setGravity(Gravity.CENTER);
                        if (saveDate.length()==0){

                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        String headerContent = "";
                        headerContent = "\nExp "+saveDate;
                        holder.limit.setText(headerContent);

                        holder.coupon_badge.setVisibility(View.GONE);
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                        holder.tv_coupon_flag.setText("");
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
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
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            /*Spanned varietiesUnderline = Html.fromHtml("Every Day Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));


                        } catch (Exception e) {

                        }

                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }

                    }

                    else if (product.getPrimaryOfferTypeId()==420){
                        holder.item_layout_tile.setVisibility(View.GONE);
                        // Gets linearlayout
                        //LinearLayout layout = findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
                        ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
// Changes the height and width to the specified *pixels*
                        params.height = 30;
                        // params.width = 100;
                        holder.item_layout_tile.setLayoutParams(params);
                    }
                }

                else {
                    if (product.getTileNumber().equalsIgnoreCase("999")) {
                        if (activate.OtherCoupon == 0) {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                            activate.OtherCoupon=product.getCouponID();
                        }
                        else if (activate.OtherCoupon==product.getCouponID())
                        {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                        }
                        else
                        {
                            if (activate.OtherCouponmulti == 0) {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                                activate.OtherCouponmulti=product.getCouponID();
                            }
                            else if (activate.OtherCouponmulti==product.getCouponID())
                            {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                            }
                            else
                                holder.additional_offers.setVisibility(View.GONE);
                        }
                    }else {
                        holder.additional_offers.setVisibility(View.GONE);
                    }
                    Log.i("elseincircular", String.valueOf(product.getInCircular()));

                    if (product.getPrimaryOfferTypeId() == 3) {
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");
                        holder.limit.setGravity(Gravity.CENTER);
                        String saveDate = product.getValidityEndDate();
                        if (saveDate.length()==0){
                            // getTokenkey();
                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        String headerContent = "";
                        headerContent = "Exp "+saveDate;
                        holder.limit.setText("\n"+headerContent);
                        holder.coupon_badge.setVisibility(View.GONE);
                        holder.tv_coupon_flag.setText("With MyFareway");
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
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
                        //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                        //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                        //holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                        try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                           /* Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                           holder.tv_saving_pri_fix.setText("Everyday Price ");
                           //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                           holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                        } catch (Exception e) {

                        }
                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");

                        }else if (product.getClickCount()==0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");
                        }


                    }

                    else if (product.getPrimaryOfferTypeId() == 2) {
                        String saveDate = product.getValidityEndDate();
                        if (saveDate.length()==0){
                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        holder.coupon_badge.setVisibility(View.VISIBLE);
                        if (product.getIsbadged().equalsIgnoreCase("True")){
                            Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);

                        }else {
                            Picasso.get().load(R.color.white).into(holder.coupon_badge);
                        }
                        holder.tv_coupon_flag.setText("With Coupon");
                        holder.liner_save.setVisibility(View.GONE);
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
                        holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        holder.tv_deal_type.setText(product.getOfferTypeTagName());
                        String headerContent = "";
                        headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                        holder.limit.setText(headerContent);
                        if (product.getMinAmount()>0){
                            headerContent = "* WITH $"+product.getMinAmount()+" PURCHASE"+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
                        }else if (product.getRequiredQty()>1){
                            headerContent = "* MUST BUY " + product.getRequiredQty()+"\nLimit "+product.getLimitPerTransection()+", Exp "+saveDate;
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
                                    headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+product.getLimitPerTransection()+", Exp "+saveDate);
                                }
                            }
                            if (product.getRewardType().equalsIgnoreCase("3")&&product.getPrimaryOfferTypeId()==2)
                            {
                                String displayPrice=product.getDisplayPrice().toString();
                                if(product.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                Log.i("anshu", String.valueOf(result));
                                holder.tv_price.setText(result);

                            }else if(product.getRewardType().equalsIgnoreCase("2")&&product.getPrimaryOfferTypeId()==2){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                try {
                                    String displayPrice=product.getDisplayPrice().toString();
                                    if(product.getDisplayPrice().toString().split("\\.").length>1)
                                        displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

                                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                    Log.i("anshu", String.valueOf(result));
                                    if (product.getOfferDefinitionId()==3){
                                        holder.tv_price.setText("FREE");
                                        holder.tv_saving_pri_fix.setText("");
                                        holder.tv_saving.setText("");
                                        holder.tv_promo_price__pri_fix.setText("");
                                        holder.tv_promo_price.setText("");

                                    }else {
                                        Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        //
                                        DecimalFormat dF2 = new DecimalFormat("00.00");
                                        Number adPrice = dF2.parse(product.getAdPrice());
                                        Number everyDayPrice = dF2.parse(product.getRegularPrice());

                                        holder.tv_saving.setText("$"+everyDayPrice);
                                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                                        holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                                        holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));

                                        holder.tv_price.setText(result);
                                    }
                                    /*holder.tv_saving_pri_fix.setText("");
                                    holder.tv_saving.setText("");
                                    holder.tv_promo_price__pri_fix.setText("");
                                    holder.tv_promo_price.setText("");*/

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                String displayPrice=product.getDisplayPrice().toString();
                                if(product.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                holder.tv_price.setText(result);
                            }
                        }
                        holder.coupon_badge.setVisibility(View.VISIBLE);
                        holder.limit.setGravity(Gravity.CENTER);
                        holder.limit.setText(headerContent);
                        if (product.getIsbadged().equalsIgnoreCase("True")){
                            Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                            headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()+", Exp "+saveDate);
                            holder.limit.setText(headerContent);
                            holder.limit.setGravity(Gravity.RIGHT);
                        }else {
                            Picasso.get().load(R.color.white).into(holder.coupon_badge);
                        }

                        if (product.getOfferDefinitionId()==3 || product.getOfferDefinitionId()==2 || product.getOfferDefinitionId()==1 || product.getOfferDefinitionId()==4){
                            String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);
                            if (product.getSavings().equalsIgnoreCase("0.0000")){
                                holder.liner_save.setVisibility(View.GONE);
                            }else {
                                holder.liner_save.setVisibility(View.VISIBLE);
                            }
                        }else {
                            String chars = capitalize(product.getDescription());
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
                                DecimalFormat dF2 = new DecimalFormat("00.00");
                                Number coupon_discount = dF.parse(product.getCouponDiscount());
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                                DecimalFormat Dfinal = new DecimalFormat("00.00");
                                Number finalprice = Dfinal.parse(product.getFinalPrice());
                                Float floatnum= Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                                if (product.getRewardType().equalsIgnoreCase("1")){
                                    holder.tv_price.setText("Buy "+product.getRequiredQty());
                                    holder.tv_saving.setText("Get "+product.getRewardQty()+" $"+new DecimalFormat("##.##").format(reward_value)+" Off *");
                                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                                }else if(product.getRewardType().equalsIgnoreCase("3") && floatnum>0) {
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                    holder.liner_save.setVisibility(View.GONE);

                                }else {
                                    holder.tv_price.setText("Buy "+product.getRequiredQty());
                                    holder.tv_saving.setText("Get "+product.getRewardQty()+" "+new DecimalFormat("##.##").format(coupon_discount)+" %OFF*");
                                    holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.red));
                                    holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                    holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                    holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.BOLD);
                                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                    holder.tv_saving.setTypeface(typeface);
                                }


                            }else if (product.getOfferDefinitionId()==1){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number adPrice = dF.parse(product.getAdPrice());
                                Number everyDayPrice = dF.parse(product.getRegularPrice());
                                if (product.getIsbadged().equalsIgnoreCase("True")){
                                    holder.tv_saving.setText("$"+everyDayPrice);
                                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                                    holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                                    holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                                }else {
                                    holder.tv_saving_pri_fix.setText("");
                                    holder.tv_saving.setText("");
                                    holder.tv_promo_price__pri_fix.setText("");
                                    holder.tv_promo_price.setText("");

                                }

                            }else if (product.getOfferDefinitionId()==3){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number adPrice = dF.parse(product.getAdPrice());
                                Number couponDiscount = dF.parse(product.getCouponDiscount());
                               /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                            }else if (product.getOfferDefinitionId()==2){
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number regularPrice = dF.parse(product.getRegularPrice());
                                Number savings = dF.parse(product.getSavings());
                                holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                                holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                                holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                            }else if (product.getOfferDefinitionId()==5) {
                                DecimalFormat dF = new DecimalFormat("00.00");
                                Number savings = dF.parse(product.getSavings());
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");
                                holder.tv_promo_price__pri_fix.setText("");
                                holder.tv_promo_price.setText("");
                                holder.tv_detail.setText(product.getoCouponShortDescription());
                            }

                        } catch (Exception e) {

                        }

                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }

                        if (product.getClickCount()>0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");

                        }else if (product.getClickCount()==0){
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");
                        }

                    }

                    else if (product.getPrimaryOfferTypeId() == 1) {
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");
                        holder.circular_layout.setVisibility(View.GONE);
                        String saveDate = product.getValidityEndDate();
                        holder.limit.setGravity(Gravity.CENTER);
                        if (saveDate.length()==0){

                        }else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            System.out.println(saveDate);


                        }
                        String headerContent = "";
                        headerContent = "\nExp "+saveDate;
                        holder.limit.setText(headerContent);

                        holder.coupon_badge.setVisibility(View.GONE);
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                        holder.tv_coupon_flag.setText("");
                        String charsUnit =lowercase(product.getPackagingSize());
                        holder.tv_unit.setText(charsUnit);
                        //holder.tv_unit.setText(product.getPackagingSize());
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
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            /*Spanned varietiesUnderline = Html.fromHtml("Every Day Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(num)+"</strike>");
                            holder.tv_saving.setText(varietiesUnderline);*/
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));


                        } catch (Exception e) {

                        }

                        if (product.getHasRelatedItems()==1){
                            if (product.getRelatedItemCount()>1){
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }else {
                                //holder.tv_varieties.setVisibility(View.GONE);
                                holder.tv_varieties.setVisibility(View.VISIBLE);
                                //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                                holder.tv_varieties.setText("");
                            }
                        }else if (product.getHasRelatedItems()==0){
                            //holder.tv_varieties.setVisibility(View.INVISIBLE);
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }


                    }

                    else if (product.getPrimaryOfferTypeId()==420){
                        holder.item_layout_tile.setVisibility(View.GONE);
                        // Gets linearlayout
                        //LinearLayout layout = findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
                        ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
// Changes the height and width to the specified *pixels*
                        params.height = 30;
                        // params.width = 100;
                        holder.item_layout_tile.setLayoutParams(params);
                    }
                }

            if (product.getOfferDefinitionId()==5){
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(product.getCouponImageURl()).into(holder.imv_item);
                }
            }else {
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(product.getLargeImagePath()).into(holder.imv_item);
                }
            }


            }


        ///////////////////////

      /*  holder.remove_layout_mul.setOnClickListener(new View.OnClickListener() {
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

                                product.setListCount(0);
                                product.setQuantity("0");
                                holder.tv_status_mul.setText("Add");
                                holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                                holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));

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

       /* holder.add_plus.setOnClickListener(new View.OnClickListener() {
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
                                holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                                holder.tv_status.setText("Activated");

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
        void onProductActivate(Product product);
        void onProductMultiActivate(Product product);
        void onProductRemove(Product product);
        void onProductMultiRemove(Product product);

    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }
    private String lowercase(String lowerString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(lowerString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toLowerCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }



}


