package com.example.android_final_test.model;

public class Category {

    private String Name, Image;

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public Category() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
