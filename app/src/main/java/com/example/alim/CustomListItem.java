package com.example.alim;

public class CustomListItem {


    String Title;
    int userPhoto;

    public CustomListItem(String title, int userPhoto) {
        Title = title;
        this.userPhoto = userPhoto;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }
}
