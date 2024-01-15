package com.github.hmzi.tinnygenius.Classes;

public class ScreenItem {
    String Title, Description;
    int ScreenImg;

    // constructor
    public ScreenItem(String title, String description, int screenImg){
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }

    //setter method
    public void setTitle(String title) {
        Title = title;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    // getter method
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public int getScreenImg() {
        return ScreenImg;
    }

}
