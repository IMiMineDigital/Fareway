package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.fareway.R;
import com.fareway.controller.FarewayApplication;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashFw extends AppCompatActivity {

    private AppUtilFw appUtil;
    private Activity activity;
    private UserAlertDialog userAlertDialog;
    //  private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SplashFw.this;
        appUtil = new AppUtilFw(activity);
        userAlertDialog = new UserAlertDialog(activity);
        mQueue = FarewayApplication.getmInstance(this).getmRequestQueue();
        getSupportActionBar().hide();

        String saveDate = appUtil.getPrefrence(".expires");
        Log.i("saveDate", saveDate);

        Uri uri = getIntent().getData();
        if (uri !=null){
            List<String> params = uri.getPathSegments();
            String id = params.get(params.size()-1);
            //Toast.makeText(this, "id="+id, Toast.LENGTH_SHORT).show();
            if (appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")==true){
                if (saveDate != null) {

                    if (saveDate.length() == 0) {
                        Log.i("start date", saveDate + appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes"));
                        getTokenkey();
                    }
                    else {
                        SimpleDateFormat spf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                        Date newDate = null;
                        try {
                            newDate = spf.parse(saveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String c = "yyyy-MM-dd HH:mm:ss";
                        spf = new SimpleDateFormat(c);
                        saveDate = spf.format(newDate);
                        System.out.println("saveDate " + saveDate);

                        Calendar c2 = Calendar.getInstance();
                        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDate = dateformat2.format(c2.getTime());
                        System.out.println("currentDate " + currentDate);
                        appUtil.setPrefrence("comeFrom", "mpp");

                        if (appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")==true) {
                            getTokenkey2();
                        }
                        else {
                            if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                                getTokenkey();
                            } else {
                                alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                        getString(R.string.ok), getString(R.string.alert));
                                alertDialog.show();
                            }
                        }
                    }

                }
                else {

                    if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                        getTokenkey();
                    } else {
                        alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                getString(R.string.ok), getString(R.string.alert));
                        alertDialog.show();
                    }
                }
            }else {
                finish();
                //startActivity(new Intent(MainActivity.this, Second.class));
                Log.i("test",id+"id");

            }
        }
        else {
            if (saveDate != null) {

                if (saveDate.length() == 0) {
                    Log.i("start date", saveDate + appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes"));
                    getTokenkey();
                }
                else {
                    SimpleDateFormat spf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                    Date newDate = null;
                    try {
                        newDate = spf.parse(saveDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String c = "yyyy-MM-dd HH:mm:ss";
                    spf = new SimpleDateFormat(c);
                    saveDate = spf.format(newDate);
                    System.out.println("saveDate " + saveDate);

                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println("currentDate " + currentDate);
                    appUtil.setPrefrence("comeFrom", "mpp");

                    if (appUtil.getPrefrence("isLogin").equalsIgnoreCase("yes")==true) {
                        getTokenkey2();
                    }
                    else {
                        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                            getTokenkey();
                        } else {
                            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                    getString(R.string.ok), getString(R.string.alert));
                            alertDialog.show();
                        }
                    }
                }

            }
            else {

                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                    getTokenkey();
                } else {
                    alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok), getString(R.string.alert));
                    alertDialog.show();
                }
            }
        }

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
                                    Intent i = new Intent(activity, LoginFw.class);
                                    startActivity(i);
                                    finish();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkey", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        try {
                            saveErrorLog("getTokenkey", String.valueOf(error.networkResponse.statusCode));
                        }catch (Exception e){
                            e.printStackTrace();
                            alertDialog=userAlertDialog.createPositiveAlert("Checking the network cables, modem, and router\nReconnecting to Wi-Fi",
                                    getString(R.string.ok),"No Internet");
                            alertDialog.show();
                        }
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
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

    private void getTokenkey2() {
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
                                    Intent i = new Intent(activity, MainFwActivity.class);

                                    if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("mpp")) {
                                        i.putExtra("comeFrom", "mpp");
                                    } else if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("moreOffer")) {
                                        i.putExtra("comeFrom", "moreOffer");
                                    }

                                    startActivity(i);
                                    finish();
                                } catch (Throwable e) {
                                    //  progressDialog.dismiss();
                                    Log.i("Excep", "error----" + e.getMessage());
                                    e.printStackTrace();
                                    saveErrorLog("getTokenkey2", e.getLocalizedMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        try {
                            saveErrorLog("getTokenkey2", String.valueOf(error.networkResponse.statusCode));
                        }catch (Exception e){
                            e.printStackTrace();
                            alertDialog=userAlertDialog.createPositiveAlert("Checking the network cables, modem, and router\nReconnecting to Wi-Fi",
                                    getString(R.string.ok),"No Internet");
                            alertDialog.show();
                        }
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
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
