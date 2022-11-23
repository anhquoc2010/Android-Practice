package com.example.loginlisttest.model;

public class CayThuocNam {
    private String name;
    private String color;
    private String description;
    private String image;

    public CayThuocNam() {
    }

    public CayThuocNam(String name, String color, String description, String image) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
