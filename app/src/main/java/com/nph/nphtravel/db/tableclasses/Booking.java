package com.nph.nphtravel.db.tableclasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Booking {
    private int id;
    private String created_date;
    private String status;
    private double tourPrice;
    private int quantity;
    private double total;
    private int userId;
    private int tourId;


    public Booking() {

    }
    public Booking(boolean completelyNew) {
        if (completelyNew) {
            this.id = new Random().nextInt(99999998) + 1;
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            this.created_date = df.format(date);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTourPrice() {
        return tourPrice;
    }

    public void setTourPrice(double tourPrice) {
        this.tourPrice = tourPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
