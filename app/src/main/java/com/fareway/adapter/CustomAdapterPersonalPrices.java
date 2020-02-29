package com.fareway.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private CustomAdapterPersonalPricesListener resetListener;
    private CustomAdapterPersonalPricesListener changeStoreListener;
    private CustomAdapterPersonalPricesListener activateMultiListener;
    private CustomAdapterPersonalPricesListener removeListener;
    private CustomAdapterPersonalPricesListener removeMultiListener;
    public MainFwActivity activate = new MainFwActivity();
    public MainFwActivity varites = new MainFwActivity();
    public static AppUtilFw appUtil;
    public int tileNo=0;
    //public static boolean couponTile=true;

    int p=0;
    int q=0;
    int r=0;
    int s=0;
    int blockTile=0;
    private static final  String TAG = CustomAdapterPersonalPrices.class.getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tv_coupon_type_1,add_item_flag,tv_status,tv_remove,tv_price,tv_unit,tv_saving,tv_saving_pri_fix,tv_promo_price__pri_fix,tv_promo_price,tv_detail,tv_deal_type,
                tv_coupon_flag,tv_varieties,limit,must_buy,add_plus,add_minus,tv_quantity,additional_offers,personal_offers,tv_location,
                tv_personal_lable,tv_location_multi;
        public ImageView imv_item, imv_info,imv_status,coupon_badge,imv_location,circular_layout;
        private LinearLayout bottomLayout,liner_save,count_product_number,liner_promo_price,
                item_layout_tile,linear_personal_ad_lable,linear_multi_personal_ad_lable;
        private CardView card_view;
        private RelativeLayout imv_layout,relative_badge;
        private RelativeLayout linear_personal_ad_lable_title_search_adapter;
        private TextView tv_personal_lable_title_search_adapter,search_keyword_adapter,search_reset_adapter,
                tv_location_title_search_adapter,change_store_search_adapter;
        private ImageView imv_location_search_adapter;
        private View view_line_search_adapter;
        public MyViewHolder(View view) {

            super(view);
            ///////////////////////
            linear_personal_ad_lable_title_search_adapter = (RelativeLayout) view.findViewById(R.id.linear_personal_ad_lable_title_search_adapter);

            tv_personal_lable_title_search_adapter = (TextView) view.findViewById(R.id.tv_personal_lable_title_search_adapter);
            search_keyword_adapter = (TextView) view.findViewById(R.id.search_keyword_adapter);
            search_reset_adapter = (TextView) view.findViewById(R.id.search_reset_adapter);
            tv_location_title_search_adapter = (TextView) view.findViewById(R.id.tv_location_title_search_adapter);
            change_store_search_adapter = (TextView) view.findViewById(R.id.change_store_search_adapter);
            imv_location_search_adapter = (ImageView) view.findViewById(R.id.imv_location_search_adapter);
            view_line_search_adapter = (View)view.findViewById(R.id.view_line_search_adapter);
            /////////////////////////
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            relative_badge = (RelativeLayout) view.findViewById(R.id.relative_badge);

            card_view=(CardView) view.findViewById(R.id.card_view);
         /*   tv_status = (TextView) view.findViewById(R.id.tv_status);*/
            //tv_status_mul = (TextView) view.findViewById(R.id.tv_status_mul);

            //tv_remove_mul = (TextView) view.findViewById(R.id.tv_remove_mul);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_saving_pri_fix = (TextView) view.findViewById(R.id.tv_saving_pri_fix);
            tv_promo_price__pri_fix = (TextView) view.findViewById(R.id.tv_promo_price__pri_fix);
            tv_promo_price = (TextView) view.findViewById(R.id.tv_promo_price);

            tv_coupon_type_1 = (TextView) view.findViewById(R.id.tv_coupon_type_1);


            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
            tv_coupon_flag=(TextView)view.findViewById(R.id.tv_coupon_flag);
            additional_offers=(TextView)view.findViewById(R.id.additional_offers);
            tv_location=(TextView)view.findViewById(R.id.tv_location);
            tv_location_multi=(TextView)view.findViewById(R.id.tv_location_multi);

            tv_personal_lable=(TextView)view.findViewById(R.id.tv_personal_lable);




            tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            add_item_flag = (TextView) view.findViewById(R.id.add_item_flag);

            add_plus = (TextView) view.findViewById(R.id.add_plus);
            add_minus = (TextView) view.findViewById(R.id.add_minus);


            imv_location = (ImageView) view.findViewById(R.id.imv_location);
            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            coupon_badge = (ImageView) view.findViewById(R.id.coupon_badge);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            liner_promo_price = (LinearLayout) view.findViewById(R.id.liner_promo_price);
            item_layout_tile = (LinearLayout) view.findViewById(R.id.item_layout_tile);
            linear_personal_ad_lable = (LinearLayout) view.findViewById(R.id.linear_personal_ad_lable);
            linear_multi_personal_ad_lable = (LinearLayout) view.findViewById(R.id.linear_multi_personal_ad_lable);


            limit = view.findViewById(R.id.limit);

            must_buy= view.findViewById(R.id.must_buy);

            /*imv_status = (ImageView) view.findViewById(R.id.imv_status);*/

            circular_layout= (ImageView) view.findViewById(R.id.circular_layout);

            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);

            relative_badge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onProductVeritiesSelected(productListFiltered.get(getAdapterPosition()));
                }
            });

            imv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductSelected(productListFiltered.get(getAdapterPosition()));
                }
            });

            search_reset_adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetListener.onReset(productListFiltered.get(getAdapterPosition()));
                    //MainFwActivity.reLoadApi();
                }
            });
            change_store_search_adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeStoreListener.onChangeStore(productListFiltered.get(getAdapterPosition()));
                    //MainFwActivity.reLoadApi();
                }
            });

        }
    }

    public CustomAdapterPersonalPrices(Context mContext, List<Product> ProductList, CustomAdapterPersonalPricesListener listener,
                                       CustomAdapterPersonalPricesListener listener2, CustomAdapterPersonalPricesListener activateListener,
                                       CustomAdapterPersonalPricesListener activateMultiListener, CustomAdapterPersonalPricesListener removeListener,
                                       CustomAdapterPersonalPricesListener removeMultiListener, MainFwActivity resetListener,
                                       MainFwActivity changeStoreListener) {
        this.mContext = mContext;
        this.listener = listener;
        this.listener2 = listener2;
        this.activateListener = activateListener;
        this.resetListener = resetListener;
        this.changeStoreListener = changeStoreListener;

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
        MainFwActivity.tv_location_title.setText(appUtil.getPrefrence("StoreName"));
        MainFwActivity.tv_location_title_search.setText(appUtil.getPrefrence("StoreName"));

        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
        holder.tv_location_title_search_adapter.setText(appUtil.getPrefrence("StoreName"));
        holder.tv_personal_lable_title_search_adapter.setText(MainFwActivity.offerTitle_search_string);
        holder.search_keyword_adapter.setText(MainFwActivity.search_keyword_string);

        if (MainFwActivity.singleView) {
            if (MainFwActivity.x == 3) {
                if (position == 0) {
                    if (MainFwActivity.categoryShort || MainFwActivity.offferShort || MainFwActivity.savingsShort || MainFwActivity.tmp == 1 ||
                            MainFwActivity.tmp == 2 || MainFwActivity.tmp == 3) {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
                    } else {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.imv_location_search_adapter.setVisibility(View.VISIBLE);
                        holder.tv_location_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.view_line_search_adapter.setVisibility(View.VISIBLE);
                        holder.change_store_search_adapter.setVisibility(View.VISIBLE);

                        holder.tv_personal_lable_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.search_keyword_adapter.setVisibility(View.VISIBLE);
                        holder.search_reset_adapter.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
                }

            }
            else {
                holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
            }
            holder.card_view.setVisibility(View.VISIBLE);
            holder.item_layout_tile.setVisibility(View.VISIBLE);

            holder.circular_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product.getPrimaryOfferTypeId() == 3) {
                        activateListener.onProductActivate(productListFiltered.get(position));
                        product.setClickCount(1);
                        product.setListCount(1);
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                        /*holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/
                    } else if (product.getPrimaryOfferTypeId() == 2) {
                        activateListener.onProductActivate(productListFiltered.get(position));
                        product.setClickCount(1);
                        product.setListCount(1);
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                        /*holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/
                    } else if (product.getPrimaryOfferTypeId() == 1) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                       /* holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Added");*/

                        activateListener.onProductActivate(productListFiltered.get(position));
                    }

                }
            });

            holder.circular_layout.setVisibility(View.VISIBLE);

            /*holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);*/
            //holder.imv_status

            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
            holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
            holder.tv_coupon_type_1.setVisibility(View.GONE);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);

            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.must_buy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);


            holder.circular_layout.getLayoutParams().height = 250;
            holder.circular_layout.getLayoutParams().width = 250;

            holder.relative_badge.getLayoutParams().width = 140;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            if (((product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) && product.getPrimaryOfferTypeId() == 2 &&
                    product.getCPRPromoTypeId() == 3 && product.getOfferDetailId() == 4) || (product.getPrimaryOfferTypeId() == 2 && product.getRelevantUPC().equalsIgnoreCase("0"))) {
                holder.relative_badge.setVisibility(View.GONE);
            } else {
                holder.relative_badge.setVisibility(View.VISIBLE);
            }

           /* if (product.getTotalQuantity().equalsIgnoreCase("0")){
                holder.add_item_flag.setText("+");
            }else {
                holder.add_item_flag.setText(product.getTotalQuantity());
            }*/
            if (product.getTotalQuantity() == null) {
                holder.add_item_flag.setText("+");
            } else if (product.getTotalQuantity().equalsIgnoreCase("0")) {
                holder.add_item_flag.setText("+");

            } else {
                holder.add_item_flag.setText(product.getTotalQuantity());
            }


            if (product.getInCircular() == 0) {
                holder.additional_offers.setVisibility(View.GONE);
                holder.linear_personal_ad_lable.setVisibility(View.GONE);
                holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                if (position == 0) {
                    // holder.additional_offers.setVisibility(View.VISIBLE);
                    // holder.additional_offers.setText("Personal Ad");
                } else {
                    holder.additional_offers.setVisibility(View.GONE);
                    holder.additional_offers.setText("");
                }
                Log.i("elseincircular", String.valueOf(product.getInCircular()));

                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                        // getTokenkey();
                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "Exp " + saveDate;
                    holder.limit.setText("\n" + headerContent);
                    //holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    //String chars = capitalize(product.getCouponShortDescription());
                    //holder.tv_detail.setText(chars);
                    holder.tv_detail.setText(product.getCouponShortDescription());
                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);
                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        holder.tv_price.setText(result2);
                    }else {
                        holder.tv_price.setText(result);
                    }

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);

                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                    String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                    holder.tv_promo_price.setText("$" + formattedAdPriceString);
                    Log.i("ad", product.getAdPrice());
                    Log.i("re", product.getRegularPrice());
                    if (formattedString.equalsIgnoreCase(formattedAdPriceString)) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    if (formattedAdPriceString.equalsIgnoreCase("0.00")) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    /*try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }*/
                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                        /*holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                        /*holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");*/
                    }

                }
                else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                    holder.tv_coupon_type_1.setVisibility(View.GONE);
                    //
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                    } else {
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


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }
                    String charsCouponShort = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(charsCouponShort);
                    holder.tv_coupon_flag.setText("With Coupon");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    /////////////////////////
                    String headerContent = "";
                    if (product.getLimitPerTransection() > 1) {
                        headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    } else {
                        headerContent = "<br>Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    }

                    if (product.getMinAmount() > 0||product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) {
                        headerContent = "<strong>* With $" + product.getMinAmount() + " Purchase " + "</strong><br><span>Exp " + saveDate + "</span>";
                        String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                        holder.tv_detail.setText("*" + charsStarCouponShort);
                        //headerContent = "* WITH $" + product.getMinAmount() + " PURCHASE" + "\nLimit " + product.getLimitPerTransection() + ", Exp " + saveDate;
                    }  else if (product.getRequiredQty() > 1) {
                        if (product.getLimitPerTransection() > 1) {
                            //<strong>must</strong></br><span>test</span>
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + " | Limit " + product.getLimitPerTransection() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        } else {
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        }
                    }
                    if (product.getLimitPerTransection() > 0) {
                        if (headerContent != "") {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = headerContent + "<br>Limit : " + product.getLimitPerTransection();
                            }
                        } else {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            }
                        }

                        if (product.getRewardType().equalsIgnoreCase("3") && product.getPrimaryOfferTypeId() == 2) {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            holder.tv_price.setText(result);

                        } else if (product.getRewardType().equalsIgnoreCase("2") && product.getPrimaryOfferTypeId() == 2) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue) + "% OFF" + "<sup><small><small> *</small></small></sup>");
                                if (product.getOfferDefinitionId() == 3) {
                                    holder.tv_price.setText("FREE");
                                } else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                    String displayPrice = product.getDisplayPrice().toString();
                                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                    holder.tv_price.setText(result2);
                                }
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";


                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            //Spanned result = Html.fromHtml("<sup><small><small>$</small></small></sup>1<sup><small><small>50</small></small></sup><sup><small><small>OFF</small></small></sup>");
                            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>") + "<sup><small><small> *</small></small></sup>");
                            holder.tv_price.setText(result);
                            if (product.getRequiredQty() > 1||product.getMinAmount() > 0) {
                                holder.tv_price.setText(result2);
                            }
                        }

                    }

                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    Spanned headerHtml = Html.fromHtml(headerContent);
                    holder.limit.setText(headerHtml);

                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            holder.limit.setGravity(Gravity.RIGHT);
                        } else {
                            headerContent = "<br>Exp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        }
                        Spanned headerHtml2 = Html.fromHtml(headerContent);

                        holder.limit.setText(headerHtml2);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                    }

                    ///////////////

                    if (product.getOfferDefinitionId() == 3 || product.getOfferDefinitionId() == 2 || product.getOfferDefinitionId() == 1 || product.getOfferDefinitionId() == 4) {
                        /*String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    } else {
                        /*String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    }

                    try {
                        if (product.getOfferDefinitionId() == 4) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum = Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")) {
                                //holder.tv_price.setText("Buy " + product.getRequiredQty());
                                //holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);

                            } else if (product.getRewardType().equalsIgnoreCase("3") && floatnum > 0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            } else {
                                holder.tv_price.setText("Buy " + product.getRequiredQty());
                                holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " " + new DecimalFormat("##.##").format(coupon_discount) + "%OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);
                            }


                        } else if (product.getOfferDefinitionId() == 1) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")) {

                                holder.tv_coupon_type_1.setVisibility(View.GONE);
                                holder.liner_save.setVisibility(View.VISIBLE);
                                holder.liner_promo_price.setVisibility(View.VISIBLE);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                //holder.tv_saving.setText("$" + everyDayPrice);

                                float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                                String formattedString = String.format("%.02f", myFloatRegularPrice);
                                holder.tv_saving.setText("$" + formattedString);

                                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                                //holder.tv_promo_price.setText("$" + new DecimalFormat("##.##").format(adPrice));

                                float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                                holder.tv_promo_price.setText("$" + formattedAdPriceString);
                            } else {
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            }

                        } else if (product.getOfferDefinitionId() == 3) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        } else if (product.getOfferDefinitionId() == 2) {
                          /*  DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));*/
                        } else if (product.getOfferDefinitionId() == 5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            //holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                     /*   holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                    /*    holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");*/
                    }

                    /*if (product.getRequiresActivation().equalsIgnoreCase("false")){

                        holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                        holder.tv_deal_type.setText(product.getOfferTypeTagName());
                        holder.circular_layout.setVisibility(View.GONE);

                        String displayPrice=product.getDisplayPrice().toString();
                        if(product.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ (product.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        holder.tv_price.setText(result);
                        holder.tv_coupon_flag.setVisibility(View.GONE);
                    }else {
                        holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                    }*/

                }
                else if (product.getPrimaryOfferTypeId() == 1) {
                    if (product.getQuantity() == null) {
                        holder.add_item_flag.setText("+");
                    } else if (product.getQuantity().equalsIgnoreCase("0")) {
                        holder.add_item_flag.setText("+");

                    } else {
                        holder.add_item_flag.setText(product.getQuantity());
                    }
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length() == 0) {

                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "\nExp " + saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(product.getDescription());

                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);
                    /*try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                    } catch (Exception e) {

                    }*/

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }


                }
                else if (product.getPrimaryOfferTypeId() == 0) {
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
                if (position == 0) {

                } else {
                    holder.additional_offers.setVisibility(View.GONE);
                    holder.additional_offers.setText("");
                }

                if (MainFwActivity.multiLable == true) {
                    MainFwActivity.couponTile = true;
                    MainFwActivity.multiLable = false;
                }
                if (product.getTileNumber().equalsIgnoreCase("998")) {

                    if (MainFwActivity.couponTile == true && MainFwActivity.pdView == true) {
                        r = position;

                        MainFwActivity.couponTile = false;
                        MainFwActivity.singleLable = true;
                    }
                }

                Log.i("valuer", String.valueOf(r));
                Log.i("values", String.valueOf(s));

                if (r == position && r != 0 && MainFwActivity.pdView == true) {
                    holder.additional_offers.setVisibility(View.VISIBLE);
                    holder.additional_offers.setText("Additional Offers");
                    Log.d(TAG, ">> Additional Offers");
                    //MainFwActivity.offerTitle.setText("Additional Offers");
                } else {
                    holder.additional_offers.setVisibility(View.GONE);
                    holder.linear_personal_ad_lable.setVisibility(View.GONE);
                    holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                    //holder.additional_offers.setVisibility(View.GONE);
                }
                Log.i("elseincircular", String.valueOf(product.getInCircular()));

                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.item_layout_tile.setVisibility(View.VISIBLE);
                    MainFwActivity.offerTitle.setText("Personal Ad");
                    if (position == 0 && MainFwActivity.pdView == true) {

                        holder.additional_offers.setVisibility(View.GONE);
                        holder.tv_personal_lable.setVisibility(View.VISIBLE);
                        holder.imv_location.setVisibility(View.VISIBLE);
                        holder.tv_location.setVisibility(View.VISIBLE);
                        holder.linear_personal_ad_lable.setVisibility(View.GONE);
                        //holder.linear_personal_ad_lable.setVisibility(View.VISIBLE);
                        //holder.tv_location.setText(appUtil.getPrefrence("StoreName"));
                    }
                    else {
                        holder.additional_offers.setVisibility(View.GONE);
                        holder.linear_personal_ad_lable.setVisibility(View.GONE);
                        holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);

                    }
                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                        // getTokenkey();
                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "Exp " + saveDate;
                    holder.limit.setText("\n" + headerContent);
                    //holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(product.getCouponShortDescription());
                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        holder.tv_price.setText(result2);
                    }else {
                        holder.tv_price.setText(result);
                    }


                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));

                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);

                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                    String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                    holder.tv_promo_price.setText("$" + formattedAdPriceString);
                    Log.i("ad", product.getAdPrice());
                    Log.i("re", product.getRegularPrice());
                    if (formattedString.equalsIgnoreCase(formattedAdPriceString)) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    if (formattedAdPriceString.equalsIgnoreCase("0.00")) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    /*try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }*/
                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }
                    else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                      /*  holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/

                    }
                    else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                        /*holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");*/
                    }

                }
                else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.item_layout_tile.setVisibility(View.VISIBLE);
                    /*if (product.getTileNumber().equalsIgnoreCase("998")||product.getTileNumber().equalsIgnoreCase("999")){
                        MainFwActivity.offerTitle.setText("Additional Offers");
                    }else {
                        MainFwActivity.offerTitle.setText("Personal Ad");
                    }*/
                    Log.i("valuepadd", String.valueOf(p));
                    Log.i("valueadd", String.valueOf(p - 5));
                    Log.i("valueradd", String.valueOf(r));
                    Log.i("valuermin", String.valueOf(r + 5));
                    if ((position) >= (r + 2) && product.getTileNumber().equalsIgnoreCase("999")) {
                        if (r != 0) {
                            MainFwActivity.offerTitle.setText("Additional Offers");
                        }

                    }

                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                    holder.tv_coupon_type_1.setVisibility(View.GONE);

                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                    } else {
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


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }
                    String charsCouponShort = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(charsCouponShort);
                    holder.tv_coupon_flag.setText("With Coupon");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
