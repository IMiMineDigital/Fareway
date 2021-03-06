package com.fareway.model;

public class Product {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private int OfferTypeID;
    private String Title;
    private String BannerImageURL;
    private String PackagingSize;
    private String LargeImagePath;
    private String RegularPrice;
    private String Value;
    private String LongDescription;
    private String BestPrice;
    private String CouponValue;
    private String SalesPrice;
    private String UPC;
    private String ValidityEndDate;
    private String PrimaryOfferTypeName;
    private String PriceAssociationCode;
    private String StoreID;
    private String RelevantUPC;
    private int PrimaryOfferTypeId;
    private String CouponID;
    private String CategoryID;
    private String CategoryName;
    private String OfferTypeTagName;
    private int ListCount;
    private int GroupCount;
    private int RelatedItemCount;
    private String Description;
    private String Savings;
    private String DisplayPrice;
    private String FinalPrice;
    private int PersonalCircularID;
    private int OfferDetailId;
    private int MemberID;
    private int HasRelatedItems;
    private int ClickCount;
    private int PageID;
    private int inCircular;
    private String RequiresActivation;
    private String Groupname;
    private int LimitPerTransection;
    private int RequiredQty;
    private String CouponDiscount;
    private String TileNumber;
    private String AdPrice;
    private String PricingMasterID;
    private String RewardType;
    private String Quantity;
    private String TotalQuantity;

    //private int MinAmount;
    private float MinAmount;
    private String PercentSavings;
    private String RewardValue;
    private String RewardQty;
    private int OfferDefinitionId;
    private String CouponImageURl;
    private String oCouponShortDescription;
    private String BadgeId;
    private String Isbadged;
    private String BadgeFileName;
    private String CouponShortDescription;
    private int CPRPromoTypeId;
    private String PosMultiple;
    private String IsFamily;
    private String IsStacked;

    public Product() {
    }

