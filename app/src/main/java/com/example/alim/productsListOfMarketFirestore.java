package com.example.alim;

import java.lang.reflect.Array;

public class productsListOfMarketFirestore  {

    String productArea,productCategory,productCondition,productDescription;
    String productId,productOwner,productPrice,productRegion,productTitle;

    public productsListOfMarketFirestore() {
    }

    public productsListOfMarketFirestore(String productArea, String productCategory, String productCondition, String productDescription, String productId, String productOwner, String productPrice, String productRegion, String productTitle) {
        this.productArea = productArea;
        this.productCategory = productCategory;
        this.productCondition = productCondition;
        this.productDescription = productDescription;
        this.productId = productId;
        this.productOwner = productOwner;
        this.productPrice = productPrice;
        this.productRegion = productRegion;
        this.productTitle = productTitle;
    }

    public String getProductArea() {
        return productArea;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductRegion() {
        return productRegion;
    }

    public String getProductTitle() {
        return productTitle;
    }
}
