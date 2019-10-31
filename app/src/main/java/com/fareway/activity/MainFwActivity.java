package com.fareway.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
/*
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;*/
import android.text.Html;
import android.text.Spanned;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
/*
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;*/
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.fareway.adapter.CustomAdapterFilter;
import com.fareway.adapter.CustomAdapterParticipateItems;
import com.fareway.adapter.CustomAdapterPersonalPrices;
import com.fareway.adapter.CustomGroupAdapter;
import com.fareway.adapter.ShoppingListAdapter;
import com.fareway.adapter.SwipeToDeleteCallback;
import com.fareway.controller.FarewayApplication;
import com.fareway.model.Category;
import com.fareway.model.Group;
import com.fareway.model.Product;
import com.fareway.model.RelatedItem;
import com.fareway.model.Shopping;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.ConnectivityReceiver;
import com.fareway.utility.Constant;
import com.fareway.utility.DividerRVDecoration;
import com.fareway.utility.NetworkUtils;
import com.fareway.utility.UserAlertDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFwActivity extends AppCompatActivity
        implements CustomAdapterPersonalPrices.CustomAdapterPersonalPricesListener,
        CustomAdapterParticipateItems.CustomAdapterParticipateItemsListener, CustomGroupAdapter.CustomAdapterGroupItemsListener,
        ShoppingListAdapter.ShoppingListAdapterListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = MainFwActivity.class.getSimpleName();
    private DrawerLayout drawer;
    public static Toolbar toolbar;
    private Toolbar DetaileToolbar;
    private Toolbar participateToolbar;
    //private NavigationView navigationView;
    private TextView mTextMessage, tv_uname, tv_filter_by_category, tv_filter_by_offer, tv_type, tv_number_item, add_item;
    private ImageView imv_view_list, imv_all_delete, imv_logo, imv_status_verities;
    private static RecyclerView rv_items, rv_items_verite, rv_items_group;
    private static CustomAdapterParticipateItems customAdapterParticipateItems;
    private static CustomGroupAdapter customGroupAdapter;
    ArrayList<RelatedItem> relatedItemsList;
    ArrayList<Group> groupItemsList;
    public JSONArray jsonParam;
    public JSONArray jsonShoppingParam;
    public static ArrayList<Product> productList;
    private static CustomAdapterPersonalPrices customAdapterPersonalPrices;
    public static boolean singleView = true;
    private Activity activity;
    public static boolean pdView = true;
    public static boolean offferShort = false;
    public static boolean savingsShort = false;
    public static boolean categoryShort = false;
    public static boolean singleLable = false;
    public static boolean multiLable = false;
    public static boolean searchLable = false;
    public static boolean couponTile = true;
    public static boolean cdOddView = false;
    public static String UPCOddView = "";
    private BottomNavigationView navigation;
    RelativeLayout main;
    static RelativeLayout search_message;
    private PopupWindow popupWindow;
    public static String comeFrom;
    AppUtilFw appUtil;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    private SearchView searchView;
    private LinearLayout rowLayout, rowLayoutClearFilter, rowLayout0, rowLayout1, rowLayout2, rowLayout3;
    private LinearLayout rowLayoutShort, ShortClearFilter, rowLayout0Short, rowLayout1Short, rowLayout2Short, rowLayout3Short;
    public static int tmp = 0;
    public String OtherCoupon = "0";

    public String OtherCouponmulti = "0";
    public static JSONArray message;
    public static JSONArray morecouponlist;
    public static JSONArray message3;
    public static JSONArray messageCategory;
    public static JSONArray shoppingId;
    private static RecyclerView rv_category, rv_shopping_list_items;
    private ArrayList<Category> categoryList;

    public static JSONArray shopping;
    public static JSONArray activatedOffer;
    private CustomAdapterFilter customAdapterFilter;
    private ArrayList<Shopping> shoppingArrayList;
    private ShoppingListAdapter shoppingListAdapter;

    public static int x = 0;
    int y = 0;
    int z = 1;
    int c = 0;
    int participate = 1;
    int shopper = 0;
    public int a = 0;
    int b = 0;
    private EditText et_search;
    private RequestQueue mQueue;
    public static RequestQueue mQueue2;
    public static AppUtilFw appUtil2;
    ImageView submit_btn, imv_micro_recorder, print, email;
    EditText edit_txt;
    ScrollView scrollView;
    TextView group_count_text, header_title;
    String Group = "";
    int groupcount = 0;
    Product productrelated2;
    private View notificationBadge;
    private LinearLayout shopping_list_header;
    TextView tv, shopping_date;
    TextView all_Varieties_activate, tv_fareway_flag;
    LinearLayout liner_all_Varieties_activate, linear_shopping_list_tab, linear_coupon_tab, liner_detail_add_item, liner_add_sub_button;
    Button shopping_list_fragment, activated_offer_fragment;
    TextView add_minus_detail, tv_quantity_detail, add_plus_detail, btn_return_pd, btn_try_another_search;
    RelativeLayout relative_main;
    int qty = 0;
    String shoppingCouponID = "";

    Boolean IPValue;
    LocationManager locationManager;
    String IPaddress;
    String osName;
    String myVersion;
    String Latitude = "";
    String Longitude = "";
    int sdkVersion;
    double diagonalInches;
    String deviceType = "";
    public static boolean locationGet = true;

    Location mylocation;
    GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fw);
        activity = MainFwActivity.this;
        mQueue = FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil = new AppUtilFw(activity);
        userAlertDialog = new UserAlertDialog(activity);

        mQueue2 = FarewayApplication.getmInstance(this).getmRequestQueue();
        appUtil2 = new AppUtilFw(activity);
        comeFrom = getIntent().getStringExtra("comeFrom");


        /*ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        getLocation();*/
        changeStore();
        linkUIElements();
        /*String osName= Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        Log.i("osName",osName);*/
        /*String myVersion = android.os.Build.VERSION.RELEASE;
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        Log.i("myVersion",myVersion);
        Log.i("sdkVersion", String.valueOf(sdkVersion));*/
        //NetwordDetect();
        /*String DeviceId =  android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.i("device",DeviceId);*/
        osName = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        myVersion = android.os.Build.VERSION.RELEASE;
        sdkVersion = android.os.Build.VERSION.SDK_INT;
        NetwordDetect();

        imv_status_verities = findViewById(R.id.imv_status_verities);
        tv_fareway_flag = findViewById(R.id.tv_fareway_flag);


        relative_main = findViewById(R.id.relative_main);

        add_minus_detail = findViewById(R.id.add_minus_detail);
        tv_quantity_detail = findViewById(R.id.tv_quantity_detail);
        add_plus_detail = findViewById(R.id.add_plus_detail);

        linear_shopping_list_tab = findViewById(R.id.linear_shopping_list_tab);
        linear_coupon_tab = findViewById(R.id.linear_coupon_tab);
        search_message = findViewById(R.id.search_message);
        search_message.setVisibility(View.GONE);
