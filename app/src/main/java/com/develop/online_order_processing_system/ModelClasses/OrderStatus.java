package com.develop.online_order_processing_system.ModelClasses;

public class OrderStatus {

    private int price,quantity;
    private String productName,customerId,companysId;

    public OrderStatus() {
    }

    public OrderStatus(int price, int quantity, String productName, String customerId,String companysId) {
        this.price = price;
        this.quantity = quantity;
        this.productName = productName;
        this.customerId = customerId;
        this.companysId = companysId;
    }


    public String getCompanysId() {
        return companysId;
    }

    public void setCompanysId(String companysId) {
        this.companysId = companysId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
