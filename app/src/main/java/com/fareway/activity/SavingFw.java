package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
/*
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.fareway.controller.FarewayApplication;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SavingFw extends AppCompatActivity {
    private ProgressDialog progressDialog;
    AppUtilFw appUtil;
    public static boolean singleView=true;private Activity activity;
    public static String comeFrom="mpp";
    public static JSONArray message;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    TextView tv_pty,tv_tyc,tv_pc,tv_total_save;
    TextView tv_pty_lbl,tv_tyc_lbl,tv_pc_lbl,tv_total_save_lbl;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_fw);
        activity=SavingFw.this;
        mQueue=FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);
        comeFrom=getIntent().getStringExtra("comeFrom");
        getSupportActionBar().setTitle("MyFareway Savings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_pty =findViewById(R.id.tv_pty);
        tv_tyc =findViewById(R.id.tv_tyc);
        tv_pc =findViewById(R.id.tv_pc);
        tv_total_save =findViewById(R.id.tv_total_save);

        tv_pty_lbl =findViewById(R.id.tv_pty_lbl);
        tv_tyc_lbl =findViewById(R.id.tv_tyc_lbl);
        tv_pc_lbl =findViewById(R.id.tv_pc_lbl);
        tv_total_save_lbl =findViewById(R.id.tv_total_save_lbl);
        messageLoad();

    }
    private void messageLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.SAVING+"?MemberId="+appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                        //fetchProduct();
                                        for (int i = 0; i < message.length(); i++) {

                                            tv_pty_lbl.setText(message.getJSONObject(i).names().getString(0).replace("PersonalDealSaving","My Personal Deals"));
                                            tv_pty.setText("$"+message.getJSONObject(i).getString("PersonalDealSaving"));
                                            tv_tyc_lbl.setText(message.getJSONObject(i).names().getString(1).replace("CouponSaving","Digital Coupons"));
                                            tv_tyc.setText("$"+message.getJSONObject(i).getString("CouponSaving"));
                                            tv_pc_lbl.setText(message.getJSONObject(i).names().getString(2).replace("TPRSaving","Sale Items"));
                                            tv_pc.setText("$"+message.getJSONObject(i).getString("TPRSaving"));
                                            tv_total_save_lbl.setText(message.getJSONObject(i).names().getString(3).replace("TotalSaving","Total Savings"));
                                            tv_total_save.setText("$"+message.getJSONObject(i).getString("TotalSaving"));
                                            if (message.getJSONObject(i).getString("PersonalDealSaving").equalsIgnoreCase("")){
                                                tv_pty.setText("$0.00");
                                            }
                                            if (message.getJSONObject(i).getString("CouponSaving").equalsIgnoreCase("")){
                                                tv_tyc.setText("$0.00");
                                            }
                                            if (message.getJSONObject(i).getString("TotalSaving").equalsIgnoreCase("")){
                                                tv_total_save.setText("$0.00");
                                            }

                                        }
                                    }else  if (root.getString("errorcode").equals("400")){
                                        progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                        tv_pty_lbl.setText("My Personal Deals");
                                        tv_tyc_lbl.setText("Digital Coupons");
                                        tv_total_save_lbl.setText("Total Savings");
                                        //fetchProduct();
                                        /*for (int i = 0; i < message.length(); i++) {
                                            tv_pty_lbl.setText(message.getJSONObject(i).names().getString(0).replace("PersonalDealSaving","My Personal Deals"));
                                            tv_pty.setText("$"+message.getJSONObject(i).getString("PersonalDealSaving"));
                                            tv_tyc_lbl.setText(message.getJSONObject(i).names().getString(1).replace("CouponSaving","Digital Coupons"));
                                            tv_tyc.setText("$"+message.getJSONObject(i).getString("CouponSaving"));
                                            tv_pc_lbl.setText(message.getJSONObject(i).names().getString(2).replace("TPRSaving","Sale Items"));
                                            tv_pc.setText("$"+message.getJSONObject(i).getString("TPRSaving"));
                                            tv_total_save_lbl.setText(message.getJSONObject(i).names().getString(3).replace("TotalSaving","Total Savings"));
                                            tv_total_save.setText("$"+message.getJSONObject(i).getString("TotalSaving"));

                                        }*/
                                    }
                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    tv_pty_lbl.setText("My Personal Deals");
                                    tv_tyc_lbl.setText("Digital Coupons");
                                    tv_total_save_lbl.setText("Total Savings");
                                    e.printStackTrace();
                                    saveErrorLog("messageLoadSavingFw", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());

                        tv_pty_lbl.setText("My Personal Deals");
                        tv_tyc_lbl.setText("Digital Coupons");
                        tv_total_save_lbl.setText("Total Savings");
                        error.printStackTrace();
                        saveErrorLog("messageLoadSavingFw", String.valueOf(error.networkResponse.statusCode));
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
                    saveErrorLog("messageLoadSavingFw", e.getLocalizedMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                saveErrorLog("messageLoadSavingFw", e.getLocalizedMessage());
                progressDialog.dismiss();
//                displayAlert();
            }
        }
        else {
            /*alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();*/
            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
}
