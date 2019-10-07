package com.fareway.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.Formatter;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LoginFw extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private TextView tv_signUp,tv_forgot;
    private EditText et_email,et_pwd;
    private Button btn_signin;
    private Activity activity;
    private UserAlertDialog userAlertDialog;
    private AppUtilFw appUtil;
    private AlertDialog alertDialog;
    private RequestQueue mQueue;
    // private ProgressDialog progressDialog;
    Boolean IPValue;
    LocationManager locationManager;
    String IPaddress;
    String osName;
    String myVersion;
    String Latitude="";
    String Longitude="";
    int sdkVersion;
    double diagonalInches;
    String deviceType="";
    public static boolean locationGet=true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fw);
        //getSupportActionBar().hide();
        activity=LoginFw.this;
        appUtil=new AppUtilFw(activity);
        getSupportActionBar().hide();
        userAlertDialog=new UserAlertDialog(activity);
        mQueue=FarewayApplication.getmInstance(this).getmRequestQueue();

        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        //getLocation();

        osName= Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        myVersion = android.os.Build.VERSION.RELEASE;
        sdkVersion = android.os.Build.VERSION.SDK_INT;
        NetwordDetect();
        /*Log.i("myVersion",myVersion);
        Log.i("sdkVersion", String.valueOf(sdkVersion));*/

        Display display = ((Activity)   activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
        //Log.i("test", String.valueOf(diagonalInches));
        /*String DeviceId =  android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.i("device",DeviceId);*/


        /*appUtil.setPrefrence("Email", "prajput@juno.com");
        appUtil.setPrefrence("Password", "123456");*/
        linkUIElements();
        //checkLocationPermission();
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
        //login();
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
                                        appUtil.setPrefrence("SaveLogin", "no");

                                        appUtil.setPrefrence("isLogin", "yes");
                                        Intent i = new Intent(activity, MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        startActivity(i);
                                        finish();

                                        Log.i("IPaddress",IPaddress);
                                        Log.i("osName",osName);
                                        Log.i("myVersion",myVersion);
                                        Log.i("sdkVersion", String.valueOf(sdkVersion));
                                        Log.i("diagonalInches", String.valueOf(diagonalInches));
                                        if (diagonalInches>=6.80){
                                            deviceType="tablet";
                                        }else {
                                            deviceType="mobile";
                                        }
                                        Log.i("deviceType", deviceType);
                                        try {
                                            Log.i("Latitude", Latitude);
                                            Log.i("Longitude", Longitude);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        //saveLogin();

                                    }else if (root.getString("errorcode").equals("200")){
                                        finish();
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
                                    }
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

                        //params.put("UserName", et_email.getText().toString());
                        //params.put("password", et_pwd.getText().toString());
                        //appUtil.setPrefrence("Email", et_email.getText().toString());
                        //appUtil.setPrefrence("Password", et_pwd.getText().toString());
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
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Current Location",location.getLatitude() + ", " + location.getLongitude());
        Latitude= String.valueOf(location.getLatitude());
        Longitude= String.valueOf(location.getLongitude());
        if (Latitude!="" && Longitude!="" && locationGet==true){
            Log.i("test","ifget");

            //login();
            locationGet=false;
        }else {
           Log.i("test","elseget");
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Toast.makeText(activity, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("pro1",provider);
        login();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("pro2",provider);
        login();
    }

    void getLocation() {
        try {
            login();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
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

        if(WIFI == true)

        {
            IPaddress = GetDeviceipWiFiData();
            Log.i("ip",IPaddress);
            //textview.setText(IPaddress);


        }

        if(MOBILE == true)
        {

            IPaddress = GetDeviceipMobileData();
            Log.i("mobileip",IPaddress);
            // textview.setText(IPaddress);

        }

    }


    public String GetDeviceipMobileData(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
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

    public String GetDeviceipWiFiData()
    {

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

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.LOGINSAVE+"&Information="+appUtil.getPrefrence("Email")+"|"+appUtil.getPrefrence("Password")+"|"+deviceType+"|"+osName+"|"+myVersion+"|"+""+"|"+""+"|"+""+"|"+Latitude+"|"+Longitude+"|6.1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){


                                        //appUtil.setPrefrence("comeFrom","mpp");
                                        appUtil.setPrefrence("isLogin", "yes");
                                        Intent i = new Intent(activity, MainFwActivity.class);
                                        i.putExtra("comeFrom","mpp");
                                        startActivity(i);
                                        finish();
                                    }else if (root.getString("errorcode").equals("200")){
                                        finish();
                                        Toast.makeText(activity, root.getString("message"), Toast.LENGTH_LONG).show();
                                    }
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
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Allow")
                        .setMessage("Location")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginFw.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
            return false;
        } else {
            Log.i("test","else");
            login();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        getLocation();
                        //login();
                        //

                        //Request location updates:
                        // locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    login();
                    Toast.makeText(this, "Denied", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
