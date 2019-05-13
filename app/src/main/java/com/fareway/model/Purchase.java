package com.fareway.model;

public class Purchase {

    private String purchasedate;
    private String storelocation;
    private String totalamount;
    private String remainamount;
    private String purchaseid;

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getStorelocation() {
        return storelocation;
    }

    public void setStorelocation(String storelocation) {
        this.storelocation = storelocation;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getRemainamount() {
        return remainamount;
    }

    public void setRemainamount(String remainamount) {
        this.remainamount = remainamount;
    }

    public String getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(String purchaseid) {
        this.purchaseid = purchaseid;
    }
}
