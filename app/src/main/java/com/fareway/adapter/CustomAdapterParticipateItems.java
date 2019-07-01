package com.fareway.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fareway.R;
import com.fareway.activity.MainFwActivity;
import com.fareway.model.RelatedItem;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAdapterParticipateItems extends RecyclerView.Adapter<CustomAdapterParticipateItems.MyViewHolder> {

    private Context mContext;
    private List<RelatedItem> relatedItemsList;
    private CustomAdapterParticipateItemsListener listener;
    private CustomAdapterParticipateItemsListener listener2;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView limit,tv_status,tv_status_mul, tv_price, tv_unit, tv_saving,tv_deal_type,tv_detail,tv_varieties,tv_coupon_flag;
        private ImageView imv_item, imv_info, imv_more, imv_status,imv_status_mul;
        private LinearLayout circular_layout,circular_layout_mul, bottomLayout,remove_layout,remove_layout_mul,liner_save;
        private CardView card_view;
        private RelativeLayout imv_layout;
        //private Button all_Varieties_activate;

        public MyViewHolder(View view) {
            super(view);
            //all_Varieties_activate= (Button)view.findViewById(R.id.all_Varieties_activate);
            imv_layout = (RelativeLayout) view.findViewById(R.id.imv_layout);
            card_view=(CardView) view.findViewById(R.id.card_view);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_status_mul = (TextView) view.findViewById(R.id.tv_status_mul);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            tv_saving = (TextView) view.findViewById(R.id.tv_saving);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            tv_deal_type = (TextView) view.findViewById(R.id.tv_deal_type);
          /*  tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            add_plus = (TextView) view.findViewById(R.id.add_plus);
            add_minus = (TextView) view.findViewById(R.id.add_minus);*/
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
            limit = view.findViewById(R.id.limit);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRelatedItemSelected(relatedItemsList.get(getAdapterPosition()));
                }
            });
            circular_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onRelatedItemSelected2(relatedItemsList.get(getAdapterPosition()));
                    circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    tv_status.setText("Added");
                }
            });
            remove_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove_layout.setVisibility(View.GONE);
                    //listener2.onRelatedItemSelected2(relatedItemsList.get(getAdapterPosition()));
                    circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    tv_status.setText("Add");
                }
            });

        }
    }


    public CustomAdapterParticipateItems(Context mContext, List<RelatedItem> ProductList,CustomAdapterParticipateItemsListener listener,
                                         CustomAdapterParticipateItemsListener listener2) {
        this.mContext = mContext;
        this.relatedItemsList = ProductList;
        this.listener = listener;
        this.listener2 = listener2;
    }

    @Override
    public CustomAdapterParticipateItems.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new CustomAdapterParticipateItems.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterParticipateItems.MyViewHolder holder, int position) {

        final RelatedItem relatedItem = relatedItemsList.get(position);

        if(MainFwActivity.singleView) {
            holder.circular_layout_mul.setVisibility(View.INVISIBLE);
            holder.remove_layout_mul.setVisibility(View.INVISIBLE);
            holder.circular_layout.setVisibility(View.VISIBLE);
            holder.remove_layout.setVisibility(View.VISIBLE);
            holder.tv_varieties.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_coupon_flag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
            holder.tv_unit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.tv_saving.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.tv_deal_type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
            //holder.tv_remove.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.tv_status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.limit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            holder.tv_unit.setText(relatedItem.getPackagingSize());
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

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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

                /*Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);*/
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status.setText("Added");
                   // holder.count_product_number.setVisibility(View.VISIBLE);
                    holder.remove_layout.setVisibility(View.VISIBLE);
                   // holder.tv_quantity.setText(relatedItem.getQuantity());
                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Add");
                   // holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout.setVisibility(View.GONE);
                }
                }else {
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Activate");
                   // holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout.setVisibility(View.GONE);
                }

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
                holder.liner_save.setVisibility(View.GONE);
                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout.setVisibility(View.GONE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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
            }else if (relatedItem.getPrimaryOfferTypeId() == 1) {
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
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                    holder.tv_status.setText("Added");
                  //  holder.count_product_number.setVisibility(View.VISIBLE);
                    holder.remove_layout.setVisibility(View.VISIBLE);
                  //  holder.tv_quantity.setText(relatedItem.getQuantity());
                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status.setText("Add");
                  //  holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout.setVisibility(View.GONE);
                }
            }

            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(relatedItem.getLargeImagePath())
                        .into(holder.imv_item);
            }
        }else {

            //multi view code
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
            //holder.tv_remove_mul.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            holder.tv_unit.setText(relatedItem.getPackagingSize());
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
                holder.tv_coupon_flag.setText("With MyFareway");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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

                /*Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
                holder.tv_price.setText(result);*/
                holder.bottomLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mehrune));
                if (relatedItem.getClickCount()>0) {
                    if (relatedItem.getListCount()>0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tick));
                        holder.tv_status_mul.setText("Added");
                        // holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.VISIBLE);
                        // holder.tv_quantity.setText(relatedItem.getQuantity());
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                        // holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout_mul.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Activate");
                    // holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }

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
                // holder.liner_save.setVisibility(View.GONE);
                holder.tv_coupon_flag.setText("With Coupon");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.GONE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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
                        // holder.count_product_number.setVisibility(View.VISIBLE);
                        holder.remove_layout_mul.setVisibility(View.GONE);
                        ///  holder.tv_quantity.setText(relatedItem.getQuantity());
                    }else if (relatedItem.getListCount()==0){
                        holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        holder.tv_status_mul.setText("Add");
                        //  holder.count_product_number.setVisibility(View.GONE);
                        holder.remove_layout_mul.setVisibility(View.GONE);
                    }
                }else {
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Activate");
                    //  holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }
            }else if (relatedItem.getPrimaryOfferTypeId() == 1) {
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
                holder.tv_coupon_flag.setText("");
                holder.tv_deal_type.setText(relatedItem.getOfferTypeTagName());

                String chars = capitalize(relatedItem.getDescription());
                holder.tv_detail.setText(chars);

                holder.circular_layout_mul.setVisibility(View.VISIBLE);
                holder.tv_saving.setVisibility(View.VISIBLE);
                holder.tv_varieties.setVisibility(View.INVISIBLE);
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
                    //  holder.count_product_number.setVisibility(View.VISIBLE);
                    holder.remove_layout_mul.setVisibility(View.VISIBLE);
                    //  holder.tv_quantity.setText(relatedItem.getQuantity());
                }else if (relatedItem.getListCount()==0){
                    holder.circular_layout_mul.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                    holder.imv_status_mul.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                    holder.tv_status_mul.setText("Add");
                    //  holder.count_product_number.setVisibility(View.GONE);
                    holder.remove_layout_mul.setVisibility(View.GONE);
                }
            }

            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(mContext)
                        .load("https://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg")
                        .into(holder.imv_item);
            }else {
                Glide.with(mContext)
                        .load(relatedItem.getLargeImagePath())
                        .into(holder.imv_item);
            }
        }




    }

    @Override
    public int getItemCount() {
        return relatedItemsList.size();
    }

    public interface CustomAdapterParticipateItemsListener {
        void onRelatedItemSelected(RelatedItem relatedItem);
        void onRelatedItemSelected2(RelatedItem relatedItem);
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

