package com.fareway.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
/*
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
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;

import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.RelatedItem;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAdapterParticipateItems extends RecyclerView.Adapter<CustomAdapterParticipateItems.MyViewHolder> {

    private Context mContext;
    private List<RelatedItem> relatedItemsList;
    private CustomAdapterParticipateItemsListener listener;
    private CustomAdapterParticipateItemsListener listener2;
    private CustomAdapterParticipateItemsListener listener3;
    private CustomAdapterParticipateItemsListener listener4;
    private CustomAdapterParticipateItemsListener listener5;
    public static AppUtilFw appUtil;
    public MainFwActivity activate = new MainFwActivity();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView add_item_flag,add_plus,add_minus,tv_quantity,limit,tv_status, tv_price, tv_unit, tv_saving,tv_coupon_type_1,
                tv_saving_pri_fix,tv_promo_price__pri_fix,tv_promo_price,tv_deal_type,tv_detail,tv_varieties,tv_coupon_flag;
        private ImageView imv_item, imv_info, imv_more, imv_status,circular_layout;
        private LinearLayout  bottomLayout,liner_save,liner_item_add,linear_tab_button,liner_promo_price;
        private CardView card_view;
        private RelativeLayout imv_layout,layoutforprofileimage,relative_badge;
        private RelativeLayout linear_personal_ad_lable_title_search_adapter;
        //private Button all_Varieties_activate;

        public MyViewHolder(View view) {
            super(view);
            linear_personal_ad_lable_title_search_adapter = (RelativeLayout) view.findViewById(R.id.linear_personal_ad_lable_title_search_adapter);

            relative_badge = (RelativeLayout) view.findViewById(R.id.relative_badge);
            //all_Varieties_activate= (Button)view.findViewById(R.id.all_Varieties_activate);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            layoutforprofileimage = (RelativeLayout) view.findViewById(R.id.layoutforprofileimage);
            card_view=(CardView) view.findViewById(R.id.card_view);
            /*tv_status = (TextView) view.findViewById(R.id.tv_status);*/

            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_saving_pri_fix = (TextView) view.findViewById(R.id.tv_saving_pri_fix);
            tv_promo_price__pri_fix = (TextView) view.findViewById(R.id.tv_promo_price__pri_fix);
            tv_promo_price = (TextView) view.findViewById(R.id.tv_promo_price);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
            tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            add_plus = (TextView) view.findViewById(R.id.add_plus);
            add_minus = (TextView) view.findViewById(R.id.add_minus);
            tv_coupon_type_1 = (TextView) view.findViewById(R.id.tv_coupon_type_1);

            add_item_flag = (TextView) view.findViewById(R.id.add_item_flag);

            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            tv_coupon_flag=(TextView)view.findViewById(R.id.tv_coupon_flag);
            //liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            //  imv_more = (ImageView) view.findViewById(R.id.imv_more);
            /*imv_status = (ImageView) view.findViewById(R.id.imv_status);*/

            circular_layout= (ImageView) view.findViewById(R.id.circular_layout);

            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);
           // count_product_number = (LinearLayout)view.findViewById(R.id.count_product_number);

            liner_save = (LinearLayout)view.findViewById(R.id.liner_save);
            liner_item_add = (LinearLayout)view.findViewById(R.id.liner_item_add);
            linear_tab_button = (LinearLayout)view.findViewById(R.id.linear_tab_button);
            liner_promo_price = (LinearLayout) view.findViewById(R.id.liner_promo_price);


            limit = view.findViewById(R.id.limit);

            linear_tab_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liner_item_add.setVisibility(View.VISIBLE);
                    linear_tab_button.setVisibility(View.VISIBLE);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liner_item_add.setVisibility(View.VISIBLE);
                    linear_tab_button.setVisibility(View.VISIBLE);
                }
            });

            imv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRelatedItemSelected(relatedItemsList.get(getAdapterPosition()));
                }
            });


        }
    }


    public CustomAdapterParticipateItems(Context mContext, List<RelatedItem> ProductList,CustomAdapterParticipateItemsListener listener,
                                         CustomAdapterParticipateItemsListener listener2,CustomAdapterParticipateItemsListener listener3,
                                         CustomAdapterParticipateItemsListener listener4,CustomAdapterParticipateItemsListener listener5) {
        this.mContext = mContext;
        this.relatedItemsList = ProductList;
        this.listener = listener;
        this.listener2 = listener2;
        this.listener3 = listener3;
        this.listener4 = listener4;
        this.listener5 = listener5;
        appUtil=new AppUtilFw(mContext);
    }

    @Override
    public CustomAdapterParticipateItems.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new CustomAdapterParticipateItems.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterParticipateItems.MyViewHolder holder, final int position) {

        final RelatedItem relatedItem = relatedItemsList.get(position);
        holder.liner_item_add.setVisibility(View.VISIBLE);
        holder.linear_tab_button.setVisibility(View.VISIBLE);
        holder.tv_coupon_type_1.setVisibility(View.GONE);
        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);

        if(MainFwActivity.singleView) {
            holder.circular_layout.setVisibility(View.GONE);
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
            //holder.tv_remove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            /*holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);*/
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            String charsUnit =lowercase(relatedItem.getPackagingSize());
            holder.tv_unit.setText(charsUnit);
            //holder.tv_unit.setText(relatedItem.getPackagingSize());
            holder.tv_quantity.setText(relatedItem.getQuantity());
            //holder.add_item_flag.setText("+");



            holder.circular_layout.getLayoutParams().height = 300;
            holder.circular_layout.getLayoutParams().width = 300;

            holder.add_minus.getLayoutParams().height = 75;
            holder.add_minus.getLayoutParams().width = 75;
            holder.tv_quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.add_plus.getLayoutParams().height = 75;
            holder.add_plus.getLayoutParams().width = 75;

            holder.relative_badge.getLayoutParams().width = 140;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            if (relatedItem.getPrimaryOfferTypeId() == 3) {

                String saveDate = relatedItem.getValidityEndDate();
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
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_coupon_flag.setText("With MyFareway");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                //String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(relatedItem.getDescription());

                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                holder.tv_saving_pri_fix.setText("Everyday Price ");
                float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                String formattedString = String.format("%.02f", myFloatRegularPrice);
                holder.tv_saving.setText("$" + formattedString);

                holder.liner_promo_price.setVisibility(View.VISIBLE);
                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                float myFloatAdPrice = Float.parseFloat(relatedItem.getAdPrice()+"f");
                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                holder.tv_promo_price.setText("$"+formattedAdPriceString);
                if (formattedString.equalsIgnoreCase(formattedAdPriceString)){
                    holder.liner_promo_price.setVisibility(View.GONE);
                }
                if (formattedAdPriceString.equalsIgnoreCase("0.00")){
                    holder.liner_promo_price.setVisibility(View.GONE);
                }
                /*try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }*/

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                //holder.tv_price.setText(result);
                Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                if (relatedItem.getCouponDiscount().equalsIgnoreCase("0.00") ||
                        relatedItem.getCouponDiscount().equalsIgnoreCase("0")){
                    holder.tv_price.setText(result);
                }else {
                    holder.tv_price.setText(result2);
                }

               holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));


            }

            else if (relatedItem.getPrimaryOfferTypeId() == 2) {
                holder.liner_save.setVisibility(View.GONE);
                holder.liner_promo_price.setVisibility(View.GONE);
                holder.tv_coupon_type_1.setVisibility(View.GONE);

                String saveDate = relatedItem.getValidityEndDate();
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
                /*String headerContent = "";
                headerContent = ", Exp "+saveDate;
                holder.limit.setText("\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+headerContent);*/

                ///////////////

                String headerContent = "";
                if (relatedItem.getLimitPerTransection()>1){
                    headerContent = "\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+", Exp "+saveDate;
                    holder.limit.setText(headerContent);
                }
                else {
                    headerContent = "\nExp "+saveDate;
                    holder.limit.setText(headerContent);
                }

                if (relatedItem.getMinAmount()>0){
                    headerContent = "* WITH $"+relatedItem.getMinAmount()+" PURCHASE"+"\nLimit "+relatedItem.getLimitPerTransection()+", Exp "+saveDate;
                }
                else if (relatedItem.getRequiredQty()>1){
                    if (relatedItem.getLimitPerTransection()>1){
                        headerContent = "*Must Buy " + relatedItem.getRequiredQty()+" | Limit "+relatedItem.getLimitPerTransection()+"\nExp "+saveDate;
                        String charsStarCouponShort = capitalize(relatedItem.getCouponShortDescription());
                        holder.tv_detail.setText("*"+charsStarCouponShort);
                    }else {
                        headerContent = "*Must Buy " + relatedItem.getRequiredQty()+"\nExp "+saveDate;
                        String charsStarCouponShort = capitalize(relatedItem.getCouponShortDescription());
                        holder.tv_detail.setText("*"+charsStarCouponShort);
                    }
                }
                if(relatedItem.getLimitPerTransection()>0) {
                    if(headerContent != "") {
                        if (relatedItem.getLimitPerTransection()>1){
                            headerContent = headerContent + "\nLimit : " + relatedItem.getLimitPerTransection();
                        }
                    }
                    else {
                        if (relatedItem.getLimitPerTransection()>1){
                            headerContent = "\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+", Exp "+saveDate;
                        }
                    }

                    if (relatedItem.getRewardType().equalsIgnoreCase("3")&&relatedItem.getPrimaryOfferTypeId()==2) {
                        String displayPrice=relatedItem.getDisplayPrice().toString();
                        if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";

                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        holder.tv_price.setText(result);

                    }

                    else if(relatedItem.getRewardType().equalsIgnoreCase("2")&&relatedItem.getPrimaryOfferTypeId()==2){
                        DecimalFormat dF = new DecimalFormat("00.00");
                        /*try {
                            Number rewardValue = dF.parse(relatedItem.getRewardValue());
                            Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small><small> *</small></small></sup>");*/
                            if (relatedItem.getOfferDefinitionId()==3){
                                holder.tv_price.setText("FREE");
                            }else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                String displayPrice=relatedItem.getDisplayPrice().toString();
                                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";

                                Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                holder.tv_price.setText(result2);
                            }
                            holder.tv_saving_pri_fix.setText("");
                            holder.tv_saving.setText("");

                        /*} catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                    }
                    else {
                        String displayPrice=relatedItem.getDisplayPrice().toString();
                        if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                        holder.tv_price.setText(result);
                        if (relatedItem.getRequiredQty()>1){
                            holder.tv_price.setText(result2);
                        }
                    }

                }
                holder.limit.setText(headerContent);

                ///////////////

                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.GONE);
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                try {

                    float myFloatAdPrice = Float.parseFloat(relatedItem.getAdPrice()+"f");
                    String adPrice = String.format("%.02f", myFloatAdPrice);

                    float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                    String everyDayPrice = String.format("%.02f", myFloatRegularPrice);

                    //DecimalFormat dF = new DecimalFormat("00.00");
                    //Number adPrice = dF.parse(relatedItem.getAdPrice());
                    //Number everyDayPrice = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving.setText("$"+everyDayPrice);
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    holder.tv_promo_price.setText("$"+adPrice);
                    if (adPrice.equalsIgnoreCase("0.00")&&everyDayPrice.equalsIgnoreCase("0.00")){
                        /*holder.tv_saving_pri_fix.setText("");
                        holder.tv_saving.setText("");
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");*/
                        holder.liner_save.setVisibility(View.GONE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else if (myFloatAdPrice==myFloatRegularPrice){
                        /*holder.tv_saving_pri_fix.setText("");
                        holder.tv_saving.setText("");
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");*/
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else if(myFloatRegularPrice==0.00){
                        holder.liner_save.setVisibility(View.GONE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else {
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.liner_promo_price.setVisibility(View.VISIBLE);
                        /*holder.tv_saving.setText("$"+everyDayPrice);
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                        holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));*/
                    }

                } catch (Exception e) {

                }

                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                //holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                       /* holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");*/

                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                       /* holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");*/

                    }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    /*holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");*/

                }
                /*if (relatedItem.getIsbadged().equalsIgnoreCase("True")){
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                }else {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                }*/
                /*if (relatedItem.getRequiresActivation().equalsIgnoreCase("false")){

                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());
                    holder.circular_layout.setVisibility(View.GONE);

                    String displayPrice1=relatedItem.getDisplayPrice().toString();
                    if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice1= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                    Spanned result1 = Html.fromHtml(displayPrice1.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result1);
                    holder.tv_coupon_flag.setVisibility(View.GONE);
                }else {
                    holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                }*/
            }

            else if (relatedItem.getPrimaryOfferTypeId() == 1) {
                String saveDate = relatedItem.getValidityEndDate();
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
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_coupon_flag.setText("");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                //String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(relatedItem.getDescription());

                holder.circular_layout.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                holder.tv_saving_pri_fix.setText("Everyday Price ");
                float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                String formattedString = String.format("%.02f", myFloatRegularPrice);
                holder.tv_saving.setText("$" + formattedString);
                /*try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                } catch (Exception e) {

                }*/
                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.circular_layout.setVisibility(View.GONE);

            }

            if (relatedItem.getOfferDefinitionId()==5||relatedItem.getOfferDefinitionId()==8){
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(relatedItem.getLargeImagePath()).into(holder.imv_item);
                }
            }

            else {
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(relatedItem.getLargeImagePath()).into(holder.imv_item);
                }
            }
        }

        else {
            holder.circular_layout.setVisibility(View.GONE);
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            String charsUnit =lowercase(relatedItem.getPackagingSize());
            holder.tv_unit.setText(charsUnit);
            holder.tv_quantity.setText(relatedItem.getQuantity());
            holder.add_item_flag.setText("+");

            /*holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);*/
            holder.circular_layout.getLayoutParams().height = 200;
            holder.circular_layout.getLayoutParams().width = 200;

            holder.add_minus.getLayoutParams().height = 50;
            holder.add_minus.getLayoutParams().width = 50;
            holder.tv_quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.add_plus.getLayoutParams().height = 50;
            holder.add_plus.getLayoutParams().width = 50;

            holder.relative_badge.getLayoutParams().width = 100;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);

            if (relatedItem.getPrimaryOfferTypeId() == 3) {

                String saveDate = relatedItem.getValidityEndDate();
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
                headerContent = "Exp "+saveDate;
                holder.limit.setText("\n"+headerContent);

                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_coupon_flag.setText("With MyFareway");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                //String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(relatedItem.getDescription());

                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                holder.tv_saving_pri_fix.setText("Everyday Price ");
                float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                String formattedString = String.format("%.02f", myFloatRegularPrice);
                holder.tv_saving.setText("$" + formattedString);

                holder.liner_promo_price.setVisibility(View.VISIBLE);
                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                float myFloatAdPrice = Float.parseFloat(relatedItem.getAdPrice()+"f");
                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                holder.tv_promo_price.setText("$"+formattedAdPriceString);
                if (formattedString.equalsIgnoreCase(formattedAdPriceString)){
                    holder.liner_promo_price.setVisibility(View.GONE);
                }
                if (formattedAdPriceString.equalsIgnoreCase("0.00")){
                    holder.liner_promo_price.setVisibility(View.GONE);
                }
                /*try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }*/

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                //holder.tv_price.setText(result);
                Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                if (relatedItem.getCouponDiscount().equalsIgnoreCase("0.00") ||
                        relatedItem.getCouponDiscount().equalsIgnoreCase("0")){
                    holder.tv_price.setText(result);
                }else {
                    holder.tv_price.setText(result2);
                }
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));

            }

            else if (relatedItem.getPrimaryOfferTypeId() == 2) {
                String saveDate = relatedItem.getValidityEndDate();
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
                /*String headerContent = "";
                headerContent = ", Exp "+saveDate;
                holder.limit.setText("\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+headerContent);*/

                ///////////////

                String headerContent = "";
                if (relatedItem.getLimitPerTransection()>1){
                    headerContent = "\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+", Exp "+saveDate;
                    holder.limit.setText(headerContent);
                }
                else {
                    headerContent = "\nExp "+saveDate;
                    holder.limit.setText(headerContent);
                }

                if (relatedItem.getMinAmount()>0){
                    headerContent = "* WITH $"+relatedItem.getMinAmount()+" PURCHASE"+"\nLimit "+relatedItem.getLimitPerTransection()+", Exp "+saveDate;
                }
                else if (relatedItem.getRequiredQty()>1){
                    if (relatedItem.getLimitPerTransection()>1){
                        headerContent = "*Must Buy " + relatedItem.getRequiredQty()+" | Limit "+relatedItem.getLimitPerTransection()+"\nExp "+saveDate;
                        String charsStarCouponShort = capitalize(relatedItem.getCouponShortDescription());
                        holder.tv_detail.setText("*"+charsStarCouponShort);
                    }else {
                        headerContent = "*Must Buy " + relatedItem.getRequiredQty()+"\nExp "+saveDate;
                        String charsStarCouponShort = capitalize(relatedItem.getCouponShortDescription());
                        holder.tv_detail.setText("*"+charsStarCouponShort);
                    }
                }
                if(relatedItem.getLimitPerTransection()>0) {
                    if(headerContent != "") {
                        if (relatedItem.getLimitPerTransection()>1){
                            headerContent = headerContent + "\nLimit : " + relatedItem.getLimitPerTransection();
                        }
                    }
                    else {
                        if (relatedItem.getLimitPerTransection()>1){
                            headerContent = "\nLimit " + String.valueOf(relatedItem.getLimitPerTransection())+", Exp "+saveDate;
                        }
                    }

                    if (relatedItem.getRewardType().equalsIgnoreCase("3")&&relatedItem.getPrimaryOfferTypeId()==2) {
                        String displayPrice=relatedItem.getDisplayPrice().toString();
                        if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";

                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        holder.tv_price.setText(result);

                    }

                    else if(relatedItem.getRewardType().equalsIgnoreCase("2")&&relatedItem.getPrimaryOfferTypeId()==2){
                        DecimalFormat dF = new DecimalFormat("00.00");
                       /* try {
                            Number rewardValue = dF.parse(relatedItem.getRewardValue());
                            Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue)+"% OFF"+"<sup><small><small> *</small></small></sup>");*/
                            if (relatedItem.getOfferDefinitionId()==3){
                                holder.tv_price.setText("FREE");
                            }else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                String displayPrice=relatedItem.getDisplayPrice().toString();
                                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";

                                Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                                holder.tv_price.setText(result2);
                            }
                            holder.tv_saving_pri_fix.setText("");
                            holder.tv_saving.setText("");

                       /* } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                    }
                    else {
                        String displayPrice=relatedItem.getDisplayPrice().toString();
                        if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                        holder.tv_price.setText(result);
                        if (relatedItem.getRequiredQty()>1){
                            holder.tv_price.setText(result2);
                        }
                    }

                }
                holder.limit.setText(headerContent);

                ///////////////

                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.GONE);
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                try {
                    float myFloatAdPrice = Float.parseFloat(relatedItem.getAdPrice()+"f");
                    String adPrice = String.format("%.02f", myFloatAdPrice);

                    float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                    String everyDayPrice = String.format("%.02f", myFloatRegularPrice);

                    //DecimalFormat dF = new DecimalFormat("00.00");
                    //Number adPrice = dF.parse(relatedItem.getAdPrice());
                    //Number everyDayPrice = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving.setText("$"+everyDayPrice);
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    holder.tv_promo_price.setText("$"+adPrice);
                    if (adPrice.equalsIgnoreCase("0.00")&&everyDayPrice.equalsIgnoreCase("0.00")){
                        /*holder.tv_saving_pri_fix.setText("");
                        holder.tv_saving.setText("");
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");*/
                        holder.liner_save.setVisibility(View.GONE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else if (adPrice.equalsIgnoreCase(everyDayPrice)){
                        /*holder.tv_saving_pri_fix.setText("");
                        holder.tv_saving.setText("");
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");*/
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else if(everyDayPrice.equalsIgnoreCase("0.00")){
                        holder.liner_save.setVisibility(View.GONE);
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }else {
                        holder.liner_save.setVisibility(View.VISIBLE);
                        holder.liner_promo_price.setVisibility(View.VISIBLE);
                        /*holder.tv_saving.setText("$"+everyDayPrice);
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                        holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));*/
                    }

                } catch (Exception e) {

                }

                //Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";

                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                //holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        /*holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");*/

                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                     /*   holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");*/


                    }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    /*holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");*/


                }
                /*if (relatedItem.getIsbadged().equalsIgnoreCase("True")){
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                }else {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                }*/

                /*if (relatedItem.getRequiresActivation().equalsIgnoreCase("false")){

                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());
                    holder.circular_layout.setVisibility(View.GONE);

                    String displayPrice1=relatedItem.getDisplayPrice().toString();
                    if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                        displayPrice1= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                    Spanned result1 = Html.fromHtml(displayPrice1.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                    holder.tv_price.setText(result1);
                    holder.tv_coupon_flag.setVisibility(View.GONE);
                }else {
                    holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                }*/
            }

            else if (relatedItem.getPrimaryOfferTypeId() == 1) {
                holder.tv_promo_price__pri_fix.setText("");
                holder.tv_promo_price.setText("");
                String saveDate = relatedItem.getValidityEndDate();
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
                headerContent = "Exp "+saveDate;
                holder.limit.setText("\n"+headerContent);
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_coupon_flag.setText("");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                //String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(relatedItem.getDescription());

                holder.circular_layout.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                holder.tv_saving_pri_fix.setText("Everyday Price ");
                float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice()+"f");
                String formattedString = String.format("%.02f", myFloatRegularPrice);
                holder.tv_saving.setText("$" + formattedString);

                /*try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }*/

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ (relatedItem.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                holder.tv_price.setText(result);

                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.circular_layout.setVisibility(View.GONE);

            }

            if (relatedItem.getOfferDefinitionId()==5||relatedItem.getOfferDefinitionId()==8){
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(relatedItem.getLargeImagePath()).into(holder.imv_item);
                }
            }

            else {
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                }else {
                    Picasso.get().load(relatedItem.getLargeImagePath()).into(holder.imv_item);
                }
            }
        }


        holder.add_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("add_plusQuantity",relatedItem.getQuantity());
                listener2.onRelatedItemSelected2(relatedItemsList.get(position));
                holder.tv_quantity.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));


            }
        });

        holder.add_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("add_minusQuantity",relatedItem.getQuantity());
                if (Integer.parseInt(relatedItem.getQuantity())>0){
                    listener3.onRelatedItemSelected3(relatedItemsList.get(position));
                    holder.tv_quantity.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())-1)));

                }else {
                  holder.tv_quantity.setText("0");

                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return relatedItemsList.size();
    }

    public interface CustomAdapterParticipateItemsListener {
        void onRelatedItemSelected(RelatedItem relatedItem);
        void onRelatedItemSelected2(RelatedItem relatedItem);
        void onRelatedItemSelected3(RelatedItem relatedItem);
        void onRelatedItemSelected4(RelatedItem relatedItem);
        void onRelatedItemSelected5(RelatedItem relatedItem);
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