/////////////////////////////
                    String headerContent = "";
                    if (product.getLimitPerTransection() > 1) {
                        headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    } else {
                        headerContent = "<br>Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    }

                    if (product.getMinAmount() > 0||product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) {
                        headerContent = "<strong>* With $" + product.getMinAmount() + " Purchase " + "</strong><br><span>Exp " + saveDate + "</span>";
                        String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                        holder.tv_detail.setText("*" + charsStarCouponShort);
                        //headerContent = "* WITH $" + product.getMinAmount() + " PURCHASE" + "\nLimit " + product.getLimitPerTransection() + ", Exp " + saveDate;
                    }  else if (product.getRequiredQty() > 1) {
                        if (product.getLimitPerTransection() > 1) {
                            //<strong>must</strong></br><span>test</span>
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + " | Limit " + product.getLimitPerTransection() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        } else {
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        }
                    }
                    if (product.getLimitPerTransection() > 0) {
                        if (headerContent != "") {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = headerContent + "<br>Limit : " + product.getLimitPerTransection();
                            }
                        } else {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            }
                        }

                        if (product.getRewardType().equalsIgnoreCase("3") && product.getPrimaryOfferTypeId() == 2) {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            holder.tv_price.setText(result);

                        } else if (product.getRewardType().equalsIgnoreCase("2") && product.getPrimaryOfferTypeId() == 2) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue) + "% OFF" + "<sup><small><small> *</small></small></sup>");
                                if (product.getOfferDefinitionId() == 3) {
                                    holder.tv_price.setText("FREE");
                                } else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                    String displayPrice = product.getDisplayPrice().toString();
                                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                    holder.tv_price.setText(result2);
                                }
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";


                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            //Spanned result = Html.fromHtml("<sup><small><small>$</small></small></sup>1<sup><small><small>50</small></small></sup><sup><small><small>OFF</small></small></sup>");
                            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>") + "<sup><small><small> *</small></small></sup>");
                            holder.tv_price.setText(result);
                            if (product.getRequiredQty() > 1||product.getMinAmount() > 0) {
                                holder.tv_price.setText(result2);
                            }
                        }

                    }

                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    Spanned headerHtml = Html.fromHtml(headerContent);
                    holder.limit.setText(headerHtml);

                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        } else {
                            headerContent = "<br>Exp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        }
                        Spanned headerHtml2 = Html.fromHtml(headerContent);

                        holder.limit.setText(headerHtml2);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                    }
                    /////////////////////////////////

                    if (product.getOfferDefinitionId() == 3 || product.getOfferDefinitionId() == 2 || product.getOfferDefinitionId() == 1 || product.getOfferDefinitionId() == 4) {
                        /*String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    } else {
                        /*String chars = capitalize(product.getDescription());
                        holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    }

                    try {
                        if (product.getOfferDefinitionId() == 4) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum = Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")) {
                                //holder.tv_price.setText("Buy " + product.getRequiredQty());
                                //holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);

                            } else if (product.getRewardType().equalsIgnoreCase("3") && floatnum > 0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            } else {
                                holder.tv_price.setText("Buy " + product.getRequiredQty());
                                holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " " + new DecimalFormat("##.##").format(coupon_discount) + "%OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);
                            }


                        } else if (product.getOfferDefinitionId() == 1) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")) {

                                holder.tv_coupon_type_1.setVisibility(View.GONE);
                                holder.liner_save.setVisibility(View.VISIBLE);
                                holder.liner_promo_price.setVisibility(View.VISIBLE);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                //holder.tv_saving.setText("$" + everyDayPrice);

                                float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                                String formattedString = String.format("%.02f", myFloatRegularPrice);
                                holder.tv_saving.setText("$" + formattedString);

                                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                                //holder.tv_promo_price.setText("$" + new DecimalFormat("##.##").format(adPrice));

                                float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                                holder.tv_promo_price.setText("$" + formattedAdPriceString);
                            } else {
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            }

                        } else if (product.getOfferDefinitionId() == 3) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        } else if (product.getOfferDefinitionId() == 2) {
                          /*  DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));*/
                        } else if (product.getOfferDefinitionId() == 5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            //holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                     /*   holder.imv_status.setVisibility(View.VISIBLE);
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                       /* holder.imv_status.setVisibility(View.GONE);
                        holder.tv_status.setText("Activate");*/
                    }

                    /*if (product.getRequiresActivation().equalsIgnoreCase("false")){

                        holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                        holder.tv_deal_type.setText(product.getOfferTypeTagName());
                        holder.circular_layout.setVisibility(View.GONE);

                        String displayPrice=product.getDisplayPrice().toString();
                        if(product.getDisplayPrice().toString().split("\\.").length>1)
                            displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ (product.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                        Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                        holder.tv_price.setText(result);
                        holder.tv_coupon_flag.setVisibility(View.GONE);
                    }else {
                        holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                    }*/

                }
                else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.item_layout_tile.setVisibility(View.VISIBLE);
                    Log.i("valuep", String.valueOf(p));
                    Log.i("valuemin", String.valueOf(p - 5));
                    if ((position) <= (r - 2)) {

                        if (r != 0) {
                            MainFwActivity.offerTitle.setText("Personal Ad");
                        }
                    }
                    //MainFwActivity.offerTitle.setText("Personal Ad");
                    if (product.getQuantity() == null) {
                        holder.add_item_flag.setText("+");
                    } else if (product.getQuantity().equalsIgnoreCase("0")) {
                        holder.add_item_flag.setText("+");

                    } else {
                        holder.add_item_flag.setText(product.getQuantity());
                    }
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length() == 0) {

                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "\nExp " + saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(product.getDescription());

                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);
                    /*try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getRegularPrice());
                        holder.tv_saving_pri_fix.setText("Everyday Price ");
                        holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                    } catch (Exception e) {

                    }*/

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }
                    else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }


                }
                else if (product.getPrimaryOfferTypeId() == 0) {
                    //MainFwActivity.offerTitle.setText(R.string.add_ofr);
                    holder.card_view.setVisibility(View.GONE);
                    // Gets linearlayout
                    //LinearLayout layout = findViewById(R.id.numberPadLayout);
// Gets the layout params that will allow you to resize the layout
                    /*ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();

                    params.height = 30;
                    // params.width = 100;
                    holder.item_layout_tile.setLayoutParams(params);*/
                }
            }

            if (product.getOfferDefinitionId() == 5 || product.getOfferDefinitionId() == 8) {
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else {
                    Picasso.get().load(product.getCouponImageURl()).into(holder.imv_item);
                }
            } else {
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else if (product.getLargeImagePath().equalsIgnoreCase("")) {
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else if (product.getPrimaryOfferTypeId() != 0) {
                    Picasso.get().load(product.getLargeImagePath()).into(holder.imv_item);
                }
            }


        }
        else {
            if (MainFwActivity.x == 3) {
                if (position == 0) {
                    if (MainFwActivity.categoryShort || MainFwActivity.offferShort || MainFwActivity.savingsShort || MainFwActivity.tmp == 1 ||
                            MainFwActivity.tmp == 2 || MainFwActivity.tmp == 3) {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
                    } else {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.imv_location_search_adapter.setVisibility(View.GONE);
                        holder.tv_location_title_search_adapter.setVisibility(View.GONE);
                        holder.view_line_search_adapter.setVisibility(View.GONE);
                        holder.change_store_search_adapter.setVisibility(View.GONE);

                        holder.tv_personal_lable_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.search_keyword_adapter.setVisibility(View.VISIBLE);
                        holder.search_reset_adapter.setVisibility(View.VISIBLE);
                    }

                }
                else if (position == 1) {
                    if (MainFwActivity.categoryShort || MainFwActivity.offferShort || MainFwActivity.savingsShort || MainFwActivity.tmp == 1 ||
                            MainFwActivity.tmp == 2 || MainFwActivity.tmp == 3) {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
                    } else {
                        holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.imv_location_search_adapter.setVisibility(View.VISIBLE);
                        holder.tv_location_title_search_adapter.setVisibility(View.VISIBLE);
                        holder.view_line_search_adapter.setVisibility(View.VISIBLE);
                        holder.change_store_search_adapter.setVisibility(View.VISIBLE);

                        holder.tv_personal_lable_title_search_adapter.setVisibility(View.GONE);
                        holder.search_keyword_adapter.setVisibility(View.GONE);
                        holder.search_reset_adapter.setVisibility(View.GONE);
                    }

                }
                else {
                    holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
                }

            }
            else {
                holder.linear_personal_ad_lable_title_search_adapter.setVisibility(View.GONE);
            }

            holder.item_layout_tile.setVisibility(View.VISIBLE);
            holder.circular_layout.setVisibility(View.VISIBLE);
            holder.circular_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product.getPrimaryOfferTypeId() == 3) {
                        activateListener.onProductActivate(productListFiltered.get(position));
                        product.setClickCount(1);
                        product.setListCount(1);
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                            /*holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/

                    }
                    else if (product.getPrimaryOfferTypeId() == 2) {
                        activateListener.onProductActivate(productListFiltered.get(position));
                        product.setClickCount(1);
                        product.setListCount(1);
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                         /*   holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/
                    }
                    else if (product.getPrimaryOfferTypeId() == 1) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                       /*     holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/
                        activateListener.onProductActivate(productListFiltered.get(position));
                    }
                    else {

                    }

                }
            });


            //holder.imv_status


            /*holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);*/
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_coupon_type_1.setVisibility(View.GONE);

            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_saving_pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price__pri_fix.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            holder.tv_promo_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.must_buy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            /*holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);*/
            holder.circular_layout.getLayoutParams().height = 150;
            holder.circular_layout.getLayoutParams().width = 150;

            holder.relative_badge.getLayoutParams().width = 100;
            holder.add_item_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
            //offerdefinitionid=8 and PrimaryOfferTypeId=2 and CPRPromoTypeId=3 and OfferDetailId=4
            if (((product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) && product.getPrimaryOfferTypeId() == 2 &&
                    product.getCPRPromoTypeId() == 3 && product.getOfferDetailId() == 4) || (product.getPrimaryOfferTypeId() == 2 && product.getRelevantUPC().equalsIgnoreCase("0"))) {
                holder.relative_badge.setVisibility(View.GONE);
            } else {
                holder.relative_badge.setVisibility(View.VISIBLE);
            }
/*
            if (product.getTotalQuantity().equalsIgnoreCase("0")){
                holder.add_item_flag.setText("+");
            }else {
                holder.add_item_flag.setText(product.getTotalQuantity());
            }*/
            if (product.getTotalQuantity() == null) {
                holder.add_item_flag.setText("+");
            } else if (product.getTotalQuantity().equalsIgnoreCase("0")) {
                holder.add_item_flag.setText("+");

            } else {
                holder.add_item_flag.setText(product.getTotalQuantity());
            }

            if (product.getInCircular() == 0) {
                holder.additional_offers.setVisibility(View.GONE);
                holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                holder.linear_personal_ad_lable.setVisibility(View.GONE);
                if (position == 0) {
                    //holder.additional_offers.setVisibility(View.VISIBLE);
                    //holder.additional_offers.setText("Personal Ad");
                } else if (position == 1) {
                    //holder.additional_offers.setVisibility(View.VISIBLE);
                    //holder.additional_offers.setText("");
                } else {
                    holder.additional_offers.setVisibility(View.GONE);
                    holder.additional_offers.setText("");
                }
                  /*  if (product.getTileNumber().equalsIgnoreCase("999")) {
                        if (activate.OtherCoupon.equalsIgnoreCase("0")) {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                            activate.OtherCoupon=product.getCouponID();
                        }
                        else if (activate.OtherCoupon.equalsIgnoreCase(product.getCouponID()))
                        {
                            holder.additional_offers.setVisibility(View.VISIBLE);
                            holder.additional_offers.setText("Additional Offers");
                        }
                        else
                        {
                            if (activate.OtherCouponmulti.equalsIgnoreCase("0")) {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                                activate.OtherCouponmulti=product.getCouponID();
                            }
                            else if (activate.OtherCouponmulti.equalsIgnoreCase(product.getCouponID()))
                            {
                                holder.additional_offers.setVisibility(View.VISIBLE);
                                holder.additional_offers.setText(" ");
                            }
                            else
                                holder.additional_offers.setVisibility(View.GONE);
                        }
                    }else {
                        holder.additional_offers.setVisibility(View.GONE);
                    }*/
                Log.i("elseincircular", String.valueOf(product.getInCircular()));

                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                        // getTokenkey();
                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "Exp " + saveDate;
                    holder.limit.setText("\n" + headerContent);
                    //holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(product.getCouponShortDescription());
                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);
                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        holder.tv_price.setText(result2);
                    }else {
                        holder.tv_price.setText(result);
                    }

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    //holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);

                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                    String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                    holder.tv_promo_price.setText("$" + formattedAdPriceString);
                    Log.i("ad", product.getAdPrice());
                    Log.i("re", product.getRegularPrice());
                    if (formattedString.equalsIgnoreCase(formattedAdPriceString)) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    if (formattedAdPriceString.equalsIgnoreCase("0.00")) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                        /*try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                        } catch (Exception e) {

                        }*/
                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                           /* holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                           /* holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");*/
                    }

                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }

                }
                else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                    holder.tv_coupon_type_1.setVisibility(View.GONE);


                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                    } else {
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


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }
                    String charsCouponShort = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(charsCouponShort);

                    holder.tv_coupon_flag.setText("With Coupon");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    /////////////

                    String headerContent = "";
                    if (product.getLimitPerTransection() > 1) {
                        headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    } else {
                        headerContent = "<br>Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    }

                    if (product.getMinAmount() > 0||product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) {
                        headerContent = "<strong>* With $" + product.getMinAmount() + " Purchase " + "</strong><br><span>Exp " + saveDate + "</span>";
                        String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                        holder.tv_detail.setText("*" + charsStarCouponShort);
                        //headerContent = "* WITH $" + product.getMinAmount() + " PURCHASE" + "\nLimit " + product.getLimitPerTransection() + ", Exp " + saveDate;
                    } else if (product.getRequiredQty() > 1) {
                        if (product.getLimitPerTransection() > 1) {
                            //<strong>must</strong></br><span>test</span>
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + " | Limit " + product.getLimitPerTransection() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        } else {
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        }
                    }
                    if (product.getLimitPerTransection() > 0) {
                        if (headerContent != "") {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = headerContent + "<br>Limit : " + product.getLimitPerTransection();
                            }
                        } else {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            }
                        }

                        if (product.getRewardType().equalsIgnoreCase("3") && product.getPrimaryOfferTypeId() == 2) {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            holder.tv_price.setText(result);

                        } else if (product.getRewardType().equalsIgnoreCase("2") && product.getPrimaryOfferTypeId() == 2) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue) + "% OFF" + "<sup><small><small> *</small></small></sup>");
                                if (product.getOfferDefinitionId() == 3) {
                                    holder.tv_price.setText("FREE");
                                } else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                    String displayPrice = product.getDisplayPrice().toString();
                                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                    holder.tv_price.setText(result2);
                                }
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";


                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            //Spanned result = Html.fromHtml("<sup><small><small>$</small></small></sup>1<sup><small><small>50</small></small></sup><sup><small><small>OFF</small></small></sup>");
                            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>") + "<sup><small><small> *</small></small></sup>");
                            holder.tv_price.setText(result);
                            if (product.getRequiredQty() > 1||product.getMinAmount() > 0) {
                                holder.tv_price.setText(result2);
                            }
                        }

                    }

                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    Spanned headerHtml = Html.fromHtml(headerContent);
                    holder.limit.setText(headerHtml);

                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            holder.limit.setGravity(Gravity.RIGHT);
                        } else {
                            headerContent = "<br>Exp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        }
                        Spanned headerHtml2 = Html.fromHtml(headerContent);

                        holder.limit.setText(headerHtml2);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                    }
                    ///////////////

                    if (product.getOfferDefinitionId() == 3 || product.getOfferDefinitionId() == 2 || product.getOfferDefinitionId() == 1 || product.getOfferDefinitionId() == 4) {
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    } else {
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    }

                    try {
                        if (product.getOfferDefinitionId() == 4) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum = Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")) {
                                //holder.tv_price.setText("Buy " + product.getRequiredQty());
                                //holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);

                            } else if (product.getRewardType().equalsIgnoreCase("3") && floatnum > 0) {
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            } else {
                                holder.tv_price.setText("Buy " + product.getRequiredQty());
                                holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " " + new DecimalFormat("##.##").format(coupon_discount) + "%OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                holder.tv_coupon_type_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                holder.tv_coupon_type_1.setTypeface(holder.tv_coupon_type_1.getTypeface(), Typeface.BOLD);
                                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.roboto_black);
                                holder.tv_coupon_type_1.setTypeface(typeface);
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);
                            }


                        } else if (product.getOfferDefinitionId() == 1) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")) {

                                holder.tv_coupon_type_1.setVisibility(View.GONE);
                                holder.liner_save.setVisibility(View.VISIBLE);
                                holder.liner_promo_price.setVisibility(View.VISIBLE);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                //holder.tv_saving.setText("$" + everyDayPrice);

                                float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                                String formattedString = String.format("%.02f", myFloatRegularPrice);
                                holder.tv_saving.setText("$" + formattedString);

                                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                                //holder.tv_promo_price.setText("$" + new DecimalFormat("##.##").format(adPrice));

                                float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                                holder.tv_promo_price.setText("$" + formattedAdPriceString);
                            } else {
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            }

                        } else if (product.getOfferDefinitionId() == 3) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        } else if (product.getOfferDefinitionId() == 2) {
                          /*  DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));*/
                        } else if (product.getOfferDefinitionId() == 5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            //holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    } catch (Exception e) {

                    }

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                           /* holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                         /*   holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");*/
                    }
                        /*if (product.getRequiresActivation().equalsIgnoreCase("false")){

                            holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                            holder.tv_deal_type.setText(product.getOfferTypeTagName());
                            holder.circular_layout.setVisibility(View.GONE);

                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ (product.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                            holder.tv_price.setText(result);
                            holder.tv_coupon_flag.setVisibility(View.GONE);
                        }else {
                            holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                        }*/
                }
                else if (product.getPrimaryOfferTypeId() == 1) {
                    if (product.getQuantity() == null) {
                        holder.add_item_flag.setText("+");
                    } else if (product.getQuantity().equalsIgnoreCase("0")) {
                        holder.add_item_flag.setText("+");

                    } else {
                        holder.add_item_flag.setText(product.getQuantity());
                    }
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length() == 0) {

                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "\nExp " + saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(product.getDescription());

                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);
                        /*try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));
                        } catch (Exception e) {

                        }*/

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                }
                else if (product.getPrimaryOfferTypeId() == 0) {
                    holder.item_layout_tile.setVisibility(View.GONE);

                        /*ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
                        params.height = 30;
                        holder.item_layout_tile.setLayoutParams(params);*/
                }
            }
            else {
                if (MainFwActivity.singleLable == true) {
                    MainFwActivity.couponTile = true;
                    MainFwActivity.singleLable = false;
                }
                if (product.getCouponID().equalsIgnoreCase("11111111")) {
                    if (MainFwActivity.couponTile == true && MainFwActivity.pdView == true) {
                        p = position + 1;
                        q = position + 2;
                        MainFwActivity.couponTile = false;
                        MainFwActivity.multiLable = true;
                        holder.item_layout_tile.setVisibility(View.GONE);
                    }
                }
                else if (product.getTileNumber().equalsIgnoreCase("998")) {
                    if (MainFwActivity.couponTile == true && MainFwActivity.pdView == true) {
                        r = position;
                        s = position + 1;
                        MainFwActivity.couponTile = false;
                    }
                }


                if (p == position && p != 0 && MainFwActivity.pdView == true) {

                    holder.additional_offers.setVisibility(View.VISIBLE);
                    holder.additional_offers.setText("Additional Offers");
                    //MainFwActivity.offerTitle.setText("Additional Offers");
                }
                else if (q == position && q != 0 && MainFwActivity.pdView == true) {
                    holder.additional_offers.setVisibility(View.VISIBLE);
                    holder.additional_offers.setText("");
                }
                else {
                    holder.additional_offers.setVisibility(View.GONE);
                    holder.linear_personal_ad_lable.setVisibility(View.GONE);
                    holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                }

                if (r == position && r != 0 && MainFwActivity.pdView == true) {
                    holder.additional_offers.setVisibility(View.VISIBLE);
                    holder.additional_offers.setText("Additional Offers");
                    //MainFwActivity.offerTitle.setText("Additional Offers");
                    //aditinoal offers
                }
                else if (s == position && s != 0 && MainFwActivity.pdView == true) {
                    holder.additional_offers.setVisibility(View.VISIBLE);
                    holder.additional_offers.setText("");
                }
                else {
                    Log.i("linear_multi", "test");
                    //holder.additional_offers.setVisibility(View.GONE);
                    holder.linear_personal_ad_lable.setVisibility(View.GONE);
                    holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                    //holder.additional_offers.setVisibility(View.GONE);
                }


                if (product.getPrimaryOfferTypeId() == 3) {
                    MainFwActivity.offerTitle.setText("Personal Ad");
                    if (position == 0 && MainFwActivity.pdView == true) {
                        holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                        holder.linear_personal_ad_lable.setVisibility(View.GONE);
                        holder.additional_offers.setVisibility(View.GONE);
                        holder.additional_offers.setText("Personal Ad");
                        //MainFwActivity.offerTitle.setText("Personal Ad");

                        //holder.tv_location.setText(appUtil.getPrefrence("StoreName"));
                    } else if (position == 1 && MainFwActivity.pdView == true) {
                        holder.additional_offers.setVisibility(View.GONE);
                        holder.linear_personal_ad_lable.setVisibility(View.GONE);

                        holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                        holder.tv_location_multi.setVisibility(View.GONE);
                        holder.tv_location_multi.setText(appUtil.getPrefrence("StoreName"));
                        Log.i("storeidd", "test");
                    } else {
                        holder.additional_offers.setVisibility(View.GONE);
                        holder.linear_personal_ad_lable.setVisibility(View.GONE);
                        holder.linear_multi_personal_ad_lable.setVisibility(View.GONE);
                    }


                    holder.limit.setGravity(Gravity.CENTER);
                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                        // getTokenkey();
                    } else {
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


                    }
                        /*String headerContent = "";
                        headerContent = "Exp "+saveDate;
                        holder.limit.setText("\n"+headerContent);*/
                    ///////////////////////////////////////////////////////

                    String headerContent = "";
                    if (product.getLimitPerTransection() > 1) {
                        headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    } else {
                        headerContent = "\nExp " + saveDate;
                        holder.limit.setText(headerContent);
                    }

                    if (product.getMinAmount() > 0) {
                        headerContent = "* WITH $" + product.getMinAmount() + " PURCHASE" + "\nLimit " + product.getLimitPerTransection() + ", Exp " + saveDate;
                    } else if (product.getRequiredQty() > 1) {
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "*Must Buy " + product.getRequiredQty() + " | Limit " + product.getLimitPerTransection() + "\nExp " + saveDate;
                            //String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + product.getCouponShortDescription());
                        } else {
                            headerContent = "*Must Buy " + product.getRequiredQty() + "\nExp " + saveDate;
                            //String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + product.getCouponShortDescription());
                        }
                    }
                    if (product.getLimitPerTransection() > 0) {
                        if (headerContent != "") {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = headerContent + "\nLimit : " + product.getLimitPerTransection();
                            }
                        } else {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            }
                        }

                        if (product.getRewardType().equalsIgnoreCase("3") && product.getPrimaryOfferTypeId() == 2) {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            holder.tv_price.setText(result);

                        } else if (product.getRewardType().equalsIgnoreCase("2") && product.getPrimaryOfferTypeId() == 2) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue) + "% OFF" + "<sup><small><small> *</small></small></sup>");
                                if (product.getOfferDefinitionId() == 3) {
                                    holder.tv_price.setText("FREE");
                                } else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                    String displayPrice = product.getDisplayPrice().toString();
                                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                    holder.tv_price.setText(result2);
                                }
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>") + "<sup><small><small> *</small></small></sup>");
                            holder.tv_price.setText(result);
                            if (product.getRequiredQty() > 1) {
                                holder.tv_price.setText(result2);
                            }
                        }

                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    holder.limit.setText(headerContent);

                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "\nLimit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            holder.limit.setGravity(Gravity.RIGHT);
                        } else {
                            headerContent = "\nExp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        }

                        holder.limit.setText(headerContent);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                    }

                    ///////////////////////////////////////////////
                    //holder.coupon_badge.setVisibility(View.GONE);
                    holder.tv_coupon_flag.setText("With MyFareway");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
                    String chars = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(product.getCouponShortDescription());
                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);
                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small><small> *</small></small></sup>");
                    if (product.getIsbadged().equalsIgnoreCase("True")){
                        holder.tv_price.setText(result2);
                    }else {
                        holder.tv_price.setText(result);
                    }

                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    //holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    //holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));
                    //holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);

                    holder.liner_promo_price.setVisibility(View.VISIBLE);
                    holder.tv_promo_price__pri_fix.setText("Promo Price ");
                    float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                    String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                    holder.tv_promo_price.setText("$" + formattedAdPriceString);
                    Log.i("ad", product.getAdPrice());
                    Log.i("re", product.getRegularPrice());
                    if (formattedString.equalsIgnoreCase(formattedAdPriceString)) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                    if (formattedAdPriceString.equalsIgnoreCase("0.00")) {
                        holder.liner_promo_price.setVisibility(View.GONE);
                    }
                        /*try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                        } catch (Exception e) {

                        }*/
                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }
                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                            /*holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/

                    } else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                          /*  holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");*/
                    }


                }
                else if (product.getPrimaryOfferTypeId() == 2) {
                        /*if (product.getTileNumber().equalsIgnoreCase("999")||product.getTileNumber().equalsIgnoreCase("999")){
                            MainFwActivity.offerTitle.setText("Additional Offers");
                        }else if(p>position) {
                            MainFwActivity.offerTitle.setText("Additional Offers");
                        }*/
                    Log.i("valuepadd", String.valueOf(p));
                    Log.i("valueadd", String.valueOf(p - 5));
                    Log.i("valueradd", String.valueOf(r));
                    Log.i("valuermin", String.valueOf(r + 5));
                    if ((position) >= (p + 4) && product.getTileNumber().equalsIgnoreCase("999") && r == 0) {
                        if (p != 0) {
                            MainFwActivity.offerTitle.setText("Additional Offers");
                        }

                    } else if ((position) >= (r + 4) && product.getTileNumber().equalsIgnoreCase("999")) {
                        if (r != 0) {
                            MainFwActivity.offerTitle.setText("Additional Offers");
                        }

                    }

                    holder.liner_save.setVisibility(View.GONE);
                    holder.liner_promo_price.setVisibility(View.GONE);
                    holder.tv_coupon_type_1.setVisibility(View.GONE);

                    String saveDate = product.getValidityEndDate();
                    if (saveDate.length() == 0) {
                    } else {
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


                    }
                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                    }
                    String charsCouponShort = capitalize(product.getCouponShortDescription());
                    holder.tv_detail.setText(charsCouponShort);

                    holder.tv_coupon_flag.setText("With Coupon");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());
