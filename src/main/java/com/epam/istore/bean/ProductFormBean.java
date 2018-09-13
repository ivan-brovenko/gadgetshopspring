package com.epam.istore.bean;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProductFormBean {
    private Integer id;
    private String priceMin;
    private String priceMax;
    private String[] category;
    private String productCountry;
    private String productName;
    private String productLimit;
    private String sortingType;
    private int productPageCount;
    private String currentPage;
}
