package com.epam.istore.dao.impl;


import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.dao.ProductRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getFiltered(String query){
        return entityManager.createNativeQuery(query, Product.class).getResultList();
    }

    public Product getProductById(int productId){
        return entityManager.find(Product.class, productId);
    }

    @Override
    public List<ProducerCountry> getAllCountries() {
        return entityManager.createQuery("from producer_country").getResultList();
    }


    @Override
    public List<Category> getAllCategories(){
        return entityManager.createQuery("from category").getResultList();
    }

    @Override
    public int getNumberOfRows(String query){
        return getFiltered(query).size();
    }

}
