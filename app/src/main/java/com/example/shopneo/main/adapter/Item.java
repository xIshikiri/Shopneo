package com.example.shopneo.main.adapter;

public class Item {
    private String name;
    private String desc;
    private String photo;
    private float price;
    private String type;

    public Item(String name, String desc, String photo, float price, String type) {
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.price = price;
        this.type = type;
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

        if (Float.compare(item.price, price) != 0) return false;
        if (!name.equals(item.name)) return false;
        if (!desc.equals(item.desc)) return false;
        if (!photo.equals(item.photo)) return false;
        return type.equals(item.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + photo.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + type.hashCode();
        return result;
    }
}