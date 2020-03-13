package com.example.smartfieldservice.ModelClasses;

public class CompanysInfo {
    private String cName,cEmail,cLocation,cLicence,CompanysDetails;

    private String ProfilePic,CoverPic,LicenceImage,feedback;

    private int like,unlike;
    private float rating;
    private double latitute;
    private double lagatitute;

    public CompanysInfo() {
    }

    public CompanysInfo(String cName, String cLocation, String cLicence,int like,int unlike ,float rating,double latitute,double lagatitute) {
        this.cName = cName;
        this.cLocation = cLocation;
        this.cLicence = cLicence;
        this.like = like;
        this.unlike = unlike;
        this.rating = rating;
        this.latitute = latitute;
        this.lagatitute = lagatitute;
    }

    public double getLatitute() {
        return latitute;
    }

    public double getLagatitute() {
        return lagatitute;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    public String getCompanysDetails() {
        return CompanysDetails;
    }

    public void setCompanysDetails(String companysDetails) {
        CompanysDetails = companysDetails;
    }

    public String getLicenceImage() {
        return LicenceImage;
    }

    public void setLicenceImage(String licenceImage) {
        LicenceImage = licenceImage;
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


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcLocation() {
        return cLocation;
    }

    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    public String getcLicence() {
        return cLicence;
    }

    public void setcLicence(String cLicence) {
        this.cLicence = cLicence;
    }
}
