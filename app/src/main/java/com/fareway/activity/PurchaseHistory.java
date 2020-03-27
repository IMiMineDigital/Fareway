package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
/*import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.volley.toolbox.StringRequest;
import com.fareway.R;
import com.fareway.adapter.PurchaseHistoryAdapter;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Purchase;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.DividerRVDecoration;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
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

public class PurchaseHistory extends AppCompatActivity implements PurchaseHistoryAdapter.PurchaseHistoryAdapterListener {
    private ProgressDialog progressDialog;
    AppUtilFw appUtil;
    private Activity activity;
    private AlertDialog alertDialog;
    private RequestQueue mQueue;
    private static RecyclerView rv_purchase_history;
    private ArrayList<Purchase> purchaseArrayList;
    private PurchaseHistoryAdapter purchaseListAdapter;
    public static JSONArray purchasemessage;
    private UserAlertDialog userAlertDialog;

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
        setContentView(R.layout.activity_purchase_history);
        activity=PurchaseHistory.this;
        mQueue= FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);

        purchaseArrayList = new ArrayList<>();
        rv_purchase_history = (RecyclerView) findViewById(R.id.rv_purchase_history);
        purchaseListAdapter = new PurchaseHistoryAdapter(this, purchaseArrayList,this,this);
        RecyclerView.LayoutManager mLayoutManagerShoppingList = new LinearLayoutManager(activity);
        rv_purchase_history.setLayoutManager(mLayoutManagerShoppingList);
        rv_purchase_history.setAdapter(purchaseListAdapter);
       /* Drawable dividerDrawableShoppingList = ContextCompat.getDrawable(activity, R.drawable.divider);
        rv_purchase_history.addItemDecoration(new DividerRVDecoration(dividerDrawableShoppingList));*/

        getSupportActionBar().setTitle("Purchase history");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                String c = "yyyy-MM-dd HH:mm:ss";
                //String c= "dd MMM yyyy";
                spf = new SimpleDateFormat(c);
                saveDate = spf.format(newDate);
                System.out.println("saveDate " + saveDate);

                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println("currentDate " + currentDate);
                appUtil.setPrefrence("comeFrom", "mpp");

                if (appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")==true) {
                    /*if (currentDate.compareTo(saveDate) < 0) {
                        purchaseHistoryLoad();

                    } else {
                        getTokenkey();
                    }*/
                    getTokenkey2();

                } else {

                    if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                        getTokenkey();
                    } else {
                        Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
                        /*alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                getString(R.string.ok), getString(R.string.alert));
                        alertDialog.show();*/
                    }


                }
            }
        }
        else {

            if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                getTokenkey();
            } else {
                /*alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                        getString(R.string.ok), getString(R.string.alert));
                alertDialog.show();*/
                Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
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

    private void purchaseHistoryLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                /*progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();*/
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PURCHASEHISTORY+appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Api Data", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){

                                        purchasemessage= root.getJSONArray("purchasemessage");

                                        purchaseArrayList.clear();
                                        List<Purchase> items = new Gson().fromJson(purchasemessage.toString(), new TypeToken<List<Purchase>>() {
                                        }.getType());
                                        purchaseArrayList.addAll(items);
                                        purchaseListAdapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }else {
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                    saveErrorLog("purchaseHistoryLoad", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("purchaseHistoryLoad", String.valueOf(error.networkResponse.statusCode));
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
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }
        } else {

           // alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
           //         getString(R.string.ok),getString(R.string.alert));
           // alertDialog.show();
            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onProductSelected(final Purchase purchase) {

        //Toast.makeText(getApplicationContext(), "Selected: " + purchase.getPurchasedate(), Toast.LENGTH_SHORT).show();
        try {
            DecimalFormat dF = new DecimalFormat("00.00");
            Number num = dF.parse(purchase.getTotalamount());

            Intent i = new Intent(PurchaseHistory.this, PurchaseHistoryDetail.class);
        i.putExtra("PURCHASEID",purchase.getPurchaseid());
        i.putExtra("REMAINAMOUTNT",purchase.getRemainamount());
        i.putExtra("PURCHASEDATE",purchase.getPurchasedate());
        i.putExtra("PURCHASESTORELOCATION",purchase.getStorelocation());
        i.putExtra("PURCHASETOTALAMOUNT",new DecimalFormat("##.##").format(num));
        startActivity(i);
        } catch (Exception e) {
            saveErrorLog("PurchaseHistoryonProductSelected", e.getLocalizedMessage());
        }
    }

    @Override
    public void onProductDateSelected(Purchase purchase) {
        Toast.makeText(getApplicationContext(), "Selected: " + purchase.getPurchasedate(), Toast.LENGTH_SHORT).show();
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
                                    //  progressDialog.dismiss();
                                    /*Intent i = new Intent(activity, LoginFw.class);
                                    startActivity(i);
                                    finish();*/
                                    login();

                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkeypurchaseHistoryLoad", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("getTokenkeypurchaseHistoryLoad", String.valueOf(error.networkResponse.statusCode));
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
                    saveErrorLog("getTokenkeypurchaseHistoryLoad", e.getLocalizedMessage());
                }

            } catch (Exception e) {

                e.printStackTrace();
                saveErrorLog("getTokenkeypurchaseHistoryLoad", e.getLocalizedMessage());
                //  progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            /*alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();*/
            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
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
                                        purchaseHistoryLoad();

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
                                        saveErrorLog("loginPurchaseHistory", "200 "+root.getString("message"));
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    saveErrorLog("loginPurchaseHistory", e.getLocalizedMessage());
                                    finish();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("loginPurchaseHistory", String.valueOf(error.networkResponse.statusCode));
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
                    saveErrorLog("loginPurchaseHistory", e.getLocalizedMessage());
                    finish();
                    e.printStackTrace();
                }

            } catch (Exception e) {
                saveErrorLog("loginPurchaseHistory", e.getLocalizedMessage());
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

                                    purchaseHistoryLoad();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkey2PurchaseHistory", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        saveErrorLog("getTokenkey2PurchaseHistory", String.valueOf(error.networkResponse.statusCode));
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
                                    /*if (root.getString("errorcode").equals("0")){

                                        appUtil.setPrefrence("isLogin", "yes");
                                        Intent i = new Intent(activity, MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        startActivity(i);
                                        finish();
                                    }else if (root.getString("errorcode").equals("200")){
                                        finish();
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
                                    }*/
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
