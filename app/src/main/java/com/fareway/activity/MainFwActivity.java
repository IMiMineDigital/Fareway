package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fareway.R;
import com.fareway.adapter.CustomAdapterFilter;
import com.fareway.adapter.CustomAdapterParticipateItems;
import com.fareway.adapter.CustomAdapterPersonalPrices;
import com.fareway.adapter.CustomGroupAdapter;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Category;
import com.fareway.model.Group;
import com.fareway.model.Product;
import com.fareway.model.RelatedItem;
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

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFwActivity extends AppCompatActivity
        implements CustomAdapterPersonalPrices.CustomAdapterPersonalPricesListener,
        CustomAdapterParticipateItems.CustomAdapterParticipateItemsListener,CustomGroupAdapter.CustomAdapterGroupItemsListener{

    private DrawerLayout drawer;
    private Toolbar toolbar;private Toolbar DetaileToolbar;private Toolbar participateToolbar;
    //private NavigationView navigationView;
    private TextView mTextMessage,tv_uname,tv_filter_by_category,tv_filter_by_offer,tv_type;
    private ImageView imv_view_list;
    private static RecyclerView rv_items,rv_items_verite,rv_items_group;
    private static CustomAdapterParticipateItems customAdapterParticipateItems;
    private static CustomGroupAdapter customGroupAdapter;
    ArrayList<RelatedItem> relatedItemsList;
    ArrayList<Group> groupItemsList;
    public JSONArray jsonParam;
    public static ArrayList<Product> productList;
    private static CustomAdapterPersonalPrices customAdapterPersonalPrices;
    public static boolean singleView=true;private Activity activity;
    private BottomNavigationView navigation;
    private RelativeLayout main;
    private PopupWindow popupWindow;
    public static String comeFrom;
    AppUtilFw appUtil;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    private SearchView searchView;
    private LinearLayout rowLayout,rowLayout0,rowLayout1,rowLayout2,rowLayout3;
    public static int tmp=0;
    public static JSONArray message;
    public static JSONArray message2;
    public static JSONArray message3;
    private static RecyclerView rv_category;
    private ArrayList<Category> categoryList;
    private CustomAdapterFilter customAdapterFilter;
    public static   int x=0; int y=0;
    public int a=0; int b=0 ;
    private EditText et_search;
    private RequestQueue mQueue;
    public static RequestQueue mQueue2;
    public static  AppUtilFw appUtil2;
    ImageView submit_btn,imv_micro_recorder;
    EditText edit_txt;
    ScrollView scrollView;
    TextView group_count_text;
    String Group="";
    int groupcount=0;
    Product productrelated2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fw);
        activity=MainFwActivity.this;
        mQueue=FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil=new AppUtilFw(activity);
        userAlertDialog=new UserAlertDialog(activity);

        mQueue2=FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil2=new AppUtilFw(activity);
        comeFrom=getIntent().getStringExtra("comeFrom");
        Log.i("test",comeFrom+" singh");
        linkUIElements();
        edit_txt = (EditText) findViewById(R.id.search_edit);

        edit_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (edit_txt.getText().toString().isEmpty()){
                        Log.i("if","test");
                    }else {
                        if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                            submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                            submit_btn.setTag(1);
                            String search=edit_txt.getText().toString();
                            searchLoad(search);
                            x=3;
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edit_txt.getWindowToken(), 0);

                        } else {
                            submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                            submit_btn.setTag(0);
                            edit_txt.getText().clear();
                            fetchProduct();
                            x=0;
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        submit_btn = (ImageView) findViewById(R.id.submit_btn);
        submit_btn.setTag(0);
        submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edit_txt.getText().toString().isEmpty()){
                        Log.i("if","test");
                    }else {
                        if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                            submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                            submit_btn.setTag(1);
                            String search=edit_txt.getText().toString();
                            searchLoad(search);
                            x=3;

                        } else {
                            submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                            submit_btn.setTag(0);
                            edit_txt.getText().clear();
                            fetchProduct();
                            x=0;
                        }
                    }
                }
            });
        imv_micro_recorder = (ImageView) findViewById(R.id.imv_micro_recorder);
        imv_micro_recorder.setTag(0);
        imv_micro_recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(imv_micro_recorder.getTag().toString())==0){
                    imv_micro_recorder.setImageResource(R.drawable.ic_clear_black_24dp);
                    imv_micro_recorder.setTag(1);
                    getSpeechInput(v);
                }else {
                    imv_micro_recorder.setImageResource(R.drawable.micro_recorder);
                    imv_micro_recorder.setTag(0);
                    edit_txt.getText().clear();
                    fetchProduct();
                    x=0;
                }
            }
        });
    }


    private void linkUIElements()

    {

        Display display = ((Activity)   activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        final double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
        Log.i("test", String.valueOf(diagonalInches));
        group_count_text = findViewById(R.id.group_count_text);
        scrollView = findViewById(R.id.scrollView);
        categoryList = new ArrayList<>();
        rv_category = (RecyclerView) findViewById(R.id.rv_category_items);
        customAdapterFilter = new CustomAdapterFilter(this, categoryList);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(activity);
        rv_category.setLayoutManager(mLayoutManager2);
        rv_category.setAdapter(customAdapterFilter);
        Drawable dividerDrawable = ContextCompat.getDrawable(activity, R.drawable.divider);
        rv_category.addItemDecoration(new DividerRVDecoration(dividerDrawable));

        rowLayout = findViewById(R.id.rowLayout);
        rowLayout0 = findViewById(R.id.rowLayout0);
        rowLayout1 = findViewById(R.id.rowLayout1);
        rowLayout2 = findViewById(R.id.rowLayout2);
        rowLayout3 = findViewById(R.id.rowLayout3);
        //et_search = findViewById(R.id.et_search);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DetaileToolbar = (Toolbar) findViewById(R.id.toolbar2);
        participateToolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

     /*   ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));*/
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        //tv_uname = (TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_uname);
        //tv_uname.setText(appUtil.getPrefrence("FName")+" "+appUtil.getPrefrence("LName"));
        rowLayout = findViewById(R.id.rowLayout);
        tv_type=findViewById(R.id.tv_type);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if(comeFrom.equalsIgnoreCase("mpp"))
        {
            navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Digital Coupon");
            Log.i("navif", String.valueOf(x));
        }
        else  if(comeFrom.equalsIgnoreCase("moreOffer"))
        {
            //navigation.getMenu().findItem(R.id.moreCoupons).setTitle(getString(R.string.more_coupons));
            navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Personal Ad");
            Log.i("navelse", String.valueOf(x));
        }


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rv_items_group = (RecyclerView) findViewById(R.id.rv_items_group);
        groupItemsList = new ArrayList<>();
        customGroupAdapter = new CustomGroupAdapter(this, groupItemsList,this);
        RecyclerView.LayoutManager mLayoutManager5 = new GridLayoutManager(this, 5);
        rv_items_group.setLayoutManager(mLayoutManager5);
        rv_items_group.setAdapter(customGroupAdapter);


        productList = new ArrayList<>();

        imv_view_list = (ImageView)findViewById(R.id.imv_view_list);
        imv_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!singleView) {
                    if (diagonalInches>=9.27){
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView=true;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    }else {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView=true;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    }

                }
                else{
                    if (diagonalInches>=9.00){
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 4);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView=false;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 4);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    }else {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView=false;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    }

                }
            }
        });

        rv_items_verite = (RecyclerView) findViewById(R.id.rv_items_verite);
        relatedItemsList = new ArrayList<>();
        customAdapterParticipateItems = new CustomAdapterParticipateItems(this, relatedItemsList,this,this);
       // RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
       // rv_items_verite.setLayoutManager(mLayoutManager4);
        rv_items_verite.setAdapter(customAdapterParticipateItems);

        rv_items = (RecyclerView) findViewById(R.id.rv_items);
        customAdapterPersonalPrices = new CustomAdapterPersonalPrices(this, productList,this,this);
      //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
      //  rv_items.setLayoutManager(mLayoutManager);
        rv_items.setAdapter(customAdapterPersonalPrices);

        if (diagonalInches>=6.80){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 4);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 4);
            rv_items.setLayoutManager(mLayoutManager);
            singleView=false;
        }else if (diagonalInches>=7.16){
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
            rv_items.setLayoutManager(mLayoutManager);
            singleView=false;
        }else {
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
            rv_items.setLayoutManager(mLayoutManager);
            singleView=true;
        }

        messageLoad();
        moreCouponLoad();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            if (i == R.id.moreCoupons) {

                Intent i2 = new Intent(activity, MainFwActivity.class);
                if (comeFrom.equalsIgnoreCase("mpp")&&x==0) {
                    i2.putExtra("comeFrom", "moreOffer");
                    comeFrom="moreOffer";
                    fetchMoreCoupon();
                    x=1;
                    navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Personal Ad");
                    Log.i("ifbottom", String.valueOf(x)+comeFrom);

                } else if(x==1) {
                    i2.putExtra("comeFrom", "mpp");
                    comeFrom="mpp";
                    x=0;
                    tmp=0;
                    navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Digital Coupon");
                    fetchProduct();
                    Log.i("elsebottom", String.valueOf(x)+comeFrom);
                }
                return true;
            }else  if (i == R.id.home_button) {

                finish();
                return true;
            } else if (i == R.id.savings) {
                // fetchProduct();
                Intent i1=new Intent(activity,SavingFw.class);
                startActivity(i1);
                return true;
            } else if (i == R.id.filter) {
                PopupMenu popupMenu = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation, Gravity.END);
                } else {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation);
                }
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                popupMenu.inflate(R.menu.filter);
                popupMenu.show();
                return true;
            }
            return false;
        }
    };




    private class OnDismissListener implements PopupMenu.OnDismissListener {

        @Override
        public void onDismiss(PopupMenu menu) {
            // TODO Auto-generated method stub
//            Toast.makeText(getApplicationContext(), "Popup Menu is dismissed",
//                    Toast.LENGTH_SHORT).show();
        }

    }

    private class OnMenuItemClickListener implements
            PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // TODO Auto-generated method stub
            int i2 = item.getItemId();
            if (i2 == R.id.filter_by_categories) {
                rv_items.setVisibility(View.INVISIBLE);
                rowLayout.setVisibility(View.GONE);
                rv_category.setVisibility(View.VISIBLE);

                return true;
            } else if (i2 == R.id.filter_by_all_offer) {
                if (x==1){

                }
                else {
                    rowLayout.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.INVISIBLE);
                    rv_category.setVisibility(View.INVISIBLE);
                    //liner_category_Layout.setVisibility(View.GONE);
                    rowLayout0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // applySearch("");
                            //onfilter(0);
                            tmp=0;
                            //  messageLoad();
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //applySearch("My Personal Deal");
                            tmp=3;
                            //fetchProductList(3);
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // applySearch("My Personal Coupon");
                            tmp=2;
                            // fetchProductList(2);
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //applySearch("My Sale Item");
                            tmp=1;
                            // fetchProductList(1);
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                        }
                    });
                }


                return true;
            }
            return false;
        }
    }
   /*@Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
  /*  @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_welcome) {
            finish();
            tmp=0;

            } else if (id == R.id.nav_my_pp) {
            Intent i2=new Intent(activity,MainFwActivity.class);
            i2.putExtra("comeFrom", "mpp");
            startActivity(i2);
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    public void searchLoad(String s) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL+Constant.SEARCH+"MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&StoreId="+appUtil.getPrefrence("StoreId")+"&SearchText="+s,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());
                                progressDialog.dismiss();

                                if (!response.equals("[]")) {
                                    try {
                                        Log.i("Fareway response", response.toString());
                                        JSONArray jsonParam = new JSONArray(response.toString());
                                        message3=jsonParam;
                                        tmp=0;
                                        searchProduct();
                                    } catch (Throwable e) {
                                        progressDialog.dismiss();
                                        Log.i("Excep", "error----" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    progressDialog.dismiss();
                                    //alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.incorrect_credentials),
                                    //        getString(R.string.ok),getString(R.string.alert));
                                    //alertDialog.show();
                                }

                               /* try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                        fetchProduct();
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

    private void messageLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.PRODUCTLIST+"?memberid="+appUtil.getPrefrence("MemberId")+"&Plateform=2",
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
                                        message= root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")){
                                            moreCouponLoad();
                                            x=1;
                                        }else {
                                            fetchProduct();
                                        }


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

    private void detatilActivated() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.PRODUCTLIST+"?memberid="+appUtil.getPrefrence("MemberId")+"&Plateform=2",
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
                                        fetchProduct();

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

    public static String reLoadApi() {
      //  if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.PRODUCTLIST+"?memberid="+appUtil2.getPrefrence("MemberId")+"&Plateform=2",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                       // fetchProduct();

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
                       // progressDialog.dismiss();
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
                        params.put("Device", "5");
                        return params;
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        params.put("Authorization", appUtil2.getPrefrence("token_type")+" "+appUtil2.getPrefrence("access_token"));
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
                    mQueue2.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // progressDialog.dismiss();
//                displayAlert();
            }
      //  }
      /*  else {
            //alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
            //        getString(R.string.ok),getString(R.string.alert));
           // alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }*/
      return null;
    }

    private void fetchProduct(){


       /* rv_items.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        toolbar2.setVisibility(View.GONE);*/

        if (message.length() == 0) {
            //no students
        } else {
            String strCategory="";
            String strCategoryCheck="";
            int Categoryid=0;
            int category_count = 0;
            int subcat=0;
            if (tmp==0){
                for (int i = 0; i < message.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if(Categoryid !=message.getJSONObject(i).getInt("CategoryID")) {

                            if (!strCategoryCheck.contains("#" + message.getJSONObject(i).getInt("CategoryID") + "#")) {
                                strCategoryCheck += "#" + message.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat = 0;
                                for (int q = 0; q < message.length(); q++) {
                                    if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID" )) {
                                        subcat = subcat + 1;
                                    }
                                }
                                Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                 /*   if(message.getJSONObject(i).getString("UPC") == relevenatupc)
                    {
                        message.getJSONObject(i).put("ListCouont", 1);
                    }*/


                }
                // Log.i("meat seafood", String.valueOf(category_count));
                List<Product> items = new Gson().fromJson(message.toString(), new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();

                strCategory ="{" +"\"CategoryID\":"+0+","+"\"CategoryName\":"+"\"All Category ("+category_count+")\"}," + strCategory;
                //Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "["+String.valueOf(strCategory)+"]";
                // categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    //Log.i("response", String.valueOf(jsonParam.length()));
                    /*for (int i=0;i<jsonParam.length();i++) {
                        Log.i("response", String.valueOf(jsonParam.length()));
                    }*/
                    // refreshing recycler view
                    List<Category> items1 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items1);
                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                {
                String categorydata="";
                for (int i = 0; i < message.length(); i++) {
                   // Log.i("test", String.valueOf(tmp));
                    try {
                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = message.getJSONObject(i);
                           // Log.i("test", String.valueOf(finalObject));
                            categorydata +=(categorydata==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                            {
                                if (!strCategoryCheck.contains("#" + message.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat=0;
                                for (int q = 0; q < message.length(); q++) {
                                    if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                        if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            subcat = subcat + 1;
                                        }
                                    }
                                }
                                Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                            }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items1 = new Gson().fromJson("["+categorydata.toString()+"]", new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items1);
               // Log.i("string", String.valueOf(items1));
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();

                strCategory ="{" +"\"CategoryID\":"+0+","+"\"CategoryName\":"+"\"All Category ("+category_count+")\"}," + strCategory;
               // Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "["+String.valueOf(strCategory)+"]";
                 //categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    for (int i=0;i<jsonParam.length();i++) {
                       // Log.i("response", String.valueOf(jsonParam.length()));

                        //categoryList.add(jsonParam.getJSONObject(i));
                    }
                    // refreshing recycler view
                    List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items2);
                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void searchProduct(){
        if (message3.length() == 0) {
            //no students
        } else {
            String strCategory="";
            String strCategoryCheck="";
            int Categoryid=0;
            int category_count = 0;
            int subcat=0;
            if (tmp==0){
                for (int i = 0; i < message3.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if(Categoryid !=message3.getJSONObject(i).getInt("CategoryID")) {

                            if (!strCategoryCheck.contains("#" + message3.getJSONObject(i).getInt("CategoryID") + "#")) {
                                strCategoryCheck += "#" + message3.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat = 0;
                                for (int q = 0; q < message3.length(); q++) {
                                    if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID" )) {
                                        subcat = subcat + 1;
                                    }
                                }
                                Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Log.i("meat seafood", String.valueOf(category_count));
                List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();

                strCategory ="{" +"\"CategoryID\":"+0+","+"\"CategoryName\":"+"\"All Category ("+category_count+")\"}," + strCategory;
                //Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "["+String.valueOf(strCategory)+"]";
                // categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    //Log.i("response", String.valueOf(jsonParam.length()));
                    /*for (int i=0;i<jsonParam.length();i++) {
                        Log.i("response", String.valueOf(jsonParam.length()));
                    }*/
                    // refreshing recycler view
                    List<Category> items1 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items1);
                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                String categorydata="";
                for (int i = 0; i < message.length(); i++) {
                    // Log.i("test", String.valueOf(tmp));
                    try {
                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = message.getJSONObject(i);
                            // Log.i("test", String.valueOf(finalObject));
                            categorydata +=(categorydata==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                            {
                                if (!strCategoryCheck.contains("#" + message.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat=0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items1 = new Gson().fromJson("["+categorydata.toString()+"]", new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items1);
                // Log.i("string", String.valueOf(items1));
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();

                strCategory ="{" +"\"CategoryID\":"+0+","+"\"CategoryName\":"+"\"All Category ("+category_count+")\"}," + strCategory;
                // Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "["+String.valueOf(strCategory)+"]";
                //categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    for (int i=0;i<jsonParam.length();i++) {
                        // Log.i("response", String.valueOf(jsonParam.length()));

                        //categoryList.add(jsonParam.getJSONObject(i));
                    }
                    // refreshing recycler view
                    List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items2);
                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void moreCouponLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                // progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.MORECOUPON+"?MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&CircularType=0",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        // progressDialog.dismiss();
                                        message2= root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")){
                                            fetchMoreCoupon();
                                        }

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
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchMoreCoupon(){
        if (message2.length() == 0) {
            //no students
        } else {
            String strCategory="";
            String strCategoryCheck="";
            int Categoryid=0;
            int category_count = 0;
            int subcat=0;
            tmp=2;
            if (tmp==0){
                for (int i = 0; i < message2.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID")) {
                            if (!strCategoryCheck.contains("#" + message2.getJSONObject(i).getInt("CategoryID") + "#")) {
                                strCategoryCheck += "#" + message2.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat = 0;
                                for (int q = 0; q < message2.length(); q++) {
                                    if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                        subcat = subcat + 1;
                                    }
                                }
                                Categoryid = message2.getJSONObject(i).getInt("CategoryID");
                                strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message2.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message2.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message2.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message2.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items = new Gson().fromJson(message2.toString(), new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();
            }else {
                String categorydata="";
                for (int i = 0; i < message2.length(); i++) {
                   // Log.i("test", String.valueOf(tmp));
                    try {
                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = message2.getJSONObject(i);
                            //Log.i("test", String.valueOf(finalObject));
                            categorydata +=(categorydata==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID")) {
                                if (!strCategoryCheck.contains("#" + message2.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message2.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat = 0;
                                    for (int q = 0; q < message2.length(); q++) {
                                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message2.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message2.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message2.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message2.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message2.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items1 = new Gson().fromJson("["+categorydata.toString()+"]", new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items1);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();
            }
            strCategory ="{" +"\"CategoryID\":"+0+","+"\"CategoryName\":"+"\"All Category ("+category_count+")\"}," + strCategory;
            //Log.i("text", "["+String.valueOf(strCategory)+"]");
            String data = "["+String.valueOf(strCategory)+"]";
            try {
                JSONArray jsonParam = new JSONArray(data.toString());
                for (int i=0;i<jsonParam.length();i++) {
                    //Log.i("response", String.valueOf(jsonParam.length()));
                }
                // refreshing recycler view
                List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                }.getType());
                categoryList.clear();
                categoryList.addAll(items2);
                rv_category.setAdapter(customAdapterFilter);
                customAdapterFilter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProductSelected(final Product product) {
        navigation.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        DetaileToolbar.setVisibility(View.VISIBLE);
        rv_items.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        DetaileToolbar.setTitle("Detail");
        DetaileToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                //fetchProduct();
            }
        });
        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
        //tv_status_detaile.setTextSize((int) getResources().getDimension(R.dimen.status_size));
        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
        TextView tv_reg_price_detail = (TextView)findViewById(R.id.tv_reg_price_detail);
        TextView tv_save_detail = (TextView)findViewById(R.id.tv_save_detail);
        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
        TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
        TextView tv_valid_detail = (TextView)findViewById(R.id.tv_valid_detail);
        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);


        ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);

        imv_item_detaile.setImageDrawable(getResources().getDrawable(R.drawable.item));
        final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
       /* ViewGroup.LayoutParams params3 = imv_status_detaile.getLayoutParams();
        params3.height = (int) getResources().getDimension(R.dimen.imv_status_size);
        params3.width = (int) getResources().getDimension(R.dimen.imv_status_size);*/
        final LinearLayout circular_layout_detaile= (LinearLayout) findViewById(R.id.circular_layout_detaile);
        LinearLayout bottomLayout_detaile= (LinearLayout) findViewById(R.id.bottomLayout_detaile);
        TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
        TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
        TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
        TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
        TableRow table_save = (TableRow) findViewById(R.id.table_save);
        TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
        TableRow table_package = (TableRow) findViewById(R.id.table_package);
        TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
        TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
        TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);


        if (product.getPrimaryOfferTypeId()==3){
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            if (product.getPackagingSize().equalsIgnoreCase("")){
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            }else {
                tv_package_detail.setText(product.getPackagingSize());
            }

            Log.i("listCount", String.valueOf(product.getListCount()));
            if (product.getRequiresActivation().contains("False")){
                if (product.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                }else if (product.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add\nTo List");
                }
            }else {
                if (product.getClickCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Activate");
                }else {

                if (product.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                }else if (product.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add\nTo List");
                }
            }
            }

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));
            Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(product.getUPC());
            tv_valid_detail.setText(product.getValidityEndDate());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = product.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }


        }else if(product.getPrimaryOfferTypeId()==2){

            table_limit.setVisibility(View.VISIBLE);
            table_limit_view.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_package_view.setVisibility(View.VISIBLE);
            Log.i("reward",product.getRewardType());
            Log.i("Limit", String.valueOf(product.getLimitPerTransection()));
            if (product.getRewardType().equalsIgnoreCase("3")){
                Log.i("ifreward",product.getRewardType());
                table_coupon.setVisibility(View.GONE);
                table_coupon_view.setVisibility(View.GONE);
            }else {
                table_coupon.setVisibility(View.VISIBLE);
                table_coupon_view.setVisibility(View.VISIBLE);
                Log.i("elsereward",product.getRewardType());
            }

            try {

                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getCouponDiscount());
                tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));


            } catch (Exception e) {

            }
            tv_package_detail.setText(product.getPackagingSize());
            if (product.getRequiresActivation().contains("False")){
                if (product.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                }else if (product.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add\nTo List");
                }
            }else {
                if (product.getClickCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Activate");
                }else {

                    if (product.getListCount()>0){
                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                        tv_status_detaile.setText("Added");
                    }else if (product.getListCount()==0){
                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                        tv_status_detaile.setText("Add\nTo List");
                    }
                }
            }
            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));
            Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }
            tv_limit.setText(String.valueOf(product.getLimitPerTransection()));
            tv_upc_detail.setText(product.getUPC());
            if (product.getPackagingSize().equalsIgnoreCase("")){
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            }else {
                tv_package_detail.setText(product.getPackagingSize());
            }
            tv_valid_detail.setText(product.getValidityEndDate());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = product.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }
        }else if(product.getPrimaryOfferTypeId()==1){
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_package_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            tv_package_detail.setText(product.getPackagingSize());
            if (product.getListCount()>0){

                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Added");
            }else if (product.getListCount()==0){

                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add\nTo List");
            }
            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));
            Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(product.getUPC());
            tv_valid_detail.setText(product.getValidityEndDate());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = product.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }
        }

        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (product.getListCount()>0){
                    }else if (product.getListCount()==0) {
                        // Log.i("test", relatedItem.getDescription());


                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Fareway response Main", response.toString());
                                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                            imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                            tv_status_detaile.setText("Added");
                                            //product.setListCount(1);
                                           // product.setClickCount(1);
                                            SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                            //reLoadApi();

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Volley error resp", "error----" + error.getMessage());
                                    error.printStackTrace();
                                    // progressDialog.dismiss();
                                }
                            }) {
                                @Override
                                public String getBodyContentType() {
                                    return "application/x-www-form-urlencoded";
                                }

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("UPCCode", product.getUPC());
                                    params.put("CategoryID", product.getCategoryID());
                                    params.put("SalePrice", product.getFinalPrice());
                                    params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                    params.put("OfferDetailId", String.valueOf(product.getOfferDetailId()));
                                    params.put("PersonalCircularID", String.valueOf(product.getPersonalCircularID()));
                                    params.put("ExpirationDate", product.getValidityEndDate());
                                    params.put("ClientID", "1");
                                    params.put("PackagingSize", product.getPackagingSize());
                                    params.put("DisplayPrice", product.getDisplayPrice());
                                    params.put("PageID", String.valueOf(product.getPageID()));
                                    params.put("Description", product.getDescription());
                                    params.put("CouponID", String.valueOf(product.getCouponID()));
                                    params.put("MemberID", String.valueOf(product.getMemberID()));
                                    params.put("DeviceId", "1");

                           /* if (primaryoffertypeid==2){
                                params.put("ClickType", "2");
                            }else {
                                params.put("ClickType", "1");
                            }*/
                                    params.put("ClickType", "1");
                                    params.put("iPositionID", product.getTileNumber());
                                    params.put("OPMOfferID", product.getPricingMasterID());
                                    params.put("AdPrice", product.getAdPrice());
                                    params.put("RegPrice", product.getRegularPrice());
                                    params.put("Savings", product.getSavings());
                                    return params;
                                }

                                //this is the part, that adds the header to the request
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Content-Type", "application/x-www-form-urlencoded");
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //progressDialog.dismiss();
//                displayAlert();
                        }
                    }
                } else {

                }
            }
        });


        // Toast.makeText(getApplicationContext(), "Selected: " + product.getRegularPrice() + ", " + product.getOfferTypeID(), Toast.LENGTH_LONG).show();
    }
