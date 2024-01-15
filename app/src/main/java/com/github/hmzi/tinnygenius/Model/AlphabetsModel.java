package com.github.hmzi.tinnygenius.Model;

public class AlphabetsModel {
    private String letter;
    private String bgColor;
    private int image;

    public AlphabetsModel(String letter, String bgColor, int image) {
        this.letter = letter;
        this.bgColor = bgColor;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
