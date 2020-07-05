package com.ziko.isaac.recyclerviewanimation.Model;

public class News {
    private String title;
    private String description;
    private String date;
    private int img;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public News() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public News(String title, String description, String date, int img) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.img = img;
    }
}
