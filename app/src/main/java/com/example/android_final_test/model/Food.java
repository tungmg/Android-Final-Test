package com.example.android_final_test.model;

public class Food {

    private String Id, Name, Description, Image;
    private long Price, Discount;
    private String CategoryId;

    public Food() {
    }

    public Food(String id, String name, String description, String image, long price, long discount, String categoryId) {
        Id = id;
        Name = name;
        Description = description;
        Image = image;
        Price = price;
        Discount = discount;
        CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getPrice() {
        return Price;
    }

    public long getDiscount() {
        return Discount;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public void setDiscount(long discount) {
        Discount = discount;
    }
}
