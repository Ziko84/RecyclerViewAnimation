package com.ziko.isaac.recyclerviewanimation.Model;

public class EasySaleModel {
    private int item_id;
    private int quantity;
    private int sale_nis;
    private String img;

    public EasySaleModel(int item_id, int quantity, int sale_nis, String img) {
        this.item_id = item_id;
        this.quantity = quantity;
        this.sale_nis = sale_nis;
        this.img = img;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSale_nis() {
        return sale_nis;
    }

    public void setSale_nis(int sale_nis) {
        this.sale_nis = sale_nis;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
