package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginFw extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_signUp,tv_forgot;
    private EditText et_email,et_pwd;
    private Button btn_signin;
    private Activity activity;
    private UserAlertDialog userAlertDialog;
    private AppUtilFw appUtil;
    private AlertDialog alertDialog;
    private RequestQueue mQueue;
    // private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fw);
        //getSupportActionBar().hide();
        activity=LoginFw.this;
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);
        mQueue=FarewayApplication.getmInstance(this).getmRequestQueue();
        linkUIElements();
        login();
    }
    private void linkUIElements()
    {
        tv_signUp=(TextView) findViewById(R.id.tv_signUp);
        tv_forgot=(TextView) findViewById(R.id.tv_forgot);
        et_email=(EditText) findViewById(R.id.et_email);
        et_pwd=(EditText) findViewById(R.id.et_pwd);
        btn_signin=(Button)findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(this);
        SpannableString ss = new SpannableString(getString(R.string.sign_up));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
              /*  Intent i=new Intent(activity,SignUp.class);
                startActivity(i);*/
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_grey)), 23, 30, 0);
        tv_signUp.setText(ss);
        tv_signUp.setMovementMethod(LinkMovementMethod.getInstance());
        tv_signUp.setHighlightColor(Color.TRANSPARENT);
        SpannableString forgot_support = new SpannableString(getString(R.string.forgot_support));
        ClickableSpan forot_clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
               /* Intent i=new Intent(activity,ForgetPassword.class);
                startActivity(i);*/
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ClickableSpan support_clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //  Intent i=new Intent(activity,Support.class);
                //  startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        forgot_support.setSpan(forot_clickableSpan, 0, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot_support.setSpan(support_clickableSpan, 18, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgot_support.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_grey)), 0, 25, 0);
        tv_forgot.setText(forgot_support);
        tv_forgot.setMovementMethod(LinkMovementMethod.getInstance());
        tv_forgot.setHighlightColor(Color.TRANSPARENT);
       // login();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_signin) {
            if (et_email.getText().toString().isEmpty()) {
                alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.plz_input_email),
                        getString(R.string.ok), getString(R.string.alert));
                alertDialog.show();
                et_email.requestFocus();
            } else if (!appUtil.isEmailCorrect(et_email.getText().toString())) {
                alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.plz_input_val_email),
                        getString(R.string.ok), getString(R.string.alert));
                alertDialog.show();
                et_email.requestFocus();
            } else if (et_pwd.getText().toString().isEmpty()) {
                alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.plz_input_pwd),
                        getString(R.string.ok), getString(R.string.alert));
                alertDialog.show();
                et_pwd.requestFocus();
            } else {

                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                    login();
                } else {
                    alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok), getString(R.string.alert));
                    alertDialog.show();
                }
            }
        } else {

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
                                            appUtil.setPrefrence("StoreId", jsonParam.getString("StoreId"));
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
                                        }
                                        //appUtil.setPrefrence("comeFrom","mpp");
                                        appUtil.setPrefrence("isLogin", "yes");
                                        Intent i = new Intent(activity, MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        //i.putExtra("comeFrom", "mpp");
                                    /*  if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("mpp")){
                                            i.putExtra("comeFrom","mpp");
                                        }else if (appUtil.getPrefrence("comeFrom").equalsIgnoreCase("moreOffer")){
                                            i.putExtra("comeFrom","moreOffer");
                                        }
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                                        // progressDialog.dismiss();
                                        startActivity(i);
                                        finish();
                                    }else if (root.getString("errorcode").equals("200")){
                                       // finish();
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
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


                        //params.put("UserName", et_email.getText().toString());
                        //params.put("password", et_pwd.getText().toString());
                        params.put("UserName", appUtil.getPrefrence("Email"));
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
