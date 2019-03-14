package com.fareway.adapter;

import android.content.Context;
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
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Product;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
        private TextView tv_status,tv_price,tv_unit,tv_saving,tv_detail,tv_deal_type,tv_varieties,limit,must;
        public ImageView imv_item, imv_info,imv_status;
        private LinearLayout circular_layout,bottomLayout,liner_save;
        private CardView card_view;
        private RelativeLayout imv_layout;
        public MyViewHolder(View view) {

            super(view);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            card_view=(CardView) view.findViewById(R.id.card_view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
            imv_item = (ImageView) view.findViewById(R.id.imv_item);
            tv_varieties = view.findViewById(R.id.tv_varieties);
            liner_save = (LinearLayout) view.findViewById(R.id.liner_save);
            limit = view.findViewById(R.id.limit);

            //  imv_more = (ImageView) view.findViewById(R.id.imv_more);
            imv_status = (ImageView) view.findViewById(R.id.imv_status);
            circular_layout= (LinearLayout) view.findViewById(R.id.circular_layout);
            bottomLayout= (LinearLayout) view.findViewById(R.id.bottomLayout);

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

            if (product.getInCircular()==0){
                Log.i("ifincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_unit.setText(product.getPackagingSize());
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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activate));
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add\nTo anshu");
                            }
                        }


                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText(mContext.getString(R.string.activate));
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo anshu");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));

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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }

                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.limit.setText("");
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
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add\nTo List");

                    }

                }
            }else {
                Log.i("elseincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
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

                        if (product.getListCount()>0){

                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activated));
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }

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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));

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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }
                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.limit.setText("");
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
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add\nTo List");

                    }

                }
            }

            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(product.getLargeImagePath())
                        .into(holder.imv_item);
            }

        }else {

            //multi view code
            if (product.getInCircular()==0){
                Log.i("ifincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.tv_unit.setText(product.getPackagingSize());
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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activate));
                            }else if (product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Add\nTo anshu");
                            }
                        }


                    }else if (product.getClickCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        if (product.getRequiresActivation().contains("True")){
                            holder.tv_status.setText(mContext.getString(R.string.activate));
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo anshu");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));

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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }

                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.limit.setText("");
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
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add\nTo List");

                    }

                }
            }else {
                Log.i("elseincircular", String.valueOf(product.getInCircular()));
                if (product.getPrimaryOfferTypeId() == 3) {
                    holder.limit.setText("");
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.tv_saving.setVisibility(TextView.VISIBLE);
                    holder.liner_save.setVisibility(View.VISIBLE);
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

                        if (product.getListCount()>0){

                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                            if (product.getRequiresActivation().contains("True")){
                                holder.tv_status.setText(mContext.getString(R.string.activated));
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }

                } else if (product.getPrimaryOfferTypeId() == 2) {
                    holder.liner_save.setVisibility(View.GONE);
                    holder.tv_unit.setText(product.getPackagingSize());
                    holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                    holder.tv_deal_type.setText(product.getOfferTypeTagName());

                    String chars = capitalize(product.getDescription());
                    holder.tv_detail.setText(chars);

                    Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                    holder.tv_price.setText(result);
                    holder.liner_save.setVisibility(View.VISIBLE);
                    try {
                        DecimalFormat dF = new DecimalFormat("00.00");
                        Number num = dF.parse(product.getSavings());
                        holder.tv_saving.setText("SAVE $ " + new DecimalFormat("##.##").format(num));

                    } catch (Exception e) {

                    }

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
                            }else if(product.getRequiresActivation().contains("False")){
                                holder.tv_status.setText("Added");
                            }
                        }else {
                            holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                            holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));

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
                        }else if (product.getRequiresActivation().contains("False")){
                            holder.tv_status.setText("Add\nTo List");
                        }

                    }
                }else if (product.getPrimaryOfferTypeId() == 1) {
                    holder.limit.setText("");
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
                    }else if (product.getListCount()==0){
                        holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status.setText("Add\nTo List");

                    }

                }
            }

            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(product.getLargeImagePath())
                        .into(holder.imv_item);
            }
        }

        holder.circular_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getClickCount()>0){
Log.i("test", String.valueOf(product.getClickCount()));
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status.setText("Added");
                    }else if (product.getRequiresActivation().contains("True")){
                        holder.tv_status.setText(mContext.getString(R.string.activated));
                    }else {
                        holder.tv_status.setText("Added");
                    }

                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());

                                       /* Intent i=new Intent(mContext,MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        mContext.startActivity(i);*/
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            //MainFwActivity.reLoadApi();
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            }
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                            //MainFwActivity.reLoadApi();
                                        }else if (product.getPrimaryOfferTypeId()==1){
                                            product.setListCount(1);
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                            //MainFwActivity.reLoadApi();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();


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
                                // params.put("grant_type", "password");
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

                            //this is the part, that adds the header to the request
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
                            // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();

//                displayAlert();
                    }
                }else if (product.getClickCount()==0){

                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    if (product.getPrimaryOfferTypeId()==1){
                        holder.tv_status.setText("Added");
                    }else if (product.getRequiresActivation().contains("True")){
                        holder.tv_status.setText(mContext.getString(R.string.activated));
                    }else {
                        holder.tv_status.setText("Added");
                    }

                    RequestQueue mQueue;
                    mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {

                                        Log.i("Fareway text", response.toString());
                                       /* Intent i=new Intent(mContext,MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        mContext.startActivity(i);*/
                                        if (product.getPrimaryOfferTypeId()==3){
                                            product.setClickCount(1);
                                            product.setListCount(1);
                                            //MainFwActivity.reLoadApi();
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        }else if (product.getPrimaryOfferTypeId()==2){
                                            product.setClickCount(1);
                                            if (product.getRequiresActivation().contains("False")){
                                                product.setListCount(1);
                                            }else {
                                                product.setListCount(1);
                                            }
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                            //MainFwActivity.reLoadApi();
                                        }else if (product.getPrimaryOfferTypeId()==1){
                                            product.setListCount(1);
                                            activate.SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                            //MainFwActivity.reLoadApi();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();


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
                                // params.put("grant_type", "password");
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

                            //this is the part, that adds the header to the request
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
                           // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();

//                displayAlert();
                    }
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