//////////////////////////////////////////////////////////////////////////
    @Override
    public void onProductVeritiesSelected(final Product product) {
        Group="";
        productrelated2=product;
        groupItemsList.clear();
        toolbar.setVisibility(View.GONE);
        rv_items.setVisibility(View.GONE);
        navigation.setVisibility(View.GONE);

        participateToolbar.setVisibility(View.VISIBLE);
        participateToolbar.setTitle("Participating Items");
        rv_items_verite.setVisibility(View.VISIBLE);

        rv_items_group.setVisibility(View.VISIBLE);
        if (product.getGroupname()==null){
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        }else if (product.getGroupname()==""){
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        }else if (product.getGroupname()=="null"){
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        }else if (product.getGroupname().length()>0){
            veritiesGroupDetail(product.getCouponID());
        }

        relatedItemsList.clear();
        participateToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x==0){
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                }else {
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                }


            }
        });

        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.RELATEDITEMLIST+"?MemberID="+appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {

                                if (!response.equals("[]")) {
                                    try {
                                        Log.i("Verites response", response.toString());
                                        jsonParam = new JSONArray(response.toString());
                                        progressDialog.dismiss();
                                        if (product.getGroupname().length()>0){
                                            fetchVeritesProduct2(Group);
                                        }else if (product.getGroupname()==null){
                                            fetchVeritesProduct();
                                        }else if (product.getGroupname()=="null"){
                                            fetchVeritesProduct();
                                        }else if (product.getGroupname()==""){
                                            fetchVeritesProduct();
                                        }

                                    } catch (Throwable e) {
                                        progressDialog.dismiss();
                                        Log.i("Excep", "error----" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    progressDialog.dismiss();
                                    alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.incorrect_credentials),
                                            getString(R.string.ok),getString(R.string.alert));
                                    alertDialog.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
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

                        if (product.getPrimaryOfferTypeId()==1){
                            params.put("PriceAssociationCode",product.getPriceAssociationCode());
                            params.put("UPC", product.getUPC());
                            params.put("StoreId", product.getStoreID());
                            params.put("Plateform", "2");
                            params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                            params.put("RelevantUPC", product.getRelevantUPC());
                        }else {
                            params.put("PriceAssociationCode",product.getPriceAssociationCode());
                            params.put("UPC", String.valueOf(product.getCouponID()));
                            params.put("StoreId", product.getStoreID());
                            params.put("Plateform", "2");
                            params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                            params.put("RelevantUPC", product.getRelevantUPC());
                        }

                        return params;
                    }


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
        }

    }

    @Override
    public void onGroupItemSelected(final Group groupItem) {
        Log.i("Select Group ==",groupItem.getGroupname());
        fetchVeritesProduct2(groupItem.getGroupname());
    }

    public static String getDate(int s){
        getCategoryViceData(s);
        rv_category.setVisibility(View.GONE);
        rv_items.setVisibility(View.VISIBLE);
        return null;
    }

    public static String getCategoryViceData(int s){
        if (message.length() == 0) {

        } else {
            String strCategory="";
            int Categoryid=0;
            int category_count = 0;
            int subcat=0;
            if (tmp==0){
                if(x==0){
                    String test1="";
                    for (int i = 0; i < message.length(); i++) {
                        try {
                            if (s==message.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (s==0){
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    Log.i("response","["+test1.toString()+"]");
                    // adding product to product list
                    productList.clear();
                    productList.addAll(items1);
                    // refreshing recycler view
                    customAdapterPersonalPrices.notifyDataSetChanged();
                }else if (x==1){
                    String test1="";
                    for (int i = 0; i < message2.length(); i++) {
                        try {
                            if (s==message2.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message2.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message2.length(); q++) {
                                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message2.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (s==0){
                                JSONObject finalObject = message2.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message2.length(); q++) {
                                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message2.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    // adding product to product list
                    productList.clear();
                    productList.addAll(items1);
                    // refreshing recycler view
                    customAdapterPersonalPrices.notifyDataSetChanged();
                }

            }else {
                if (x==1){
                    String test1="";
                    for (int i = 0; i < message2.length(); i++) {
                        try {
                            if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==message2.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message2.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message2.length(); q++) {
                                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message2.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==0){
                                JSONObject finalObject = message2.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message2.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message2.length(); q++) {
                                        if (tmp == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message2.getJSONObject(q).getInt("CategoryID") == message2.getJSONObject(i).getInt("CategoryID") && message2.getJSONObject(q).getInt("PrimaryOfferTypeId") == message2.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message2.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message2.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message2.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    // adding product to product list
                    productList.clear();
                    productList.addAll(items1);
                    // refreshing recycler view
                    customAdapterPersonalPrices.notifyDataSetChanged();
                }else if (x==0){
                    String test1="";
                    for (int i = 0; i < message.length(); i++) {
                        try {
                            if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==message.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==0){
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=message.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=message.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+message.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+message.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    // adding product to product list
                    productList.clear();
                    productList.addAll(items1);
                    // refreshing recycler view
                    customAdapterPersonalPrices.notifyDataSetChanged();
                }

            }
        }
        return null;
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("audio",result.get(0));
                    searchLoad(result.get(0));
                    edit_txt.setText(result.get(0));
                    x=3;
                    //txvResult.setText(result.get(0));
                }
                break;
        }
    }

    public void  SetProductActivate(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType)
    {

        Log.i("test1", String.valueOf(PrimaryOfferTypeID));
        Log.i("test2", String.valueOf(CouponID));
        Log.i("test3",UPC);
       if (x==0){
           try {
               for (int i = 0; i < message.length(); i++) {

                   if(PrimaryOfferTypeID == 1)
                   {
                       Log.i("apiupc",message.getJSONObject(i).getString("UPC"));
                       if (message.getJSONObject(i).getString("UPC") == UPC) {
                           Log.i("upc",UPC);
                           message.getJSONObject(i).put("ListCount", 1);
                           message.getJSONObject(i).put("ClickCount", 1);
                       }
                   }
                   else
                   {
                       if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                           if (RequireActivation=="True" && PrimaryOfferTypeID==2 && ActivateType==2)
                           {
                               message.getJSONObject(i).put("ClickCount", 1);
                           }
                           else {
                               message.getJSONObject(i).put("ListCount", 1);
                               message.getJSONObject(i).put("ClickCount", 1);
                               Log.i("testjhj", String.valueOf(PrimaryOfferTypeID));
                               Log.i("testhah", String.valueOf(CouponID));
                               Log.i("test",UPC);

                           }


                       }
                   }
               }
           }
           catch (JSONException e) {
               e.printStackTrace();
           }
       }else if (x==1){
           try {
               for (int i = 0; i < message2.length(); i++) {

                   if(PrimaryOfferTypeID == 1)
                   {
                       Log.i("apiupc",message2.getJSONObject(i).getString("UPC"));
                       if (message2.getJSONObject(i).getString("UPC") == UPC) {
                           Log.i("upc",UPC);
                           message2.getJSONObject(i).put("ListCount", 1);
                           message2.getJSONObject(i).put("ClickCount", 1);
                       }
                   }
                   else
                   {
                       if (message2.getJSONObject(i).getInt("CouponID") == CouponID) {
                           if (RequireActivation=="True" && PrimaryOfferTypeID==2 && ActivateType==2)
                           {
                               message2.getJSONObject(i).put("ClickCount", 1);
                           }
                           else {
                               message2.getJSONObject(i).put("ListCount", 1);
                               message2.getJSONObject(i).put("ClickCount", 1);
                               Log.i("testjhj", String.valueOf(PrimaryOfferTypeID));
                               Log.i("testhah", String.valueOf(CouponID));
                               Log.i("test",UPC);

                           }


                       }
                   }
               }
           }
           catch (JSONException e) {
               e.printStackTrace();
           }
       }



    }

    public void  SetProductActivateDetaile(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType)
    {


        Log.i("primary ", String.valueOf(PrimaryOfferTypeID));
        Log.i("morecoupan ", String.valueOf(x));
        if (x==0){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                            // Log.i("primaryinner ", String.valueOf(PrimaryOfferTypeID));
                        }

                    }
                    else
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);


                            }
                        }
                        //
                       /* if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                            if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==2)
                            {
                                message.getJSONObject(i).put("ClickCount", 1);
                            }
                            else {
                                if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==1)
                                {
                                    message.getJSONObject(i).put("ClickCount", 1);
                                    message.getJSONObject(i).put("ListCount", 1);
                                }
                                else if (PrimaryOfferTypeID==2 && ActivateType==1) {
                                    message.getJSONObject(i).put("ListCount", 1);
                                    message.getJSONObject(i).put("ClickCount", 1);
                                }
                                else {
                                    message.getJSONObject(i).put("ListCount", 1);
                                    message.getJSONObject(i).put("ClickCount", 1);
                                }
                                Log.i("test", String.valueOf(PrimaryOfferTypeID));
                                Log.i("test", String.valueOf(CouponID));
                                Log.i("test",UPC);

                            }


                        }*/
                    }
                }
                fetchProduct();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                            Log.i("testttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));
                        }else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            Log.i("elsetestttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));

                        }
                        Log.i("test", String.valueOf(jsonParam.getJSONObject(j)));
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (x==1){
            Log.i("test", String.valueOf(x));
            try {

                for (int i = 0; i < message2.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message2.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message2.getJSONObject(i).put("ListCount", 1);
                            message2.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                            // Log.i("primaryinner ", String.valueOf(PrimaryOfferTypeID));
                        }

                    }
                    else
                    {
                        if (message2.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message2.getJSONObject(i).put("ClickCount", 1);
                            message2.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (message2.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message2.getJSONObject(i).put("ClickCount", 1);


                            }
                        }
                        //
                       /* if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                            if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==2)
                            {
                                message.getJSONObject(i).put("ClickCount", 1);
                            }
                            else {
                                if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==1)
                                {
                                    message.getJSONObject(i).put("ClickCount", 1);
                                    message.getJSONObject(i).put("ListCount", 1);
                                }
                                else if (PrimaryOfferTypeID==2 && ActivateType==1) {
                                    message.getJSONObject(i).put("ListCount", 1);
                                    message.getJSONObject(i).put("ClickCount", 1);
                                }
                                else {
                                    message.getJSONObject(i).put("ListCount", 1);
                                    message.getJSONObject(i).put("ClickCount", 1);
                                }
                                Log.i("test", String.valueOf(PrimaryOfferTypeID));
                                Log.i("test", String.valueOf(CouponID));
                                Log.i("test",UPC);

                            }


                        }*/
                    }
                }
                fetchMoreCoupon();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                            Log.i("testttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));
                        }else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            Log.i("elsetestttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));

                        }
                        Log.i("test", String.valueOf(jsonParam.getJSONObject(j)));
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (x==3){
            try {

                for (int i = 0; i < message3.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("ClickCount", 1);

                        }

                    }
                    else
                    {
                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);

                        }else {
                            if (message3.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message3.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

                    }
                }
                searchProduct();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                            Log.i("testttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));
                        }else {

                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            Log.i("elsetestttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));

                        }
                        Log.i("test", String.valueOf(jsonParam.getJSONObject(j)));
                    }

                    fetchVeritesProduct();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void  SetVeritesActivateDetaile(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType)
    {

      /*  Log.i("req",RequireActivation);
        try {
            for (int i = 0; i < jsonParam.length(); i++) {

                 if (jsonParam.getJSONObject(i).getString("UPC").contains(UPC)) {
                        jsonParam.getJSONObject(i).put("ListCount", 1);
                        jsonParam.getJSONObject(i).put("ClickCount", 1);
                 }
            }
            fetchVeritesProduct();
        }
        catch (JSONException e) {
            e.printStackTrace();
        } */
        Log.i("primary ", String.valueOf(PrimaryOfferTypeID));
        if (x==0){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                        }

                    }
                    else
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

                    }
                }
                fetchProduct();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                            Log.i("testttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));
                        }else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            Log.i("elsetestttt", String.valueOf(jsonParam.getJSONObject(j).getString("UPC").contains(UPC)));

                        }
                        Log.i("test", String.valueOf(jsonParam.getJSONObject(j)));
                    }

                    fetchVeritesProduct();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }else if (x==1){
            try {
                for (int i = 0; i < message2.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {
                        if (message2.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message2.getJSONObject(i).put("ListCount", 1);
                            message2.getJSONObject(i).put("ClickCount", 1);
                        }
                    }else {
                        if (message2.getJSONObject(i).getInt("CouponID") == CouponID) {
                            if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==2)
                            {
                                message2.getJSONObject(i).put("ClickCount", 1);
                            } else {
                                if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==1)
                                {
                                    message2.getJSONObject(i).put("ClickCount", 1);
                                    message2.getJSONObject(i).put("ListCount", 1);
                                } else if (PrimaryOfferTypeID==2 && ActivateType==1) {

                                } else {
                                    message2.getJSONObject(i).put("ListCount", 1);
                                    message2.getJSONObject(i).put("ClickCount", 1);
                                }
                                Log.i("test", String.valueOf(PrimaryOfferTypeID));
                                Log.i("test", String.valueOf(CouponID));
                                Log.i("test",UPC);

                            }


                        }
                    }
                }
                fetchMoreCoupon();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onRelatedItemSelected(final RelatedItem relatedItem) {
        group_count_text.setVisibility(View.GONE);
        rv_items_group.setVisibility(View.GONE);
        rv_items_verite.setVisibility(View.GONE);
        participateToolbar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        DetaileToolbar.setVisibility(View.VISIBLE);
        DetaileToolbar.setTitle("Detail");
        DetaileToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);


                rv_items_group.setVisibility(View.VISIBLE);
                rv_items_verite.setVisibility(View.VISIBLE);
                participateToolbar.setVisibility(View.VISIBLE);
                //Log.i("testttt","anshyuman"+Group);
                if (Group.length()>0){
                    group_count_text.setVisibility(View.VISIBLE);
                }
                fetchVeritesProduct2(Group);

            }
        });
        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
        TextView tv_reg_price_detail = (TextView)findViewById(R.id.tv_reg_price_detail);
        TextView tv_save_detail = (TextView)findViewById(R.id.tv_save_detail);
        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
        TextView tv_valid_detail = (TextView)findViewById(R.id.tv_valid_detail);
        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
        ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);

        imv_item_detaile.setImageDrawable(getResources().getDrawable(R.drawable.item));
        final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
        final LinearLayout circular_layout_detaile= (LinearLayout) findViewById(R.id.circular_layout_detaile);
        LinearLayout bottomLayout_detaile= (LinearLayout) findViewById(R.id.bottomLayout_detaile);
        TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
        TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
        TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
        TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
        TableRow table_save = (TableRow) findViewById(R.id.table_save);
        TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
        TableRow table_package = (TableRow) findViewById(R.id.table_package);
        TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
        TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
        TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);

        if (relatedItem.getPrimaryOfferTypeId()==3){
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            tv_package_detail.setText(relatedItem.getPackagingSize());
            Log.i("listCount", String.valueOf(relatedItem.getListCount()));

            if (relatedItem.getPackagingSize().equalsIgnoreCase("")){
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            }else {
                tv_package_detail.setText(relatedItem.getPackagingSize());
            }


            if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                }else if (relatedItem.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add\nTo List");
                }
            }else {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Activate");
            }

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));
            Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+relatedItem.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(relatedItem.getUPC());
            tv_valid_detail.setText(relatedItem.getValidityEndDate());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = relatedItem.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }


        }else if(relatedItem.getPrimaryOfferTypeId()==2){
            table_limit.setVisibility(View.VISIBLE);
            table_limit_view.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_package_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.VISIBLE);
            table_coupon_view.setVisibility(View.VISIBLE);


            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getCouponDiscount());
                tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }
            tv_package_detail.setText(relatedItem.getPackagingSize());


            if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                }else if (relatedItem.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add\nTo List");
                }
            }else {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Activate");
            }

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));
            Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+relatedItem.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(relatedItem.getUPC());
            tv_valid_detail.setText(relatedItem.getValidityEndDate());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = relatedItem.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }
        }else if(relatedItem.getPrimaryOfferTypeId()==1){
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.VISIBLE);
            table_package_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            tv_package_detail.setText(relatedItem.getPackagingSize());
            if (relatedItem.getListCount()>0){
                Log.i("iflistCount", String.valueOf(relatedItem.getListCount()));
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Added");
            }else if (relatedItem.getListCount()==0){
                Log.i("elselistCount", String.valueOf(relatedItem.getListCount()));
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add\nTo List");
            }
            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));
            Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars);

            tv_reg_price_detail.setText("$ "+relatedItem.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(relatedItem.getUPC());
            tv_valid_detail.setText(relatedItem.getValidityEndDate());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            ImageView bindImage = (ImageView)findViewById(R.id.imv_item_detaile);
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                String largeImagePath = "http://fwstaging.immdemo.net/webapiaccessclient/images/GEnoimage.jpg";
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }else {
                String largeImagePath = relatedItem.getLargeImagePath();
                DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(bindImage);
                downloadTask.execute(largeImagePath);
            }
        }

        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (relatedItem.getListCount()>0){

                    }else if (relatedItem.getListCount()==0) {
                        // Log.i("test", relatedItem.getDescription());


                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Fareway response Main", response.toString());
                                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                            imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                            tv_status_detaile.setText("Added");
                                            if (relatedItem.getPrimaryOfferTypeId()==3 || relatedItem.getPrimaryOfferTypeId()==2){
                                                SetProductActivateDetaile
                                                        (relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);
                                                groupcount=1;

                                                veritiesGroupDetail2(relatedItem.getCouponID());

                                               // onProductVeritiesSelected(productrelated2);
                                            }

                                            SetVeritesActivateDetaile
                                                    (relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Volley error resp", "error----" + error.getMessage());
                                    error.printStackTrace();
                                    // progressDialog.dismiss();
                                }
                            }) {
                                @Override
                                public String getBodyContentType() {
                                    return "application/x-www-form-urlencoded";
                                }

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("UPCCode", relatedItem.getUPC());
                                    params.put("CategoryID", relatedItem.getCategoryID());
                                    params.put("SalePrice", relatedItem.getFinalPrice());
                                    params.put("PrimaryOfferTypeId", String.valueOf(relatedItem.getPrimaryOfferTypeId()));
                                    params.put("OfferDetailId", String.valueOf(relatedItem.getOfferDetailId()));
                                    params.put("PersonalCircularID", String.valueOf(relatedItem.getPersonalCircularID()));
                                    params.put("ExpirationDate", relatedItem.getValidityEndDate());
                                    params.put("ClientID", "1");
                                    params.put("PackagingSize", relatedItem.getPackagingSize());
                                    params.put("DisplayPrice", relatedItem.getDisplayPrice());
                                    params.put("PageID", String.valueOf(relatedItem.getPageID()));
                                    params.put("Description", relatedItem.getDescription());
                                    params.put("CouponID", String.valueOf(relatedItem.getCouponID()));
                                    params.put("MemberID", String.valueOf(relatedItem.getMemberID()));
                                    params.put("DeviceId", "1");

                                    params.put("ClickType", "1");
                                    params.put("iPositionID", relatedItem.getTileNumber());
                                    params.put("OPMOfferID", relatedItem.getPricingMasterID());
                                    params.put("AdPrice", relatedItem.getAdPrice());
                                    params.put("RegPrice", relatedItem.getRegularPrice());
                                    params.put("Savings", relatedItem.getSavings());
                                    return params;
                                }

                                //this is the part, that adds the header to the request
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Content-Type", "application/x-www-form-urlencoded");
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //progressDialog.dismiss();
//                displayAlert();
                        }
                    }
                } else {

                }
            }
        });


     //   Toast.makeText(getApplicationContext(), "Selected: " + product.getRegularPrice() + ", " + product.getOfferTypeID(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRelatedItemSelected2(final RelatedItem relatedItem) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                if (relatedItem.getPrimaryOfferTypeId()==3|| relatedItem.getPrimaryOfferTypeId()==2 ){
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);
                               groupcount=1;

                                   //onProductVeritiesSelected(productrelated2);
                                    veritiesGroupDetail(relatedItem.getCouponID());

                                }else if (relatedItem.getPrimaryOfferTypeId()==1){
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                      //  progressDialog.dismiss();
                        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
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
                        params.put("UPCCode", relatedItem.getUPC());
                        params.put("CategoryID", String.valueOf(relatedItem.getCategoryID()));
                        params.put("SalePrice", relatedItem.getFinalPrice());
                        params.put("PrimaryOfferTypeId", String.valueOf(relatedItem.getPrimaryOfferTypeId()));
                        params.put("OfferDetailId", String.valueOf(relatedItem.getOfferDetailId()));
                        params.put("PersonalCircularID", String.valueOf(relatedItem.getPersonalCircularID()));
                        params.put("ExpirationDate", relatedItem.getValidityEndDate());
                        params.put("ClientID", "1");
                        params.put("PackagingSize", relatedItem.getPackagingSize());
                        params.put("DisplayPrice", relatedItem.getDisplayPrice());
                        params.put("PageID", String.valueOf(relatedItem.getPageID()));
                        params.put("Description", relatedItem.getDescription());
                        params.put("CouponID", String.valueOf(relatedItem.getCouponID()));
                        params.put("MemberID", String.valueOf(relatedItem.getMemberID()));
                        params.put("DeviceId", "1");

                        params.put("ClickType", "1");
                        params.put("iPositionID", relatedItem.getTileNumber());
                        params.put("OPMOfferID", relatedItem.getPricingMasterID());
                        params.put("AdPrice", relatedItem.getAdPrice());
                        params.put("RegPrice", relatedItem.getRegularPrice());
                        params.put("Savings", relatedItem.getSavings());

                        return params;
                    }

                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                       // params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
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
        }


    }

    private void fetchVeritesProduct(){
        if (jsonParam.length() == 0) {
            //no students
        } else {
            List<RelatedItem> items = new Gson().fromJson(jsonParam.toString(), new TypeToken<List<RelatedItem>>() {
            }.getType());
            // adding product to product list
            relatedItemsList.clear();
            relatedItemsList.addAll(items);
            // refreshing recycler view
            customAdapterParticipateItems.notifyDataSetChanged();
        }
    }

    private void fetchVeritesProduct2(String group){
        if (jsonParam.length() == 0) {
            //no students
        } else {
           /* List<RelatedItem> items = new Gson().fromJson(jsonParam.toString(), new TypeToken<List<RelatedItem>>() {
            }.getType());

            relatedItemsList.clear();
            relatedItemsList.addAll(items);

            customAdapterParticipateItems.notifyDataSetChanged();*/


            String groupData="";
            //Log.i("for",group.replace(":","").replace(" ","")+jsonParam.length());
            for (int i = 0; i < jsonParam.length(); i++) {
                // Log.i("test", String.valueOf(tmp));
                try {
                    if (jsonParam.getJSONObject(i).getString("Filter").contains(group)) {
                        Group=jsonParam.getJSONObject(i).getString("Filter");
                        //Log.i("forinner",group.replace(":","").replace(" ",""));
                        JSONObject finalObject = jsonParam.getJSONObject(i);
                        // Log.i("test", String.valueOf(finalObject));
                        groupData +=(groupData==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            List<RelatedItem> items = new Gson().fromJson("["+groupData.toString()+"]", new TypeToken<List<RelatedItem>>() {
            }.getType());
            relatedItemsList.clear();
            relatedItemsList.addAll(items);
            // refreshing recycler view
            customAdapterParticipateItems.notifyDataSetChanged();

        }
    }

    public void veritiesGroupDetail(int CouponId){

            group_count_text.setVisibility(View.VISIBLE);
            try {
                for (int p = 0; p < message.length(); p++) {
                    int a=message.getJSONObject(p).getInt("CouponID");
                    if (a == CouponId) {
                        String GroupStr =message.getJSONObject(p).getString("Groupname");

                        group_count_text.setText(GroupStr);

                        String GroupData="";
                        String selectGroupLimit="";
                        String selectGroupLimitDetail="";
                        if(Group=="") {
                            if (GroupStr.split(",").length > 0)
                                Group = GroupStr.split(",")[0].split(":")[0];

                        }
                        Log.i("testlog",Group);
                        for(int i = 0; GroupStr.split(",").length>i; i++)
                        {

                            selectGroupLimitDetail +=(selectGroupLimitDetail==""?" You must buy "+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+" from Group "+GroupStr.split(",")[i].split(":")[0] :" " +
                                    "and "+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+" from Group "+GroupStr.split(",")[i].split(":")[0]+"");


                            selectGroupLimit +=(selectGroupLimit==""?GroupStr.split(",")[i].split(":")[0]+":"+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1]):"," +
                                    GroupStr.split(",")[i].split(":")[0]+":"+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+"");


                            GroupData+=(GroupData==""?"{\"Groupname\": \""+GroupStr.split(",")[i].split(":")[0]+"\"}":","+"{\"Groupname\": \""+GroupStr.split(",")[i].split(":")[0]+"\"}");

                            Log.i("for",GroupStr.split(",")[i].split(":")[1]);
                        }
                        group_count_text.setText(selectGroupLimitDetail);
                        message.getJSONObject(p).put("Groupname", selectGroupLimit);
                        groupcount=0;
                        Log.i("new ",selectGroupLimit);
                        List<Group> items = new Gson().fromJson(" ["+GroupData+"]", new TypeToken<List<Group>>() {
                        }.getType());

                        // adding contacts to contacts list
                        if(GroupStr.split(",").length>1) {
                            groupItemsList.clear();
                            groupItemsList.addAll(items);
                            customGroupAdapter.notifyDataSetChanged();

                        }
                        Log.i("testcoup", String.valueOf(a));
                    }

                }
                fetchProduct();
            }
            catch (Exception e)
            {

            }



    }

    public void veritiesGroupDetail2(int CouponId){

        //group_count_text.setVisibility(View.VISIBLE);
        try {
            for (int p = 0; p < message.length(); p++) {
                int a=message.getJSONObject(p).getInt("CouponID");
                if (a == CouponId) {
                    String GroupStr =message.getJSONObject(p).getString("Groupname");
                   // fetchVeritesProduct2(GroupStr.split(",")[0].split(":")[0]);
                    group_count_text.setText(GroupStr);

                    String GroupData="";
                    String selectGroupLimit="";
                    String selectGroupLimitDetail="";
                    if(Group=="") {
                        if (GroupStr.split(",").length > 0)
                            Group = GroupStr.split(",")[0].split(":")[0];

                    }
                    Log.i("testlog",Group);
                    for(int i = 0; GroupStr.split(",").length>i; i++)
                    {

                        selectGroupLimitDetail +=(selectGroupLimitDetail==""?" You must buy "+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+" from Group "+GroupStr.split(",")[i].split(":")[0] :" " +
                                "and "+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+" from Group "+GroupStr.split(",")[i].split(":")[0]+"");


                        selectGroupLimit +=(selectGroupLimit==""?GroupStr.split(",")[i].split(":")[0]+":"+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1]):"," +
                                GroupStr.split(",")[i].split(":")[0]+":"+(Group.contains(GroupStr.split(",")[i].split(":")[0])==true? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount)>0?Integer.parseInt(GroupStr.split(",")[i].split(":")[1])-groupcount:"0"):GroupStr.split(",")[i].split(":")[1])+"");


                        GroupData+=(GroupData==""?"{\"Groupname\": \""+GroupStr.split(",")[i].split(":")[0]+"\"}":","+"{\"Groupname\": \""+GroupStr.split(",")[i].split(":")[0]+"\"}");

                        Log.i("for",GroupStr.split(",")[i].split(":")[1]);
                    }
                    group_count_text.setText(selectGroupLimitDetail);
                    message.getJSONObject(p).put("Groupname", selectGroupLimit);
                    groupcount=0;
                    Log.i("new ",selectGroupLimit);
                    List<Group> items = new Gson().fromJson(" ["+GroupData+"]", new TypeToken<List<Group>>() {
                    }.getType());

                    // adding contacts to contacts list
                    groupItemsList.clear();
                    groupItemsList.addAll(items);
                    customGroupAdapter.notifyDataSetChanged();


                    Log.i("testcoup", String.valueOf(a));
                }

            }
            fetchProduct();
        }
        catch (Exception e)
        {

        }



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