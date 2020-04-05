package com.nishu.inventory_management_and_order_processing_system.ModelClasses;

public class ShopKeeperInfo {
    private String sName,sPhone,sArea,sRoad,sNumber,sLicence;

    private String ProfilePic,CoverPic;

    public ShopKeeperInfo() {
    }

    public ShopKeeperInfo(String sName, String sPhone, String sArea, String sRoad, String sNumber, String sLicence) {
        this.sName = sName;
        this.sPhone = sPhone;
        this.sArea = sArea;
        this.sRoad = sRoad;
        this.sNumber = sNumber;
        this.sLicence = sLicence;
    }

    public ShopKeeperInfo(String profilePic, String coverPic) {
        ProfilePic = profilePic;
        CoverPic = coverPic;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getCoverPic() {
        return CoverPic;
    }

    public void setCoverPic(String coverPic) {
        CoverPic = coverPic;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsArea() {
        return sArea;
    }

    public void setsArea(String sArea) {
        this.sArea = sArea;
    }

    public String getsRoad() {
        return sRoad;
    }

    public void setsRoad(String sRoad) {
        this.sRoad = sRoad;
    }


    public String getsNumber() {
        return sNumber;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public String getsLicence() {
        return sLicence;
    }

    public void setsLicence(String sLicence) {
        this.sLicence = sLicence;
    }
}
