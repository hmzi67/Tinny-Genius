package com.github.hmzi.tinnygenius.Model;

public class Users {
    private String userID;
    private String userName;
    private String userEmail;
    private String userImg;

    private String userScore;

    public Users() {
        // empty constructor
    }

    public Users(String userID, String userName, String userEmail, String userImg, String userScore) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImg = userImg;
        this.userScore = userScore;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScrore) {
        this.userScore = userScrore;
    }
}
