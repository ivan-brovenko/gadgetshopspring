package com.epam.istore.repository.impl;


import com.epam.istore.model.Category;
import com.epam.istore.model.Product;
import com.epam.istore.model.ProducerCountry;
import com.epam.istore.repository.GadgetRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class GadgetRepositoryImpl implements GadgetRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Product> getFiltered(String query){
        return getSession().createSQLQuery(query).getResultList();
    }

    public Product getProductById(int productId){
        return getSession().get(Product.class, productId);
    }

    @Override
    public List<ProducerCountry> getAllCountries() {
        return getSession().createQuery("from producer_country").list();
    }


    @Override
    public List<Category> getAllCategories(){
        return getSession().createQuery("from category").list();
    }

    @Override
    public int getNumberOfRows(String query){
        return getFiltered(query).size();
    }

    private Session getSession() {
        return entityManagerFactory.createEntityManager().unwrap(Session.class);
    }
}
