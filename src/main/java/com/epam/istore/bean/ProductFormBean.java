package com.epam.istore.bean;



public class ProductFormBean {
    private String priceMin;
    private String priceMax;
    private String[] category;
    private String productCountry;
    private String productName;
    private String productLimit;
    private String sortingType;
    private int productPageCount;
    private String currentPage;

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getProductCountry() {
        return productCountry;
    }

    public void setProductCountry(String productCountry) {
        this.productCountry = productCountry;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }

    public int getProductPageCount() {
        return productPageCount;
    }

    public void setProductPageCount(int productPageCount) {
        this.productPageCount = productPageCount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
