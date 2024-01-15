package com.github.hmzi.tinnygenius.Model;

public class ShapeModel {
    private String image;
    private String bgColor;

    public ShapeModel(String image, String bgColor) {
        this.image = image;
        this.bgColor = bgColor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
