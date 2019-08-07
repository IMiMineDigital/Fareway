package com.fareway.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fareway.R;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingListPrint extends AppCompatActivity {
    private WebView myWebView;
    AppUtilFw appUtil;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    private Activity activity;
    private RequestQueue mQueue;
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_print);
        activity=ShoppingListPrint.this;
        mQueue= FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);

        //printWebView();
        //shoppingListLoad();

        /////
      /*  this.webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid=5155567152&memberid=41761");*/

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid=5155567152&memberid=41761"));
        startActivity(browserIntent);
        //webView.loadData("<html><body>Hello, world!</body></html>", "text/html", "UTF-8");

    }

    public void shoppingListLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, "https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid="+appUtil.getPrefrence("ShopperID")+"&memberid="+appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());
                                printWebView(response.toString());
                                progressDialog.dismiss();

                              /*  try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();
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
                                            //tv.setText(String.valueOf(shopping.length()));

                                            shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
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
                        //params.put("Content-Type", "application/x-www-form-urlencoded");
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
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void printWebView(String s) {

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request)
            {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        String htmlDocument = s;

        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/HTML", "UTF-8", null);

        myWebView = webView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    /////
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
