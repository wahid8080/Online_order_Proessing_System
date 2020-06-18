package com.develop.online_order_processing_system.ModelClasses;

public class Feedback {
    private String comment,name;
    private int like,unLike;
    private float rating;


    public Feedback ()
    {

    }

    public Feedback(float rating, String comment, String name, int like, int unLike) {
        this.rating = rating;
        this.comment = comment;
        this.name = name;
        this.like = like;
        this.unLike = unLike;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUnLike() {
        return unLike;
    }

    public void setUnLike(int unLike) {
        this.unLike = unLike;
    }
}