//        shopping_date=findViewById(R.id.shopping_date);

        shopping_list_fragment = findViewById(R.id.shopping_list_fragment);
        shopping_list_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // shoppingListLoad();
                //fetchShoppingListLoad();
                // rv_shopping_list_items.setVisibility(View.VISIBLE);
                // shopping_date.setText("Price");
                linear_shopping_list_tab.setVisibility(View.VISIBLE);
                linear_coupon_tab.setVisibility(View.GONE);

                if (z == 0) {
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z = 1;
                    fetchShopping();
                } else {
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z = 1;
                }

            }
        });
        activated_offer_fragment = findViewById(R.id.activated_offer_fragment);
        activated_offer_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activatedOffersListIdLoad();
                //shopping_date.setText("Expiration Date");
                //fetchActivatedOffer();
                linear_coupon_tab.setVisibility(View.VISIBLE);
                linear_shopping_list_tab.setVisibility(View.GONE);
                if (z == 1) {
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z = 0;
                    fetchActivatedOffer();
                } else {
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z = 0;
                    fetchActivatedOffer();
                }
                // rv_shopping_list_items.setVisibility(View.GONE);
            }
        });
        email = findViewById(R.id.email);
        /*email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withEditText();
            }
        });*/
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
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        sendEmailShoppingList(userInput.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        print = findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainFwActivity.this,ShoppingListPrint.class));
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid="+appUtil.getPrefrence("ShopperID")+"&memberid="+appUtil.getPrefrence("MemberId")));
                startActivity(browserIntent);*/

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

        btn_return_pd = findViewById(R.id.btn_return_pd);
        btn_return_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*rv_items.setVisibility(View.VISIBLE);
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                fetchProduct();
                x=0;*/
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                pdView = true;
                couponTile = true;
                savingsShort = false;
                offferShort = false;
                categoryShort = false;

                participate = 1;
                tmp = 0;
                x = 0;
                searchLable = false;
                header_title.setVisibility(View.GONE);
                fetchProduct();
            }
        });
        btn_try_another_search = findViewById(R.id.btn_try_another_search);
        btn_try_another_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*rv_items.setVisibility(View.VISIBLE);
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                fetchProduct();
                x=0;*/
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                pdView = true;
                couponTile = true;
                savingsShort = false;
                offferShort = false;
                categoryShort = false;

                participate = 1;
                tmp = 0;
                x = 0;
                searchLable = false;
                header_title.setVisibility(View.GONE);
                fetchProduct();
            }
        });

        edit_txt = (EditText) findViewById(R.id.search_edit);
        edit_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (edit_txt.getText().toString().isEmpty()) {

                    } else {

                        if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                            submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                            submit_btn.setTag(1);
                            String search = edit_txt.getText().toString();
                            participate = 0;
                            pdView = false;
                            couponTile = false;
                            x = 3;
                            searchLable = true;
                            navigation.setVisibility(View.VISIBLE);
                            header_title.setVisibility(View.GONE);
                            searchLoad(search);
                        } else {
                            search_message.setVisibility(View.GONE);
                            navigation.setVisibility(View.VISIBLE);
                            submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                            submit_btn.setTag(0);
                            edit_txt.getText().clear();
                            pdView = true;
                            couponTile = true;
                            savingsShort = false;
                            offferShort = false;
                            categoryShort = false;

                            participate = 1;
                            tmp = 0;
                            x = 0;
                            searchLable = false;
                            header_title.setVisibility(View.GONE);
                            fetchProduct();
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
                if (edit_txt.getText().toString().isEmpty()) {

                } else {

                    if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                        submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                        submit_btn.setTag(1);
                        String search = edit_txt.getText().toString();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit_txt.getWindowToken(), 0);

                        participate = 0;
                        pdView = false;
                        couponTile = false;
                        navigation.setVisibility(View.VISIBLE);
                        x = 3;
                        header_title.setVisibility(View.GONE);
                        searchLable = true;
                        searchLoad(search);

                    } else {
                        //
                        search_message.setVisibility(View.GONE);
                        navigation.setVisibility(View.VISIBLE);
                        submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                        submit_btn.setTag(0);
                        edit_txt.getText().clear();

                        participate = 1;
                        pdView = true;
                        couponTile = true;
                        savingsShort = false;
                        offferShort = false;
                        categoryShort = false;
                        tmp = 0;
                        x = 0;
                        //header_title visible
                        header_title.setVisibility(View.GONE);
                        searchLable = false;
                        fetchProduct();
                    }
                }
            }
        });
        imv_micro_recorder = (ImageView) findViewById(R.id.imv_micro_recorder);
        imv_micro_recorder.setTag(0);
        imv_micro_recorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(imv_micro_recorder.getTag().toString()) == 0) {
                    imv_micro_recorder.setImageResource(R.drawable.ic_clear_black_24dp);
                    imv_micro_recorder.setTag(1);
                    participate = 0;
                    pdView = false;
                    couponTile = false;
                    navigation.setVisibility(View.VISIBLE);
                    x = 3;
                    header_title.setVisibility(View.GONE);
                    searchLable = true;
                    getSpeechInput(v);
                } else {
                    imv_micro_recorder.setImageResource(R.drawable.micro_recorder);
                    imv_micro_recorder.setTag(0);
                    edit_txt.getText().clear();
                    search_message.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    /*fetchProduct();
                    x=0;
                    searchLable=false;*/
                    //
                    participate = 1;
                    pdView = true;
                    couponTile = true;
                    savingsShort = false;
                    offferShort = false;
                    categoryShort = false;
                    tmp = 0;
                    x = 0;
                    //header_title visible
                    header_title.setVisibility(View.GONE);
                    searchLable = false;
                    fetchProduct();
                }
            }
        });

        liner_detail_add_item = findViewById(R.id.liner_detail_add_item);
        liner_detail_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liner_detail_add_item.setVisibility(View.VISIBLE);
                liner_add_sub_button.setVisibility(View.VISIBLE);
            }
        });
        liner_add_sub_button = findViewById(R.id.liner_add_sub_button);

        relative_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liner_detail_add_item.setVisibility(View.VISIBLE);
                liner_add_sub_button.setVisibility(View.VISIBLE);
            }
        });
        //checkLocationPermission();

        setupClient();

        //
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, " >> onconnected");
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@androidx.annotation.NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            Double latitude = mylocation.getLatitude();
            Double longitude = mylocation.getLongitude();
            Log.d(TAG, " >> Lat >> " + latitude + " " + "Long >> " + longitude);

            Latitude = String.valueOf(latitude);
            Longitude = String.valueOf(longitude);
            if (Latitude != "" && Longitude != "" && locationGet == true && appUtil.getPrefrence("SaveLogin").equalsIgnoreCase("no")) {
                //login();
                if (diagonalInches >= 6.80) {
                    deviceType = "tablet";
                } else {
                    deviceType = "mobile";
                }
                saveLogin();
                locationGet = false;

            } else {

            }
        }
    }

    private void setupClient() {
        Log.d(TAG, " >> setupclient");
        googleApiClient = new GoogleApiClient.Builder(MainFwActivity.this).
                enableAutoManage(this, 0, this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
        googleApiClient.connect();
    }

    private void getMyLocation() {
        Log.d(TAG, " >> getmylocation");
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(MainFwActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(300000);
                    locationRequest.setFastestInterval(300000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(MainFwActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically

                                        status.startResolutionForResult(MainFwActivity.this, REQUEST_CHECK_SETTINGS_GPS);


                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    //finish();
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    finish();
                                    break;
                                case LocationSettingsStatusCodes.CANCELED:
                                    Log.d(TAG, ">> cancled");
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    private void checkPermissions() {
        Log.d(TAG, " >> checkPermissions");
        int permissionLocation = ContextCompat.checkSelfPermission(MainFwActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            //getMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        /*int permissionLocation = ContextCompat.checkSelfPermission(MainFwActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }*/
        Log.d(TAG, " >> onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    getMyLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean checkLoctionSetting() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {Log.e(TAG, "Location provider error " + ex.getMessage());}

        if(!gps_enabled && !network_enabled) {
            return false;
        } else {
            return true;
        }
    }

    public void addBadgeView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.view_notification_badge, bottomNavigationMenuView, false);
        tv = badge.findViewById(R.id.notification_badge);

        tv.setText(String.valueOf(0));
        itemView.addView(badge);

    }


    private void linkUIElements() {
        add_item = findViewById(R.id.add_item);
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
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        addShoppingItem(userInput.getText().toString());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        imv_all_delete = findViewById(R.id.imv_all_delete);
        liner_all_Varieties_activate = findViewById(R.id.liner_all_Varieties_activate);
        all_Varieties_activate = findViewById(R.id.all_Varieties_activate);
        imv_logo = findViewById(R.id.imv_logo);
        imv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponTile = true;
                x = 0;
                participate = 1;
                finish();
            }
        });
        header_title = findViewById(R.id.header_title);
        header_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_txt.getWindowToken(), 0);

            }
        });
        shopping_list_header = findViewById(R.id.shopping_list_header);
        tv_number_item = findViewById(R.id.tv_number_item);

        Display display = ((Activity) activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        final double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

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

        shoppingArrayList = new ArrayList<>();
        rv_shopping_list_items = (RecyclerView) findViewById(R.id.rv_shopping_list_items);
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingArrayList, this, this, this, this);
        RecyclerView.LayoutManager mLayoutManagerShoppingList = new LinearLayoutManager(activity);
        rv_shopping_list_items.setLayoutManager(mLayoutManagerShoppingList);
        rv_shopping_list_items.setAdapter(shoppingListAdapter);
        //Drawable dividerDrawableShoppingList = ContextCompat.getDrawable(activity, R.drawable.divider);
        //rv_shopping_list_items.addItemDecoration(new DividerRVDecoration(dividerDrawableShoppingList));

        //swep item
        //enableSwipeToDeleteAndUndo();

        rowLayout = findViewById(R.id.rowLayout);
        rowLayoutClearFilter = findViewById(R.id.rowLayoutClearFilter);
        rowLayout0 = findViewById(R.id.rowLayout0);
        rowLayout1 = findViewById(R.id.rowLayout1);
        rowLayout2 = findViewById(R.id.rowLayout2);
        rowLayout3 = findViewById(R.id.rowLayout3);

        rowLayoutShort = findViewById(R.id.rowLayoutShort);
        ShortClearFilter = findViewById(R.id.ShortClearFilter);
        rowLayout0Short = findViewById(R.id.rowLayout0Short);
        rowLayout1Short = findViewById(R.id.rowLayout1Short);
        rowLayout2Short = findViewById(R.id.rowLayout2Short);
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
        tv_type = findViewById(R.id.tv_type);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if (comeFrom.equalsIgnoreCase("mpp")) {
            // navigation.getMenu().findItem(R.id.moreCoupons).setTitle("More Savings");

            //    header_title.setText("My Personal Ad");
        } else if (comeFrom.equalsIgnoreCase("moreOffer")) {
            // navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Personal Ad");
            //  header_title.setText("More Savings");

        }


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rv_items_group = (RecyclerView) findViewById(R.id.rv_items_group);
        groupItemsList = new ArrayList<>();
        customGroupAdapter = new CustomGroupAdapter(this, groupItemsList, this);
        RecyclerView.LayoutManager mLayoutManager5 = new GridLayoutManager(this, 5);
        rv_items_group.setLayoutManager(mLayoutManager5);
        rv_items_group.setAdapter(customGroupAdapter);


        productList = new ArrayList<>();

        imv_view_list = (ImageView) findViewById(R.id.imv_view_list);
        imv_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!singleView) {
                    if (diagonalInches >= 9.27) {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView = true;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    } else {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView = true;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
                        rv_items_verite.setLayoutManager(mLayoutManager4);

                    }

                } else {
                    if (diagonalInches >= 9.00) {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 4);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView = false;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 4);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    } else {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
                        rv_items.setLayoutManager(mLayoutManager);
                        singleView = false;
                        RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
                        rv_items_verite.setLayoutManager(mLayoutManager4);
                    }

                }
            }
        });

        rv_items_verite = (RecyclerView) findViewById(R.id.rv_items_verite);
        relatedItemsList = new ArrayList<>();
        customAdapterParticipateItems = new CustomAdapterParticipateItems(this, relatedItemsList, this, this, this, this, this);
        // RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
        // rv_items_verite.setLayoutManager(mLayoutManager4);
        rv_items_verite.setAdapter(customAdapterParticipateItems);

        rv_items = (RecyclerView) findViewById(R.id.rv_items);
        customAdapterPersonalPrices = new CustomAdapterPersonalPrices(this, productList, this, this, this, this, this, this);
        //  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 1);
        //  rv_items.setLayoutManager(mLayoutManager);
        rv_items.setAdapter(customAdapterPersonalPrices);

        if (diagonalInches >= 6.80) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 4);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 4);
            rv_items.setLayoutManager(mLayoutManager);
            singleView = false;
        } else if (diagonalInches >= 7.16) {
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
            rv_items.setLayoutManager(mLayoutManager);
            singleView = false;
        } else {
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
            rv_items.setLayoutManager(mLayoutManager);
            singleView = false;

        }

        messageLoad();
        // moreCouponLoad();
        addBadgeView();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
          /*  if (i == R.id.moreCoupons) {

                Intent i2 = new Intent(activity, MainFwActivity.class);
                if (comeFrom.equalsIgnoreCase("mpp")&&x==0) {
                    i2.putExtra("comeFrom", "moreOffer");
                    comeFrom="moreOffer";
                    fetchMoreCoupon();
                    x=1;
                    navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Personal Ad");
                   // header_title.setText("More Savings");


                } else if(x==1) {
                    i2.putExtra("comeFrom", "mpp");
                    comeFrom="mpp";
                    x=0;
                    tmp=0;
                    navigation.getMenu().findItem(R.id.moreCoupons).setTitle("More Saving");
                   // header_title.setText("My Personal Ad");
                    fetchProduct();

                }
                return true;
            }else*/
            if (i == R.id.home_button) {
                couponTile = true;
                x = 0;
                participate = 1;
                finish();
                return true;
            } else if (i == R.id.savings) {
                // fetchProduct();
                Intent i1 = new Intent(activity, SavingFw.class);
                startActivity(i1);
                return true;
            } else if (i == R.id.filter) {
                //rv_shopping_list_items.setVisibility(View.GONE);
                PopupMenu popupMenu = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation, Gravity.END);
                } else {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation);
                }
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                if (x == 1) {
                    popupMenu.inflate(R.menu.shopping_filter);
                } else if (x == 0) {
                    popupMenu.inflate(R.menu.filter);
                } else if (x == 3) {
                    popupMenu.inflate(R.menu.filter);
                }

                popupMenu.show();
                return true;
            } else if (i == R.id.ShoppingList) {
                //startActivity(new Intent(MainFwActivity.this,ShoppingFw.class));
                if (x == 0 || x == 3) {
                    linear_shopping_list_tab.setVisibility(View.GONE);
                    linear_coupon_tab.setVisibility(View.VISIBLE);

                    rv_shopping_list_items.setVisibility(View.VISIBLE);
                    shopping_list_header.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    DetaileToolbar.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    tv.setVisibility(View.GONE);

                    rv_category.setVisibility(View.GONE);
                    rowLayout.setVisibility(View.GONE);
                    rowLayoutShort.setVisibility(View.GONE);

                    x = 1;
                    z = 0;
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    navigation.getMenu().findItem(R.id.ShoppingList).setTitle("Personal Ad");
                    navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.account);

                    //fetchActivatedOffer();
                    //
                    activatedOffersListIdLoad();
                    //
                    //
                    imv_all_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewRemoveAllDialog alert = new ViewRemoveAllDialog();
                            alert.showDialog(activity, "Do you want to delete all the items from the shopping list?");
                        }
                    });

                    DetaileToolbar.setTitle("MyFareway List");
                    DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //
                            x = 0;
                            z = 0;
                            shoppingArrayList.clear();
                            shoppingListAdapter.notifyDataSetChanged();

                            navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                            navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                            tv.setVisibility(View.VISIBLE);
                            shopping_list_header.setVisibility(View.GONE);
                            navigation.setVisibility(View.VISIBLE);
                            rv_shopping_list_items.setVisibility(View.GONE);
                            DetaileToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                        }
                    });

                } else if (x == 1) {
                    if (searchLable == true) {
                        x = 3;
                        z = 0;
                        if (participate == 0) {
                            search_message.setVisibility(View.VISIBLE);
                        } else if (participate == 1) {
                            search_message.setVisibility(View.GONE);
                        }
                    } else {
                        x = 0;
                        z = 0;
                    }


                    shoppingArrayList.clear();
                    shoppingListAdapter.notifyDataSetChanged();

                    navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                    navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                    tv.setVisibility(View.VISIBLE);
                    shopping_list_header.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    rv_shopping_list_items.setVisibility(View.GONE);

                    DetaileToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);

                }

            } else if (i == R.id.ShopperID) {

                PopupMenu popupMenu = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation, Gravity.END);
                } else {
                    popupMenu = new PopupMenu(MainFwActivity.this, navigation);
                }
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                popupMenu.inflate(R.menu.more_popup);
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
                //pdView=false;

                search_message.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                shopping_list_header.setVisibility(View.GONE);
                rv_shopping_list_items.setVisibility(View.GONE);

                navigation.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                // x=0;
                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);

                rv_items.setVisibility(View.INVISIBLE);
                rowLayoutShort.setVisibility(View.GONE);
                rowLayout.setVisibility(View.GONE);
                rv_category.setVisibility(View.VISIBLE);
                //OtherCoupon=0;
                //OtherCouponmulti=0;

                return true;
            } else if (i2 == R.id.filter_by_all_offer) {
                search_message.setVisibility(View.GONE);
                rowLayoutShort.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                shopping_list_header.setVisibility(View.GONE);
                rv_shopping_list_items.setVisibility(View.GONE);

                navigation.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);

                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);

                if (x == 0) {
                    rowLayout.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.INVISIBLE);
                    rv_category.setVisibility(View.INVISIBLE);

                    rowLayoutClearFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pdView=true;
                            couponTile=true;
                            offferShort = false;
                            savingsShort = false;
                            categoryShort=false;
                            tmp = 0;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });

                    rowLayout0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            categoryShort = false;
                            if (savingsShort == true) {
                                pdView = false;
                                couponTile = false;
                                offferShort = false;
                            } else if (offferShort == true) {
                                pdView = false;
                                couponTile = false;
                                savingsShort = false;
                            } else {
                                pdView = true;
                                couponTile = true;
                                offferShort = false;
                            }

                            tmp = 0;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            categoryShort = false;
                            if (savingsShort == true) {
                                pdView = false;
                                couponTile = false;
                                offferShort = false;
                            } else if (offferShort == true) {
                                pdView = false;
                                couponTile = false;
                                savingsShort = false;
                            } else {
                                pdView = false;
                            }

                            tmp = 3;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                        }
                    });
                    rowLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            categoryShort = false;
                            if (savingsShort == true) {
                                pdView = false;
                                couponTile = false;
                                offferShort = false;
                            } else if (offferShort == true) {
                                pdView = false;
                                couponTile = false;
                                savingsShort = false;
                            } else {
                                pdView = false;
                            }
                            tmp = 2;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            categoryShort = false;
                            if (savingsShort == true) {
                                pdView = false;
                                couponTile = false;
                                offferShort = false;
                            } else if (offferShort == true) {
                                pdView = false;
                                couponTile = false;
                                savingsShort = false;
                            } else {
                                pdView = false;
                            }
                            tmp = 1;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (x == 3) {
                    rowLayout.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.INVISIBLE);
                    rv_category.setVisibility(View.INVISIBLE);

                    rowLayoutClearFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pdView=true;
                            couponTile=true;
                            offferShort = false;
                            savingsShort = false;
                            categoryShort=false;
                            tmp = 0;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });

                    rowLayout0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp = 0;
                            searchProduct();
                            //
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp = 3;
                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp = 2;
                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp = 1;
                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayout.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                }


                return true;
            } else if (i2 == R.id.filter_by_recent_added) {

                //
                if (z == 1) {
                    fetchShopping();
                } else {
                    fetchActivatedOffer();
                }
                return true;
            } else if (i2 == R.id.filter_by_exp_date) {

                if (z == 1) {
                    filterExpShopping();
                } else {
                    filterExpActivatedOffer();
                }

                return true;
            } else if (i2 == R.id.filter_by_short) {
                rowLayout.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                shopping_list_header.setVisibility(View.GONE);
                rv_shopping_list_items.setVisibility(View.GONE);

                navigation.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);

                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                if (x == 0) {

                    rowLayoutShort.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.INVISIBLE);
                    rv_category.setVisibility(View.INVISIBLE);

                    ShortClearFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp=0;
                            pdView=true;
                            couponTile=true;
                            offferShort = false;
                            savingsShort = false;
                            categoryShort=false;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });

                    rowLayout0Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //pdView=true;
                            //couponTile=true;
                            offferShort = false;
                            savingsShort = false;

                            //tmp=0;
                            if (tmp == 0 && categoryShort == false) {
                                pdView = true;
                                couponTile = true;
                            } else {

                            }
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout1Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pdView = false;
                            couponTile = false;
                            offferShort = false;
                            savingsShort = true;
                            //tmp=0;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                        }
                    });
                    rowLayout2Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            offferShort = true;
                            pdView = false;
                            savingsShort = false;
                            couponTile = false;
                            //tmp=0;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (x == 3) {
                    rowLayoutShort.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.INVISIBLE);
                    rv_category.setVisibility(View.INVISIBLE);

                    ShortClearFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tmp=0;
                            pdView=true;
                            couponTile=true;
                            offferShort = false;
                            savingsShort = false;
                            categoryShort=false;
                            fetchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });

                    rowLayout0Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //tmp=0;
                            pdView = false;
                            couponTile = false;
                            savingsShort = false;
                            offferShort = false;

                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout1Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //tmp=0;
                            pdView = false;
                            couponTile = false;
                            savingsShort = true;
                            offferShort = false;

                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                    rowLayout2Short.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //tmp=0;
                            pdView = false;
                            couponTile = false;
                            savingsShort = false;
                            offferShort = true;

                            searchProduct();
                            rv_category.setVisibility(View.GONE);
                            rowLayoutShort.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                        }
                    });
                }


                return true;
            }

           /* else if (i2==R.id.by_shopper_id){

                Intent i1=new Intent(activity,ShopperId.class);
                startActivity(i1);
            }*/

            else if (i2 == R.id.by_purchase_history) {

                Intent i1 = new Intent(activity, PurchaseHistory.class);
                startActivity(i1);
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

    public void searchLoad(final String s) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.SEARCH + "MemberId=" + appUtil.getPrefrence("MemberId") + "&Plateform=2&StoreId=" + appUtil.getPrefrence("StoreId") + "&SearchText=" + s,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());
                                progressDialog.dismiss();

                                if (!response.equals("[]")) {
                                    try {
                                        Log.i("Fareway response", response.toString());
                                        JSONArray jsonParam = new JSONArray(response.toString());
                                        /*
                                          message3=message;
                                        message=jsonParam;
                                        */
                                        message3 = jsonParam;
                                        //tmp=0;
                                        searchProduct();
                                    } catch (Throwable e) {
                                        progressDialog.dismiss();
                                        Log.i("Excep", "error----" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                } else {
                                    //Toast.makeText(activity, "neelam", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    ViewErrorAllDialog alert = new ViewErrorAllDialog();
                                    alert.showDialog(activity, "Sorry, your search for " + s + " did not return any result in your personal Ad.Currently we don't have any deals, coupons, sales price items matching your search.\n" +
                                            "Our stores still might carry it and if we do its at the right price.");

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                    }
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
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
                progressDialog.setCanceledOnTouchOutside(false);
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PRODUCTLIST + "?memberid=" + appUtil.getPrefrence("MemberId") + "&Plateform=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Personal Deal", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        //progressDialog.dismiss();
                                        message = root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")) {
                                            // moreCouponLoad();
                                            x = 1;
                                        } else {
                                            CircularmoreCouponLoad();
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void messageLoadRefresh() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PRODUCTLIST + "?memberid=" + appUtil.getPrefrence("MemberId") + "&Plateform=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Personal Deal", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        //progressDialog.dismiss();
                                        message = root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")) {
                                            // moreCouponLoad();
                                            x = 1;
                                        } else {
                                            CircularmoreCouponLoad();
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PRODUCTLIST + "?memberid=" + appUtil.getPrefrence("MemberId") + "&Plateform=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        progressDialog.dismiss();
                                        message = root.getJSONArray("message");
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
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public static String reLoadApi() {
        try {

            StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PRODUCTLIST + "?memberid=" + appUtil2.getPrefrence("MemberId") + "&Plateform=2",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Fareway response Main", response.toString());

                            try {
                                JSONObject root = new JSONObject(response);
                                root.getString("errorcode");
                                Log.i("errorcode", root.getString("errorcode"));
                                if (root.getString("errorcode").equals("0")) {
                                    //progressDialog.dismiss();
                                    message = root.getJSONArray("message");
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

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    params.put("Authorization", appUtil2.getPrefrence("token_type") + " " + appUtil2.getPrefrence("access_token"));
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
                mQueue2.add(jsonObjectRequest);
            } catch (Exception e) {
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

    private void fetchProduct() {

        if (message.length() == 0) {
            //no students
        } else {
            String strCategory = "";
            String strCategoryCheck = "";
            int Categoryid = 0;
            int category_count = 0;
            int subcat = 0;
            if (tmp == 0) {

                for (int i = 0; i < message.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {

                            if (!strCategoryCheck.contains("#" + message.getJSONObject(i).getInt("CategoryID") + "#")) {
                                strCategoryCheck += "#" + message.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat = 0;
                                for (int q = 0; q < message.length(); q++) {
                                    if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID")) {
                                        subcat = subcat + 1;
                                    }
                                }
                                Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                // adding product to product list
                   /* productList.clear();
                    productList.addAll(items);

                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                //
                if (categoryShort == true) {
                    if (offferShort == true) {

                        List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        try {
                            List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                            }.getType());
                            // adding product to product list
                            productList.clear();
                            productList.addAll(items);

                            customAdapterPersonalPrices.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();*/
                    }
                }
                else {
                    if (offferShort == true) {
                        List<Product> items = new Gson().fromJson(message.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson(message.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        List<Product> items = new Gson().fromJson(message.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }
                }


                //

                strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category \"}," + strCategory;
                String data = "[" + String.valueOf(strCategory) + "]";
                // categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    // refreshing recycler view
                    List<Category> items1 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items1);

                    Collections.sort(categoryList, new Comparator<Category>() {

                        @Override
                        public int compare(Category o1, Category o2) {
                            return o1.getCategoryName().compareTo(o2.getCategoryName());
                        }

                    });

                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else {
                String categorydata = "";
                for (int i = 0; i < message.length(); i++) {
                    try {
                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = message.getJSONObject(i);
                            categorydata += (categorydata == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {
                                if (!strCategoryCheck.contains("#" + message.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat = 0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                        /*
                        List<Product> items1 = new Gson().fromJson("["+categorydata.toString()+"]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items1);
                        customAdapterPersonalPrices.notifyDataSetChanged();*/
                if (categoryShort == true) {
                    if (offferShort == true) {
                        List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        List<Product> items = new Gson().fromJson(messageCategory.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }
                } else {
                    if (offferShort == true) {
                        List<Product> items = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        List<Product> items = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }
                }


                strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category \"}," + strCategory;

                String data = "[" + String.valueOf(strCategory) + "]";

                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    // refreshing recycler view
                    List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                    }.getType());
                    categoryList.clear();
                    categoryList.addAll(items2);
                    try {
                        Collections.sort(categoryList, new Comparator<Category>() {

                            @Override
                            public int compare(Category o1, Category o2) {
                                return o1.getCategoryName().compareTo(o2.getCategoryName());
                            }

                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    customAdapterFilter.notifyDataSetChanged();
                    rv_category.setAdapter(customAdapterFilter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private void searchProduct() {
        if (message3.length() == 0) {
            //no students
        } else {
            if (message3.length() < 5) {

                if (participate == 0) {
                    search_message.setVisibility(View.VISIBLE);
                } else if (participate == 1) {
                    search_message.setVisibility(View.GONE);
                }

                String strCategory = "";
                String strCategoryCheck = "";
                int Categoryid = 0;
                int category_count = 0;
                int subcat = 0;

                if (tmp == 0) {
                    for (int i = 0; i < message3.length(); i++) {
                        category_count = category_count + 1;
                        try {
                            if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {

                                if (!strCategoryCheck.contains("#" + message3.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message3.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID")) {
                                            subcat = subcat + 1;
                                        }
                                    }
                                    Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                /*List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                }.getType());

                productList.clear();
                productList.addAll(items);

                customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });
                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (offferShort == true) {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                                //return o1.getPrimaryOfferTypeId().compareTo(o2.getPrimaryOfferTypeId());
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

                    strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category \"}," + strCategory;
                    String data = "[" + String.valueOf(strCategory) + "]";
                    // categoryList.clear();
                    try {
                        JSONArray jsonParam = new JSONArray(data.toString());
                    /*for (int i=0;i<jsonParam.length();i++) {
                   }*/
                        // refreshing recycler view
                        List<Category> items1 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                        }.getType());
                        categoryList.clear();
                        categoryList.addAll(items1);
                        try {
                            Collections.sort(categoryList, new Comparator<Category>() {

                                @Override
                                public int compare(Category o1, Category o2) {
                                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                                }

                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        customAdapterFilter.notifyDataSetChanged();
                        rv_category.setAdapter(customAdapterFilter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String categorydata = "";
                    for (int i = 0; i < message3.length(); i++) {
                        try {
                            if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                categorydata += (categorydata == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    if (!strCategoryCheck.contains("#" + message3.getJSONObject(i).getInt("CategoryID") + "#")) {
                                        strCategoryCheck += "#" + message3.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                        subcat = 0;
                                        for (int q = 0; q < message3.length(); q++) {
                                            if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                    subcat = subcat + 1;
                                                }
                                            }
                                        }
                                        Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                        strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                                    }
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    List<Product> items1;
                    if (categorydata != "") {
                    /*items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                    }.getType());
                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                        if (offferShort == true) {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            productList.clear();
                            productList.addAll(items1);

                            Collections.sort(productList, new Comparator<Product>() {

                                @Override
                                public int compare(Product o2, Product o1) {
                                    return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                                    //return o1.getPrimaryOfferTypeId().compareTo(o2.getPrimaryOfferTypeId());
                                }

                            });

                            customAdapterPersonalPrices.notifyDataSetChanged();
                            //rv_items.setAdapter(customAdapterPersonalPrices);
                        } else if (savingsShort == true) {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            productList.clear();
                            productList.addAll(items1);

                            Collections.sort(productList, new Comparator<Product>() {

                                @Override
                                public int compare(Product o2, Product o1) {
                                    return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                    // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                                }

                            });
                            customAdapterPersonalPrices.notifyDataSetChanged();
                            //rv_items.setAdapter(customAdapterPersonalPrices);
                        } else {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            // adding product to product list
                            productList.clear();
                            productList.addAll(items1);

                            customAdapterPersonalPrices.notifyDataSetChanged();
                        }
                        strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category\"}," + strCategory;
                    } else {
                        Toast.makeText(activity, "no data", Toast.LENGTH_SHORT).show();
                        productList.clear();
                        customAdapterPersonalPrices.notifyDataSetChanged();
                        strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category\"}";
                    }

                    // adding product to product list

                    // refreshing recycler view
                    String data = "[" + String.valueOf(strCategory) + "]";
                    //categoryList.clear();
                    try {
                        JSONArray jsonParam = new JSONArray(data.toString());
                        for (int i = 0; i < jsonParam.length(); i++) {

                            //categoryList.add(jsonParam.getJSONObject(i));
                        }
                        // refreshing recycler view
                        List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                        }.getType());
                        categoryList.clear();
                        categoryList.addAll(items2);
                        try {
                            Collections.sort(categoryList, new Comparator<Category>() {

                                @Override
                                public int compare(Category o1, Category o2) {
                                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                                }

                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        customAdapterFilter.notifyDataSetChanged();
                        rv_category.setAdapter(customAdapterFilter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                //////

            } else {
                String strCategory = "";
                String strCategoryCheck = "";
                int Categoryid = 0;
                int category_count = 0;
                int subcat = 0;
                if (tmp == 0) {
                    for (int i = 0; i < message3.length(); i++) {
                        category_count = category_count + 1;
                        try {
                            if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {

                                if (!strCategoryCheck.contains("#" + message3.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + message3.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID")) {
                                            subcat = subcat + 1;
                                        }
                                    }
                                    Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                /*List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                }.getType());

                productList.clear();
                productList.addAll(items);

                customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (savingsShort == true) {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });
                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (offferShort == true) {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                                //return o1.getPrimaryOfferTypeId().compareTo(o2.getPrimaryOfferTypeId());
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        List<Product> items = new Gson().fromJson(message3.toString(), new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

                    strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category\"}," + strCategory;
                    String data = "[" + String.valueOf(strCategory) + "]";
                    // categoryList.clear();
                    try {
                        JSONArray jsonParam = new JSONArray(data.toString());
                    /*for (int i=0;i<jsonParam.length();i++) {
                    }*/
                        // refreshing recycler view
                        List<Category> items1 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                        }.getType());
                        categoryList.clear();
                        categoryList.addAll(items1);
                        Collections.sort(categoryList, new Comparator<Category>() {

                            @Override
                            public int compare(Category o1, Category o2) {
                                return o1.getCategoryName().compareTo(o2.getCategoryName());
                            }

                        });
                        customAdapterFilter.notifyDataSetChanged();
                        rv_category.setAdapter(customAdapterFilter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String categorydata = "";
                    for (int i = 0; i < message3.length(); i++) {
                        try {
                            if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                categorydata += (categorydata == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    if (!strCategoryCheck.contains("#" + message3.getJSONObject(i).getInt("CategoryID") + "#")) {
                                        strCategoryCheck += "#" + message3.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                        subcat = 0;
                                        for (int q = 0; q < message3.length(); q++) {
                                            if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                    subcat = subcat + 1;
                                                }
                                            }
                                        }
                                        Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                        strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName").replace("\n", "") + " (" + subcat + ")\"}");
                                    }
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    List<Product> items1;
                    if (categorydata != "") {
                    /*items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                    }.getType());
                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                        if (offferShort == true) {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            productList.clear();
                            productList.addAll(items1);

                            Collections.sort(productList, new Comparator<Product>() {

                                @Override
                                public int compare(Product o2, Product o1) {
                                    return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                                    //return o1.getPrimaryOfferTypeId().compareTo(o2.getPrimaryOfferTypeId());
                                }

                            });

                            customAdapterPersonalPrices.notifyDataSetChanged();
                            //rv_items.setAdapter(customAdapterPersonalPrices);
                        } else if (savingsShort == true) {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            productList.clear();
                            productList.addAll(items1);

                            Collections.sort(productList, new Comparator<Product>() {

                                @Override
                                public int compare(Product o2, Product o1) {
                                    return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                    // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                                }

                            });
                            customAdapterPersonalPrices.notifyDataSetChanged();
                            //rv_items.setAdapter(customAdapterPersonalPrices);
                        } else {
                            items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                            }.getType());
                            // adding product to product list
                            productList.clear();
                            productList.addAll(items1);

                            customAdapterPersonalPrices.notifyDataSetChanged();
                        }
                        strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category\"}," + strCategory;
                    } else {
                        Toast.makeText(activity, "no data", Toast.LENGTH_SHORT).show();
                        productList.clear();
                        customAdapterPersonalPrices.notifyDataSetChanged();
                        strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category\"}";
                    }

                    // adding product to product list

                    // refreshing recycler view

                    String data = "[" + String.valueOf(strCategory) + "]";
                    //categoryList.clear();
                    try {
                        JSONArray jsonParam = new JSONArray(data.toString());
                        for (int i = 0; i < jsonParam.length(); i++) {

                            //categoryList.add(jsonParam.getJSONObject(i));
                        }
                        // refreshing recycler view
                        List<Category> items2 = new Gson().fromJson(data, new TypeToken<List<Category>>() {
                        }.getType());
                        categoryList.clear();
                        categoryList.addAll(items2);
                        try {
                            Collections.sort(categoryList, new Comparator<Category>() {

                                @Override
                                public int compare(Category o1, Category o2) {
                                    return o1.getCategoryName().compareTo(o2.getCategoryName());
                                }

                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        customAdapterFilter.notifyDataSetChanged();
                        rv_category.setAdapter(customAdapterFilter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
    }

    private void CircularmoreCouponLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog2 = new ProgressDialog(activity);
                // progressDialog2.setMessage("Processing");
                //progressDialog2.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.MORECOUPON + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&Plateform=2&CircularType=0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Digital coupon response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        progressDialog.dismiss();
                                        if (message != null) {
                                            if (root.getJSONArray("message").length() > 0) {
                                                root.getJSONArray("message").getJSONObject(0).put("TileNumber", 998);
                                            }
                                            String s1 = root.getJSONArray("message").toString();
                                            String s2 = message.toString();
                                            String s3 = "";
                                            String s4 = "{\"oCouponShortDescription\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"CouponShortDescription\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"CouponLongDescription\":\"\",\"RewardType\":\"3\",\"RewardQty\":\"0\",\"Groupname\":\"\",\"oGroupname\":\"\",\"oDisplayPrice\":\"<sup>$</sup>2.84\",\"rewardGroupname\":\"\",\"Quantity\":1,\"inCircular\":1,\"RequiresActivation\":\"True\",\"IsMidWeek\":0,\"FreeOffer\":0,\"AltTitleBarImage\":\"\",\"LimitPerTransection\":0,\"TileNumber\":\"2\",\"MemberID\":41761,\"UPCRank\":\"0\",\"HasRelatedItems\":1,\"OriginatorID\":0,\"RelevantUPC\":\"4470002288\",\"IsEmployeeOffer\":false,\"BadgeId\":\"0\",\"RedeemLimit\":0,\"RequiredQty\":1,\"CategoryPriority\":1,\"PercentSavings\":\"28.82\",\"FinalPrice\":\"2.8400\",\"AdPrice\":\"0.0000\",\"CouponDiscount\":\"0.0000\",\"PersonalCircularID\":38477,\"LoyaltyCardNumber\":\"5155567152\",\"PersonalCircularItemId\":1029271,\"SectionNumber\":3,\"StoreID\":\"657\",\"RegularPrice\":\"3.99\",\"DisplayPrice\":\"<sup>$</sup>2.84\",\"Savings\":\"1.1500\",\"DateAdded\":\"7/20/2019 2:44:13 AM\",\"ValidityStartDate\":\"7/9/19\",\"BadgeName\":\"\",\"BadgeFileName\":\"\",\"ValidityEndDate\":\"7/24/19\",\"Description\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"PackagingSize\":\"5.5 OZ\",\"PricingMasterID\":0,\"CategoryID\":1000,\"UPC\":\"4470002288\",\"CategoryName\":\"z\",\"SmallImagePath\":\"https://images.immdemo.net/product/wlarge/00044700022887.png\",\"LargeImagePath\":\"https://images.immdemo.net/product/wlarge/00044700022887.png\",\"Isbadged\":\"False\",\"ListCount\":1,\"SpecialInformation\":\"\",\"TileTemplateID\":3,\"MinAmount\":0.0,\"PriceAssociationCode\":\"\",\"PrimaryOfferTypeName\":\"Personal Deals\",\"OfferTypeTagName\":\"My Personal Deal\",\"OfferDefinition\":\"New Price\",\"CPRPromoTypeName\":\"Individual\",\"RelevancyDetail\":\"Pushed\",\"PrimaryOfferTypeId\":0,\"OfferDetailId\":1,\"OfferDefinitionId\":2,\"CPRPromoTypeId\":1,\"RelevancyTypeD\":5,\"CouponID\":1111,\"RelatedItemCount\":2,\"ClickCount\":1,\"PageID\":1,\"BrandId\":1,\"BrandName\":\"Sally Hansen\",\"DietaryId\":0,\"DietaryName\":\"\",\"RewardValue\":\"2.84\",\"CouponImageURl\":\"http://images.immdemo.net/coupon/wlarge/couponImg.jpg\"}";
                                            s1 = s1.substring(s1.indexOf("[") + 1, s1.lastIndexOf("]"));
                                            s2 = s2.substring(s2.indexOf("[") + 1, s2.lastIndexOf("]"));
                                            int a = message.length() / 2;
                                            //s3="["+s2+","+s1+"]";
                                            if (a * 2 == message.length()) {
                                                s3 = "[" + s2 + "," + s1 + "]";
                                            } else {
                                                //cdOddView=true;
                                               /*for (int i = message.length()-1; i < message.length(); i++) {
                                                   UPCOddView=message.getJSONObject(i).getString("UPC");
                                                   s3="["+s2+","+String.valueOf(message.getJSONObject(i))+","+s1+"]";
                                               }*/
                                                //s3="["+s2+","+s1+"]";
                                                s3 = "[" + s2 + "," + s4 + "," + s1 + "]";

                                            }

                                            message = null;
                                            JSONArray jsonArray = new JSONArray(s3);
                                            message = jsonArray;
                                            fetchProduct();
                                            shoppingListIdLoad();
                                            //
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
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog2.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void moreCouponLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.MORECOUPON + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&Plateform=2&CircularType=0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Digital coupon response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        // progressDialog.dismiss();
                                        morecouponlist = root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchMoreCoupon() {
        if (morecouponlist.length() == 0) {
            //no students
        } else {
            String strCategory = "";
            String strCategoryCheck = "";
            int Categoryid = 0;
            int category_count = 0;
            int subcat = 0;
            tmp = 2;
            if (tmp == 0) {
                for (int i = 0; i < morecouponlist.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if (Categoryid != morecouponlist.getJSONObject(i).getInt("CategoryID")) {
                            if (!strCategoryCheck.contains("#" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "#")) {
                                strCategoryCheck += "#" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                subcat = 0;
                                for (int q = 0; q < morecouponlist.length(); q++) {
                                    if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                        subcat = subcat + 1;
                                    }
                                }
                                Categoryid = morecouponlist.getJSONObject(i).getInt("CategoryID");
                                strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + morecouponlist.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + morecouponlist.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items = new Gson().fromJson(morecouponlist.toString(), new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();
            } else {
                String categorydata = "";
                for (int i = 0; i < morecouponlist.length(); i++) {
                    try {
                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = morecouponlist.getJSONObject(i);
                            categorydata += (categorydata == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if (Categoryid != morecouponlist.getJSONObject(i).getInt("CategoryID")) {
                                if (!strCategoryCheck.contains("#" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "#")) {
                                    strCategoryCheck += "#" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "#" + ",";
                                    subcat = 0;
                                    for (int q = 0; q < morecouponlist.length(); q++) {
                                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = morecouponlist.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + morecouponlist.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + morecouponlist.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + morecouponlist.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Product> items1 = new Gson().fromJson("[" + categorydata.toString() + "]", new TypeToken<List<Product>>() {
                }.getType());
                // adding product to product list
                productList.clear();
                productList.addAll(items1);
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();
            }
            strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category (" + category_count + ")\"}," + strCategory;
            String data = "[" + String.valueOf(strCategory) + "]";
            try {
                JSONArray jsonParam = new JSONArray(data.toString());
                for (int i = 0; i < jsonParam.length(); i++) {

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
        //participate=0;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit_txt.getWindowToken(), 0);

        tv_quantity_detail.setText(product.getQuantity());
        navigation.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        DetaileToolbar.setVisibility(View.VISIBLE);
        rv_items.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        DetaileToolbar.setTitle("Detail");
        DetaileToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liner_all_Varieties_activate.setVisibility(View.GONE);
                rv_items_verite.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                liner_detail_add_item.setVisibility(View.VISIBLE);
                liner_add_sub_button.setVisibility(View.VISIBLE);
                //messageLoadRefresh();
                qty = 0;

            }
        });

        tv_quantity_detail.setText(product.getQuantity());

        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
        TextView tv_reg_price_detail = (TextView) findViewById(R.id.tv_reg_price_detail);
        TextView tv_save_detail = (TextView) findViewById(R.id.tv_save_detail);
        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
        TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
        TextView tv_valid_detail = (TextView) findViewById(R.id.tv_valid_detail);
        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
        TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
        TextView tv_promo_price_detail = (TextView) findViewById(R.id.tv_promo_price_detail);
        TextView tv_coupon_detail_detail = (TextView) findViewById(R.id.tv_coupon_detail_detail);


        String saveDate = product.getValidityEndDate();
        if (saveDate.length() == 0) {

        } else {
            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
            Date newDate = null;
            try {
                newDate = spf.parse(saveDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String c = "MM/dd";
            spf = new SimpleDateFormat(c);
            saveDate = spf.format(newDate);
            tv_valid_detail.setText(saveDate);
            System.out.println(saveDate);
        }

        //
        liner_all_Varieties_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Fareway Related btn", response.toString());
                                    //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                    SetProductActivateShopping(product.getUPC(), product.getPrimaryOfferTypeId(), product.getCouponID(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));
//
                                    if (product.getPrimaryOfferTypeId() == 3 || product.getPrimaryOfferTypeId() == 2) {

                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        all_Varieties_activate.setVisibility(View.VISIBLE);
                                        all_Varieties_activate.setText("Activated");
                                        imv_status_verities.setVisibility(View.VISIBLE);

                                    } else {
                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                        all_Varieties_activate.setVisibility(View.GONE);
                                        imv_status_verities.setVisibility(View.GONE);
                                    }


                                    progressDialog.dismiss();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Volley error resp", "error----" + error.getMessage());
                            error.printStackTrace();

                            if (error.networkResponse == null) {

                                if (error.getClass().equals(TimeoutError.class)) {
                                }
                            }
                            progressDialog.dismiss();
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
                            params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                            if (product.getPrimaryOfferTypeId() == 2) {
                                params.put("ClickType", "2");
                            } else {
                                params.put("ClickType", "2");
                            }
                            params.put("iPositionID", product.getTileNumber());
                            params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                            params.put("AdPrice", product.getAdPrice());
                            params.put("RegPrice", product.getRegularPrice());
                            params.put("Savings", product.getSavings());

                            return params;
                        }

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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();


                }

            }
        });

        tv_varieties_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                header_title.setVisibility(View.GONE);

                if (product.getPrimaryOfferTypeId() == 3 || product.getPrimaryOfferTypeId() == 2) {

                    if (product.getClickCount() == 0) {
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                        all_Varieties_activate.setText("Activate");
                        imv_status_verities.setVisibility(View.GONE);

                    } else {
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        all_Varieties_activate.setVisibility(View.VISIBLE);
                        all_Varieties_activate.setText("Activated");
                        imv_status_verities.setVisibility(View.VISIBLE);
                    }
                } else {
                    liner_all_Varieties_activate.setVisibility(View.GONE);
                    all_Varieties_activate.setVisibility(View.GONE);
                    imv_status_verities.setVisibility(View.GONE);
                }

                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                participateToolbar.setVisibility(View.VISIBLE);
                participateToolbar.setTitle("Participating Items");

                Group = "";
                productrelated2 = product;
                groupItemsList.clear();

                rv_items_group.setVisibility(View.VISIBLE);
                if (product.getGroupname() == null) {
                    rv_items_group.setVisibility(View.GONE);
                    group_count_text.setVisibility(View.GONE);
                } else if (product.getGroupname() == "") {
                    rv_items_group.setVisibility(View.GONE);
                    group_count_text.setVisibility(View.GONE);
                } else if (product.getGroupname() == "null") {
                    rv_items_group.setVisibility(View.GONE);
                    group_count_text.setVisibility(View.GONE);
                } else if (product.getGroupname().length() > 0) {
                    veritiesGroupDetail(product.getCouponID());
                }

                //relatedItemsList.clear();
                qty = 0;


                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                    try {
                        progressDialog = new ProgressDialog(activity);
                        progressDialog.setMessage("Processing");
                        progressDialog.show();
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.RELATEDITEMLIST + "?MemberID=" + appUtil.getPrefrence("MemberId"),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (!response.equals("[]")) {
                                            try {

                                                Log.i("Verites response", response.toString());
                                                jsonParam = new JSONArray(response.toString());
                                                for (int i = 0; i < jsonParam.length(); i++) {
                                                    jsonParam.getJSONObject(i).getString("Quantity");
                                                    qty = qty + Integer.parseInt(jsonParam.getJSONObject(i).getString("Quantity"));
                                                }

                                                //
                                                progressDialog.dismiss();
                                                rv_items_verite.setVisibility(View.VISIBLE);
                                                if (product.getGroupname().length() > 0) {
                                                    fetchVeritesProduct2(Group);
                                                } else if (product.getGroupname() == null) {
                                                    fetchVeritesProduct();
                                                } else if (product.getGroupname() == "null") {
                                                    fetchVeritesProduct();
                                                } else if (product.getGroupname() == "") {
                                                    fetchVeritesProduct();
                                                } else {
                                                    fetchVeritesProduct();
                                                }
                                                participateToolbar.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                                        //messageLoadRefresh();
                                                        qty = 0;
                                                        if (x == 0) {
                                                            rv_items_group.setVisibility(View.GONE);
                                                            rv_items_verite.setVisibility(View.GONE);
                                                            participateToolbar.setVisibility(View.GONE);
                                                            rv_items.setVisibility(View.VISIBLE);
                                                            toolbar.setVisibility(View.VISIBLE);
                                                            navigation.setVisibility(View.VISIBLE);
                                                            group_count_text.setVisibility(View.GONE);
                                                            //header_title visible
                                                            header_title.setVisibility(View.GONE);
                                                        } else {
                                                            search_message.setVisibility(View.VISIBLE);
                                                            rv_items_group.setVisibility(View.GONE);
                                                            rv_items_verite.setVisibility(View.GONE);
                                                            participateToolbar.setVisibility(View.GONE);
                                                            rv_items.setVisibility(View.VISIBLE);
                                                            toolbar.setVisibility(View.VISIBLE);
                                                            navigation.setVisibility(View.VISIBLE);
                                                            group_count_text.setVisibility(View.GONE);
                                                            //header_title visible
                                                            header_title.setVisibility(View.GONE);
                                                        }


                                                    }
                                                });


                                            } catch (Throwable e) {
                                                progressDialog.dismiss();
                                                Log.i("Excep", "error----" + e.getMessage());
                                                e.printStackTrace();
                                                participateToolbar.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                                        //messageLoadRefresh();
                                                        qty = 0;
                                                        if (x == 0) {
                                                            rv_items_group.setVisibility(View.GONE);
                                                            rv_items_verite.setVisibility(View.GONE);
                                                            participateToolbar.setVisibility(View.GONE);
                                                            rv_items.setVisibility(View.VISIBLE);
                                                            toolbar.setVisibility(View.VISIBLE);
                                                            navigation.setVisibility(View.VISIBLE);
                                                            group_count_text.setVisibility(View.GONE);
                                                            //header_title visible
                                                            header_title.setVisibility(View.GONE);

                                                        } else {
                                                            rv_items_group.setVisibility(View.GONE);
                                                            rv_items_verite.setVisibility(View.GONE);
                                                            participateToolbar.setVisibility(View.GONE);
                                                            rv_items.setVisibility(View.VISIBLE);
                                                            toolbar.setVisibility(View.VISIBLE);
                                                            navigation.setVisibility(View.VISIBLE);
                                                            group_count_text.setVisibility(View.GONE);
                                                            //header_title visible
                                                            header_title.setVisibility(View.GONE);
                                                        }


                                                    }
                                                });
                                            }
                                        } else {
                                            progressDialog.dismiss();
                                            /*alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.incorrect_credentials),
                                                    getString(R.string.ok),getString(R.string.alert));
                                            alertDialog.show();*/
                                            participateToolbar.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    liner_all_Varieties_activate.setVisibility(View.GONE);
                                                    //messageLoadRefresh();
                                                    qty = 0;
                                                    if (x == 0) {
                                                        rv_items_group.setVisibility(View.GONE);
                                                        rv_items_verite.setVisibility(View.GONE);
                                                        participateToolbar.setVisibility(View.GONE);
                                                        rv_items.setVisibility(View.VISIBLE);
                                                        toolbar.setVisibility(View.VISIBLE);
                                                        navigation.setVisibility(View.VISIBLE);
                                                        group_count_text.setVisibility(View.GONE);
                                                        //header_title visible
                                                        header_title.setVisibility(View.GONE);
                                                    } else {
                                                        rv_items_group.setVisibility(View.GONE);
                                                        rv_items_verite.setVisibility(View.GONE);
                                                        participateToolbar.setVisibility(View.GONE);
                                                        rv_items.setVisibility(View.VISIBLE);
                                                        toolbar.setVisibility(View.VISIBLE);
                                                        navigation.setVisibility(View.VISIBLE);
                                                        group_count_text.setVisibility(View.GONE);
                                                        //header_title visible
                                                        header_title.setVisibility(View.GONE);
                                                    }


                                                }
                                            });
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
                                participateToolbar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                        //messageLoadRefresh();
                                        qty = 0;
                                        if (x == 0) {
                                            rv_items_group.setVisibility(View.GONE);
                                            rv_items_verite.setVisibility(View.GONE);
                                            participateToolbar.setVisibility(View.GONE);
                                            rv_items.setVisibility(View.VISIBLE);
                                            toolbar.setVisibility(View.VISIBLE);
                                            navigation.setVisibility(View.VISIBLE);
                                            group_count_text.setVisibility(View.GONE);
                                            //header_title visible
                                            header_title.setVisibility(View.GONE);
                                        } else {
                                            rv_items_group.setVisibility(View.GONE);
                                            rv_items_verite.setVisibility(View.GONE);
                                            participateToolbar.setVisibility(View.GONE);
                                            rv_items.setVisibility(View.VISIBLE);
                                            toolbar.setVisibility(View.VISIBLE);
                                            navigation.setVisibility(View.VISIBLE);
                                            group_count_text.setVisibility(View.GONE);
                                            //header_title visible
                                            header_title.setVisibility(View.GONE);
                                        }


                                    }
                                });
                            }
                        }) {

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                if (product.getPrimaryOfferTypeId() == 1) {
                                    params.put("PriceAssociationCode", product.getPriceAssociationCode());
                                    params.put("UPC", product.getUPC());
                                    params.put("StoreId", product.getStoreID());
                                    params.put("Plateform", "2");
                                    params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                                    params.put("RelevantUPC", product.getRelevantUPC());
                                } else {
                                    params.put("PriceAssociationCode", product.getPriceAssociationCode());
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

                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                            participateToolbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    liner_all_Varieties_activate.setVisibility(View.GONE);
                                    //messageLoadRefresh();
                                    qty = 0;
                                    if (x == 0) {
                                        rv_items_group.setVisibility(View.GONE);
                                        rv_items_verite.setVisibility(View.GONE);
                                        participateToolbar.setVisibility(View.GONE);
                                        rv_items.setVisibility(View.VISIBLE);
                                        toolbar.setVisibility(View.VISIBLE);
                                        navigation.setVisibility(View.VISIBLE);
                                        group_count_text.setVisibility(View.GONE);
                                        //header_title visible
                                        header_title.setVisibility(View.GONE);
                                    } else {
                                        rv_items_group.setVisibility(View.GONE);
                                        rv_items_verite.setVisibility(View.GONE);
                                        participateToolbar.setVisibility(View.GONE);
                                        rv_items.setVisibility(View.VISIBLE);
                                        toolbar.setVisibility(View.VISIBLE);
                                        navigation.setVisibility(View.VISIBLE);
                                        group_count_text.setVisibility(View.GONE);
                                        //header_title visible
                                        header_title.setVisibility(View.GONE);
                                    }


                                }
                            });
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        progressDialog.dismiss();
//                displayAlert();
                    }

                } else {
                    alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok), getString(R.string.alert));
                    alertDialog.show();
                }
            }
        });

        ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);
        imv_item_detaile.setImageDrawable(getResources().getDrawable(R.drawable.item));
        final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
        final LinearLayout circular_layout_detaile = (LinearLayout) findViewById(R.id.circular_layout_detaile);
        LinearLayout bottomLayout_detaile = (LinearLayout) findViewById(R.id.bottomLayout_detaile);

        TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
        TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
        TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
        TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
        TableRow table_promo = (TableRow) findViewById(R.id.table_promo);
        TableRow table_promo_view = (TableRow) findViewById(R.id.table_promo_view);
        TableRow table_save = (TableRow) findViewById(R.id.table_save);
        TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
        TableRow table_package = (TableRow) findViewById(R.id.table_package);
        TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
        TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
        TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);
        TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
        TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);
        TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
        TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);

        if (product.getOfferDefinitionId() == 5 || product.getOfferDefinitionId() == 8) {
            if (product.getCouponImageURl().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else {
                Picasso.get().load(product.getCouponImageURl()).into(imv_item_detaile);
            }
        } else {
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else if (product.getLargeImagePath().equalsIgnoreCase("")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else {
                Picasso.get().load(product.getLargeImagePath()).into(imv_item_detaile);
            }
        }


        if (product.getPrimaryOfferTypeId() == 3) {
            //  tv_quantity_detail.setText(product.getQuantity());
            tv_detail_detail.setVisibility(View.VISIBLE);
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_fareway_flag.setText("With MyFareway");
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);
            table_package.setVisibility(View.GONE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_promo.setVisibility(View.GONE);
            table_promo_view.setVisibility(View.GONE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);

            if (product.getPackagingSize().equalsIgnoreCase("")) {
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            } else {
                tv_package_detail.setText(product.getPackagingSize());
            }
            circular_layout_detaile.setVisibility(View.VISIBLE);
            if (product.getClickCount() > 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");

            } else if (product.getClickCount() == 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");

            }

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

            String displayPrice = product.getDisplayPrice().toString();
            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + product.getDisplayPrice().split("\\.")[1] + "<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
            tv_price_detaile.setText(result);

            //Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            //tv_price_detaile.setText(result);

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars + " " + product.getPackagingSize());

            tv_reg_price_detail.setText("$" + product.getRegularPrice());

            float myFloatSaving = Float.parseFloat(product.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
           /* try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/

            tv_upc_detail.setText(product.getUPC());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());
            if (product.getHasRelatedItems() == 1) {
                if (product.getRelatedItemCount() > 1) {
                    table_varieties.setVisibility(View.VISIBLE);
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + product.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                } else {
                    table_varieties.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                }
            } else if (product.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }


        }

        else if (product.getPrimaryOfferTypeId() == 2) {
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_fareway_flag.setText("With Coupon");
            circular_layout_detaile.setVisibility(View.VISIBLE);

            if (product.getClickCount() > 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");

            } else if (product.getClickCount() == 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");

            }

            table_limit.setVisibility(View.VISIBLE);
            table_limit_view.setVisibility(View.VISIBLE);

            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);

            table_promo.setVisibility(View.VISIBLE);
            table_promo_view.setVisibility(View.VISIBLE);

            table_coupon.setVisibility(View.VISIBLE);
            table_coupon_view.setVisibility(View.VISIBLE);

            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);

            table_package.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);

            table_upc.setVisibility(View.VISIBLE);
            table_upc_view.setVisibility(View.VISIBLE);

            if (product.getAdPrice().equalsIgnoreCase("0.0000") || product.getAdPrice().equalsIgnoreCase("0")) {

                table_regular.setVisibility(View.GONE);
                table_regular_view.setVisibility(View.GONE);
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);
                table_promo.setVisibility(View.GONE);
                table_promo_view.setVisibility(View.GONE);
                table_coupon.setVisibility(View.GONE);
                table_coupon_view.setVisibility(View.GONE);
                table_save.setVisibility(View.GONE);
                table_save_view.setVisibility(View.GONE);
                table_upc.setVisibility(View.GONE);
                table_upc_view.setVisibility(View.GONE);

                table_limit.setVisibility(View.VISIBLE);
                table_limit_view.setVisibility(View.VISIBLE);
                table_varieties.setVisibility(View.VISIBLE);
                table_varieties_view.setVisibility(View.VISIBLE);

            }

            if (product.getHasRelatedItems() == 1) {
                table_varieties.setVisibility(View.VISIBLE);
                tv_varieties_detail.setVisibility(View.VISIBLE);
                table_varieties_view.setVisibility(View.VISIBLE);
                Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                tv_varieties_detail.setText(varietiesUnderline);
                table_upc.setVisibility(View.VISIBLE);
                table_upc_view.setVisibility(View.VISIBLE);
                tv_coupon_detail_detail.setVisibility(View.VISIBLE);
            } else if (product.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
                table_upc.setVisibility(View.GONE);
                table_upc_view.setVisibility(View.GONE);
                tv_coupon_detail_detail.setVisibility(View.GONE);
            }
            try {
                float myFloatCouponDiscount = Float.parseFloat(product.getCouponDiscount() + "f");
                String formattedStringCouponDiscount = String.format("%.02f", myFloatCouponDiscount);
                tv_coupon_detail.setText("$" + formattedStringCouponDiscount);

                float myFloatAdPrice = Float.parseFloat(product.getAdPrice() + "f");
                String formattedStringAdPrice = String.format("%.02f", myFloatAdPrice);
                tv_promo_price_detail.setText("$" + formattedStringAdPrice);

                float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
                String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);

                /*DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getCouponDiscount());
                Number num2 = dF.parse(product.getAdPrice());

                Number num3 = dF.parse(product.getRegularPrice());
                tv_coupon_detail.setText("$" + new DecimalFormat("##.##").format(num));
                tv_promo_price_detail.setText("$" + new DecimalFormat("##.##").format(num2));*/
                if (formattedStringAdPrice.equalsIgnoreCase(formattedStringRegularPrice)) {
                    table_promo.setVisibility(View.GONE);
                    table_promo_view.setVisibility(View.GONE);
                }

            } catch (Exception e) {

            }
            tv_package_detail.setText(product.getPackagingSize());


            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));
            String displayPrice = product.getDisplayPrice().toString();
            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + product.getDisplayPrice().split("\\.")[1] + "<sup>";

            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
            Spanned result2 = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>")+"<sup><small> *</small></sup>");

            tv_price_detaile.setText(result);
            if (product.getRequiredQty()>1){
                tv_price_detaile.setText(result2);
            }
            //
            if (product.getRewardType().equalsIgnoreCase("2") && product.getOfferDefinitionId() == 4) {
                tv_price_detaile.setText("Buy " + product.getRequiredQty() + "\n" + "Get " + product.getRewardQty() + " " + result + "*");
            } else if (product.getRewardType().equalsIgnoreCase("1") && product.getOfferDefinitionId() == 4) {
                try {
                    DecimalFormat dF = new DecimalFormat("0.00");
                    Number reward_value = dF.parse(product.getRewardValue());
                    tv_price_detaile.setText("Buy " + product.getRequiredQty() + "\n" + "Get " + product.getRewardQty() + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");

                } catch (Exception e) {

                }

            } else {

            }
            //

            String chars = capitalize(product.getDescription());
            if (product.getIsbadged().equalsIgnoreCase("True")) {
                tv_detail_detail.setVisibility(View.VISIBLE);
                tv_detail_detail.setText(chars + " " + product.getPackagingSize());
            } else {
                tv_detail_detail.setVisibility(View.GONE);
            }

            String chars2 = capitalize(product.getCouponShortDescription());
            tv_coupon_detail_detail.setText("\n" + chars2);

            if (product.getRequiredQty() > 1) {
                String chars3 = capitalize(product.getCouponShortDescription());
                tv_coupon_detail_detail.setText("\n" + "*" + chars3);
            }

            //tv_reg_price_detail.setText("$"+product.getRegularPrice());

            float myFloatRegularPrice = Float.parseFloat(product.getRegularPrice() + "f");
            String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);
            tv_reg_price_detail.setText("$" + formattedStringRegularPrice);

            float myFloatSaving = Float.parseFloat(product.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
            /*try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/
            tv_limit.setText(String.valueOf(product.getLimitPerTransection()));
            tv_upc_detail.setText(product.getUPC());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());

        }

        else if (product.getPrimaryOfferTypeId() == 1) {
            tv_detail_detail.setVisibility(View.VISIBLE);
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_fareway_flag.setText(" ");
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);
            table_package.setVisibility(View.GONE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_promo.setVisibility(View.GONE);
            table_promo_view.setVisibility(View.GONE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            tv_package_detail.setText(product.getPackagingSize());
            circular_layout_detaile.setVisibility(View.INVISIBLE);

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));

            // old display price
            String displayPrice = product.getDisplayPrice().toString();
            if (product.getDisplayPrice().toString().split("\\.").length > 1)
                displayPrice = product.getDisplayPrice().split("\\.")[0] + "<sup>" + product.getDisplayPrice().split("\\.")[1] + "<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
            tv_price_detaile.setText(result);

            /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);*/

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars + " " + product.getPackagingSize());

            tv_reg_price_detail.setText("$" + product.getRegularPrice());
            float myFloatSaving = Float.parseFloat(product.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
            /*try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/

            tv_upc_detail.setText(product.getUPC());

            tv_deal_type_detaile.setText(product.getOfferTypeTagName());


            if (product.getHasRelatedItems() == 1) {
                if (product.getRelatedItemCount() > 1) {
                    table_varieties.setVisibility(View.VISIBLE);
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + product.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                } else {
                    table_varieties.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + product.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }
            } else if (product.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }

        }

        add_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                //
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", product.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity()) + 1));
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
                final String mRequestBody = "'" + studentsObj.toString() + "'";
                //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                String url = null;
                if (product.getQuantity().equalsIgnoreCase("0") && product.getPrimaryOfferTypeId() == 1) {
                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity()) + 1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        qty = Integer.parseInt(product.getTotalQuantity()) + 1;

                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
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
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                                if (product.getPrimaryOfferTypeId() == 2) {
                                    params.put("ClickType", "1");
                                } else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());

                                return params;
                            }

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                } else if (product.getQuantity().equalsIgnoreCase("0")) {
                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity()) + 1)));
                                        product.setTotalQuantity(String.valueOf((Integer.parseInt(product.getTotalQuantity()) + 1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        qty = Integer.parseInt(product.getTotalQuantity());

                                        //add_item_flag_detail.setText(product.getQuantity());
                                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        imv_status_detaile.setVisibility(View.VISIBLE);
                                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        tv_status_detaile.setText("Activated");

                                        //
                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();

                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        all_Varieties_activate.setText("Activated");
                                        imv_status_verities.setVisibility(View.VISIBLE);
                                        product.setClickCount(1);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
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
                                params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                                if (product.getPrimaryOfferTypeId() == 2) {
                                    params.put("ClickType", "1");
                                } else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", product.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                                params.put("AdPrice", product.getAdPrice());
                                params.put("RegPrice", product.getRegularPrice());
                                params.put("Savings", product.getSavings());

                                return params;
                            }

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                } else {
                    url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + product.getUPC() + "&Quantity=" + (Integer.parseInt(product.getQuantity()) + 1) + "&DateAddedOn=" + currentDate;

                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("success", String.valueOf(response));
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity()) + 1)));
                                        product.setTotalQuantity(String.valueOf((Integer.parseInt(product.getTotalQuantity()) + 1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        qty = Integer.parseInt(product.getTotalQuantity());
                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

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
                            mQueue.add(jsonObjectRequest);

                        } catch (Exception e) {
                            e.printStackTrace();

                            progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        });

        add_minus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                if (Integer.parseInt(product.getQuantity()) > 1) {
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println(currentDate);
                    JSONObject ShoppingListItems = new JSONObject();
                    try {
                        ShoppingListItems.put("UPC", product.getUPC());
                        ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity()) - 1));
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
                    final String mRequestBody = "'" + studentsObj.toString() + "'";

                    String url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + product.getUPC() + "&Quantity=" + (Integer.parseInt(product.getQuantity()) - 1) + "&DateAddedOn=" + currentDate;

                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        /*Log.i("success", String.valueOf(response));
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));
                                        qty= Integer.parseInt(product.getTotalQuantity())-1;
                                        tv_quantity_detail.setText(product.getQuantity());
                                        fetchShoppingListLoad();
                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())-0)));
                                        progressDialog.dismiss();*/


                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity()) - 1)));
                                        product.setTotalQuantity(String.valueOf((Integer.parseInt(product.getTotalQuantity()) - 1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        qty = Integer.parseInt(product.getTotalQuantity());

                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) - 0)));

                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Integer.parseInt(product.getQuantity()) == 1) {
                    String url = Constant.WEB_URL + Constant.REMOVE + product.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("success", String.valueOf(response));

                                        qty = Integer.parseInt(product.getTotalQuantity()) - 1;

                                        tv_quantity_detail.setText("0");

                                        SetRemoveActivateDetail(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1);
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();
                }
            }
        });

        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (product.getClickCount() > 0) {
                    } else if (product.getClickCount() == 0) {

                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Fareway btn Main", response.toString());
                                            //fetchShoppingListLoad();
                                            if (product.getPrimaryOfferTypeId() == 2 || product.getPrimaryOfferTypeId() == 3) {
                                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                imv_status_detaile.setVisibility(View.VISIBLE);
                                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                                tv_status_detaile.setText("Activated");
                                                SetProductActivateDetaile1(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1, String.valueOf(0));
                                                //
                                                liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                                liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                all_Varieties_activate.setText("Activated");
                                                imv_status_verities.setVisibility(View.VISIBLE);
                                                product.setClickCount(1);
                                            }
                                            shoppingListLoad();
                                            //


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
                                    params.put("ClickType", "2");
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
                                //FarewayApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                                mQueue.add(jsonObjectRequest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //progressDialog.dismiss();
                            //displayAlert();
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
        Group = "";
        productrelated2 = product;
        groupItemsList.clear();

        participateToolbar.setVisibility(View.VISIBLE);
        participateToolbar.setTitle("Participating Items");
        rv_items_group.setVisibility(View.VISIBLE);

        search_message.setVisibility(View.GONE);
        header_title.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        rv_items.setVisibility(View.GONE);
        navigation.setVisibility(View.GONE);


        if (product.getGroupname() == null) {
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        } else if (product.getGroupname() == "") {
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        } else if (product.getGroupname() == "null") {
            rv_items_group.setVisibility(View.GONE);
            group_count_text.setVisibility(View.GONE);
        } else if (product.getGroupname().length() > 0) {
            veritiesGroupDetail(product.getCouponID());
            //
        }

        //relatedItemsList.clear();
        if (product.getPrimaryOfferTypeId() == 3 || product.getPrimaryOfferTypeId() == 2) {
            if (product.getClickCount() == 0) {
                all_Varieties_activate.setVisibility(View.VISIBLE);
                liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                all_Varieties_activate.setText("Activate");
                imv_status_verities.setVisibility(View.GONE);

            } else {
                all_Varieties_activate.setVisibility(View.VISIBLE);
                liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                all_Varieties_activate.setText("Activated");
                imv_status_verities.setVisibility(View.VISIBLE);
            }
        } else {
            liner_all_Varieties_activate.setVisibility(View.GONE);
            imv_status_verities.setVisibility(View.GONE);
        }


        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.RELATEDITEMLIST + "?MemberID=" + appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("anshuman:", response);

                                if (!response.equals("[]")) {
                                    try {
                                        rv_items_verite.setVisibility(View.VISIBLE);
                                        Log.i("Verites response", response.toString());
                                        jsonParam = new JSONArray(response.toString());
                                       /* List<RelatedItem> items = new Gson().fromJson(jsonParam.toString(), new TypeToken<List<RelatedItem>>() {
                                        }.getType());
                                        // adding product to product list
                                        relatedItemsList.clear();
                                        relatedItemsList.addAll(items);
                                        // refreshing recycler view
                                        customAdapterParticipateItems.notifyDataSetChanged();*/
                                        //
                                        //
                                        qty = 0;
                                        for (int i = 0; i < jsonParam.length(); i++) {
                                            jsonParam.getJSONObject(i).getString("Quantity");
                                            qty = qty + Integer.parseInt(jsonParam.getJSONObject(i).getString("Quantity"));

                                        }


                                        progressDialog.dismiss();
                                        //

                                        if (product.getGroupname().length() > 0) {
                                            fetchVeritesProduct2(Group);

                                        } else if (product.getGroupname() == null) {
                                            fetchVeritesProduct();

                                        } else if (product.getGroupname() == "null") {

                                            fetchVeritesProduct();
                                        } else if (product.getGroupname() == "") {
                                            fetchVeritesProduct();

                                        } else {
                                            fetchVeritesProduct();

                                        }

                                        participateToolbar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //fetchShoppingListLoad();
                                                qty = 0;

                                                liner_all_Varieties_activate.setVisibility(View.GONE);
                                                //header_title visible
                                                header_title.setVisibility(View.GONE);

                                                //messageLoadRefresh();
                                                rv_items_verite.setVisibility(View.GONE);
                                                if (x == 0) {
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.VISIBLE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                } else if (x == 3) {
                                                    if (message3.length() < 5) {
                                                        search_message.setVisibility(View.VISIBLE);
                                                    }
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.VISIBLE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                } else {
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.VISIBLE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                }


                                            }
                                        });

                                    } catch (Throwable e) {
                                        progressDialog.dismiss();
                                        Log.i("Excep", "error----" + e.getMessage());
                                        e.printStackTrace();
                                        participateToolbar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //fetchShoppingListLoad();
                                                qty = 0;
                                                liner_all_Varieties_activate.setVisibility(View.GONE);
                                                //header_title visible
                                                header_title.setVisibility(View.GONE);

                                                //messageLoadRefresh();
                                                rv_items_verite.setVisibility(View.GONE);
                                                if (x == 0) {
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.VISIBLE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                } else if (x == 3) {
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.GONE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                } else {
                                                    rv_items_group.setVisibility(View.GONE);
                                                    rv_items_verite.setVisibility(View.GONE);
                                                    participateToolbar.setVisibility(View.GONE);
                                                    rv_items.setVisibility(View.VISIBLE);
                                                    toolbar.setVisibility(View.VISIBLE);
                                                    navigation.setVisibility(View.VISIBLE);
                                                    group_count_text.setVisibility(View.GONE);
                                                    //header_title visible
                                                    header_title.setVisibility(View.GONE);
                                                }


                                            }
                                        });
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    alertDialog = userAlertDialog.createPositiveAlert("Participating Items (0)Activated",
                                            getString(R.string.ok), getString(R.string.alert));
                                    alertDialog.show();
                                    participateToolbar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //fetchShoppingListLoad();
                                            qty = 0;
                                            liner_all_Varieties_activate.setVisibility(View.GONE);
                                            //header_title visible
                                            header_title.setVisibility(View.GONE);

                                            //messageLoadRefresh();
                                            rv_items_verite.setVisibility(View.GONE);
                                            if (x == 0) {
                                                rv_items_group.setVisibility(View.GONE);
                                                rv_items_verite.setVisibility(View.GONE);
                                                participateToolbar.setVisibility(View.GONE);
                                                rv_items.setVisibility(View.VISIBLE);
                                                toolbar.setVisibility(View.VISIBLE);
                                                navigation.setVisibility(View.VISIBLE);
                                                group_count_text.setVisibility(View.GONE);
                                                //header_title visible
                                                header_title.setVisibility(View.GONE);
                                            } else if (x == 3) {
                                                rv_items_group.setVisibility(View.GONE);
                                                rv_items_verite.setVisibility(View.GONE);
                                                participateToolbar.setVisibility(View.GONE);
                                                rv_items.setVisibility(View.VISIBLE);
                                                toolbar.setVisibility(View.VISIBLE);
                                                navigation.setVisibility(View.GONE);
                                                group_count_text.setVisibility(View.GONE);
                                                //header_title visible
                                                header_title.setVisibility(View.GONE);
                                            } else {
                                                rv_items_group.setVisibility(View.GONE);
                                                rv_items_verite.setVisibility(View.GONE);
                                                participateToolbar.setVisibility(View.GONE);
                                                rv_items.setVisibility(View.VISIBLE);
                                                toolbar.setVisibility(View.VISIBLE);
                                                navigation.setVisibility(View.VISIBLE);
                                                group_count_text.setVisibility(View.GONE);
                                                //header_title visible
                                                header_title.setVisibility(View.GONE);
                                            }


                                        }
                                    });
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
                        participateToolbar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //fetchShoppingListLoad();
                                qty = 0;
                                liner_all_Varieties_activate.setVisibility(View.GONE);
                                //header_title visible
                                header_title.setVisibility(View.GONE);

                                //messageLoadRefresh();
                                rv_items_verite.setVisibility(View.GONE);
                                if (x == 0) {
                                    rv_items_group.setVisibility(View.GONE);
                                    rv_items_verite.setVisibility(View.GONE);
                                    participateToolbar.setVisibility(View.GONE);
                                    rv_items.setVisibility(View.VISIBLE);
                                    toolbar.setVisibility(View.VISIBLE);
                                    navigation.setVisibility(View.VISIBLE);
                                    group_count_text.setVisibility(View.GONE);
                                    //header_title visible
                                    header_title.setVisibility(View.GONE);
                                } else if (x == 3) {
                                    rv_items_group.setVisibility(View.GONE);
                                    rv_items_verite.setVisibility(View.GONE);
                                    participateToolbar.setVisibility(View.GONE);
                                    rv_items.setVisibility(View.VISIBLE);
                                    toolbar.setVisibility(View.VISIBLE);
                                    navigation.setVisibility(View.GONE);
                                    group_count_text.setVisibility(View.GONE);
                                    //header_title visible
                                    header_title.setVisibility(View.GONE);
                                } else {
                                    rv_items_group.setVisibility(View.GONE);
                                    rv_items_verite.setVisibility(View.GONE);
                                    participateToolbar.setVisibility(View.GONE);
                                    rv_items.setVisibility(View.VISIBLE);
                                    toolbar.setVisibility(View.VISIBLE);
                                    navigation.setVisibility(View.VISIBLE);
                                    group_count_text.setVisibility(View.GONE);
                                    //header_title visible
                                    header_title.setVisibility(View.GONE);
                                }


                            }
                        });
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        if (product.getPrimaryOfferTypeId() == 1) {
                            params.put("PriceAssociationCode", product.getPriceAssociationCode());
                            params.put("UPC", product.getUPC());
                            params.put("StoreId", product.getStoreID());
                            params.put("Plateform", "2");
                            params.put("PrimaryOfferTypeId", String.valueOf(product.getPrimaryOfferTypeId()));
                            params.put("RelevantUPC", product.getRelevantUPC());
                        } else {
                            params.put("PriceAssociationCode", product.getPriceAssociationCode());
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

                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    participateToolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //fetchShoppingListLoad();
                            qty = 0;
                            liner_all_Varieties_activate.setVisibility(View.GONE);
                            //header_title visible
                            header_title.setVisibility(View.GONE);

                            //messageLoadRefresh();
                            rv_items_verite.setVisibility(View.GONE);
                            if (x == 0) {
                                rv_items_group.setVisibility(View.GONE);
                                rv_items_verite.setVisibility(View.GONE);
                                participateToolbar.setVisibility(View.GONE);
                                rv_items.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                                navigation.setVisibility(View.VISIBLE);
                                group_count_text.setVisibility(View.GONE);
                                //header_title visible
                                header_title.setVisibility(View.GONE);
                            } else if (x == 3) {
                                rv_items_group.setVisibility(View.GONE);
                                rv_items_verite.setVisibility(View.GONE);
                                participateToolbar.setVisibility(View.GONE);
                                rv_items.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                                navigation.setVisibility(View.GONE);
                                group_count_text.setVisibility(View.GONE);
                                //header_title visible
                                header_title.setVisibility(View.GONE);
                            } else {
                                rv_items_group.setVisibility(View.GONE);
                                rv_items_verite.setVisibility(View.GONE);
                                participateToolbar.setVisibility(View.GONE);
                                rv_items.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                                navigation.setVisibility(View.VISIBLE);
                                group_count_text.setVisibility(View.GONE);
                                //header_title visible
                                header_title.setVisibility(View.GONE);
                            }


                        }
                    });
                }

            } catch (Exception e) {

                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
        }
        liner_all_Varieties_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Fareway Reated text", response.toString());
                                    //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                    SetProductActivateShopping(product.getUPC(), product.getPrimaryOfferTypeId(), product.getCouponID(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));


                                    liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                    liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                    all_Varieties_activate.setVisibility(View.VISIBLE);
                                    all_Varieties_activate.setText("Activated");
                                    imv_status_verities.setVisibility(View.VISIBLE);


                                    progressDialog.dismiss();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Volley error resp", "error----" + error.getMessage());
                            error.printStackTrace();

                            if (error.networkResponse == null) {

                                if (error.getClass().equals(TimeoutError.class)) {
                                }
                            }
                            progressDialog.dismiss();
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
                            params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                            if (product.getPrimaryOfferTypeId() == 2) {
                                params.put("ClickType", "2");
                            } else {
                                params.put("ClickType", "2");
                            }
                            params.put("iPositionID", product.getTileNumber());
                            params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                            params.put("AdPrice", product.getAdPrice());
                            params.put("RegPrice", product.getRegularPrice());
                            params.put("Savings", product.getSavings());

                            return params;
                        }

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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();


                }


                //
            }
        });

    }

    @Override
    public void onProductActivate(final Product product) {

        if (product.getClickCount() == 0) {
            //

            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway clickcount", response.toString());
                                if (product.getPrimaryOfferTypeId() == 3 || product.getPrimaryOfferTypeId() == 2) {
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    SetProductActivateShopping(product.getUPC(), product.getPrimaryOfferTypeId(), product.getCouponID(), 1, String.valueOf((Integer.parseInt(product.getQuantity()) + 0)));

                                } else if (product.getPrimaryOfferTypeId() == 1) {

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();

                        if (error.networkResponse == null) {

                            if (error.getClass().equals(TimeoutError.class)) {
                            }
                        }
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
                        params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                        if (product.getPrimaryOfferTypeId() == 2) {
                            params.put("ClickType", "2");
                        } else {
                            params.put("ClickType", "2");
                        }
                        params.put("iPositionID", product.getTileNumber());
                        params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                        params.put("AdPrice", product.getAdPrice());
                        params.put("RegPrice", product.getRegularPrice());
                        params.put("Savings", product.getSavings());

                        return params;
                    }

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();


            }

        }
        //

    }

    @Override
    public void onProductMultiActivate(final Product product) {

        if (product.getClickCount() > 0) {

            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway text", response.toString());
                                fetchShoppingListLoad();
                                if (product.getPrimaryOfferTypeId() == 3) {
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1);
                                } else if (product.getPrimaryOfferTypeId() == 2) {
                                    product.setClickCount(1);
                                    product.setQuantity("1");
                                    if (product.getRequiresActivation().contains("False")) {
                                        product.setListCount(1);
                                    } else {
                                        product.setListCount(1);
                                    }
                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 2);
                                } else if (product.getPrimaryOfferTypeId() == 1) {
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();

                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                            }
                        }
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
                        params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                        params.put("ClickType", "1");
                        params.put("iPositionID", product.getTileNumber());
                        params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                        params.put("AdPrice", product.getAdPrice());
                        params.put("RegPrice", product.getRegularPrice());
                        params.put("Savings", product.getSavings());
                        return params;
                    }

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();


            }
        } else if (product.getClickCount() == 0) {


            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway text", response.toString());
                                fetchShoppingListLoad();
                                if (product.getPrimaryOfferTypeId() == 3) {
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");

                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1);
                                } else if (product.getPrimaryOfferTypeId() == 2) {
                                    product.setClickCount(1);
                                    product.setListCount(1);

                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 2);
                                } else if (product.getPrimaryOfferTypeId() == 1) {

                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(), product.getCouponID(), product.getUPC(), product.getRequiresActivation(), 1);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();

                        if (error.networkResponse == null) {

                            if (error.getClass().equals(TimeoutError.class)) {
                            }
                        }
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
                        params.put("CategoryID", String.valueOf(product.getCategoryID()));
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
                        if (product.getPrimaryOfferTypeId() == 2) {
                            params.put("ClickType", "1");
                        } else {
                            params.put("ClickType", "1");
                        }
                        params.put("iPositionID", product.getTileNumber());
                        params.put("OPMOfferID", String.valueOf(product.getPricingMasterID()));
                        params.put("AdPrice", product.getAdPrice());
                        params.put("RegPrice", product.getRegularPrice());
                        params.put("Savings", product.getSavings());

                        return params;
                    }

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();


            }

        }
    }

    @Override
    public void onProductRemove(final Product product) {

        String url = Constant.WEB_URL + Constant.REMOVE + product.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("success remove", String.valueOf(response));
                        product.setListCount(0);
                        product.setQuantity("0");
                        fetchShoppingListLoad();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProductMultiRemove(final Product product) {

        String url = Constant.WEB_URL + Constant.REMOVE + product.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("success", String.valueOf(response));
                        product.setListCount(0);
                        product.setQuantity("0");
                        fetchShoppingListLoad();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGroupItemSelected(final Group groupItem) {

        fetchVeritesProduct2(groupItem.getGroupname());
    }

    public static String getDate(int s) {
        //MainFwActivity activate = new MainFwActivity();
        //activate.OtherCouponmulti=0;
        //activate.OtherCoupon=0;
        getCategoryViceData(s);
        rv_category.setVisibility(View.GONE);
        rv_items.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

        return null;
    }


    public static String getCategoryViceData(int s) {

        if (x == 0) {
            if (message.length() == 0) {

            } else {
                String strCategory = "";
                int Categoryid = 0;
                int category_count = 0;
                int subcat = 0;
                if (tmp == 0) {

                    String test1 = "";
                    for (int i = 0; i < message.length(); i++) {
                        try {
                            if (s == message.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            } else if (s == 0) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    /*
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());

                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (offferShort == true) {

                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {

                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {

                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

                } else {
                    pdView = false;
                    String test1 = "";
                    for (int i = 0; i < message.length(); i++) {
                        try {
                            if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId") && s == message.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            } else if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId") && s == 0) {
                                JSONObject finalObject = message.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message.length(); q++) {
                                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message.getJSONObject(q).getInt("CategoryID") == message.getJSONObject(i).getInt("CategoryID") && message.getJSONObject(q).getInt("PrimaryOfferTypeId") == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    /*
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (offferShort == true) {
                        Log.i("if", "sortOffer");
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        Log.i("if", "sortSaving");
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

                }
            }
        } else if (x == 3) {
            if (message3.length() == 0) {

            } else {
                if (message3.length() < 5) {
                    search_message.setVisibility(View.VISIBLE);
                } else {
                    search_message.setVisibility(View.GONE);
                }
                String strCategory = "";
                int Categoryid = 0;
                int category_count = 0;
                int subcat = 0;
                if (tmp == 0) {

                    String test1 = "";
                    for (int i = 0; i < message3.length(); i++) {
                        try {
                            if (s == message3.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            } else if (s == 0) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
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
                    /*
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());

                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (offferShort == true) {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {

                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

                } else {

                    String test1 = "";
                    for (int i = 0; i < message3.length(); i++) {
                        try {
                            if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId") && s == message3.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid = message3.getJSONObject(i).getInt("CategoryID");
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message3.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message3.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
                                }
                            } else if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId") && s == 0) {
                                JSONObject finalObject = message3.getJSONObject(i);
                                test1 += (test1 == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if (Categoryid != message3.getJSONObject(i).getInt("CategoryID")) {
                                    subcat = 0;
                                    for (int q = 0; q < message3.length(); q++) {
                                        if (tmp == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (message3.getJSONObject(q).getInt("CategoryID") == message3.getJSONObject(i).getInt("CategoryID") && message3.getJSONObject(q).getInt("PrimaryOfferTypeId") == message3.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
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
                    /*
                    List<Product> items1 = new Gson().fromJson("["+test1.toString()+"]", new TypeToken<List<Product>>() {
                    }.getType());
                    productList.clear();
                    productList.addAll(items1);
                    customAdapterPersonalPrices.notifyDataSetChanged();*/
                    if (offferShort == true) {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Integer.parseInt(String.valueOf(o1.getPrimaryOfferTypeId())) - Integer.parseInt(String.valueOf(o2.getPrimaryOfferTypeId()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else if (savingsShort == true) {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        productList.clear();
                        productList.addAll(items);

                        Collections.sort(productList, new Comparator<Product>() {

                            @Override
                            public int compare(Product o2, Product o1) {
                                return Float.valueOf(o1.getSavings()).compareTo(Float.valueOf(o2.getSavings()));
                                // return Integer.parseInt(String.valueOf(o1.getSavings())) - Integer.parseInt(String.valueOf(o2.getSavings()));
                            }

                        });

                        customAdapterPersonalPrices.notifyDataSetChanged();
                        //rv_items.setAdapter(customAdapterPersonalPrices);
                    } else {
                        String response = "[" + test1.toString() + "]";
                        JSONArray jsonParam = null;
                        try {
                            jsonParam = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        messageCategory = jsonParam;
                        List<Product> items = new Gson().fromJson("[" + test1.toString() + "]", new TypeToken<List<Product>>() {
                        }.getType());
                        // adding product to product list
                        productList.clear();
                        productList.addAll(items);

                        customAdapterPersonalPrices.notifyDataSetChanged();
                    }

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

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchLoad(result.get(0));
                    edit_txt.setText(result.get(0));
                    x=3;
                }
                break;
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("audio", result.get(0));
                    searchLoad(result.get(0));
                    edit_txt.setText(result.get(0));
                    x = 3;
                    //txvResult.setText(result.get(0));
                }
                break;
            case REQUEST_CHECK_SETTINGS_GPS:
                if (checkLoctionSetting()) {
                    Log.d(TAG, " >> location setting is ON");
                } else {
                    Log.d(TAG, " >> location setting is OFF finish activity ");
                    finish();
                }
                break;
        }

    }


    public void SetProductActivate(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType) {
        //  addBadgeView();

        if (x == 0) {
            try {
                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                        }
                    } else {
                        if (message.getJSONObject(i).getString("CouponID") == CouponID) {
                            if (RequireActivation == "True" && PrimaryOfferTypeID == 2 && ActivateType == 2) {
                                //message.getJSONObject(i).put("ListCount", 1);
                                message.getJSONObject(i).put("ClickCount", 1);
                            } else {
                                message.getJSONObject(i).put("ListCount", 1);
                                message.getJSONObject(i).put("ClickCount", 1);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (x == 1) {
            try {
                for (int i = 0; i < morecouponlist.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (morecouponlist.getJSONObject(i).getString("UPC") == UPC) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                        }
                    } else {
                        if (morecouponlist.getJSONObject(i).getString("CouponID") == CouponID) {
                            if (RequireActivation == "True" && PrimaryOfferTypeID == 2 && ActivateType == 2) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            } else {
                                morecouponlist.getJSONObject(i).put("ListCount", 1);
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);

                            }


                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        shoppingListLoad();


    }

    public void SetProductActivateShopping(String upc, int PrimaryOfferTypeID, String UPC, int ActivateType, String quantity) {
        if (x == 0) {
            try {
                for (int i = 0; i < message.length(); i++) {
                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    } else {

                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    }
                }
                if (messageCategory != null) {
                    for (int i = 0; i < messageCategory.length(); i++) {

                        if (PrimaryOfferTypeID == 1) {

                            if (messageCategory.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);
                            }
                        } else {

                            if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);

                            }
                            if (messageCategory.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                                messageCategory.getJSONObject(i).put("Quantity", quantity);
                            }

                        }
                    }
                }


                fetchProduct();
                shoppingListLoad();
                //

                if (jsonParam == null) {

                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {

                        if (jsonParam.getJSONObject(j).getString("UPC").equalsIgnoreCase(upc)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

                        } else if (jsonParam.getJSONObject(j).getString("CouponID").equalsIgnoreCase(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        }
                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {
            try {
              /*  for (int i = 0; i < jsonShoppingParam.length(); i++) {

                    jsonShoppingParam.getJSONObject(i).put("Quantity", quantity+1);
                }*/

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    } else {
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {

                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                            //message.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);

                        } else {

                        }

                    }
                }
                if (message3 != null) {
                    for (int i = 0; i < message3.length(); i++) {

                        if (PrimaryOfferTypeID == 1) {
                            if (message3.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                                message3.getJSONObject(i).put("ListCount", 1);
                                message3.getJSONObject(i).put("ClickCount", 1);
                                message3.getJSONObject(i).put("Quantity", quantity);
                                message3.getJSONObject(i).put("TotalQuantity", qty);
                            }
                        } else {

                            if (message3.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                                message3.getJSONObject(i).put("ClickCount", 1);
                                message3.getJSONObject(i).put("ListCount", 1);
                                message3.getJSONObject(i).put("TotalQuantity", qty);

                            }
                            if (message3.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                                message3.getJSONObject(i).put("Quantity", quantity);
                            }

                        }
                    }
                }
                if (messageCategory != null) {
                    for (int i = 0; i < messageCategory.length(); i++) {

                        if (PrimaryOfferTypeID == 1) {

                            if (messageCategory.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {

                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);

                            }

                        } else {

                            if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);

                            }
                            if (messageCategory.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                                messageCategory.getJSONObject(i).put("Quantity", quantity);

                            }

                        }
                    }
                }
                if (searchLable == true) {
                    searchProduct();
                } else {
                    fetchProduct();
                }


                shoppingListLoad();

                if (jsonParam == null) {

                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {

                        if (jsonParam.getJSONObject(j).getString("UPC").equalsIgnoreCase(upc)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

                        } else {

                        }
                    }

                    //fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 3) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    } else {
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }
                        if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                            message.getJSONObject(i).put("Quantity", quantity);
                        }
                    }
                }
                // fetchProduct();

                for (int i = 0; i < message3.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message3.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("Quantity", quantity);
                            message3.getJSONObject(i).put("TotalQuantity", qty);
                        }
                    } else {

                        if (message3.getJSONObject(i).getString("CouponID").equalsIgnoreCase(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message3.getJSONObject(i).getString("UPC").equalsIgnoreCase(upc)) {
                            message3.getJSONObject(i).put("Quantity", quantity);
                        }

                    }
                }
                //
                searchProduct();
                shoppingListLoad();
                if (jsonParam == null) {

                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").equalsIgnoreCase(upc)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else if (jsonParam.getJSONObject(j).getString("CouponID").equalsIgnoreCase(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        }
                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetProductActivateDetaile1(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType, String quantity) {


        if (x == 0) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);


                            }
                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);


                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);


                        }

                    }
                }
                if (messageCategory != null) {
                    for (int i = 0; i < messageCategory.length(); i++) {

                        if (PrimaryOfferTypeID == 1) {
                            if (messageCategory.getJSONObject(i).getString("UPC").contains(UPC)) {
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);

                            } else {
                                if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                    messageCategory.getJSONObject(i).put("ClickCount", 1);
                                    messageCategory.getJSONObject(i).put("ListCount", 1);
                                    //message.getJSONObject(i).put("Quantity", quantity);


                                }
                            }

                        } else {
                            if (messageCategory.getJSONObject(i).getString("UPC").contains(UPC)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);


                            }
                            if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);


                            }

                        }
                    }
                }

                fetchProduct();
                if (jsonParam == null) {

                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }
                fetchProduct();
                if (jsonParam == null) {

                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 3) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);


                            }
                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            //message.getJSONObject(i).put("TotalQuantity", qty);
                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }

                for (int i = 0; i < message3.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("Quantity", quantity);
                        }

                    } else {
                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("Quantity", quantity);
                            message3.getJSONObject(i).put("TotalQuantity", qty);
                        } else if (message3.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);
                            //message3.getJSONObject(i).put("Quantity", quantity);
                            message3.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }
                searchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetProductActivateDetaile(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType, String quantity) {
        if (x == 0) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);


                            }
                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }
                if (messageCategory != null) {
                    for (int i = 0; i < messageCategory.length(); i++) {

                        if (PrimaryOfferTypeID == 1) {
                            if (messageCategory.getJSONObject(i).getString("UPC").contains(UPC)) {
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);

                            } else {
                                if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                    messageCategory.getJSONObject(i).put("ClickCount", 1);
                                    messageCategory.getJSONObject(i).put("ListCount", 1);
                                    //message.getJSONObject(i).put("Quantity", quantity);


                                }
                            }

                        } else {
                            if (messageCategory.getJSONObject(i).getString("UPC").contains(UPC)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                messageCategory.getJSONObject(i).put("Quantity", quantity);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);

                            }
                            if (messageCategory.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                messageCategory.getJSONObject(i).put("ClickCount", 1);
                                messageCategory.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);
                                messageCategory.getJSONObject(i).put("TotalQuantity", qty);
                            }

                        }
                    }
                }

                //fetchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);

                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }
                fetchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 3) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("ListCount", 1);
                                //message.getJSONObject(i).put("Quantity", quantity);


                            }
                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);
                            //message.getJSONObject(i).put("TotalQuantity", qty);
                        }
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            //message.getJSONObject(i).put("Quantity", quantity);
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }

                for (int i = 0; i < message3.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("Quantity", quantity);
                        }

                    } else {
                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("Quantity", quantity);
                            message3.getJSONObject(i).put("TotalQuantity", qty);
                        } else if (message3.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 1);
                            //message3.getJSONObject(i).put("Quantity", quantity);
                            message3.getJSONObject(i).put("TotalQuantity", qty);
                        }

                    }
                }
                searchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetProductRemoveDetaile(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType, String quantity) {
        if (x == 0) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("ClickCount", 1);

                        }

                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("Quantity", quantity);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", quantity);


                            }
                        }

                    }
                }
                fetchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {
            try {

                for (int i = 0; i < morecouponlist.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 0);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                        }

                    } else {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            morecouponlist.getJSONObject(i).put("ListCount", 0);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        } else {
                            if (morecouponlist.getJSONObject(i).getString("CouponID") == CouponID) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

                    }
                }
                fetchMoreCoupon();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }

                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 3) {
            try {

                for (int i = 0; i < message3.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ListCount", 0);
                            message3.getJSONObject(i).put("ClickCount", 1);
                        }

                    } else {
                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 0);

                        } else {
                            if (message3.getJSONObject(i).getString("CouponID") == CouponID) {
                                message3.getJSONObject(i).put("ClickCount", 1);
                            }
                        }

                    }
                }
                searchProduct();
                if (jsonParam == null) {

                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        } else {
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }

                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetRemoveActivateDetail(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType) {

        if (x == 0) {
            try {

                for (int i = 0; i < message.length(); i++) {


                    if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                        message.getJSONObject(i).put("ClickCount", 1);
                        message.getJSONObject(i).put("ListCount", 0);
                        message.getJSONObject(i).put("Quantity", "0");
                        message.getJSONObject(i).put("TotalQuantity", qty);

                    } else {
                        if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 0);
                            // message.getJSONObject(i).put("Quantity", "0");
                            message.getJSONObject(i).put("TotalQuantity", qty);
                        }
                    }

                }
                fetchProduct();
                if (jsonParam == null) {

                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", 0);

                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        }

                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {

            try {

                for (int i = 0; i < morecouponlist.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                        }

                    } else {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            morecouponlist.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        } else {
                            if (morecouponlist.getJSONObject(i).getString("CouponID") == CouponID) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

                    }
                }
                fetchMoreCoupon();
                if (jsonParam == null) {

                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 3) {
            try {

                for (int i = 0; i < message.length(); i++) {


                    if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                        message.getJSONObject(i).put("ClickCount", 1);
                        message.getJSONObject(i).put("ListCount", 0);
                        message.getJSONObject(i).put("Quantity", "0");
                        //message.getJSONObject(i).put("TotalQuantity", qty);

                    }
                    if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                        message.getJSONObject(i).put("ClickCount", 1);
                        message.getJSONObject(i).put("ListCount", 0);
                        //message.getJSONObject(i).put("Quantity", "0");
                        message.getJSONObject(i).put("TotalQuantity", qty);
                    }

                }

                for (int i = 0; i < message3.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {

                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ListCount", 1);
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", "0");
                        }

                    } else {
                        if (message3.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 0);
                            message3.getJSONObject(i).put("Quantity", "0");
                            message3.getJSONObject(i).put("TotalQuantity", qty);

                        }

                    }
                }

                searchProduct();
                //fetchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", 0);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }
                    }
                    // jsonParam.getJSONObject(i).put("ClickCount", 1);
                    // jsonParam.getJSONObject(i).put("ListCount", 1);
                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetVeritesActivateDetaile(int PrimaryOfferTypeID, String CouponID, String UPC, String RequireActivation, int ActivateType) {
        if (x == 0) {
            try {

                for (int i = 0; i < message.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                        }
                    } else {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);

                        } else {
                            if (message.getJSONObject(i).getString("CouponID").equalsIgnoreCase(CouponID)) {
                                message.getJSONObject(i).put("ClickCount", 1);
                            }
                        }

                    }
                }
                fetchProduct();
                if (jsonParam == null) {
                    //no students
                } else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                        } else {
                            //  jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);

                        }

                    }

                    fetchVeritesProduct();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (x == 1) {
            try {
                for (int i = 0; i < morecouponlist.length(); i++) {

                    if (PrimaryOfferTypeID == 1) {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                        }
                    } else {
                        if (morecouponlist.getJSONObject(i).getString("CouponID") == CouponID) {
                            if (RequireActivation.contains("True") && PrimaryOfferTypeID == 2 && ActivateType == 2) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            } else {
                                if (RequireActivation.contains("True") && PrimaryOfferTypeID == 2 && ActivateType == 1) {
                                    morecouponlist.getJSONObject(i).put("ClickCount", 1);
                                    morecouponlist.getJSONObject(i).put("ListCount", 1);
                                } else if (PrimaryOfferTypeID == 2 && ActivateType == 1) {

                                } else {
                                    morecouponlist.getJSONObject(i).put("ListCount", 1);
                                    morecouponlist.getJSONObject(i).put("ClickCount", 1);
                                }

                            }


                        }
                    }
                }
                fetchMoreCoupon();
            } catch (JSONException e) {
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

        tv_quantity_detail.setText(relatedItem.getQuantity());

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
                if (shopper == 1) {

                    if (participate == 0) {
                        search_message.setVisibility(View.VISIBLE);
                    } else if (participate == 1) {
                        search_message.setVisibility(View.GONE);
                    }

                    shopper = 0;
                    DetaileToolbar.setVisibility(View.VISIBLE);
                    DetaileToolbar.setTitle("MyFareway List");
                    shopping_list_header.setVisibility(View.VISIBLE);
                    rv_shopping_list_items.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    liner_all_Varieties_activate.setVisibility(View.GONE);
                    rv_items_group.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    qty = 0;
                    //header_title visible
                    header_title.setVisibility(View.GONE);
                    DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shopping_list_header.setVisibility(View.GONE);
                            rv_shopping_list_items.setVisibility(View.GONE);
                            DetaileToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                            navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                            tv.setVisibility(View.VISIBLE);
                            x = 0;
                            z = 0;

                            //header_title visible

                        }
                    });
                } else {
                    scrollView.setVisibility(View.GONE);
                    DetaileToolbar.setVisibility(View.GONE);
                    rv_items_group.setVisibility(View.VISIBLE);
                    rv_items_verite.setVisibility(View.VISIBLE);
                    participateToolbar.setVisibility(View.VISIBLE);
                    liner_detail_add_item.setVisibility(View.VISIBLE);
                    liner_add_sub_button.setVisibility(View.VISIBLE);
                    if (Group.length() > 0) {
                        group_count_text.setVisibility(View.VISIBLE);
                    }
                    fetchVeritesProduct2(Group);
                }

                //messageLoadRefresh();
            }
        });
        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
        TextView tv_reg_price_detail = (TextView) findViewById(R.id.tv_reg_price_detail);
        TextView tv_save_detail = (TextView) findViewById(R.id.tv_save_detail);
        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
        TextView tv_valid_detail = (TextView) findViewById(R.id.tv_valid_detail);
        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
        TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
        TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
        TextView tv_promo_price_detail = (TextView) findViewById(R.id.tv_promo_price_detail);
        TextView tv_coupon_detail_detail = (TextView) findViewById(R.id.tv_coupon_detail_detail);

        //
        participateToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liner_all_Varieties_activate.setVisibility(View.GONE);
                qty = 0;
                //messageLoadRefresh();
                if (x == 0) {
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                    //header_title visible
                    header_title.setVisibility(View.GONE);

                } else if (x == 3) {
                    if (message3.length() < 5) {
                        search_message.setVisibility(View.VISIBLE);
                    }
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                    //header_title visible
                    header_title.setVisibility(View.GONE);
                } else if (shopper == 1) {
                    shopper = 0;
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    shopping_list_header.setVisibility(View.VISIBLE);
                    rv_shopping_list_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                    //header_title visible
                    header_title.setVisibility(View.GONE);
                    DetaileToolbar.setVisibility(View.VISIBLE);
                    DetaileToolbar.setTitle("MyFareway List");
                    DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (participate == 0) {
                                search_message.setVisibility(View.VISIBLE);
                            } else if (participate == 1) {
                                search_message.setVisibility(View.GONE);
                            }
                            shopping_list_header.setVisibility(View.GONE);
                            rv_shopping_list_items.setVisibility(View.GONE);
                            DetaileToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                            navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                            tv.setVisibility(View.VISIBLE);
                            x = 0;
                            z = 0;
                        }
                    });
                } else {
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                    //header_title visible
                    header_title.setVisibility(View.GONE);
                }


            }
        });
        //
        liner_all_Varieties_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Fareway Reated text", response.toString());
                                    //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                    SetProductActivateShopping(relatedItem.getUPC(), relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));

                                    if (relatedItem.getPrimaryOfferTypeId() == 3 || relatedItem.getPrimaryOfferTypeId() == 2) {

                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        all_Varieties_activate.setVisibility(View.VISIBLE);
                                        all_Varieties_activate.setText("Activated");
                                        imv_status_verities.setVisibility(View.VISIBLE);

                                    } else {
                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                        all_Varieties_activate.setVisibility(View.GONE);
                                        imv_status_verities.setVisibility(View.GONE);
                                    }


                                    progressDialog.dismiss();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Volley error resp", "error----" + error.getMessage());
                            error.printStackTrace();

                            if (error.networkResponse == null) {

                                if (error.getClass().equals(TimeoutError.class)) {
                                }
                            }
                            progressDialog.dismiss();
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
                            if (relatedItem.getPrimaryOfferTypeId() == 2) {
                                params.put("ClickType", "2");
                            } else {
                                params.put("ClickType", "2");
                            }
                            params.put("iPositionID", relatedItem.getTileNumber());
                            params.put("OPMOfferID", String.valueOf(relatedItem.getPricingMasterID()));
                            params.put("AdPrice", relatedItem.getAdPrice());
                            params.put("RegPrice", relatedItem.getRegularPrice());
                            params.put("Savings", relatedItem.getSavings());

                            return params;
                        }

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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();


                }


                //
            }
        });

        tv_varieties_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shopper == 1) {
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    //z=1;
                    //shopper=0;
                    scrollView.setVisibility(View.GONE);
                    DetaileToolbar.setVisibility(View.GONE);

                    liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                    rv_items_group.setVisibility(View.VISIBLE);
                    rv_items_verite.setVisibility(View.VISIBLE);
                    participateToolbar.setVisibility(View.VISIBLE);
                    if (Group.length() > 0) {
                        group_count_text.setVisibility(View.VISIBLE);
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    DetaileToolbar.setVisibility(View.GONE);
                    rv_items_group.setVisibility(View.VISIBLE);
                    rv_items_verite.setVisibility(View.VISIBLE);
                    participateToolbar.setVisibility(View.VISIBLE);
                    liner_detail_add_item.setVisibility(View.VISIBLE);
                    liner_add_sub_button.setVisibility(View.VISIBLE);
                    if (Group.length() > 0) {
                        group_count_text.setVisibility(View.VISIBLE);
                    }
                }

                //fetchVeritesProduct2(Group);
                /*header_title.setVisibility(View.GONE);

                if (relatedItem.getPrimaryOfferTypeId()==3 || relatedItem.getPrimaryOfferTypeId()==2){

                    if (relatedItem.getClickCount()==0){
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                        all_Varieties_activate.setText("Activate");
                        imv_status_verities.setVisibility(View.GONE);

                    } else {
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        all_Varieties_activate.setVisibility(View.VISIBLE);
                        all_Varieties_activate.setText("Activated");
                        imv_status_verities.setVisibility(View.VISIBLE);
                    }
                }

                else {
                    liner_all_Varieties_activate.setVisibility(View.GONE);
                    all_Varieties_activate.setVisibility(View.GONE);
                    imv_status_verities.setVisibility(View.GONE);
                }

                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                participateToolbar.setVisibility(View.VISIBLE);
                participateToolbar.setTitle("Participating Items");

                Group="";

                groupItemsList.clear();
                relatedItemsList.clear();
                qty=0;


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
                                                rv_items_verite.setVisibility(View.VISIBLE);
                                                Log.i("Verites response", response.toString());
                                                jsonParam = new JSONArray(response.toString());
                                                for (int i = 0; i < jsonParam.length(); i++) {
                                                    jsonParam.getJSONObject(i).getString("Quantity");
                                                    qty= qty+Integer.parseInt(jsonParam.getJSONObject(i).getString("Quantity"));
                                                    Log.i("qut", jsonParam.getJSONObject(i).getString("Quantity"));
                                                }
                                                Log.i("qut", String.valueOf(qty));

                                                progressDialog.dismiss();

                                                fetchVeritesProduct();
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

                                if (relatedItem.getPrimaryOfferTypeId()==1){
                                    params.put("PriceAssociationCode",relatedItem.getPriceAssociationCode());
                                    params.put("UPC", relatedItem.getUPC());
                                    params.put("StoreId", relatedItem.getStoreID());
                                    params.put("Plateform", "2");
                                    params.put("PrimaryOfferTypeId", String.valueOf(relatedItem.getPrimaryOfferTypeId()));
                                    params.put("RelevantUPC", relatedItem.getRelevantUPC());
                                }else {
                                    params.put("PriceAssociationCode",relatedItem.getPriceAssociationCode());
                                    params.put("UPC", String.valueOf(relatedItem.getCouponID()));
                                    params.put("StoreId", relatedItem.getStoreID());
                                    params.put("Plateform", "2");
                                    params.put("PrimaryOfferTypeId", String.valueOf(relatedItem.getPrimaryOfferTypeId()));
                                    params.put("RelevantUPC", relatedItem.getRelevantUPC());
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
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                }

                else {
                    alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok),getString(R.string.alert));
                    alertDialog.show();
                }*/
            }
        });

        ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);

        imv_item_detaile.setImageDrawable(getResources().getDrawable(R.drawable.item));
        final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
        final LinearLayout circular_layout_detaile = (LinearLayout) findViewById(R.id.circular_layout_detaile);
        LinearLayout bottomLayout_detaile = (LinearLayout) findViewById(R.id.bottomLayout_detaile);
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
        TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
        TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);
        TableRow table_promo = (TableRow) findViewById(R.id.table_promo);
        TableRow table_promo_view = (TableRow) findViewById(R.id.table_promo_view);
        TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
        TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);


        Log.i("image", relatedItem.getLargeImagePath() + "singh");
        if (relatedItem.getOfferDefinitionId() == 5 || relatedItem.getOfferDefinitionId() == 8) {
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else {
                Picasso.get().load(relatedItem.getLargeImagePath()).into(imv_item_detaile);
            }
        } else {
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")) {
                Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else {
                Picasso.get().load(relatedItem.getLargeImagePath()).into(imv_item_detaile);
            }
        }
        String saveDate = relatedItem.getValidityEndDate();
        if (saveDate.length() == 0) {

        } else {
            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
            Date newDate = null;
            try {
                newDate = spf.parse(saveDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String c = "MM/dd";
            spf = new SimpleDateFormat(c);
            saveDate = spf.format(newDate);
            tv_valid_detail.setText(saveDate);
            System.out.println(saveDate);
        }


        if (relatedItem.getPrimaryOfferTypeId() == 3) {
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_detail_detail.setVisibility(View.VISIBLE);
            tv_fareway_flag.setText("With MyFareway");
            circular_layout_detaile.setVisibility(View.VISIBLE);
            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);
            table_package.setVisibility(View.GONE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            table_promo.setVisibility(View.GONE);
            table_promo_view.setVisibility(View.GONE);
            tv_package_detail.setText(relatedItem.getPackagingSize());
            Log.i("listCount", String.valueOf(relatedItem.getListCount()));

            if (relatedItem.getPackagingSize().equalsIgnoreCase("")) {
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            } else {
                tv_package_detail.setText(relatedItem.getPackagingSize());
            }
            Log.i("getClickCount", String.valueOf(relatedItem.getClickCount()));
            if (relatedItem.getClickCount() == 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
            } else if (relatedItem.getClickCount() > 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
            }


            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

            String displayPrice = relatedItem.getDisplayPrice().toString();
            if (relatedItem.getDisplayPrice().toString().split("\\.").length > 1)
                displayPrice = relatedItem.getDisplayPrice().split("\\.")[0] + "<sup>" + relatedItem.getDisplayPrice().split("\\.")[1] + "<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
            tv_price_detaile.setText(result);
            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars + " " + relatedItem.getPackagingSize());

            tv_reg_price_detail.setText("$ " + relatedItem.getRegularPrice());
            float myFloatSaving = Float.parseFloat(relatedItem.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
            /*try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/
            tv_upc_detail.setText(relatedItem.getUPC());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            if (relatedItem.getHasRelatedItems() == 1) {
                if (relatedItem.getRelatedItemCount() > 1) {
                    table_varieties.setVisibility(View.VISIBLE);
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + relatedItem.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                } else {
                    table_varieties.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.GONE);
                }
            } else if (relatedItem.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }
            //

        } else if (relatedItem.getPrimaryOfferTypeId() == 2) {
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_detail_detail.setVisibility(View.VISIBLE);
            tv_fareway_flag.setText("With Coupon");
            circular_layout_detaile.setVisibility(View.VISIBLE);

            table_limit.setVisibility(View.VISIBLE);
            table_limit_view.setVisibility(View.VISIBLE);

            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);

            table_promo.setVisibility(View.VISIBLE);
            table_promo_view.setVisibility(View.VISIBLE);

            table_coupon.setVisibility(View.VISIBLE);
            table_coupon_view.setVisibility(View.VISIBLE);

            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);

            table_coupon.setVisibility(View.VISIBLE);
            table_coupon_view.setVisibility(View.VISIBLE);

            table_package.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);

            table_upc.setVisibility(View.VISIBLE);
            table_upc_view.setVisibility(View.VISIBLE);
            Log.i("getAdPrice", relatedItem.getAdPrice());

            if (relatedItem.getAdPrice().equalsIgnoreCase("0.0000") || relatedItem.getAdPrice().equalsIgnoreCase("0")) {
                Log.i("if", "atul");
                table_limit.setVisibility(View.VISIBLE);
                table_limit_view.setVisibility(View.VISIBLE);
                table_regular.setVisibility(View.GONE);
                table_regular_view.setVisibility(View.GONE);
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

                table_promo.setVisibility(View.GONE);
                table_promo_view.setVisibility(View.GONE);
                table_coupon.setVisibility(View.GONE);
                table_coupon_view.setVisibility(View.GONE);
                table_save.setVisibility(View.GONE);
                table_save_view.setVisibility(View.GONE);

                table_upc.setVisibility(View.VISIBLE);
                table_upc_view.setVisibility(View.VISIBLE);

                table_varieties.setVisibility(View.VISIBLE);
                table_varieties_view.setVisibility(View.VISIBLE);

            }


            try {
                float myFloatCouponDiscount = Float.parseFloat(relatedItem.getCouponDiscount() + "f");
                String formattedStringCouponDiscount = String.format("%.02f", myFloatCouponDiscount);
                tv_coupon_detail.setText("$" + formattedStringCouponDiscount);

                float myFloatAdPrice = Float.parseFloat(relatedItem.getAdPrice() + "f");
                String formattedStringAdPrice = String.format("%.02f", myFloatAdPrice);
                tv_promo_price_detail.setText("$" + formattedStringAdPrice);

                float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice() + "f");
                String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);



                /*DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getCouponDiscount());
                Number num2 = dF.parse(relatedItem.getAdPrice());
                Number num3 = dF.parse(relatedItem.getRegularPrice());
                tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));
                tv_promo_price_detail.setText("$ " + new DecimalFormat("##.##").format(num2));*/
                if (formattedStringAdPrice.equalsIgnoreCase(formattedStringRegularPrice)) {
                    table_promo.setVisibility(View.GONE);
                    table_promo_view.setVisibility(View.GONE);
                }

            } catch (Exception e) {

            }


            tv_package_detail.setText(relatedItem.getPackagingSize());
            if (relatedItem.getClickCount() == 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
            } else if (relatedItem.getClickCount() > 0) {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
            }

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));
            /*Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);*/
            //
            String displayPrice = relatedItem.getDisplayPrice().toString();
            Log.i("OfferDefinitionId", String.valueOf(relatedItem.getOfferDefinitionId()));
            Log.i("RewardType", relatedItem.getRewardType());
            if (relatedItem.getDisplayPrice().toString().split("\\.").length > 1)
                displayPrice = relatedItem.getDisplayPrice().split("\\.")[0] + "<sup>" + relatedItem.getDisplayPrice().split("\\.")[1] + "<sup>";

            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars + " " + relatedItem.getPackagingSize());

            String chars2 = capitalize(relatedItem.getCouponShortDescription());
            tv_coupon_detail_detail.setText("\n" + chars2);


            //tv_reg_price_detail.setText("$ "+relatedItem.getRegularPrice());

            float myFloatRegularPrice = Float.parseFloat(relatedItem.getRegularPrice() + "f");
            String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);
            tv_reg_price_detail.setText("$" + formattedStringRegularPrice);

            float myFloatSaving = Float.parseFloat(relatedItem.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
            /*try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/

            if (relatedItem.getHasRelatedItems() == 1) {
                if (relatedItem.getRelatedItemCount() > 1) {
                    table_varieties.setVisibility(View.VISIBLE);
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + "Participating Items" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                } else {
                    table_varieties.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                }
            } else if (relatedItem.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }
            tv_limit.setText(String.valueOf(relatedItem.getLimitPerTransection()));
            tv_upc_detail.setText(relatedItem.getUPC());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

        } else if (relatedItem.getPrimaryOfferTypeId() == 1) {
            tv_coupon_detail_detail.setVisibility(View.GONE);
            tv_detail_detail.setVisibility(View.VISIBLE);
            tv_fareway_flag.setText(" ");
            circular_layout_detaile.setVisibility(View.INVISIBLE);

            table_limit.setVisibility(View.GONE);
            table_limit_view.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);
            table_package.setVisibility(View.GONE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_coupon.setVisibility(View.GONE);
            table_coupon_view.setVisibility(View.GONE);
            table_promo.setVisibility(View.GONE);
            table_promo_view.setVisibility(View.GONE);
            tv_package_detail.setText(relatedItem.getPackagingSize());
           /* if (relatedItem.getListCount()>0){
                Log.i("iflistCount", String.valueOf(relatedItem.getListCount()));
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Added");

            }else if (relatedItem.getListCount()==0){
                Log.i("elselistCount", String.valueOf(relatedItem.getListCount()));
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add");

            }*/
            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));
            Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>", "<sup><small>").replace("</sup>", "</small></sup>"));
            tv_price_detaile.setText(result);

            String chars = capitalize(relatedItem.getDescription());
            tv_detail_detail.setText(chars + " " + relatedItem.getPackagingSize());

            tv_reg_price_detail.setText("$ " + relatedItem.getRegularPrice());
            float myFloatSaving = Float.parseFloat(relatedItem.getSavings() + "f");
            String formattedString = String.format("%.02f", myFloatSaving);
            tv_save_detail.setText("$" + formattedString);
            /*try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(relatedItem.getSavings());
                tv_save_detail.setText("$ " + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }*/

            tv_upc_detail.setText(relatedItem.getUPC());
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            if (relatedItem.getHasRelatedItems() == 1) {
                if (relatedItem.getRelatedItemCount() > 1) {
                    table_varieties.setVisibility(View.VISIBLE);
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    table_varieties_view.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + relatedItem.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                } else {
                    table_varieties.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>" + relatedItem.getRelatedItemCount() + " Varieties" + "</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }
            } else if (relatedItem.getHasRelatedItems() == 0) {
                table_varieties.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }

        }


        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (relatedItem.getClickCount() == 0) {

                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Circular btn Main", response.toString());
                                            fetchShoppingListLoad();
                                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                            imv_status_detaile.setVisibility(View.VISIBLE);
                                            imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                            tv_status_detaile.setText("Activated");
                                            //
                                            liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                            liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                            all_Varieties_activate.setVisibility(View.VISIBLE);
                                            all_Varieties_activate.setText("Activated");
                                            imv_status_verities.setVisibility(View.VISIBLE);

                                            if (relatedItem.getPrimaryOfferTypeId() == 3 || relatedItem.getPrimaryOfferTypeId() == 2) {
                                                SetProductActivateShopping(relatedItem.getUPC(), relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));

                                                groupcount = 1;
                                                // veritiesGroupDetail2(relatedItem.getCouponID());
                                            }

                                            //   SetVeritesActivateDetaile
                                            //           (relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);

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

                                    params.put("ClickType", "2");
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
                                mQueue.add(jsonObjectRequest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                }
            }
        });

        add_minus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                if (Integer.parseInt(relatedItem.getQuantity()) > 1) {
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println(currentDate);
                    JSONObject ShoppingListItems = new JSONObject();
                    try {
                        ShoppingListItems.put("UPC", relatedItem.getUPC());
                        ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity()) - 1));
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
                    final String mRequestBody = "'" + studentsObj.toString() + "'";
                    Log.i("test", mRequestBody);
                    String url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + relatedItem.getUPC() + "&Quantity=" + (Integer.parseInt(relatedItem.getQuantity()) - 1) + "&DateAddedOn=" + currentDate;

                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("success", String.valueOf(response));
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) - 1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        //.setText(product.getQuantity());
                                        qty = qty - 1;

                                        //
                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) - 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (Integer.parseInt(relatedItem.getQuantity()) == 1) {
                    //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));

                    Log.i("remove", "remove");
                    String url = Constant.WEB_URL + Constant.REMOVE + relatedItem.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("success", String.valueOf(response));

                                        qty = qty - 1;
                                        tv_quantity_detail.setText("0");

                                        SetRemoveActivateDetail(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1);
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                        ///
                                        /*fetchShoppingListLoad();
                                        qty= Integer.parseInt(product.getTotalQuantity())-1;

                                        tv_quantity_detail.setText("0");

                                        SetRemoveActivateDetail(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                        progressDialog.dismiss();*/
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDialog.dismiss();
                }
            }
        });

        add_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", relatedItem.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity()) + 1));
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
                final String mRequestBody = "'" + studentsObj.toString() + "'";
                Log.i("test", mRequestBody);
                //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                String url = "";
                Log.i("testobject", mRequestBody);
                if (relatedItem.getQuantity().equalsIgnoreCase("0") && relatedItem.getPrimaryOfferTypeId() == 1) {

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("ClickAdd", response.toString());
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());

                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                        progressDialog.dismiss();
                                        //
                                        //

                                       /* Log.i("success", String.valueOf(response));
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                        relatedItem.setTotalQuantity(String.valueOf((Integer.parseInt(relatedItem.getTotalQuantity()) + 1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        Log.i("testqty", String.valueOf(qty));
                                        qty = Integer.parseInt(relatedItem.getTotalQuantity());
                                        Log.i("testqty", String.valueOf(qty));*/

                                        /*SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                        fetchShoppingListLoad();*/
                                        //
                                        //
                                        progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
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
                                if (relatedItem.getPrimaryOfferTypeId() == 2) {
                                    params.put("ClickType", "1");
                                } else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", relatedItem.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(relatedItem.getPricingMasterID()));
                                params.put("AdPrice", relatedItem.getAdPrice());
                                params.put("RegPrice", relatedItem.getRegularPrice());
                                params.put("Savings", relatedItem.getSavings());

                                return params;
                            }

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                } else if (relatedItem.getQuantity().equalsIgnoreCase("0")) {
                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        //add_item_flag_detail.setText(relatedItem.getQuantity());
                                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        imv_status_detaile.setVisibility(View.VISIBLE);
                                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        tv_status_detaile.setText("Activated");
                                        qty = qty + 1;
                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();

                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        all_Varieties_activate.setVisibility(View.VISIBLE);
                                        all_Varieties_activate.setText("Activated");
                                        imv_status_verities.setVisibility(View.VISIBLE);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volley error resp", "error----" + error.getMessage());
                                error.printStackTrace();

                                if (error.networkResponse == null) {

                                    if (error.getClass().equals(TimeoutError.class)) {
                                    }
                                }
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
                                if (relatedItem.getPrimaryOfferTypeId() == 2) {
                                    params.put("ClickType", "1");
                                } else {
                                    params.put("ClickType", "1");
                                }
                                params.put("iPositionID", relatedItem.getTileNumber());
                                params.put("OPMOfferID", String.valueOf(relatedItem.getPricingMasterID()));
                                params.put("AdPrice", relatedItem.getAdPrice());
                                params.put("RegPrice", relatedItem.getRegularPrice());
                                params.put("Savings", relatedItem.getSavings());

                                return params;
                            }

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                } else {
                    url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + relatedItem.getUPC() + "&Quantity=" + (Integer.parseInt(relatedItem.getQuantity()) + 1) + "&DateAddedOn=" + currentDate;

                    try {
                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("ClickAddgr81", String.valueOf(response));
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        //add_item_flag_detail.setText(relatedItem.getQuantity());
                                        //
                                        Log.i("qty", String.valueOf(qty));
                                        qty = qty + 1;

                                        ////
                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                        fetchShoppingListLoad();
                                        //progressDialog.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("fail", String.valueOf(error));
                            }
                        }) {

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
                            mQueue.add(jsonObjectRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    @Override
    public void onRelatedItemSelected2(final RelatedItem relatedItem) {
        participate = 1;
        Log.i("clickcount", String.valueOf(relatedItem.getClickCount()));
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        JSONObject json = new JSONObject();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
        String currentDate = dateformat2.format(c2.getTime());
        System.out.println(currentDate);

        JSONObject ShoppingListItems = new JSONObject();
        try {
            ShoppingListItems.put("UPC", relatedItem.getUPC());
            ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity()) + 1));
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

        final String mRequestBody = "'" + studentsObj.toString() + "'";
        String url = null;
        Log.i("testobject", mRequestBody);

        if (relatedItem.getQuantity().equalsIgnoreCase("0")) {
            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Reated text", response.toString());
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                relatedItem.setClickCount(1);
                                qty = qty + 1;
                                //
                                SetProductActivateShopping(relatedItem.getUPC(), relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
//
                                if (relatedItem.getPrimaryOfferTypeId() == 3 || relatedItem.getPrimaryOfferTypeId() == 2) {

                                    liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                    liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                    all_Varieties_activate.setVisibility(View.VISIBLE);
                                    all_Varieties_activate.setText("Activated");
                                    imv_status_verities.setVisibility(View.VISIBLE);

                                } else {
                                    liner_all_Varieties_activate.setVisibility(View.GONE);
                                    all_Varieties_activate.setVisibility(View.GONE);
                                    imv_status_verities.setVisibility(View.GONE);
                                }
                                //progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();

                        if (error.networkResponse == null) {

                            if (error.getClass().equals(TimeoutError.class)) {
                            }
                        }
                        progressDialog.dismiss();
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
                        if (relatedItem.getPrimaryOfferTypeId() == 2) {
                            params.put("ClickType", "1");
                        } else {
                            params.put("ClickType", "1");
                        }
                        params.put("iPositionID", relatedItem.getTileNumber());
                        params.put("OPMOfferID", String.valueOf(relatedItem.getPricingMasterID()));
                        params.put("AdPrice", relatedItem.getAdPrice());
                        params.put("RegPrice", relatedItem.getRegularPrice());
                        params.put("Savings", relatedItem.getSavings());

                        return params;
                    }

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

                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            } catch (Exception e) {

                e.printStackTrace();
                progressDialog.dismiss();


            }

        } else {
            url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + relatedItem.getUPC() + "&Quantity=" + (Integer.parseInt(relatedItem.getQuantity()) + 1) + "&DateAddedOn=" + currentDate;

            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Selected2success", String.valueOf(response));
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 1)));
                                Log.i("qtytest", String.valueOf(qty));
                                qty = qty + 1;
                                //
                                Log.i("qtytest2", String.valueOf(qty));
                                SetProductActivateShopping(relatedItem.getUPC(), relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                //progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        progressDialog.dismiss();
                    }
                }) {

                   /* @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }*/

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        }
    }

    @Override
    public void onRelatedItemSelected3(final RelatedItem relatedItem) {
        participate = 1;
        Log.i("remove", "remove");
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        if (Integer.parseInt(relatedItem.getQuantity()) > 1) {

            JSONObject json = new JSONObject();
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
            String currentDate = dateformat2.format(c2.getTime());
            System.out.println(currentDate);

            JSONObject ShoppingListItems = new JSONObject();
            try {
                ShoppingListItems.put("UPC", relatedItem.getUPC());
                ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity()) - 1));
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

            final String mRequestBody = "'" + studentsObj.toString() + "'";
            String url = "";
            Log.i("testobject", mRequestBody);
            url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + relatedItem.getUPC() + "&Quantity=" + (Integer.parseInt(relatedItem.getQuantity()) - 1) + "&DateAddedOn=" + currentDate;

            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("success", String.valueOf(response));
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity()) - 1)));
                                qty = qty - 1;

                                SetProductActivateShopping(relatedItem.getUPC(), relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) - 0)));
                                //progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        progressDialog.dismiss();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        } else if (Integer.parseInt(relatedItem.getQuantity()) == 1) {
            Log.i("remove", "remove");
            String url = Constant.WEB_URL + Constant.REMOVE + relatedItem.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("success", String.valueOf(response));

                                //progressDialog.dismiss();
                                qty = qty - 1;

                                //

                                SetRemoveActivateDetail(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1);

                                fetchShoppingListLoad();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

        } else {
            progressDialog.dismiss();
        }

    }

    @Override
    public void onRelatedItemSelected4(final RelatedItem relatedItem) {
        Log.i("remove", "remove");

        String url = Constant.WEB_URL + Constant.REMOVE + relatedItem.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("success", String.valueOf(response));
                        fetchShoppingListLoad();
                        if (relatedItem.getPrimaryOfferTypeId() == 3 || relatedItem.getPrimaryOfferTypeId() == 2) {
                            relatedItem.setClickCount(0);
                            relatedItem.setListCount(0);
                            relatedItem.setQuantity("1");
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                            groupcount = 1;
                            //onProductVeritiesSelected(productrelated2);
                            veritiesGroupDetail(relatedItem.getCouponID());
                        } else if (relatedItem.getPrimaryOfferTypeId() == 1) {
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, "5");
                        }

                        //    holder.count_product_number.setVisibility(View.GONE);
                        //product.setClickCount(0);
                        //product.setListCount(0);
                        //product.setQuantity("0");
                        //holder.tv_status.setText("Add");
                        //holder.circular_layout.setBackground(mContext.getResources().getDrawable(R.drawable.circular_red_bg));
                        //holder.imv_status.setImageDrawable(mContext.getResources().getDrawable(R.drawable.addwhite));
                        // holder.tv_quantity.setText("1");
                        //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                        //holder.tv_quantity.setText(product.getQuantity());
                        //serverResp.setText("String Response : "+ response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
                // serverResp.setText("Error getting response");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRelatedItemSelected5(final RelatedItem relatedItem) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                fetchShoppingListLoad();
                                if (relatedItem.getPrimaryOfferTypeId() == 3 || relatedItem.getPrimaryOfferTypeId() == 2) {
                                    relatedItem.setClickCount(1);
                                    relatedItem.setListCount(1);
                                    relatedItem.setQuantity("1");
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, String.valueOf((Integer.parseInt(relatedItem.getQuantity()) + 0)));
                                    groupcount = 1;
                                    //onProductVeritiesSelected(productrelated2);
                                    veritiesGroupDetail(relatedItem.getCouponID());
                                } else if (relatedItem.getPrimaryOfferTypeId() == 1) {
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(), relatedItem.getCouponID(), relatedItem.getUPC(), relatedItem.getRequiresActivation(), 1, "5");
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
                }) {

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
        }
    }

    private void fetchVeritesProduct() {
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
            //rv_items_verite.setVisibility(View.VISIBLE);
        }
    }

    private void fetchVeritesProduct2(String group) {
        if (jsonParam.length() == 0) {
            //no students
        } else {

            String groupData = "";
            for (int i = 0; i < jsonParam.length(); i++) {
                try {
                    if (jsonParam.getJSONObject(i).getString("Filter").contains(group)) {
                        Group = jsonParam.getJSONObject(i).getString("Filter");
                        JSONObject finalObject = jsonParam.getJSONObject(i);
                        groupData += (groupData == "" ? String.valueOf(finalObject) : "," + String.valueOf(finalObject));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            List<RelatedItem> items = new Gson().fromJson("[" + groupData.toString() + "]", new TypeToken<List<RelatedItem>>() {
            }.getType());
            relatedItemsList.clear();
            relatedItemsList.addAll(items);
            // refreshing recycler view
            customAdapterParticipateItems.notifyDataSetChanged();
            rv_items_verite.setVisibility(View.VISIBLE);

        }
    }

    public void veritiesGroupDetail(String CouponId) {

        group_count_text.setVisibility(View.VISIBLE);
        try {
            for (int p = 0; p < message.length(); p++) {
                String a = message.getJSONObject(p).getString("CouponID");
                if (a == CouponId) {
                    String GroupStr = message.getJSONObject(p).getString("Groupname");

                    group_count_text.setText(GroupStr);

                    String GroupData = "";
                    String selectGroupLimit = "";
                    String selectGroupLimitDetail = "";
                    if (Group == "") {
                        if (GroupStr.split(",").length > 0)
                            Group = GroupStr.split(",")[0].split(":")[0];

                    }
                    Log.i("testlog", Group);
                    for (int i = 0; GroupStr.split(",").length > i; i++) {

                        selectGroupLimitDetail += (selectGroupLimitDetail == "" ? " You must buy " + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + " from Group " + GroupStr.split(",")[i].split(":")[0] : " " +
                                "and " + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + " from Group " + GroupStr.split(",")[i].split(":")[0] + "");


                        selectGroupLimit += (selectGroupLimit == "" ? GroupStr.split(",")[i].split(":")[0] + ":" + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) : "," +
                                GroupStr.split(",")[i].split(":")[0] + ":" + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + "");


                        GroupData += (GroupData == "" ? "{\"Groupname\": \"" + GroupStr.split(",")[i].split(":")[0] + "\"}" : "," + "{\"Groupname\": \"" + GroupStr.split(",")[i].split(":")[0] + "\"}");

                        Log.i("for", GroupStr.split(",")[i].split(":")[1]);
                    }
                    group_count_text.setText(selectGroupLimitDetail);
                    message.getJSONObject(p).put("Groupname", selectGroupLimit);
                    groupcount = 0;
                    Log.i("new ", selectGroupLimit);
                    List<Group> items = new Gson().fromJson(" [" + GroupData + "]", new TypeToken<List<Group>>() {
                    }.getType());

                    // adding contacts to contacts list
                    if (GroupStr.split(",").length > 1) {
                        groupItemsList.clear();
                        groupItemsList.addAll(items);
                        customGroupAdapter.notifyDataSetChanged();

                    }
                    Log.i("testcoup", String.valueOf(a));
                }

            }
            fetchProduct();
        } catch (Exception e) {

        }


    }

    public void veritiesGroupDetail2(String CouponId) {

        try {
            for (int p = 0; p < message.length(); p++) {
                String a = message.getJSONObject(p).getString("CouponID");
                if (a == CouponId) {
                    String GroupStr = message.getJSONObject(p).getString("Groupname");

                    group_count_text.setText(GroupStr);
                    String GroupData = "";
                    String selectGroupLimit = "";
                    String selectGroupLimitDetail = "";
                    if (Group == "") {
                        if (GroupStr.split(",").length > 0)
                            Group = GroupStr.split(",")[0].split(":")[0];

                    }
                    Log.i("testlog", Group);
                    for (int i = 0; GroupStr.split(",").length > i; i++) {

                        selectGroupLimitDetail += (selectGroupLimitDetail == "" ? " You must buy " + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + " from Group " + GroupStr.split(",")[i].split(":")[0] : " " +
                                "and " + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + " from Group " + GroupStr.split(",")[i].split(":")[0] + "");


                        selectGroupLimit += (selectGroupLimit == "" ? GroupStr.split(",")[i].split(":")[0] + ":" + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) : "," +
                                GroupStr.split(",")[i].split(":")[0] + ":" + (Group.contains(GroupStr.split(",")[i].split(":")[0]) == true ? ((Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount) > 0 ? Integer.parseInt(GroupStr.split(",")[i].split(":")[1]) - groupcount : "0") : GroupStr.split(",")[i].split(":")[1]) + "");


                        GroupData += (GroupData == "" ? "{\"Groupname\": \"" + GroupStr.split(",")[i].split(":")[0] + "\"}" : "," + "{\"Groupname\": \"" + GroupStr.split(",")[i].split(":")[0] + "\"}");

                        Log.i("for", GroupStr.split(",")[i].split(":")[1]);
                    }
                    group_count_text.setText(selectGroupLimitDetail);
                    message.getJSONObject(p).put("Groupname", selectGroupLimit);
                    groupcount = 0;
                    Log.i("new ", selectGroupLimit);
                    List<Group> items = new Gson().fromJson(" [" + GroupData + "]", new TypeToken<List<Group>>() {
                    }.getType());

                    // adding contacts to contacts list
                    groupItemsList.clear();
                    groupItemsList.addAll(items);
                    customGroupAdapter.notifyDataSetChanged();


                    Log.i("testcoup", String.valueOf(a));
                }

            }
            fetchProduct();
        } catch (Exception e) {

        }
    }

    private String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public void shoppingListTabLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.ShoppingList + "MemberId=" + appUtil.getPrefrence("MemberId") + "&CategoryID=1",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")) {
                                        progressDialog.dismiss();
                                        Log.i("anshuman", "test");

                                        try {
                                            shopping = root2.getJSONArray("ShoppingListItems");
                                        } catch (Exception ex) {
                                            shopping = null;
                                        }

                                        if (shopping == null) {
                                            Log.i("anshuman", "test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            tv.setText(String.valueOf(0));

                                        } else {
                                            Log.i("shopping", String.valueOf(shopping));

                                            for (int i = 0; i < shopping.length(); i++) {
                                            }
                                            tv_number_item.setText(String.valueOf(shopping.length()));
                                            tv.setText(String.valueOf(shopping.length()));

                                            shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();
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
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                //final Shopping item = shoppingListAdapter.getData().get(position);

                shoppingListAdapter.removeItem(position);


               /* Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        shoppingListAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });*/

                //snackbar.setActionTextColor(Color.YELLOW);
                //snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv_shopping_list_items);
    }

    private void shoppingListIdLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + "ShoppingList/List?" + "MemberId=" + appUtil.getPrefrence("MemberId") + "&CategoryID=1",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("ShoppingListId", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")) {
                                        //progressDialog.dismiss();
                                        Log.i("anshuman", "test");

                                        try {
                                            shoppingId = root2.getJSONArray("ListName");
                                        } catch (Exception ex) {
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
                                }
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //  progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void shoppingListLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.ShoppingList + "MemberId=" + appUtil.getPrefrence("MemberId") + "&CategoryID=1",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("shoppingList Response", response.toString());
                                progressDialog.dismiss();

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")) {
                                        // progressDialog.dismiss();
                                        Log.i("anshuman", "test");

                                        try {
                                            shopping = root2.getJSONArray("ShoppingListItems");
                                        } catch (Exception ex) {
                                            shopping = null;
                                        }

                                        if (shopping == null) {
                                            Log.i("shopping", "test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            tv.setText(String.valueOf(0));
                                            //activatedOffersListIdLoad();

                                        } else {
                                            Log.i("shopping", String.valueOf(shopping));
                                            String couponId = "";

                                            for (int i = 0; i < shopping.length(); i++) {

                                               /* couponId=shopping.getJSONObject(i).getString("CouponID");

                                                Log.i("couponId", shopping.getJSONObject(i).getString("CouponID"));

                                                shopping.getJSONObject(i).getString("Quantity");
                                                qty= qty+Integer.parseInt(shopping.getJSONObject(i).getString("Quantity"));
                                                Log.i("qut", shopping.getJSONObject(i).getString("Quantity"));*/
                                            }
                                            tv_number_item.setText(String.valueOf(shopping.length()));
                                            tv.setText(String.valueOf(shopping.length()));
                                            //
                                            //activatedOffersListIdLoad();
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
                        //  progressDialog.dismiss();
                    }
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void fetchShoppingListLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.ShoppingList + "MemberId=" + appUtil.getPrefrence("MemberId") + "&CategoryID=1",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")) {
                                        //progressDialog.dismiss();
                                        Log.i("anshuman", "test");

                                        try {
                                            shopping = root2.getJSONArray("ShoppingListItems");
                                        } catch (Exception ex) {
                                            shopping = null;
                                        }

                                        if (shopping == null) {
                                            Log.i("anshuman2", "test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            tv.setText(String.valueOf(0));
                                            //messageLoadRefresh();
                                            for (int i = 0; i < message.length(); i++) {

                                                if (shoppingCouponID.equalsIgnoreCase(message.getJSONObject(i).getString("CouponID"))) {
//
                                                    message.getJSONObject(i).put("TotalQuantity", 0);
                                                    //message.getJSONObject(i).put("Quantity", 0);
                                                    //message.getJSONObject(i).put("ClickCount", 0);

                                                }
                                            }
                                            if (messageCategory != null) {
                                                for (int i = 0; i < messageCategory.length(); i++) {


                                                    messageCategory.getJSONObject(i).put("TotalQuantity", 0);
                                                    //messageCategory.getJSONObject(i).put("Quantity", 0);
                                                    //messageCategory.getJSONObject(i).put("ClickCount", 0);


                                                }
                                            }
                                            if (message3 != null) {
                                                for (int i = 0; i < message3.length(); i++) {


                                                    message3.getJSONObject(i).put("TotalQuantity", 0);
                                                    //message3.getJSONObject(i).put("Quantity", 0);
                                                    //message3.getJSONObject(i).put("ClickCount", 0);


                                                }
                                            }
                                            if (x == 3) {

                                            } else {
                                                if (searchLable == true) {
                                                    searchProduct();
                                                } else {
                                                    fetchProduct();
                                                }
                                            }
                                            Log.i("qut", String.valueOf(0));
                                            progressDialog.dismiss();

                                        } else {
                                            Log.i("shopping", String.valueOf(shopping));
                                            String couponId = "";
                                            int qtyShopping = 0;

                                            for (int i = 0; i < shopping.length(); i++) {
                                                Log.i("mainquant", shopping.getJSONObject(i).getString("Quantity"));
                                                if (shoppingCouponID.equalsIgnoreCase(shopping.getJSONObject(i).getString("CouponID"))) {
                                                    Log.i("quant", shopping.getJSONObject(i).getString("Quantity"));
                                                    Log.i("qutd2", String.valueOf(qtyShopping));
                                                    qtyShopping = qtyShopping + Integer.parseInt(shopping.getJSONObject(i).getString("Quantity"));
                                                    Log.i("qutd", String.valueOf(qtyShopping));

                                                }
                                            }
                                            for (int i = 0; i < message.length(); i++) {

                                                if (shoppingCouponID.equalsIgnoreCase(message.getJSONObject(i).getString("CouponID"))) {

                                                    message.getJSONObject(i).put("TotalQuantity", qtyShopping);

                                                }
                                            }
                                            if (messageCategory != null) {
                                                for (int i = 0; i < messageCategory.length(); i++) {

                                                    if (shoppingCouponID.equalsIgnoreCase(messageCategory.getJSONObject(i).getString("CouponID"))) {

                                                        messageCategory.getJSONObject(i).put("TotalQuantity", qtyShopping);

                                                    }
                                                }
                                            }
                                            if (message3 != null) {
                                                for (int i = 0; i < message3.length(); i++) {

                                                    if (shoppingCouponID.equalsIgnoreCase(message3.getJSONObject(i).getString("CouponID"))) {

                                                        message3.getJSONObject(i).put("TotalQuantity", qtyShopping);

                                                    }
                                                }
                                            }
                                            if (x == 3) {

                                            } else {
                                                if (searchLable == true) {
                                                    searchProduct();
                                                } else {
                                                    fetchProduct();
                                                }

                                            }

                                            Log.i("qut", String.valueOf(qtyShopping));

                                            tv_number_item.setText(String.valueOf(shopping.length()));
                                            tv.setText(String.valueOf(shopping.length()));
                                            if (z == 0) {

                                            } else {
                                                shoppingArrayList.clear();
                                                List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                                }.getType());
                                                shoppingArrayList.addAll(items);
                                                shoppingListAdapter.notifyDataSetChanged();
                                            }
                                            progressDialog.dismiss();

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
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShoppingItemSelected(final Shopping shopping) {
        shoppingCouponID = shopping.getCouponID();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Log.i("test", shopping.getDisplayUPC().replace("UPC: ", ""));

        for (int i = 0; i < message.length(); i++) {


            try {
                if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(shopping.getDisplayUPC().replace("UPC: ", ""))) {
                    message.getJSONObject(i).put("ListCount", 1);
                    message.getJSONObject(i).put("ClickCount", 1);
                    message.getJSONObject(i).put("Quantity", "0");
                    //

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (messageCategory != null) {
            for (int i = 0; i < messageCategory.length(); i++) {


                try {
                    if (messageCategory.getJSONObject(i).getString("UPC").equalsIgnoreCase(shopping.getDisplayUPC().replace("UPC: ", ""))) {
                        messageCategory.getJSONObject(i).put("ListCount", 1);
                        messageCategory.getJSONObject(i).put("ClickCount", 1);
                        messageCategory.getJSONObject(i).put("Quantity", "0");
                        //

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        // fetchProduct();
        //shoppingListLoad();

        Log.i("remove", "remove" + shopping.getPrimaryOfferTypeId());
        //https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=404
        if (shopping.getPrimaryOfferTypeId() == 0) {
            removeSingleOwnItem(shopping.getShoppingListItemID());
        } else {
            String url = Constant.WEB_URL + Constant.SHOPPINGLISTSINGAL + shopping.getShoppingListItemID() + "&MemberId=" + appUtil.getPrefrence("MemberId");
            StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("success", String.valueOf(response));
                            fetchShoppingListLoad();
                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //removeSingleOwnItem(shopping.getShoppingListItemID());
                    Log.i("fail", String.valueOf(error));
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onShoppingaddSelected(final Shopping shopping) {
//
        shoppingCouponID = shopping.getCouponID();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        if (shopping.getPrimaryOfferTypeId() == 0) {
            String url = "";


            url = Constant.WEB_URL + "ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=" + shopping.getShoppingListItemID() + "&Quantity=" + (Integer.parseInt(shopping.getQuantity()) + 1);
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("success", String.valueOf(response));
                                shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity()) + 1)));
                                tv_quantity_detail.setText(shopping.getQuantity());

                                //SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeId(),shopping.getCouponID(),1,String.valueOf((Integer.parseInt(shopping.getQuantity())+0)));

                                fetchShoppingListLoad();
                                progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }) {

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
            String currentDate = dateformat2.format(c2.getTime());
            System.out.println(currentDate);
    /*JSONObject ShoppingListItems = new JSONObject();
    try {
        ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
        ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())+1));
        ShoppingListItems.put("DateAddedOn", currentDate);
    } catch (JSONException e) {
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
    Log.i("testobject",mRequestBody);*/

            String url = "";

            url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + shopping.getDisplayUPC().replace("UPC", "").replace(":", "").replace(" ", "") + "&Quantity=" + (Integer.parseInt(shopping.getQuantity()) + 1) + "&DateAddedOn=" + currentDate;
            Log.i("url", url);

            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("quantityUpadte", shopping.getQuantity());
                                Log.i("shopping Res", String.valueOf(response));
                                shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity()) + 1)));
                                Log.i("success", shopping.getDisplayUPC().replace("UPC", "").replace(":", ""));
                                SetProductActivateShopping(shopping.getDisplayUPC().replace("UPC", "").replace(":", "").replace(" ", ""), shopping.getPrimaryOfferTypeId(), shopping.getCouponID(), 1, String.valueOf((Integer.parseInt(shopping.getQuantity()))));

                                fetchShoppingListLoad();
                                //
                                // progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }) {

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
                    mQueue.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onShoppingsubSelected(final Shopping shopping) {
        shoppingCouponID = shopping.getCouponID();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        if (shopping.getPrimaryOfferTypeId() == 0) {

            if (Integer.parseInt(shopping.getQuantity()) > 1) {
                String url = null;
                url = Constant.WEB_URL + "ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=" + shopping.getShoppingListItemID() + "&Quantity=" + (Integer.parseInt(shopping.getQuantity()) - 1);
                try {
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("success", String.valueOf(response));
                                    shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity()) - 1)));
                                    tv_quantity_detail.setText(shopping.getQuantity());

                                    SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeId(), shopping.getCouponID(), 1, String.valueOf((Integer.parseInt(shopping.getQuantity()) - 0)));
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                        }
                    }) {

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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                progressDialog.dismiss();
            }


        } else {

            if (Integer.parseInt(shopping.getQuantity()) > 1) {
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                /*JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
                    ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())-1));
                    ShoppingListItems.put("DateAddedOn", currentDate);
                } catch (JSONException e) {

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
                Log.i("testobject",mRequestBody);*/
                String url = null;

                url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + shopping.getDisplayUPC().replace("UPC", "").replace(":", "").replace(" ", "") + "&Quantity=" + (Integer.parseInt(shopping.getQuantity()) - 1) + "&DateAddedOn=" + currentDate;

                try {
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.i("shopping Res", String.valueOf(response));
                                    shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity()) - 1)));
                                    Log.i("success", shopping.getDisplayUPC().replace("UPC", "").replace(":", ""));
                                    SetProductActivateShopping(shopping.getDisplayUPC().replace("UPC", "").replace(":", "").replace(" ", ""), shopping.getPrimaryOfferTypeId(), shopping.getCouponID(), 1, String.valueOf((Integer.parseInt(shopping.getQuantity()))));
                                    fetchShoppingListLoad();
                                    //progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                        }
                    }) {

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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    public void onShoppingDetailSelected(final Shopping shopping) {


        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Processing");
        progressDialog.show();

        DetaileToolbar.setVisibility(View.VISIBLE);
        DetaileToolbar.setTitle("Detail");
        navigation.setVisibility(View.GONE);
        // scrollView.setVisibility(View.VISIBLE);
        liner_detail_add_item.setVisibility(View.VISIBLE);
        liner_add_sub_button.setVisibility(View.VISIBLE);

        DetaileToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "shopperdetailback");
                navigation.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.VISIBLE);
                DetaileToolbar.setTitle("MyFareway List");

                activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                z = 0;
                if (activatedOffer == null) {
                    shoppingArrayList.clear();
                    //no students
                } else {
                    shoppingArrayList.clear();
                    List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
                    }.getType());
                    shoppingArrayList.addAll(items);
                    shoppingListAdapter.notifyDataSetChanged();
                }

                DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("test", "shopperback");
                        x = 0;
                        z = 0;
                        navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                        navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                        tv.setVisibility(View.VISIBLE);
                        shopping_list_header.setVisibility(View.GONE);
                        navigation.setVisibility(View.VISIBLE);
                        rv_shopping_list_items.setVisibility(View.GONE);
                        DetaileToolbar.setVisibility(View.GONE);
                        rv_items.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    }
                });
                //toolbar.setVisibility(View.VISIBLE);

                qty = 0;

            }
        });

        Log.i("id", String.valueOf(shopping.getPrimaryOfferTypeid()));
        if (shopping.getPrimaryOfferTypeid() == 0 && shopping.getPrimaryOfferTypeId() == 0) {
            Log.i("getPrimaryOfferTypeid", String.valueOf(shopping.getPrimaryOfferTypeid()));
        }
        //

        else if (shopping.getPrimaryOfferTypeId() == 3 || shopping.getPrimaryOfferTypeId() == 2 || shopping.getPrimaryOfferTypeId() == 1) {
            ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);
            if (shopping.getImageURL().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
            } else {
                Picasso.get().load(shopping.getImageURL()).into(imv_item_detaile);
            }
            //
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + "Circular/ItemDetails" + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&StoreId=" + appUtil.getPrefrence("StoreId") + "&UPC=" + shopping.getDisplayUPC().replace("UPC", "").replace(":", "").replace(" ", "") + "&Plateform=2&PrimaryOfferTypeId=" + shopping.getPrimaryOfferTypeId() + "&CouponID=" + shopping.getCouponID() + "&CircularType=0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Shopping Detail Item", response.toString());
                                if (!response.equals("[]")) {
                                    try {
                                        Log.i("Verites response", response.toString());
                                        jsonShoppingParam = new JSONArray(response.toString());
                                        //

                                        for (int i = 0; i < jsonShoppingParam.length(); i++) {
                                            //

                                            scrollView.setVisibility(View.VISIBLE);
                                            jsonShoppingParam.getJSONObject(i).getString("Quantity");
                                            tv_quantity_detail.setText(jsonShoppingParam.getJSONObject(i).getString("Quantity"));

                                            TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
                                            TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
                                            TextView tv_reg_price_detail = (TextView) findViewById(R.id.tv_reg_price_detail);
                                            TextView tv_save_detail = (TextView) findViewById(R.id.tv_save_detail);
                                            TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
                                            TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
                                            TextView tv_valid_detail = (TextView) findViewById(R.id.tv_valid_detail);
                                            TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
                                            TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
                                            TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
                                            TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
                                            TextView tv_promo_price_detail = (TextView) findViewById(R.id.tv_promo_price_detail);
                                            TextView tv_coupon_detail_detail = (TextView) findViewById(R.id.tv_coupon_detail_detail);

                                            Log.i("qut", jsonShoppingParam.getJSONObject(i).getString("Quantity"));

                                            final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
                                            final LinearLayout circular_layout_detaile = (LinearLayout) findViewById(R.id.circular_layout_detaile);
                                            final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
                                            circular_layout_detaile.setVisibility(View.VISIBLE);
                                            if (jsonShoppingParam.getJSONObject(i).getInt("ClickCount") > 0) {
                                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                imv_status_detaile.setVisibility(View.VISIBLE);
                                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                                tv_status_detaile.setText("Activated");

                                            } else if (jsonShoppingParam.getJSONObject(i).getInt("ClickCount") == 0) {
                                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                                imv_status_detaile.setVisibility(View.GONE);
                                                tv_status_detaile.setText("Activate");

                                            }

                                            LinearLayout bottomLayout_detaile = (LinearLayout) findViewById(R.id.bottomLayout_detaile);
                                            TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
                                            TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
                                            TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
                                            TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
                                            TableRow table_promo = (TableRow) findViewById(R.id.table_promo);
                                            TableRow table_promo_view = (TableRow) findViewById(R.id.table_promo_view);
                                            TableRow table_save = (TableRow) findViewById(R.id.table_save);
                                            TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
                                            TableRow table_package = (TableRow) findViewById(R.id.table_package);
                                            TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
                                            TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
                                            TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);
                                            TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
                                            TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);
                                            TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
                                            TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);

                                            String saveDate = jsonShoppingParam.getJSONObject(i).getString("ValidityEndDate");
                                            if (saveDate.length() == 0) {

                                            } else {
                                                SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                                                Date newDate = null;
                                                try {
                                                    newDate = spf.parse(saveDate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                String c = "MM/dd";
                                                spf = new SimpleDateFormat(c);
                                                saveDate = spf.format(newDate);
                                                tv_valid_detail.setText(saveDate);
                                                System.out.println(saveDate);
                                            }

                                            if (shopping.getPrimaryOfferTypeId() == 3) {
                                                tv_coupon_detail_detail.setVisibility(View.GONE);
                                                tv_fareway_flag.setText("With MyFareway");
                                                table_limit.setVisibility(View.GONE);
                                                table_limit_view.setVisibility(View.GONE);
                                                table_package_view.setVisibility(View.GONE);
                                                table_package.setVisibility(View.GONE);
                                                table_regular.setVisibility(View.VISIBLE);
                                                table_regular_view.setVisibility(View.VISIBLE);
                                                table_save.setVisibility(View.VISIBLE);
                                                table_save_view.setVisibility(View.VISIBLE);
                                                table_promo.setVisibility(View.GONE);
                                                table_promo_view.setVisibility(View.GONE);
                                                table_coupon.setVisibility(View.GONE);
                                                table_coupon_view.setVisibility(View.GONE);


                                                if (jsonShoppingParam.getJSONObject(i).getString("PackagingSize").equalsIgnoreCase("")) {
                                                    table_package.setVisibility(View.GONE);
                                                    table_package_view.setVisibility(View.GONE);

                                                } else {
                                                    tv_package_detail.setText(jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));
                                                }
                                                circular_layout_detaile.setVisibility(View.VISIBLE);

                                                bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

                                                String displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice");
                                                if (jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                                    displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";
                                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                                tv_price_detaile.setText(result);

                                                String chars = capitalize(jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                tv_detail_detail.setText(chars + " " + jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));

                                                tv_reg_price_detail.setText("$" + jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                try {
                                                    DecimalFormat dF = new DecimalFormat("0.00");
                                                    Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                    tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                                } catch (Exception e) {

                                                }
                                                tv_upc_detail.setText(jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                tv_deal_type_detaile.setText(jsonShoppingParam.getJSONObject(i).getString("OfferTypeTagName"));
                                                if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                                    if (jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                                        tv_varieties_detail.setVisibility(View.VISIBLE);
                                                        table_varieties_view.setVisibility(View.VISIBLE);
                                                        Spanned varietiesUnderline = Html.fromHtml("<u>" + jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") + " Varieties" + "</u>");
                                                        tv_varieties_detail.setText(varietiesUnderline);
                                                    } else {
                                                        tv_varieties_detail.setVisibility(View.GONE);
                                                        table_varieties_view.setVisibility(View.GONE);
                                                    }
                                                } else if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                                    tv_varieties_detail.setVisibility(View.GONE);
                                                    table_varieties_view.setVisibility(View.GONE);
                                                }


                                            } else if (shopping.getPrimaryOfferTypeId() == 2) {
                                                tv_coupon_detail_detail.setVisibility(View.VISIBLE);
                                                tv_fareway_flag.setText("With Coupon");
                                                circular_layout_detaile.setVisibility(View.VISIBLE);

                                                table_limit.setVisibility(View.VISIBLE);
                                                table_limit_view.setVisibility(View.VISIBLE);

                                                table_regular.setVisibility(View.VISIBLE);
                                                table_regular_view.setVisibility(View.VISIBLE);

                                                table_promo.setVisibility(View.VISIBLE);
                                                table_promo_view.setVisibility(View.VISIBLE);

                                                table_coupon.setVisibility(View.VISIBLE);
                                                table_coupon_view.setVisibility(View.VISIBLE);

                                                table_save.setVisibility(View.VISIBLE);
                                                table_save_view.setVisibility(View.VISIBLE);

                                                table_package.setVisibility(View.GONE);
                                                table_package_view.setVisibility(View.GONE);

                                                table_upc.setVisibility(View.VISIBLE);
                                                table_upc_view.setVisibility(View.VISIBLE);

                                                if (jsonShoppingParam.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0.0000") || jsonShoppingParam.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0")) {
                                                    Log.i("if", "atul");
                                                    table_limit.setVisibility(View.VISIBLE);
                                                    table_limit_view.setVisibility(View.VISIBLE);
                                                    table_regular.setVisibility(View.GONE);
                                                    table_regular_view.setVisibility(View.GONE);
                                                    table_package.setVisibility(View.GONE);
                                                    table_package_view.setVisibility(View.GONE);

                                                    table_promo.setVisibility(View.GONE);
                                                    table_promo_view.setVisibility(View.GONE);
                                                    table_coupon.setVisibility(View.GONE);
                                                    table_coupon_view.setVisibility(View.GONE);
                                                    table_save.setVisibility(View.GONE);
                                                    table_save_view.setVisibility(View.GONE);

                                                    table_upc.setVisibility(View.VISIBLE);
                                                    table_upc_view.setVisibility(View.VISIBLE);

                                                    table_varieties.setVisibility(View.VISIBLE);
                                                    table_varieties_view.setVisibility(View.VISIBLE);

                                                }
                                                if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                                    if (jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                                        tv_varieties_detail.setVisibility(View.VISIBLE);
                                                        table_varieties_view.setVisibility(View.VISIBLE);
                                                        Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                                                        tv_varieties_detail.setText(varietiesUnderline);
                                                    } else {
                                                        tv_varieties_detail.setVisibility(View.GONE);
                                                        table_varieties_view.setVisibility(View.GONE);
                                                    }
                                                } else if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                                    tv_varieties_detail.setVisibility(View.GONE);
                                                    table_varieties_view.setVisibility(View.GONE);
                                                }
                                                try {
                                                    DecimalFormat dF = new DecimalFormat("0.00");
                                                    Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("CouponDiscount"));
                                                    Number num2 = dF.parse(jsonShoppingParam.getJSONObject(i).getString("AdPrice"));
                                                    Number num3 = dF.parse(jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                    tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));
                                                    tv_promo_price_detail.setText("$ " + new DecimalFormat("##.##").format(num2));
                                                    if (new DecimalFormat("##.##").format(num2).equalsIgnoreCase(new DecimalFormat("##.##").format(num3))) {
                                                        table_promo.setVisibility(View.GONE);
                                                        table_promo_view.setVisibility(View.GONE);
                                                    }

                                                } catch (Exception e) {

                                                }
                                                tv_package_detail.setText(jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));


                                                bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));

                                                String displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString();
                                                if (jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                                    displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";

                                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                                Log.i("anshu", String.valueOf(result));
                                                if (jsonShoppingParam.getJSONObject(i).getString("RewardType").equalsIgnoreCase("2") && jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 4) {
                                                    tv_price_detaile.setText("Buy " + jsonShoppingParam.getJSONObject(i).getString("RequiredQty") + "\n" + "Get " + jsonShoppingParam.getJSONObject(i).getString("RewardQty") + " " + result + "*");
                                                } else if (jsonShoppingParam.getJSONObject(i).getString("RewardType").equalsIgnoreCase("3") || jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 4 || jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 1) {
                                                    tv_price_detaile.setText(result);
                                                } else {
                                                    tv_price_detaile.setText(result + "*");
                                                }
                                                String chars = capitalize(jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                tv_detail_detail.setText(chars);

                                                String chars2 = capitalize(jsonShoppingParam.getJSONObject(i).getString("CouponShortDescription"));
                                                tv_coupon_detail_detail.setText("\n" + chars2);

                                                tv_reg_price_detail.setText("$" + jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                try {
                                                    DecimalFormat dF = new DecimalFormat("0.00");
                                                    Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                    tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                                } catch (Exception e) {

                                                }
                                                tv_limit.setText(String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("LimitPerTransection")));
                                                tv_upc_detail.setText(jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                tv_deal_type_detaile.setText(jsonShoppingParam.getJSONObject(i).getString("OfferTypeTagName"));

                                            } else if (shopping.getPrimaryOfferTypeId() == 1) {
                                                tv_coupon_detail_detail.setVisibility(View.GONE);
                                                tv_fareway_flag.setText("");
                                                table_limit.setVisibility(View.GONE);
                                                table_limit_view.setVisibility(View.GONE);
                                                table_package_view.setVisibility(View.GONE);
                                                table_package.setVisibility(View.GONE);
                                                table_regular.setVisibility(View.VISIBLE);
                                                table_regular_view.setVisibility(View.VISIBLE);
                                                table_save.setVisibility(View.VISIBLE);
                                                table_save_view.setVisibility(View.VISIBLE);
                                                table_promo.setVisibility(View.GONE);
                                                table_promo_view.setVisibility(View.GONE);
                                                table_coupon.setVisibility(View.GONE);
                                                table_coupon_view.setVisibility(View.GONE);


                                                if (jsonShoppingParam.getJSONObject(i).getString("PackagingSize").equalsIgnoreCase("")) {
                                                    table_package.setVisibility(View.GONE);
                                                    table_package_view.setVisibility(View.GONE);

                                                } else {
                                                    tv_package_detail.setText(jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));
                                                }
                                                circular_layout_detaile.setVisibility(View.GONE);

                                                bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));

                                                String displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice");
                                                if (jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                                    displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";
                                                Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                                tv_price_detaile.setText(result);

                                                String chars = capitalize(jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                tv_detail_detail.setText(chars + " " + jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));

                                                tv_reg_price_detail.setText("$" + jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                try {
                                                    DecimalFormat dF = new DecimalFormat("0.00");
                                                    Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                    tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                                } catch (Exception e) {

                                                }
                                                tv_upc_detail.setText(jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                tv_deal_type_detaile.setText(jsonShoppingParam.getJSONObject(i).getString("OfferTypeTagName"));
                                                if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                                    if (jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                                        tv_varieties_detail.setVisibility(View.VISIBLE);
                                                        table_varieties_view.setVisibility(View.VISIBLE);
                                                        Spanned varietiesUnderline = Html.fromHtml("<u>" + jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") + " Varieties" + "</u>");
                                                        tv_varieties_detail.setText(varietiesUnderline);
                                                    } else {
                                                        tv_varieties_detail.setVisibility(View.GONE);
                                                        table_varieties_view.setVisibility(View.GONE);
                                                    }
                                                } else if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                                    tv_varieties_detail.setVisibility(View.GONE);
                                                    table_varieties_view.setVisibility(View.GONE);
                                                }

                                            }


                                        }


                                        progressDialog.dismiss();


                                    } catch (Throwable e) {
                                        progressDialog.dismiss();
                                        Log.i("Excep", "error----" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                                progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();

                        if (error.networkResponse == null) {

                            if (error.getClass().equals(TimeoutError.class)) {
                            }
                        }
                        progressDialog.dismiss();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();


                        return params;
                    }

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
                    mQueue2.add(jsonObjectRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            } catch (Exception e) {

                e.printStackTrace();
                progressDialog.dismiss();


            }
        } else if (shopping.getPrimaryOfferTypeid() == 3 || shopping.getPrimaryOfferTypeid() == 2 || shopping.getPrimaryOfferTypeid() == 1) {
            Log.i("CouponID", String.valueOf(shopping.getCouponid()));
            scrollView.setVisibility(View.VISIBLE);
            progressDialog.dismiss();

            int flag = 0;
            for (int i = 0; i < message.length(); i++) {

                try {
                    if (shopping.getCouponid() == message.getJSONObject(i).getInt("CouponID")) {
                        Log.i("test", "if");

                        flag = 1;
                        message.getJSONObject(i).getString("Quantity");
                        ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);

                        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);

                        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
                        TextView tv_reg_price_detail = (TextView) findViewById(R.id.tv_reg_price_detail);
                        TextView tv_save_detail = (TextView) findViewById(R.id.tv_save_detail);
                        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
                        TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
                        TextView tv_valid_detail = (TextView) findViewById(R.id.tv_valid_detail);
                        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
                        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
                        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
                        TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
                        TextView tv_promo_price_detail = (TextView) findViewById(R.id.tv_promo_price_detail);
                        TextView tv_coupon_detail_detail = (TextView) findViewById(R.id.tv_coupon_detail_detail);

                        Log.i("qut", message.getJSONObject(i).getString("Quantity"));
                        tv_quantity_detail.setText(message.getJSONObject(i).getString("Quantity"));


                        final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
                        final LinearLayout circular_layout_detaile = (LinearLayout) findViewById(R.id.circular_layout_detaile);
                        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);

                        circular_layout_detaile.setVisibility(View.VISIBLE);
                        if (message.getJSONObject(i).getInt("ClickCount") > 0) {
                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                            imv_status_detaile.setVisibility(View.VISIBLE);
                            imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                            tv_status_detaile.setText("Activated");

                        } else if (message.getJSONObject(i).getInt("ClickCount") == 0) {
                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                            imv_status_detaile.setVisibility(View.GONE);
                            tv_status_detaile.setText("Activate");

                        }

                        LinearLayout bottomLayout_detaile = (LinearLayout) findViewById(R.id.bottomLayout_detaile);
                        TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
                        TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
                        TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
                        TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
                        TableRow table_promo = (TableRow) findViewById(R.id.table_promo);
                        TableRow table_promo_view = (TableRow) findViewById(R.id.table_promo_view);
                        TableRow table_save = (TableRow) findViewById(R.id.table_save);
                        TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
                        TableRow table_package = (TableRow) findViewById(R.id.table_package);
                        TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
                        TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
                        TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);
                        TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
                        TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);
                        TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
                        TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);
                            /*
                            table_varieties.setVisibility(View.VISIBLE);
                            table_varieties_view.setVisibility(View.VISIBLE);
                            */

                        String saveDate = message.getJSONObject(i).getString("ValidityEndDate");
                        if (saveDate.length() == 0) {

                        } else {
                            SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                            Date newDate = null;
                            try {
                                newDate = spf.parse(saveDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String c = "MM/dd";
                            spf = new SimpleDateFormat(c);
                            saveDate = spf.format(newDate);
                            tv_valid_detail.setText(saveDate);
                            System.out.println(saveDate);
                        }

                        final int primaryId = message.getJSONObject(i).getInt("PrimaryOfferTypeId");
                        final int clickcount = message.getJSONObject(i).getInt("ClickCount");
                        final String PriceAssociationCode = message.getJSONObject(i).getString("PriceAssociationCode");
                        final String UPC = message.getJSONObject(i).getString("UPC");
                        final String StoreId = message.getJSONObject(i).getString("StoreID");
                        final String RelevantUPC = message.getJSONObject(i).getString("RelevantUPC");
                        final int CouponID = message.getJSONObject(i).getInt("CouponID");


                        tv_varieties_detail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search_message.setVisibility(View.GONE);
                                header_title.setVisibility(View.GONE);

                                if (primaryId == 3 || primaryId == 2) {

                                    if (clickcount == 0) {
                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                        all_Varieties_activate.setText("Activate");
                                        imv_status_verities.setVisibility(View.GONE);

                                    } else {
                                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        all_Varieties_activate.setVisibility(View.VISIBLE);
                                        all_Varieties_activate.setText("Activated");
                                        imv_status_verities.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    liner_all_Varieties_activate.setVisibility(View.GONE);
                                    all_Varieties_activate.setVisibility(View.GONE);
                                    imv_status_verities.setVisibility(View.GONE);
                                }


                                Group = "";
                                groupItemsList.clear();
                                rv_items_group.setVisibility(View.VISIBLE);
                                qty = 0;
                                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                                    try {
                                        progressDialog = new ProgressDialog(activity);
                                        progressDialog.setMessage("Processing");
                                        progressDialog.show();
                                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.RELATEDITEMLIST + "?MemberID=" + appUtil.getPrefrence("MemberId"),
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        if (!response.equals("[]")) {
                                                            try {

                                                                Log.i("Verites response", response.toString());
                                                                jsonParam = new JSONArray(response.toString());
                                                                for (int i = 0; i < jsonParam.length(); i++) {
                                                                    jsonParam.getJSONObject(i).getString("Quantity");
                                                                    qty = qty + Integer.parseInt(jsonParam.getJSONObject(i).getString("Quantity"));
                                                                    Log.i("qut", jsonParam.getJSONObject(i).getString("Quantity"));
                                                                }
                                                                Log.i("qut", String.valueOf(qty));

                                                                progressDialog.dismiss();

                                                                fetchVeritesProduct();
                                                                //
                                                                rv_items_group.setVisibility(View.GONE);
                                                                rv_items_verite.setVisibility(View.VISIBLE);
                                                                participateToolbar.setVisibility(View.VISIBLE);
                                                                participateToolbar.setTitle("Participated Item");
                                                                DetaileToolbar.setVisibility(View.GONE);
                                                                shopping_list_header.setVisibility(View.GONE);
                                                                rv_shopping_list_items.setVisibility(View.GONE);
                                                                scrollView.setVisibility(View.GONE);
                                                                rv_items.setVisibility(View.GONE);
                                                                toolbar.setVisibility(View.GONE);
                                                                navigation.setVisibility(View.GONE);
                                                                group_count_text.setVisibility(View.GONE);

                                                                header_title.setVisibility(View.GONE);
                                                                shopper = 1;
                                                                participateToolbar.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        Log.i("testjkjk", "click");
                                                                        if (participate == 0) {
                                                                            search_message.setVisibility(View.VISIBLE);
                                                                        } else if (participate == 1) {
                                                                            search_message.setVisibility(View.GONE);
                                                                        }
                                                                        shopper = 0;
                                                                        DetaileToolbar.setVisibility(View.VISIBLE);
                                                                        DetaileToolbar.setTitle("MyFareway List");
                                                                        shopping_list_header.setVisibility(View.VISIBLE);
                                                                        rv_shopping_list_items.setVisibility(View.VISIBLE);
                                                                        navigation.setVisibility(View.VISIBLE);
                                                                        group_count_text.setVisibility(View.GONE);
                                                                        rv_items_verite.setVisibility(View.GONE);
                                                                        liner_all_Varieties_activate.setVisibility(View.GONE);
                                                                        rv_items_group.setVisibility(View.GONE);
                                                                        participateToolbar.setVisibility(View.GONE);
                                                                        qty = 0;

                                                                        header_title.setVisibility(View.GONE);
                                                                        DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                Log.i("test", "detail");
                                                                                shopper = 0;
                                                                                x = 0;
                                                                                z = 0;
                                                                                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                                                                                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                                                                                tv.setVisibility(View.VISIBLE);
                                                                                shopping_list_header.setVisibility(View.GONE);
                                                                                navigation.setVisibility(View.VISIBLE);
                                                                                rv_shopping_list_items.setVisibility(View.GONE);
                                                                                DetaileToolbar.setVisibility(View.GONE);
                                                                                rv_items.setVisibility(View.VISIBLE);
                                                                                toolbar.setVisibility(View.VISIBLE);
                                                                            }
                                                                        });


                                                                    }
                                                                });


                                                            } catch (Throwable e) {
                                                                progressDialog.dismiss();
                                                                Log.i("Excep", "error----" + e.getMessage());
                                                                e.printStackTrace();

                                                            }
                                                        } else {
                                                            progressDialog.dismiss();
                                                                /*alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.incorrect_credentials),
                                                                        getString(R.string.ok),getString(R.string.alert));*/
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
                                        }) {

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/x-www-form-urlencoded";
                                            }

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();

                                                if (primaryId == 1) {
                                                    params.put("PriceAssociationCode", String.valueOf(PriceAssociationCode));
                                                    params.put("UPC", String.valueOf(UPC));
                                                    params.put("StoreId", String.valueOf(StoreId));
                                                    params.put("Plateform", "2");
                                                    params.put("PrimaryOfferTypeId", String.valueOf(primaryId));
                                                    params.put("RelevantUPC", String.valueOf(RelevantUPC));
                                                } else {
                                                    params.put("PriceAssociationCode", String.valueOf(PriceAssociationCode));
                                                    params.put("UPC", String.valueOf(CouponID));
                                                    params.put("StoreId", String.valueOf(StoreId));
                                                    params.put("Plateform", "2");
                                                    params.put("PrimaryOfferTypeId", String.valueOf(primaryId));
                                                    params.put("RelevantUPC", String.valueOf(RelevantUPC));
                                                }

                                                return params;
                                            }


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

                                            mQueue.add(jsonObjectRequest);
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }

                                    } catch (Exception e) {

                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }

                                } else {
                                    alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                            getString(R.string.ok), getString(R.string.alert));
                                    alertDialog.show();
                                }
                            }
                        });

                        if (shopping.getPrimaryOfferTypeid() == 3) {
                            if (message.getJSONObject(i).getString("LargeImagePath").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                                Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                            } else {
                                Picasso.get().load(message.getJSONObject(i).getString("LargeImagePath")).into(imv_item_detaile);
                            }
                            tv_coupon_detail_detail.setVisibility(View.GONE);
                            tv_fareway_flag.setText("With MyFareway");
                            table_limit.setVisibility(View.GONE);
                            table_limit_view.setVisibility(View.GONE);
                            table_package_view.setVisibility(View.GONE);
                            table_package.setVisibility(View.GONE);
                            table_regular.setVisibility(View.VISIBLE);
                            table_regular_view.setVisibility(View.VISIBLE);
                            table_save.setVisibility(View.VISIBLE);
                            table_save_view.setVisibility(View.VISIBLE);
                            table_promo.setVisibility(View.GONE);
                            table_promo_view.setVisibility(View.GONE);
                            table_coupon.setVisibility(View.GONE);
                            table_coupon_view.setVisibility(View.GONE);


                            if (message.getJSONObject(i).getString("PackagingSize").equalsIgnoreCase("")) {
                                table_package.setVisibility(View.GONE);
                                table_package_view.setVisibility(View.GONE);

                            } else {
                                tv_package_detail.setText(message.getJSONObject(i).getString("PackagingSize"));
                            }
                            circular_layout_detaile.setVisibility(View.VISIBLE);

                            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

                            String displayPrice = message.getJSONObject(i).getString("DisplayPrice");
                            if (message.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                displayPrice = message.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + message.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";
                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            tv_price_detaile.setText(result);

                            String chars = capitalize(message.getJSONObject(i).getString("Description"));
                            tv_detail_detail.setText(chars + " " + message.getJSONObject(i).getString("PackagingSize"));

                            tv_reg_price_detail.setText("$" + message.getJSONObject(i).getString("RegularPrice"));

                            float myFloatSavings = Float.parseFloat(message.getJSONObject(i).getString("Savings") + "f");
                            String formattedString = String.format("%.02f", myFloatSavings);
                            tv_save_detail.setText("$" + formattedString);
                                /*try {
                                    DecimalFormat dF = new DecimalFormat("0.00");
                                    Number num = dF.parse(message.getJSONObject(i).getString("Savings"));
                                    tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                } catch (Exception e) {

                                }*/
                            tv_upc_detail.setText(message.getJSONObject(i).getString("UPC"));
                            tv_deal_type_detaile.setText(message.getJSONObject(i).getString("OfferTypeTagName"));
                            if (message.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                if (message.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                    table_varieties.setVisibility(View.VISIBLE);
                                    tv_varieties_detail.setVisibility(View.VISIBLE);
                                    table_varieties_view.setVisibility(View.VISIBLE);
                                    Spanned varietiesUnderline = Html.fromHtml("<u>" + message.getJSONObject(i).getInt("RelatedItemCount") + " Varieties" + "</u>");
                                    tv_varieties_detail.setText(varietiesUnderline);
                                } else {
                                    table_varieties.setVisibility(View.GONE);
                                    table_varieties_view.setVisibility(View.GONE);
                                }
                            } else if (message.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                table_varieties.setVisibility(View.GONE);
                                table_varieties_view.setVisibility(View.GONE);
                            }


                        } else if (shopping.getPrimaryOfferTypeid() == 2) {

                               /* if (message.getJSONObject(i).getString("LargeImagePath").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                                    Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                }else {
                                    Picasso.get().load(message.getJSONObject(i).getString("LargeImagePath")).into(imv_item_detaile);
                                }*/
                            if (message.getJSONObject(i).getInt("OfferDefinitionId") == 5 || message.getJSONObject(i).getInt("OfferDefinitionId") == 8) {
                                if (message.getJSONObject(i).getString("CouponImageURl").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                                    //Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                } else {
                                    Picasso.get().load(message.getJSONObject(i).getString("CouponImageURl")).into(imv_item_detaile);
                                }
                            } else {
                                if (message.getJSONObject(i).getString("LargeImagePath").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                                    Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                } else if (message.getJSONObject(i).getString("LargeImagePath").equalsIgnoreCase("")) {
                                    Picasso.get().load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                } else {
                                    Picasso.get().load(message.getJSONObject(i).getString("LargeImagePath")).into(imv_item_detaile);
                                }
                            }
                            tv_coupon_detail_detail.setVisibility(View.VISIBLE);
                            tv_fareway_flag.setText("With Coupon");
                            circular_layout_detaile.setVisibility(View.VISIBLE);

                            table_limit.setVisibility(View.VISIBLE);
                            table_limit_view.setVisibility(View.VISIBLE);

                            table_regular.setVisibility(View.VISIBLE);
                            table_regular_view.setVisibility(View.VISIBLE);

                            table_promo.setVisibility(View.VISIBLE);
                            table_promo_view.setVisibility(View.VISIBLE);

                            table_coupon.setVisibility(View.VISIBLE);
                            table_coupon_view.setVisibility(View.VISIBLE);

                            table_save.setVisibility(View.VISIBLE);
                            table_save_view.setVisibility(View.VISIBLE);

                            table_package.setVisibility(View.GONE);
                            table_package_view.setVisibility(View.GONE);

                            table_upc.setVisibility(View.VISIBLE);
                            table_upc_view.setVisibility(View.VISIBLE);

                            if (message.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0.0000") || message.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0")) {
                                Log.i("if", "atul");
                                table_limit.setVisibility(View.VISIBLE);
                                table_limit_view.setVisibility(View.VISIBLE);
                                table_regular.setVisibility(View.GONE);
                                table_regular_view.setVisibility(View.GONE);
                                table_package.setVisibility(View.GONE);
                                table_package_view.setVisibility(View.GONE);

                                table_promo.setVisibility(View.GONE);
                                table_promo_view.setVisibility(View.GONE);
                                table_coupon.setVisibility(View.GONE);
                                table_coupon_view.setVisibility(View.GONE);
                                table_save.setVisibility(View.GONE);
                                table_save_view.setVisibility(View.GONE);

                                table_upc.setVisibility(View.VISIBLE);
                                table_upc_view.setVisibility(View.VISIBLE);
                                table_varieties.setVisibility(View.VISIBLE);
                                table_varieties_view.setVisibility(View.VISIBLE);

                            }
                            if (message.getJSONObject(i).getInt("HasRelatedItems") == 1) {

                                table_varieties.setVisibility(View.VISIBLE);
                                tv_varieties_detail.setVisibility(View.VISIBLE);
                                table_varieties_view.setVisibility(View.VISIBLE);
                                Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                                tv_varieties_detail.setText(varietiesUnderline);
                                table_upc.setVisibility(View.VISIBLE);
                                table_upc_view.setVisibility(View.VISIBLE);
                                tv_coupon_detail_detail.setVisibility(View.VISIBLE);
                            } else if (message.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                table_varieties.setVisibility(View.GONE);
                                table_varieties_view.setVisibility(View.GONE);
                                table_upc.setVisibility(View.GONE);
                                table_upc_view.setVisibility(View.GONE);
                                tv_coupon_detail_detail.setVisibility(View.GONE);
                            }
                            try {
                                float myFloatCouponDiscount = Float.parseFloat(message.getJSONObject(i).getString("CouponDiscount") + "f");
                                String formattedStringCouponDiscount = String.format("%.02f", myFloatCouponDiscount);
                                tv_coupon_detail.setText("$" + formattedStringCouponDiscount);

                                float myFloatAdPrice = Float.parseFloat(message.getJSONObject(i).getString("AdPrice") + "f");
                                String formattedStringAdPrice = String.format("%.02f", myFloatAdPrice);
                                tv_promo_price_detail.setText("$" + formattedStringAdPrice);

                                float myFloatRegularPrice = Float.parseFloat(message.getJSONObject(i).getString("RegularPrice") + "f");
                                String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);


                                    /*DecimalFormat dF = new DecimalFormat("0.00");
                                    Number num = dF.parse(message.getJSONObject(i).getString("CouponDiscount"));
                                    Number num2 = dF.parse(message.getJSONObject(i).getString("AdPrice"));
                                    Number num3 = dF.parse(message.getJSONObject(i).getString("RegularPrice"));
                                    tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));
                                    tv_promo_price_detail.setText("$ " + new DecimalFormat("##.##").format(num2));*/

                                if (formattedStringAdPrice.equalsIgnoreCase(formattedStringRegularPrice)) {
                                    table_promo.setVisibility(View.GONE);
                                    table_promo_view.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {

                            }
                            tv_package_detail.setText(message.getJSONObject(i).getString("PackagingSize"));


                            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));

                            //


                            String displayPrice = message.getJSONObject(i).getString("DisplayPrice").toString();
                            if (message.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                displayPrice = message.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + message.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";

                            Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                            //
                            Log.i("anshu", String.valueOf(result));
                            tv_price_detaile.setText(result);
                            if (message.getJSONObject(i).getString("RewardType").equalsIgnoreCase("2") && message.getJSONObject(i).getInt("OfferDefinitionId") == 4) {
                                tv_price_detaile.setText("Buy " + message.getJSONObject(i).getString("RequiredQty") + "\n" + "Get " + message.getJSONObject(i).getString("RewardQty") + " " + result + "*");
                            } else if (message.getJSONObject(i).getString("RewardType").equalsIgnoreCase("1") && message.getJSONObject(i).getInt("OfferDefinitionId") == 4) {
                                try {
                                    DecimalFormat dF = new DecimalFormat("0.00");
                                    Number reward_value = dF.parse(message.getJSONObject(i).getString("RewardValue"));
                                    tv_price_detaile.setText("Buy " + message.getJSONObject(i).getString("RequiredQty") + "\n" + "Get " + message.getJSONObject(i).getString("RewardQty") + " $" + new DecimalFormat("##.##").format(reward_value) + " OFF*");

                                } catch (Exception e) {

                                }

                            } else {

                            }
                            String chars = capitalize(message.getJSONObject(i).getString("Description"));

                            if (message.getJSONObject(i).getString("Isbadged").equalsIgnoreCase("True")) {
                                tv_detail_detail.setVisibility(View.VISIBLE);
                                tv_detail_detail.setText(chars + " " + message.getJSONObject(i).getString("PackagingSize"));
                            } else {
                                tv_detail_detail.setVisibility(View.GONE);
                            }

                            String chars2 = capitalize(message.getJSONObject(i).getString("CouponShortDescription"));
                            tv_coupon_detail_detail.setText("\n" + chars2);

                            if (message.getJSONObject(i).getInt("RequiredQty") > 1) {
                                String chars3 = capitalize(message.getJSONObject(i).getString("CouponShortDescription"));
                                tv_coupon_detail_detail.setText("\n" + "*" + chars3);
                            }

                            //tv_reg_price_detail.setText("$"+message.getJSONObject(i).getString("RegularPrice"));

                            float myFloatRegularPrice = Float.parseFloat(message.getJSONObject(i).getString("RegularPrice") + "f");
                            String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);
                            tv_reg_price_detail.setText("$" + formattedStringRegularPrice);

                            float myFloatSavings = Float.parseFloat(message.getJSONObject(i).getString("Savings") + "f");
                            String formattedString = String.format("%.02f", myFloatSavings);
                            tv_save_detail.setText("$" + formattedString);
                                /*try {
                                    DecimalFormat dF = new DecimalFormat("0.00");
                                    Number num = dF.parse(message.getJSONObject(i).getString("Savings"));
                                    tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                } catch (Exception e) {

                                }*/
                            tv_limit.setText(String.valueOf(message.getJSONObject(i).getInt("LimitPerTransection")));
                            tv_upc_detail.setText(message.getJSONObject(i).getString("UPC"));
                            tv_deal_type_detaile.setText(message.getJSONObject(i).getString("OfferTypeTagName"));

                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (flag == 0) {
                Log.i("test", "else");
                try {
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + "Circular/ItemDetails" + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&StoreId=" + appUtil.getPrefrence("StoreId") + "&UPC=" + shopping.getUPC().replace("UPC", "").replace(":", "").replace(" ", "") + "&Plateform=2&PrimaryOfferTypeId=" + shopping.getPrimaryOfferTypeid() + "&CouponID=" + shopping.getCouponid() + "&CircularType=0",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Fareway Reated text", response.toString());
                                    scrollView.setVisibility(View.VISIBLE);
                                    if (!response.equals("[]")) {
                                        try {
                                            Log.i("url", Constant.WEB_URL + "Circular/ItemDetails" + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&StoreId=" + appUtil.getPrefrence("StoreId") + "&UPC=" + shopping.getUPC().replace("UPC", "").replace(":", "").replace(" ", "") + "&Plateform=2&PrimaryOfferTypeId=" + shopping.getPrimaryOfferTypeid() + "&CouponID=" + shopping.getCouponid() + "&CircularType=0");

                                            Log.i("Verites response", response.toString());
                                            jsonShoppingParam = new JSONArray(response.toString());

                                            for (int i = 0; i < jsonShoppingParam.length(); i++) {

                                                jsonShoppingParam.getJSONObject(i).getString("Quantity");
                                                ImageView imv_item_detaile = (ImageView) findViewById(R.id.imv_item_detaile);

                                                TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);

                                                TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
                                                TextView tv_reg_price_detail = (TextView) findViewById(R.id.tv_reg_price_detail);
                                                TextView tv_save_detail = (TextView) findViewById(R.id.tv_save_detail);
                                                TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
                                                TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
                                                TextView tv_valid_detail = (TextView) findViewById(R.id.tv_valid_detail);
                                                TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
                                                TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
                                                TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
                                                TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
                                                TextView tv_promo_price_detail = (TextView) findViewById(R.id.tv_promo_price_detail);
                                                TextView tv_coupon_detail_detail = (TextView) findViewById(R.id.tv_coupon_detail_detail);

                                                Log.i("qut", jsonShoppingParam.getJSONObject(i).getString("Quantity"));
                                                tv_quantity_detail.setText(jsonShoppingParam.getJSONObject(i).getString("Quantity"));


                                                final ImageView imv_status_detaile = (ImageView) findViewById(R.id.imv_status_detaile);
                                                final LinearLayout circular_layout_detaile = (LinearLayout) findViewById(R.id.circular_layout_detaile);
                                                final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);

                                                circular_layout_detaile.setVisibility(View.VISIBLE);
                                                if (jsonShoppingParam.getJSONObject(i).getInt("ClickCount") > 0) {
                                                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                    imv_status_detaile.setVisibility(View.VISIBLE);
                                                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                                    tv_status_detaile.setText("Activated");

                                                } else if (jsonShoppingParam.getJSONObject(i).getInt("ClickCount") == 0) {
                                                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                                    imv_status_detaile.setVisibility(View.GONE);
                                                    tv_status_detaile.setText("Activate");

                                                }

                                                LinearLayout bottomLayout_detaile = (LinearLayout) findViewById(R.id.bottomLayout_detaile);
                                                TableRow table_limit = (TableRow) findViewById(R.id.table_limit);
                                                TableRow table_limit_view = (TableRow) findViewById(R.id.table_limit_view);
                                                TableRow table_regular = (TableRow) findViewById(R.id.table_regular);
                                                TableRow table_regular_view = (TableRow) findViewById(R.id.table_regular_view);
                                                TableRow table_promo = (TableRow) findViewById(R.id.table_promo);
                                                TableRow table_promo_view = (TableRow) findViewById(R.id.table_promo_view);
                                                TableRow table_save = (TableRow) findViewById(R.id.table_save);
                                                TableRow table_save_view = (TableRow) findViewById(R.id.table_save_view);
                                                TableRow table_package = (TableRow) findViewById(R.id.table_package);
                                                TableRow table_package_view = (TableRow) findViewById(R.id.table_package_view);
                                                TableRow table_coupon = (TableRow) findViewById(R.id.table_coupon);
                                                TableRow table_coupon_view = (TableRow) findViewById(R.id.table_coupon_view);
                                                TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
                                                TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);
                                                TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
                                                TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);
                                                table_varieties.setVisibility(View.VISIBLE);
                                                table_varieties_view.setVisibility(View.VISIBLE);

                                                String saveDate = jsonShoppingParam.getJSONObject(i).getString("ValidityEndDate");
                                                if (saveDate.length() == 0) {

                                                } else {
                                                    SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yy");
                                                    Date newDate = null;
                                                    try {
                                                        newDate = spf.parse(saveDate);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String c = "MM/dd";
                                                    spf = new SimpleDateFormat(c);
                                                    saveDate = spf.format(newDate);
                                                    tv_valid_detail.setText(saveDate);
                                                    System.out.println(saveDate);
                                                }

                                                final int primaryId = jsonShoppingParam.getJSONObject(i).getInt("PrimaryOfferTypeId");
                                                final int clickcount = jsonShoppingParam.getJSONObject(i).getInt("ClickCount");
                                                final String PriceAssociationCode = jsonShoppingParam.getJSONObject(i).getString("PriceAssociationCode");
                                                final String UPC = jsonShoppingParam.getJSONObject(i).getString("UPC");
                                                final String StoreId = jsonShoppingParam.getJSONObject(i).getString("StoreID");
                                                final String RelevantUPC = jsonShoppingParam.getJSONObject(i).getString("RelevantUPC");
                                                final int CouponID = jsonShoppingParam.getJSONObject(i).getInt("CouponID");


                                                tv_varieties_detail.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        header_title.setVisibility(View.GONE);

                                                        if (primaryId == 3 || primaryId == 2) {

                                                            if (clickcount == 0) {
                                                                liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                                                liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                                                all_Varieties_activate.setText("Activate");
                                                                imv_status_verities.setVisibility(View.GONE);

                                                            } else {
                                                                liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                                                                liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                                all_Varieties_activate.setVisibility(View.VISIBLE);
                                                                all_Varieties_activate.setText("Activated");
                                                                imv_status_verities.setVisibility(View.VISIBLE);
                                                            }
                                                        } else {
                                                            liner_all_Varieties_activate.setVisibility(View.GONE);
                                                            all_Varieties_activate.setVisibility(View.GONE);
                                                            imv_status_verities.setVisibility(View.GONE);
                                                        }


                                                        Group = "";
                                                        groupItemsList.clear();
                                                        rv_items_group.setVisibility(View.VISIBLE);
                                                        qty = 0;
                                                        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
                                                            try {
                                                                progressDialog = new ProgressDialog(activity);
                                                                progressDialog.setMessage("Processing");
                                                                progressDialog.show();
                                                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.RELATEDITEMLIST + "?MemberID=" + appUtil.getPrefrence("MemberId"),
                                                                        new Response.Listener<String>() {
                                                                            @Override
                                                                            public void onResponse(String response) {

                                                                                if (!response.equals("[]")) {
                                                                                    try {

                                                                                        Log.i("Verites response", response.toString());
                                                                                        jsonParam = new JSONArray(response.toString());
                                                                                        for (int i = 0; i < jsonParam.length(); i++) {
                                                                                            jsonParam.getJSONObject(i).getString("Quantity");
                                                                                            qty = qty + Integer.parseInt(jsonParam.getJSONObject(i).getString("Quantity"));
                                                                                            Log.i("qut", jsonParam.getJSONObject(i).getString("Quantity"));
                                                                                        }
                                                                                        Log.i("qut", String.valueOf(qty));

                                                                                        progressDialog.dismiss();

                                                                                        fetchVeritesProduct();

                                                                                        rv_items_group.setVisibility(View.GONE);
                                                                                        rv_items_verite.setVisibility(View.VISIBLE);
                                                                                        participateToolbar.setVisibility(View.VISIBLE);
                                                                                        participateToolbar.setTitle("Participated Item");
                                                                                        DetaileToolbar.setVisibility(View.GONE);
                                                                                        shopping_list_header.setVisibility(View.GONE);
                                                                                        rv_shopping_list_items.setVisibility(View.GONE);
                                                                                        scrollView.setVisibility(View.GONE);
                                                                                        rv_items.setVisibility(View.GONE);
                                                                                        toolbar.setVisibility(View.GONE);
                                                                                        navigation.setVisibility(View.GONE);
                                                                                        group_count_text.setVisibility(View.GONE);

                                                                                        header_title.setVisibility(View.GONE);
                                                                                        shopper = 1;
                                                                                        participateToolbar.setOnClickListener(new View.OnClickListener() {
                                                                                            @Override
                                                                                            public void onClick(View v) {
                                                                                                Log.i("test", "click");
                                                                                                shopper = 0;
                                                                                                DetaileToolbar.setVisibility(View.VISIBLE);
                                                                                                DetaileToolbar.setTitle("MyFareway List");
                                                                                                shopping_list_header.setVisibility(View.VISIBLE);
                                                                                                rv_shopping_list_items.setVisibility(View.VISIBLE);
                                                                                                navigation.setVisibility(View.VISIBLE);
                                                                                                group_count_text.setVisibility(View.GONE);
                                                                                                rv_items_verite.setVisibility(View.GONE);
                                                                                                liner_all_Varieties_activate.setVisibility(View.GONE);
                                                                                                rv_items_group.setVisibility(View.GONE);
                                                                                                participateToolbar.setVisibility(View.GONE);
                                                                                                qty = 0;

                                                                                                header_title.setVisibility(View.GONE);
                                                                                                DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(View v) {
                                                                                                        Log.i("test", "detail");
                                                                                                        shopper = 0;
                                                                                                        x = 0;
                                                                                                        z = 0;
                                                                                                        navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                                                                                                        navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                                                                                                        tv.setVisibility(View.VISIBLE);
                                                                                                        shopping_list_header.setVisibility(View.GONE);
                                                                                                        navigation.setVisibility(View.VISIBLE);
                                                                                                        rv_shopping_list_items.setVisibility(View.GONE);
                                                                                                        DetaileToolbar.setVisibility(View.GONE);
                                                                                                        rv_items.setVisibility(View.VISIBLE);
                                                                                                        toolbar.setVisibility(View.VISIBLE);
                                                                                                    }
                                                                                                });


                                                                                            }
                                                                                        });


                                                                                    } catch (Throwable e) {
                                                                                        progressDialog.dismiss();
                                                                                        Log.i("Excep", "error----" + e.getMessage());
                                                                                        e.printStackTrace();

                                                                                    }
                                                                                } else {
                                                                                    progressDialog.dismiss();
                                                                                    alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.incorrect_credentials),
                                                                                            getString(R.string.ok), getString(R.string.alert));
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
                                                                }) {

                                                                    @Override
                                                                    public String getBodyContentType() {
                                                                        return "application/x-www-form-urlencoded";
                                                                    }

                                                                    @Override
                                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                                        Map<String, String> params = new HashMap<String, String>();

                                                                        if (primaryId == 1) {
                                                                            params.put("PriceAssociationCode", String.valueOf(PriceAssociationCode));
                                                                            params.put("UPC", String.valueOf(UPC));
                                                                            params.put("StoreId", String.valueOf(StoreId));
                                                                            params.put("Plateform", "2");
                                                                            params.put("PrimaryOfferTypeId", String.valueOf(primaryId));
                                                                            params.put("RelevantUPC", String.valueOf(RelevantUPC));
                                                                        } else {
                                                                            params.put("PriceAssociationCode", String.valueOf(PriceAssociationCode));
                                                                            params.put("UPC", String.valueOf(CouponID));
                                                                            params.put("StoreId", String.valueOf(StoreId));
                                                                            params.put("Plateform", "2");
                                                                            params.put("PrimaryOfferTypeId", String.valueOf(primaryId));
                                                                            params.put("RelevantUPC", String.valueOf(RelevantUPC));
                                                                        }

                                                                        return params;
                                                                    }


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

                                                                    mQueue.add(jsonObjectRequest);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();

                                                                }

                                                            } catch (Exception e) {

                                                                e.printStackTrace();
                                                                progressDialog.dismiss();
                                                            }

                                                        } else {
                                                                    /*alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                                                                            getString(R.string.ok),getString(R.string.alert));*/
                                                            alertDialog.show();
                                                        }
                                                    }
                                                });

                                                if (shopping.getPrimaryOfferTypeid() == 3) {
                                                    if (jsonShoppingParam.getJSONObject(i).getString("LargeImagePath").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                                                        Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                                    } else {
                                                        Picasso.get().load(jsonShoppingParam.getJSONObject(i).getString("LargeImagePath")).into(imv_item_detaile);
                                                    }
                                                    tv_coupon_detail_detail.setVisibility(View.GONE);
                                                    tv_fareway_flag.setText("With MyFareway");
                                                    table_limit.setVisibility(View.GONE);
                                                    table_limit_view.setVisibility(View.GONE);
                                                    table_package_view.setVisibility(View.GONE);
                                                    table_package.setVisibility(View.GONE);
                                                    table_regular.setVisibility(View.VISIBLE);
                                                    table_regular_view.setVisibility(View.VISIBLE);
                                                    table_save.setVisibility(View.VISIBLE);
                                                    table_save_view.setVisibility(View.VISIBLE);
                                                    table_promo.setVisibility(View.GONE);
                                                    table_promo_view.setVisibility(View.GONE);
                                                    table_coupon.setVisibility(View.GONE);
                                                    table_coupon_view.setVisibility(View.GONE);


                                                    if (jsonShoppingParam.getJSONObject(i).getString("PackagingSize").equalsIgnoreCase("")) {
                                                        table_package.setVisibility(View.GONE);
                                                        table_package_view.setVisibility(View.GONE);

                                                    } else {
                                                        tv_package_detail.setText(jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));
                                                    }
                                                    circular_layout_detaile.setVisibility(View.VISIBLE);

                                                    bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

                                                    String displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice");
                                                    if (jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                                        displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";
                                                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                                    tv_price_detaile.setText(result);

                                                    String chars = capitalize(jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                    tv_detail_detail.setText(chars + " " + jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));

                                                    tv_reg_price_detail.setText("$" + jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));

                                                    float myFloatSavings = Float.parseFloat(message.getJSONObject(i).getString("Savings") + "f");
                                                    String formattedString = String.format("%.02f", myFloatSavings);
                                                    tv_save_detail.setText("$" + formattedString);
                                                            /*try {
                                                                DecimalFormat dF = new DecimalFormat("0.00");
                                                                Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                                            } catch (Exception e) {

                                                            }*/
                                                    tv_upc_detail.setText(jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                    tv_deal_type_detaile.setText(jsonShoppingParam.getJSONObject(i).getString("OfferTypeTagName"));
                                                    if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                                        if (jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                                            table_varieties.setVisibility(View.VISIBLE);
                                                            tv_varieties_detail.setVisibility(View.VISIBLE);
                                                            table_varieties_view.setVisibility(View.VISIBLE);
                                                            Spanned varietiesUnderline = Html.fromHtml("<u>" + jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") + " Varieties" + "</u>");
                                                            tv_varieties_detail.setText(varietiesUnderline);
                                                        } else {
                                                            table_varieties.setVisibility(View.GONE);
                                                            table_varieties_view.setVisibility(View.GONE);
                                                        }
                                                    } else if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                                        table_varieties.setVisibility(View.GONE);
                                                        table_varieties_view.setVisibility(View.GONE);
                                                    }


                                                } else if (shopping.getPrimaryOfferTypeid() == 2) {
                                                    if (jsonShoppingParam.getJSONObject(i).getString("LargeImagePath").contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")) {
                                                        Picasso.get().load("https://platform.immdemo.net/web/images/GEnoimage.jpg").into(imv_item_detaile);
                                                    } else {
                                                        Picasso.get().load(jsonShoppingParam.getJSONObject(i).getString("LargeImagePath")).into(imv_item_detaile);
                                                    }
                                                    tv_coupon_detail_detail.setVisibility(View.VISIBLE);
                                                    tv_fareway_flag.setText("With Coupon");
                                                    circular_layout_detaile.setVisibility(View.VISIBLE);

                                                    table_limit.setVisibility(View.VISIBLE);
                                                    table_limit_view.setVisibility(View.VISIBLE);

                                                    table_regular.setVisibility(View.VISIBLE);
                                                    table_regular_view.setVisibility(View.VISIBLE);

                                                    table_promo.setVisibility(View.VISIBLE);
                                                    table_promo_view.setVisibility(View.VISIBLE);

                                                    table_coupon.setVisibility(View.VISIBLE);
                                                    table_coupon_view.setVisibility(View.VISIBLE);

                                                    table_save.setVisibility(View.VISIBLE);
                                                    table_save_view.setVisibility(View.VISIBLE);

                                                    table_package.setVisibility(View.GONE);
                                                    table_package_view.setVisibility(View.GONE);

                                                    table_upc.setVisibility(View.VISIBLE);
                                                    table_upc_view.setVisibility(View.VISIBLE);

                                                    if (jsonShoppingParam.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0.0000") || jsonShoppingParam.getJSONObject(i).getString("AdPrice").equalsIgnoreCase("0")) {
                                                        Log.i("if", "atul");
                                                        table_limit.setVisibility(View.VISIBLE);
                                                        table_limit_view.setVisibility(View.VISIBLE);
                                                        table_regular.setVisibility(View.GONE);
                                                        table_regular_view.setVisibility(View.GONE);
                                                        table_package.setVisibility(View.GONE);
                                                        table_package_view.setVisibility(View.GONE);

                                                        table_promo.setVisibility(View.GONE);
                                                        table_promo_view.setVisibility(View.GONE);
                                                        table_coupon.setVisibility(View.GONE);
                                                        table_coupon_view.setVisibility(View.GONE);
                                                        table_save.setVisibility(View.GONE);
                                                        table_save_view.setVisibility(View.GONE);

                                                        table_upc.setVisibility(View.VISIBLE);
                                                        table_upc_view.setVisibility(View.VISIBLE);
                                                        table_varieties.setVisibility(View.VISIBLE);
                                                        table_varieties_view.setVisibility(View.VISIBLE);

                                                    }
                                                    if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 1) {
                                                        if (jsonShoppingParam.getJSONObject(i).getInt("RelatedItemCount") > 1) {
                                                            table_varieties.setVisibility(View.VISIBLE);
                                                            tv_varieties_detail.setVisibility(View.VISIBLE);
                                                            table_varieties_view.setVisibility(View.VISIBLE);
                                                            Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                                                            tv_varieties_detail.setText(varietiesUnderline);
                                                        } else {
                                                            table_varieties.setVisibility(View.VISIBLE);
                                                            tv_varieties_detail.setVisibility(View.VISIBLE);
                                                            table_varieties_view.setVisibility(View.VISIBLE);
                                                            Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                                                            tv_varieties_detail.setText(varietiesUnderline);
                                                        }
                                                    } else if (jsonShoppingParam.getJSONObject(i).getInt("HasRelatedItems") == 0) {
                                                        table_varieties.setVisibility(View.GONE);
                                                        table_varieties_view.setVisibility(View.GONE);
                                                    }
                                                    try {
                                                        float myFloatCouponDiscount = Float.parseFloat(jsonShoppingParam.getJSONObject(i).getString("CouponDiscount") + "f");
                                                        String formattedStringCouponDiscount = String.format("%.02f", myFloatCouponDiscount);
                                                        tv_coupon_detail.setText("$" + formattedStringCouponDiscount);

                                                        float myFloatAdPrice = Float.parseFloat(jsonShoppingParam.getJSONObject(i).getString("AdPrice") + "f");
                                                        String formattedStringAdPrice = String.format("%.02f", myFloatAdPrice);
                                                        tv_promo_price_detail.setText("$" + formattedStringAdPrice);

                                                        float myFloatRegularPrice = Float.parseFloat(jsonShoppingParam.getJSONObject(i).getString("RegularPrice") + "f");
                                                        String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);



                                                                /*DecimalFormat dF = new DecimalFormat("0.00");
                                                                Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("CouponDiscount"));
                                                                Number num2 = dF.parse(jsonShoppingParam.getJSONObject(i).getString("AdPrice"));
                                                                Number num3 = dF.parse(jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                                tv_coupon_detail.setText("$ " + new DecimalFormat("##.##").format(num));
                                                                tv_promo_price_detail.setText("$ " + new DecimalFormat("##.##").format(num2));*/
                                                        if (formattedStringAdPrice.equalsIgnoreCase(formattedStringRegularPrice)) {
                                                            table_promo.setVisibility(View.GONE);
                                                            table_promo_view.setVisibility(View.GONE);
                                                        }

                                                    } catch (Exception e) {

                                                    }
                                                    tv_package_detail.setText(jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));


                                                    bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));

                                                    String displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString();
                                                    if (jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").toString().split("\\.").length > 1)
                                                        displayPrice = jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[0] + "<sup>" + jsonShoppingParam.getJSONObject(i).getString("DisplayPrice").split("\\.")[1] + "<sup>";

                                                    Spanned result = Html.fromHtml(displayPrice.replace("<sup>", "<sup><small><small>").replace("</sup>", "</small></small></sup>"));
                                                    //
                                                    Log.i("anshu", String.valueOf(result));
                                                    if (jsonShoppingParam.getJSONObject(i).getString("RewardType").equalsIgnoreCase("2") && jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 4) {
                                                        tv_price_detaile.setText("Buy " + jsonShoppingParam.getJSONObject(i).getString("RequiredQty") + "\n" + "Get " + jsonShoppingParam.getJSONObject(i).getString("RewardQty") + " " + result + "*");
                                                    } else if (jsonShoppingParam.getJSONObject(i).getString("RewardType").equalsIgnoreCase("3") || jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 4 || jsonShoppingParam.getJSONObject(i).getInt("OfferDefinitionId") == 1) {
                                                        tv_price_detaile.setText(result);
                                                    } else {
                                                        tv_price_detaile.setText(result + "*");
                                                    }
                                                    String chars = capitalize(jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                    tv_detail_detail.setText(chars);

                                                    String chars2 = capitalize(jsonShoppingParam.getJSONObject(i).getString("CouponShortDescription"));
                                                    tv_coupon_detail_detail.setText("\n" + chars2);

                                                    //tv_reg_price_detail.setText("$"+jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));

                                                    float myFloatRegularPrice = Float.parseFloat(jsonShoppingParam.getJSONObject(i).getString("RegularPrice") + "f");
                                                    String formattedStringRegularPrice = String.format("%.02f", myFloatRegularPrice);
                                                    tv_reg_price_detail.setText("$" + formattedStringRegularPrice);

                                                    float myFloatSavings = Float.parseFloat(jsonShoppingParam.getJSONObject(i).getString("Savings") + "f");
                                                    String formattedString = String.format("%.02f", myFloatSavings);
                                                    tv_save_detail.setText("$" + formattedString);
                                                            /*try {
                                                                DecimalFormat dF = new DecimalFormat("0.00");
                                                                Number num = dF.parse(jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

                                                            } catch (Exception e) {

                                                            }*/
                                                    tv_limit.setText(String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("LimitPerTransection")));
                                                    tv_upc_detail.setText(jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                    tv_deal_type_detaile.setText(jsonShoppingParam.getJSONObject(i).getString("OfferTypeTagName"));

                                                }


                                            }


                                            progressDialog.dismiss();


                                        } catch (Throwable e) {
                                            progressDialog.dismiss();
                                            Log.i("Excep", "error----" + e.getMessage());
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.i("test", "end");
                                    progressDialog.dismiss();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Volley error resp", "error----" + error.getMessage());
                            error.printStackTrace();

                            if (error.networkResponse == null) {

                                if (error.getClass().equals(TimeoutError.class)) {
                                }
                            }
                            progressDialog.dismiss();
                        }
                    }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();


                            return params;
                        }

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
                        mQueue2.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();


                }
            }
            //


        }

        add_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();

                String couponId = "";
                int quantity;
                //
                int flag2 = 0;

                for (int i = 0; i < message.length(); i++) {

                    try {
                        if (shopping.getCouponid() == message.getJSONObject(i).getInt("CouponID")) {
                            Log.i("test", "if");

                            flag2 = 1;
                            try {
                                Log.i("couponiid", String.valueOf(message.getJSONObject(i).getInt("CouponID")));

                                //scrollView.setVisibility(View.VISIBLE);
                                quantity = Integer.parseInt(message.getJSONObject(i).getString("Quantity")) + 1;
                                couponId = String.valueOf(message.getJSONObject(i).getInt("CouponID"));
                                Log.i("quantiyuy", String.valueOf(quantity));

                                message.getJSONObject(i).put("Quantity", quantity);
                                //

                                try {
                                    ShoppingListItems.put("UPC", message.getJSONObject(i).getString("UPC"));
                                    ShoppingListItems.put("Quantity", quantity);
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
                                final String mRequestBody = "'" + studentsObj.toString() + "'";
                                Log.i("test", mRequestBody);

                                String url = null;
                                Log.i("testobject", mRequestBody);
                                final int finalQuantity = quantity;

                                url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + message.getJSONObject(i).getString("UPC") + "&Quantity=" + quantity + "&DateAddedOn=" + currentDate;
                                Log.i("url", url);
                                if (message.getJSONObject(i).getString("Quantity").equalsIgnoreCase("1")) {
                                    try {
                                        final int finalQuantity1 = quantity;
                                        final String finalCouponId = couponId;
                                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Log.i("Fareway text", response.toString());
                                                        fetchShoppingListLoad();
                                                        //fetchActivatedOffer();
                                                        //shoppingListLoad();
                                                        shoppingCouponID = finalCouponId;
                                                        progressDialog.dismiss();
                                                        tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                        Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                        //
                                                        SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));


                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.i("Volley error resp", "error----" + error.getMessage());
                                                error.printStackTrace();

                                                if (error.networkResponse == null) {

                                                    if (error.getClass().equals(TimeoutError.class)) {
                                                    }
                                                }
                                            }
                                        }) {

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/x-www-form-urlencoded";
                                            }

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();
                                                try {
                                                    for (int i = 0; i < message.length(); i++) {
                                                        if (shopping.getCouponid() == message.getJSONObject(i).getInt("CouponID")) {
                                                            params.put("UPCCode", message.getJSONObject(i).getString("UPC"));
                                                            params.put("CategoryID", String.valueOf(message.getJSONObject(i).getInt("CategoryID")));
                                                            params.put("SalePrice", message.getJSONObject(i).getString("FinalPrice"));
                                                            params.put("PrimaryOfferTypeId", String.valueOf(message.getJSONObject(i).getInt("PrimaryOfferTypeId")));
                                                            params.put("OfferDetailId", String.valueOf(message.getJSONObject(i).getInt("OfferDetailId")));
                                                            params.put("PersonalCircularID", String.valueOf(message.getJSONObject(i).getInt("PersonalCircularID")));
                                                            params.put("ExpirationDate", message.getJSONObject(i).getString("ValidityEndDate"));
                                                            params.put("ClientID", "1");
                                                            params.put("PackagingSize", message.getJSONObject(i).getString("PackagingSize"));
                                                            params.put("DisplayPrice", message.getJSONObject(i).getString("DisplayPrice"));
                                                            params.put("PageID", String.valueOf(message.getJSONObject(i).getInt("PageID")));
                                                            params.put("Description", message.getJSONObject(i).getString("Description"));
                                                            params.put("CouponID", String.valueOf(message.getJSONObject(i).getInt("CouponID")));
                                                            params.put("MemberID", String.valueOf(message.getJSONObject(i).getInt("MemberID")));
                                                            params.put("DeviceId", "1");
                                                            params.put("ClickType", "1");
                                                            params.put("iPositionID", message.getJSONObject(i).getString("TileNumber"));
                                                            params.put("OPMOfferID", String.valueOf(message.getJSONObject(i).getInt("PricingMasterID")));
                                                            params.put("AdPrice", message.getJSONObject(i).getString("AdPrice"));
                                                            params.put("RegPrice", message.getJSONObject(i).getString("RegularPrice"));
                                                            params.put("Savings", message.getJSONObject(i).getString("Savings"));
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                return params;
                                            }

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
                                            mQueue.add(jsonObjectRequest);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } catch (Exception e) {

                                        e.printStackTrace();


                                    }
                                } else {
                                    try {
                                        final int finalQuantity1 = quantity;
                                        final String finalCouponId = couponId;
                                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Log.i("success", String.valueOf(response));
                                                        shoppingCouponID = finalCouponId;
                                                        progressDialog.dismiss();
                                                        tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                        Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                        //
                                                        fetchShoppingListLoad();
                                                        SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.i("fail", String.valueOf(error));
                                            }
                                        }) {

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/x-www-form-urlencoded";
                                            }

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
                                            mQueue.add(jsonObjectRequest);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (flag2 == 0) {
                    try {
                        for (int i = 0; i < jsonShoppingParam.length(); i++) {
                            scrollView.setVisibility(View.VISIBLE);
                            quantity = Integer.parseInt(jsonShoppingParam.getJSONObject(i).getString("Quantity")) + 1;
                            couponId = String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("CouponID"));
                            Log.i("quantiyuy", String.valueOf(quantity));

                            jsonShoppingParam.getJSONObject(i).put("Quantity", quantity);
                            //

                            try {
                                ShoppingListItems.put("UPC", jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                ShoppingListItems.put("Quantity", quantity);
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
                            final String mRequestBody = "'" + studentsObj.toString() + "'";
                            Log.i("test", mRequestBody);

                            String url = null;
                            Log.i("testobject", mRequestBody);
                            final int finalQuantity = quantity;

                            url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + jsonShoppingParam.getJSONObject(i).getString("UPC") + "&Quantity=" + quantity + "&DateAddedOn=" + currentDate;

                            if (jsonShoppingParam.getJSONObject(i).getString("Quantity").equalsIgnoreCase("1")) {
                                try {
                                    final int finalQuantity1 = quantity;
                                    final String finalCouponId = couponId;
                                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.i("Fareway text", response.toString());
                                                    fetchShoppingListLoad();
                                                    //fetchActivatedOffer();
                                                    //shoppingListLoad();
                                                    shoppingCouponID = finalCouponId;
                                                    progressDialog.dismiss();
                                                    tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                    Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                    //
                                                    SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("Volley error resp", "error----" + error.getMessage());
                                            error.printStackTrace();

                                            if (error.networkResponse == null) {

                                                if (error.getClass().equals(TimeoutError.class)) {
                                                }
                                            }
                                        }
                                    }) {

                                        @Override
                                        public String getBodyContentType() {
                                            return "application/x-www-form-urlencoded";
                                        }

                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();
                                            try {
                                                for (int i = 0; i < jsonShoppingParam.length(); i++) {
                                                    params.put("UPCCode", jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                                    params.put("CategoryID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("CategoryID")));
                                                    params.put("SalePrice", jsonShoppingParam.getJSONObject(i).getString("FinalPrice"));
                                                    params.put("PrimaryOfferTypeId", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("PrimaryOfferTypeId")));
                                                    params.put("OfferDetailId", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("OfferDetailId")));
                                                    params.put("PersonalCircularID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("PersonalCircularID")));
                                                    params.put("ExpirationDate", jsonShoppingParam.getJSONObject(i).getString("ValidityEndDate"));
                                                    params.put("ClientID", "1");
                                                    params.put("PackagingSize", jsonShoppingParam.getJSONObject(i).getString("PackagingSize"));
                                                    params.put("DisplayPrice", jsonShoppingParam.getJSONObject(i).getString("DisplayPrice"));
                                                    params.put("PageID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("PageID")));
                                                    params.put("Description", jsonShoppingParam.getJSONObject(i).getString("Description"));
                                                    params.put("CouponID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("CouponID")));
                                                    params.put("MemberID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("MemberID")));
                                                    params.put("DeviceId", "1");
                                                    params.put("ClickType", "1");
                                                    params.put("iPositionID", jsonShoppingParam.getJSONObject(i).getString("TileNumber"));
                                                    params.put("OPMOfferID", String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("PricingMasterID")));
                                                    params.put("AdPrice", jsonShoppingParam.getJSONObject(i).getString("AdPrice"));
                                                    params.put("RegPrice", jsonShoppingParam.getJSONObject(i).getString("RegularPrice"));
                                                    params.put("Savings", jsonShoppingParam.getJSONObject(i).getString("Savings"));
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            return params;
                                        }

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
                                        mQueue.add(jsonObjectRequest);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();


                                }
                            } else {
                                try {
                                    final int finalQuantity1 = quantity;
                                    final String finalCouponId = couponId;
                                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.i("success", String.valueOf(response));
                                                    shoppingCouponID = finalCouponId;
                                                    progressDialog.dismiss();
                                                    tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                    Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                    //
                                                    fetchShoppingListLoad();
                                                    SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("fail", String.valueOf(error));
                                        }
                                    }) {

                                        @Override
                                        public String getBodyContentType() {
                                            return "application/x-www-form-urlencoded";
                                        }

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
                                        mQueue.add(jsonObjectRequest);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        //
       /* if (Integer.parseInt(shopping.getQuantity())>1){

        }*/

        add_minus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("ddMMMyyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();

                int quantity;
                String couponId = "";

                int flag3 = 0;
/////////

                /////
                for (int i = 0; i < message.length(); i++) {
                    try {
                        if (shopping.getCouponid() == message.getJSONObject(i).getInt("CouponID")) {
                            Log.i("test", "if");

                            flag3 = 1;

                            quantity = Integer.parseInt(message.getJSONObject(i).getString("Quantity")) - 1;
                            Log.i("quantiyuy", String.valueOf(quantity));

                            message.getJSONObject(i).put("Quantity", quantity);
                            couponId = String.valueOf(message.getJSONObject(i).getInt("CouponID"));
                            //

                            try {
                                ShoppingListItems.put("UPC", message.getJSONObject(i).getString("UPC"));
                                ShoppingListItems.put("Quantity", quantity);
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
                            final String mRequestBody = "'" + studentsObj.toString() + "'";
                            Log.i("test", mRequestBody);

                            String url = null;
                            Log.i("testobject", mRequestBody);
                            if (quantity > 0) {
                                //url = Constant.WEB_URL + Constant.SHOPPINGLIST + appUtil.getPrefrence("MemberId");
                                url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + message.getJSONObject(i).getString("UPC") + "&Quantity=" + quantity + "&DateAddedOn=" + currentDate;

                                final int finalQuantity = quantity;
                                final int finalQuantity1 = quantity;
                                final String finalCouponId = couponId;
                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("success", String.valueOf(response));
                                            /*tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                            fetchShoppingListLoad();
                                            progressDialog.dismiss();*/

                                                shoppingCouponID = finalCouponId;
                                                progressDialog.dismiss();
                                                tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));
                                                fetchShoppingListLoad();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("fail", String.valueOf(error));
                                    }
                                }) {

                                    @Override
                                    public String getBodyContentType() {
                                        return "application/x-www-form-urlencoded";
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
                                    mQueue.add(jsonObjectRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));

                                Log.i("remove", "remove");
                                String url2 = Constant.WEB_URL + Constant.REMOVE + shopping.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url2,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("success", String.valueOf(response));
                                                fetchShoppingListLoad();
                                                //qty= Integer.parseInt(shopping.getTotalQuantity())-1;

                                                tv_quantity_detail.setText("0");

                                                //SetRemoveActivateDetail(shopping.getPrimaryOfferTypeId(),shopping.getCouponID(),shopping.getUPC(),shopping.getRequiresActivation(),1);
                                                progressDialog.dismiss();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("fail", String.valueOf(error));
                                        progressDialog.dismiss();
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//flagsearch
                if (flag3 == 3) {

                    try {
                        for (int i = 0; i < jsonShoppingParam.length(); i++) {
                            scrollView.setVisibility(View.VISIBLE);
                            quantity = Integer.parseInt(jsonShoppingParam.getJSONObject(i).getString("Quantity")) - 1;
                            Log.i("quantiyuy", String.valueOf(quantity));

                            jsonShoppingParam.getJSONObject(i).put("Quantity", quantity);
                            couponId = String.valueOf(jsonShoppingParam.getJSONObject(i).getInt("CouponID"));
                            //

                            try {
                                ShoppingListItems.put("UPC", jsonShoppingParam.getJSONObject(i).getString("UPC"));
                                ShoppingListItems.put("Quantity", quantity);
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
                            final String mRequestBody = "'" + studentsObj.toString() + "'";
                            Log.i("test", mRequestBody);

                            String url = null;
                            Log.i("testobject", mRequestBody);
                            if (quantity > 0) {
                                //url = Constant.WEB_URL + Constant.SHOPPINGLIST + appUtil.getPrefrence("MemberId");
                                url = Constant.WEB_URL + Constant.SHOPPINGLISTUPDATE + "?MemberId=" + appUtil.getPrefrence("MemberId") + "&UPC=" + jsonShoppingParam.getJSONObject(i).getString("UPC") + "&Quantity=" + quantity + "&DateAddedOn=" + currentDate;

                                final int finalQuantity = quantity;
                                final int finalQuantity1 = quantity;
                                final String finalCouponId = couponId;
                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.PUT, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("success", String.valueOf(response));
                                            /*tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                            fetchShoppingListLoad();
                                            progressDialog.dismiss();*/

                                                shoppingCouponID = finalCouponId;
                                                progressDialog.dismiss();
                                                tv_quantity_detail.setText(String.valueOf(finalQuantity));
                                                Log.i("upc", (shopping.getCouponID() + "kjghj"));
                                                SetProductActivateShopping(shopping.getUPC(), shopping.getPrimaryOfferTypeid(), finalCouponId, 1, String.valueOf(finalQuantity1));
                                                fetchShoppingListLoad();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("fail", String.valueOf(error));
                                    }
                                }) {

                                    @Override
                                    public String getBodyContentType() {
                                        return "application/x-www-form-urlencoded";
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
                                    mQueue.add(jsonObjectRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));

                                Log.i("remove", "remove");
                                String url2 = Constant.WEB_URL + Constant.REMOVE + shopping.getUPC() + "&" + "MemberId=" + appUtil.getPrefrence("MemberId");
                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url2,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("success", String.valueOf(response));
                                                fetchShoppingListLoad();
                                                //qty= Integer.parseInt(shopping.getTotalQuantity())-1;

                                                tv_quantity_detail.setText("0");

                                                //SetRemoveActivateDetail(shopping.getPrimaryOfferTypeId(),shopping.getCouponID(),shopping.getUPC(),shopping.getRequiresActivation(),1);
                                                progressDialog.dismiss();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("fail", String.valueOf(error));
                                        progressDialog.dismiss();
                                    }
                                }) {
                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });



       /* Log.i("pri",shopping.getPrimaryOfferTypeId()+"jhjg");
        Log.i("prid",shopping.getPrimaryOfferTypeid()+"jhjg");
        if (shopping.getPrimaryOfferTypeId()==0){
            Log.i("upc1",shopping.getDisplayUPC().replace("UPC","").replace(":","").replace(" ","")+"test");
        }else {
            Log.i("upc",shopping.getDisplayUPC().replace("UPC","").replace(":","").replace(" ",""));
        }*/

    }

    public class ViewRemoveAllDialog {

        public void showDialog(final Activity activity, String msg) {
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
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing");
                    progressDialog.show();
                    String url = Constant.WEB_URL + "ShoopingList/ShoppingListByTYC?shoppinglistid=" + appUtil.getPrefrence("ShoppingListId") + "&MemberId=" + appUtil.getPrefrence("MemberId");
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("ViewRemoveAllDialog", String.valueOf(response));
                                    //shoppingListLoad();
                                    if (messageCategory != null) {
                                        for (int i = 0; i < messageCategory.length(); i++) {


                                            try {
                                                messageCategory.getJSONObject(i).put("ListCount", 0);
                                                messageCategory.getJSONObject(i).put("ClickCount", 0);
                                                messageCategory.getJSONObject(i).put("Quantity", "0");
                                                messageCategory.getJSONObject(i).put("TotalQuantity", "0");
                                                //


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                    if (message != null) {
                                        for (int i = 0; i < message.length(); i++) {


                                            try {
                                                message.getJSONObject(i).put("ListCount", 0);
                                                message.getJSONObject(i).put("ClickCount", 0);
                                                message.getJSONObject(i).put("Quantity", "0");
                                                message.getJSONObject(i).put("TotalQuantity", "0");
                                                //


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                    removeOwnItem();
                                    activatedOffer = null;
                                    shoppingArrayList.clear();
                                    shoppingListAdapter.notifyDataSetChanged();
                                    //
                                    //
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                            if (messageCategory != null) {
                                for (int i = 0; i < messageCategory.length(); i++) {


                                    try {
                                        messageCategory.getJSONObject(i).put("ListCount", 0);
                                        messageCategory.getJSONObject(i).put("ClickCount", 0);
                                        messageCategory.getJSONObject(i).put("Quantity", "0");
                                        messageCategory.getJSONObject(i).put("TotalQuantity", "0");

                                        //


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            if (message != null) {
                                for (int i = 0; i < message.length(); i++) {


                                    try {
                                        message.getJSONObject(i).put("ListCount", 0);
                                        message.getJSONObject(i).put("ClickCount", 0);
                                        message.getJSONObject(i).put("Quantity", "0");
                                        message.getJSONObject(i).put("TotalQuantity", "0");
                                        //


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            // messageLoad();
                            activatedOffer = null;
                            shoppingArrayList.clear();
                            shoppingListAdapter.notifyDataSetChanged();
                            removeOwnItem();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
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
                        mQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
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

    public void withEditText() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Shopping List");

        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                sendEmailShoppingList(input.getText().toString());
            }
        });
        builder.show();
    }

    public class ViewErrorAllDialog {

        public void showDialog(Activity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.error_dialog);
            rv_items.setVisibility(View.GONE);

            TextView text = (TextView) dialog.findViewById(R.id.error_dialog_info);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.error_dialog_ok);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                    submit_btn.setTag(0);
                    edit_txt.getText().clear();
                    rv_items.setVisibility(View.VISIBLE);
                    search_message.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    x = 0;
                    dialog.dismiss();
                }
            });

            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.error_dialog_cancel);
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                    submit_btn.setTag(0);
                    edit_txt.getText().clear();
                    rv_items.setVisibility(View.VISIBLE);
                    search_message.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    x = 0;
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    public void activatedOffersListIdLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + "ShoopingList/ActivatedCoupons?MemberId=" + appUtil.getPrefrence("MemberId") + "&CategoryID=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Shopping ActivateCoupon", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")) {
                                        progressDialog.dismiss();
                                        Log.i("anshuman", "test");

                                        try {
                                            activatedOffer = root2.getJSONArray("WCouponsDetails");
                                        } catch (Exception ex) {
                                            activatedOffer = null;
                                        }
                                        /*activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                                        activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                                        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                                        shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                                        z=0;*/
                                        if (activatedOffer == null) {
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            //no students
                                        } else {
                                            shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();
                                        }
                                        //fetchShoppingListLoad();

                                        /*if (activatedOffer==null ){
                                            Log.i("anshuman","test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();

                                        }else {
                                            Log.i("activatedOffer", String.valueOf(activatedOffer));

                                            for (int i = 0; i < activatedOffer.length(); i++) {
                                            }
                                        }*/
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
                        //progressDialog.dismiss();
                    }
                }) {
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchShopping() {
        // Log.i("shopping", String.valueOf(shopping.length()));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
        z = 1;

        if (shopping == null) {
            //no students
            shoppingArrayList.clear();
        } else {
            shoppingArrayList.clear();
            Log.i("primary", shopping.toString());
            for (int i = 0; i < shopping.length(); i++) {

            }

            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }

    }

    private void filterExpShopping() {
        // Log.i("shopping", String.valueOf(shopping.length()));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
        z = 1;

        if (shopping == null) {
            //no students
            shoppingArrayList.clear();
        } else {
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.clear();
            shoppingArrayList.addAll(items);

            Collections.sort(shoppingArrayList, new Comparator<Shopping>() {

                @Override
                public int compare(Shopping o2, Shopping o1) {
                    if (o1.getExpirationDate() == null || o2.getExpirationDate() == null)
                        return 0;
                    return o1.getExpirationDate().compareTo(o2.getExpirationDate());
                }

            });

            shoppingListAdapter.notifyDataSetChanged();

        }


    }

    private void filterExpActivatedOffer() {
        // Log.i("shopping", String.valueOf(shopping.length()));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
        z = 0;

        if (activatedOffer == null) {
            //no students
            shoppingArrayList.clear();
        } else {
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.clear();
            shoppingArrayList.addAll(items);

            Collections.sort(shoppingArrayList, new Comparator<Shopping>() {

                @Override
                public int compare(Shopping o2, Shopping o1) {
                    if (o1.getExpirationDate() == null || o2.getExpirationDate() == null)
                        return 0;
                    return o1.getExpirationDate().compareTo(o2.getExpirationDate());
                }

            });

            shoppingListAdapter.notifyDataSetChanged();

        }


    }

    private void fetchActivatedOffer() {
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
        z = 0;
        if (activatedOffer == null) {
            shoppingArrayList.clear();
            //no students
        } else {
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }

    }

    private void sendEmailShoppingList(final String emails) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + "ShoopingList/EmailShoppingList",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                progressDialog.dismiss();
                                //  fetchShoppingListLoad();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
                                alertDialog = userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok), "Fail");
                                alertDialog.show();

                            }
                        }
                    }
                }) {

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
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void addShoppingItem(final String itemName) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ADDSHOPPINGITEM,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                progressDialog.dismiss();
                                fetchShoppingListLoad();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Volley error resp", "error----" + error.getMessage());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        if (error.networkResponse == null) {
                            progressDialog.dismiss();
                            if (error.getClass().equals(TimeoutError.class)) {
//                                Toast.makeText(activity, "Time out error", Toast.LENGTH_LONG).show();
                                alertDialog = userAlertDialog.createPositiveAlert("Time out error",
                                        getString(R.string.ok), "Fail");
                                alertDialog.show();

                            }
                        }
                    }
                }) {

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
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();
                progressDialog.dismiss();
//                displayAlert();
            }

        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void removeOwnItem() {
        String url = Constant.WEB_URL + Constant.REMOVESHOPPINGOWMITEM + appUtil.getPrefrence("MemberId");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("removeOwnItemsuccess", String.valueOf(response));
                        fetchShoppingListLoad();
                        //
                        progressDialog.dismiss();
                        //messageLoad();
                        //removeOwnItem();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
                messageLoad();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
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
            mQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dialog.dismiss();

    }

    public void removeSingleOwnItem(String shoppingListItemID) {
        Log.i("remove", "remove");
        //https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=404
        String url = Constant.WEB_URL + "ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=" + shoppingListItemID + "&MemberId=" + appUtil.getPrefrence("MemberId");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("success12", String.valueOf(response));
                        //shoppingListLoad();
                        fetchShoppingListLoad();
                        progressDialog.dismiss();

                        //    count_product_number_detail.setVisibility(View.GONE);
                        // product.setClickCount(1);
                        // tv_status_detaile.setText("Add");
                        // circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                        //  imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                        //remove quantity
                        //  SetRemoveActivateDetail(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //removeSingleOwnItem(shopping.getShoppingListItemID());
                Log.i("fail", String.valueOf(error));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type") + " " + appUtil.getPrefrence("access_token"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void messageShoppingLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                // progressDialog.setMessage("Processing");
                //  progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.PRODUCTLIST + "?memberid=" + appUtil.getPrefrence("MemberId") + "&Plateform=2",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Personal Deal", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {
                                        //progressDialog.dismiss();
                                        message = root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")) {
                                            // moreCouponLoad();
                                            x = 1;
                                        } else {
                                            CircularmoreCouponLoad();
                                            //moreCouponLoad();
                                            // fetchProduct();

                                            //shoppingListLoad();
                                            //shoppingListIdLoad();
                                            //shoppingListLoad();
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
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog = userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok), getString(R.string.alert));
            alertDialog.show();
