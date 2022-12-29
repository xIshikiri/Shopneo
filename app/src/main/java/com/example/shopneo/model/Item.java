package com.example.shopneo.model;

import androidx.annotation.NonNull;

public class Item {
    private int id;
    private String name;
    private String desc;
    private String photo;
    private float price;
    private String type;

    public Item(int id, String name, String desc, String photo, float price, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (Float.compare(item.price, price) != 0) return false;
        if (!name.equals(item.name)) return false;
        if (!desc.equals(item.desc)) return false;
        if (!photo.equals(item.photo)) return false;
        return type.equals(item.type);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + photo.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}