package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;*/
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.fareway.adapter.ShoppingListAdapter;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingFw extends AppCompatActivity implements ShoppingListAdapter.ShoppingListAdapterListener {

    AppUtilFw appUtil;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    private Activity activity;
    private RequestQueue mQueue;
    private ArrayList<Shopping> shoppingArrayList;
    public static JSONArray shopping;
    public static JSONArray activatedOffer;
    private ShoppingListAdapter shoppingListAdapter;
    private static RecyclerView rv_shopping_list_items;
    private TextView tv_number_item;
   // TextView tv;
    private ImageView imv_all_delete,print,email;
    private TextView add_item;
    public static JSONArray shoppingId;
    Button shopping_list_fragment,activated_offer_fragment;
    LinearLayout linear_shopping_list_tab,linear_coupon_tab;
    public static   int z=0;
    private int count_activated_offer,count_shopping_list;

    Boolean IPValue;
    String IPaddress;
    String osName;
    String myVersion;
    String Latitude = "";
    String Longitude = "";
    int sdkVersion;
    double diagonalInches;
    String deviceType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_fw);
        activity=ShoppingFw.this;
        mQueue= FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);
        tv_number_item = findViewById(R.id.tv_number_item);

        getSupportActionBar().setTitle("Shopping List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        shoppingArrayList = new ArrayList<>();
        rv_shopping_list_items = (RecyclerView) findViewById(R.id.rv_shopping_list_items);
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingArrayList,this,this,this,this);
        RecyclerView.LayoutManager mLayoutManagerShoppingList = new LinearLayoutManager(activity);
        rv_shopping_list_items.setLayoutManager(mLayoutManagerShoppingList);
        rv_shopping_list_items.setAdapter(shoppingListAdapter);

        imv_all_delete = findViewById(R.id.imv_all_delete);

        imv_all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("remove","All");
                ViewRemoveAllDialog alert = new ViewRemoveAllDialog();
                alert.showDialog(activity, "Do you want to delete all the items from the shopping list?");
            }
        });

        add_item=findViewById(R.id.add_item);
        add_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(activity);
                View promptsView = li.inflate(R.layout.prompts_add_item, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        activity);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add My Items",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        addShoppingItem(userInput.getText().toString());
                                        linear_shopping_list_tab.setVisibility(View.VISIBLE);
                                        linear_coupon_tab.setVisibility(View.GONE);

                                        if (z==0){
                                            shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                                            shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                                            activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                                            activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                                            z=1;
                                            fetchShopping();
                                        }
                                        else {
                                            shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                                            shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                                            activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                                            activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                                            z=1;
                                        }
                                        //Log.i("text", String.valueOf(userInput.getText()));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        linear_shopping_list_tab=findViewById(R.id.linear_shopping_list_tab);
        linear_coupon_tab=findViewById(R.id.linear_coupon_tab);

        shopping_list_fragment=findViewById(R.id.shopping_list_fragment);
        shopping_list_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_shopping_list_tab.setVisibility(View.VISIBLE);
                linear_coupon_tab.setVisibility(View.GONE);

                if (z==0){
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=1;
                    fetchShopping();
                }else {
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=1;
                }

            }
        });
        activated_offer_fragment=findViewById(R.id.activated_offer_fragment);
        activated_offer_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_coupon_tab.setVisibility(View.VISIBLE);
                linear_shopping_list_tab.setVisibility(View.GONE);
                if (z==1){
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=0;
                    fetchActivatedOffer();
                }else {
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=0;
                    fetchActivatedOffer();
                }
                // rv_shopping_list_items.setVisibility(View.GONE);
            }
        });

        email=findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(activity);
                View promptsView = li.inflate(R.layout.prompts_send_email, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        activity);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Send",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        sendEmailShoppingList(userInput.getText().toString());
                                        //Log.i("text", String.valueOf(userInput.getText()));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        print=findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String urlString = "https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid="+appUtil.getPrefrence("ShopperID")+"&memberid="+appUtil.getPrefrence("MemberId");
                String urlString = Constant.PRINT_WEB_URL + "shopperid=" + appUtil.getPrefrence("ShopperID") + "&memberid=" + appUtil.getPrefrence("MemberId") + "&storename=" + appUtil.getPrefrence("StoreName");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed and open Kindle Browser
                    intent.setPackage("com.amazon.cloud9");
                    activity.startActivity(intent);
                }
            }
        });

        String saveDate = appUtil.getPrefrence(".expires");
        Log.i("saveDate", saveDate);
        if (saveDate != null) {
            if (saveDate.length() == 0) {
                Log.i("start date", saveDate + appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes"));
                //Toast.makeText(activity, "first time open", Toast.LENGTH_LONG).show();
                getTokenkey();
            } else {
                //Toast.makeText(activity, "second time open" + saveDate+appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes"), Toast.LENGTH_LONG).show();
                SimpleDateFormat spf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                Date newDate = null;
                try {
                    newDate = spf.parse(saveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String c = "dd MMM yyyy HH:mm:ss";
                //String c= "dd MMM yyyy";
                spf = new SimpleDateFormat(c);
                saveDate = spf.format(newDate);
                System.out.println("saveDate " + saveDate);

                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                //SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println("currentDate " + currentDate);
                appUtil.setPrefrence("comeFrom", "mpp");

                if (appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")==true) {
                    /*if (currentDate.compareTo(saveDate) < 0) {
                        shoppingListIdLoad();

                    } else {
                        getTokenkey();
                    }*/
                    getTokenkey2();

                } else {

                    if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                        getTokenkey();
                    } else {
                        alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                getString(R.string.ok), getString(R.string.alert));
                        alertDialog.show();
                    }


                }
            }
        } else {

            if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                getTokenkey();
            } else {
                alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                        getString(R.string.ok), getString(R.string.alert));
                alertDialog.show();
            }
        }

        osName = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        myVersion = android.os.Build.VERSION.RELEASE;
        sdkVersion = android.os.Build.VERSION.SDK_INT;
        NetwordDetect();

        Display display = ((Activity) activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        final double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));


    }

    public void shoppingListLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                /*progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();*/
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.ShoppingList+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("shoppingList Response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");

                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        try
                                        {
                                            shopping= root2.getJSONArray("ShoppingListItems");
                                        }
                                        catch (Exception ex)
                                        {
                                            shopping = null;
                                        }

                                        if (shopping==null ){
                                            Log.i("shopping","test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            //tv_number_item.setText(String.valueOf(0));
                                            //tv.setText(String.valueOf(0));
                                            activatedOffersListIdLoad();

                                        }else {
                                            Log.i("shopping", String.valueOf(shopping));

                                            for (int i = 0; i < shopping.length(); i++) {
                                            }
                                            //tv_number_item.setText(String.valueOf(shopping.length()));
                                            //tv.setText(String.valueOf(shopping.length()));

                                            /*shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();*/
                                            activatedOffersListIdLoad();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("shoppingListLoadShoppingFW", e.getLocalizedMessage());
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("shoppingListLoadShoppingFW", String.valueOf(error.networkResponse.statusCode));
                          progressDialog.dismiss();
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
                        // params.put("UserName", et_email.getText().toString().trim());
                        // params.put("password", et_pwd.getText().toString().trim());
                        //params.put("Device", "5");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                 progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void activatedOffersListIdLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + "ShoopingList/ActivatedCoupons?MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=2",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");

                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();

                                        try
                                        {
                                            activatedOffer= root2.getJSONArray("WCouponsDetails");
                                        }
                                        catch (Exception ex)
                                        {
                                            activatedOffer = null;
                                        }

                                        if (activatedOffer==null ){

                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            // tv.setText(String.valueOf(0));

                                        }else {
                                            for (int i = 0; i < activatedOffer.length(); i++) {
                                            }
                                            tv_number_item.setText(String.valueOf(activatedOffer.length()));
                                            // tv.setText(String.valueOf(shopping.length()));

                                         /* shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged(); */
                                            fetchActivatedOffer();
                                            //fetchShopping();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("activatedOffersListIdLoadShoppingFW", e.getLocalizedMessage());
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("activatedOffersListIdLoadShoppingFW", String.valueOf(error.networkResponse.statusCode));
                        progressDialog.dismiss();
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
                        // params.put("UserName", et_email.getText().toString().trim());
                        // params.put("password", et_pwd.getText().toString().trim());
                        //params.put("Device", "5");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchShopping(){
        // Log.i("shopping", String.valueOf(shopping.length()));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
        z=1;


        if (shopping == null) {
            //no students
            tv_number_item.setText(String.valueOf("0"));
            shoppingArrayList.clear();
        }else {
            tv_number_item.setText(String.valueOf(shopping.length()));
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onShoppingItemSelected(final Shopping shopping) {
        //shoppingListLoad();
        Log.i("remove","remove");
        if (shopping.getPrimaryOfferTypeId()==0){
            removeSingleOwnItem(shopping.getShoppingListItemID());
        }else{
            String url = Constant.WEB_URL+Constant.SHOPPINGLISTSINGAL+shopping.getShoppingListItemID()+"&MemberId="+appUtil.getPrefrence("MemberId");
            StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                    new Response.Listener<String >() {
                        @Override
                        public void onResponse(String  response) {
                            Log.i("success", String.valueOf(response));
                            try {
                                fetchShoppingListLoad();
                            }catch (Exception e){
                                saveErrorLog("onShoppingItemSelectedShoppingFW", e.getLocalizedMessage());

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    saveErrorLog("onShoppingItemSelectedShoppingFW", String.valueOf(error.networkResponse.statusCode));
                    Log.i("fail", String.valueOf(error));
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded";
                }
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                    params.put("Content-Type", "application/x-www-form-urlencoded");
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
                saveErrorLog("onShoppingItemSelectedShoppingFW", e.getLocalizedMessage());
            }
        }




    }

    @Override
    public void onShoppingaddSelected(Shopping shopping) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        if (shopping.getPrimaryOfferTypeId()==0){
            String url = null;


            url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shopping.getShoppingListItemID()+"&Quantity="+(Integer.parseInt(shopping.getQuantity())+1);

            //url ="https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=505&Quantity=3"

            StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                    new Response.Listener<String >() {
                        @Override
                        public void onResponse(String  response) {
                            Log.i("success", String.valueOf(response));
                            try {
                                fetchShoppingListLoad();
                                progressDialog.dismiss();
                            }catch (Exception e){
                                saveErrorLog("onShoppingaddSelectedShoppingFW", e.getLocalizedMessage());
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("fail", String.valueOf(error));
                    saveErrorLog("onShoppingaddSelectedShoppingFW", String.valueOf(error.networkResponse.statusCode));
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("grant_type", "password");
                    return params;
                }
                //this is the part, that adds the header to the request
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
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
                saveErrorLog("onShoppingaddSelectedShoppingFW", e.getLocalizedMessage());
            }

        }

        else {
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
            String currentDate = dateformat2.format(c2.getTime());
            System.out.println(currentDate);
            JSONObject ShoppingListItems = new JSONObject();
            try {
                ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
                ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())+1));
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
            //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
            String url = "";
            Log.i("testobject",mRequestBody);
            url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE+"?MemberId="+appUtil.getPrefrence("MemberId")+"&UPC="+shopping.getDisplayUPC().replace("UPC","").replace(":","").replace(" ","")+"&Quantity="+(Integer.parseInt(shopping.getQuantity()) + 1)+"&DateAddedOn="+currentDate;

            try {
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                try {
                                    fetchShoppingListLoad();
                                    progressDialog.dismiss();
                                }catch (Exception e){
                                    saveErrorLog("onShoppingaddSelectedShoppingFW", e.getLocalizedMessage());
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        saveErrorLog("onShoppingaddSelectedShoppingFW", String.valueOf(error.networkResponse.statusCode));
                    }
                }){

                    /*@Override
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
         }*/
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
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
                    saveErrorLog("onShoppingaddSelectedShoppingFW", e.getLocalizedMessage());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                saveErrorLog("onShoppingaddSelectedShoppingFW", e.getLocalizedMessage());
            }

        }
    }

    @Override
    public void onShoppingsubSelected(Shopping shopping) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        if (shopping.getPrimaryOfferTypeId()==0){
            if (Integer.parseInt(shopping.getQuantity())>1){

                String url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shopping.getShoppingListItemID()+"&Quantity="+(Integer.parseInt(shopping.getQuantity())+1);

                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                try {
                                    fetchShoppingListLoad();
                                    progressDialog.dismiss();
                                }catch (Exception e){
                                    saveErrorLog("onShoppingsubSelectedShoppingFW", e.getLocalizedMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        saveErrorLog("onShoppingsubSelectedShoppingFW", String.valueOf(error.networkResponse.statusCode));
                    }
                }){

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("grant_type", "password");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
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
                    saveErrorLog("onShoppingsubSelectedShoppingFW", e.getLocalizedMessage());
                }
            }
            else {
                progressDialog.dismiss();
            }
        }
        else {
            if (Integer.parseInt(shopping.getQuantity())>1){
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
                    ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())-1));
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
                String  url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE+"?MemberId="+appUtil.getPrefrence("MemberId")+"&UPC="+shopping.getDisplayUPC().replace("UPC","").replace(":","").replace(" ","")+"&Quantity="+(Integer.parseInt(shopping.getQuantity()) - 1)+"&DateAddedOn="+currentDate;
                try {
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("success", String.valueOf(response));
                                    try {
                                        fetchShoppingListLoad();
                                        progressDialog.dismiss();
                                    }catch (Exception e){
                                        saveErrorLog("onShoppingsubSelectedShoppingFW", e.getLocalizedMessage());
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                            saveErrorLog("onShoppingsubSelectedShoppingFW", String.valueOf(error.networkResponse.statusCode));
                        }
                    }){

                        /*@Override
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
        }*/
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }
                        //this is the part, that adds the header to the request
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
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
                        saveErrorLog("onShoppingsubSelectedShoppingFW", e.getLocalizedMessage());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    saveErrorLog("onShoppingsubSelectedShoppingFW", e.getLocalizedMessage());
                }

            }
            else {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onShoppingDetailSelected(Shopping shopping) {

    }

    public void fetchShoppingListLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.ShoppingList+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        Log.i("anshuman","test");

                                        try
                                        {
                                            shopping= root2.getJSONArray("ShoppingListItems");
                                        }
                                        catch (Exception ex)
                                        {
                                            shopping = null;
                                        }

                                        if (shopping==null ){
                                            Log.i("anshuman","test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));


                                        }else {
                                            Log.i("shopping", String.valueOf(shopping));

                                            for (int i = 0; i < shopping.length(); i++) {
                                            }
                                            tv_number_item.setText(String.valueOf(shopping.length()));
                                            shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("fetchShoppingListLoadShoppingFW", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("fetchShoppingListLoadShoppingFW", String.valueOf(error.networkResponse.statusCode));
                        //progressDialog.dismiss();
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
                        // params.put("UserName", et_email.getText().toString().trim());
                        // params.put("password", et_pwd.getText().toString().trim());
                        //params.put("Device", "5");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    saveErrorLog("fetchShoppingListLoadShoppingFW", e.getLocalizedMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                saveErrorLog("fetchShoppingListLoadShoppingFW", e.getLocalizedMessage());
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getTokenkey() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                 progressDialog = new ProgressDialog(activity);
                 progressDialog.setMessage("Processing");
                 progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.GET_TOKEN,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.i("Fareway text", response.toString());
                                    JSONObject jsonParam = new JSONObject(response.toString());
                                    appUtil.setPrefrence("access_token", jsonParam.getString("access_token"));
                                    appUtil.setPrefrence("token_type", jsonParam.getString("token_type"));
                                    appUtil.setPrefrence("expires_in", jsonParam.getString("expires_in"));
                                    appUtil.setPrefrence(".issued", jsonParam.getString(".issued"));
                                    appUtil.setPrefrence(".expires", jsonParam.getString(".expires"));
                                    //progressDialog.dismiss();
                                  //  Intent i = new Intent(activity, LoginFw.class);
                                  //  startActivity(i);
                                 //   finish();
                                   // shoppingListLoad();
                                    //shoppingListIdLoad();
                                    login();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkeyShoppingFW", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("getTokenkeyShoppingFW", String.valueOf(error.networkResponse.statusCode));
                         progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            //  progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog=userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok),"Fail");
                                alertDialog.show();

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
                        params.put("grant_type", "password");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        params.put("username", "imemine@usa.com");
                        params.put("password", "123456");
                        params.put("ClientID", "1");
//                        params.put("Content-Type", "application/json ;charset=utf-8");
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
                    //  FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    saveErrorLog("getTokenkeyShoppingFW", e.getLocalizedMessage());
                }

            } catch (Exception e) {

                e.printStackTrace();
                saveErrorLog("getTokenkeyShoppingFW", e.getLocalizedMessage());
                  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public class ViewRemoveAllDialog {

        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.all_remove_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.dialog_info);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialog_ok);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = Constant.WEB_URL+"ShoopingList/ShoppingListByTYC?shoppinglistid="+appUtil.getPrefrence("ShoppingListId")+"&MemberId="+appUtil.getPrefrence("MemberId");
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("ViewRemoveAllDialog", String.valueOf(response));
                                    try {
                                        activatedOffer=null;
                                        shoppingArrayList.clear();
                                        shoppingListAdapter.notifyDataSetChanged();
                                    }catch (Exception e){
                                        saveErrorLog("ViewRemoveAllDialogShoppingFW", e.getLocalizedMessage());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                            // messageLoad();
                            activatedOffer=null;
                            shoppingArrayList.clear();
                            shoppingListAdapter.notifyDataSetChanged();
                            removeOwnItem();
                            saveErrorLog("ViewRemoveAllDialogShoppingFW", String.valueOf(error.networkResponse.statusCode));
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
                        saveErrorLog("ViewRemoveAllDialogShoppingFW", e.getLocalizedMessage());
                    }
                    dialog.dismiss();
                }
            });

            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialog_cancel);
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    public void removeOwnItem(){
        String url = Constant.WEB_URL+Constant.REMOVESHOPPINGOWMITEM+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("removeOwnItemsuccess", String.valueOf(response));
                        try {
                            shoppingListLoad();
                        }catch (Exception e){
                            saveErrorLog("removeOwnItemShoppingFW", e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
                saveErrorLog("removeOwnItemShoppingFW", String.valueOf(error.networkResponse.statusCode));
                //messageLoad();
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
            saveErrorLog("removeOwnItemShoppingFW", e.getLocalizedMessage());
        }
        //dialog.dismiss();

    }

    public void removeSingleOwnItem(String shoppingListItemID){
        Log.i("remove","remove");
        //https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=404
        String url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shoppingListItemID+"&MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        try {
                            fetchShoppingListLoad();
                        }catch (Exception e){
                            saveErrorLog("removeSingleOwnItemShoppingFW", e.getLocalizedMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //removeSingleOwnItem(shopping.getShoppingListItemID());
                Log.i("fail", String.valueOf(error));
                saveErrorLog("removeSingleOwnItemShoppingFW", String.valueOf(error.networkResponse.statusCode));
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
            saveErrorLog("removeSingleOwnItemShoppingFW", e.getLocalizedMessage());
        }
    }

    public void withEditText() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Multiple Items");

        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Add My Items", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                addShoppingItem(input.getText().toString());
            }
        });
        builder.show();
    }

    private void addShoppingItem(final String itemName) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ADDSHOPPINGITEM,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    progressDialog.dismiss();
                                    fetchShoppingListLoad();
                                }catch (Exception e){
                                    saveErrorLog("addShoppingItemShoppingFW", e.getLocalizedMessage());
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("addShoppingItemShoppingFW", String.valueOf(error.networkResponse.statusCode));
                        progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog=userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok),"Fail");
                                alertDialog.show();

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


                        params.put("MyOwnItem", itemName);
                        params.put("MemberId", appUtil.getPrefrence("MemberId"));
                        //params.put("UserName", appUtil.getPrefrence("Email"));
                        //params.put("password", appUtil.getPrefrence("Password"));
                        //test
                        params.put("Device", "5");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    saveErrorLog("addShoppingItemShoppingFW", e.getLocalizedMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
                saveErrorLog("addShoppingItemShoppingFW", e.getLocalizedMessage());
                progressDialog.dismiss();
            }

        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void shoppingListIdLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + "ShoppingList/List?"+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("ShoppingListId", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){


                                        try
                                        {
                                            shoppingId= root2.getJSONArray("ListName");
                                        }
                                        catch (Exception ex)
                                        {
                                            shoppingId = null;
                                        }

                                        if (shoppingId == null) {
                                            Log.i("shoppingId1", "test");

                                        } else {
                                            Log.i("shoppingId2", String.valueOf(shoppingId));

                                            for (int i = 0; i < shoppingId.length(); i++) {
                                                JSONObject jsonParam = shoppingId.getJSONObject(i);
                                                if (jsonParam.getInt("IsActive") == 1) {
                                                    appUtil.setPrefrence("ShoppingListId", jsonParam.getString("ShoppingListId"));
                                                    Log.i("ShoppingListId", appUtil.getPrefrence("ShoppingListId"));
                                                    shoppingListLoad();
                                                }


                                            }
                                           /*   tv_number_item.setText(String.valueOf(shopping.length()));
                                            tv.setText(String.valueOf(shopping.length()));

                                            shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();*/
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("shoppingListIdLoadShoppingFW", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("shoppingListIdLoadShoppingFW", String.valueOf(error.networkResponse.statusCode));
                         progressDialog.dismiss();
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
                        // params.put("UserName", et_email.getText().toString().trim());
                        // params.put("password", et_pwd.getText().toString().trim());
                        //params.put("Device", "5");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchActivatedOffer(){
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
        z=0;
        if (activatedOffer == null) {
            tv_number_item.setText(String.valueOf("0"));
            shoppingArrayList.clear();
            //no students
        }else {
            tv_number_item.setText(String.valueOf(activatedOffer.length()));
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }
/*
        ArrayList<Events> futureEvents = new ArrayList<>();
        Date currentDate = new Date();

        for(Events events : eventsList) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = null;
            try {
                date = formatter.parse(events.getEventDate() + " " + events.getEventTime());
            } catch (ParseException e) {

            }

            if(currentDate.before(date)) {
                futureEvents.add(events);
            }
        }

        adapter.filter(futureEvents);*/

    }

    private void sendEmailShoppingList(final String emails){
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL +"ShoopingList/EmailShoppingList",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    progressDialog.dismiss();
                                }catch (Exception e){
                                    saveErrorLog("sendEmailShoppingListShoppingFW", e.getLocalizedMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("sendEmailShoppingListShoppingFW", String.valueOf(error.networkResponse.statusCode));
                        progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog=userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok),"Fail");
                                alertDialog.show();

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


                        params.put("Emails", emails);
                        params.put("MemberId", appUtil.getPrefrence("MemberId"));
                        params.put("LoyaltyNumber", appUtil.getPrefrence("LoyaltyCard"));
                        params.put("ShopperID", appUtil.getPrefrence("ShopperID"));
                        //params.put("password", appUtil.getPrefrence("Password"));
                        //test
                        params.put("Device", "5");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    saveErrorLog("sendEmailShoppingListShoppingFW", e.getLocalizedMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
                saveErrorLog("sendEmailShoppingListShoppingFW", e.getLocalizedMessage());
                progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void login() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                // progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.LOGIN,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){

                                        JSONArray message= root.getJSONArray("message");
                                        for(int i=0;i<message.length();i++)
                                        {
                                            JSONObject jsonParam= message.getJSONObject(i);
                                            appUtil.setPrefrence("GeoStatus", jsonParam.getString("GeoStatus"));
                                            appUtil.setPrefrence("ZipCode", jsonParam.getString("ZipCode"));
                                            appUtil.setPrefrence("UserAccessToken", jsonParam.getString("UserAccessToken"));
                                            appUtil.setPrefrence("SecretQuestionID", jsonParam.getString("SecretQuestionID"));
                                            appUtil.setPrefrence("ErrorMessage", jsonParam.getString("ErrorMessage"));
                                            appUtil.setPrefrence("MemberId", jsonParam.getString("MemberId"));
                                            appUtil.setPrefrence("IsEmployee", jsonParam.getString("IsEmployee"));
                                            appUtil.setPrefrence("FName", jsonParam.getString("FName"));
                                            appUtil.setPrefrence("LName", jsonParam.getString("LName"));
                                            appUtil.setPrefrence("LoyaltyCard", jsonParam.getString("LoyaltyCard"));
                                            appUtil.setPrefrence("ActivaStatus", jsonParam.getString("ActivaStatus"));
                                            appUtil.setPrefrence("ShopperID", jsonParam.getString("ShopperID"));
                                            appUtil.setPrefrence("StoreId", jsonParam.getString("StoreId"));
                                            appUtil.setPrefrence("BackupStoreId", jsonParam.getString("StoreId"));
                                            appUtil.setPrefrence("StoreName", jsonParam.getString("storename"));

                                        }
                                        appUtil.setPrefrence("SaveLogin", "yes");

                                        appUtil.setPrefrence("isLogin", "yes");
                                        /*Intent i = new Intent(activity, MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        startActivity(i);
                                        finish();*/
                                        shoppingListIdLoad();

                                        Log.i("IPaddress",IPaddress);
                                        Log.i("osName",osName);
                                        Log.i("myVersion",myVersion);
                                        Log.i("sdkVersion", String.valueOf(sdkVersion));
                                        Log.i("diagonalInches", String.valueOf(diagonalInches));
                                        if (diagonalInches>=6.80){
                                            deviceType="5";
                                        }else {
                                            deviceType="2";
                                        }
                                        Log.i("deviceType", deviceType);
                                        try {
                                            Log.i("Latitude", Latitude);
                                            Log.i("Longitude", Longitude);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                            saveErrorLog("login", e.getLocalizedMessage());
                                        }
                                        saveLogin();

                                    }else if (root.getString("errorcode").equals("200")){
                                        //finish();
                                        saveErrorLog("loginShoppingFw", "200 "+root.getString("message"));
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("loginShoppingFw", e.getLocalizedMessage());
                                    finish();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("loginShoppingFw", String.valueOf(error.networkResponse.statusCode));
                        //  progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            //      progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog=userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok),"Fail");
                                alertDialog.show();

                            }
                        }
                        finish();
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
                        ///////
                        /*appUtil.setPrefrence("Latitude", "0.00");
                        appUtil.setPrefrence("Longitude", "0.00");
                        appUtil.setPrefrence("Email", "");
                        appUtil.setPrefrence("Password", "");*/
                        ///////
                        String charsLowerEmail =lowercase(appUtil.getPrefrence("Email"));
                        appUtil.setPrefrence("Email", charsLowerEmail);
                        params.put("UserName", charsLowerEmail);
                        params.put("password", appUtil.getPrefrence("Password"));

                        //test
                        params.put("Device", "5");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    saveErrorLog("loginShoppingFw", e.getLocalizedMessage());
                    finish();
                    e.printStackTrace();
                }

            } catch (Exception e) {
                saveErrorLog("loginShoppingFw", e.getLocalizedMessage());
                finish();
                e.printStackTrace();
                //  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            finish();
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getTokenkey2() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                 progressDialog = new ProgressDialog(activity);
                  progressDialog.setMessage("Processing");
                  progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.GET_TOKEN,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.i("Fareway text", response.toString());
                                    JSONObject jsonParam = new JSONObject(response.toString());
                                    appUtil.setPrefrence("access_token", jsonParam.getString("access_token"));
                                    appUtil.setPrefrence("token_type", jsonParam.getString("token_type"));
                                    appUtil.setPrefrence("expires_in", jsonParam.getString("expires_in"));
                                    appUtil.setPrefrence(".issued", jsonParam.getString(".issued"));
                                    appUtil.setPrefrence(".expires", jsonParam.getString(".expires"));

                                    shoppingListIdLoad();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkey2ShoppingFw", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        saveErrorLog("getTokenkey2ShoppingFw", String.valueOf(error.networkResponse.statusCode));
                        // progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            //  progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog=userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok),"Fail");
                                alertDialog.show();

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
                        params.put("grant_type", "password");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
//                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        params.put("username", "imemine@usa.com");
                        params.put("password", "123456");
                        params.put("ClientID", "1");
//                        params.put("Content-Type", "application/json ;charset=utf-8");
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
                    //  FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();
                //  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private String lowercase(String lowerString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(lowerString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toLowerCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    private void saveErrorLog(String FunctionName, String ErrorDetail) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ERRORLOG + "?FunctionName=" + FunctionName + "&ErrorSource=" + "android" + "&ErrorStatus=" + "fail" + "&ErrorDetail="+ErrorDetail + "&MemberId=" + appUtil.getPrefrence("MemberId") ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                alertDialog = userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok), "Fail");
                                alertDialog.show();

                            }
                        }
                        //finish();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("Device", "5");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    //finish();
                    e.printStackTrace();
                }

            } catch (Exception e) {
                //finish();
                e.printStackTrace();
                //  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            //finish();
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    //Check the internet connection.
    private void NetwordDetect() {

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if (WIFI == true) {
            IPaddress = GetDeviceipWiFiData();
            Log.i("ip", IPaddress);
            //textview.setText(IPaddress);


        }

        if (MOBILE == true) {

            IPaddress = GetDeviceipMobileData();
            Log.i("mobileip", IPaddress);
            // textview.setText(IPaddress);

        }

    }

    public String GetDeviceipMobileData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public String GetDeviceipWiFiData() {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    private void saveLogin() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                // progressDialog.setMessage("Processing");
                //progressDialog.show();

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.LOGINSAVE + "&Information=" + appUtil.getPrefrence("Email") + "|" + appUtil.getPrefrence("Password") + "|" + deviceType + "|Android-" + osName + "|" + myVersion + "|" + "" + "|" + "" + "|" + "" + "|" + Latitude + "|" + Longitude + "|"+Constant.APP_VERSION,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                Log.i("url",Constant.WEB_URL + Constant.LOGINSAVE + "&Information=" + appUtil.getPrefrence("Email") + "|" + appUtil.getPrefrence("Password") + "|" + deviceType + "|Android-" + osName + "|" + myVersion + "|" + "" + "|" + "" + "|" + "" + "|" + Latitude + "|" + Longitude + "|10.6");
                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    appUtil.setPrefrence("SaveLogin", "yes");
                                } catch (JSONException e) {
                                    finish();
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        //  progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            //      progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog = userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok), "Fail");
                                alertDialog.show();

                            }
                        }
                        finish();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();


                        //params.put("UserName", et_email.getText().toString());
                        //params.put("password", et_pwd.getText().toString());
                        //params.put("UserName", appUtil.getPrefrence("Email"));
                        //params.put("password", appUtil.getPrefrence("Password"));

                        //test
                        params.put("Device", "5");
                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
                        return params;
                    }
                };
                RetryPolicy policy = new DefaultRetryPolicy
                        (5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(policy);
                try {
                    // FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    finish();
                    e.printStackTrace();
                }

            } catch (Exception e) {
                finish();
                e.printStackTrace();
                //  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            finish();
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }
}
