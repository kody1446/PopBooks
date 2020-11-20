package com.example.popbooks;


public class Scout {
    long id;
    String name;
    int rank;
    int img;
    public Scout(){
        this.name = "";
        this.rank = 1;
        this.img = R.drawable.ic_cub_scouts;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
