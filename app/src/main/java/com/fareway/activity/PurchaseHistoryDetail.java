package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
/*
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
/*
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;*/
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fareway.R;
import com.fareway.adapter.PurchaseHistoryDetailAdapter;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.PurchaseModelHistory;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.DividerRVDecoration;
import com.fareway.utility.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseHistoryDetail extends AppCompatActivity {
    private ProgressDialog progressDialog;
    AppUtilFw appUtil;
    private Activity activity;
    private AlertDialog alertDialog;
    private RequestQueue mQueue;
    private static RecyclerView rv_purchase_history;
    private ArrayList<PurchaseModelHistory> purchaseArrayList;
    private PurchaseHistoryDetailAdapter purchaseListAdapter;
    public static JSONArray purchasemessage;
    TextView tv_bottom_bar1,tv_bottom_bar2,tv_bottom_bar3,
            tv_header_bar,tv_header_location,tv_header_total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history_detail);
        activity=PurchaseHistoryDetail.this;
        mQueue= FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);

        tv_bottom_bar1=findViewById(R.id.tv_bottom_bar1);
        tv_bottom_bar2=findViewById(R.id.tv_bottom_bar2);
        tv_bottom_bar3=findViewById(R.id.tv_bottom_bar3);

        tv_header_bar=findViewById(R.id.tv_header_bar);
        tv_header_location=findViewById(R.id.tv_header_location);
        tv_header_total_price=findViewById(R.id.tv_header_total_price);

        /*String header_content = getIntent().getExtras().getString("PURCHASEDATE")+" Purchase \nLocation: "+
                getIntent().getExtras().getString("PURCHASESTORELOCATION")+"\nTotal Price Paid: $"+
                getIntent().getExtras().getString("PURCHASETOTALAMOUNT");*/
        //String rate = getIntent().getExtras().getString("PURCHASESTORELOCATION");
        //String rate = getIntent().getExtras().getString("PURCHASETOTALAMOUNT");
        tv_header_bar.setText(getIntent().getExtras().getString("PURCHASEDATE"));
        tv_header_location.setText(getIntent().getExtras().getString("PURCHASESTORELOCATION"));
        tv_header_total_price.setText("$"+getIntent().getExtras().getString("PURCHASETOTALAMOUNT"));

        purchaseArrayList = new ArrayList<>();
        rv_purchase_history = (RecyclerView) findViewById(R.id.rv_purchase_history);
        purchaseListAdapter = new PurchaseHistoryDetailAdapter(this, purchaseArrayList);
        RecyclerView.LayoutManager mLayoutManagerShoppingList = new LinearLayoutManager(activity);
        rv_purchase_history.setLayoutManager(mLayoutManagerShoppingList);
        rv_purchase_history.setAdapter(purchaseListAdapter);
       /* Drawable dividerDrawableShoppingList = ContextCompat.getDrawable(activity, R.drawable.divider);
        rv_purchase_history.addItemDecoration(new DividerRVDecoration(dividerDrawableShoppingList));*/

        getSupportActionBar().setTitle("Past Purchase Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        purchaseHistoryLoad();
    }

    private void purchaseHistoryLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                String purchaseId = getIntent().getExtras().getString("PURCHASEID");
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PURCHASEDETAILHISTORY+purchaseId,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Purchase detail Data", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();
                                        purchasemessage= root.getJSONArray("purchasemessage");
                                        for (int i = 0; i < 1; i++) {
                                            tv_bottom_bar1.setText(purchasemessage.getJSONObject(i).getString("totalquantity"));
                                            tv_bottom_bar2.setText("$"+purchasemessage.getJSONObject(i).getString("remainamount"));
                                            tv_bottom_bar3.setText(purchasemessage.getJSONObject(i).getString("totalamount"));
                                        }

                                        purchaseArrayList.clear();
                                        List<PurchaseModelHistory> items = new Gson().fromJson(purchasemessage.toString(), new TypeToken<List<PurchaseModelHistory>>() {
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
}
