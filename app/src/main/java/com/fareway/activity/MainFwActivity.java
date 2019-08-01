package com.fareway.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.bumptech.glide.Glide;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFwActivity extends AppCompatActivity
        implements CustomAdapterPersonalPrices.CustomAdapterPersonalPricesListener,
        CustomAdapterParticipateItems.CustomAdapterParticipateItemsListener,CustomGroupAdapter.CustomAdapterGroupItemsListener,
        ShoppingListAdapter.ShoppingListAdapterListener{

    private DrawerLayout drawer;
    private Toolbar toolbar;private Toolbar DetaileToolbar;private Toolbar participateToolbar;
    //private NavigationView navigationView;
    private TextView mTextMessage,tv_uname,tv_filter_by_category,tv_filter_by_offer,tv_type,tv_number_item,add_item;
    private ImageView imv_view_list,imv_all_delete,imv_logo,imv_status_verities;
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
    private RelativeLayout main,search_message;
    private PopupWindow popupWindow;
    public static String comeFrom;
    AppUtilFw appUtil;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    private AlertDialog alertDialog;
    private UserAlertDialog userAlertDialog;
    private SearchView searchView;
    private LinearLayout rowLayout,rowLayout0,rowLayout1,rowLayout2,rowLayout3;
    public static int tmp=0;
    public int OtherCoupon=0;
    public int OtherCouponmulti=0;
    public static JSONArray message;
    public static JSONArray morecouponlist;
    public static JSONArray message3;
    public static JSONArray shoppingId;
    private static RecyclerView rv_category,rv_shopping_list_items;
    private ArrayList<Category> categoryList;

    public static JSONArray shopping;
    public static JSONArray activatedOffer;
    private CustomAdapterFilter customAdapterFilter;
    private ArrayList<Shopping> shoppingArrayList;
    private ShoppingListAdapter shoppingListAdapter;

    public static   int x=0; int y=0; int z=0; int c=0;
    public int a=0; int b=0 ;
    private EditText et_search;
    private RequestQueue mQueue;
    public static RequestQueue mQueue2;
    public static  AppUtilFw appUtil2;
    ImageView submit_btn,imv_micro_recorder,print,email;
    EditText edit_txt;
    ScrollView scrollView;
    TextView group_count_text,header_title;
    String Group="";
    int groupcount=0;
    Product productrelated2;
    private View notificationBadge;
    private LinearLayout shopping_list_header;
    TextView tv,shopping_date;
    TextView all_Varieties_activate,tv_fareway_flag;
    LinearLayout linear_tab_button_detail,liner_item_add_detail,liner_all_Varieties_activate,linear_shopping_list_tab,linear_coupon_tab;
    Button shopping_list_fragment,activated_offer_fragment;
    TextView add_item_flag_detail,add_minus_detail,tv_quantity_detail,add_plus_detail,btn_return_pd,btn_try_another_search;
    RelativeLayout relative_main;



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
        imv_status_verities=findViewById(R.id.imv_status_verities);
        tv_fareway_flag=findViewById(R.id.tv_fareway_flag);


        relative_main=findViewById(R.id.relative_main);
        linear_tab_button_detail=findViewById(R.id.linear_tab_button_detail);
        add_item_flag_detail=findViewById(R.id.add_item_flag_detail);

        liner_item_add_detail=findViewById(R.id.liner_item_add_detail);
        add_minus_detail=findViewById(R.id.add_minus_detail);
        tv_quantity_detail=findViewById(R.id.tv_quantity_detail);
        add_plus_detail=findViewById(R.id.add_plus_detail);

        linear_shopping_list_tab=findViewById(R.id.linear_shopping_list_tab);
        linear_coupon_tab=findViewById(R.id.linear_coupon_tab);
        search_message=findViewById(R.id.search_message);
//        shopping_date=findViewById(R.id.shopping_date);

        shopping_list_fragment=findViewById(R.id.shopping_list_fragment);
        shopping_list_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // shoppingListLoad();
                //fetchShoppingListLoad();
               // rv_shopping_list_items.setVisibility(View.VISIBLE);
               // shopping_date.setText("Price");
                linear_shopping_list_tab.setVisibility(View.VISIBLE);
                linear_coupon_tab.setVisibility(View.GONE);

                if (z==0){
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=1;
                    fetchShopping();
                }else {
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=1;
                }

            }
        });
        activated_offer_fragment=findViewById(R.id.activated_offer_fragment);
        activated_offer_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // activatedOffersListIdLoad();
                //shopping_date.setText("Expiration Date");
                //fetchActivatedOffer();
                linear_coupon_tab.setVisibility(View.VISIBLE);
                linear_shopping_list_tab.setVisibility(View.GONE);
                if (z==1){
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=0;
                    fetchActivatedOffer();
                }else {
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
                    z=0;
                    fetchActivatedOffer();
                }
               // rv_shopping_list_items.setVisibility(View.GONE);
            }
        });
        email=findViewById(R.id.email);
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
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        sendEmailShoppingList(userInput.getText().toString());
                                        //Log.i("text", String.valueOf(userInput.getText()));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        print=findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainFwActivity.this,ShoppingListPrint.class));
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid="+appUtil.getPrefrence("ShopperID")+"&memberid="+appUtil.getPrefrence("MemberId")));
                startActivity(browserIntent);*/

                String urlString = "https://fwstaging.immdemo.net/web/printshoppinglist.aspx?shopperid="+appUtil.getPrefrence("ShopperID")+"&memberid="+appUtil.getPrefrence("MemberId");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
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

        btn_return_pd=findViewById(R.id.btn_return_pd);
        btn_return_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                fetchProduct();
                x=0;
            }
        });
        btn_try_another_search=findViewById(R.id.btn_try_another_search);
        btn_try_another_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_message.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                submit_btn.setImageResource(R.drawable.ic_search_black_24dp);
                submit_btn.setTag(0);
                edit_txt.getText().clear();
                fetchProduct();
                x=0;
            }
        });

        edit_txt = (EditText) findViewById(R.id.search_edit);
        edit_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (edit_txt.getText().toString().isEmpty()){
                        Log.i("if","test");
                    }else {
                        Log.i("if","search1");
                        if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                            submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                            submit_btn.setTag(1);
                            String search=edit_txt.getText().toString();
                            searchLoad(search);
                            x=3;
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edit_txt.getWindowToken(), 0);

                        } else {
                            search_message.setVisibility(View.GONE);
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
                        Log.i("if","search");
                    }else {
                        Log.i("if","search2");
                        if (Integer.parseInt(submit_btn.getTag().toString()) == 0) {
                            submit_btn.setImageResource(R.drawable.ic_clear_black_24dp);
                            submit_btn.setTag(1);
                            String search=edit_txt.getText().toString();
                            searchLoad(search);
                            navigation.setVisibility(View.GONE);
                            x=3;

                        } else {
                            //
                            search_message.setVisibility(View.GONE);
                            navigation.setVisibility(View.VISIBLE);
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
                    navigation.setVisibility(View.GONE);
                }else {
                    imv_micro_recorder.setImageResource(R.drawable.micro_recorder);
                    imv_micro_recorder.setTag(0);
                    edit_txt.getText().clear();
                    search_message.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    fetchProduct();
                    x=0;
                }
            }
        });
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


    private void linkUIElements()

    {
        add_item=findViewById(R.id.add_item);
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
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        addShoppingItem(userInput.getText().toString());
                                        //Log.i("text", String.valueOf(userInput.getText()));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
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
        liner_all_Varieties_activate=findViewById(R.id.liner_all_Varieties_activate);
        all_Varieties_activate = findViewById(R.id.all_Varieties_activate);
        imv_logo=findViewById(R.id.imv_logo);
       // header_title = findViewById(R.id.header_title);
        shopping_list_header = findViewById(R.id.shopping_list_header);
        tv_number_item = findViewById(R.id.tv_number_item);

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

        shoppingArrayList = new ArrayList<>();
        rv_shopping_list_items = (RecyclerView) findViewById(R.id.rv_shopping_list_items);
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingArrayList,this,this,this);
        RecyclerView.LayoutManager mLayoutManagerShoppingList = new LinearLayoutManager(activity);
        rv_shopping_list_items.setLayoutManager(mLayoutManagerShoppingList);
        rv_shopping_list_items.setAdapter(shoppingListAdapter);
        //Drawable dividerDrawableShoppingList = ContextCompat.getDrawable(activity, R.drawable.divider);
        //rv_shopping_list_items.addItemDecoration(new DividerRVDecoration(dividerDrawableShoppingList));

        //swep item
        //enableSwipeToDeleteAndUndo();

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
           // navigation.getMenu().findItem(R.id.moreCoupons).setTitle("More Savings");
            Log.i("navif", String.valueOf(x));
        //    header_title.setText("My Personal Ad");
        }
        else  if(comeFrom.equalsIgnoreCase("moreOffer"))
        {
           // navigation.getMenu().findItem(R.id.moreCoupons).setTitle("Personal Ad");
          //  header_title.setText("More Savings");
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
                        Log.i("test","grid");
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
        customAdapterParticipateItems = new CustomAdapterParticipateItems(this, relatedItemsList,this,this,this,this,this);
       // RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 1);
       // rv_items_verite.setLayoutManager(mLayoutManager4);
        rv_items_verite.setAdapter(customAdapterParticipateItems);

        rv_items = (RecyclerView) findViewById(R.id.rv_items);
        customAdapterPersonalPrices = new CustomAdapterPersonalPrices(this, productList,this,this,this,this,this,this);
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
            RecyclerView.LayoutManager mLayoutManager4 = new GridLayoutManager(activity, 2);
            rv_items_verite.setLayoutManager(mLayoutManager4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
            rv_items.setLayoutManager(mLayoutManager);
            singleView=false;
            Log.i("test","grid2");
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
                    Log.i("ifbottom", String.valueOf(x)+comeFrom);

                } else if(x==1) {
                    i2.putExtra("comeFrom", "mpp");
                    comeFrom="mpp";
                    x=0;
                    tmp=0;
                    navigation.getMenu().findItem(R.id.moreCoupons).setTitle("More Saving");
                   // header_title.setText("My Personal Ad");
                    fetchProduct();
                    Log.i("elsebottom", String.valueOf(x)+comeFrom);
                }
                return true;
            }else*/  if (i == R.id.home_button) {

                finish();
                return true;
            } else if (i == R.id.savings) {
                // fetchProduct();
                Intent i1=new Intent(activity,SavingFw.class);
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
                if (x==1){
                    popupMenu.inflate(R.menu.shopping_filter);
                }else if (x==0){
                    popupMenu.inflate(R.menu.filter);
                }

                popupMenu.show();
                return true;
            }else if(i == R.id.ShoppingList){
                //startActivity(new Intent(MainFwActivity.this,ShoppingFw.class));
                if (x==0) {
                    linear_shopping_list_tab.setVisibility(View.VISIBLE);
                    linear_coupon_tab.setVisibility(View.GONE);

                    tv.setVisibility(View.GONE);
                    x=1;
                    z=0;
                    shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
                    shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
                    activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
                    activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
                    navigation.getMenu().findItem(R.id.ShoppingList).setTitle("Personal Ad");
                    navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.account);
                    Log.i("ifbottom", String.valueOf(x)+comeFrom);

                    rv_shopping_list_items.setVisibility(View.VISIBLE);
                    shopping_list_header.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    DetaileToolbar.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    fetchShopping();
                    imv_all_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("remove","All");
                            ViewRemoveAllDialog alert = new ViewRemoveAllDialog();
                            alert.showDialog(activity, "Do you want to delete all the items from the shopping list?");
                        }
                    });

                    DetaileToolbar.setTitle("MyFareway List");
                    DetaileToolbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x=0;
                            z=0;
                            navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                            navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                            tv.setVisibility(View.VISIBLE);
                            shopping_list_header.setVisibility(View.GONE);
                            navigation.setVisibility(View.VISIBLE);
                            rv_shopping_list_items.setVisibility(View.GONE);
                            DetaileToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            //messageShoppingLoad();
                            //
                        }
                    });

                } else if(x==1) {
                    //messageShoppingLoad();
                    tv.setVisibility(View.VISIBLE);
                    comeFrom="mpp";
                    x=0;

                    tmp=0;
                    shoppingArrayList.clear();

                    navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                    navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);
                    fetchProduct();
                    Log.i("elsebottom", String.valueOf(x)+comeFrom);
                    rv_shopping_list_items.setVisibility(View.GONE);
                    shopping_list_header.setVisibility(View.GONE);
                    navigation.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    rv_items.setVisibility(View.VISIBLE);
                    DetaileToolbar.setVisibility(View.GONE);
                }

            }else if(i == R.id.ShopperID){

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
                DetaileToolbar.setVisibility(View.GONE);
                shopping_list_header.setVisibility(View.GONE);
                rv_shopping_list_items.setVisibility(View.GONE);

                navigation.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                x=0;
                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);

                rv_items.setVisibility(View.INVISIBLE);
                rowLayout.setVisibility(View.GONE);
                rv_category.setVisibility(View.VISIBLE);
                //OtherCoupon=0;
                //OtherCouponmulti=0;

                return true;
            } else if (i2 == R.id.filter_by_all_offer) {
                DetaileToolbar.setVisibility(View.GONE);
                shopping_list_header.setVisibility(View.GONE);
                rv_shopping_list_items.setVisibility(View.GONE);

                navigation.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                x=0;
                navigation.getMenu().findItem(R.id.ShoppingList).setTitle("MyFareway List");
                navigation.getMenu().findItem(R.id.ShoppingList).setIcon(R.drawable.ic_view_list_black_24dp);

                if (x==1){
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
            }else if (i2==R.id.filter_by_recent_added){
                Log.i("filter_by_recent_added","anshuma");
                fetchShopping();
                return true;
            }else if (i2==R.id.filter_by_exp_date){
                Log.i("filter_by_exp_date","anshuma");
                filterExpShopping();
                return true;
            }else if (i2==R.id.by_shopper_id){
                //Log.i("test","anshuma");
                Intent i1=new Intent(activity,ShopperId.class);
                startActivity(i1);
            }else if (i2==R.id.by_purchase_history){
                //Log.i("test","anshuma");
                Intent i1=new Intent(activity,PurchaseHistory.class);
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL+Constant.SEARCH+"MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&StoreId="+appUtil.getPrefrence("StoreId")+"&SearchText="+s,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());
                                //Log.i("serach",Constant.WEB_URL+Constant.SEARCH+"MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&StoreId="+appUtil.getPrefrence("StoreId")+"&SearchText="+s);
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
                                    //Toast.makeText(activity, "neelam", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    ViewErrorAllDialog alert = new ViewErrorAllDialog();
                                    alert.showDialog(activity, "Sorry, your search for \"[ nothing ]\" did not return any result in your personal Ad.Currently we don't have any deals, coupons, sales price items matching your search.\n" +
                                            "Our stores still might carry it and if we do its at the right price.");

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
                                Log.i("Fareway Personal Deal", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")){
                                           // moreCouponLoad();
                                            x=1;
                                        }else {
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
               // progressDialog.dismiss();
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
         try {

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

                        params.put("Device", "5");
                        return params;
                    }

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
              //  Log.i("rrrrrrrrrrr", String.valueOf(morecouponlist));
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
            Log.i("obj", String.valueOf(message3.length()));
            if (message3.length() < 5) {
                search_message.setVisibility(View.VISIBLE);
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

                    strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category (" + category_count + ")\"}," + strCategory;
                    //Log.i("text", "["+String.valueOf(strCategory)+"]");
                    String data = "[" + String.valueOf(strCategory) + "]";
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
                } else {
                    String categorydata = "";
                    for (int i = 0; i < message.length(); i++) {
                        // Log.i("test", String.valueOf(tmp));
                        try {
                            if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                JSONObject finalObject = message.getJSONObject(i);
                                // Log.i("test", String.valueOf(finalObject));
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
                                        strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
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
                    // Log.i("string", String.valueOf(items1));
                    // refreshing recycler view
                    customAdapterPersonalPrices.notifyDataSetChanged();

                    strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category (" + category_count + ")\"}," + strCategory;
                    // Log.i("text", "["+String.valueOf(strCategory)+"]");
                    String data = "[" + String.valueOf(strCategory) + "]";
                    //categoryList.clear();
                    try {
                        JSONArray jsonParam = new JSONArray(data.toString());
                        for (int i = 0; i < jsonParam.length(); i++) {
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
                //////




            }else {
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

                strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category (" + category_count + ")\"}," + strCategory;
                //Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "[" + String.valueOf(strCategory) + "]";
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
            } else {
                String categorydata = "";
                for (int i = 0; i < message.length(); i++) {
                    // Log.i("test", String.valueOf(tmp));
                    try {
                        if (tmp == message.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = message.getJSONObject(i);
                            // Log.i("test", String.valueOf(finalObject));
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
                                    strCategory += (strCategory == "" ? "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}" : "," + "{" + "\"CategoryID\":" + message.getJSONObject(i).getInt("CategoryID") + "," + "\"CategoryName\":\"" + message.getJSONObject(i).getString("CategoryName") + " (" + subcat + ")\"}");
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
                // Log.i("string", String.valueOf(items1));
                // refreshing recycler view
                customAdapterPersonalPrices.notifyDataSetChanged();

                strCategory = "{" + "\"CategoryID\":" + 0 + "," + "\"CategoryName\":" + "\"All Category (" + category_count + ")\"}," + strCategory;
                // Log.i("text", "["+String.valueOf(strCategory)+"]");
                String data = "[" + String.valueOf(strCategory) + "]";
                //categoryList.clear();
                try {
                    JSONArray jsonParam = new JSONArray(data.toString());
                    for (int i = 0; i < jsonParam.length(); i++) {
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
    }

    private void CircularmoreCouponLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog2 = new ProgressDialog(activity);
               // progressDialog2.setMessage("Processing");
                //progressDialog2.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.MORECOUPON+"?MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&CircularType=0",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Digital coupon response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        progressDialog.dismiss();
                                       if(message!=null)
                                       {
                                         String s1=  root.getJSONArray("message").toString();
                                           String s2=  message.toString();
                                           String s3="";
                                           String s4="{\"oCouponShortDescription\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"CouponShortDescription\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"CouponLongDescription\":\"\",\"RewardType\":\"3\",\"RewardQty\":\"0\",\"Groupname\":\"\",\"oGroupname\":\"\",\"oDisplayPrice\":\"<sup>$</sup>2.84\",\"rewardGroupname\":\"\",\"Quantity\":1,\"inCircular\":1,\"RequiresActivation\":\"True\",\"IsMidWeek\":0,\"FreeOffer\":0,\"AltTitleBarImage\":\"\",\"LimitPerTransection\":0,\"TileNumber\":\"2\",\"MemberID\":41761,\"UPCRank\":\"0\",\"HasRelatedItems\":1,\"OriginatorID\":0,\"RelevantUPC\":\"4470002288\",\"IsEmployeeOffer\":false,\"BadgeId\":\"0\",\"RedeemLimit\":0,\"RequiredQty\":1,\"CategoryPriority\":1,\"PercentSavings\":\"28.82\",\"FinalPrice\":\"2.8400\",\"AdPrice\":\"0.0000\",\"CouponDiscount\":\"0.0000\",\"PersonalCircularID\":38477,\"LoyaltyCardNumber\":\"5155567152\",\"PersonalCircularItemId\":1029271,\"SectionNumber\":3,\"StoreID\":\"657\",\"RegularPrice\":\"3.99\",\"DisplayPrice\":\"<sup>$</sup>2.84\",\"Savings\":\"1.1500\",\"DateAdded\":\"7/20/2019 2:44:13 AM\",\"ValidityStartDate\":\"7/9/19\",\"BadgeName\":\"\",\"BadgeFileName\":\"\",\"ValidityEndDate\":\"7/24/19\",\"Description\":\"OSCAR MAYER GRILLED CHICKEN STRIPS\",\"PackagingSize\":\"5.5 OZ\",\"PricingMasterID\":0,\"CategoryID\":11,\"UPC\":\"4470002288\",\"CategoryName\":\"Meat & Seafood\",\"SmallImagePath\":\"https://images.immdemo.net/product/wlarge/00044700022887.png\",\"LargeImagePath\":\"https://images.immdemo.net/product/wlarge/00044700022887.png\",\"Isbadged\":\"False\",\"ListCount\":1,\"SpecialInformation\":\"\",\"TileTemplateID\":3,\"MinAmount\":0.0,\"PriceAssociationCode\":\"\",\"PrimaryOfferTypeName\":\"Personal Deals\",\"OfferTypeTagName\":\"My Personal Deal\",\"OfferDefinition\":\"New Price\",\"CPRPromoTypeName\":\"Individual\",\"RelevancyDetail\":\"Pushed\",\"PrimaryOfferTypeId\":420,\"OfferDetailId\":1,\"OfferDefinitionId\":2,\"CPRPromoTypeId\":1,\"RelevancyTypeD\":5,\"CouponID\":7984,\"RelatedItemCount\":2,\"ClickCount\":1,\"PageID\":1,\"BrandId\":1,\"BrandName\":\"Sally Hansen\",\"DietaryId\":0,\"DietaryName\":\"\",\"RewardValue\":\"2.84\",\"CouponImageURl\":\"http://images.immdemo.net/coupon/wlarge/couponImg.jpg\"}";
                                           s1=s1.substring(s1.indexOf("[")+1, s1.lastIndexOf("]"));
                                           s2=s2.substring(s2.indexOf("[")+1, s2.lastIndexOf("]"));
                                           int a=message.length()/2;

                                           if (a*2==message.length()){
                                               s3="["+s2+","+s1+"]";
                                           }else {
                                               s3="["+s2+","+s4+","+s1+"]";
                                           }

                                           message=null;
                                           JSONArray jsonArray = new JSONArray(s3);
                                           message=jsonArray;
                                           fetchProduct();
                                           shoppingListIdLoad();
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
                        progressDialog2.dismiss();
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
                progressDialog2.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.MORECOUPON+"?MemberId="+appUtil.getPrefrence("MemberId")+"&Plateform=2&CircularType=0",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Digital coupon response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                       // progressDialog.dismiss();
                                        morecouponlist= root.getJSONArray("message");
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
               // progressDialog.dismiss();
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
        if (morecouponlist.length() == 0) {
            //no students
        } else {
            String strCategory="";
            String strCategoryCheck="";
            int Categoryid=0;
            int category_count = 0;
            int subcat=0;
            tmp=2;
            if (tmp==0){
                for (int i = 0; i < morecouponlist.length(); i++) {
                    category_count = category_count + 1;
                    try {
                        if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID")) {
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
            }else {
                String categorydata="";
                for (int i = 0; i < morecouponlist.length(); i++) {
                   // Log.i("test", String.valueOf(tmp));
                    try {
                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                            JSONObject finalObject = morecouponlist.getJSONObject(i);
                            //Log.i("test", String.valueOf(finalObject));
                            categorydata +=(categorydata==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                            category_count = category_count + 1;
                            if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID")) {
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
                linear_tab_button_detail.setVisibility(View.VISIBLE);
                liner_item_add_detail.setVisibility(View.GONE);
            }
        });
        add_item_flag_detail.setText(product.getQuantity());
        linear_tab_button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_tab_button_detail.setVisibility(View.GONE);
                liner_item_add_detail.setVisibility(View.VISIBLE);
                tv_quantity_detail.setText(product.getQuantity());
                add_item_flag_detail.setText(product.getQuantity());
            }
        });
        relative_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_tab_button_detail.setVisibility(View.VISIBLE);
                liner_item_add_detail.setVisibility(View.GONE);
            }
        });

        TextView tv_package_detail = (TextView) findViewById(R.id.tv_package_detail);
        final TextView tv_status_detaile = (TextView) findViewById(R.id.tv_status_detaile);
        TextView tv_price_detaile = (TextView) findViewById(R.id.tv_price_detaile);
        TextView tv_reg_price_detail = (TextView)findViewById(R.id.tv_reg_price_detail);
        TextView tv_save_detail = (TextView)findViewById(R.id.tv_save_detail);
        TextView tv_upc_detail = (TextView) findViewById(R.id.tv_upc_detail);
        TextView tv_limit = (TextView) findViewById(R.id.tv_limit);
        TextView tv_valid_detail = (TextView)findViewById(R.id.tv_valid_detail);
        TextView tv_detail_detail = (TextView) findViewById(R.id.tv_detail_detail);
        TextView tv_deal_type_detaile = (TextView) findViewById(R.id.tv_deal_type_detaile);
        TextView tv_coupon_detail = (TextView) findViewById(R.id.tv_coupon_detail);
        TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);
       // tv_varieties_detail.setText(product.getRelatedItemCount()+" Varieties");
        //detail verite click lisner

        String saveDate = product.getValidityEndDate();
        if (saveDate.length()==0){

        }else {
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
        tv_varieties_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                DetaileToolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                if (product.getPrimaryOfferTypeId()==3 || product.getPrimaryOfferTypeId()==2){

                    if (product.getClickCount()==0){
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        liner_all_Varieties_activate.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                        all_Varieties_activate.setText("Activate");
                        imv_status_verities.setVisibility(View.GONE);

                    } else {
                        liner_all_Varieties_activate.setVisibility(View.VISIBLE);
                        all_Varieties_activate.setVisibility(View.VISIBLE);
                        all_Varieties_activate.setText("Activated");
                        //all_Varieties_activate.setBackgroundColor(Color.GREEN);
                        all_Varieties_activate.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    }
                }

                else {
                    liner_all_Varieties_activate.setVisibility(View.GONE);
                    all_Varieties_activate.setVisibility(View.GONE);
                }

                Group="";
                productrelated2=product;
                groupItemsList.clear();
                toolbar.setVisibility(View.GONE);
                rv_items.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);

                participateToolbar.setVisibility(View.VISIBLE);
               // header_title.setVisibility(View.GONE);
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
                        liner_all_Varieties_activate.setVisibility(View.GONE);
                        if (x==0){
                            rv_items_group.setVisibility(View.GONE);
                            rv_items_verite.setVisibility(View.GONE);
                            participateToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            navigation.setVisibility(View.VISIBLE);
                            group_count_text.setVisibility(View.GONE);
                         //   header_title.setVisibility(View.VISIBLE);
                        }else {
                            rv_items_group.setVisibility(View.GONE);
                            rv_items_verite.setVisibility(View.GONE);
                            participateToolbar.setVisibility(View.GONE);
                            rv_items.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            navigation.setVisibility(View.VISIBLE);
                            group_count_text.setVisibility(View.GONE);
                         //   header_title.setVisibility(View.VISIBLE);
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

                }

                else {
                    alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                            getString(R.string.ok),getString(R.string.alert));
                    alertDialog.show();
                }
            }
        });

       /* final TextView tv_quantity_detail=(TextView)findViewById(R.id.tv_quantity_detail);
        TextView add_minus_detail=(TextView)findViewById(R.id.add_minus_detail);
        TextView add_plus_detail=(TextView)findViewById(R.id.add_plus_detail);*/

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
        TableRow table_upc = (TableRow) findViewById(R.id.table_upc);
        TableRow table_upc_view = (TableRow) findViewById(R.id.table_upc_view);
        TableRow table_varieties = (TableRow) findViewById(R.id.table_varieties);
        TableRow table_varieties_view = (TableRow) findViewById(R.id.table_varieties_view);

        //final LinearLayout count_product_number_detail= (LinearLayout) findViewById(R.id.count_product_number_detail);
        final LinearLayout remove_layout_detail= (LinearLayout) findViewById(R.id.remove_layout_detail);

        if (product.getOfferDefinitionId()==5){
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(activity)
                        .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else {
                Glide.with(activity)
                        .load(product.getCouponImageURl())
                        .into(imv_item_detaile);
            }
        }else {
            if (product.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(activity)
                        .load("https://platform.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else {
                Glide.with(activity)
                        .load(product.getLargeImagePath())
                        .into(imv_item_detaile);
            }
        }


        if (product.getPrimaryOfferTypeId()==3){
          //  tv_quantity_detail.setText(product.getQuantity());
            tv_fareway_flag.setText("With MyFareway");
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

            if (product.getPackagingSize().equalsIgnoreCase("")){
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            }else {
                tv_package_detail.setText(product.getPackagingSize());
            }
            circular_layout_detaile.setVisibility(View.VISIBLE);
            if (product.getClickCount()>0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
                remove_layout_detail.setVisibility(View.GONE);
            }else if (product.getClickCount()==0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
                remove_layout_detail.setVisibility(View.GONE);
            }


          /*  if (product.getRequiresActivation().contains("False")){
                if (product.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                    remove_layout_detail.setVisibility(View.VISIBLE);
                }else if (product.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add");
                    remove_layout_detail.setVisibility(View.GONE);
                }
            }else {
                if (product.getClickCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Activate");
                    remove_layout_detail.setVisibility(View.GONE);
                }else {

                if (product.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                    remove_layout_detail.setVisibility(View.VISIBLE);
                }else if (product.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add");
                    remove_layout_detail.setVisibility(View.GONE);
                }
            }
            }*/

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

            String displayPrice=product.getDisplayPrice().toString();
            if(product.getDisplayPrice().toString().split("\\.").length>1)
                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
            tv_price_detaile.setText(result);

            //Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            //tv_price_detaile.setText(result);

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars+" "+product.getPackagingSize());

            tv_reg_price_detail.setText("$"+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(product.getUPC());
            tv_deal_type_detaile.setText(product.getOfferTypeTagName());
            if (product.getHasRelatedItems()==1){
                if (product.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                }
            }else if (product.getHasRelatedItems()==0){
                tv_varieties_detail.setVisibility(View.INVISIBLE);
            }


        }

        else if(product.getPrimaryOfferTypeId()==2){
            tv_fareway_flag.setText("With Coupon");
            Log.i("ClickCount==", String.valueOf(product.getClickCount()));
            circular_layout_detaile.setVisibility(View.VISIBLE);
            if (product.getClickCount()>0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
                remove_layout_detail.setVisibility(View.GONE);
            }else if (product.getClickCount()==0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
                remove_layout_detail.setVisibility(View.GONE);
            }

            table_limit.setVisibility(View.VISIBLE);
            table_limit_view.setVisibility(View.VISIBLE);
            table_regular.setVisibility(View.VISIBLE);
            table_regular_view.setVisibility(View.VISIBLE);
            table_save.setVisibility(View.VISIBLE);
            table_save_view.setVisibility(View.VISIBLE);
            table_package.setVisibility(View.GONE);
            table_package_view.setVisibility(View.GONE);
            table_upc.setVisibility(View.VISIBLE);
            table_upc_view.setVisibility(View.VISIBLE);
            table_varieties.setVisibility(View.VISIBLE);
            table_varieties_view.setVisibility(View.VISIBLE);
            Log.i("reward",product.getRewardType());
            Log.i("Limit", String.valueOf(product.getLimitPerTransection()));
            if (product.getRewardType().equalsIgnoreCase("3")){
                Log.i("ifreward",product.getRewardType());
                table_coupon.setVisibility(View.VISIBLE);
                table_coupon_view.setVisibility(View.VISIBLE);
                //tv_package_detail.setText(product.getRelatedItemCount()+" varieties");
            }else {
                table_coupon.setVisibility(View.GONE);
                table_coupon_view.setVisibility(View.GONE);
                Log.i("elsereward",product.getRewardType());
            }
            if (product.getAdPrice().equalsIgnoreCase("0.0000")){
                Log.i("if","atul");
                table_limit.setVisibility(View.VISIBLE);
                table_limit_view.setVisibility(View.VISIBLE);
                table_regular.setVisibility(View.GONE);
                table_regular_view.setVisibility(View.GONE);
                table_save.setVisibility(View.GONE);
                table_save_view.setVisibility(View.GONE);
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);
                table_save.setVisibility(View.GONE);
                table_save_view.setVisibility(View.GONE);
                table_upc.setVisibility(View.VISIBLE);
                table_upc_view.setVisibility(View.VISIBLE);
                table_varieties.setVisibility(View.VISIBLE);
                table_varieties_view.setVisibility(View.VISIBLE);

            }
            tv_varieties_detail.setText(product.getRelatedItemCount()+" varieties");

            if (product.getHasRelatedItems()==1){
                if (product.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>Participating Items</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                }
            }else if (product.getHasRelatedItems()==0){
                tv_varieties_detail.setVisibility(View.GONE);
                table_varieties_view.setVisibility(View.GONE);
            }
            try {

                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getCouponDiscount());
                tv_coupon_detail.setText("$" + new DecimalFormat("##.##").format(num));


            } catch (Exception e) {

            }
            tv_package_detail.setText(product.getPackagingSize());


            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));

            String displayPrice=product.getDisplayPrice().toString();
            if(product.getDisplayPrice().toString().split("\\.").length>1)
                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";

            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
            Log.i("anshu", String.valueOf(result));
            if (product.getRewardType().equalsIgnoreCase("2") && product.getOfferDefinitionId()==4){
                tv_price_detaile.setText("Buy "+product.getRequiredQty()+"\n"+"Get "+product.getRewardQty()+" "+result+"*");
            }else if (product.getRewardType().equalsIgnoreCase("3") || product.getOfferDefinitionId()==4 || product.getOfferDefinitionId()==1){
                tv_price_detaile.setText(result);
            }else {
                tv_price_detaile.setText(result+"*");
                table_upc.setVisibility(View.GONE);
                table_upc_view.setVisibility(View.GONE);
            }


           /* Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);*/

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars+" "+product.getPackagingSize());

            tv_reg_price_detail.setText("$"+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }
            tv_limit.setText(String.valueOf(product.getLimitPerTransection()));
            tv_upc_detail.setText(product.getUPC());
          /*  if (product.getPackagingSize().equalsIgnoreCase("")){
                table_package.setVisibility(View.GONE);
                table_package_view.setVisibility(View.GONE);

            }else {
                tv_package_detail.setText(product.getPackagingSize());
            } */
            //tv_valid_detail.setText(product.getValidityEndDate());

            tv_deal_type_detaile.setText(product.getOfferTypeTagName());

        }

        else if(product.getPrimaryOfferTypeId()==1){
            tv_fareway_flag.setText(" ");
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
            tv_package_detail.setText(product.getPackagingSize());
            circular_layout_detaile.setVisibility(View.INVISIBLE);
            remove_layout_detail.setVisibility(View.GONE);

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.blue));

            // old display price
            String displayPrice=product.getDisplayPrice().toString();
            if(product.getDisplayPrice().toString().split("\\.").length>1)
                displayPrice= product.getDisplayPrice().split("\\.")[0]+"<sup>"+ product.getDisplayPrice().split("\\.")[1]+"<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));
            tv_price_detaile.setText(result);

            /*Spanned result = Html.fromHtml(product.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
            tv_price_detaile.setText(result);*/

            String chars = capitalize(product.getDescription());
            tv_detail_detail.setText(chars+" "+product.getPackagingSize());

            tv_reg_price_detail.setText("$"+product.getRegularPrice());
            try {
                DecimalFormat dF = new DecimalFormat("0.00");
                Number num = dF.parse(product.getSavings());
                tv_save_detail.setText("$" + new DecimalFormat("##.##").format(num));

            } catch (Exception e) {

            }

            tv_upc_detail.setText(product.getUPC());

            tv_deal_type_detaile.setText(product.getOfferTypeTagName());


                if (product.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>"+product.getRelatedItemCount()+" Varieties"+"</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                    table_varieties_view.setVisibility(View.GONE);
                }

        }

        remove_layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("remove","remove");
                String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                fetchShoppingListLoad();
                                remove_layout_detail.setVisibility(View.GONE);
                            //    count_product_number_detail.setVisibility(View.GONE);
                                product.setClickCount(1);
                                tv_status_detaile.setText("Activate");
                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                                //remove quantity
                                SetRemoveActivateDetail(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        params.put("Content-Type", "application/json ;charset=utf-8");
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
            }
        });

        add_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", product.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity())+1));
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
                final String mRequestBody = "'"+studentsObj.toString()+"'";
                Log.i("test",mRequestBody);
                //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                String url = null;
                Log.i("testobject",mRequestBody);
                if (product.getQuantity().equalsIgnoreCase("0")&& product.getPrimaryOfferTypeId()==1){
                    RequestQueue mQueue2;
                    mQueue2=FarewayApplication.getmInstance(activity).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        add_item_flag_detail.setText(product.getQuantity());
                                        fetchShoppingListLoad();
                                        //circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        //imv_status_detaile.setVisibility(View.VISIBLE);
                                        //imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        //tv_status_detaile.setText("Activated");
                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())+0)));
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
                        })
                        {

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
                                if (product.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
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
                            mQueue2.add(jsonObjectRequest);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                }else if (product.getQuantity().equalsIgnoreCase("0")){
                    RequestQueue mQueue2;
                    mQueue2=FarewayApplication.getmInstance(activity).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                        tv_quantity_detail.setText(product.getQuantity());
                                        add_item_flag_detail.setText(product.getQuantity());
                                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        imv_status_detaile.setVisibility(View.VISIBLE);
                                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        tv_status_detaile.setText("Activated");
                                        fetchShoppingListLoad();
                                        SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())+0)));
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
                        })
                        {

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
                                if (product.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
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
                            mQueue2.add(jsonObjectRequest);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                }
                else {
                    url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                }
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                tv_quantity_detail.setText(product.getQuantity());
                                add_item_flag_detail.setText(product.getQuantity());

                                //
                                SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())+0)));
                                fetchShoppingListLoad();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){

                    @Override
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
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
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
            }
        });

        add_minus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(product.getQuantity())>1){
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                    String currentDate = dateformat2.format(c2.getTime());
                    System.out.println(currentDate);
                    JSONObject ShoppingListItems = new JSONObject();
                    try {
                        ShoppingListItems.put("UPC", product.getUPC());
                        ShoppingListItems.put("Quantity", (Integer.parseInt(product.getQuantity())-1));
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
                    final String mRequestBody = "'"+studentsObj.toString()+"'";
                    Log.i("test",mRequestBody);
                    String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("success", String.valueOf(response));
                                    product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));
                                    tv_quantity_detail.setText(product.getQuantity());
                                    add_item_flag_detail.setText(product.getQuantity());
                                    fetchShoppingListLoad();
                                    SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())-0)));

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                        }
                    }){

                        @Override
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
                        }
                        //this is the part, that adds the header to the request
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
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
                }else {
                    //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())-1)));

                    Log.i("remove","remove");
                    String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("success", String.valueOf(response));
                                    fetchShoppingListLoad();
                                    remove_layout_detail.setVisibility(View.GONE);
                                    tv_quantity_detail.setText("0");
                                    //    count_product_number_detail.setVisibility(View.GONE);
                                    //product.setClickCount(1);
                                    //tv_status_detaile.setText("Add");
                                    //circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                    //imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                                    //remove quantity
                                    SetRemoveActivateDetail(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                            params.put("Content-Type", "application/json ;charset=utf-8");
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
                }
            }
        });

        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (product.getListCount()>0){
                    }else if (product.getListCount()==0) {

                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Fareway response Main", response.toString());
                                            fetchShoppingListLoad();
                                            if (product.getPrimaryOfferTypeId()==2){
                                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                imv_status_detaile.setVisibility(View.VISIBLE);
                                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                                tv_status_detaile.setText("Activated");
                                                SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                                //   count_product_number_detail.setVisibility(View.VISIBLE);
                                                remove_layout_detail.setVisibility(View.GONE);
                                                //product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                                //    tv_quantity_detail.setText(product.getQuantity());
                                            }else {
                                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                                tv_status_detaile.setText("Added");
                                                SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                                //   count_product_number_detail.setVisibility(View.VISIBLE);
                                                remove_layout_detail.setVisibility(View.VISIBLE);
                                                product.setQuantity(String.valueOf((Integer.parseInt(product.getQuantity())+1)));
                                                //    tv_quantity_detail.setText(product.getQuantity());
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
                }
                else {

                }
            }
        });


        // Toast.makeText(getApplicationContext(), "Selected: " + product.getRegularPrice() + ", " + product.getOfferTypeID(), Toast.LENGTH_LONG).show();
    }
