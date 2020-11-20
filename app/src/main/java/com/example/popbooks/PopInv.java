package com.example.popbooks;

public class PopInv {
    long id;
    long scoutId;
    long saleId;
    String name;
    String desc;
    double price;
    int qty;
    int img;

    public PopInv() {
        this.name = "";
        this.desc = "";
        this.price = 0.0;
        this.qty = 0;
    }
    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getImg() {
        return img;
    }

    public void setImg(String name) {
        switch(name){
            case "White Cheddar":
                this.img = R.drawable.whitecheddar;
                break;
            case "Unbelievable Butter Popcorn":
                this.img = R.drawable.unbelieveable;
                break;
            case "Salted Caramel Popcorn":
                this.img = R.drawable.saltedcaramel;
                break;
            case "Dark Chocolate Salted Caramels":
                this.img = R.drawable.darkchosaltcaramels;
                break;
            case "Caramel Corn":
                this.img = R.drawable.caramelcorn;
                break;
            case "Blazing Hot Popcorn":
                this.img = R.drawable.blazinghot;
                break;
            case "30 Dollar American Heroes Donation":
                this.img = R.drawable.amerheroes;
                break;
            case "Chocolatey Caramel Crunch Tin":
                this.img = R.drawable.choctin;
                break;
            default:
                this.img = R.drawable.defaultpop;

        }
    }
}