//            Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void saveLogin() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                // progressDialog.setMessage("Processing");
                //progressDialog.show();

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.LOGINSAVE + "&Information=" + appUtil.getPrefrence("Email") + "|" + appUtil.getPrefrence("Password") + "|" + deviceType + "|Android " + osName + "|" + myVersion + "|" + "" + "|" + "" + "|" + "" + "|" + Latitude + "|" + Longitude + "|6.5",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
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
                                ActivityCompat.requestPermissions(MainFwActivity.this,
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
            Log.i("test", "else");
            //login();
            return true;
        }
    }

    /*@Override
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
                    //login();
                    Toast.makeText(this, "Denied", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }*/

    private void changeStore() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                // progressDialog = new ProgressDialog(activity);
                // progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, Constant.WEB_URL + Constant.CHANGESTORE + "?MemberId=" + appUtil.getPrefrence("MemberId"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway", response.toString());
                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")) {

                                        JSONArray message = root.getJSONArray("message");
                                        for (int i = 0; i < message.length(); i++) {
                                            JSONObject jsonParam = message.getJSONObject(i);
                                            appUtil.setPrefrence("StoreId", jsonParam.getString("StoreID"));
                                            appUtil.setPrefrence("BackupStoreId", jsonParam.getString("StoreID"));
                                            appUtil.setPrefrence("StoreName", jsonParam.getString("StoreAddress") + "," + jsonParam.getString("StoreCity") + ", " + jsonParam.getString("StoreState"));
                                        }

                                    } else if (root.getString("errorcode").equals("200")) {
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