//////////////////////////////////////
                    String headerContent = "";
                    if (product.getLimitPerTransection() > 1) {
                        headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    } else {
                        headerContent = "<br>Exp " + saveDate;
                        holder.limit.setText(headerContent);
                    }

                    if (product.getMinAmount() > 0||product.getOfferDefinitionId() == 8 ||product.getOfferDefinitionId() == 9) {
                        headerContent = "<strong>* With $" + product.getMinAmount() + " Purchase " + "</strong><br><span>Exp " + saveDate + "</span>";
                        String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                        holder.tv_detail.setText("*" + charsStarCouponShort);
                        //headerContent = "* WITH $" + product.getMinAmount() + " PURCHASE" + "\nLimit " + product.getLimitPerTransection() + ", Exp " + saveDate;
                    } else if (product.getRequiredQty() > 1) {
                        if (product.getLimitPerTransection() > 1) {
                            //<strong>must</strong></br><span>test</span>
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + " | Limit " + product.getLimitPerTransection() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        } else {
                            headerContent = "<strong>*Must Buy " + product.getRequiredQty() + "</strong><br><span>Exp " + saveDate + "</span>";
                            String charsStarCouponShort = capitalize(product.getCouponShortDescription());
                            holder.tv_detail.setText("*" + charsStarCouponShort);
                        }
                    }
                    if (product.getLimitPerTransection() > 0) {
                        if (headerContent != "") {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = headerContent + "<br>Limit : " + product.getLimitPerTransection();
                            }
                        } else {
                            if (product.getLimitPerTransection() > 1) {
                                headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            }
                        }

                        if (product.getRewardType().equalsIgnoreCase("3") && product.getPrimaryOfferTypeId() == 2) {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            holder.tv_price.setText(result);

                        }
                        else if (product.getRewardType().equalsIgnoreCase("2") && product.getPrimaryOfferTypeId() == 2) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            try {
                                Number rewardValue = dF.parse(product.getRewardValue());
                                Spanned result = Html.fromHtml(new DecimalFormat("##.##").format(rewardValue) + "% OFF" + "<sup><small><small> *</small></small></sup>");
                                if (product.getOfferDefinitionId() == 3) {
                                    holder.tv_price.setText("FREE");
                                } else {
                                        /*Log.i("test", String.valueOf(product.getOfferDefinitionId()));
                                        holder.tv_price.setText(result);*/
                                    String displayPrice = product.getDisplayPrice().toString();
                                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";

                                    Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                    holder.tv_price.setText(result2);
                                }
                                holder.tv_saving_pri_fix.setText("");
                                holder.tv_saving.setText("");

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            String displayPrice = product.getDisplayPrice().toString();
                            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";


                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            //Spanned result = Html.fromHtml("<sup><small><small>$</small></small></sup>1<sup><small><small>50</small></small></sup><sup><small><small>OFF</small></small></sup>");
                            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>") + "<sup><small><small> *</small></small></sup>");
                            holder.tv_price.setText(result);
                            if (product.getRequiredQty() > 1||product.getMinAmount() > 0) {
                                holder.tv_price.setText(result2);
                            }
                        }

                    }

                    holder.coupon_badge.setVisibility(View.VISIBLE);
                    holder.limit.setGravity(Gravity.CENTER);
                    Spanned headerHtml = Html.fromHtml(headerContent);
                    holder.limit.setText(headerHtml);

                    if (product.getIsbadged().equalsIgnoreCase("True")) {
                        Picasso.get().load(product.getBadgeFileName()).into(holder.coupon_badge);
                        if (product.getLimitPerTransection() > 1) {
                            headerContent = "<br>Limit " + String.valueOf(product.getLimitPerTransection()) + ", Exp " + saveDate;
                            holder.limit.setGravity(Gravity.RIGHT);
                        } else {
                            headerContent = "<br>Exp " + saveDate;
                            holder.limit.setGravity(Gravity.CENTER);
                        }
                        Spanned headerHtml2 = Html.fromHtml(headerContent);

                        holder.limit.setText(headerHtml2);


                    } else {
                        Picasso.get().load(R.color.white).into(holder.coupon_badge);
                            /*String chars = capitalize(product.getDescription());
                            holder.tv_detail.setText(chars);*/
                    }
                    //

                    if (product.getOfferDefinitionId() == 3 || product.getOfferDefinitionId() == 2 || product.getOfferDefinitionId() == 1 || product.getOfferDefinitionId() == 4) {
                        String chars = capitalize(product.getDescription());
                        //holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    }
                    else {
                        String chars = capitalize(product.getDescription());
                        //holder.tv_detail.setText(chars);
                        if (product.getSavings().equalsIgnoreCase("0.0000") || product.getSavings().equalsIgnoreCase("0")) {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        } else {
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            holder.tv_coupon_type_1.setVisibility(View.GONE);
                        }
                    }

                    try {
                        if (product.getOfferDefinitionId() == 4) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number reward_value = dF.parse(product.getRewardValue());
                            DecimalFormat dF2 = new DecimalFormat("00.00");
                            Number coupon_discount = dF.parse(product.getCouponDiscount());
                            DecimalFormat Dfinal = new DecimalFormat("00.00");
                            Number finalprice = Dfinal.parse(product.getFinalPrice());
                            Float floatnum = Float.valueOf(new DecimalFormat("##.##").format(finalprice));

                            if (product.getRewardType().equalsIgnoreCase("1")) {
                                //holder.tv_price.setText("Buy " + product.getRequiredQty());
                                //holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);

                            } else if (product.getRewardType().equalsIgnoreCase("3") && floatnum > 0) {
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            } else {
                                holder.tv_price.setText("Buy " + product.getRequiredQty());
                                holder.tv_coupon_type_1.setText("Get " + product.getRewardQty() + " " + new DecimalFormat("##.##").format(coupon_discount) + "%OFF*");
                                holder.tv_coupon_type_1.setTextColor(mContext.getResources().getColor(R.color.red));
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.VISIBLE);
                            }


                        }
                        else if (product.getOfferDefinitionId() == 1) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number everyDayPrice = dF.parse(product.getRegularPrice());
                            if (product.getIsbadged().equalsIgnoreCase("True")) {

                                holder.tv_coupon_type_1.setVisibility(View.GONE);
                                holder.liner_save.setVisibility(View.VISIBLE);
                                holder.liner_promo_price.setVisibility(View.VISIBLE);

                                holder.tv_saving_pri_fix.setText("Everyday Price ");
                                //holder.tv_saving.setText("$" + everyDayPrice);

                                float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                                String formattedString = String.format("%.02f", myFloatRegularPrice);
                                holder.tv_saving.setText("$" + formattedString);

                                holder.tv_promo_price__pri_fix.setText("Promo Price ");
                                //holder.tv_promo_price.setText("$" + new DecimalFormat("##.##").format(adPrice));

                                float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                                String formattedAdPriceString = String.format("%.02f", myFloatAdPrice);
                                holder.tv_promo_price.setText("$" + formattedAdPriceString);
                            } else {
                                holder.liner_save.setVisibility(View.GONE);
                                holder.liner_promo_price.setVisibility(View.GONE);
                                holder.tv_coupon_type_1.setVisibility(View.GONE);

                            }

                        }
                        else if (product.getOfferDefinitionId() == 3) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number adPrice = dF.parse(product.getAdPrice());
                            Number couponDiscount = dF.parse(product.getCouponDiscount());
                           /* holder.tv_saving.setText("Ad Price " + new DecimalFormat("##.##").format(adPrice)+"\nDigital Coupon $"+couponDiscount+" Off");
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));
                            holder.liner_save.setBackgroundColor(mContext.getResources().getColor(R.color.white));*/
                        }
                        else if (product.getOfferDefinitionId() == 2) {
                          /*  DecimalFormat dF = new DecimalFormat("00.00");
                            Number regularPrice = dF.parse(product.getRegularPrice());
                            Number savings = dF.parse(product.getSavings());
                            holder.tv_saving.setText("Ad Price $" + new DecimalFormat("##.##").format(regularPrice)+"\nSAVINGS $"+savings+" ON "+product.getRequiredQty());
                            holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.black));*/
                        }
                        else if (product.getOfferDefinitionId() == 5) {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number savings = dF.parse(product.getSavings());
                            holder.liner_save.setVisibility(View.GONE);
                            holder.liner_promo_price.setVisibility(View.GONE);
                            //holder.tv_detail.setText(product.getoCouponShortDescription());
                        }

                    }
                    catch (Exception e) {

                    }

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    }
                    else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }

                    if (product.getClickCount() > 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activated));
                           /* holder.imv_status.setVisibility(View.VISIBLE);
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            holder.tv_status.setText("Activated");*/

                    }
                    else if (product.getClickCount() == 0) {
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.activate));
                          /*  holder.imv_status.setVisibility(View.GONE);
                            holder.tv_status.setText("Activate");*/
                    }

                        /*if (product.getRequiresActivation().equalsIgnoreCase("false")){

                            holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                            holder.tv_deal_type.setText(product.getOfferTypeTagName());
                            holder.circular_layout.setVisibility(View.GONE);

                            String displayPrice=product.getDisplayPrice().toString();
                            if(product.getDisplayPrice().toString().split("\\.").length>1)
                                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ (product.getDisplayPrice().split("\\.")[1]).replace("<sup>","").replace("</sup>","")+"</sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
                            holder.tv_price.setText(result);
                            holder.tv_coupon_flag.setVisibility(View.GONE);
                        }else {
                            holder.tv_coupon_flag.setVisibility(View.VISIBLE);
                        }*/

                }
                else if (product.getPrimaryOfferTypeId() == 1) {
                    Log.i("valuep", String.valueOf(p));
                    Log.i("valuemin", String.valueOf(p - 5));
                    if ((position) <= (p - 4)) {

                        if (p != 0) {
                            MainFwActivity.offerTitle.setText("Personal Ad");
                        }
                    } else if ((position) <= (r - 4)) {

                        if (r != 0) {
                            MainFwActivity.offerTitle.setText("Personal Ad");
                        }
                    }
                    //MainFwActivity.offerTitle.setText("Personal Ad");

                    if (product.getQuantity() == null) {
                        holder.add_item_flag.setText("+");
                    } else if (product.getQuantity().equalsIgnoreCase("0")) {
                        holder.add_item_flag.setText("+");

                    } else {
                        holder.add_item_flag.setText(product.getQuantity());
                    }
                    holder.tv_promo_price__pri_fix.setText("");
                    holder.tv_promo_price.setText("");
                    holder.circular_layout.setVisibility(View.GONE);
                    String saveDate = product.getValidityEndDate();
                    holder.limit.setGravity(Gravity.CENTER);
                    if (saveDate.length() == 0) {

                    } else {
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


                    }
                    String headerContent = "";
                    headerContent = "\nExp " + saveDate;
                    holder.limit.setText(headerContent);

                    holder.coupon_badge.setVisibility(View.GONE);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                  /*  holder.tv_saving.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.liner_save.setBackground(mContext.getResources().getDrawable(R.drawable.red_strip));*/

                    holder.tv_coupon_flag.setText("");
                    String charsUnit = lowercase(product.getPackagingSize());
                    holder.tv_unit.setText(charsUnit);
                    //holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(product.getDescription());

                    // old display price
                    String displayPrice = product.getDisplayPrice().toString();
                    if (product.getDisplayPrice().toString().split("\\.").length > 1)
                        displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + (product.getDisplayPrice().split("\\.")[1]).replace("<sup>", "").replace("</sup>", "") + "</sup>";
                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                    holder.tv_price.setText(result);

                    /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);*/

                    holder.tv_saving_pri_fix.setText("Everyday Price ");
                    float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                    String formattedString = String.format("%.02f", myFloatRegularPrice);
                    holder.tv_saving.setText("$" + formattedString);
                        /*try {
                            DecimalFormat dF = new DecimalFormat("00.00");
                            Number num = dF.parse(product.getRegularPrice());
                            holder.tv_saving_pri_fix.setText("Everyday Price ");
                            holder.tv_saving.setText("$"+new DecimalFormat("##.##").format(num));

                        } catch (Exception e) {

                        }*/

                    if (product.getHasRelatedItems() == 1) {
                        if (product.getRelatedItemCount() > 1) {
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        } else {
                            //holder.tv_varieties.setVisibility(View.GONE);
                            holder.tv_varieties.setVisibility(View.VISIBLE);
                            //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                            holder.tv_varieties.setText("");
                        }
                    } else if (product.getHasRelatedItems() == 0) {
                        //holder.tv_varieties.setVisibility(View.INVISIBLE);
                        //holder.tv_varieties.setVisibility(View.GONE);
                        holder.tv_varieties.setVisibility(View.VISIBLE);
                        //Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+"Add Items"+"</u>");
                        holder.tv_varieties.setText("");
                    }


                }
                else if (product.getPrimaryOfferTypeId() == 0 && MainFwActivity.savingsShort == false) {

                    holder.item_layout_tile.setVisibility(View.GONE);
                        /*
                        ViewGroup.LayoutParams params = holder.item_layout_tile.getLayoutParams();
                        params.height = 30;
                        holder.item_layout_tile.setLayoutParams(params);*/


                }

            }

            if (product.getOfferDefinitionId() == 5 || product.getOfferDefinitionId() == 8) {
                if (product.getCouponImageURl().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                    // Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else {
                    Picasso.get().load(product.getCouponImageURl()).into(holder.imv_item);
                }
            }
            else {
                if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else if (product.getLargeImagePath().equalsIgnoreCase("")) {
                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(holder.imv_item);
                } else {
                    Picasso.get().load(product.getLargeImagePath()).into(holder.imv_item);
                }
            }


        }





        //removing additional offer label
        holder.additional_offers.setVisibility(View.GONE);
        MainFwActivity.offerTitle.setText("Personal Ad");


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
        void onReset(Product product);
        void onChangeStore(Product product);

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


