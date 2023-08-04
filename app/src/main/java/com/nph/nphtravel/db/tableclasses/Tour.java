package com.nph.nphtravel.db.tableclasses;

import java.util.Date;

public class Tour {
    private String id;
    private String tour_name;
    private double price;
    private String description;
    private String location;
    private String start_day;
    private String end_day;
    private double discount;
    private String avatar;

    private String category_id;

    //thêm category


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Tour(){

    }
    public Tour(String tour_name, double price, String description, String location, String start_day, String end_day,
                double discount, String avatar, String category_id){
        this.tour_name = tour_name;
        this.price = price;
        this.description = description;
        this.location = location;
        this.start_day = start_day;
        this.end_day = end_day;
        this.discount = discount;
        this.avatar = avatar;
        this.category_id = category_id;


    }

    public Tour(String id_Tour,String tour_name, double price, String description, String location, String start_day, String end_day,
                double discount, String avatar, String category_id){
        this.id = id_Tour;
        this.tour_name = tour_name;
        this.price = price;
        this.description = description;
        this.location = location;
        this.start_day = start_day;
        this.end_day = end_day;
        this.discount = discount;
        this.avatar = avatar;
        this.category_id = category_id;


    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