//////////////////////////////////////////////////////////////////////////
    @Override
    public void onProductVeritiesSelected(final Product product) {
        search_message.setVisibility(View.GONE);
        if (product.getPrimaryOfferTypeId()==3 || product.getPrimaryOfferTypeId()==2){

            if (product.getClickCount()==0){
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
        }else {
            liner_all_Varieties_activate.setVisibility(View.GONE);
            //all_Varieties_activate.setVisibility(View.GONE);
        }

        Group="";
        productrelated2=product;
        groupItemsList.clear();
        toolbar.setVisibility(View.GONE);
        rv_items.setVisibility(View.GONE);
        navigation.setVisibility(View.GONE);

        participateToolbar.setVisibility(View.VISIBLE);
      //  header_title.setVisibility(View.GONE);
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
                search_message.setVisibility(View.VISIBLE);
                fetchShoppingListLoad();
                liner_all_Varieties_activate.setVisibility(View.GONE);
                if (x==0){
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                  //  header_title.setVisibility(View.VISIBLE);
                }else {
                    rv_items_group.setVisibility(View.GONE);
                    rv_items_verite.setVisibility(View.GONE);
                    participateToolbar.setVisibility(View.GONE);
                    rv_items.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    group_count_text.setVisibility(View.GONE);
                  //  header_title.setVisibility(View.VISIBLE);
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
                                Log.i("anshuman:",response);

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
                                    alertDialog=userAlertDialog.createPositiveAlert("Participating Items (0)Activated",
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
    public void onProductActivate(final Product product) {

        if (product.getClickCount()>0){


           // RequestQueue mQueue;
            //mQueue=FarewayApplication.getmInstance(mContext).getmRequestQueue();
            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                fetchShoppingListLoad();
                                Log.i("Activate api Response", response.toString());
                                if (product.getPrimaryOfferTypeId()==3){
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                }else if (product.getPrimaryOfferTypeId()==2){
                                    product.setClickCount(1);
                                    product.setQuantity("1");
                                    if (product.getRequiresActivation().contains("False")){
                                        product.setListCount(1);
                                    }else {
                                        product.setListCount(1);
                                    }
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                }else if (product.getPrimaryOfferTypeId()==1){
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
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
                })
                {
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
                        params.put("ClickType", "2");
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


            }
        }else if (product.getClickCount()==0){

            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway clickcount", response.toString());
                                fetchShoppingListLoad();
                                if (product.getPrimaryOfferTypeId()==3){
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");

                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                }else if (product.getPrimaryOfferTypeId()==2){
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                }else if (product.getPrimaryOfferTypeId()==1){

                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
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
                })
                {

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
                        if (product.getPrimaryOfferTypeId()==2){
                            params.put("ClickType", "1");
                        }else {
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


            }

        }
    }

    @Override
    public void onProductMultiActivate(final Product product) {

        if (product.getClickCount()>0){

            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway text", response.toString());
                                fetchShoppingListLoad();
                                if (product.getPrimaryOfferTypeId()==3){
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                }else if (product.getPrimaryOfferTypeId()==2){
                                    product.setClickCount(1);
                                    product.setQuantity("1");
                                    if (product.getRequiresActivation().contains("False")){
                                        product.setListCount(1);
                                    }else {
                                        product.setListCount(1);
                                    }
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                }else if (product.getPrimaryOfferTypeId()==1){
                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
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
                })
                {
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


            }
        }
        else if (product.getClickCount()==0){


            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway text", response.toString());
                                fetchShoppingListLoad();
                                if (product.getPrimaryOfferTypeId()==3){
                                    product.setClickCount(1);
                                    product.setListCount(1);
                                    product.setQuantity("1");

                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
                                }else if (product.getPrimaryOfferTypeId()==2){
                                    product.setClickCount(1);
                                    product.setListCount(1);

                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),2);
                                }else if (product.getPrimaryOfferTypeId()==1){

                                    product.setListCount(1);
                                    product.setQuantity("1");
                                    SetProductActivate(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1);
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
                })
                {

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
                        if (product.getPrimaryOfferTypeId()==2){
                            params.put("ClickType", "1");
                        }else {
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


            }

        }
    }

    @Override
    public void onProductRemove(final Product product) {
        Log.i("remove","remove");
        String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
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
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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
    }

    @Override
    public void onProductMultiRemove(final Product product) {
        Log.i("remove","remove");
        String url = Constant.WEB_URL+Constant.REMOVE+product.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
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
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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
    }

    @Override
    public void onGroupItemSelected(final Group groupItem) {
        Log.i("Select Group ==",groupItem.getGroupname());
        fetchVeritesProduct2(groupItem.getGroupname());
    }

    public static String getDate(int s){
         //MainFwActivity activate = new MainFwActivity();
        //activate.OtherCouponmulti=0;
        //activate.OtherCoupon=0;
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
                    for (int i = 0; i < morecouponlist.length(); i++) {
                        try {
                            if (s==morecouponlist.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = morecouponlist.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < morecouponlist.length(); q++) {
                                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=morecouponlist.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (s==0){
                                JSONObject finalObject = morecouponlist.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < morecouponlist.length(); q++) {
                                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=morecouponlist.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
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
                    for (int i = 0; i < morecouponlist.length(); i++) {
                        try {
                            if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==morecouponlist.getJSONObject(i).getInt("CategoryID")) {
                                JSONObject finalObject = morecouponlist.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < morecouponlist.length(); q++) {
                                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=morecouponlist.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
                                }
                            }else if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId") && s==0){
                                JSONObject finalObject = morecouponlist.getJSONObject(i);
                                test1 +=(test1==""? String.valueOf(finalObject):","+String.valueOf(finalObject));
                                category_count = category_count + 1;
                                if(Categoryid !=morecouponlist.getJSONObject(i).getInt("CategoryID"))
                                {    subcat=0;
                                    for (int q = 0; q < morecouponlist.length(); q++) {
                                        if (tmp == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                            if (morecouponlist.getJSONObject(q).getInt("CategoryID") == morecouponlist.getJSONObject(i).getInt("CategoryID") && morecouponlist.getJSONObject(q).getInt("PrimaryOfferTypeId") == morecouponlist.getJSONObject(i).getInt("PrimaryOfferTypeId")) {
                                                subcat = subcat + 1;
                                            }
                                        }
                                    }
                                    Categoryid=morecouponlist.getJSONObject(i).getInt("CategoryID");
                                    strCategory +=(strCategory==""? "{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}":","+"{" +"\"CategoryID\":"+morecouponlist.getJSONObject(i).getInt("CategoryID")+","+"\"CategoryName\":\""+morecouponlist.getJSONObject(i).getString("CategoryName")+" ("+subcat+")\"}");
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
      //  addBadgeView();


        Log.i("AnshuPrimaryoffertypeid", String.valueOf(PrimaryOfferTypeID));
        Log.i("couponid", String.valueOf(CouponID));
        Log.i("UPC",UPC);
       if (x==0){
           try {
               for (int i = 0; i < message.length(); i++) {

                   if(PrimaryOfferTypeID == 1)
                   {
                       Log.i("apiupc",message.getJSONObject(i).getString("UPC"));
                       if (message.getJSONObject(i).getString("UPC").equalsIgnoreCase(UPC)) {
                           Log.i("upcprimaery",UPC);
                           message.getJSONObject(i).put("ListCount", 1);
                           message.getJSONObject(i).put("ClickCount", 1);
                       }
                   }
                   else
                   {
                       if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                           if (RequireActivation=="True" && PrimaryOfferTypeID==2 && ActivateType==2)
                           {
                               //message.getJSONObject(i).put("ListCount", 1);
                               message.getJSONObject(i).put("ClickCount", 1);
                           }
                           else {
                               message.getJSONObject(i).put("ListCount", 1);
                               message.getJSONObject(i).put("ClickCount", 1);
                               Log.i("SinghPrimaryoffertypeid", String.valueOf(PrimaryOfferTypeID));
                               Log.i("couponid", String.valueOf(CouponID));
                               Log.i("UPC",UPC);
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
               for (int i = 0; i < morecouponlist.length(); i++) {

                   if(PrimaryOfferTypeID == 1)
                   {
                       Log.i("apiupc",morecouponlist.getJSONObject(i).getString("UPC"));
                       if (morecouponlist.getJSONObject(i).getString("UPC") == UPC) {
                           Log.i("upc",UPC);
                           morecouponlist.getJSONObject(i).put("ListCount", 1);
                           morecouponlist.getJSONObject(i).put("ClickCount", 1);
                       }
                   }
                   else
                   {
                       if (morecouponlist.getJSONObject(i).getInt("CouponID") == CouponID) {
                           if (RequireActivation=="True" && PrimaryOfferTypeID==2 && ActivateType==2)
                           {
                               morecouponlist.getJSONObject(i).put("ClickCount", 1);
                           }
                           else {
                               morecouponlist.getJSONObject(i).put("ListCount", 1);
                               morecouponlist.getJSONObject(i).put("ClickCount", 1);
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

    public void  SetProductActivateShopping(int PrimaryOfferTypeID,String UPC,int ActivateType,String quantity)
    {


        Log.i("primary ", String.valueOf(PrimaryOfferTypeID));
        Log.i("morecoupan ", String.valueOf(x));

        if (x==1){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    }
                    else
                    {
                        Log.i("ansnns","test1");
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            Log.i("ansnns","test2");
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }else {
                           /* if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", quantity);


                            }*/
                        }

                    }
                }
                fetchProduct();
                fetchShoppingListLoad();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

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

        }

        else if (x==0){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {

                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    }
                    else
                    {
                        Log.i("ansnns","test1");
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            Log.i("ansnns","test2");
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }else {
                           /* if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", quantity);


                            }*/
                        }

                    }
                }
                fetchProduct();
                fetchShoppingListLoad();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 1);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

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

        }

        else if (x==3){
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
                           /* if (message3.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message3.getJSONObject(i).put("ClickCount", 1);
                            }*/
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

    public void  SetProductActivateDetaile(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType,String quantity)
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
                            message.getJSONObject(i).put("Quantity", quantity);

                        }

                    }
                    else
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 1);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }else {
                            if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", quantity);


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
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

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

                for (int i = 0; i < morecouponlist.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                            // Log.i("primaryinner ", String.valueOf(PrimaryOfferTypeID));
                        }

                    }
                    else
                    {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            morecouponlist.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (morecouponlist.getJSONObject(i).getInt("CouponID") == CouponID) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

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

    public void  SetProductRemoveDetaile(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType,String quantity)
    {


        Log.i("primary ", String.valueOf(PrimaryOfferTypeID));
        Log.i("morecoupan ", String.valueOf(x));
        if (x==0){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("ClickCount", 1);

                        }

                    }
                    else
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("Quantity", quantity);

                        }else {
                            if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", quantity);


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
                            jsonParam.getJSONObject(j).put("ListCount", 0);
                            jsonParam.getJSONObject(j).put("ClickCount", 1);
                            jsonParam.getJSONObject(j).put("Quantity", quantity);

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

                for (int i = 0; i < morecouponlist.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 0);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                            // Log.i("primaryinner ", String.valueOf(PrimaryOfferTypeID));
                        }

                    }
                    else
                    {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            morecouponlist.getJSONObject(i).put("ListCount", 0);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (morecouponlist.getJSONObject(i).getInt("CouponID") == CouponID) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

                    }
                }
                fetchMoreCoupon();
                if (jsonParam == null) {
                    Log.i("testtttt", String.valueOf(jsonParam));
                    //no students
                }else {
                    for (int j = 0; j < jsonParam.length(); j++) {
                        if (jsonParam.getJSONObject(j).getString("UPC").contains(UPC)) {
                            jsonParam.getJSONObject(j).put("ListCount", 0);
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
                            message3.getJSONObject(i).put("ListCount", 0);
                            message3.getJSONObject(i).put("ClickCount", 1);
                        }

                    }
                    else
                    {
                        if (message3.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message3.getJSONObject(i).put("ClickCount", 1);
                            message3.getJSONObject(i).put("ListCount", 0);

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
                            jsonParam.getJSONObject(j).put("ListCount", 0);
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

    public void  SetRemoveActivateDetail(int PrimaryOfferTypeID,int CouponID,String UPC,String RequireActivation,int ActivateType)
    {
        Log.i("primary ", String.valueOf(PrimaryOfferTypeID));
        Log.i("morecoupan ", String.valueOf(x));
        if (x==0){
            try {

                for (int i = 0; i < message.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("ClickCount", 1);
                        }
                    }
                    else
                    {
                        if (message.getJSONObject(i).getString("UPC").contains(UPC)) {
                            message.getJSONObject(i).put("ClickCount", 1);
                            message.getJSONObject(i).put("ListCount", 0);
                            message.getJSONObject(i).put("Quantity", "0");

                        }else {
                            if (message.getJSONObject(i).getInt("CouponID") == CouponID) {
                                message.getJSONObject(i).put("ClickCount", 1);
                                message.getJSONObject(i).put("Quantity", "0");
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
                            jsonParam.getJSONObject(j).put("ListCount", 0);
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

                for (int i = 0; i < morecouponlist.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {

                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            //message.getJSONObject(i).put("ClickCount", 1);
                            //onProductSelected(message.getJSONObject(i))
                            // Log.i("primaryinner ", String.valueOf(PrimaryOfferTypeID));
                        }

                    }
                    else
                    {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            morecouponlist.getJSONObject(i).put("ListCount", 1);

                            //jsonParam.getJSONObject(i).put("ListCount", 1);
                        }else {
                            if (morecouponlist.getJSONObject(i).getInt("CouponID") == CouponID) {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);


                            }
                        }

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
                for (int i = 0; i < morecouponlist.length(); i++) {

                    if(PrimaryOfferTypeID ==1)
                    {
                        if (morecouponlist.getJSONObject(i).getString("UPC").contains(UPC)) {
                            morecouponlist.getJSONObject(i).put("ListCount", 1);
                            morecouponlist.getJSONObject(i).put("ClickCount", 1);
                        }
                    }else {
                        if (morecouponlist.getJSONObject(i).getInt("CouponID") == CouponID) {
                            if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==2)
                            {
                                morecouponlist.getJSONObject(i).put("ClickCount", 1);
                            } else {
                                if (RequireActivation.contains("True") && PrimaryOfferTypeID==2 && ActivateType==1)
                                {
                                    morecouponlist.getJSONObject(i).put("ClickCount", 1);
                                    morecouponlist.getJSONObject(i).put("ListCount", 1);
                                } else if (PrimaryOfferTypeID==2 && ActivateType==1) {

                                } else {
                                    morecouponlist.getJSONObject(i).put("ListCount", 1);
                                    morecouponlist.getJSONObject(i).put("ClickCount", 1);
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



        add_item_flag_detail.setText(relatedItem.getQuantity());
        linear_tab_button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_tab_button_detail.setVisibility(View.GONE);
                liner_item_add_detail.setVisibility(View.VISIBLE);
                tv_quantity_detail.setText(relatedItem.getQuantity());
                add_item_flag_detail.setText(relatedItem.getQuantity());
            }
        });
        relative_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_tab_button_detail.setVisibility(View.VISIBLE);
                liner_item_add_detail.setVisibility(View.GONE);
            }
        });

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
                if (Group.length()>0){
                    group_count_text.setVisibility(View.VISIBLE);
                }
                fetchVeritesProduct2(Group);
                linear_tab_button_detail.setVisibility(View.VISIBLE);
                liner_item_add_detail.setVisibility(View.GONE);
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
        TextView tv_varieties_detail = (TextView) findViewById(R.id.tv_varieties_detail);

        /* final TextView tv_quantity_detail=(TextView)findViewById(R.id.tv_quantity_detail);
        TextView add_minus_detail=(TextView)findViewById(R.id.add_minus_detail);
        TextView add_plus_detail=(TextView)findViewById(R.id.add_plus_detail);*/
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

        final LinearLayout remove_layout_detail= (LinearLayout) findViewById(R.id.remove_layout_detail);


        Log.i("image",relatedItem.getLargeImagePath()+"singh");
        if (relatedItem.getOfferDefinitionId()==5){
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(activity)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                Glide.with(activity)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else {
                Glide.with(activity)
                        .load(relatedItem.getCouponImageURl())
                        .into(imv_item_detaile);
            }
        }else {
            if (relatedItem.getLargeImagePath().contains("http://pty.bashas.com/webapiaccessclient/images/noimage-large.png")){
                Glide.with(activity)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else if (relatedItem.getLargeImagePath().equalsIgnoreCase("")){
                Glide.with(activity)
                        .load("https://fwstaging.immdemo.net/web/images/GEnoimage.jpg")
                        .into(imv_item_detaile);
            }else {
                Glide.with(activity)
                        .load(relatedItem.getLargeImagePath())
                        .into(imv_item_detaile);
            }
        }
        String saveDate = relatedItem.getValidityEndDate();
        if (saveDate.length()==0){

        }else {
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


        if (relatedItem.getPrimaryOfferTypeId()==3){
            tv_fareway_flag.setText("With MyFareway");
            circular_layout_detaile.setVisibility(View.VISIBLE);
            remove_layout_detail.setVisibility(View.GONE);
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
            if (relatedItem.getClickCount()==0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
            }else if (relatedItem.getClickCount()>0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
            }

            /*if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                    remove_layout_detail.setVisibility(View.VISIBLE);
                }else if (relatedItem.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add");
                    remove_layout_detail.setVisibility(View.VISIBLE);
                }
            }else {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add");
            }*/

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.mehrune));

            String displayPrice=relatedItem.getDisplayPrice().toString();
            if(relatedItem.getDisplayPrice().toString().split("\\.").length>1)
                displayPrice= relatedItem.getDisplayPrice().split("\\.")[0]+"<sup>"+ relatedItem.getDisplayPrice().split("\\.")[1]+"<sup>";
            Spanned result = Html.fromHtml(displayPrice.replace("<sup>","<sup><small><small>").replace("</sup>","</small></small></sup>"));


           // Spanned result = Html.fromHtml(relatedItem.getDisplayPrice().replace("<sup>","<sup><small>").replace("</sup>","</small></sup>"));
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
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            if (relatedItem.getHasRelatedItems()==1){
                if (relatedItem.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>"+relatedItem.getRelatedItemCount()+" Varieties"+"</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                }
            }else if (relatedItem.getHasRelatedItems()==0){
                tv_varieties_detail.setVisibility(View.INVISIBLE);
            }

        }

        else if(relatedItem.getPrimaryOfferTypeId()==2){
            tv_fareway_flag.setText("With Coupon");
            circular_layout_detaile.setVisibility(View.VISIBLE);
            remove_layout_detail.setVisibility(View.GONE);
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
            if (relatedItem.getClickCount()==0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setVisibility(View.GONE);
                tv_status_detaile.setText("Activate");
            }else if (relatedItem.getClickCount()>0){
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                imv_status_detaile.setVisibility(View.VISIBLE);
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                tv_status_detaile.setText("Activated");
            }
            /*if (relatedItem.getClickCount()>0) {
                if (relatedItem.getListCount()>0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    tv_status_detaile.setText("Added");
                    remove_layout_detail.setVisibility(View.GONE);
                }else if (relatedItem.getListCount()==0){
                    circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                    imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                    tv_status_detaile.setText("Add");
                    remove_layout_detail.setVisibility(View.GONE);
                }
            }else {
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add");
            }*/

            bottomLayout_detaile.setBackgroundColor(getResources().getColor(R.color.green));
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
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());
            if (relatedItem.getHasRelatedItems()==1){
                if (relatedItem.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>"+"Participating Items"+"</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                }
            }else if (relatedItem.getHasRelatedItems()==0){
                tv_varieties_detail.setVisibility(View.INVISIBLE);
            }

        }

        else if(relatedItem.getPrimaryOfferTypeId()==1){
            tv_fareway_flag.setText(" ");
            circular_layout_detaile.setVisibility(View.INVISIBLE);
            remove_layout_detail.setVisibility(View.INVISIBLE);
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
               // count_product_number_detail.setVisibility(View.VISIBLE);
                remove_layout_detail.setVisibility(View.GONE);
            }else if (relatedItem.getListCount()==0){
                Log.i("elselistCount", String.valueOf(relatedItem.getListCount()));
                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                tv_status_detaile.setText("Add");
              //  count_product_number_detail.setVisibility(View.GONE);
                remove_layout_detail.setVisibility(View.GONE);
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
            tv_deal_type_detaile.setText(relatedItem.getOfferTypeTagName());

            if (relatedItem.getHasRelatedItems()==1){
                if (relatedItem.getRelatedItemCount()>1){
                    tv_varieties_detail.setVisibility(View.VISIBLE);
                    Spanned varietiesUnderline = Html.fromHtml("<u>"+relatedItem.getRelatedItemCount()+" Varieties"+"</u>");
                    tv_varieties_detail.setText(varietiesUnderline);
                }else {
                    tv_varieties_detail.setVisibility(View.GONE);
                }
            }else if (relatedItem.getHasRelatedItems()==0){
                tv_varieties_detail.setVisibility(View.INVISIBLE);
            }

        }

        remove_layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("remove","remove");
                String url = Constant.WEB_URL+Constant.REMOVE+relatedItem.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                fetchShoppingListLoad();
                                remove_layout_detail.setVisibility(View.GONE);
                                //    count_product_number_detail.setVisibility(View.GONE);
                                relatedItem.setClickCount(1);
                                tv_status_detaile.setText("Add");
                                circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_red_bg));
                                imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.addwhite));
                                //remove quantity
                                SetRemoveActivateDetail(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                        params.put("Content-Type", "application/json ;charset=utf-8");
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
            }
        });

        circular_layout_detaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {

                    if (relatedItem.getListCount()>0){

                    }else if (relatedItem.getListCount()==0) {

                        try {
                            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constant.WEB_URL + Constant.ACTIVATE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("Fareway response Main", response.toString());
                                            fetchShoppingListLoad();
                                            remove_layout_detail.setVisibility(View.GONE);
                                            circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                            imv_status_detaile.setVisibility(View.VISIBLE);
                                            imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                            tv_status_detaile.setText("Activated");
                                            all_Varieties_activate.setBackgroundColor(getResources().getColor(R.color.dark_green));
                                            all_Varieties_activate.setText("Activated");

                                            if (relatedItem.getPrimaryOfferTypeId()==3 || relatedItem.getPrimaryOfferTypeId()==2){
                                                SetProductActivateDetaile
                                                        (relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,"1");
                                                groupcount=1;
                                                veritiesGroupDetail2(relatedItem.getCouponID());
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {

                }
            }
        });

        add_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", relatedItem.getUPC());
                    ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity())+1));
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
                final String mRequestBody = "'"+studentsObj.toString()+"'";
                Log.i("test",mRequestBody);
                //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                String url = null;
                Log.i("testobject",mRequestBody);
                if (relatedItem.getQuantity().equalsIgnoreCase("0")&& relatedItem.getPrimaryOfferTypeId()==1){
                    RequestQueue mQueue2;
                    mQueue2=FarewayApplication.getmInstance(activity).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        add_item_flag_detail.setText(relatedItem.getQuantity());
                                        //circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        //imv_status_detaile.setVisibility(View.VISIBLE);
                                        //imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        //tv_status_detaile.setText("Activated");
                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
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
                                if (relatedItem.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
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
                            mQueue2.add(jsonObjectRequest);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }

                }else if (relatedItem.getQuantity().equalsIgnoreCase("0")){
                    RequestQueue mQueue2;
                    mQueue2=FarewayApplication.getmInstance(activity).getmRequestQueue();

                    try {

                        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                                new Response.Listener<String>(){
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Fareway text", response.toString());
                                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                        tv_quantity_detail.setText(relatedItem.getQuantity());
                                        add_item_flag_detail.setText(relatedItem.getQuantity());
                                        circular_layout_detaile.setBackground(getResources().getDrawable(R.drawable.circular_mehrune_bg));
                                        imv_status_detaile.setVisibility(View.VISIBLE);
                                        imv_status_detaile.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                                        tv_status_detaile.setText("Activated");
                                        SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
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
                                if (relatedItem.getPrimaryOfferTypeId()==2){
                                    params.put("ClickType", "1");
                                }else {
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
                            mQueue2.add(jsonObjectRequest);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();


                    }
                }
                else {
                    url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                }
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                tv_quantity_detail.setText(relatedItem.getQuantity());
                                add_item_flag_detail.setText(relatedItem.getQuantity());
                                fetchShoppingListLoad();
                                //
                                SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){

                    @Override
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
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
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
            }
        });

    }

    @Override
    public void onRelatedItemSelected2(final RelatedItem relatedItem) {
        JSONObject json = new JSONObject();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
        String currentDate = dateformat2.format(c2.getTime());
        System.out.println(currentDate);
        //RequestQueue mQueue;
        //mQueue= FarewayApplication.getmInstance(mContext).getmRequestQueue();

        JSONObject ShoppingListItems = new JSONObject();
        try {
            ShoppingListItems.put("UPC", relatedItem.getUPC());
            ShoppingListItems.put("Quantity", (Integer.parseInt(relatedItem.getQuantity())+1));
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

        final String mRequestBody = "'"+studentsObj.toString()+"'";
        String url = null;
        Log.i("testobject",mRequestBody);
        if (relatedItem.getQuantity().equalsIgnoreCase("0")){

            //RequestQueue mQueue2;
           // mQueue2=FarewayApplication.getmInstance(mContext).getmRequestQueue();

            try {

                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway text", response.toString());
                                relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                                SetProductActivateShopping(relatedItem.getPrimaryOfferTypeId(),relatedItem.getUPC(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
                                //holder.tv_quantity.setText(relatedItem.getQuantity());
                                //holder.add_item_flag.setText(relatedItem.getQuantity());

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
                        if (relatedItem.getPrimaryOfferTypeId()==2){
                            params.put("ClickType", "1");
                        }else {
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
                    mQueue2.add(jsonObjectRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                e.printStackTrace();


            }

        }
        else {
            url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
        }

        StringRequest jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        relatedItem.setQuantity(String.valueOf((Integer.parseInt(relatedItem.getQuantity())+1)));
                        SetProductActivateShopping(relatedItem.getPrimaryOfferTypeId(),relatedItem.getUPC(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));

                        // holder.tv_quantity.setText(relatedItem.getQuantity());
                       // holder.add_item_flag.setText(relatedItem.getQuantity());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
            }
        }){

            @Override
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
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
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
    }

    @Override
    public void onRelatedItemSelected3(final RelatedItem relatedItem) {
        Log.i("remove","remove");

        String url = Constant.WEB_URL+Constant.REMOVE+relatedItem.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        fetchShoppingListLoad();
                        if (relatedItem.getPrimaryOfferTypeId()==3|| relatedItem.getPrimaryOfferTypeId()==2 ){
                            relatedItem.setClickCount(0);
                            relatedItem.setListCount(0);
                            relatedItem.setQuantity("0");
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
                            groupcount=1;
                            //onProductVeritiesSelected(productrelated2);
                            veritiesGroupDetail(relatedItem.getCouponID());
                        }else if (relatedItem.getPrimaryOfferTypeId()==1){
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,"5");
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
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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
    }

    @Override
    public void onRelatedItemSelected4(final RelatedItem relatedItem) {
        Log.i("remove","remove");

        String url = Constant.WEB_URL+Constant.REMOVE+relatedItem.getUPC()+"&"+"MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        fetchShoppingListLoad();
                        if (relatedItem.getPrimaryOfferTypeId()==3|| relatedItem.getPrimaryOfferTypeId()==2 ){
                            relatedItem.setClickCount(0);
                            relatedItem.setListCount(0);
                            relatedItem.setQuantity("1");
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
                            groupcount=1;
                            //onProductVeritiesSelected(productrelated2);
                            veritiesGroupDetail(relatedItem.getCouponID());
                        }else if (relatedItem.getPrimaryOfferTypeId()==1){
                            SetProductRemoveDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,"5");
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
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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
    }

    @Override
    public void onRelatedItemSelected5(final RelatedItem relatedItem) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ACTIVATE,
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                fetchShoppingListLoad();
                                if (relatedItem.getPrimaryOfferTypeId()==3|| relatedItem.getPrimaryOfferTypeId()==2 ){
                                    relatedItem.setClickCount(1);
                                    relatedItem.setListCount(1);
                                    relatedItem.setQuantity("1");
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,String.valueOf((Integer.parseInt(relatedItem.getQuantity())+0)));
                                    groupcount=1;
                                    //onProductVeritiesSelected(productrelated2);
                                    veritiesGroupDetail(relatedItem.getCouponID());
                                }else if (relatedItem.getPrimaryOfferTypeId()==1){
                                    SetProductActivateDetaile(relatedItem.getPrimaryOfferTypeId(),relatedItem.getCouponID(),relatedItem.getUPC(),relatedItem.getRequiresActivation(),1,"5");
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

            String groupData="";
            for (int i = 0; i < jsonParam.length(); i++) {
                try {
                    if (jsonParam.getJSONObject(i).getString("Filter").contains(group)) {
                        Group=jsonParam.getJSONObject(i).getString("Filter");
                        JSONObject finalObject = jsonParam.getJSONObject(i);
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

    public void shoppingListTabLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.ShoppingList+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
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
                                            tv.setText(String.valueOf(0));

                                        }else {
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + "ShoppingList/List?"+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("ShoppingListId", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        Log.i("anshuman","test");

                                        try
                                        {
                                            shoppingId= root2.getJSONArray("ListName");
                                        }
                                        catch (Exception ex)
                                        {
                                            shoppingId = null;
                                        }

                                        if (shoppingId==null ){
                                            Log.i("shoppingId","test");
                                        /*    shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            tv.setText(String.valueOf(0));*/

                                        }else {
                                            Log.i("shoppingId", String.valueOf(shoppingId));

                                            for (int i = 0; i < shoppingId.length(); i++) {
                                                JSONObject jsonParam= shoppingId.getJSONObject(i);
                                                appUtil.setPrefrence("ShoppingListId", jsonParam.getString("ShoppingListId"));
                                                Log.i("ShoppingListId",appUtil.getPrefrence("ShoppingListId"));
                                                shoppingListLoad();

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
                //  progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.ShoppingList+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("shoppingList Response", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        // progressDialog.dismiss();
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
                                            Log.i("shopping","test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                            tv_number_item.setText(String.valueOf(0));
                                            tv.setText(String.valueOf(0));
                                            activatedOffersListIdLoad();

                                        }else {
                                            Log.i("shopping", String.valueOf(shopping));

                                            for (int i = 0; i < shopping.length(); i++) {
                                            }
                                            tv_number_item.setText(String.valueOf(shopping.length()));
                                            tv.setText(String.valueOf(shopping.length()));

                                            /*shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged();*/
                                            activatedOffersListIdLoad();
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
                // progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
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
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.ShoppingList+"MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=1",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
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
                                            tv.setText(String.valueOf(0));

                                        }else {
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
                        //progressDialog.dismiss();
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
                //progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShoppingItemSelected(final Shopping shopping) {
        Log.i("test",shopping.getDisplayUPC().replace("UPC: ",""));

        for (int i = 0; i < message.length(); i++) {


            try {
                if (message.getJSONObject(i).getString("UPC").contains(shopping.getDisplayUPC().replace("UPC: ",""))) {
                    message.getJSONObject(i).put("ListCount", 0);
                    message.getJSONObject(i).put("ClickCount", 1);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        fetchProduct();
        //shoppingListLoad();

        Log.i("remove","remove");
        //https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=404
        String url = Constant.WEB_URL+Constant.SHOPPINGLISTSINGAL+shopping.getShoppingListItemID()+"&MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        //shoppingListLoad();
                        fetchShoppingListLoad();
                       // remove_layout_detail.setVisibility(View.GONE);
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
                removeSingleOwnItem(shopping.getShoppingListItemID());
                Log.i("fail", String.valueOf(error));
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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


    }

    @Override
    public void onShoppingaddSelected(final Shopping shopping) {

    if (shopping.getPrimaryOfferTypeId()==0){
        String url = null;


    url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shopping.getShoppingListItemID()+"&Quantity="+(Integer.parseInt(shopping.getQuantity())+1);

    //url ="https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=505&Quantity=3"

    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
            new Response.Listener<String >() {
                @Override
                public void onResponse(String  response) {
                    Log.i("success", String.valueOf(response));
                    shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity())+1)));
                    tv_quantity_detail.setText(shopping.getQuantity());
                    add_item_flag_detail.setText(shopping.getQuantity());


                    SetProductActivateShopping(shopping.getPrimaryOfferTypeId(),shopping.getUPC(),1,String.valueOf((Integer.parseInt(shopping.getQuantity())+0)));
                    //fetchShoppingListLoad();
                    //activatedOffersListIdLoad();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("fail", String.valueOf(error));
        }
    }){

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

}

else {
    Calendar c2 = Calendar.getInstance();
    SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
    String currentDate = dateformat2.format(c2.getTime());
    System.out.println(currentDate);
    JSONObject ShoppingListItems = new JSONObject();
    try {
        ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
        ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())+1));
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
    final String mRequestBody = "'"+studentsObj.toString()+"'";
    Log.i("test",mRequestBody);
    //String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
    String url = null;
    Log.i("testobject",mRequestBody);


    url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");

    //url ="https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=505&Quantity=3"

    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
            new Response.Listener<String >() {
                @Override
                public void onResponse(String  response) {
                    Log.i("shopping Res", String.valueOf(response));
                    shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity())+1)));
                    //tv_quantity_detail.setText(shopping.getQuantity());
                    //add_item_flag_detail.setText(shopping.getQuantity());
                    Log.i("success", shopping.getDisplayUPC().replace("UPC","").replace(":",""));

                    SetProductActivateShopping(shopping.getPrimaryOfferTypeId(),shopping.getDisplayUPC().replace("UPC","").replace(" ","").replace(":",""),1,String.valueOf((Integer.parseInt(shopping.getQuantity()))));
                    //SetProductActivateDetaile(shopping.getPrimaryOfferTypeId(),shopping.getCouponID(),shopping.getUPC(),shopping.getRequiresActivation(),1,String.valueOf((Integer.parseInt(shopping.getQuantity())+0)));
                    //fetchShoppingListLoad();
                    //activatedOffersListIdLoad();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("fail", String.valueOf(error));
        }
    }){

        @Override
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
        }
        //this is the part, that adds the header to the request
        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/json");
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
}


    }

    @Override
    public void onShoppingsubSelected(final Shopping shopping) {

        if (shopping.getPrimaryOfferTypeId()==0){
            if (Integer.parseInt(shopping.getQuantity())>1){

                String url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shopping.getShoppingListItemID()+"&Quantity="+(Integer.parseInt(shopping.getQuantity())+1);

                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity())-1)));

                                SetProductActivateShopping(shopping.getPrimaryOfferTypeId(),shopping.getDisplayUPC().replace("UPC","").replace(" ","").replace(":",""),1,String.valueOf((Integer.parseInt(shopping.getQuantity()))));
                                //fetchShoppingListLoad();
                                //tv_quantity_detail.setText(shopping.getQuantity());
                                //add_item_flag_detail.setText(shopping.getQuantity());

                                //SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())-0)));

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){

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
            }
        }
        else {
            if (Integer.parseInt(shopping.getQuantity())>1){
                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat dateformat2 = new SimpleDateFormat("dd MMM yyyy");
                String currentDate = dateformat2.format(c2.getTime());
                System.out.println(currentDate);
                JSONObject ShoppingListItems = new JSONObject();
                try {
                    ShoppingListItems.put("UPC", shopping.getDisplayUPC().replace("UPC","").replace(":",""));
                    ShoppingListItems.put("Quantity", (Integer.parseInt(shopping.getQuantity())-1));
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
                final String mRequestBody = "'"+studentsObj.toString()+"'";
                Log.i("test",mRequestBody);
                String url = Constant.WEB_URL+Constant.SHOPPINGLIST+appUtil.getPrefrence("MemberId");
                StringRequest  jsonObjectRequest = new StringRequest (Request.Method.PUT, url,
                        new Response.Listener<String >() {
                            @Override
                            public void onResponse(String  response) {
                                Log.i("success", String.valueOf(response));
                                shopping.setQuantity(String.valueOf((Integer.parseInt(shopping.getQuantity())-1)));
                                fetchShoppingListLoad();
                                //tv_quantity_detail.setText(shopping.getQuantity());
                                //add_item_flag_detail.setText(shopping.getQuantity());

                                //SetProductActivateDetaile(product.getPrimaryOfferTypeId(),product.getCouponID(),product.getUPC(),product.getRequiresActivation(),1,String.valueOf((Integer.parseInt(product.getQuantity())-0)));

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("fail", String.valueOf(error));
                    }
                }){

                    @Override
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
                    }
                    //this is the part, that adds the header to the request
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
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
            }
        }


    }

    public class ViewRemoveAllDialog {

        public void showDialog(Activity activity, String msg){
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
                    String url = Constant.WEB_URL+"ShoopingList/ShoppingListByTYC?shoppinglistid="+appUtil.getPrefrence("ShoppingListId")+"&MemberId="+appUtil.getPrefrence("MemberId");
                    StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                            new Response.Listener<String >() {
                                @Override
                                public void onResponse(String  response) {
                                    Log.i("ViewRemoveAllDialog", String.valueOf(response));
                                    //shoppingListLoad();
                                    //removeOwnItem();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("fail", String.valueOf(error));
                           // messageLoad();
                            removeOwnItem();
                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<String, String>();
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

        public void showDialog(Activity activity, String msg){
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
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    public void activatedOffersListIdLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                //progressDialog = new ProgressDialog(activity);
                //progressDialog.setMessage("Processing");
                //progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + "ShoopingList/ActivatedCoupons?MemberId="+appUtil.getPrefrence("MemberId")+"&CategoryID=2",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway response Main", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    Log.i("message", root.getString("message"));


                                    JSONObject root2 = new JSONObject(root.getString("message"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        Log.i("anshuman","test");

                                        try
                                        {
                                            activatedOffer= root2.getJSONArray("WCouponsDetails");
                                        }
                                        catch (Exception ex)
                                        {
                                            activatedOffer = null;
                                        }

                                        if (activatedOffer==null ){
                                            Log.i("anshuman","test");
                                            shoppingArrayList.clear();
                                            shoppingListAdapter.notifyDataSetChanged();
                                           // tv_number_item.setText(String.valueOf(0));
                                           // tv.setText(String.valueOf(0));

                                        }else {
                                            Log.i("activatedOffer", String.valueOf(activatedOffer));

                                            for (int i = 0; i < activatedOffer.length(); i++) {
                                            }
                                           // tv_number_item.setText(String.valueOf(shopping.length()));
                                           // tv.setText(String.valueOf(shopping.length()));

                                         /* shoppingArrayList.clear();
                                            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
                                            }.getType());
                                            shoppingArrayList.addAll(items);
                                            shoppingListAdapter.notifyDataSetChanged(); */
                                         fetchShopping();
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
                          //progressDialog.dismiss();
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
                 //progressDialog.dismiss();
//                displayAlert();
            }
        } else {
            alertDialog=userAlertDialog.createPositiveAlert(getString(R.string.noInternet),
                    getString(R.string.ok),getString(R.string.alert));
            alertDialog.show();
            //Toast.makeText(activity, "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchShopping(){
       // Log.i("shopping", String.valueOf(shopping.length()));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
        z=1;

        if (shopping == null) {
            //no students
            shoppingArrayList.clear();
        }else {
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(shopping.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }

    }

    private void filterExpShopping(){
        // Log.i("shopping", String.valueOf(shopping.length()));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.white));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.black));
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.grey));
        z=1;

        if (shopping == null) {
            //no students
            shoppingArrayList.clear();
        }else {
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
           /* Collections.sort(myList, new Comparator<MyObject>() {
                public int compare(MyObject o1, MyObject o2) {
                    if (o1.getDateTime() == null || o2.getDateTime() == null)
                        return 0;
                    return o1.getDateTime().compareTo(o2.getDateTime());
                }
            });*/

            shoppingListAdapter.notifyDataSetChanged();
            //rv_shopping_list_items.setAdapter(shoppingListAdapter);
        }

        /*
        ArrayList<Events> futureEvents = new ArrayList<>();
        Date currentDate = new Date();

        for(Events events : eventsList) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = null;
            try {
                date = formatter.parse(events.getEventDate() + " " + events.getEventTime());
            } catch (ParseException e) {

            }

            if(currentDate.before(date)) {
                futureEvents.add(events);
            }
        }

        adapter.filter(futureEvents);*/

    }

    private void fetchActivatedOffer(){
        activated_offer_fragment.setBackground(getResources().getDrawable(R.color.white));
        activated_offer_fragment.setTextColor(getResources().getColor(R.color.black));
        shopping_list_fragment.setBackground(getResources().getDrawable(R.color.light_grey));
        shopping_list_fragment.setTextColor(getResources().getColor(R.color.grey));
        z=0;
        if (activatedOffer == null) {
            shoppingArrayList.clear();
            //no students
        }else {
            shoppingArrayList.clear();
            List<Shopping> items = new Gson().fromJson(activatedOffer.toString(), new TypeToken<List<Shopping>>() {
            }.getType());
            shoppingArrayList.addAll(items);
            shoppingListAdapter.notifyDataSetChanged();
        }
/*
        ArrayList<Events> futureEvents = new ArrayList<>();
        Date currentDate = new Date();

        for(Events events : eventsList) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = null;
            try {
                date = formatter.parse(events.getEventDate() + " " + events.getEventTime());
            } catch (ParseException e) {

            }

            if(currentDate.before(date)) {
                futureEvents.add(events);
            }
        }

        adapter.filter(futureEvents);*/

    }

    private void sendEmailShoppingList(final String emails){
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL +"ShoopingList/EmailShoppingList",
                        new Response.Listener<String>(){
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

    private void addShoppingItem(final String itemName) {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Processing");
                progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,Constant.WEB_URL + Constant.ADDSHOPPINGITEM,
                        new Response.Listener<String>(){
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

    public void removeOwnItem(){
        String url = Constant.WEB_URL+Constant.REMOVESHOPPINGOWMITEM+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("removeOwnItemsuccess", String.valueOf(response));
                        // fetchShoppingListLoad();
                        messageLoad();
                        //removeOwnItem();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("fail", String.valueOf(error));
                messageLoad();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
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
        //dialog.dismiss();

    }

    public void removeSingleOwnItem(String shoppingListItemID){
        Log.i("remove","remove");
        //https://fwstagingapi.immdemo.net/api/v1/ShoppingList/List/MyOwnItem?ShoppingListOwnItemID=404
        String url = Constant.WEB_URL+"ShoppingList/List/MyOwnItem?ShoppingListOwnItemID="+shoppingListItemID+"&MemberId="+appUtil.getPrefrence("MemberId");
        StringRequest  jsonObjectRequest = new StringRequest (Request.Method.DELETE, url,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        Log.i("success", String.valueOf(response));
                        //shoppingListLoad();
                        fetchShoppingListLoad();
                        // remove_layout_detail.setVisibility(View.GONE);
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
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", appUtil.getPrefrence("token_type")+" "+appUtil.getPrefrence("access_token"));
                params.put("Content-Type", "application/json ;charset=utf-8");
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
    }

    private void messageShoppingLoad() {
        if (ConnectivityReceiver.isConnected(activity) != NetworkUtils.TYPE_NOT_CONNECTED) {
            try {
               // progressDialog = new ProgressDialog(activity);
               // progressDialog.setMessage("Processing");
              //  progressDialog.show();
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,Constant.WEB_URL + Constant.PRODUCTLIST+"?memberid="+appUtil.getPrefrence("MemberId")+"&Plateform=2",
                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Log.i("Fareway Personal Deal", response.toString());

                                try {
                                    JSONObject root = new JSONObject(response);
                                    root.getString("errorcode");
                                    Log.i("errorcode", root.getString("errorcode"));
                                    if (root.getString("errorcode").equals("0")){
                                        //progressDialog.dismiss();
                                        message= root.getJSONArray("message");
                                        if (comeFrom.equalsIgnoreCase("moreOffer")){
                                            // moreCouponLoad();
                                            x=1;
                                        }else {
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
                // progressDialog.dismiss();
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
