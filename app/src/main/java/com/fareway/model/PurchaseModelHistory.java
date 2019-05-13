package com.fareway.model;

public class PurchaseModelHistory {
    private String vDescription;
    private String image;
    private String fUnitprice;
    private String subtotalamount;
    private String iQuantity;
    private String vUPCCode;
    private String couponflag;
    private String subsavingamount;
    private String remainamount;

    public String getvDescription() {
        return vDescription;
    }

    public void setvDescription(String vDescription) {
        this.vDescription = vDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getfUnitprice() {
        return fUnitprice;
    }

    public void setfUnitprice(String fUnitprice) {
        this.fUnitprice = fUnitprice;
    }

    public String getSubtotalamount() {
        return subtotalamount;
    }

    public void setSubtotalamount(String subtotalamount) {
        this.subtotalamount = subtotalamount;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getvUPCCode() {
        return vUPCCode;
    }

    public void setvUPCCode(String vUPCCode) {
        this.vUPCCode = vUPCCode;
    }

    public String getCouponflag() {
        return couponflag;
    }

    public void setCouponflag(String couponflag) {
        this.couponflag = couponflag;
    }

    public String getSubsavingamount() {
        return subsavingamount;
    }

    public void setSubsavingamount(String subsavingamount) {
        this.subsavingamount = subsavingamount;
    }

    public String getRemainamount() {
        return remainamount;
    }

    public void setRemainamount(String remainamount) {
        this.remainamount = remainamount;
    }
}
