package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        getSupportActionBar().setTitle("Past Purchase");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String saveDate = appUtil.getPrefrence(".expires");
        if (saveDate.length()==0){
            getTokenkey();
        }else {
            SimpleDateFormat spf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            Date newDate= null;
            try {
                newDate = spf.parse(saveDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String c= "dd MMM yyyy";
            spf= new SimpleDateFormat(c);
            saveDate = spf.format(newDate);
            System.out.println(saveDate);

            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
            String currentDate = dateformat2.format(c2.getTime());
            System.out.println(currentDate);
            appUtil.setPrefrence("comeFrom","mpp");

            if(appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")){
                if (saveDate.equalsIgnoreCase(currentDate)){
                   /* Intent i=new Intent(activity, MainFwActivity.class);
                    if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("mpp")){
                        i.putExtra("comeFrom","mpp");
                    }else if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("moreOffer")){
                        i.putExtra("comeFrom","moreOffer");
                    }

                    startActivity(i);
                    finish();*/
                    purchaseHistoryLoad();
                }else {
                    getTokenkey();
                }

            }
            else{

                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                    getTokenkey();
                }
                else {
                    alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok),getString(R.string.alert));
                    alertDialog.show();
                }


            }
        }


    }

    private void purchaseHistoryLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
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
                                        progressDialog.dismiss();
                                        purchasemessage= root.getJSONArray("purchasemessage");

                                        purchaseArrayList.clear();
                                        List<Purchase> items = new Gson().fromJson(purchasemessage.toString(), new TypeToken<List<Purchase>>() {
                                        }.getType());
                                        purchaseArrayList.addAll(items);
                                        purchaseListAdapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
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
                        (50000,
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
            progressDialog.dismiss();
           // alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
           //         getString(R.string.ok),getString(R.string.alert));
           // alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onProductSelected(final Purchase purchase) {

        Toast.makeText(getApplicationContext(), "Selected: " + purchase.getPurchasedate(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(PurchaseHistory.this, PurchaseHistoryDetail.class);
        i.putExtra("PURCHASEID",purchase.getPurchaseid());
        i.putExtra("PURCHASEDATE",purchase.getPurchasedate());
        i.putExtra("PURCHASESTORELOCATION",purchase.getStorelocation());
        i.putExtra("PURCHASETOTALAMOUNT",purchase.getTotalamount());
        startActivity(i);
    }

    @Override
    public void onProductDateSelected(Purchase purchase) {
        Toast.makeText(getApplicationContext(), "Selected: " + purchase.getPurchasedate(), Toast.LENGTH_SHORT).show();
    }

    private void getTokenkey() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                //  progressDialog.setMessage("Processing");
                //  progressDialog.show();
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
                                    purchaseHistoryLoad();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
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
                        (50000,
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
}
