package com.nishu.inventory_management_and_order_processing_system.ModelClasses;

public class ProductUpload {
    private String productName,productDesc,productPrice,companysId,productOffer;
    private String filePth;
    private int like,unlike;
    private float rating;

    public ProductUpload() {
    }


    public ProductUpload(String productName, String productDesc, String productPrice, String filePth,String companysId,String productOffer){
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.filePth = filePth;
        this.companysId = companysId;
        this.productOffer = productOffer;

    }

    public String getProductOffer() {
        return productOffer;
    }

    public void setProductOffer(String productOffer) {
        this.productOffer = productOffer;
    }

    public String getFilePth() {
        return filePth;
    }

    public String getCompanysId() {
        return companysId;
    }

    public void setCompanysId(String companysId) {
        this.companysId = companysId;
    }

    public void setFilePth(String filePth) {
        this.filePth = filePth;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
