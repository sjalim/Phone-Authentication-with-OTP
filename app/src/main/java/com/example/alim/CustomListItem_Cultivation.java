package com.example.alim;

public class CustomListItem_Cultivation {


    String Title;
    int userPhoto;

    public CustomListItem_Cultivation(String title, int userPhoto) {
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
