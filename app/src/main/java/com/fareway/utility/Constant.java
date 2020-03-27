package com.fareway.utility;

public class Constant {

    public static final String APP_VERSION = "10.8";
    public static final String WEB_URL="https://fwstagingapi.immdemo.net/api/v1/";
    public static final String PRINT_WEB_URL="https://fwstaging.immdemo.net/web/printshoppinglist.aspx?";
    public static final String GET_TOKEN="Token";
    public static final String LOGIN="Account/Login";
    public static final String LOGINSAVE="Account/SaveLogLogin?FileName=LoginLog";
    public static final String ERRORLOG="Error/Log";

    public static final String PRODUCTLIST="Circular/Offers";
    public static final String RELATEDITEMLIST="Circular/RelatedItems";
    public static final String SIGNUP="Account/SignUp";
    public static final String NEWPASSWORD="Account/ResetPassword";
    public static final String RESETPASSWORD="Account/GenerateOTP";
    public static final String VALIDATEPASSWORD="Account/ValidateOTP";
    public static final String ACTIVATE="Circular/ShoppingList";
    public static final String MORECOUPON="Circular/CouponGallary";
    public static final String SAVING="Circular/Saving";
    public static final String SEARCH="Circular/searchItems?";
    public static final String SHOPPINGLIST="ShoppingList/List/Item?MemberId=";
    public static final String REMOVE="ShoopingList/ShoppingListItemByupc?upccode=";
    public static final String SHOPPINGLISTSINGAL="ShoopingList/ShoppingListById?ShoppingListItemID=";
    public static final String ShoppingList="ShoopingList/ShoppingListByvTYC?";
    public static final String SHOPPINGLISTALL = "ShoopingList/ShoppingListByTYC?shoppinglistid=";
    public static final String PURCHASEHISTORY = "Account/PurchaseHistory?MemberId=";
    public static final String PURCHASEDETAILHISTORY = "Account/PurchaseHistoryDetails?PurchaseId=";
    public static final String ADDSHOPPINGITEM = "ShoopingList/List/MyOwnItem";
    public static final String REMOVESHOPPINGOWMITEM = "ShoopingList/AllownItems?Memberid=";
    public static final String PRISHOPPERID = "45";
    public static final String SHOPPINGLISTUPDATE = "ShoppingList/List/ParmItem";
    public static final String CHANGESTORE = "Account/StoresByMemberId";


    //https://fwstagingapi.immdemo.net/api/v1/Account/SaveLogLogin?FileName=LoginLog&Information=prajput@juno.com|123456|DeviceTypeId|jscd.os|jscd.osVersion|jscd.browser|jscd.browserVersion|document.URL.toString() |latitude.toString()|longitude.toString()|1
    //https://fwstagingapi.immdemo.net/api/v1/Account/SaveLogLogin?FileName=LoginLog&Information=prajput@juno.com|123456|mobile      |testos| 8.0.0|          test|       test|               test|                    37.521998333333336| -123.08499833333332
   // https://fwstagingapi.immdemo.net




    //url: APIUrl + "api/v1/ShoopingList/ShoppingListByTYC" + "?shoppinglistid=’0’&MemberId=’3433443’

    //api/v1/ShoopingList/ShoppingListById?ShoppingListItemID=’45656’&MemberId=’4558885’


    // http://platformapi.immdemo.net/api/v1/ShoopingList/ShoppingListItemByupc?upccode=7398132891&MemberId=1


    public static final String MEMBERID="memberid";

    //Related to change store
    public static final String GEOCODER_API = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String FINDSTORE = "https://fwstagingapi.immdemo.net/api/v1/Account/StoresByCurrentLocation?UserCurrentLatitude=";
    public static final String SELECT_STORE = "--Select Preferred Store";
    public static final String STATUS = "OK";
    public static final String ERRORCODE = "0";
    public static final String UPDATE_STORE = "https://fwstagingapi.immdemo.net/api/v1/Account/cirularMemberUpdateStore?ShopperID=";
    public static final String CHECK_CIRCULAR = "Circular/CircularCount?memberid=";


    public static final String CREATED = "Created";
}


