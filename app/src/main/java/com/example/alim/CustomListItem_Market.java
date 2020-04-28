package com.example.alim;

public class CustomListItem_Market {

    String title;
    String featured_photos;
    String price;
    public CustomListItem_Market() {


    }

    public CustomListItem_Market(String title, String featured_photos, String price) {
        this.title = title;
        this.featured_photos = featured_photos;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeatured_photos() {
        return featured_photos;
    }

    public void setFeatured_photos(String featured_photos) {
        this.featured_photos = featured_photos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
