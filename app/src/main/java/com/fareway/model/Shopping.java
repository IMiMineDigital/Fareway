package com.fareway.model;

public class Shopping {
    private int CategoryID;
    private String CategoryName;
    private String LongDescription;
    private String PurchaseAmount;
    private int PurchaseQty;
    private int PrimaryOfferTypeId;
    private String ImageURL;
    private String UPC;
    private String CouponDescription;
    private String ShoppingListItemID;
    private String DisplayUPC;

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getPurchaseAmount() {
        return PurchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        PurchaseAmount = purchaseAmount;
    }

    public int getPurchaseQty() {
        return PurchaseQty;
    }

    public void setPurchaseQty(int purchaseQty) {
        PurchaseQty = purchaseQty;
    }

    public int getPrimaryOfferTypeId() {
        return PrimaryOfferTypeId;
    }

    public void setPrimaryOfferTypeId(int primaryOfferTypeId) {
        PrimaryOfferTypeId = primaryOfferTypeId;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getCouponDescription() {
        return CouponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        CouponDescription = couponDescription;
    }

    public String getShoppingListItemID() {
        return ShoppingListItemID;
    }

    public void setShoppingListItemID(String shoppingListItemID) {
        ShoppingListItemID = shoppingListItemID;
    }

    public String getDisplayUPC() {
        return DisplayUPC;
    }

    public void setDisplayUPC(String displayUPC) {
        DisplayUPC = displayUPC;
    }
}
