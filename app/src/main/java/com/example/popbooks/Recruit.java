package com.example.popbooks;

public class Recruit {
    long scoutId;
    String name;
    String phone;
    public Recruit() {
        this.name = "";
        this.phone = "";
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
