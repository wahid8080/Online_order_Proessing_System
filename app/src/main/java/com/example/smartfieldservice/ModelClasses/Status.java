package com.example.smartfieldservice.ModelClasses;

public class Status {
    private String company,shopkeeper,deliveryMan;

    public Status() {
    }

    public Status(String company, String shopkeeper, String deliveryMan) {
        this.company = company;
        this.shopkeeper = shopkeeper;
        this.deliveryMan = deliveryMan;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShopkeeper() {
        return shopkeeper;
    }

    public void setShopkeeper(String shopkeeper) {
        this.shopkeeper = shopkeeper;
    }
}
