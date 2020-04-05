package com.nishu.inventory_management_and_order_processing_system.ModelClasses;

public class OrderStatusForCompany {

    private String productName,aceptedOrder,randomKey,delivery_trak,packing,deliveryComplete;
    private String price, quantity, total,customerId,companysId,order,customerInfo,date,companysTitle;

    public OrderStatusForCompany() {
    }

    public OrderStatusForCompany(String productName, String price,
                                 String quantity, String total, String customerId,
                                 String companysId,String randomKey,String order,String delivery_trak,String packing,
                                 String deliveryComplete,String date,String companysTitle) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.customerId = customerId;
        this.companysId = companysId;
        this.randomKey = randomKey;
        this.order = order;
        this.delivery_trak = delivery_trak;
        this.packing = packing;
        this.deliveryComplete = deliveryComplete;
        this.date = date;
        this.companysTitle = companysTitle;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanysTitle() {
        return companysTitle;
    }

    public void setCompanysTitle(String companysTitle) {
        this.companysTitle = companysTitle;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAceptedOrder() {
        return aceptedOrder;
    }

    public void setAceptedOrder(String aceptedOrder) {
        this.aceptedOrder = aceptedOrder;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getDelivery_trak() {
        return delivery_trak;
    }

    public void setDelivery_trak(String delivery_trak) {
        this.delivery_trak = delivery_trak;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getDeliveryComplete() {
        return deliveryComplete;
    }

    public void setDeliveryComplete(String deliveryComplete) {
        this.deliveryComplete = deliveryComplete;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanysId() {
        return companysId;
    }

    public void setCompanysId(String companysId) {
        this.companysId = companysId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
