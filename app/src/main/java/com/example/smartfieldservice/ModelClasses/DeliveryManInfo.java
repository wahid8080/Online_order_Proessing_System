package com.example.smartfieldservice.ModelClasses;

public class DeliveryManInfo {

    private String userName,nid,phone,distrect,area,deliveryManProfile,companyId,companyName;

    public DeliveryManInfo() {
    }

    public DeliveryManInfo(String userName, String nid, String phone, String distrect, String area, String companyId,String companyName) {
        this.userName = userName;
        this.nid = nid;
        this.phone = phone;
        this.distrect = distrect;
        this.area = area;
        this.companyId = companyId;
        this.companyName = companyName;
    }


    public DeliveryManInfo(String userName, String companyName) {
        this.userName = userName;
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrect() {
        return distrect;
    }

    public void setDistrect(String distrect) {
        this.distrect = distrect;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDeliveryManProfile() {
        return deliveryManProfile;
    }

    public void setDeliveryManProfile(String deliveryManProfile) {
        this.deliveryManProfile = deliveryManProfile;
    }
}
