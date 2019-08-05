package com.fareway.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.RelatedItem;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;

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
        private TextView add_item_flag,add_plus,add_minus,tv_quantity,limit,tv_status,tv_status_mul, tv_price, tv_unit, tv_saving,
                tv_saving_pri_fix,tv_promo_price__pri_fix,tv_promo_price,tv_deal_type,tv_detail,tv_varieties,tv_coupon_flag;
        private ImageView imv_item, imv_info, imv_more, imv_status,imv_status_mul;
        private LinearLayout circular_layout,circular_layout_mul, bottomLayout,remove_layout,remove_layout_mul,liner_save,liner_item_add,linear_tab_button;
        private CardView card_view;
        private RelativeLayout imv_layout,layoutforprofileimage,relative_badge;
        //private Button all_Varieties_activate;

        public MyViewHolder(View view) {
            super(view);

            relative_badge = (RelativeLayout) view.findViewById(R.id.relative_badge);
            //all_Varieties_activate= (Button)view.findViewById(R.id.all_Varieties_activate);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            layoutforprofileimage = (RelativeLayout) view.findViewById(R.id.layoutforprofileimage);
            card_view=(CardView) view.findViewById(R.id.card_view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_status_mul = (TextView) view.findViewById(R.id.tv_status_mul);
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
            add_item_flag = (TextView) view.findViewById(R.id.add_item_flag);

            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            tv_coupon_flag=(TextView)view.findViewById(R.id.tv_coupon_flag);
            //liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            //  imv_more = (ImageView) view.findViewById(R.id.imv_more);
            imv_status = (ImageView) view.findViewById(R.id.imv_status);
            imv_status_mul = (ImageView) view.findViewById(R.id.imv_status_mul);
            circular_layout= (LinearLayout) view.findViewById(R.id.circular_layout);
            circular_layout_mul= (LinearLayout) view.findViewById(R.id.circular_layout_mul);
            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);
           // count_product_number = (LinearLayout)view.findViewById(R.id.count_product_number);
            remove_layout = (LinearLayout)view.findViewById(R.id.remove_layout);
            remove_layout_mul = (LinearLayout)view.findViewById(R.id.remove_layout_mul);
            liner_save = (LinearLayout)view.findViewById(R.id.liner_save);
            liner_item_add = (LinearLayout)view.findViewById(R.id.liner_item_add);
            linear_tab_button = (LinearLayout)view.findViewById(R.id.linear_tab_button);


            limit = view.findViewById(R.id.limit);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liner_item_add.setVisibility(View.VISIBLE);
                    linear_tab_button.setVisibility(View.VISIBLE);
                }
            });
            add_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onRelatedItemSelected2(relatedItemsList.get(getAdapterPosition()));
                    /*circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    tv_status.setText("Added");*/
                }
            });

            add_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener3.onRelatedItemSelected3(relatedItemsList.get(getAdapterPosition()));
                    /*remove_layout.setVisibility(View.GONE);
                    circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    tv_status.setText("Add");*/
                }
            });

            circular_layout_mul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener5.onRelatedItemSelected5(relatedItemsList.get(getAdapterPosition()));
                    circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    tv_status_mul.setText("Added");
                }
            });

            remove_layout_mul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove_layout_mul.setVisibility(View.GONE);
                    listener4.onRelatedItemSelected4(relatedItemsList.get(getAdapterPosition()));
                    circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    tv_status_mul.setText("Add");
                }
            });
            linear_tab_button.setOnClickListener(new View.OnClickListener() {
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
        if(MainFwActivity.singleView) {

            holder.circular_layout_mul.setVisibility(View.INVISIBLE);
            holder.remove_layout_mul.setVisibility(View.INVISIBLE);
            holder.circular_layout.setVisibility(View.GONE);
            holder.remove_layout.setVisibility(View.GONE);
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
            //holder.tv_remove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            String charsUnit =lowercase(relatedItem.getPackagingSize());
            holder.tv_unit.setText(charsUnit);
            //holder.tv_unit.setText(relatedItem.getPackagingSize());
            holder.tv_quantity.setText(relatedItem.getQuantity());
            //holder.add_item_flag.setText(relatedItem.getQuantity());

            holder.circular_layout.getLayoutParams().height = 300;
            holder.circular_layout.getLayoutParams().width = 300;

            if (relatedItem.getPrimaryOfferTypeId() == 3) {
                holder.tv_promo_price__pri_fix.setText("");
                holder.tv_promo_price.setText("");
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

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ relatedItem.getDisplayPrice().split("\\.")[1]+"<sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                holder.tv_price.setText(result);

                /*Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);*/
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                /*if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status.setText("Added");
                    holder.remove_layout.setVisibility(View.VISIBLE);

                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Add");
                    holder.remove_layout.setVisibility(View.GONE);
                }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");
                    holder.remove_layout.setVisibility(View.GONE);
                }*/

            }

            else if (relatedItem.getPrimaryOfferTypeId() == 2) {
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

                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.GONE);
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                /*try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number num = dF.parse(relatedItem.getSavings());
                    holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }*/
                try {

                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number adPrice = dF.parse(relatedItem.getAdPrice());
                        Number everyDayPrice = dF.parse(relatedItem.getRegularPrice());

                            holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                            Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                            holder.tv_saving.setTypeface(typeface);

                            /*Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+everyDayPrice+"</strike>"+"<br/>"+"Promo Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(adPrice)+"</strike>");

                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                            if (relatedItem.getRegularPrice().equalsIgnoreCase("0")){
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");
                                holder.tv_promo_price__pri_fix.setText("");
                                holder.tv_promo_price.setText("");
                            }else {

                              holder.tv_saving.setText("$"+everyDayPrice);
                              holder.tv_saving_pri_fix.setText("Everyday Price ");
                              holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                              holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                            }

                } catch (Exception e) {

                }

                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                       // holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.GONE);
                      ///  holder.tv_quantity.setText(relatedItem.getQuantity());
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");
                      //  holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");
                  //  holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout.setVisibility(View.GONE);
                }
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

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                   // holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                } catch (Exception e) {

                }
                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.circular_layout.setVisibility(View.GONE);
                holder.remove_layout.setVisibility(View.GONE);
                /*if (relatedItem.getListCount()>0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status.setText("Added");
                    holder.remove_layout.setVisibility(View.VISIBLE);

                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Add");
                    holder.remove_layout.setVisibility(View.GONE);
                }*/
            }

           /* if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(relatedItem.getLargeImagePath())
                        .into(holder.imv_item);
            }*/
           if (relatedItem.getOfferDefinitionId()==5){
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getCouponImageURl())
                            .into(holder.imv_item);
                }
            }else {
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getLargeImagePath())
                            .into(holder.imv_item);
                }
            }
        }

        else {

            holder.circular_layout_mul.setVisibility(View.GONE);
            holder.remove_layout_mul.setVisibility(View.GONE);
            holder.circular_layout.setVisibility(View.GONE);
            holder.remove_layout.setVisibility(View.GONE);

            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_status_mul.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            String charsUnit =lowercase(relatedItem.getPackagingSize());
            holder.tv_unit.setText(charsUnit);
            //holder.tv_unit.setText(relatedItem.getPackagingSize());
            holder.tv_quantity.setText(relatedItem.getQuantity());
            //holder.add_item_flag.setText(relatedItem.getQuantity());

            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.circular_layout.getLayoutParams().height = 200;
            holder.circular_layout.getLayoutParams().width = 200;

            holder.relative_badge.getLayoutParams().width = 110;
            //holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);

            if (relatedItem.getPrimaryOfferTypeId() == 3) {
                holder.tv_promo_price__pri_fix.setText("");
                holder.tv_promo_price.setText("");
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

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                   // holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ relatedItem.getDisplayPrice().split("\\.")[1]+"<sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                holder.tv_price.setText(result);

                /*Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);*/
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));


            }

            else if (relatedItem.getPrimaryOfferTypeId() == 2) {
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

                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.GONE);
                holder.liner_save.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                /*try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number num = dF.parse(relatedItem.getSavings());
                    holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }*/
                try {

                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number adPrice = dF.parse(relatedItem.getAdPrice());
                    Number everyDayPrice = dF.parse(relatedItem.getRegularPrice());
/*
                    holder.tv_saving.setTypeface(holder.tv_saving.getTypeface(), Typeface.NORMAL);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                    holder.tv_saving.setTypeface(typeface);

                    Spanned varietiesUnderline = Html.fromHtml("Everyday Price "+"<strike>"+"$"+everyDayPrice+"</strike>"+"<br/>"+"Promo Price "+"<strike>"+"$"+new DecimalFormat("##.##").format(adPrice)+"</strike>");
*/

                    if (relatedItem.getRegularPrice().equalsIgnoreCase("0")){
                        holder.tv_saving_pri_fix.setText("");
                        holder.tv_saving.setText("");
                        holder.tv_promo_price__pri_fix.setText("");
                        holder.tv_promo_price.setText("");
                    }else {

                        //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_saving.setText("$"+everyDayPrice);
                              holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_promo_price__pri_fix.setText("Promo Price    ");
                        //holder.tv_promo_price.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_promo_price.setText("$"+new DecimalFormat("##.##").format(adPrice));
                    }

                } catch (Exception e) {

                }

                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");
                        // holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout.setVisibility(View.GONE);
                        ///  holder.tv_quantity.setText(relatedItem.getQuantity());
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add");
                        //  holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");
                    //  holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout.setVisibility(View.GONE);
                }
            }

            else if (relatedItem.getPrimaryOfferTypeId() == 1) {
                holder.tv_promo_price__pri_fix.setText("");
                holder.tv_promo_price.setText("");
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

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);

                try {
                    DecimalFormat dF = new DecimalFormat("00.00");
                    Number num = dF.parse(relatedItem.getRegularPrice());
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    //holder.tv_saving.setPaintFlags(holder.tv_saving.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }
                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.circular_layout.setVisibility(View.GONE);
                holder.remove_layout.setVisibility(View.GONE);
                /*if (relatedItem.getListCount()>0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status.setText("Added");
                    holder.remove_layout.setVisibility(View.VISIBLE);

                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Add");
                    holder.remove_layout.setVisibility(View.GONE);
                }*/
            }

           /* if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(relatedItem.getLargeImagePath())
                        .into(holder.imv_item);
            }*/
            if (relatedItem.getOfferDefinitionId()==5){
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getCouponImageURl())
                            .into(holder.imv_item);
                }
            }else {
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getLargeImagePath())
                            .into(holder.imv_item);
                }
            }
        }
        /*{

            holder.circular_layout.setVisibility(View.INVISIBLE);
            holder.remove_layout.setVisibility(View.INVISIBLE);
            holder.circular_layout_mul.setVisibility(View.VISIBLE);
            holder.remove_layout_mul.setVisibility(View.VISIBLE);

            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_status_mul.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
             holder.tv_unit.setText(relatedItem.getPackagingSize());
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
                holder.tv_coupon_flag.setText("With MyFareway");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number num = dF.parse(relatedItem.getSavings());
                    holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }

                String displayPrice=relatedItem.getDisplayPrice().toString();
                if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                    displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ relatedItem.getDisplayPrice().split("\\.")[1]+"<sup>";
                Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                holder.tv_price.setText(result);

                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                        holder.remove_layout_mul.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Activate");
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }

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
                String headerContent = "";
                headerContent = "Exp "+saveDate;
                holder.limit.setText("\n"+headerContent);
                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.GONE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number num = dF.parse(relatedItem.getSavings());
                    holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }
                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                       holder.remove_layout_mul.setVisibility(View.GONE);
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                       holder.remove_layout_mul.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Activate");
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }
            }else if (relatedItem.getPrimaryOfferTypeId() == 1) {
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
                holder.tv_coupon_flag.setText("");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.VISIBLE);
                try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number num = dF.parse(relatedItem.getSavings());
                    holder.tv_saving.setText("SAVE $" + new DecimalFormat("##.##").format(num));
                } catch (Exception e) {

                }
                Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                if (relatedItem.getListCount()>0){
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status_mul.setText("Added");
                    holder.remove_layout_mul.setVisibility(View.VISIBLE);
                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Add");
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }
            }

            Log.i("image",relatedItem.getLargeImagePath()+"singh");
            if (relatedItem.getOfferDefinitionId()==5){
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getCouponImageURl())
                            .into(holder.imv_item);
                }
            }else {
                if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                    Glide.with(mContext)
                            .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                            .into(holder.imv_item);
                }else {
                    Glide.with(mContext)
                            .load(relatedItem.getLargeImagePath())
                            .into(holder.imv_item);
                }
            }
        }*/

        holder.add_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onRelatedItemSelected2(relatedItemsList.get(position));
                holder.tv_quantity.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                //holder.add_item_flag.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                /*
                JSONObject json = new JSONObject();
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                RequestQueue mQueue;
                mQueue= FarewayApplication.getmInstance(mContext).getmRequestQueue();

                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", relatedItem.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity())+1));
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
                String url = null;
                Log.i("testobject",mRequestBody);
                if (relatedItem.getQuantity().equalsIgnoreCase("0")){

                    RequestQueue mQueue2;
                    mQueue2=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                        holder.tv_quantity.setText(relatedItem.getQuantity());
                                        holder.add_item_flag.setText(relatedItem.getQuantity());

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
                                params.put("UPCCode", relatedItem.getUPC());
                                params.put("CategoryID", String.valueOf(relatedItem.getCategoryID()));
                                params.put("SalePrice", relatedItem.getFinalPrice());
                                params.put("PrimaryOfferTypeId", String.valueOf(relatedItem.getPrimaryOfferTypeId()));
                                params.put("OfferDetailId", String.valueOf(relatedItem.getOfferDetailId()));
                                params.put("PersonalCircularID", String.valueOf(relatedItem.getPersonalCircularID()));
                                params.put("ExpirationDate", relatedItem.getValidityEndDate());
                                params.put("ClientID", "1");
                                params.put("PackagingSize", relatedItem.getPackagingSize());
                                params.put("DisplayPrice", relatedItem.getDisplayPrice());
                                params.put("PageID", String.valueOf(relatedItem.getPageID()));
                                params.put("Description", relatedItem.getDescription());
                                params.put("CouponID", String.valueOf(relatedItem.getCouponID()));
                                params.put("MemberID", String.valueOf(relatedItem.getMemberID()));
                                params.put("DeviceId", "1");
                                if (relatedItem.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", relatedItem.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(relatedItem.getPricingMasterID()));
                                params.put("AdPrice", relatedItem.getAdPrice());
                                params.put("RegPrice", relatedItem.getRegularPrice());
                                params.put("Savings", relatedItem.getSavings());

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                                return params;
                            }
                        };
                        RetryPolicy policy = new DefaultRetryPolicy
                                (50000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        jsonObjectRequest.setRetryPolicy(policy);
                        try {
                            mQueue2.add(jsonObjectRequest);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                }
                else {
                     url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                }

                StringRequest jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                holder.tv_quantity.setText(relatedItem.getQuantity());
                                holder.add_item_flag.setText(relatedItem.getQuantity());
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
                }*/

            }
        });

        holder.add_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Quantity",relatedItem.getQuantity());


                if (Integer.parseInt(relatedItem.getQuantity())>0){
                    listener3.onRelatedItemSelected3(relatedItemsList.get(position));
                    holder.tv_quantity.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())-1)));
                    //holder.add_item_flag.setText(String.valueOf((Integer.parseInt(relatedItem.getQuantity())-1)));
                    /*Log.i("IfQuantity",relatedItem.getQuantity());
                    JSONObject json = new JSONObject();
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println(currentDate);
                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    JSONObject ShoppingListItems = new JSONObject();
                    try {
                        ShoppingListItems.put("UPC", relatedItem.getUPC());
                        ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity())-1));
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
                                    relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())-1)));
                                    holder.tv_quantity.setText(relatedItem.getQuantity());
                                    holder.add_item_flag.setText(relatedItem.getQuantity());
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
                    }*/
                }else {
                    //listener3.onRelatedItemSelected3(relatedItemsList.get(position));
                    holder.tv_quantity.setText("0");
                    //holder.add_item_flag.setText("0");

                    /*relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())-1)));
                    holder.tv_quantity.setText("0");
                    listener3.onRelatedItemSelected3(relatedItemsList.get(position));*/

                    /*remove_layout.setVisibility(View.GONE);
                    activateListener.onProductActivate(productListFiltered.get(position));
                    circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    tv_status.setText("Add");*/
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

