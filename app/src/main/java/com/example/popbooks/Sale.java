package com.example.popbooks;

public class Sale {
    long id;
    long scoutId;
    String date;
    int type;
    int total;
    String location;

    public Sale() {
        this.date = "";
        this.type = 0;
        this.total = 0;
        this.location = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScoutId() {
        return scoutId;
    }

    public void setScoutId(long scoutId) {
        this.scoutId = scoutId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



}