    public Product(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBannerImageURL() {
        return BannerImageURL;
    }

    public void setBannerImageURL(String bannerImageURL) {
        BannerImageURL = bannerImageURL;
    }

    public String getPackagingSize() {
        return PackagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        PackagingSize = packagingSize;
    }

    public int getOfferTypeID() {
        return OfferTypeID;
    }

    public void setOfferTypeID(int offerTypeID) {
        OfferTypeID = offerTypeID;
    }

    public String getLargeImagePath() {
        return LargeImagePath;
    }

    public void setLargeImagePath(String largeImagePath) {
        LargeImagePath = largeImagePath;
    }

    public String getRegularPrice() {
        return RegularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        RegularPrice = regularPrice;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getBestPrice() {
        return BestPrice;
    }

    public void setBestPrice(String bestPrice) {
        BestPrice = bestPrice;
    }

    public String getCouponValue() {
        return CouponValue;
    }

    public void setCouponValue(String couponValue) {
        CouponValue = couponValue;
    }

    public String getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        SalesPrice = salesPrice;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getValidityEndDate() {
        return ValidityEndDate;
    }

    public void setValidityEndDate(String validityEndDate) {
        ValidityEndDate = validityEndDate;
    }

    public String getPrimaryOfferTypeName() {
        return PrimaryOfferTypeName;
    }

    public void setPrimaryOfferTypeName(String primaryOfferTypeName) {
        PrimaryOfferTypeName = primaryOfferTypeName;
    }

    public String getPriceAssociationCode() {
        return PriceAssociationCode;
    }

    public void setPriceAssociationCode(String priceAssociationCode) {
        PriceAssociationCode = priceAssociationCode;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getRelevantUPC() {
        return RelevantUPC;
    }

    public void setRelevantUPC(String relevantUPC) {
        RelevantUPC = relevantUPC;
    }

    public int getPrimaryOfferTypeId() {
        return PrimaryOfferTypeId;
    }

    public void setPrimaryOfferTypeId(int primaryOfferTypeId) {
        PrimaryOfferTypeId = primaryOfferTypeId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getOfferTypeTagName() {
        return OfferTypeTagName;
    }

    public void setOfferTypeTagName(String offerTypeTagName) {
        OfferTypeTagName = offerTypeTagName;
    }

    public int getListCount() {
        return ListCount;
    }

    public void setListCount(int listCount) {
        ListCount = listCount;
    }

    public int getGroupCount() {
        return GroupCount;
    }

    public void setGroupCount(int groupCount) {
        GroupCount = groupCount;
    }

    public int getRelatedItemCount() {
        return RelatedItemCount;
    }

    public void setRelatedItemCount(int relatedItemCount) {
        RelatedItemCount = relatedItemCount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSavings() {
        return Savings;
    }

    public void setSavings(String savings) {
        Savings = savings;
    }

    public String getDisplayPrice() {
        return DisplayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        DisplayPrice = displayPrice;
    }



    public int getPersonalCircularID() {
        return PersonalCircularID;
    }

    public void setPersonalCircularID(int personalCircularID) {
        PersonalCircularID = personalCircularID;
    }

    public int getOfferDetailId() {
        return OfferDetailId;
    }

    public void setOfferDetailId(int offerDetailId) {
        OfferDetailId = offerDetailId;
    }

    public String getFinalPrice() {
        return FinalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        FinalPrice = finalPrice;
    }

    public int getMemberID() {
        return MemberID;
    }

    public void setMemberID(int memberID) {
        MemberID = memberID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public int getHasRelatedItems() {
        return HasRelatedItems;
    }

    public void setHasRelatedItems(int hasRelatedItems) {
        HasRelatedItems = hasRelatedItems;
    }

    public int getClickCount() {
        return ClickCount;
    }

    public void setClickCount(int clickCount) {
        ClickCount = clickCount;
    }

    public int getPageID() {
        return PageID;
    }

    public void setPageID(int pageID) {
        PageID = pageID;
    }

    public int getInCircular() {
        return inCircular;
    }

    public void setInCircular(int inCircular) {
        this.inCircular = inCircular;
    }

    public String getRequiresActivation() {
        return RequiresActivation;
    }

    public void setRequiresActivation(String requiresActivation) {
        RequiresActivation = requiresActivation;
    }

    public String getGroupname() {
        return Groupname;
    }

    public void setGroupname(String groupname) {
        Groupname = groupname;
    }

    public int getLimitPerTransection() {
        return LimitPerTransection;
    }

    public void setLimitPerTransection(int limitPerTransection) {
        LimitPerTransection = limitPerTransection;
    }

    public int getRequiredQty() {
        return RequiredQty;
    }

    public void setRequiredQty(int requiredQty) {
        RequiredQty = requiredQty;
    }

    public String getCouponDiscount() {
        return CouponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        CouponDiscount = couponDiscount;
    }

    public String getTileNumber() {
        return TileNumber;
    }

    public void setTileNumber(String tileNumber) {
        TileNumber = tileNumber;
    }

    public String getAdPrice() {
        return AdPrice;
    }

    public void setAdPrice(String adPrice) {
        AdPrice = adPrice;
    }

    public String getPricingMasterID() {
        return PricingMasterID;
    }

    public void setPricingMasterID(String pricingMasterID) {
        PricingMasterID = pricingMasterID;
    }

    public String getRewardType() {
        return RewardType;
    }

    public void setRewardType(String rewardType) {
        RewardType = rewardType;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    /*public int getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(int minAmount) {
        MinAmount = minAmount;
    }*/

    public String getPercentSavings() {
        return PercentSavings;
    }

    public void setPercentSavings(String percentSavings) {
        PercentSavings = percentSavings;
    }

    public String getRewardValue() {
        return RewardValue;
    }

    public void setRewardValue(String rewardValue) {
        RewardValue = rewardValue;
    }

    public String getRewardQty() {
        return RewardQty;
    }

    public void setRewardQty(String rewardQty) {
        RewardQty = rewardQty;
    }

    public int getOfferDefinitionId() {
        return OfferDefinitionId;
    }

    public void setOfferDefinitionId(int offerDefinitionId) {
        OfferDefinitionId = offerDefinitionId;
    }

    public String getCouponImageURl() {
        return CouponImageURl;
    }

    public void setCouponImageURl(String couponImageURl) {
        CouponImageURl = couponImageURl;
    }

    public String getoCouponShortDescription() {
        return oCouponShortDescription;
    }

    public void setoCouponShortDescription(String oCouponShortDescription) {
        this.oCouponShortDescription = oCouponShortDescription;
    }

    public String getBadgeId() {
        return BadgeId;
    }

    public void setBadgeId(String badgeId) {
        BadgeId = badgeId;
    }

    public String getIsbadged() {
        return Isbadged;
    }

    public void setIsbadged(String isbadged) {
        Isbadged = isbadged;
    }

    public String getBadgeFileName() {
        return BadgeFileName;
    }

    public void setBadgeFileName(String badgeFileName) {
        BadgeFileName = badgeFileName;
    }

    public String getCouponID() {
        return CouponID;
    }

    public void setCouponID(String couponID) {
        CouponID = couponID;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getCouponShortDescription() {
        return CouponShortDescription;
    }

    public void setCouponShortDescription(String couponShortDescription) {
        CouponShortDescription = couponShortDescription;
    }

    public int getCPRPromoTypeId() {
        return CPRPromoTypeId;
    }

    public void setCPRPromoTypeId(int CPRPromoTypeId) {
        this.CPRPromoTypeId = CPRPromoTypeId;
    }

    public String getPosMultiple() {
        return PosMultiple;
    }

    public void setPosMultiple(String posMultiple) {
        PosMultiple = posMultiple;
    }

    public String getIsFamily() {
        return IsFamily;
    }

    public void setIsFamily(String isFamily) {
        IsFamily = isFamily;
    }

    public String getIsStacked() {
        return IsStacked;
    }

    public void setIsStacked(String isStacked) {
        IsStacked = isStacked;
    }

    public float getMinAmount() {
        return MinAmount;
    }

    public void setMinAmount(float minAmount) {
        MinAmount = minAmount;
    }
}


